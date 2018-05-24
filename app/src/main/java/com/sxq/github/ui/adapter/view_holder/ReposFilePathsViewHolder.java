package com.sxq.github.ui.adapter.view_holder;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.sxq.github.R;
import com.sxq.github.ui.widgets.FontTextView;
import com.sxq.github.ui.widgets.adapter.BaseRecyclerAdapter;
import com.sxq.github.ui.widgets.recyclerview.view_holder.BaseViewHolder;

import butterknife.BindView;

public class ReposFilePathsViewHolder extends BaseViewHolder<String> {


    @BindView(R.id.pathName)
    FontTextView mPathName;

    public static ReposFilePathsViewHolder newInstance(@NonNull ViewGroup parent, BaseRecyclerAdapter adapter) {
        return new ReposFilePathsViewHolder(getView(parent, R.layout.file_path_row_item), adapter);
    }

    private ReposFilePathsViewHolder(View itemView, BaseRecyclerAdapter adapter) {
        super(itemView, adapter);
    }

    @Override
    public void bind(@NonNull String node) {
        mPathName.setText(node);
    }
}

