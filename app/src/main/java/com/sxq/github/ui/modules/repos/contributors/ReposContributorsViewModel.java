package com.sxq.github.ui.modules.repos.contributors;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.sxq.github.data.source.interfaze.ReposSourceRepository;
import com.sxq.github.utils.InputHelper;

import io.reactivex.Observable;


public class ReposContributorsViewModel {

    @NonNull
    private ReposSourceRepository mReposSourceRepository;

    @NonNull
    private ReposContributorsNavigator mReposContributorsNavigator;

    @Nullable
    private String mLogin;

    @Nullable
    private String mReposName;


    public ReposContributorsViewModel(@NonNull ReposSourceRepository reposSourceRepository, @NonNull ReposContributorsNavigator reposContributorsNavigator, @Nullable String login, @Nullable String reposName) {
        mReposSourceRepository = InputHelper.checkNotNull(reposSourceRepository);
        mReposContributorsNavigator = InputHelper.checkNotNull(reposContributorsNavigator);
        mLogin = login;
        mReposName = reposName;
    }


    public Observable<ReposContributorsUiModel> getUiModel(@Nullable String lastPageCursor) {
        if (InputHelper.isEmpty(mLogin)) {
            return Observable.empty();
        }
        return mReposSourceRepository.getContributors(mLogin, mReposName, lastPageCursor)
                .map(data -> new ReposContributorsUiModel(data));
    }

    @NonNull
    public ReposContributorsNavigator getReposContributorsNavigator() {
        return mReposContributorsNavigator;
    }
}
