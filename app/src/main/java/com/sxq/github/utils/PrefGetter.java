package com.sxq.github.utils;

public class PrefGetter {

    public static boolean isRVAnimationEnabled() {
        return PrefHelper.getBoolean("recyclerViewAnimation");
    }
}
