package com.sxq.github.ui.modules.profile.starred;

import android.content.Context;

import com.sxq.github.ui.modules.repos.ReposActivity;

public class ProfileStarredNavigator {

    public void navigateToReposActivity(Context context, String owner, String reposName) {
        ReposActivity.startActivity(context, owner, reposName);
    }
}
