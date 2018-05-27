package com.sxq.github.ui.modules.repos.release;

import android.support.annotation.Nullable;

import com.sxq.github.data.source.interfaze.ReposSourceRepository;
import com.sxq.github.data.source.local.ReposLocalDataSource;
import com.sxq.github.data.source.remote.ReposRemoteDataSource;

public class ReposReleasesModule {

    public static ReposReleasesViewModel createViewModel(@Nullable String login, @Nullable String reposName) {
        return new ReposReleasesViewModel(ReposSourceRepository.getInstance(
                ReposLocalDataSource.getInstance(),
                ReposRemoteDataSource.getInstance()),
                new ReposReleasesNavigator(),
                login,
                reposName);
    }
}
