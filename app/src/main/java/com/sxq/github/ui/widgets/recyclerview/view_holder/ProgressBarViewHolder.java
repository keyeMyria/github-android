package com.sxq.github.ui.widgets.recyclerview.view_holder;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.sxq.github.R;

public class ProgressBarViewHolder extends BaseViewHolder {

    private ProgressBarViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public static ProgressBarViewHolder newInstance(ViewGroup viewGroup) {
        return new ProgressBarViewHolder(getView(viewGroup, R.layout.progress_layout));
    }


    @Override
    public void bind(@NonNull Object o) {

    }
}
