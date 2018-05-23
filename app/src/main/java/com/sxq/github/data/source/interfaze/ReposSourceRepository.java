package com.sxq.github.data.source.interfaze;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.sxq.github.data.source.local.ReposLocalDataSource;
import com.sxq.github.data.source.remote.ReposRemoteDataSource;

import java.util.List;

import github.repos.GetBranchesQuery;
import github.repos.GetCommitsQuery;
import github.repos.GetContributorsQuery;
import io.reactivex.Observable;

public class ReposSourceRepository implements ReposDataSource {

    @Nullable
    private static ReposSourceRepository INSTANCE;

    @NonNull
    private ReposDataSource mReposLocalRepository;
    @NonNull
    private ReposDataSource mReposRemoteRepository;

    private ReposSourceRepository(@NonNull ReposDataSource reposLocalRepository, @NonNull ReposDataSource reposRemoteRepository) {
        mReposLocalRepository = reposLocalRepository;
        mReposRemoteRepository = reposRemoteRepository;
    }

    public static ReposSourceRepository getInstance(@NonNull ReposDataSource reposLocalRepository, @NonNull ReposDataSource reposRemoteRepository) {
        if (INSTANCE == null) {
            synchronized (ReposSourceRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ReposSourceRepository(ReposLocalDataSource.getInstance(), ReposRemoteDataSource.getInstance());
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public Observable<List<GetBranchesQuery.Node>> getBranches(@NonNull String owner, @NonNull String reposName) {
        return mReposRemoteRepository.getBranches(owner, reposName);
    }

    @Override
    public Observable<GetCommitsQuery.Data> getCommits(@NonNull String owner, @NonNull String reposName, @NonNull String branch, @Nullable String pageCursor) {
        return mReposRemoteRepository.getCommits(owner, reposName, branch, pageCursor);
    }

    @Override
    public Observable<String> getFileContent(@NonNull String owner, @NonNull String reposName, @NonNull String branch, @NonNull String path) {
        return mReposRemoteRepository.getFileContent(owner, reposName, branch, path);
    }

    @Override
    public Observable<GetContributorsQuery.Data> getContributors(@NonNull String owner, @NonNull String reposName) {
        return mReposRemoteRepository.getContributors(owner, reposName);
    }
}
