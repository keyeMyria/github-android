package com.sxq.github.ui.widgets;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import com.sxq.github.utils.TypeFaceHelper;

/**
 * Uniform font Button
 */
public class FontButton extends AppCompatButton {

    public FontButton(Context context) {
        this(context, null);
    }

    public FontButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FontButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        if (isInEditMode()) return;
        TypeFaceHelper.applyTypeface(this);
    }
}
