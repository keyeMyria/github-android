package com.sxq.github.ui.modules.profile.repos;

import android.support.annotation.Nullable;

import com.sxq.github.data.source.interfaze.UserSourceRepository;
import com.sxq.github.data.source.local.UserLocalDataSource;
import com.sxq.github.data.source.remote.UserRemoteDataSource;

public class ProfileReposModule {

    public static ProfileReposViewModel createViewModel(@Nullable String login) {
        return new ProfileReposViewModel(UserSourceRepository.getInstance(
                UserLocalDataSource.getInstance(),
                UserRemoteDataSource.getInstance()),
                new ProfileReposNavigator(),
                login);
    }
}
