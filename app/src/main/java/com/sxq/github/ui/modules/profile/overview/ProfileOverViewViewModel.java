package com.sxq.github.ui.modules.profile.overview;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.sxq.github.data.model.login.Login;
import com.sxq.github.data.source.interfaze.UserSourceRepository;
import com.sxq.github.utils.InputHelper;


import io.reactivex.Observable;

/**
 * Created by shixiaoqiang01 on 2018/5/18.
 */

public class ProfileOverViewViewModel {

    @Nullable
    private String mLogin;

    @NonNull
    private UserSourceRepository mUserSourceRepository;

    @NonNull
    private ProfileOverViewNavigator mProfileOverViewNavigator;

    public ProfileOverViewViewModel(@Nullable String login, @NonNull UserSourceRepository userSourceRepository, @NonNull ProfileOverViewNavigator profileOverViewNavigator) {
        mLogin = login;
        mUserSourceRepository = InputHelper.checkNotNull(userSourceRepository);
        mProfileOverViewNavigator = InputHelper.checkNotNull(profileOverViewNavigator);
    }

    public Observable<ProfileOverViewUiModel> getUiModel() {
        if (InputHelper.isEmpty(mLogin)) {
            return Observable.empty();
        }
        return mUserSourceRepository
                .getProfileOverViewUiModel(mLogin)
                .map(nodes -> new ProfileOverViewUiModel(nodes));

//        Observable<List<GetPinnedReposQuery.Edge>> observable2 = Observable.empty();
//        Observable.zip(observable1,
//                observable2, new BiFunction<List<GetPinnedReposQuery.Node>, List<GetPinnedReposQuery.Edge>, Object>() {
//                    @Override
//                    public Object apply(List<GetPinnedReposQuery.Node> nodes, List<GetPinnedReposQuery.Edge> edges) throws Exception {
//                        return new ProfileOverViewUiModel(nodes);
//                    }
//                });
    }
}
