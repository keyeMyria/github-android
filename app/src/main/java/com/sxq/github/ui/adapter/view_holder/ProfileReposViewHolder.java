package com.sxq.github.ui.adapter.view_holder;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;

import com.sxq.github.R;
import com.sxq.github.provider.colors.ColorsProvider;
import com.sxq.github.ui.widgets.FontTextView;
import com.sxq.github.ui.widgets.LabelSpan;
import com.sxq.github.ui.widgets.SpannableBuilder;
import com.sxq.github.ui.widgets.adapter.BaseRecyclerAdapter;
import com.sxq.github.ui.widgets.recyclerview.view_holder.BaseViewHolder;
import com.sxq.github.utils.InputHelper;
import com.sxq.github.utils.ParseDateFormat;

import java.text.NumberFormat;

import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import github.profile.GetOwnedReposQuery;

public class ProfileReposViewHolder extends BaseViewHolder<GetOwnedReposQuery.Node> {


    @BindView(R.id.title)
    FontTextView mTitle;
    @BindView(R.id.stars)
    FontTextView mStars;
    @BindView(R.id.forks)
    FontTextView mForks;
    @BindView(R.id.date)
    FontTextView mDate;
    @BindView(R.id.size)
    FontTextView mSize;
    @BindView(R.id.language)
    FontTextView mLanguage;
    @BindString(R.string.forked) String forked;
    @BindString(R.string.private_repo) String privateRepo;
    @BindColor(R.color.material_indigo_700) int forkColor;
    @BindColor(R.color.material_grey_700) int privateColor;
    
    
    @NonNull
    private NumberFormat mNumberFormat;

    public static ProfileReposViewHolder newInstance(ViewGroup parent, BaseRecyclerAdapter adapter, NumberFormat mNumberFormat) {
        return new ProfileReposViewHolder(getView(parent, R.layout.repos_row_no_image_item), adapter, mNumberFormat);
    }

    private ProfileReposViewHolder(@NonNull View itemView, @Nullable BaseRecyclerAdapter adapter, NumberFormat mNumberFormat) {
        super(itemView, adapter);
        this.mNumberFormat = mNumberFormat;
    }

    @Override
    public void bind(@NonNull GetOwnedReposQuery.Node node) {
        if (node.isFork() && !node.viewerHasStarred()) {
            mTitle.setText(SpannableBuilder.builder()
                    .append(" " + node.isFork() + " ", new LabelSpan(forkColor))
                    .append(" ")
                    .append(node.name(), new LabelSpan(Color.TRANSPARENT)));
        } else if (node.isPrivate()) {
            mTitle.setText(SpannableBuilder.builder()
                    .append(" " + privateRepo + " ", new LabelSpan(privateColor))
                    .append(" ")
                    .append(node.name(), new LabelSpan(Color.TRANSPARENT)));
        } else {
            mTitle.setText(!node.isFork() ? node.name(): node.nameWithOwner());
        }
        long repoSize = node.diskUsage()> 0 ? (node.diskUsage() * 1000) : node.diskUsage();
        mSize.setText(Formatter.formatFileSize(mSize.getContext(), repoSize));
        mStars.setText(mNumberFormat.format(node.stargazers().totalCount()));
        mForks.setText(mNumberFormat.format(node.forks().totalCount()));
        mDate.setText(ParseDateFormat.parseTimeAgo(ParseDateFormat.getGithubDateFrom(node.updatedAt().toString())));
        if (!InputHelper.isEmpty(node.languages().nodes()) && !InputHelper.isEmpty(node.languages().nodes().get(0).name())) {
            mLanguage.setText(node.languages().nodes().get(0).name());
            mLanguage.setTextColor(ColorsProvider.getColorAsColor(node.languages().nodes().get(0).color(), mLanguage.getContext()));
            mLanguage.setVisibility(View.VISIBLE);
        } else {
            mLanguage.setTextColor(Color.BLACK);
            mLanguage.setVisibility(View.GONE);
            mLanguage.setText("");
        }
    }


}
