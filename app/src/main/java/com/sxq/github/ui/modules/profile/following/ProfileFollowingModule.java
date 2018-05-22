package com.sxq.github.ui.modules.profile.following;

import android.support.annotation.Nullable;

import com.sxq.github.data.source.interfaze.UserSourceRepository;
import com.sxq.github.data.source.local.UserLocalDataSource;
import com.sxq.github.data.source.remote.UserRemoteDataSource;

public class ProfileFollowingModule {

    public static ProfileFollowingViewModel createViewModel(@Nullable String login) {
        return new ProfileFollowingViewModel(UserSourceRepository.getInstance(
                UserLocalDataSource.getInstance(),
                UserRemoteDataSource.getInstance()),
                new ProfileFollowingNavigator(),
                login);
    }
}
