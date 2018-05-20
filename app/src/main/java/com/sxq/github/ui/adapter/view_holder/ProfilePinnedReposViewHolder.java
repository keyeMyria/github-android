package com.sxq.github.ui.adapter.view_holder;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.sxq.github.R;
import com.sxq.github.ui.widgets.FontTextView;
import com.sxq.github.ui.widgets.adapter.BaseRecyclerAdapter;
import com.sxq.github.ui.widgets.recyclerview.view_holder.BaseViewHolder;
import com.sxq.github.utils.InputHelper;

import java.text.NumberFormat;

import butterknife.BindView;
import github.profile.GetPinnedReposQuery;

public class ProfilePinnedReposViewHolder extends BaseViewHolder<GetPinnedReposQuery.Node> {

    @BindView(R.id.title)
    FontTextView mTitle;

    @BindView(R.id.issues)
    FontTextView mIssues;

    @BindView(R.id.pullRequests)
    FontTextView mPullRequests;

    @BindView(R.id.language)
    FontTextView mLanguage;

    @BindView(R.id.stars)
    FontTextView mStars;

    @BindView(R.id.forks)
    FontTextView mForks;

    @NonNull
    private NumberFormat mNumberFormat;

    public static ProfilePinnedReposViewHolder newInstance(ViewGroup parent, BaseRecyclerAdapter adapter, NumberFormat numberFormat) {
        return new ProfilePinnedReposViewHolder(getView(parent, R.layout.profile_pinned_repo_row_item), adapter, numberFormat);
    }

    public ProfilePinnedReposViewHolder(@NonNull View itemView, @Nullable BaseRecyclerAdapter adapter, NumberFormat numberFormat) {
        super(itemView, adapter);
        this.mNumberFormat = numberFormat;
    }

    @Override
    public void bind(@NonNull GetPinnedReposQuery.Node node) {
        mTitle.setText(node.name());
        mIssues.setText(mNumberFormat.format(node.issues().totalCount()));
        mPullRequests.setText(mNumberFormat.format(node.pullRequests().totalCount()));
        mForks.setText(mNumberFormat.format(node.forks().totalCount()));
        mStars.setText(mNumberFormat.format(node.stargazers().totalCount()));
        if (!InputHelper.isEmpty(node.primaryLanguage())) {
            if (!InputHelper.isEmpty(node.primaryLanguage().name())) {
                mLanguage.setText(node.primaryLanguage().name());
            }
            if (!InputHelper.isEmpty(node.primaryLanguage().color())) {
                if (node.primaryLanguage().color().startsWith("#")) {
                    mLanguage.tintDrawables(Color.parseColor(node.primaryLanguage().color()));
                } else {
                    mLanguage.tintDrawables(Color.parseColor("#" + node.primaryLanguage().color()));
                }
            }
        }
    }
}
