package com.sxq.github.ui.modules.repos.release;

import android.content.Context;
import android.support.annotation.NonNull;

import com.sxq.github.ui.modules.user.UserActivity;

public class ReposReleasesNavigator {

    public void navigateToUserActivity(@NonNull Context context, @NonNull String login) {
        UserActivity.startActivity(context, login);
    }
}
