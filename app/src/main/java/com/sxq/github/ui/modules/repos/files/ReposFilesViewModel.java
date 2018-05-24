package com.sxq.github.ui.modules.repos.files;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.sxq.github.data.source.interfaze.ReposSourceRepository;
import com.sxq.github.utils.InputHelper;

import io.reactivex.Observable;


public class ReposFilesViewModel {

    @NonNull
    private ReposSourceRepository mReposSourceRepository;

    @NonNull
    private ReposFilesNavigator mReposFilesNavigator;

    @Nullable
    private String mLogin;

    @Nullable
    private String mReposName;

    @Nullable
    private String mBranch;


    public ReposFilesViewModel(@NonNull ReposSourceRepository ReposSourceRepository,
                               @NonNull ReposFilesNavigator reposFilesNavigator,
                               @Nullable String login,
                               @Nullable String reposName,
                               @Nullable String branch) {
        mReposSourceRepository = InputHelper.checkNotNull(ReposSourceRepository);
        mReposFilesNavigator = InputHelper.checkNotNull(reposFilesNavigator);
        mLogin = login;
        mReposName = reposName;
        mBranch = branch;
    }

    public Observable<ReposFilesUiModel> getUiModel(@NonNull String branch, @NonNull String path) {
        if (InputHelper.isEmpty(mLogin)) {
            return Observable.empty();
        }
        mBranch = branch;
        return mReposSourceRepository.getReposFiles(mLogin, mReposName, mBranch, path)
                .map(data -> new ReposFilesUiModel(data));
    }
}
