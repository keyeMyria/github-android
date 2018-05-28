package com.sxq.github.ui.modules.profile.starred;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.Toast;

import com.sxq.github.R;
import com.sxq.github.ui.adapter.ProfileStarredAdapter;
import com.sxq.github.ui.base.BaseFragment;
import com.sxq.github.ui.widgets.StateLayout;
import com.sxq.github.ui.widgets.recyclerview.DynamicRecyclerView;
import com.sxq.github.ui.widgets.recyclerview.scroll.InfiniteScroll;
import com.sxq.github.ui.widgets.recyclerview.view_holder.BaseViewHolder;
import com.sxq.github.utils.InputHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import github.profile.GetStarredReposQuery;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by shixiaoqiang01 on 2018/5/19.
 */

public class ProfileStarredFragment extends BaseFragment {

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
    private ProfileStarredViewModel mProfileStarredViewModel;
    private CompositeDisposable mCompositeDisposable;

    private ProfileStarredAdapter mProfileReposAdapter;

    public static ProfileStarredFragment newInstance(String login) {
        Bundle args = new Bundle();
        args.putString(TAG_LOGIN, login);
        ProfileStarredFragment fragment = new ProfileStarredFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int fragmentLayout() {
        return R.layout.small_grid_refresh_list;
    }

    @Override
    protected void onFragmentCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            mLogin = getArguments().getString(TAG_LOGIN);
        }

        mProfileStarredViewModel = ProfileStarredModule.createViewModel(mLogin);
        mCompositeDisposable = new CompositeDisposable();
        bindViewModel();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unBindViewModel();
    }

    private void bindViewModel() {
        mLastPageCursor = null;
        mCompositeDisposable.clear();
        Disposable disposable = mProfileStarredViewModel.getUiModel(mLastPageCursor)
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

    private void updateUi(ProfileStarredUiModel profileStarredUiModel) {
        if (InputHelper.isEmpty(profileStarredUiModel) || InputHelper.isEmpty(profileStarredUiModel.getStarredReposData().user().starredRepositories().nodes())) {
            showEmptyView();
            return;
        }
        hideEmptyView();
        getAndSetLastPageCursor(profileStarredUiModel);
        mProfileReposAdapter = new ProfileStarredAdapter(new ArrayList<>(profileStarredUiModel.getStarredReposData().user().starredRepositories().nodes()));
        mProfileReposAdapter.setListener(new BaseViewHolder.OnItemClickListener<GetStarredReposQuery.Node>() {
            @Override
            public void onItemClick(int position, View v, GetStarredReposQuery.Node item) {
                mProfileStarredViewModel.getProfileStarredNavigator().navigateToReposActivity(getActivity(), mLogin, item.name());


            }

            @Override
            public void onItemLongClick(int position, View v, GetStarredReposQuery.Node item) {
            }
        });
        mRecycler.addDivider();
        mRecycler.setAdapter(mProfileReposAdapter);
        mRecycler.setNestedScrollingEnabled(true);
        mRecycler.addOnScrollListener(new InfiniteScroll() {
            @Override
            public boolean onLoadMore(int page, int totalItemCount) {
                Timber.d("page=%d,totalItemCount=%d", page, totalItemCount);
                mStateLayout.showProgress();
                Disposable disposable = mProfileStarredViewModel.getUiModel(mLastPageCursor)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(newUiModel -> {
                            showMore(newUiModel);
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

    private void showMore(ProfileStarredUiModel newUiModel) {
        if (InputHelper.isEmpty(newUiModel) || InputHelper.isEmpty(newUiModel.getStarredReposData().user().starredRepositories().nodes())) {
            showNoMoreData();
            return;
        }
        getAndSetLastPageCursor(newUiModel);
        mProfileReposAdapter.addItems(newUiModel.getStarredReposData().user().starredRepositories().nodes());
    }

    private String getAndSetLastPageCursor(ProfileStarredUiModel uiModel) {
        List<GetStarredReposQuery.Edge> cursors = uiModel.getStarredReposData().user().starredRepositories().edges();
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
        mProfileReposAdapter.removeProgress();
    }
}
