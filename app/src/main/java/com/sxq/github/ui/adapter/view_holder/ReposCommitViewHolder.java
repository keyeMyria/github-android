package com.sxq.github.ui.adapter.view_holder;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.sxq.github.R;
import com.sxq.github.ui.widgets.AvatarLayout;
import com.sxq.github.ui.widgets.FontTextView;
import com.sxq.github.ui.widgets.SpannableBuilder;
import com.sxq.github.ui.widgets.adapter.BaseRecyclerAdapter;
import com.sxq.github.ui.widgets.recyclerview.view_holder.BaseViewHolder;
import com.sxq.github.utils.ParseDateFormat;


import butterknife.BindView;
import github.repos.GetCommitsQuery;

public class ReposCommitViewHolder extends BaseViewHolder<GetCommitsQuery.Edge> {


    @BindView(R.id.avatarLayout)
    AvatarLayout mAvatarLayout;
    @BindView(R.id.title)
    FontTextView mTitle;
    @BindView(R.id.details)
    FontTextView mDetails;
    @BindView(R.id.commentsNo)
    FontTextView mCommentsNo;

    public static ReposCommitViewHolder newInstance(@NonNull ViewGroup parent, BaseRecyclerAdapter adapter) {
        return new ReposCommitViewHolder(getView(parent, R.layout.issue_row_item), adapter);
    }

    private ReposCommitViewHolder(View itemView, BaseRecyclerAdapter adapter) {
        super(itemView, adapter);
    }

    @Override
    public void bind(@NonNull GetCommitsQuery.Edge edge) {
        GetCommitsQuery.Node node = edge.node();
        mTitle.setText(node.messageHeadline());
        String login = node.author().name();
        String date = ParseDateFormat.parseTimeAgo(ParseDateFormat.getGithubDateFrom(node.committedDate().toString())).toString();
        mDetails.setText(SpannableBuilder.builder()
                .bold(login)
                .append(" ")
                .append(date));
        mAvatarLayout.setUrl(node.author().avatarUrl().toString(), login);
        mCommentsNo.setVisibility(View.GONE);
    }
}
