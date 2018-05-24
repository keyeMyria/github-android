package com.sxq.github.ui.modules.repos.files;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.sxq.github.R;
import com.sxq.github.ui.adapter.ReposFilesAdapter;
import com.sxq.github.ui.base.BaseFragment;
import com.sxq.github.ui.widgets.StateLayout;
import com.sxq.github.ui.widgets.recyclerview.DynamicRecyclerView;
import com.sxq.github.ui.widgets.recyclerview.view_holder.BaseViewHolder;
import com.sxq.github.utils.InputHelper;
import com.sxq.github.utils.common.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;
import github.repos.GetCurrentLevelTreeViewQuery;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class ReposFilesFragment extends BaseFragment {

    private static String TAG_LOGIN = "tag_login";
    private static String TAG_REPOS_NAME = "tag_repos_name";
    private static String TAG_BRANCH = "tag_branch";

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

    @NonNull
    private List<String> mPathList = new ArrayList<>();

    @NonNull
    private ReposFilesViewModel mReposFilesViewModel;
    private CompositeDisposable mCompositeDisposable;

    private ReposFilesAdapter mReposFilesAdapter;

    public static ReposFilesFragment newInstance(@NonNull String login, @NonNull String reposName) {
        Bundle args = new Bundle();
        args.putString(TAG_LOGIN, login);
        args.putString(TAG_REPOS_NAME, reposName);
        ReposFilesFragment fragment = new ReposFilesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int fragmentLayout() {
        return R.layout.fragment_repos_files;
    }

    @Override
    protected void onFragmentCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            mLogin = getArguments().getString(TAG_LOGIN);
            mReposName = getArguments().getString(TAG_REPOS_NAME);
            mBranch = Constants.MASTER;
        }
        mReposFilesViewModel = ReposFilesModule.createViewModel(mLogin, mReposName, mBranch);
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
        Disposable disposable = mReposFilesViewModel.getUiModel(mBranch, formatToPath())
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

    private void updateUi(ReposFilesUiModel reposFilesUiModel) {
        if (InputHelper.isEmpty(reposFilesUiModel) || InputHelper.isEmpty(reposFilesUiModel.getFileEntries())) {
            showEmptyView();
            return;
        }
        hideEmptyView();
        mReposFilesAdapter = new ReposFilesAdapter(new ArrayList<>(reposFilesUiModel.getFileEntries()));
        mReposFilesAdapter.setListener(new BaseViewHolder.OnItemClickListener<GetCurrentLevelTreeViewQuery.Entry>() {
            @Override
            public void onItemClick(int position, View v, GetCurrentLevelTreeViewQuery.Entry item) {
                if (item.type().equals(getContext().getResources().getString(R.string.tree_type_dir))
                        || item.type().equals(getContext().getResources().getString(R.string.tree_type_tree))) {
                    forward(item.name());
                }else{
                    /**
                     * todo jump to {@link com.sxq.github.ui.modules.repos.files.detail.ReposFileContentActivity}
                     */
                }
            }

            @Override
            public void onItemLongClick(int position, View v, GetCurrentLevelTreeViewQuery.Entry item) {
            }
        });
        mRecycler.addDivider();
        mRecycler.setAdapter(mReposFilesAdapter);
        mRecycler.setNestedScrollingEnabled(true);
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

    public void forward(String dirName) {
        mPathList.add(dirName);
        bindViewModel();
    }

    public void back() {
        if (mPathList.size() > 0) {
            mPathList.remove(mPathList.size() - 1);
            bindViewModel();
        }
    }

    private String formatToPath() {
        StringBuffer path = new StringBuffer();
        if (mPathList.size() > 0) {
            path.append(mPathList.stream().collect(Collectors.joining("/")))
                    .append("/");
        }
        Timber.d("path:" + path.toString());
        return path.toString();
    }

    private void showEmptyView() {
        mStateLayout.hideProgress();
        mRecycler.setEmptyView(mStateLayout);
    }

    private void hideEmptyView() {
        mStateLayout.setVisibility(View.GONE);
    }
}



