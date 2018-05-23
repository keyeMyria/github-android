package com.sxq.github.ui.modules.repos.branch;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.sxq.github.data.source.interfaze.ReposSourceRepository;
import com.sxq.github.utils.InputHelper;

import io.reactivex.Observable;


public class ReposBranchViewModel {

    @NonNull
    private ReposSourceRepository mReposSourceRepository;

    @NonNull
    private ReposBranchNavigator mReposBranchNavigator;

    @Nullable
    private String mLogin;

    @Nullable
    private String mReposName;


    public ReposBranchViewModel(@NonNull ReposSourceRepository ReposSourceRepository,
                                @NonNull ReposBranchNavigator reposBranchNavigator,
                                @Nullable String login,
                                @Nullable String reposName) {
        mReposSourceRepository = InputHelper.checkNotNull(ReposSourceRepository);
        mReposBranchNavigator = InputHelper.checkNotNull(reposBranchNavigator);
        mLogin = login;
        mReposName = reposName;
    }

    public Observable<ReposBranchUiModel> getUiModel() {
        if (InputHelper.isEmpty(mLogin)) {
            return Observable.empty();
        }
        return mReposSourceRepository.getBranches(mLogin, mReposName)
                .map(data -> new ReposBranchUiModel(data));
    }
}
