package com.sxq.github.data.source.remote;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.rx2.Rx2Apollo;
import com.sxq.github.data.source.interfaze.ReposDataSource;
import com.sxq.github.provider.network.graphql.ApolloProvider;

import java.util.List;

import github.repos.GetBranchesQuery;
import io.reactivex.Observable;

public class ReposRemoteDataSource implements ReposDataSource {

    @Nullable
    private static ReposRemoteDataSource INSTANCE;

    public static ReposRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            synchronized (ReposRemoteDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ReposRemoteDataSource();
                }
            }
        }
        return INSTANCE;
    }

    private ReposRemoteDataSource() {
    }

    @Override
    public Observable<List<GetBranchesQuery.Node>> getBranches(@NonNull String owner, @NonNull String reposName) {
        ApolloCall<GetBranchesQuery.Data> apolloCall = ApolloProvider.getApolloInstance()
                .query(GetBranchesQuery.builder()
                        .owner(owner)
                        .reposName(reposName)
                        .build());
        return Rx2Apollo.from(apolloCall)
                .filter(dataResponse -> !dataResponse.hasErrors())
                .flatMap(dataResponse -> {
                    if (dataResponse.data() != null && dataResponse.data().repository() != null && dataResponse.data().repository().refs() != null) {
                        return Observable.fromIterable(dataResponse.data().repository().refs().nodes());
                    }
                    return Observable.empty();
                })
                .toList()
                .toObservable();
    }
}
