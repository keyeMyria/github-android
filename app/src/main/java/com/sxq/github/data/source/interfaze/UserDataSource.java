package com.sxq.github.data.source.interfaze;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


import java.util.List;

import github.profile.GetFollowerQuery;
import github.profile.GetFollowingQuery;
import github.profile.GetOrganizationsQuery;
import github.profile.GetOwnedReposQuery;
import github.profile.GetPinnedReposQuery;
import io.reactivex.Observable;

/**
 * Created by shixiaoqiang01 on 2018/5/18.
 */

public interface UserDataSource {

    Observable<List<GetPinnedReposQuery.Node>> getPinnedRepositories(@NonNull String login);

    Observable<GetOrganizationsQuery.Data> getOrganizations(@NonNull String login);

    Observable<GetFollowingQuery.Data> getFollowing(@NonNull String login, @Nullable String pageCursor);

    Observable<GetFollowerQuery.Data> getFollower(@NonNull String login, @Nullable String pageCursor);

    Observable<GetOwnedReposQuery.Data> getOwnedRepos(@NonNull String login, @Nullable String pageCursor);

}
