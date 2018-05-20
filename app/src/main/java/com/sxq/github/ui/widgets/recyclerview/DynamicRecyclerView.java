package com.sxq.github.ui.widgets.recyclerview;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.View;

import com.sxq.github.R;
import com.sxq.github.ui.widgets.StateLayout;
import com.sxq.github.ui.widgets.recyclerview.decoration.BottomPaddingDecoration;
import com.sxq.github.ui.widgets.recyclerview.decoration.InsetDividerDecoration;
import com.sxq.github.utils.ViewHelper;

public class DynamicRecyclerView extends RecyclerView {

    private StateLayout mEmptyView;

    @Nullable
    private View mParentView;

    private BottomPaddingDecoration mBottomPaddingDecoration;

    @NonNull
    private AdapterDataObserver mObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            showEmptyView();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            showEmptyView();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
            showEmptyView();
        }
    };

    public DynamicRecyclerView(Context context) {
        this(context, null);
    }

    public DynamicRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DynamicRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        if (isInEditMode()) return;
        if (adapter != null) {
            adapter.registerAdapterDataObserver(mObserver);
            mObserver.onChanged();
        }
    }

    public void removeBottomDecoration() {
        if (mBottomPaddingDecoration != null) {
            removeItemDecoration(mBottomPaddingDecoration);
            mBottomPaddingDecoration = null;
        }
    }

    public void addDecoration() {
        mBottomPaddingDecoration = BottomPaddingDecoration.with(getContext());
        addItemDecoration(mBottomPaddingDecoration);
    }

    private void showEmptyView() {
        Adapter<?> adapter = getAdapter();
        if (adapter != null) {
            if (mEmptyView != null) {
                if (adapter.getItemCount() == 0) {
                    showParentOrSelf(false);
                } else {
                    showParentOrSelf(true);
                }
            }
        } else {
            if (mEmptyView != null) {
                showParentOrSelf(false);
            }
        }
    }

    private void showParentOrSelf(boolean showRecyclerView) {
        if (mParentView != null) {
            mParentView.setVisibility(VISIBLE);
        }

        setVisibility(VISIBLE);
        mEmptyView.setVisibility(!showRecyclerView ? VISIBLE : GONE);
    }

    public void setEmptyView(@NonNull StateLayout emptyView, @Nullable View parentView) {
        this.mEmptyView = emptyView;
        this.mParentView = parentView;
        showEmptyView();
    }

    public void setEmptyView(@NonNull StateLayout emptyView) {
        setEmptyView(emptyView, null);
    }

    public void hideProgress(@NonNull StateLayout view) {
        view.hideProgress();
    }

    public void showProgress(@NonNull StateLayout view) {
        view.showProgress();
    }

    public void addKeyListener() {
        if (canAddDivider()) {
            Resources resources = getResources();
            addItemDecoration(new InsetDividerDecoration(
                    resources.getDimensionPixelSize(R.dimen.divider_height),
                    resources.getDimensionPixelSize(R.dimen.keyline_2),
                    ViewHelper.getListDiver(getContext())));
        }
    }

    public void addDivider() {
        if (canAddDivider()) {
            Resources resources = getResources();
            addItemDecoration(new InsetDividerDecoration(
                    resources.getDimensionPixelSize(R.dimen.divider_height),
                    0,
                    ViewHelper.getListDiver(getContext())
            ));
        }
    }

    public void addNormalSpacingDivider() {
        addDivider();
    }

    private boolean canAddDivider() {
        if (getLayoutManager() != null) {
            if (getLayoutManager() instanceof GridLayoutManager) {
                return (((GridLayoutManager) getLayoutManager()).getSpanCount() == 1);
            } else if (getLayoutManager() instanceof LinearLayoutManager) {
                return true;
            } else if (getLayoutManager() instanceof StaggeredGridLayoutManager) {
                return (((StaggeredGridLayoutManager) getLayoutManager()).getSpanCount() == 1);
            }
        }
        return false;
    }
}
