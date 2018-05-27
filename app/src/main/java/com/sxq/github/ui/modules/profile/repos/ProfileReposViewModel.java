package com.sxq.github.ui.modules.profile.repos;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.sxq.github.data.source.interfaze.UserSourceRepository;
import com.sxq.github.utils.InputHelper;

import io.reactivex.Observable;


public class ProfileReposViewModel {

    @NonNull
    private UserSourceRepository mUserSourceRepository;

    @NonNull
    private ProfileReposNavigator mProfileReposNavigator;

    @Nullable
    private String mLogin;


    public ProfileReposViewModel(@NonNull UserSourceRepository userSourceRepository, @NonNull ProfileReposNavigator profileReposNavigator, @Nullable String login) {
        mUserSourceRepository = InputHelper.checkNotNull(userSourceRepository);
        mProfileReposNavigator = InputHelper.checkNotNull(profileReposNavigator);
        mLogin = login;
    }


    public Observable<ProfileReposUiModel> getUiModel(@Nullable String lastPageCursor) {
        if (InputHelper.isEmpty(mLogin)) {
            return Observable.empty();
        }
        return mUserSourceRepository.getOwnedRepos(mLogin, lastPageCursor)
                .map(data -> new ProfileReposUiModel(data));
    }

    @NonNull
    public ProfileReposNavigator getProfileReposNavigator() {
        return mProfileReposNavigator;
    }
}
