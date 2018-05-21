package com.sxq.github.ui.modules.profile.overview;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.sxq.github.data.source.interfaze.UserSourceRepository;
import com.sxq.github.utils.InputHelper;


import java.util.List;

import github.profile.GetOrganizationsQuery;
import github.profile.GetPinnedReposQuery;
import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;

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
        Observable<List<GetPinnedReposQuery.Node>> pinnedObservable = mUserSourceRepository
                .getPinnedRepositories(mLogin);

        Observable<GetOrganizationsQuery.Data> orgsObservable = mUserSourceRepository
                .getOrganizations(mLogin);
        return Observable.zip(pinnedObservable,
                orgsObservable,
                new BiFunction<List<GetPinnedReposQuery.Node>, GetOrganizationsQuery.Data, Object>() {
                    @Override
                    public Object apply(List<GetPinnedReposQuery.Node> nodes, GetOrganizationsQuery.Data data) throws Exception {
                        return new ProfileOverViewUiModel(nodes, data);
                    }
                }).map(o -> (ProfileOverViewUiModel) o);
    }
}
