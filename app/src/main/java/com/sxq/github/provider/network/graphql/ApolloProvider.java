package com.sxq.github.provider.network.graphql;

import com.apollographql.apollo.ApolloClient;
import com.sxq.github.BuildConfig;
import com.sxq.github.provider.network.OkHttpProvider;

/**
 * Created by shixiaoqiang01 on 2018/5/17.
 */

public class ApolloProvider {

    private static ApolloClient INSTANCE = null;

    public static ApolloClient getApolloInstance() {
        if (INSTANCE == null) {
            synchronized (ApolloClient.class) {
                if (INSTANCE == null) {
                    INSTANCE = ApolloClient.builder()
                            .serverUrl(BuildConfig.REST_URL)
                            .okHttpClient(OkHttpProvider.provideOkHttpClient())
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public static void destroy() {
        INSTANCE = null;
    }
}