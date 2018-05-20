package com.sxq.github.ui.adapter;

import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.sxq.github.ui.adapter.view_holder.ProfilePinnedReposViewHolder;
import com.sxq.github.ui.widgets.adapter.BaseRecyclerAdapter;
import com.sxq.github.ui.widgets.recyclerview.view_holder.BaseViewHolder;

import java.text.NumberFormat;
import java.util.List;

import github.profile.GetPinnedReposQuery;

public class ProfilePinnedReposAdapter extends BaseRecyclerAdapter<
        GetPinnedReposQuery.Node,
        ProfilePinnedReposViewHolder,
        BaseViewHolder.OnItemClickListener<GetPinnedReposQuery.Node>> {

    private NumberFormat mNumberFormat = NumberFormat.getNumberInstance();

    public ProfilePinnedReposAdapter(@NonNull List<GetPinnedReposQuery.Node> data) {
        super(data);
    }

    @Override
    protected ProfilePinnedReposViewHolder viewHolder(ViewGroup parent, int viewType) {
        return ProfilePinnedReposViewHolder.newInstance(parent, this, mNumberFormat);
    }

    @Override
    protected void onBindView(ProfilePinnedReposViewHolder holder, int position) {
        holder.bind(getData().get(position));
    }
}
