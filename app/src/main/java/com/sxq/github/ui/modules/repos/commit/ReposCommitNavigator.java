package com.sxq.github.ui.modules.repos.commit;

import android.content.Context;
import android.support.annotation.NonNull;

import com.sxq.github.ui.modules.user.UserActivity;

public class ReposCommitNavigator {

    public void navigateToUserActivity(@NonNull Context context, @NonNull String login) {
        UserActivity.startActivity(context, login);
    }
}
