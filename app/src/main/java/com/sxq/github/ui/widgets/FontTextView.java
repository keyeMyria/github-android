package com.sxq.github.ui.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.Gravity;

import com.sxq.github.R;
import com.sxq.github.utils.TypeFaceHelper;
import com.sxq.github.utils.ViewHelper;

/**
 * Uniform font TextView
 *
 * <p>
 * <b>FontView attrs</b>
 * <p>
 * See {@link com.sxq.github.R.styleable#FontTextView FontTextView Attributes}
 *
 * @attr {@link com.sxq.github.R.styleable#FontTextView_drawableColor}
 */
public class FontTextView extends AppCompatTextView {

    private int mTintColor = -1;
    private boolean mSelected;

    public FontTextView(Context context) {
        this(context, null);
    }

    public FontTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FontTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        this.mSelected = selected;
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState savedState = new SavedState(superState);
        savedState.setTintColor(mTintColor);
        savedState.setSelected(mSelected);
        return savedState;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        mTintColor = savedState.getTintColor();
        mSelected = savedState.isSelected();
        requestLayout();
    }

    public void tintDrawables(@ColorInt int color) {
        if (color != -1) {
            this.mTintColor = color;
            Drawable[] drawables = getCompoundDrawablesRelative();
            for (Drawable drawable : drawables) {
                if (drawable == null) continue;
                ViewHelper.tintDrawable(drawable, color);
            }
        }
    }

    public void setEventsIcon(@DrawableRes int drawableRes) {
        Drawable drawable = ContextCompat.getDrawable(getContext(), drawableRes);
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        drawable.setBounds(0, 0, width / 2, height / 2);
        ScaleDrawable sd = new ScaleDrawable(drawable, Gravity.CENTER, 0.6f, 0.6f);
        sd.setLevel(8000);
        ViewHelper.tintDrawable(drawable, ViewHelper.getTertiaryTextColor(getContext()));
        setCompoundDrawablesWithIntrinsicBounds(sd, null, null, null);
    }

    private void init(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        if (attributeSet != null) {
            TypedArray tp = context.obtainStyledAttributes(attributeSet, R.styleable.FontTextView);
            try {
                int color = tp.getColor(R.styleable.FontTextView_drawableColor, -1);
                tintDrawables(color);
            } finally {
                tp.recycle();
            }
            if (isInEditMode()) return;
            setFreezesText(true);
            TypeFaceHelper.applyTypeface(this);
        }
    }

    /**
     * Save view state
     */
    public static class SavedState extends BaseSavedState {
        private int mTintColor;
        private boolean mSelected;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            mTintColor = in.readInt();
            mSelected = in.readInt() == 1;
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(mTintColor);
            out.writeInt(mSelected ? 1 : 0);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Parcelable.Creator<SavedState> CREATOR
                = new Parcelable.Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };

        public int getTintColor() {
            return mTintColor;
        }

        public void setTintColor(int tintColor) {
            mTintColor = tintColor;
        }

        public boolean isSelected() {
            return mSelected;
        }

        public void setSelected(boolean selected) {
            mSelected = selected;
        }
    }
}