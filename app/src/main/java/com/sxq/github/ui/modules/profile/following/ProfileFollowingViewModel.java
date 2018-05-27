package com.sxq.github.ui.modules.profile.following;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.sxq.github.data.source.interfaze.UserSourceRepository;
import com.sxq.github.utils.InputHelper;

import io.reactivex.Observable;


public class ProfileFollowingViewModel {

    @NonNull
    private UserSourceRepository mUserSourceRepository;

    @NonNull
    private ProfileFollowingNavigator mProfileFollowingNavigator;

    @Nullable
    private String mLogin;

    @Nullable
    private String mPageCursor;


    public ProfileFollowingViewModel(@NonNull UserSourceRepository userSourceRepository, @NonNull ProfileFollowingNavigator profileFollowingNavigator, @Nullable String login) {
        mUserSourceRepository = InputHelper.checkNotNull(userSourceRepository);
        mProfileFollowingNavigator = InputHelper.checkNotNull(profileFollowingNavigator);
        mLogin = login;
    }


    public io.reactivex.Observable<ProfileFollowingUiModel> getUiModel(@Nullable String lastPageCursor) {
        if (InputHelper.isEmpty(mLogin)) {
            return Observable.empty();
        }
        return mUserSourceRepository.getFollowing(mLogin, lastPageCursor)
                .map(data -> new ProfileFollowingUiModel(data));
    }

    @NonNull
    public ProfileFollowingNavigator getProfileFollowingNavigator() {
        return mProfileFollowingNavigator;
    }
}
