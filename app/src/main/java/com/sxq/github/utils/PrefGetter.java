package com.sxq.github.utils;

import com.sxq.github.utils.common.Constants;

public class PrefGetter {

    public static boolean isRVAnimationEnabled() {
        return PrefHelper.getBoolean("recyclerViewAnimation");
    }

    public static boolean isRectAvatar() {
        return PrefHelper.getBoolean("rect_avatar");
    }

    public static String getCurrentUserName() {
        return PrefHelper.getString(Constants.LOGIN);
    }

    public static String getCurrentUserToken() {
        return PrefHelper.getString(Constants.TOKEN);
    }
}
