package com.sxq.github.ui.modules.profile.followers;

import android.content.Context;
import android.support.annotation.NonNull;

import com.sxq.github.ui.modules.user.UserActivity;

public class ProfileFollowerNavigator {

    public void navigateToUserActivity(@NonNull Context context, @NonNull String login) {
        UserActivity.startActivity(context, login);
    }
}
