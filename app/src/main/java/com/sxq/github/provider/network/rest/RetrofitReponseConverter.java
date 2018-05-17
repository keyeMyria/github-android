package com.sxq.github.provider.network.rest;

import com.google.gson.Gson;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.annotation.Nullable;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by shixiaoqiang01 on 2018/5/17.
 */

public class RetrofitReponseConverter extends Converter.Factory {
    private Gson mGson;

    public RetrofitReponseConverter(Gson mGson) {
        this.mGson = mGson;
    }

    @Nullable
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        try {
            if (type == String.class) {
                return new StringReponseConverter();
            }
            return GsonConverterFactory.create(mGson).responseBodyConverter(type, annotations, retrofit);
        } catch (OutOfMemoryError oom) {
            return null;
        }
    }

    @Nullable
    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return GsonConverterFactory.create(mGson).requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit);
    }

    private static class StringReponseConverter implements Converter<ResponseBody, String> {
        @Override
        public String convert(ResponseBody value) throws IOException {
            return value.string();
        }
    }
}
