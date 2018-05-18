package com.sxq.github.ui.modules.profile.overview;

import android.support.annotation.NonNull;

import com.sxq.github.data.model.login.Login;
import com.sxq.github.data.source.interfaze.UserSourceRepository;


import io.reactivex.Observable;

/**
 * Created by shixiaoqiang01 on 2018/5/18.
 */

public class ProfileOverViewViewModel {


    @NonNull
    private UserSourceRepository mUserSourceRepository;

    public ProfileOverViewViewModel(@NonNull UserSourceRepository userSourceRepository) {
        mUserSourceRepository = userSourceRepository;
    }

    public Observable<ProfileOverViewUiModel> getUiModel() {
        return mUserSourceRepository
                .getProfileOverViewUiModel(Login.getCurrentUser().getLogin())
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
