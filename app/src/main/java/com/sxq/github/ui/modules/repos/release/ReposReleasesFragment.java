package com.sxq.github.ui.modules.repos.release;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.Toast;

import com.sxq.github.R;
import com.sxq.github.ui.adapter.ReposReleaseAdapter;
import com.sxq.github.ui.base.BaseFragment;
import com.sxq.github.ui.widgets.StateLayout;
import com.sxq.github.ui.widgets.recyclerview.DynamicRecyclerView;
import com.sxq.github.ui.widgets.recyclerview.scroll.InfiniteScroll;
import com.sxq.github.ui.widgets.recyclerview.view_holder.BaseViewHolder;
import com.sxq.github.utils.InputHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import github.repos.GetReleasesQuery;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by shixiaoqiang01 on 2018/5/19.
 */

public class ReposReleasesFragment extends BaseFragment {

    private static String TAG_LOGIN = "tag_login";
    private static String TAG_REPOS_NAME = "tag_repos_name";

    @BindView(R.id.recycler)
    DynamicRecyclerView mRecycler;
    @BindView(R.id.refresh)
    SwipeRefreshLayout mRefresh;
    @BindView(R.id.stateLayout)
    StateLayout mStateLayout;

    @Nullable
    private String mLogin;

    private String mReposName;

    @Nullable
    private String mLastPageCursor;

    @NonNull
    private ReposReleasesViewModel mReposReleasesViewModel;
    private CompositeDisposable mCompositeDisposable;

    private ReposReleaseAdapter mReposReleaseAdapter;

    public static ReposReleasesFragment newInstance(@NonNull String login, @NonNull String reposName) {
        Bundle args = new Bundle();
        args.putString(TAG_LOGIN, login);
        args.putString(TAG_REPOS_NAME, reposName);
        ReposReleasesFragment fragment = new ReposReleasesFragment();
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
            mReposName = getArguments().getString(TAG_REPOS_NAME);
        }

        mReposReleasesViewModel = ReposReleasesModule.createViewModel(mLogin, mReposName);
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
        Disposable disposable = mReposReleasesViewModel.getUiModel(mLastPageCursor)
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

    private void updateUi(ReposReleasesUiModel reposReleasesUiModel) {
        if (InputHelper.isEmpty(reposReleasesUiModel)
                || InputHelper.isEmpty(reposReleasesUiModel.getReleasesData().repository())
                || InputHelper.isEmpty(reposReleasesUiModel.getReleasesData().repository().releases().nodes())) {
            showEmptyView();
            return;
        }
        hideEmptyView();
        getAndSetLastPageCursor(reposReleasesUiModel);
        mReposReleaseAdapter = new ReposReleaseAdapter(new ArrayList<>(reposReleasesUiModel.getReleasesData().repository().releases().nodes()));
        mReposReleaseAdapter.setListener(new BaseViewHolder.OnItemClickListener<GetReleasesQuery.Node>() {
            @Override
            public void onItemClick(int position, View v, GetReleasesQuery.Node item) {
                if (item.author() != null && !InputHelper.isEmpty(item.author().login()))
                    mReposReleasesViewModel.getReposReleasesNavigator().navigateToUserActivity(getActivity(), item.author().login());
            }

            @Override
            public void onItemLongClick(int position, View v, GetReleasesQuery.Node item) {
            }
        });
        mRecycler.addDivider();
        mRecycler.setAdapter(mReposReleaseAdapter);
        mRecycler.setNestedScrollingEnabled(true);
        mRecycler.addOnScrollListener(new InfiniteScroll() {
            @Override
            public boolean onLoadMore(int page, int totalItemCount) {
                Timber.d("page=%d,totalItemCount=%d", page, totalItemCount);
                mStateLayout.showProgress();
                Disposable disposable = mReposReleasesViewModel.getUiModel(mLastPageCursor)
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

    private void showMore(ReposReleasesUiModel newUiModel) {
        if (InputHelper.isEmpty(newUiModel)
                || InputHelper.isEmpty(newUiModel.getReleasesData().repository())
                || InputHelper.isEmpty(newUiModel.getReleasesData().repository().releases().nodes())) {
            showNoMoreData();
            return;
        }
        getAndSetLastPageCursor(newUiModel);
        mReposReleaseAdapter.addItems(newUiModel.getReleasesData().repository().releases().nodes());
    }

    private String getAndSetLastPageCursor(ReposReleasesUiModel uiModel) {
        List<GetReleasesQuery.Edge> cursors = uiModel.getReleasesData().repository().releases().edges();
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
        mReposReleaseAdapter.removeProgress();
    }
}
