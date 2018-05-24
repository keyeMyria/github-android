package com.sxq.github.ui.modules.profile.feeds;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.sxq.github.data.source.interfaze.UserSourceRepository;
import com.sxq.github.utils.InputHelper;

import io.reactivex.Observable;


public class ProfileFeedsViewModel {

    @NonNull
    private UserSourceRepository mUserSourceRepository;

    @NonNull
    private ProfileFeedsNavigator mProfileFeedsNavigator;

    @Nullable
    private String mLogin;


    public ProfileFeedsViewModel(@NonNull UserSourceRepository userSourceRepository, @NonNull ProfileFeedsNavigator profileFeedsNavigator, @Nullable String login) {
        mUserSourceRepository = InputHelper.checkNotNull(userSourceRepository);
        mProfileFeedsNavigator = InputHelper.checkNotNull(profileFeedsNavigator);
        mLogin = login;
    }


    public Observable<ProfileFeedsUiModel> getUiModel(@Nullable String lastPageCursor) {
        if (InputHelper.isEmpty(mLogin)) {
            return Observable.empty();
        }
        return mUserSourceRepository.getFeeds(mLogin, lastPageCursor)
                .map(data -> new ProfileFeedsUiModel(data));
    }
}
