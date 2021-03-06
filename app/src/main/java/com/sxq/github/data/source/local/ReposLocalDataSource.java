package com.sxq.github.data.source.local;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.sxq.github.data.source.interfaze.ReposDataSource;

import java.util.List;

import github.repos.GetBranchesQuery;
import github.repos.GetCommitsQuery;
import github.repos.GetContributorsQuery;
import github.repos.GetCurrentLevelTreeViewQuery;
import github.repos.GetReleasesQuery;
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
    public Observable<GetCommitsQuery.Data> getCommits(@NonNull String owner, @NonNull String reposName, @NonNull String branch, @Nullable String pageCursor) {
        return null;
    }

    @Override
    public Observable<String> getFileContent(@NonNull String owner, @NonNull String reposName, @NonNull String branch, @NonNull String path) {
        return null;
    }

    @Override
    public Observable<GetContributorsQuery.Data> getContributors(@NonNull String owner, @NonNull String reposName, @Nullable String pageCursor) {
        return null;
    }

    @Override
    public Observable<List<GetCurrentLevelTreeViewQuery.Entry>> getReposFiles(@NonNull String owner, @NonNull String reposName, @NonNull String branch, @NonNull String path) {
        return null;
    }

    @Override
    public Observable<GetReleasesQuery.Data> getReleases(@NonNull String owner, @NonNull String reposName, @Nullable String pageCursor) {
        return null;
    }
}
