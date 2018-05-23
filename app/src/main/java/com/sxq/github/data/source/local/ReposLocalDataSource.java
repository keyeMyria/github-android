package com.sxq.github.data.source.local;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.sxq.github.data.source.interfaze.ReposDataSource;

import java.util.List;

import github.repos.GetBranchesQuery;
import github.repos.GetCommitsQuery;
import github.repos.GetContributorsQuery;
import io.reactivex.Observable;

public class ReposLocalDataSource implements ReposDataSource {

    @Nullable
    private static ReposLocalDataSource INSTANCE;

    public static ReposLocalDataSource getInstance() {
        if (INSTANCE == null) {
            synchronized (ReposLocalDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ReposLocalDataSource();
                }
            }
        }
        return INSTANCE;
    }

    private ReposLocalDataSource() {
    }

    @Override
    public Observable<List<GetBranchesQuery.Node>> getBranches(@NonNull String owner, @NonNull String reposName) {
        return null;
    }

    @Override
    public Observable<GetCommitsQuery.Data> getCommits(@NonNull String owner, @NonNull String reposName, @NonNull String branch) {
        return null;
    }

    @Override
    public Observable<String> getFileContent(@NonNull String owner, @NonNull String reposName, @NonNull String branch, @NonNull String path) {
        return null;
    }

    @Override
    public Observable<GetContributorsQuery.Data> getContributors(@NonNull String owner, @NonNull String reposName) {
        return null;
    }
}
