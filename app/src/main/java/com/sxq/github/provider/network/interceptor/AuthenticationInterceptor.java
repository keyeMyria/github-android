package com.sxq.github.provider.network.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by shixiaoqiang01 on 2018/5/17.
 */

public class AuthenticationInterceptor implements Interceptor {

    private String mToken = null;

    public AuthenticationInterceptor(String token) {
        this.mToken = token;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request.Builder builder = originalRequest.newBuilder();
        builder.addHeader("Authorization", mToken.startsWith("Basic") ? mToken : ("token " + mToken));
        return chain.proceed(builder.build());
    }
}
