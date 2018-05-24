package com.sxq.github.ui.modules.profile.starred;

import android.support.annotation.Nullable;

import com.sxq.github.data.source.interfaze.UserSourceRepository;
import com.sxq.github.data.source.local.UserLocalDataSource;
import com.sxq.github.data.source.remote.UserRemoteDataSource;

public class ProfileStarredModule {

    public static ProfileStarredViewModel createViewModel(@Nullable String login) {
        return new ProfileStarredViewModel(UserSourceRepository.getInstance(
                UserLocalDataSource.getInstance(),
                UserRemoteDataSource.getInstance()),
                new ProfileStarredNavigator(),
                login);
    }
}
