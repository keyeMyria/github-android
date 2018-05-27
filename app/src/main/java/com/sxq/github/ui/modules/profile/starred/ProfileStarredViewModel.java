package com.sxq.github.ui.modules.profile.starred;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.sxq.github.data.source.interfaze.UserSourceRepository;
import com.sxq.github.utils.InputHelper;

import io.reactivex.Observable;


public class ProfileStarredViewModel {

    @NonNull
    private UserSourceRepository mUserSourceRepository;

    @NonNull
    private ProfileStarredNavigator mProfileStarredNavigator;

    @Nullable
    private String mLogin;


    public ProfileStarredViewModel(@NonNull UserSourceRepository userSourceRepository, @NonNull ProfileStarredNavigator profileStarredNavigator, @Nullable String login) {
        mUserSourceRepository = InputHelper.checkNotNull(userSourceRepository);
        mProfileStarredNavigator = InputHelper.checkNotNull(profileStarredNavigator);
        mLogin = login;
    }


    public Observable<ProfileStarredUiModel> getUiModel(@Nullable String lastPageCursor) {
        if (InputHelper.isEmpty(mLogin)) {
            return Observable.empty();
        }
        return mUserSourceRepository.getStarredRepos(mLogin, lastPageCursor)
                .map(data -> new ProfileStarredUiModel(data));
    }

    @NonNull
    public ProfileStarredNavigator getProfileStarredNavigator() {
        return mProfileStarredNavigator;
    }
}
