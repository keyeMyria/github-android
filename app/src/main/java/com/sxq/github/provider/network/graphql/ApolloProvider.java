package com.sxq.github.provider.network.graphql;

import android.os.Environment;

import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Operation;
import com.apollographql.apollo.api.ResponseField;
//import com.apollographql.apollo.cache.http.ApolloHttpCache;
import com.apollographql.apollo.cache.http.DiskLruHttpCacheStore;
import com.apollographql.apollo.cache.normalized.CacheKey;
import com.apollographql.apollo.cache.normalized.CacheKeyResolver;
import com.sxq.github.BuildConfig;
import com.sxq.github.provider.network.OkHttpProvider;

import java.io.File;
import java.util.Map;

import javax.annotation.Nonnull;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

/**
 * Created by shixiaoqiang01 on 2018/5/17.
 */

public class ApolloProvider {

    private static ApolloClient INSTANCE = null;

    public static ApolloClient getApolloInstance() {
        if (INSTANCE == null) {
            synchronized (ApolloClient.class) {
                if (INSTANCE == null) {
                    DiskLruHttpCacheStore diskLruHttpCacheStore = new DiskLruHttpCacheStore(
                            Environment.getExternalStoragePublicDirectory(DIRECTORY_DOWNLOADS), 1024 * 1024);
                    INSTANCE = ApolloClient.builder()
//                            .httpCache(new ApolloHttpCache(diskLruHttpCacheStore))
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