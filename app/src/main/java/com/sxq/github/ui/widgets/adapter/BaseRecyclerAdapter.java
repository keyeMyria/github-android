package com.sxq.github.ui.widgets.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.sxq.github.ui.widgets.recyclerview.view_holder.BaseViewHolder;
import com.sxq.github.ui.widgets.recyclerview.view_holder.ProgressBarViewHolder;
import com.sxq.github.utils.AnimHelper;
import com.sxq.github.utils.PrefGetter;

import java.util.ArrayList;
import java.util.List;



public abstract class BaseRecyclerAdapter<M, VH extends BaseViewHolder,
        P extends BaseViewHolder.OnItemClickListener<M>> extends RecyclerView.Adapter<VH> {

    private final static int PROGRESS_TYPE = 2017;

    @NonNull
    private List<M> mData;
    @Nullable
    private P mListener;

    private int mLasKnowingPosition = -1;
    private boolean mEnableAnimation = PrefGetter.isRVAnimationEnabled();
    private boolean mShowedGuide;
    private GuideListener mGuideListener;
    private boolean mProgressAdded;
    private int mRowWidth;

    protected BaseRecyclerAdapter() {
        this(new ArrayList<>());
    }

    protected BaseRecyclerAdapter(@NonNull List<M> data) {
        this(data, null);
    }

    protected BaseRecyclerAdapter(@Nullable P listener) {
        this(new ArrayList<>(), listener);
    }

    protected BaseRecyclerAdapter(@NonNull List<M> data, @Nullable P listener) {
        mData = data;
        mListener = listener;
    }

    protected abstract VH viewHolder(ViewGroup parent, int viewType);

    protected abstract void onBindView(VH holder, int position);

    @NonNull
    public List<M> getData() {
        return mData;
    }

    public M getItem(int position) {
        return getData().get(position);
    }

    public int getItemPosition(M m) {
        return getData().indexOf(m);
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == PROGRESS_TYPE) {
            addSpanLookup(parent);
            return (VH) ProgressBarViewHolder.newInstance(parent);
        } else {
            return viewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        if (holder instanceof ProgressBarViewHolder) {
            if (((ProgressBarViewHolder) holder).itemView.getLayoutParams() instanceof StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams layoutParams = (StaggeredGridLayoutManager.LayoutParams) ((ProgressBarViewHolder) holder).itemView.getLayoutParams();
                layoutParams.setFullSpan(true);
            }
        } else if (getItem(position) != null) {
            animate(holder, position);
            onBindView(holder, position);
            onShowGuide(holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return getData().size();
    }


    @Override
    public int getItemViewType(int position) {
        if (getItem(position) == null) {
            return PROGRESS_TYPE;
        }
        return super.getItemViewType(position);
    }

    private void onShowGuide(@NonNull VH holder, int position) {
        if (position == 0 && !isShowedGuide() && mGuideListener != null) {
            mGuideListener.onShowGuide(holder.itemView, getItem(position));
            mShowedGuide = true;
        }
    }


    private void animate(@NonNull VH holder, int position) {
        if (isEnableAnimation() && position > mLasKnowingPosition) {
            AnimHelper.startBeatsAnimation(holder.itemView);
            mLasKnowingPosition = position;
        }

    }

    public void insertItems(@NonNull List<M> items) {
        getData().clear();
        getData().addAll(items);
        notifyDataSetChanged();
        mProgressAdded = false;
    }

    public void addItem(M item, int position) {
        getData().add(position, item);
        notifyDataSetChanged();
    }

    public void addItem(M item) {
        removeProgress();
        getData().add(item);
        if (getData().size() == 0) {
            notifyDataSetChanged();
        } else {
            notifyItemInserted(getData().size() - 1);
        }
    }

    public void addItems(@NonNull List<M> items) {
        removeProgress();
        getData().addAll(items);
        notifyItemRangeInserted(getItemCount(), (getItemCount() + items.size()) - 1);
    }

    public void removeItem(int position) {
        getData().remove(position);
        notifyItemRemoved(position);
    }

    public void removeItems(@NonNull List<M> items) {
        int prevSize = getItemCount();
        getData().removeAll(items);
        notifyItemRangeRemoved(prevSize, Math.abs(getData().size() - prevSize));
    }

    public void swapItem(@NonNull M item) {
        int index = getItemPosition(item);
        swapItem(item, index);
    }

    public void swapItem(@NonNull M item, int position) {
        if (position != -1) {
            getData().set(position, item);
            notifyItemChanged(position);
        }
    }

    public void subList(int fromPosition, int toPosition) {
        if (getData().isEmpty()) return;
        getData().subList(fromPosition, toPosition).clear();
        notifyItemRangeRemoved(fromPosition, toPosition);
    }

    public void clear() {
        mProgressAdded = false;
        getData().clear();
        notifyDataSetChanged();
    }

    public boolean isEmpty() {
        return getData().isEmpty();
    }

    public void setEnableAnimation(boolean enableAnimation) {
        this.mEnableAnimation = enableAnimation;
        notifyDataSetChanged();
    }

    public boolean isEnableAnimation() {
        return mEnableAnimation;
    }

    @Nullable
    public P getListener() {
        return mListener;
    }

    public void setListener(@Nullable P listener) {
        this.mListener = listener;
        notifyDataSetChanged();
    }


    public void setGuideListener(GuideListener guideListener) {
        this.mGuideListener = guideListener;
    }

    public int getRowWidth() {
        return mRowWidth;
    }

    public void setRowWidth(int rowWidth) {
        if (this.mRowWidth == 0) {
            this.mRowWidth = rowWidth;
            notifyDataSetChanged();
        }
    }

    private boolean isShowedGuide() {
        return mShowedGuide;
    }


    @Override
    public void onViewDetachedFromWindow(VH holder) {
        holder.onViewIsDetaching();
        super.onViewDetachedFromWindow(holder);
    }

    public void addProgress() {
        if (!mProgressAdded && !isEmpty()) {
            addItem(null);
            mProgressAdded = true;
        }
    }

    public boolean isProgressadde() {
        return mProgressAdded;
    }

    private void removeProgress() {
        if (!isEmpty()) {
            M m = getItem(getItemCount() - 1);
            if (m == null) {
                removeItem(getItemCount() - 1);
            }
            mProgressAdded = false;
        }
    }

    private void addSpanLookup(ViewGroup parent) {
        if (parent instanceof RecyclerView) {
            if (((RecyclerView) parent).getLayoutManager() instanceof GridLayoutManager) {
                GridLayoutManager gridLayoutManager = (GridLayoutManager) (((RecyclerView) parent).getLayoutManager());
                gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        return getItemViewType(position) == PROGRESS_TYPE ? gridLayoutManager.getSpanCount() : 1;
                    }
                });
            }
        }
    }

    public interface GuideListener<M> {
        void onShowGuide(@NonNull View itemView, @NonNull M model);
    }
}
