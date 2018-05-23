package com.sxq.github.ui.adapter;

import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.sxq.github.ui.adapter.view_holder.ReposContributorsViewHolder;
import com.sxq.github.ui.widgets.adapter.BaseRecyclerAdapter;
import com.sxq.github.ui.widgets.recyclerview.view_holder.BaseViewHolder;

import java.util.List;

import github.repos.GetContributorsQuery;


public class ReposContributorsAdapter extends BaseRecyclerAdapter<
        GetContributorsQuery.Node,
        ReposContributorsViewHolder,
        BaseViewHolder.OnItemClickListener<GetContributorsQuery.Node>> {

    public ReposContributorsAdapter(@NonNull List<GetContributorsQuery.Node> data) {
        super(data);
    }

    @Override
    protected ReposContributorsViewHolder viewHolder(ViewGroup parent, int viewType) {
        return ReposContributorsViewHolder.newInstance(parent, this);
    }

    @Override
    protected void onBindView(ReposContributorsViewHolder holder, int position) {
        holder.bind(getData().get(position));
    }
}
