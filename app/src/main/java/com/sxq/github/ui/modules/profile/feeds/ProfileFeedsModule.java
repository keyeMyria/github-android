package com.sxq.github.ui.modules.profile.feeds;

import android.support.annotation.Nullable;

import com.sxq.github.data.source.interfaze.UserSourceRepository;
import com.sxq.github.data.source.local.UserLocalDataSource;
import com.sxq.github.data.source.remote.UserRemoteDataSource;

public class ProfileFeedsModule {

    public static ProfileFeedsViewModel createViewModel(@Nullable String login) {
        return new ProfileFeedsViewModel(UserSourceRepository.getInstance(
                UserLocalDataSource.getInstance(),
                UserRemoteDataSource.getInstance()),
                new ProfileFeedsNavigator(),
                login);
    }
}
