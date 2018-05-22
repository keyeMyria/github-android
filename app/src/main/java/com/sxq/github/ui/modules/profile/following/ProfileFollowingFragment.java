package com.sxq.github.ui.modules.profile.following;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.Toast;

import com.sxq.github.R;
import com.sxq.github.ui.adapter.ProfileFollowingAdapter;
import com.sxq.github.ui.base.BaseFragment;
import com.sxq.github.ui.widgets.StateLayout;
import com.sxq.github.ui.widgets.recyclerview.DynamicRecyclerView;
import com.sxq.github.ui.widgets.recyclerview.scroll.InfiniteScroll;
import com.sxq.github.ui.widgets.recyclerview.view_holder.BaseViewHolder;
import com.sxq.github.utils.InputHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import github.profile.GetFollowingQuery;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by shixiaoqiang01 on 2018/5/19.
 */

public class ProfileFollowingFragment extends BaseFragment {

    private static String TAG_LOGIN = "tag_login";
    @BindView(R.id.recycler)
    DynamicRecyclerView mRecycler;
    @BindView(R.id.refresh)
    SwipeRefreshLayout mRefresh;
    @BindView(R.id.stateLayout)
    StateLayout mStateLayout;

    @Nullable
    private String mLogin;
    @Nullable
    private String mLastPageCursor;

    @NonNull
    private ProfileFollowingViewModel mProfileFollowingViewModel;
    private CompositeDisposable mCompositeDisposable;

    private ProfileFollowingAdapter mProfileFollowerAdapter;

    public static ProfileFollowingFragment newInstance(String login) {
        Bundle args = new Bundle();
        args.putString(TAG_LOGIN, login);
        ProfileFollowingFragment fragment = new ProfileFollowingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int fragmentLayout() {
        return R.layout.fragment_profile_following;
    }

    @Override
    protected void onFragmentCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            mLogin = getArguments().getString(TAG_LOGIN);
        }

        mProfileFollowingViewModel = ProfileFollowingModule.createViewModel(mLogin);
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void onResume() {
        super.onResume();
        bindViewModel();
    }

    @Override
    public void onPause() {
        super.onPause();
        unBindViewModel();
    }

    private void bindViewModel() {
        mCompositeDisposable.clear();
        Disposable disposable = mProfileFollowingViewModel.getUiModel(mLastPageCursor)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        this::updateUi,
                        throwable -> {
                            Timber.e(throwable);
                        }

                );
        mCompositeDisposable.add(disposable);
    }

    private void unBindViewModel() {
        mCompositeDisposable.clear();
    }

    private void updateUi(ProfileFollowingUiModel profileFollowingUiModel) {
        if (InputHelper.isEmpty(profileFollowingUiModel) || InputHelper.isEmpty(profileFollowingUiModel.getFollowingData().user().following().nodes())) {
            showEmptyView();
            return;
        }
        hideEmptyView();
        getAndSetLastPageCursor(profileFollowingUiModel);
        mProfileFollowerAdapter = new ProfileFollowingAdapter(new ArrayList<>(profileFollowingUiModel.getFollowingData().user().following().nodes()));
        mProfileFollowerAdapter.setListener(new BaseViewHolder.OnItemClickListener<GetFollowingQuery.Node>() {
            @Override
            public void onItemClick(int position, View v, GetFollowingQuery.Node item) {
                /**
                 * TODO jump to {@link com.sxq.github.ui.modules.user.UserActivity}
                 */
            }

            @Override
            public void onItemLongClick(int position, View v, GetFollowingQuery.Node item) {
            }
        });
        mRecycler.addDivider();
        mRecycler.setAdapter(mProfileFollowerAdapter);
        mRecycler.setNestedScrollingEnabled(true);
        mRecycler.addOnScrollListener(new InfiniteScroll() {
            @Override
            public boolean onLoadMore(int page, int totalItemCount) {
                Timber.d("page=%d,totalItemCount=%d", page, totalItemCount);
                mStateLayout.showProgress();
                Disposable disposable = mProfileFollowingViewModel.getUiModel(mLastPageCursor)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(newProfileFollowingModel -> {
                            showMore(newProfileFollowingModel);
                        }, throwable -> {
                            Timber.e(throwable);
                        }, () -> {
                            mStateLayout.hideProgress();
                        });
                mCompositeDisposable.add(disposable);
                return true;
            }
        });
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                /**
                 * TODO update
                 */

                mRefresh.setRefreshing(false);
            }
        });
    }

    private void showMore(ProfileFollowingUiModel newUiModel) {
        if (InputHelper.isEmpty(newUiModel) || InputHelper.isEmpty(newUiModel.getFollowingData().user().following().nodes())) {
            showNoMoreData();
            return;
        }
        getAndSetLastPageCursor(newUiModel);
        mProfileFollowerAdapter.addItems(newUiModel.getFollowingData().user().following().nodes());
    }

    private String getAndSetLastPageCursor(ProfileFollowingUiModel uiModel) {
        List<GetFollowingQuery.Edge> cursors = uiModel.getFollowingData().user().following().edges();
        mLastPageCursor = cursors.get(cursors.size() - 1).cursor().toString();
        return mLastPageCursor;
    }

    private void showEmptyView() {
        mStateLayout.hideProgress();
        mRecycler.setEmptyView(mStateLayout);
    }

    private void hideEmptyView() {
        mStateLayout.setVisibility(View.GONE);
    }

    private void showNoMoreData() {
        Toast.makeText(getActivity(), "No More", Toast.LENGTH_SHORT).show();
        mStateLayout.hideProgress();// can not hide when there is no more data.
        /**
         * TODO need to optimize, the way to hide progress, no move it.
         */
        mProfileFollowerAdapter.removeProgress();
    }
}
