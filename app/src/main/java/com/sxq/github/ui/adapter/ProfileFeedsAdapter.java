package com.sxq.github.ui.adapter;

import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.sxq.github.ui.adapter.view_holder.ProfileFeedsViewHolder;
import com.sxq.github.ui.widgets.adapter.BaseRecyclerAdapter;
import com.sxq.github.ui.widgets.recyclerview.view_holder.BaseViewHolder;

import java.util.List;

import github.user.GetProfileFeedsQuery;


public class ProfileFeedsAdapter extends BaseRecyclerAdapter<
        GetProfileFeedsQuery.Node,
        ProfileFeedsViewHolder,
        BaseViewHolder.OnItemClickListener<GetProfileFeedsQuery.Node>> {

    public ProfileFeedsAdapter(@NonNull List<GetProfileFeedsQuery.Node> data) {
        super(data);
    }

    @Override
    protected ProfileFeedsViewHolder viewHolder(ViewGroup parent, int viewType) {
        return ProfileFeedsViewHolder.newInstance(parent, this);
    }

    @Override
    protected void onBindView(ProfileFeedsViewHolder holder, int position) {
        holder.bind(getData().get(position));
    }
}
