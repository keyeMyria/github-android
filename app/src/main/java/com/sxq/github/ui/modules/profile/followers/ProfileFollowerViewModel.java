package com.sxq.github.ui.modules.profile.followers;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.sxq.github.data.source.interfaze.UserSourceRepository;
import com.sxq.github.utils.InputHelper;

import io.reactivex.Observable;


public class ProfileFollowerViewModel {

    @NonNull
    private UserSourceRepository mUserSourceRepository;

    @NonNull
    private ProfileFollowerNavigator mProfileFollowerNavigator;

    @Nullable
    private String mLogin;

    @Nullable
    private String mPageCursor;


    public ProfileFollowerViewModel(@NonNull UserSourceRepository userSourceRepository, @NonNull ProfileFollowerNavigator profileFollowerNavigator, @Nullable String login) {
        mUserSourceRepository = InputHelper.checkNotNull(userSourceRepository);
        mProfileFollowerNavigator = InputHelper.checkNotNull(profileFollowerNavigator);
        mLogin = login;
    }


    public Observable<ProfileFollowerUiModel> getUiModel(@Nullable String lastPageCursor) {
        if (InputHelper.isEmpty(mLogin)) {
            return Observable.empty();
        }
        return mUserSourceRepository.getFollower(mLogin, lastPageCursor)
                .map(data -> new ProfileFollowerUiModel(data));
    }
}
