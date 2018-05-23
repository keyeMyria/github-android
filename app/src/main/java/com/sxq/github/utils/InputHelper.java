package com.sxq.github.utils;

import android.gesture.Prediction;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.google.common.base.Preconditions;

import java.util.List;

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

    public static boolean isEmpty(@Nullable List list){
        return list == null || list.isEmpty();
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


    public static <T> T checkNotNull(T reference) {
        return Preconditions.checkNotNull(reference, reference.getClass().getSimpleName() + " can not be null");
    }
}
