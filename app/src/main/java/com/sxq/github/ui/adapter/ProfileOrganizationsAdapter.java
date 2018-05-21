package com.sxq.github.ui.adapter;

import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.sxq.github.ui.adapter.view_holder.ProfileOrganizationsViewHolder;
import com.sxq.github.ui.widgets.adapter.BaseRecyclerAdapter;
import com.sxq.github.ui.widgets.recyclerview.view_holder.BaseViewHolder;

import java.util.List;

import github.profile.GetOrganizationsQuery;

public class ProfileOrganizationsAdapter extends BaseRecyclerAdapter<
        GetOrganizationsQuery.Edge,
        ProfileOrganizationsViewHolder,
        BaseViewHolder.OnItemClickListener<GetOrganizationsQuery.Edge>> {
    public ProfileOrganizationsAdapter(@NonNull List<GetOrganizationsQuery.Edge> data) {
        super(data);
    }

    @Override
    protected ProfileOrganizationsViewHolder viewHolder(ViewGroup parent, int viewType) {
        return ProfileOrganizationsViewHolder.newInstance(parent, this);
    }

    @Override
    protected void onBindView(ProfileOrganizationsViewHolder holder, int position) {
        holder.bind(getData().get(position));
    }
}
