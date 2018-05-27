package com.sxq.github.ui.modules.profile.overview;

import android.content.Context;
import android.support.annotation.NonNull;

import com.sxq.github.ui.modules.repos.ReposActivity;

/**
 * Created by shixiaoqiang01 on 2018/5/18.
 */

public class ProfileOverViewNavigator {

    public void navigateToReposActivity(@NonNull Context context, @NonNull String owner, @NonNull String reposName) {
        ReposActivity.startActivity(context, owner, reposName);
    }

}
