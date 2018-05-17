package com.sxq.github.provider.network.graphql;

import com.apollographql.apollo.ApolloClient;
import com.sxq.github.BuildConfig;
import com.sxq.github.provider.network.OkHttpProvider;

/**
 * Created by shixiaoqiang01 on 2018/5/17.
 */

public class ApolloProvider {

    public static ApolloClient getApollo() {
        return ApolloClient.builder()
                .serverUrl(BuildConfig.REST_URL)
                .okHttpClient(OkHttpProvider.provideOkHttpClient())
                .build();
    }
}