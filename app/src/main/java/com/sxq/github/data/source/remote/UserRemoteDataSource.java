package com.sxq.github.data.source.remote;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.rx2.Rx2Apollo;
import com.sxq.github.data.source.interfaze.UserDataSource;
import com.sxq.github.provider.network.graphql.ApolloProvider;

import java.util.List;

import github.profile.GetFollowerQuery;
import github.profile.GetFollowingQuery;
import github.profile.GetOrganizationsQuery;
import github.profile.GetOwnedReposQuery;
import github.profile.GetPinnedReposQuery;
import github.user.GetProfileFeedsQuery;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by shixiaoqiang01 on 2018/5/18.
 */

public class UserRemoteDataSource implements UserDataSource {

    @Nullable
    private static UserRemoteDataSource INSTANCE;

    private UserRemoteDataSource() {

    }

    public static UserRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            synchronized (UserRemoteDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new UserRemoteDataSource();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public Observable<List<GetPinnedReposQuery.Node>> getPinnedRepositories(@NonNull String login) {
        ApolloCall<GetPinnedReposQuery.Data> apolloCall = ApolloProvider.getApolloInstance()
                .query(GetPinnedReposQuery.builder()
                        .login(login)
                        .build());
        return Rx2Apollo.from(apolloCall)
                .filter(dataResponse -> !dataResponse.hasErrors())
                .flatMap(dataResponse -> {
                    if (dataResponse.data() != null && dataResponse.data().user() != null) {
                        return Observable.fromIterable(dataResponse.data().user().pinnedRepositories().edges());
                    }
                    return Observable.empty();
                })
                .map(GetPinnedReposQuery.Edge::node)
                .toList()
                .toObservable();
    }

    @Override
    public Observable<GetOrganizationsQuery.Data> getOrganizations(@NonNull String login) {
        ApolloCall<GetOrganizationsQuery.Data> apolloCall = ApolloProvider.getApolloInstance()
                .query(GetOrganizationsQuery.builder()
                        .login(login)
                        .build());
        return Rx2Apollo.from(apolloCall)
                .filter(dataResponse -> !dataResponse.hasErrors())
                .map(dataResponse -> dataResponse.data());
    }

    @Override
    public Observable<GetFollowingQuery.Data> getFollowing(@NonNull String login, @Nullable String pageCursor) {
        ApolloCall<GetFollowingQuery.Data> apolloCall = ApolloProvider.getApolloInstance()
                .query(GetFollowingQuery.builder()
                        .login(login)
                        .pageCursor(pageCursor)
                        .build());
        return Rx2Apollo.from(apolloCall)
                .filter(dataResponse -> !dataResponse.hasErrors())
                .map(dataResponse -> dataResponse.data());
    }

    @Override
    public Observable<GetFollowerQuery.Data> getFollower(@NonNull String login, @Nullable String pageCursor) {
        ApolloCall<GetFollowerQuery.Data> apolloCall = ApolloProvider.getApolloInstance()
                .query(GetFollowerQuery.builder()
                        .login(login)
                        .pageCursor(pageCursor)
                        .build());
        return Rx2Apollo.from(apolloCall)
                .filter(dataResponse -> !dataResponse.hasErrors())
                .map(dataResponse -> dataResponse.data());
    }

    @Override
    public Observable<GetOwnedReposQuery.Data> getOwnedRepos(@NonNull String login, @Nullable String pageCursor) {
        ApolloCall<GetOwnedReposQuery.Data> apolloCall = ApolloProvider.getApolloInstance()
                .query(GetOwnedReposQuery.builder()
                        .login(login)
                        .pageCursor(pageCursor)
                        .build());
        return Rx2Apollo.from(apolloCall)
                .filter(dataResponse -> !dataResponse.hasErrors())
                .map(dataResponse -> dataResponse.data());
    }

    @Override
    public Observable<GetProfileFeedsQuery.Data> getFeeds(@NonNull String login, @Nullable String pageCursor) {
        ApolloCall<GetProfileFeedsQuery.Data> apolloCall = ApolloProvider.getApolloInstance()
                .query(GetProfileFeedsQuery.builder()
                        .login(login)
                        .pageCursor(pageCursor)
                        .build());
        return Rx2Apollo.from(apolloCall)
                .filter(dataResponse -> !dataResponse.hasErrors())
                .map(dataResponse -> dataResponse.data());
    }
}
