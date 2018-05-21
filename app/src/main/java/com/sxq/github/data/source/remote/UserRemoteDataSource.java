package com.sxq.github.data.source.remote;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.rx2.Rx2Apollo;
import com.sxq.github.data.source.interfaze.UserDataSource;
import com.sxq.github.provider.network.graphql.ApolloProvider;

import java.util.List;

import github.profile.GetOrganizationsQuery;
import github.profile.GetPinnedReposQuery;
import io.reactivex.Observable;

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
                    return Observable.fromIterable(dataResponse.data().user().pinnedRepositories().edges());
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
}
