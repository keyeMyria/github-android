package com.sxq.github.ui.modules.repos.commit;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.Toast;

import com.sxq.github.R;
import com.sxq.github.ui.adapter.ReposCommitAdapter;
import com.sxq.github.ui.base.BaseFragment;
import com.sxq.github.ui.modules.repos.branch.ReposBranchModule;
import com.sxq.github.ui.widgets.FontTextView;
import com.sxq.github.ui.widgets.StateLayout;
import com.sxq.github.ui.widgets.recyclerview.DynamicRecyclerView;
import com.sxq.github.ui.widgets.recyclerview.scroll.InfiniteScroll;
import com.sxq.github.ui.widgets.recyclerview.view_holder.BaseViewHolder;
import com.sxq.github.utils.InputHelper;
import com.sxq.github.utils.common.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import github.repos.GetCommitsQuery;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class ReposCommitFragment extends BaseFragment {

    private static String TAG_LOGIN = "tag_login";
    private static String TAG_REPOS_NAME = "tag_repos_name";
    private static String TAG_BRANCH = "tag_branch";

    @BindView(R.id.branches)
    FontTextView mBranches;
    @BindView(R.id.recycler)
    DynamicRecyclerView mRecycler;
    @BindView(R.id.refresh)
    SwipeRefreshLayout mRefresh;
    @BindView(R.id.stateLayout)
    StateLayout mStateLayout;

    @Nullable
    private String mLogin;

    @Nullable
    private String mReposName;

    @Nullable
    private String mBranch;

    @Nullable
    private String mLastPageCursor;

    @NonNull
    private ReposCommitViewModel mReposCommitViewModel;
    private CompositeDisposable mCompositeDisposable;

    private ReposCommitAdapter mReposCommitAdapter;

    public static ReposCommitFragment newInstance(@NonNull String login, @NonNull String reposName) {
        Bundle args = new Bundle();
        args.putString(TAG_LOGIN, login);
        args.putString(TAG_REPOS_NAME, reposName);
        ReposCommitFragment fragment = new ReposCommitFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @OnClick(R.id.branches)
    void onClick(View v) {
        ReposBranchModule.launch(getChildFragmentManager(), mLogin, mReposName,
                new ReposBranchModule.BranchSelectedListener() {
                    @Override
                    public void onBranchSelected(String selectedBranch) {
                        Timber.d("onBranchSelected:%s", selectedBranch);
                        mBranch = selectedBranch;
                        bindViewModel();
                    }
                });
    }

    @Override
    protected int fragmentLayout() {
        return R.layout.fragment_repos_commit;
    }

    @Override
    protected void onFragmentCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            mLogin = getArguments().getString(TAG_LOGIN);
            mReposName = getArguments().getString(TAG_REPOS_NAME);
            mBranch = Constants.MASTER;
        }
        mReposCommitViewModel = ReposCommitModule.createViewModel(mLogin, mReposName, mBranch);
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
        mBranches.setText(mBranch);
        mCompositeDisposable.clear();
        Disposable disposable = mReposCommitViewModel.getUiModel(mBranch, mLastPageCursor)
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

    private void updateUi(ReposCommitUiModel reposCommitUiModel) {
        if (InputHelper.isEmpty(reposCommitUiModel) || InputHelper.isEmpty(reposCommitUiModel.getCommitData().repository().ref().target().asCommit().history().edges())) {
            showEmptyView();
            return;
        }
        hideEmptyView();
        getAndSetLastPageCursor(reposCommitUiModel);
        mReposCommitAdapter = new ReposCommitAdapter(new ArrayList<>(reposCommitUiModel.getCommitData().repository().ref().target().asCommit().history().edges()));
        mReposCommitAdapter.setListener(new BaseViewHolder.OnItemClickListener<GetCommitsQuery.Edge>() {
            @Override
            public void onItemClick(int position, View v, GetCommitsQuery.Edge item) {
                /**
                 * TODO Jump to commit detail
                 */
            }

            @Override
            public void onItemLongClick(int position, View v, GetCommitsQuery.Edge item) {

            }
        });
        mRecycler.addDivider();
        mRecycler.setAdapter(mReposCommitAdapter);
        mRecycler.setNestedScrollingEnabled(true);
        mRecycler.addOnScrollListener(new InfiniteScroll() {
            @Override
            public boolean onLoadMore(int page, int totalItemCount) {
                Timber.d("page=%d,totalItemCount=%d", page, totalItemCount);
                mStateLayout.showProgress();
                Disposable disposable = mReposCommitViewModel.getUiModel(mBranch, mLastPageCursor)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(newReposCommitUiModel -> {
                            showMore(newReposCommitUiModel);
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
                bindViewModel();
                mRefresh.setRefreshing(false);
            }
        });
    }

    private void showMore(ReposCommitUiModel newUiModel) {
        if (InputHelper.isEmpty(newUiModel) || InputHelper.isEmpty(newUiModel.getCommitData().repository().ref().target().asCommit().history().edges())) {
            showNoMoreData();
            return;
        }
        getAndSetLastPageCursor(newUiModel);
        mReposCommitAdapter.addItems(newUiModel.getCommitData().repository().ref().target().asCommit().history().edges());
    }

    private String getAndSetLastPageCursor(ReposCommitUiModel uiModel) {
        List<GetCommitsQuery.Edge> cursors = uiModel.getCommitData().repository().ref().target().asCommit().history().edges();
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
        mReposCommitAdapter.removeProgress();
    }
}



