package com.sxq.github.provider.network.interceptor;

import com.sxq.github.App;
import com.sxq.github.utils.NetworkUtil;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import timber.log.Timber;

public class RewriteCacheControlInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (!NetworkUtil.isNetworkAvailable(App.getInstance().getApplicationContext())) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
            Timber.d("Network unavailable, try to use cache");
        }

        Response response = chain.proceed(request);
        if (NetworkUtil.isNetworkAvailable(App.getInstance().getApplicationContext())) {
            String cacheControl = request.cacheControl().toString();
            return response.newBuilder()
                    .header("Cache-Control", cacheControl)
                    .removeHeader("Pragma")
                    .build();
        } else {
            return response.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=2419200")
                    .removeHeader("Pragma")
                    .build();
        }
    }
}
