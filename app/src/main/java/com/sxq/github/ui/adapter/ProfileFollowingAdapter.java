package com.sxq.github.ui.adapter;

import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.sxq.github.ui.adapter.view_holder.ProfileFollowingViewHolder;
import com.sxq.github.ui.widgets.adapter.BaseRecyclerAdapter;
import com.sxq.github.ui.widgets.recyclerview.view_holder.BaseViewHolder;

import java.util.List;

import github.profile.GetFollowingQuery;

public class ProfileFollowingAdapter extends BaseRecyclerAdapter<
        GetFollowingQuery.Node,
        ProfileFollowingViewHolder,
        BaseViewHolder.OnItemClickListener<GetFollowingQuery.Node>> {

    public ProfileFollowingAdapter(@NonNull List<GetFollowingQuery.Node> data) {
        super(data);
    }

    @Override
    protected ProfileFollowingViewHolder viewHolder(ViewGroup parent, int viewType) {
        return ProfileFollowingViewHolder.newInstance(parent, this);
    }

    @Override
    protected void onBindView(ProfileFollowingViewHolder holder, int position) {
        holder.bind(getData().get(position));
    }
}
