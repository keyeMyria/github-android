package com.sxq.github.provider.network;

import com.sxq.github.BuildConfig;
import com.sxq.github.data.model.login.Login;
import com.sxq.github.provider.network.interceptor.AuthenticationInterceptor;
import com.sxq.github.provider.network.interceptor.ContentTypeInterceptor;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by shixiaoqiang01 on 2018/5/17.
 */

public class OkHttpProvider {

    private static OkHttpClient okHttpClient = null;

    public static OkHttpClient provideOkHttpClient() {
        if (okHttpClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            if (BuildConfig.DEBUG) {
                builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
            }
            builder.addInterceptor(new AuthenticationInterceptor(Login.getCurrentUser().getToken()));
            builder.addInterceptor(new ContentTypeInterceptor());
            okHttpClient = builder.build();
        }
        return okHttpClient;
    }
}
