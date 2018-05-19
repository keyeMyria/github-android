package com.sxq.github.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

/***
 *  Helper of front style
 */
public class TypeFaceHelper {

    private static Typeface mTypeFace;

    public static void generateTypeface(Context context) {
        mTypeFace = Typeface.createFromAsset(context.getAssets(), "fonts/app_font.ttf");
    }

    public static void applyTypeface(TextView textView) {
        textView.setTypeface(mTypeFace);
    }

    public static Typeface getTypeFace() {
        return mTypeFace;
    }

}
