package com.sxq.github.ui.widgets.recyclerview.view_holder;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sxq.github.ui.widgets.adapter.BaseRecyclerAdapter;


import butterknife.ButterKnife;
import timber.log.Timber;

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    public interface OnItemClickListener<T> {
        void onItemClick(int position, View v, T item);

        void onItemLongClick(int position, View v, T item);
    }

    @Nullable
    protected final BaseRecyclerAdapter mAdapter;

    public static View getView(@NonNull ViewGroup parent, @LayoutRes int layoutRes) {
        return LayoutInflater.from(parent.getContext()).inflate(layoutRes, parent, false);
    }

    /**
     * If you need to add {@link OnItemClickListener}, you need to pass {@link BaseRecyclerAdapter} to it.
     * @param itemView
     */
    public BaseViewHolder(View itemView) {
        this(itemView, null);
    }

    /**
     * If you need to add {@link OnItemClickListener} , you need to choose this constructor to pass {@link BaseRecyclerAdapter}.
     * @param itemView
     * @param adapter
     */
    public BaseViewHolder(@NonNull View itemView, @Nullable BaseRecyclerAdapter adapter) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.mAdapter = adapter;
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (mAdapter != null && mAdapter.getListener() != null) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION && position < mAdapter.getItemCount()) {
                mAdapter.getListener().onItemClick(position, view, mAdapter.getItem(position));
            }
        }
    }

    @Override
    public boolean onLongClick(View view) {
        if (mAdapter != null && mAdapter.getListener() != null) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION && position < mAdapter.getItemCount()) {
                mAdapter.getListener().onItemLongClick(position, view, mAdapter.getItem(position));
            }
        }
        return true;
    }

    public abstract void bind(@NonNull T t);

    public void onViewIsDetaching() {
    }
}
