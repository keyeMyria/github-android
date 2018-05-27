package com.sxq.github.ui.adapter;

import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.sxq.github.ui.adapter.view_holder.ProfileFollowerViewHolder;
import com.sxq.github.ui.widgets.adapter.BaseRecyclerAdapter;
import com.sxq.github.ui.widgets.recyclerview.view_holder.BaseViewHolder;

import java.util.List;

import github.profile.GetFollowerQuery;

public class ProfileFollowerAdapter extends BaseRecyclerAdapter<
        GetFollowerQuery.Node,
        ProfileFollowerViewHolder,
        BaseViewHolder.OnItemClickListener<GetFollowerQuery.Node>> {

    public ProfileFollowerAdapter(@NonNull List<GetFollowerQuery.Node> data) {
        super(data);
    }

    @Override
    protected ProfileFollowerViewHolder viewHolder(ViewGroup parent, int viewType) {
        return ProfileFollowerViewHolder.newInstance(parent, this);
    }

    @Override
    protected void onBindView(ProfileFollowerViewHolder holder, int position) {
        holder.bind(getData().get(position));
    }
}
