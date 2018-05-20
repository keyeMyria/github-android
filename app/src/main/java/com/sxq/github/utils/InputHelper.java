package com.sxq.github.utils;

import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

public class InputHelper {

    public static boolean isWhiteSpaces(@Nullable String s) {
        return s != null && s.matches("\\s+");
    }

    public static boolean isEmpty(@Nullable String text) {
        return text == null || TextUtils.isEmpty(text) || isWhiteSpaces(text) || text.equalsIgnoreCase("null");
    }

    public static boolean isEmpty(@Nullable Object text) {
        return text == null || isEmpty(text.toString());
    }

    public static boolean isEmpty(@Nullable EditText text) {
        return text == null || isEmpty(text.getText().toString());
    }

    public static boolean isEmpty(@Nullable TextView text) {
        return text == null || isEmpty(text.getText().toString());
    }


    public static boolean isEmpty(@Nullable TextInputLayout txt) {
        return txt == null || isEmpty(txt.getEditText());
    }

}
