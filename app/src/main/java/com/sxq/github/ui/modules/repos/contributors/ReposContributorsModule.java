package com.sxq.github.ui.modules.repos.contributors;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.sxq.github.data.source.interfaze.ReposSourceRepository;
import com.sxq.github.data.source.interfaze.UserSourceRepository;
import com.sxq.github.data.source.local.ReposLocalDataSource;
import com.sxq.github.data.source.local.UserLocalDataSource;
import com.sxq.github.data.source.remote.ReposRemoteDataSource;
import com.sxq.github.data.source.remote.UserRemoteDataSource;

public class ReposContributorsModule {

    public static ReposContributorsViewModel createViewModel(@Nullable String login, @Nullable String reposName) {
        return new ReposContributorsViewModel(ReposSourceRepository.getInstance(
                ReposLocalDataSource.getInstance(),
                ReposRemoteDataSource.getInstance()),
                new ReposContributorsNavigator(),
                login,
                reposName);
    }
}
