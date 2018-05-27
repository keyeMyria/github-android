package com.sxq.github.ui.modules.repos.release;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.sxq.github.data.source.interfaze.ReposSourceRepository;
import com.sxq.github.utils.InputHelper;

import io.reactivex.Observable;


public class ReposReleasesViewModel {

    @NonNull
    private ReposSourceRepository mReposSourceRepository;

    @NonNull
    private ReposReleasesNavigator mReposReleasesNavigator;

    @Nullable
    private String mLogin;

    @Nullable
    private String mReposName;


    public ReposReleasesViewModel(@NonNull ReposSourceRepository reposSourceRepository, @NonNull ReposReleasesNavigator reposReleasesNavigator, @Nullable String login, @Nullable String reposName) {
        mReposSourceRepository = InputHelper.checkNotNull(reposSourceRepository);
        mReposReleasesNavigator = InputHelper.checkNotNull(reposReleasesNavigator);
        mLogin = login;
        mReposName = reposName;
    }


    public Observable<ReposReleasesUiModel> getUiModel(@Nullable String lastPageCursor) {
        if (InputHelper.isEmpty(mLogin)) {
            return Observable.empty();
        }
        return mReposSourceRepository.getReleases(mLogin, mReposName, lastPageCursor)
                .map(data -> new ReposReleasesUiModel(data));
    }

    @NonNull
    public ReposReleasesNavigator getReposReleasesNavigator() {
        return mReposReleasesNavigator;
    }
}
