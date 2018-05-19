package com.sxq.github.utils;

import android.support.annotation.NonNull;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by shixiaoqiang01 on 2018/5/18.
 */

public class RxUtil {
    public static <T> Observable<T> getObservable(@NonNull Observable<T> observable) {
        return observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public static <T> Observable<T> safeObserable(@NonNull Observable<T> observable) {
        return getObservable(observable)
                .doOnError(Throwable::printStackTrace);
    }
}
