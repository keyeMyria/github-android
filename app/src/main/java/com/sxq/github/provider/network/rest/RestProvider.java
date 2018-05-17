package com.sxq.github.provider.network.rest;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sxq.github.BuildConfig;
import com.sxq.github.provider.network.OkHttpProvider;

import java.lang.reflect.Modifier;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by shixiaoqiang01 on 2018/5/17.
 */

public class RestProvider {

    public final static Gson gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .disableHtmlEscaping()
            .setPrettyPrinting()
            .create();

    private static Retrofit providRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.REST_URL)
                .client(OkHttpProvider.provideOkHttpClient())
                .addConverterFactory(new RetrofitReponseConverter(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public static void downloadFile(@NonNull Context context, @NonNull String url) {
        // TODO
    }

}