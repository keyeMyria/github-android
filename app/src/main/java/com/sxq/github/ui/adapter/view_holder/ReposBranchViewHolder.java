package com.sxq.github.ui.adapter.view_holder;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.sxq.github.R;
import com.sxq.github.ui.widgets.FontTextView;
import com.sxq.github.ui.widgets.adapter.BaseRecyclerAdapter;
import com.sxq.github.ui.widgets.recyclerview.view_holder.BaseViewHolder;

import butterknife.BindView;
import github.repos.GetBranchesQuery;

public class ReposBranchViewHolder extends BaseViewHolder<GetBranchesQuery.Node> {


    @BindView(android.R.id.text1)
    FontTextView mText1;

    public static ReposBranchViewHolder newInstance(@NonNull ViewGroup parent, @NonNull BaseRecyclerAdapter adapter) {
        return new ReposBranchViewHolder(getView(parent, R.layout.branches_row_item), adapter);
    }

    private ReposBranchViewHolder(@NonNull View itemView, @Nullable BaseRecyclerAdapter adapter) {
        super(itemView, adapter);
    }

    @Override
    public void bind(@NonNull GetBranchesQuery.Node node) {
        mText1.setText(node.name());
    }
}
