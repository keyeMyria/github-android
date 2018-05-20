package com.sxq.github.ui.widgets;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;

import com.sxq.github.R;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Custom view that has state
 */
public class StateLayout extends NestedScrollView {

    private static final int STATE_SHOW_PROGRESS = 1;
    private static final int STATE_HIDE_PROGRESS = 2;
    private static final int STATE_HIDE_RELOAD = 3;
    private static final int STATE_SHOW_RELOAD = 4;
    private static final int HIDDEN = 5;
    private static final int SHOWN = 6;
    private static final int STATE_SHOW_EMPTY = 7;

    private OnClickListener mOnReloadListener;

    @BindView(R.id.empty_text)
    FontTextView mEmptyText;

    @BindView(R.id.reload)
    FontButton mReloadButton;

    private int mLayoutState = HIDDEN;
    private String mEmptyTextValue;
    private boolean mShowReload = true;

    @OnClick(R.id.reload)
    void onReload() {
        if (mOnReloadListener != null) {
            mOnReloadListener.onClick(mReloadButton);
        }
    }

    public StateLayout(Context context) {
        super(context);
    }

    public StateLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StateLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void showProgress() {
        mLayoutState = STATE_SHOW_PROGRESS;
        setVisibility(VISIBLE);
        mEmptyText.setVisibility(GONE);
        mReloadButton.setVisibility(GONE);
    }

    public void hideProgress() {
        mLayoutState = STATE_HIDE_PROGRESS;
        setVisibility(GONE);
        mEmptyText.setVisibility(VISIBLE);
        mReloadButton.setVisibility(VISIBLE);
    }


    public void hideReload() {
        mLayoutState = STATE_HIDE_RELOAD;
        mReloadButton.setVisibility(GONE);
        mEmptyText.setVisibility(GONE);
        setVisibility(GONE);
    }

    public void showReload(int adapterCount) {
        mShowReload = adapterCount == 0;
        showReload();
    }

    protected void showReload() {
        hideProgress();
        if (mShowReload) {
            mLayoutState = STATE_SHOW_RELOAD;
            mReloadButton.setVisibility(VISIBLE);
            mEmptyText.setVisibility(VISIBLE);
            setVisibility(VISIBLE);
        }
    }

    public void setEmptyText(@StringRes int resId) {
        setEmptyText(getResources().getString(resId));
    }

    public void setEmptyText(@NonNull String text) {
        this.mEmptyTextValue = text + "\n\n¯\\_(ツ)_/¯";
        mEmptyText.setText(this.mEmptyTextValue);
    }


    public void showEmptyState() {
        hideProgress();
        hideReload();
        setVisibility(VISIBLE);
        mEmptyText.setVisibility(VISIBLE);
        mLayoutState = STATE_SHOW_EMPTY;
    }

    public void setOnReloadListener(OnClickListener onClickListener) {
        this.mOnReloadListener = onClickListener;
    }


    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (visibility == GONE || visibility == INVISIBLE) {
            mLayoutState = HIDDEN;
        } else {
            mLayoutState = SHOWN;
        }
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        inflate(getContext(), R.layout.empty_layout, this);
        if (isInEditMode()) return;
        ButterKnife.bind(this);
        mEmptyText.setFreezesText(true);
    }

    @Override
    protected void onDetachedFromWindow() {
        mOnReloadListener = null;
        super.onDetachedFromWindow();
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState savedState = new SavedState(superState);
        savedState.setLayoutState(mLayoutState);
        savedState.setEmptyTextValue(mEmptyTextValue);
        savedState.setShowReload(mShowReload);
        return savedState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(state);
        mLayoutState = savedState.getLayoutState();
        mEmptyTextValue = savedState.getEmptyTextValue();
        mShowReload = savedState.isShowReload();
        onHandleLayoutState();
    }

    private void onHandleLayoutState() {
        setEmptyText(mEmptyTextValue);
        switch (mLayoutState) {
            case STATE_SHOW_PROGRESS:
                showProgress();
                break;
            case STATE_HIDE_PROGRESS:
                hideProgress();
                break;
            case STATE_HIDE_RELOAD:
                hideReload();
                break;
            case STATE_SHOW_RELOAD:
                showReload();
                break;
            case HIDDEN:
                setVisibility(GONE);
                break;
            case STATE_SHOW_EMPTY:
                showEmptyState();
                break;
            case SHOWN:
                setVisibility(VISIBLE);
                showReload();
                break;
        }
    }

    private static class SavedState extends BaseSavedState {
        private int mLayoutState;
        private String mEmptyTextValue;
        private boolean mShowReload;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            mLayoutState = in.readInt();
            mEmptyTextValue = in.readString();
            mShowReload = in.readInt() == 1;
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(mLayoutState);
            out.writeString(mEmptyTextValue);
            out.writeInt(mShowReload ? 1 : 0);

        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Parcelable.Creator<StateLayout.SavedState> CREATOR
                = new Parcelable.Creator<StateLayout.SavedState>() {
            public StateLayout.SavedState createFromParcel(Parcel in) {
                return new StateLayout.SavedState(in);
            }

            public StateLayout.SavedState[] newArray(int size) {
                return new StateLayout.SavedState[size];
            }
        };

        public int getLayoutState() {
            return mLayoutState;
        }

        public void setLayoutState(int layoutState) {
            mLayoutState = layoutState;
        }

        public String getEmptyTextValue() {
            return mEmptyTextValue;
        }

        public void setEmptyTextValue(String emptyTextValue) {
            mEmptyTextValue = emptyTextValue;
        }

        public boolean isShowReload() {
            return mShowReload;
        }

        public void setShowReload(boolean showReload) {
            mShowReload = showReload;
        }
    }

}
