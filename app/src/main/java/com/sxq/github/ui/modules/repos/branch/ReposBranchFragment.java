package com.sxq.github.ui.modules.repos.branch;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.sxq.github.R;
import com.sxq.github.ui.adapter.ReposBranchAdapter;
import com.sxq.github.ui.base.BaseDialogFragment;
import com.sxq.github.ui.widgets.StateLayout;
import com.sxq.github.ui.widgets.recyclerview.DynamicRecyclerView;
import com.sxq.github.ui.widgets.recyclerview.view_holder.BaseViewHolder;
import com.sxq.github.utils.InputHelper;

import java.util.ArrayList;

import butterknife.BindView;
import github.repos.GetBranchesQuery;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class ReposBranchFragment extends BaseDialogFragment {

    public static final String TAG = ReposBranchFragment.class.getSimpleName();
    private static String TAG_LOGIN = "tag_login";
    private static String TAG_REPOS_NAME = "tag_repos_name";
    @BindView(R.id.recycler)
    DynamicRecyclerView mRecycler;
    @BindView(R.id.refresh)
    SwipeRefreshLayout mRefresh;
    @BindView(R.id.stateLayout)
    StateLayout mStateLayout;

    @NonNull
    private String mLogin;

    @NonNull
    private String mReposName;

    @NonNull
    private ReposBranchViewModel mReposBranchViewModel;

    private CompositeDisposable mCompositeDisposable;

    private ReposBranchAdapter mReposBranchAdapter;

    private ReposBranchModule.BranchSelectedListener mBranchSelectedListener;

    public static ReposBranchFragment newInstance(@NonNull String login, @NonNull String reposName) {
        Bundle args = new Bundle();
        ReposBranchFragment fragment = new ReposBranchFragment();
        args.putString(TAG_LOGIN, login);
        args.putString(TAG_REPOS_NAME, reposName);
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

        mReposBranchViewModel = ReposBranchModule.createViewModel(mLogin, mReposName);
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

    public void setBranchSelectedListener(ReposBranchModule.BranchSelectedListener branchSelectedListener) {
        mBranchSelectedListener = branchSelectedListener;
    }

    private void bindViewModel() {
        mCompositeDisposable.clear();
        Disposable disposable = mReposBranchViewModel.getUiModel()
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

    private void updateUi(ReposBranchUiModel uiModel) {
        if (InputHelper.isEmpty(uiModel) || InputHelper.isEmpty(uiModel.getBranches())) {
            showEmptyView();
            return;
        }
        hideEmptyView();
        mReposBranchAdapter = new ReposBranchAdapter(new ArrayList<>(uiModel.getBranches()));
        mReposBranchAdapter.setListener(new BaseViewHolder.OnItemClickListener<GetBranchesQuery.Node>() {
            @Override
            public void onItemClick(int position, View v, GetBranchesQuery.Node item) {
                if (mBranchSelectedListener != null) {
                    mBranchSelectedListener.onBranchSelected(item.name());
                    Timber.d("onBranchSelected:%s", item.name());
                }
                dismiss();
            }

            @Override
            public void onItemLongClick(int position, View v, GetBranchesQuery.Node item) {
            }
        });
        mRecycler.addDivider();
        mRecycler.setAdapter(mReposBranchAdapter);
        mRecycler.setNestedScrollingEnabled(true);
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


    private void showEmptyView() {
        mStateLayout.hideProgress();
        mRecycler.setEmptyView(mStateLayout);
    }

    private void hideEmptyView() {
        mStateLayout.setVisibility(View.GONE);
    }
}

