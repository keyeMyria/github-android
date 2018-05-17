package com.sxq.github.provider.network.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by shixiaoqiang01 on 2018/5/17.
 */

public class PaginationInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        // TODO pagination
        return chain.proceed(chain.request());
    }
}
