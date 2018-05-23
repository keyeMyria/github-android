package com.sxq.github.ui.adapter;

import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.sxq.github.ui.adapter.view_holder.ProfileReposViewHolder;
import com.sxq.github.ui.widgets.adapter.BaseRecyclerAdapter;
import com.sxq.github.ui.widgets.recyclerview.view_holder.BaseViewHolder;

import java.text.NumberFormat;
import java.util.List;

import github.profile.GetOwnedReposQuery;

public class ProfileReposAdapter extends BaseRecyclerAdapter<
        GetOwnedReposQuery.Node,
        ProfileReposViewHolder,
        BaseViewHolder.OnItemClickListener<GetOwnedReposQuery.Node>> {

    private NumberFormat mNumberFormat = NumberFormat.getNumberInstance();

    public ProfileReposAdapter(@NonNull List<GetOwnedReposQuery.Node> data) {
        super(data);
    }

    @Override
    protected ProfileReposViewHolder viewHolder(ViewGroup parent, int viewType) {
        return ProfileReposViewHolder.newInstance(parent, this, mNumberFormat);
    }

    @Override
    protected void onBindView(ProfileReposViewHolder holder, int position) {
        holder.bind(getData().get(position));
    }
}
