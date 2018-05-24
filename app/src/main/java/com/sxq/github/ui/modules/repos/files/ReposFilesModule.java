package com.sxq.github.ui.modules.repos.files;

import android.support.annotation.Nullable;

import com.sxq.github.data.source.interfaze.ReposSourceRepository;
import com.sxq.github.data.source.local.ReposLocalDataSource;
import com.sxq.github.data.source.remote.ReposRemoteDataSource;

public class ReposFilesModule {

    public static ReposFilesViewModel createViewModel(@Nullable String login,
                                                      @Nullable String reposName,
                                                      @Nullable String branch) {
        return new ReposFilesViewModel(ReposSourceRepository.getInstance(
                ReposLocalDataSource.getInstance(),
                ReposRemoteDataSource.getInstance()),
                new ReposFilesNavigator(),
                login,
                reposName,
                branch);
    }
}
