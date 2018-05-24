package com.sxq.github.ui.adapter;

import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.sxq.github.ui.adapter.view_holder.ReposFilesViewHolder;
import com.sxq.github.ui.widgets.adapter.BaseRecyclerAdapter;
import com.sxq.github.ui.widgets.recyclerview.view_holder.BaseViewHolder;

import java.util.List;

import github.repos.GetCurrentLevelTreeViewQuery;


public class ReposFilesAdapter extends BaseRecyclerAdapter<
        GetCurrentLevelTreeViewQuery.Entry,
        ReposFilesViewHolder,
        BaseViewHolder.OnItemClickListener<GetCurrentLevelTreeViewQuery.Entry>> {

    public ReposFilesAdapter(@NonNull List<GetCurrentLevelTreeViewQuery.Entry> data) {
        super(data);
    }

    @Override
    protected ReposFilesViewHolder viewHolder(ViewGroup parent, int viewType) {
        return ReposFilesViewHolder.newInstance(parent, this);
    }

    @Override
    protected void onBindView(ReposFilesViewHolder holder, int position) {
        holder.bind(getData().get(position));
    }
}
