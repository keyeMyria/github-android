package com.sxq.github.ui.modules.profile.followers;

import android.support.annotation.Nullable;

import com.sxq.github.data.source.interfaze.UserSourceRepository;
import com.sxq.github.data.source.local.UserLocalDataSource;
import com.sxq.github.data.source.remote.UserRemoteDataSource;

public class ProfileFollowerModule {

    public static ProfileFollowerViewModel createViewModel(@Nullable String login) {
        return new ProfileFollowerViewModel(UserSourceRepository.getInstance(
                UserLocalDataSource.getInstance(),
                UserRemoteDataSource.getInstance()),
                new ProfileFollowerNavigator(),
                login);
    }
}
