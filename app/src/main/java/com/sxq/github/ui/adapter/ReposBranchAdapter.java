package com.sxq.github.ui.adapter;

import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.sxq.github.ui.adapter.view_holder.ReposBranchViewHolder;
import com.sxq.github.ui.widgets.adapter.BaseRecyclerAdapter;
import com.sxq.github.ui.widgets.recyclerview.view_holder.BaseViewHolder;

import java.util.List;

import github.repos.GetBranchesQuery;


public class ReposBranchAdapter extends BaseRecyclerAdapter<
        GetBranchesQuery.Node,
        ReposBranchViewHolder,
        BaseViewHolder.OnItemClickListener<GetBranchesQuery.Node>> {

    public ReposBranchAdapter(@NonNull List<GetBranchesQuery.Node> data) {
        super(data);
    }

    @Override
    protected ReposBranchViewHolder viewHolder(ViewGroup parent, int viewType) {
        return ReposBranchViewHolder.newInstance(parent, this);
    }

    @Override
    protected void onBindView(ReposBranchViewHolder holder, int position) {
        holder.bind(getData().get(position));
    }
}
