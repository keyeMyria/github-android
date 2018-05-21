package com.sxq.github.ui.adapter.view_holder;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.sxq.github.R;
import com.sxq.github.ui.widgets.AvatarLayout;
import com.sxq.github.ui.widgets.FontTextView;
import com.sxq.github.ui.widgets.adapter.BaseRecyclerAdapter;
import com.sxq.github.ui.widgets.recyclerview.view_holder.BaseViewHolder;

import butterknife.BindView;
import github.profile.GetOrganizationsQuery;

public class ProfileOrganizationsViewHolder extends BaseViewHolder<GetOrganizationsQuery.Edge> {


    @BindView(R.id.avatarLayout)
    AvatarLayout mAvatarLayout;

    @BindView(R.id.name)
    FontTextView mName;

    public static ProfileOrganizationsViewHolder newInstance(@NonNull ViewGroup parent, @Nullable BaseRecyclerAdapter adapter) {
        return new ProfileOrganizationsViewHolder(getView(parent, R.layout.profile_org_row_item), adapter);

    }

    private ProfileOrganizationsViewHolder(@NonNull View itemView, @Nullable BaseRecyclerAdapter adapter) {
        super(itemView, adapter);
    }

    @Override
    public void bind(@NonNull GetOrganizationsQuery.Edge edge) {
        GetOrganizationsQuery.Node node = edge.node();
        mAvatarLayout.setUrl(node.avatarUrl().toString(), node.login());
        mName.setText(node.name());
    }
}
