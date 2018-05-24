package com.sxq.github.ui.adapter.view_holder;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.sxq.github.R;
import com.sxq.github.ui.widgets.AvatarLayout;
import com.sxq.github.ui.widgets.FontTextView;
import com.sxq.github.ui.widgets.ForegroundRelativeLayout;
import com.sxq.github.ui.widgets.SpannableBuilder;
import com.sxq.github.ui.widgets.adapter.BaseRecyclerAdapter;
import com.sxq.github.ui.widgets.recyclerview.view_holder.BaseViewHolder;
import com.sxq.github.utils.ParseDateFormat;
import com.sxq.github.utils.common.Constants;

import butterknife.BindView;
import github.user.GetProfileFeedsQuery;

public class ProfileFeedsViewHolder extends BaseViewHolder<GetProfileFeedsQuery.Node> {


    @BindView(R.id.avatarLayout)
    AvatarLayout mAvatarLayout;
    @BindView(R.id.title)
    FontTextView mTitle;
    @BindView(R.id.description)
    FontTextView mDescription;
    @BindView(R.id.date)
    FontTextView mDate;


    public static ProfileFeedsViewHolder newInstance(@NonNull ViewGroup parent, BaseRecyclerAdapter adapter) {
        return new ProfileFeedsViewHolder(getView(parent, R.layout.feeds_row_item), adapter);
    }

    private ProfileFeedsViewHolder(View itemView, BaseRecyclerAdapter adapter) {
        super(itemView, adapter);
    }

    @Override
    public void bind(@NonNull GetProfileFeedsQuery.Node node) {
        if (node.refs().edges().isEmpty()) {
            /**
             * try to fix commit list empty exception
             *
             * todo: don't pass null commit model into adapter
             */
            return;
        }
        GetProfileFeedsQuery.Node1 commitNode = node.refs().edges().get(0).node();
        GetProfileFeedsQuery.Author author = commitNode.target().asCommit().author();
        String avatarUrl;
        String authorName;
        if (author == null) {
            avatarUrl = Constants.GITHUB_PICTURE_LOADING_URL;
            authorName = "";
        } else if (author.user() == null) {
            avatarUrl = Constants.GITHUB_PICTURE_LOADING_URL;
            authorName = author.name();
        } else {
            avatarUrl = commitNode.target().asCommit().author().user().avatarUrl().toString();
            authorName = commitNode.target().asCommit().author().user().login();
        }
        mAvatarLayout.setUrl(avatarUrl, authorName);
        SpannableBuilder titleSpannableBuilder = SpannableBuilder.builder();
        titleSpannableBuilder
                .append(authorName)
                .append(" ")
                .bold("commit")
                .append(" ")
                .bold("to")
                .append(" ")
                .append(commitNode.name())
                .append(" ")
                .bold("at")
                .append(" ")
                .append(commitNode.target().asCommit().repository().nameWithOwner());
        mTitle.setText(titleSpannableBuilder);
        SpannableBuilder descriptionSpannableBuilder = SpannableBuilder.builder();
        String sha = commitNode.target().asCommit().oid().toString();
        sha = sha.length() > 7 ? sha.substring(0, 7) : sha;
        descriptionSpannableBuilder.url(sha)
                .append(" ")
                .append(commitNode.target().asCommit().message());
        mDescription.setText(descriptionSpannableBuilder);
        String date = ParseDateFormat.parseTimeAgo(ParseDateFormat.getGithubDateFrom(commitNode.target().asCommit().committedDate().toString())).toString();
        mDate.setText(date);
    }
}

