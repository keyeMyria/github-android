package com.sxq.github.ui.adapter;

import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.sxq.github.ui.adapter.view_holder.ReposReleaseViewHolder;
import com.sxq.github.ui.widgets.adapter.BaseRecyclerAdapter;
import com.sxq.github.ui.widgets.recyclerview.view_holder.BaseViewHolder;

import java.util.List;

import github.repos.GetReleasesQuery;


public class ReposReleaseAdapter extends BaseRecyclerAdapter<
        GetReleasesQuery.Node,
        ReposReleaseViewHolder,
        BaseViewHolder.OnItemClickListener<GetReleasesQuery.Node>> {

    public ReposReleaseAdapter(@NonNull List<GetReleasesQuery.Node> data) {
        super(data);
    }

    @Override
    protected ReposReleaseViewHolder viewHolder(ViewGroup parent, int viewType) {
        return ReposReleaseViewHolder.newInstance(parent, this);
    }

    @Override
    protected void onBindView(ReposReleaseViewHolder holder, int position) {
        holder.bind(getData().get(position));
    }
}
