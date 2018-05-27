package com.sxq.github.ui.adapter.view_holder;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.sxq.github.R;
import com.sxq.github.ui.widgets.AvatarLayout;
import com.sxq.github.ui.widgets.FontTextView;
import com.sxq.github.ui.widgets.adapter.BaseRecyclerAdapter;
import com.sxq.github.ui.widgets.recyclerview.view_holder.BaseViewHolder;

import butterknife.BindView;
import github.profile.GetFollowerQuery;

public class ProfileFollowerViewHolder extends BaseViewHolder<GetFollowerQuery.Node> {

    @BindView(R.id.avatarLayout)
    AvatarLayout mAvatarLayout;
    @BindView(R.id.title)
    FontTextView mTitle;
    @BindView(R.id.date)
    FontTextView mDate;

    public static ProfileFollowerViewHolder newInstance(@NonNull ViewGroup parent, BaseRecyclerAdapter adapter) {
        return new ProfileFollowerViewHolder(getView(parent, R.layout.users_small_row_item), adapter);
    }

    private ProfileFollowerViewHolder(View itemView, BaseRecyclerAdapter adapter) {
        super(itemView, adapter);
    }

    @Override
    public void bind(@NonNull GetFollowerQuery.Node node) {
        mAvatarLayout.setUrl(node.avatarUrl().toString(), node.login());
        mTitle.setText(node.login());
        mDate.setVisibility(View.GONE);
    }
}
