package com.sxq.github.ui.modules.profile.following;

import android.content.Context;
import android.support.annotation.NonNull;

import com.sxq.github.ui.modules.user.UserActivity;

public class ProfileFollowingNavigator {

    public void navigateToUserActivity(@NonNull Context context, @NonNull String login) {
        UserActivity.startActivity(context, login);
    }
}
