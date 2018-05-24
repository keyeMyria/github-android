package com.sxq.github.ui.adapter.view_holder;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;

import com.sxq.github.R;
import com.sxq.github.ui.widgets.FontTextView;
import com.sxq.github.ui.widgets.ForegroundImageView;
import com.sxq.github.ui.widgets.adapter.BaseRecyclerAdapter;
import com.sxq.github.ui.widgets.recyclerview.view_holder.BaseViewHolder;

import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import github.repos.GetCurrentLevelTreeViewQuery;

public class ReposFilesViewHolder extends BaseViewHolder<GetCurrentLevelTreeViewQuery.Entry> {

    @BindView(R.id.contentTypeImage)
    ForegroundImageView mContentTypeImage;
    @BindView(R.id.title)
    FontTextView mTitle;

    @BindDrawable(R.drawable.ic_file_document)
    Drawable mIcFileDocument;
    @BindDrawable(R.drawable.ic_folder)
    Drawable mIcFolder;
    @BindDrawable(R.drawable.ic_submodule)
    Drawable mIcSubmodule;

    @BindString(R.string.file)
    String mFile;

    @BindString(R.string.tree_type_blob)
    String mBlobType;
    @BindString(R.string.tree_type_file)
    String mFileType;
    @BindString(R.string.tree_type_dir)
    String mDirType;
    @BindString(R.string.tree_type_tree)
    String mTreeType;
    @BindString(R.string.tree_type_symlink)
    String mSymlinkType;


    public static ReposFilesViewHolder newInstance(@NonNull ViewGroup parent, BaseRecyclerAdapter adapter) {
        return new ReposFilesViewHolder(getView(parent, R.layout.repo_files_row_item), adapter);
    }

    private ReposFilesViewHolder(View itemView, BaseRecyclerAdapter adapter) {
        super(itemView,adapter);
    }

    @Override
    public void bind(@NonNull GetCurrentLevelTreeViewQuery.Entry entry) {
        mTitle.setText(entry.name());
        mContentTypeImage.setContentDescription(String.format("%s %s", entry.name(), mFile));
        if (entry.type().equals(mBlobType)) {
            mContentTypeImage.setImageDrawable(mIcFileDocument);
        } else if (entry.type().equals(mFileType)) {
            mContentTypeImage.setImageDrawable(mIcFileDocument);
        } else if (entry.type().equals(mDirType)) {
            mContentTypeImage.setImageDrawable(mIcFolder);
        } else if (entry.type().equals(mTreeType)) {
            mContentTypeImage.setImageDrawable(mIcFolder);
        } else if (entry.type().equals(mSymlinkType)) {
            mContentTypeImage.setImageDrawable(mIcSubmodule);
        }
    }
}
