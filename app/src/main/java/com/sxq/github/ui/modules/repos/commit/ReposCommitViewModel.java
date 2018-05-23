package com.sxq.github.ui.modules.repos.commit;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.sxq.github.data.source.interfaze.ReposSourceRepository;
import com.sxq.github.utils.InputHelper;

import io.reactivex.Observable;


public class ReposCommitViewModel {

    @NonNull
    private ReposSourceRepository mReposSourceRepository;

    @NonNull
    private ReposCommitNavigator mReposCommitNavigator;

    @Nullable
    private String mLogin;

    @Nullable
    private String mReposName;

    @Nullable
    private String mBranch;

    @Nullable
    private String mPageCursor;

    private int mPageSize;


    public ReposCommitViewModel(@NonNull ReposSourceRepository ReposSourceRepository,
                                @NonNull ReposCommitNavigator reposCommitNavigator,
                                @Nullable String login,
                                @Nullable String reposName,
                                @Nullable String branch) {
        mReposSourceRepository = InputHelper.checkNotNull(ReposSourceRepository);
        mReposCommitNavigator = InputHelper.checkNotNull(reposCommitNavigator);
        mLogin = login;
        mReposName = reposName;
        mBranch = branch;
    }


    public Observable<ReposCommitUiModel> getUiModelOfNextPage() {
        return Observable.empty();
    }

    public Observable<ReposCommitUiModel> getUiModel(@Nullable String lastPageCursor) {
        if (InputHelper.isEmpty(mLogin)) {
            return Observable.empty();
        }
        return mReposSourceRepository.getCommits(mLogin, mReposName, mBranch, lastPageCursor)
                .map(data -> new ReposCommitUiModel(data));
    }
}
