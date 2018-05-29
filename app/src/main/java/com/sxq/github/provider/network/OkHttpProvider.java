package com.sxq.github.provider.network;

import com.sxq.github.App;
import com.sxq.github.BuildConfig;
import com.sxq.github.data.model.login.Login;
import com.sxq.github.provider.network.interceptor.AuthenticationInterceptor;
import com.sxq.github.provider.network.interceptor.ContentTypeInterceptor;
import com.sxq.github.provider.network.interceptor.RewriteCacheControlInterceptor;
import com.sxq.github.utils.NetworkUtil;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by shixiaoqiang01 on 2018/5/17.
 */

public class OkHttpProvider {

    private static OkHttpClient okHttpClient = null;

    public static OkHttpClient provideOkHttpClient() {
        if (okHttpClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.addInterceptor(new AuthenticationInterceptor(Login.getCurrentUser().getToken()));
            builder.addInterceptor(new ContentTypeInterceptor());
            if (BuildConfig.DEBUG) {
                builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
            }
            okHttpClient = builder.build();
        }
        return okHttpClient;
    }
}
