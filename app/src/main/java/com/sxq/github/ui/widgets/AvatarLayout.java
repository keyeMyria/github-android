package com.sxq.github.ui.widgets;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.TooltipCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.signature.StringSignature;
import com.sxq.github.R;
import com.sxq.github.utils.InputHelper;
import com.sxq.github.utils.PrefGetter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;

public class AvatarLayout extends FrameLayout {

    @BindView(R.id.avatar)
    ShapedImageView mAvatar;

    @Nullable
    private String mLogin;
    private boolean isOrg;
    private boolean isEnterprise;

    @OnClick(R.id.avatar)
    void onClick(@NonNull View view) {
        if (InputHelper.isEmpty(mLogin)) return;
        /**
         * TODO launch {@link com.sxq.github.ui.modules.user.UserActivity}
         */
    }

    public AvatarLayout(@NonNull Context context) {
        super(context);
    }

    public AvatarLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AvatarLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public AvatarLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        inflate(getContext(), R.layout.avatar_layout, this);
        if (isInEditMode()) return;
        ButterKnife.bind(this);
        setBackground();
        if (PrefGetter.isRectAvatar()) {
            mAvatar.setShape(ShapedImageView.SHAPE_MODE_ROUND_RECT, 15);
        }
    }

    public void setUrl(@Nullable String url, @Nullable String login) {
        setUrl(url, login, false);
    }

    public void setUrl(@Nullable String url, @Nullable String login, boolean reload) {
        this.mLogin = login;
        mAvatar.setContentDescription(login);
        if (login != null) {
            TooltipCompat.setTooltipText(mAvatar, login);
        } else {
            mAvatar.setOnClickListener(null);
            mAvatar.setOnLongClickListener(null);
        }
        Glide.with(getContext())
                .load(url)
                .fallback(ContextCompat.getDrawable(getContext(), R.drawable.ic_launcher_foreground))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .signature(new StringSignature(reload ? String.valueOf(System.currentTimeMillis()) : "0"))
                .dontAnimate()
                .into(mAvatar);

    }

    private void setBackground() {
        if (PrefGetter.isRectAvatar()) {
            setBackgroundResource(R.drawable.rect_shape);
        } else {
            setBackgroundResource(R.drawable.circle_shape);
        }
    }
}
