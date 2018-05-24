package com.sxq.github.ui.adapter;

import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.sxq.github.ui.adapter.view_holder.ProfileStarredViewHolder;
import com.sxq.github.ui.adapter.view_holder.ProfileStarredViewHolder;
import com.sxq.github.ui.widgets.adapter.BaseRecyclerAdapter;
import com.sxq.github.ui.widgets.recyclerview.view_holder.BaseViewHolder;

import java.text.NumberFormat;
import java.util.List;

import github.profile.GetStarredReposQuery;

public class ProfileStarredAdapter extends BaseRecyclerAdapter<
        GetStarredReposQuery.Node,
        ProfileStarredViewHolder,
        BaseViewHolder.OnItemClickListener<GetStarredReposQuery.Node>> {

    private NumberFormat mNumberFormat = NumberFormat.getNumberInstance();

    public ProfileStarredAdapter(@NonNull List<GetStarredReposQuery.Node> data) {
        super(data);
    }

    @Override
    protected ProfileStarredViewHolder viewHolder(ViewGroup parent, int viewType) {
        return ProfileStarredViewHolder.newInstance(parent, this, mNumberFormat);
    }

    @Override
    protected void onBindView(ProfileStarredViewHolder holder, int position) {
        holder.bind(getData().get(position));
    }
}
