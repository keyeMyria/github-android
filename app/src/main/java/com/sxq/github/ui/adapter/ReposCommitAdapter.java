package com.sxq.github.ui.adapter;

import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.sxq.github.ui.adapter.view_holder.ReposCommitViewHolder;
import com.sxq.github.ui.widgets.adapter.BaseRecyclerAdapter;
import com.sxq.github.ui.widgets.recyclerview.view_holder.BaseViewHolder;

import java.util.List;

import github.repos.GetCommitsQuery;


public class ReposCommitAdapter extends BaseRecyclerAdapter<
        GetCommitsQuery.Edge,
        ReposCommitViewHolder,
        BaseViewHolder.OnItemClickListener<GetCommitsQuery.Edge>> {

    public ReposCommitAdapter(@NonNull List<GetCommitsQuery.Edge> data) {
        super(data);
    }

    @Override
    protected ReposCommitViewHolder viewHolder(ViewGroup parent, int viewType) {
        return ReposCommitViewHolder.newInstance(parent, this);
    }

    @Override
    protected void onBindView(ReposCommitViewHolder holder, int position) {
        holder.bind(getData().get(position));
    }
}
