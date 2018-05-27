package com.sxq.github.ui.adapter.view_holder;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.sxq.github.R;
import com.sxq.github.ui.widgets.FontTextView;
import com.sxq.github.ui.widgets.ForegroundImageView;
import com.sxq.github.ui.widgets.SpannableBuilder;
import com.sxq.github.ui.widgets.adapter.BaseRecyclerAdapter;
import com.sxq.github.ui.widgets.recyclerview.view_holder.BaseViewHolder;
import com.sxq.github.utils.InputHelper;
import com.sxq.github.utils.ParseDateFormat;

import butterknife.BindString;
import butterknife.BindView;
import github.repos.GetReleasesQuery;

public class ReposReleaseViewHolder extends BaseViewHolder<GetReleasesQuery.Node> {


    @BindView(R.id.title)
    FontTextView mTitle;
    @BindView(R.id.details)
    FontTextView mDetails;
    @BindView(R.id.download)
    ForegroundImageView mDownload;
    @BindString(R.string.released)
    String mReleased;
    @BindString(R.string.drafted)
    String mDrafted;

    public static ReposReleaseViewHolder newInstance(@NonNull ViewGroup parent, BaseRecyclerAdapter adapter) {
        return new ReposReleaseViewHolder(getView(parent, R.layout.releases_row_item), adapter);
    }

    private ReposReleaseViewHolder(View itemView, BaseRecyclerAdapter adapter) {
        super(itemView, adapter);
//        mDownload.setOnClickListener(this);
//        mDownload.setOnLongClickListener(this);
    }

    @Override
    public void bind(@NonNull GetReleasesQuery.Node node) {
        mTitle.setText(SpannableBuilder.
                builder().
                bold(!InputHelper.isEmpty(node.name()) ? node.name() : node.name()));
        if (node.author() != null) {
            String date = ParseDateFormat.parseTimeAgo(ParseDateFormat.getGithubDateFrom(node.createdAt().toString())).toString();
            mDetails.setText(SpannableBuilder.builder()
                    .append(node.author().login())
                    .append(" ")
                    .append(node.isDraft() ? mDrafted : mReleased)
                    .append(" ")
                    .append(date));
        } else {
            mDetails.setVisibility(View.GONE);
        }
    }
}
