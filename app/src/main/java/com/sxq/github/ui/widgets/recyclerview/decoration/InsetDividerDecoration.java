package com.sxq.github.ui.widgets.recyclerview.decoration;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sxq.github.ui.widgets.recyclerview.view_holder.ProgressBarViewHolder;

public class InsetDividerDecoration extends RecyclerView.ItemDecoration {
    @NonNull
    private final Paint mPaint;
    private final int mInset;
    private final int mHeight;
    private final Class mToDivide;

    public InsetDividerDecoration(int divider, int leftInset, @ColorInt int dividerColor) {
        this(divider, leftInset, dividerColor, null);
    }

    public InsetDividerDecoration(int divider, int leftInset, @ColorInt int dividerColor, @Nullable Class toDivide) {
        this.mInset = leftInset;
        this.mHeight = divider;
        this.mPaint = new Paint();
        this.mPaint.setColor(dividerColor);
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setStrokeWidth(divider);
        this.mToDivide = toDivide;
    }

    @Override
    public void onDrawOver(@NonNull Canvas canvas, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int childCount = parent.getChildCount();
        if (childCount < 2) return;
        RecyclerView.LayoutManager lm = parent.getLayoutManager();
        float[] lines = new float[childCount * 4];
        boolean hasDividers = false;
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.ViewHolder viewHolder = parent.getChildViewHolder(child);
            if (!(viewHolder instanceof ProgressBarViewHolder)) {
                boolean canDivide = mToDivide == null || viewHolder.getClass() == mToDivide;
                if (canDivide) {
                    int position = parent.getChildAdapterPosition(child);
                    if (child.isActivated() || (i + 1 < childCount && parent.getChildAt(i + 1).isActivated())) {
                        continue;
                    }
                    if (position != (state.getItemCount() - 1)) {
                        lines[i * 4] = mInset == 0 ? mInset : mInset + lm.getDecoratedLeft(child);
                        lines[(i * 4) + 2] = lm.getDecoratedRight(child);
                        int y = lm.getDecoratedBottom(child) + (int) child.getTranslationY() - mHeight;
                        lines[(i * 4) + 1] = y;
                        lines[(i * 4) + 3] = y;
                        hasDividers = true;
                    }
                }
            }
        }
        if (hasDividers) {
            canvas.drawLines(lines, mPaint);
        }
    }
}
