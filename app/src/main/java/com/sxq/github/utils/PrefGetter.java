package com.sxq.github.utils;

public class PrefGetter {

    public static boolean isRVAnimationEnabled() {
        return PrefHelper.getBoolean("recyclerViewAnimation");
    }

    public static boolean isRectAvatar() {
        return PrefHelper.getBoolean("rect_avatar");
    }
}
