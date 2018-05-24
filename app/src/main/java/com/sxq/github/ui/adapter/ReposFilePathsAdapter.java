package com.sxq.github.ui.adapter;

import android.support.annotation.NonNull;
import android.view.ViewGroup;

import com.sxq.github.ui.adapter.view_holder.ReposFilePathsViewHolder;
import com.sxq.github.ui.widgets.adapter.BaseRecyclerAdapter;
import com.sxq.github.ui.widgets.recyclerview.view_holder.BaseViewHolder;

import java.util.List;


public class ReposFilePathsAdapter extends BaseRecyclerAdapter<
        String,
        ReposFilePathsViewHolder,
        BaseViewHolder.OnItemClickListener<String>> {

    public ReposFilePathsAdapter(@NonNull List<String> data) {
        super(data);
    }

    @Override
    protected ReposFilePathsViewHolder viewHolder(ViewGroup parent, int viewType) {
        return ReposFilePathsViewHolder.newInstance(parent, this);
    }

    @Override
    protected void onBindView(ReposFilePathsViewHolder holder, int position) {
        holder.bind(getData().get(position));
    }
}
