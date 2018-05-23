package com.sxq.github.ui.modules.profile.followers;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.Toast;

import com.sxq.github.R;
import com.sxq.github.ui.adapter.ProfileFollowerAdapter;
import com.sxq.github.ui.base.BaseFragment;
import com.sxq.github.ui.widgets.StateLayout;
import com.sxq.github.ui.widgets.recyclerview.DynamicRecyclerView;
import com.sxq.github.ui.widgets.recyclerview.scroll.InfiniteScroll;
import com.sxq.github.ui.widgets.recyclerview.view_holder.BaseViewHolder;
import com.sxq.github.utils.InputHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import github.profile.GetFollowerQuery;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by shixiaoqiang01 on 2018/5/19.
 */

public class ProfileFollowerFragment extends BaseFragment {

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
    private ProfileFollowerViewModel mProfileFollowerViewModel;
    private CompositeDisposable mCompositeDisposable;

    private ProfileFollowerAdapter mProfileFollowerAdapter;

    public static ProfileFollowerFragment newInstance(String login) {
        Bundle args = new Bundle();
        args.putString(TAG_LOGIN, login);
        ProfileFollowerFragment fragment = new ProfileFollowerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int fragmentLayout() {
        return R.layout.fragment_profile_followers;
    }

    @Override
    protected void onFragmentCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            mLogin = getArguments().getString(TAG_LOGIN);
        }

        mProfileFollowerViewModel = ProfileFollowerModule.createViewModel(mLogin);
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
        mLastPageCursor = null;
        mCompositeDisposable.clear();
        Disposable disposable = mProfileFollowerViewModel.getUiModel(mLastPageCursor)
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

    private void updateUi(ProfileFollowerUiModel profileFollowerUiModel) {
        if (InputHelper.isEmpty(profileFollowerUiModel) || InputHelper.isEmpty(profileFollowerUiModel.getFollowerData().user().followers().nodes())) {
            showEmptyView();
            return;
        }
        hideEmptyView();
        getAndSetLastPageCursor(profileFollowerUiModel);
        mProfileFollowerAdapter = new ProfileFollowerAdapter(new ArrayList<>(profileFollowerUiModel.getFollowerData().user().followers().nodes()));
        mProfileFollowerAdapter.setListener(new BaseViewHolder.OnItemClickListener<GetFollowerQuery.Node>() {
            @Override
            public void onItemClick(int position, View v, GetFollowerQuery.Node item) {
                /**
                 * TODO jump to {@link com.sxq.github.ui.modules.user.UserActivity}
                 */
            }

            @Override
            public void onItemLongClick(int position, View v, GetFollowerQuery.Node item) {
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
                Disposable disposable = mProfileFollowerViewModel.getUiModel(mLastPageCursor)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(newProfileFollowerModel -> {
                            showMore(newProfileFollowerModel);
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

    private void showMore(ProfileFollowerUiModel newUiModel) {
        if (InputHelper.isEmpty(newUiModel) || InputHelper.isEmpty(newUiModel.getFollowerData().user().followers().nodes())) {
            showNoMoreData();
            return;
        }
        getAndSetLastPageCursor(newUiModel);
        mProfileFollowerAdapter.addItems(newUiModel.getFollowerData().user().followers().nodes());
    }

    private String getAndSetLastPageCursor(ProfileFollowerUiModel uiModel) {
        List<GetFollowerQuery.Edge> cursors = uiModel.getFollowerData().user().followers().edges();
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
