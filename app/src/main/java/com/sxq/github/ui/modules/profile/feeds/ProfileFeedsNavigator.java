package com.sxq.github.ui.modules.profile.feeds;

import android.content.Context;
import android.support.annotation.NonNull;

import com.sxq.github.ui.modules.repos.ReposActivity;

public class ProfileFeedsNavigator {

    public void navigateToReposActivity(@NonNull Context context, @NonNull String owner, @NonNull String reposName) {
        ReposActivity.startActivity(context, owner, reposName);
    }
}
