package com.sxq.github.ui.widgets.recyclerview.scroll;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.sxq.github.ui.widgets.adapter.BaseRecyclerAdapter;


public abstract class InfiniteScroll extends RecyclerView.OnScrollListener {
    private int mVisibleThreshold = 3;
    private int mCurrentPage = 0;
    private int mPreviousTotalItemCount = 0;
    private boolean mLoading = true;
    private int mStartingPageIndex = 0;
    private RecyclerView.LayoutManager mLayoutManager;
    private BaseRecyclerAdapter mBaseRecyclerAdapter;
    private boolean mNewlyAdded = true;


    public InfiniteScroll() {
    }

    private void initLayoutManager(RecyclerView.LayoutManager layoutManager) {
        this.mLayoutManager = layoutManager;
        if (layoutManager instanceof GridLayoutManager) {
            mVisibleThreshold = mVisibleThreshold * ((GridLayoutManager) layoutManager).getSpanCount();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            mVisibleThreshold = ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
        }
    }

    private int getLastVisibleItem(int[] lastVisibleItemPositions) {
        int maxSize = 0;
        for (int i = 0; i < lastVisibleItemPositions.length; i++) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i];
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i];
            }
        }
        return maxSize;
    }


    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        if (mNewlyAdded) {
            mNewlyAdded = false;
            return;
        }
        onScrolled(dy > 0);
        if (mLayoutManager == null) {
            initLayoutManager(recyclerView.getLayoutManager());
        }
        if (mBaseRecyclerAdapter == null) {
            if (recyclerView.getAdapter() instanceof BaseRecyclerAdapter) {
                mBaseRecyclerAdapter = (BaseRecyclerAdapter) recyclerView.getAdapter();
            }
        }

        if (mBaseRecyclerAdapter != null && mBaseRecyclerAdapter.isProgressAdded()) return;
        int lastVisibleItemPosition = 0;
        int totalItemCount = mLayoutManager.getItemCount();
        if (mLayoutManager instanceof StaggeredGridLayoutManager) {
            int[] lastVisibleItemPositions = ((StaggeredGridLayoutManager) mLayoutManager).findLastVisibleItemPositions(null);
            lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions);
        } else if (mLayoutManager instanceof GridLayoutManager) {
            lastVisibleItemPosition = ((GridLayoutManager) mLayoutManager).findLastVisibleItemPosition();
        } else if (mLayoutManager instanceof LinearLayoutManager) {
            lastVisibleItemPosition = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
        }
        if (totalItemCount < mPreviousTotalItemCount) {
            this.mCurrentPage = this.mStartingPageIndex;
            this.mPreviousTotalItemCount = totalItemCount;
            if (totalItemCount == 0) {
                this.mLoading = true;
            }
        }
        if (mLoading && (totalItemCount > mPreviousTotalItemCount)) {
            mLoading = false;
            mPreviousTotalItemCount = totalItemCount;
        }
        if (!mLoading && (lastVisibleItemPosition + mVisibleThreshold) > totalItemCount) {
            mCurrentPage++;
            boolean isCallingApi = onLoadMore(mCurrentPage, totalItemCount);
            mLoading = true;
            if (mBaseRecyclerAdapter != null && isCallingApi) {
                mBaseRecyclerAdapter.addProgress();
            }
        }
    }

    public void reset() {
        this.mCurrentPage = this.mStartingPageIndex;
        this.mPreviousTotalItemCount = 0;
        this.mLoading = true;
    }

    public abstract boolean onLoadMore(int page, int totalItemCount);

    public void onScrolled(boolean isUp) {

    }
}
