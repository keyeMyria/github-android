package com.sxq.github.ui.modules.profile.feeds;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.Toast;

import com.sxq.github.R;
import com.sxq.github.ui.adapter.ProfileFeedsAdapter;
import com.sxq.github.ui.base.BaseFragment;
import com.sxq.github.ui.widgets.StateLayout;
import com.sxq.github.ui.widgets.recyclerview.DynamicRecyclerView;
import com.sxq.github.ui.widgets.recyclerview.scroll.InfiniteScroll;
import com.sxq.github.ui.widgets.recyclerview.view_holder.BaseViewHolder;
import com.sxq.github.utils.InputHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import github.user.GetProfileFeedsQuery;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by shixiaoqiang01 on 2018/5/19.
 */

public class ProfileFeedsFragment extends BaseFragment {

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
    private ProfileFeedsViewModel mProfileFeedsViewModel;
    private CompositeDisposable mCompositeDisposable;

    private ProfileFeedsAdapter mProfileFeedsAdapter;

    public static ProfileFeedsFragment newInstance(String login) {
        Bundle args = new Bundle();
        args.putString(TAG_LOGIN, login);
        ProfileFeedsFragment fragment = new ProfileFeedsFragment();
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

        mProfileFeedsViewModel = ProfileFeedsModule.createViewModel(mLogin);
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
        Disposable disposable = mProfileFeedsViewModel.getUiModel(mLastPageCursor)
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

    private void updateUi(ProfileFeedsUiModel uiModel) {
        if (InputHelper.isEmpty(uiModel)
                || InputHelper.isEmpty(uiModel.getFeedsData().user().repositories())
                || InputHelper.isEmpty(uiModel.getFeedsData().user().repositories().nodes())) {
            showEmptyView();
            return;
        }
        hideEmptyView();
        getAndSetLastPageCursor(uiModel);
        mProfileFeedsAdapter = new ProfileFeedsAdapter(new ArrayList<>(uiModel.getFeedsData().user().repositories().nodes()));
        mProfileFeedsAdapter.setListener(new BaseViewHolder.OnItemClickListener<GetProfileFeedsQuery.Node>() {
            @Override
            public void onItemClick(int position, View v, GetProfileFeedsQuery.Node item) {
                mProfileFeedsViewModel.getProfileFeedsNavigator().navigateToReposActivity(getActivity(),
                        mLogin, item.refs().edges().get(0).node().target().asCommit().repository().name());
            }

            @Override
            public void onItemLongClick(int position, View v, GetProfileFeedsQuery.Node item) {
            }
        });
        mRecycler.addDivider();
        mRecycler.setAdapter(mProfileFeedsAdapter);
        mRecycler.setNestedScrollingEnabled(true);
        mRecycler.addOnScrollListener(new InfiniteScroll() {
            @Override
            public boolean onLoadMore(int page, int totalItemCount) {
                Timber.d("page=%d,totalItemCount=%d", page, totalItemCount);
                mStateLayout.showProgress();
                Disposable disposable = mProfileFeedsViewModel.getUiModel(mLastPageCursor)
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

    private void showMore(ProfileFeedsUiModel newUiModel) {
        if (InputHelper.isEmpty(newUiModel)
                || InputHelper.isEmpty(newUiModel.getFeedsData().user().repositories())
                || InputHelper.isEmpty(newUiModel.getFeedsData().user().repositories().nodes())) {
            showNoMoreData();
            return;
        }
        getAndSetLastPageCursor(newUiModel);
        mProfileFeedsAdapter.addItems(newUiModel.getFeedsData().user().repositories().nodes());
    }

    private String getAndSetLastPageCursor(ProfileFeedsUiModel uiModel) {
        List<GetProfileFeedsQuery.Edge> cursors = uiModel.getFeedsData().user().repositories().edges();
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
        mProfileFeedsAdapter.removeProgress();
    }
}
