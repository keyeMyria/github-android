package com.sxq.github.ui.modules.repos.commit;

import android.support.annotation.Nullable;

import com.sxq.github.data.source.interfaze.ReposSourceRepository;
import com.sxq.github.data.source.local.ReposLocalDataSource;
import com.sxq.github.data.source.remote.ReposRemoteDataSource;

public class ReposCommitModule {

    public static ReposCommitViewModel createViewModel(@Nullable String login,
                                                       @Nullable String reposName,
                                                       @Nullable String branch) {
        return new ReposCommitViewModel(ReposSourceRepository.getInstance(
                ReposLocalDataSource.getInstance(),
                ReposRemoteDataSource.getInstance()),
                new ReposCommitNavigator(),
                login,
                reposName,
                branch);
    }
}
