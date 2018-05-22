package com.sxq.github.data.source.interfaze;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import github.profile.GetFollowingQuery;
import github.profile.GetOrganizationsQuery;
import github.profile.GetPinnedReposQuery;
import io.reactivex.Observable;


/**
 * Created by shixiaoqiang01 on 2018/5/18.
 */

public class UserSourceRepository implements UserDataSource {

    @Nullable
    private static UserSourceRepository INSTANCE = null;

    @NonNull
    private UserDataSource mUserLocalDataSource;
    @NonNull
    private UserDataSource mUserRemoteDataSource;

    private UserSourceRepository(@NonNull UserDataSource userLocalDataSource, @NonNull UserDataSource userRemoteDataSource) {
        mUserLocalDataSource = userLocalDataSource;
        mUserRemoteDataSource = userRemoteDataSource;
    }

    public static UserSourceRepository getInstance(@NonNull UserDataSource userLocalDataSource, @NonNull UserDataSource userRemoteDataSource) {
        if (INSTANCE == null) {
            synchronized (UserSourceRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new UserSourceRepository(userLocalDataSource, userRemoteDataSource);
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public Observable<List<GetPinnedReposQuery.Node>> getPinnedRepositories(@NonNull String login) {
        return mUserRemoteDataSource.getPinnedRepositories(login);
    }

    @Override
    public Observable<GetOrganizationsQuery.Data> getOrganizations(@NonNull String login) {
        return mUserRemoteDataSource.getOrganizations(login);
    }

    @Override
    public Observable<GetFollowingQuery.Data> getFollowing(@NonNull String login, @Nullable String pageCursor) {
        return mUserRemoteDataSource.getFollowing(login, pageCursor);
    }
}