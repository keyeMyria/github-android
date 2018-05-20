package com.sxq.github;

import android.app.Application;
import android.support.annotation.NonNull;

import com.sxq.github.utils.TypeFaceHelper;
import com.sxq.github.utils.log.CrashHandleTree;

import timber.log.Timber;

/**
 * Created by shixiaoqiang01 on 2018/5/17.
 */

public class App extends Application {

    private static App instance;

    @NonNull
    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        init();
    }

    private void init() {
        initFont();
        initLogging();
    }

    private void initFont() {
        TypeFaceHelper.generateTypeface(this);
    }

    private void initLogging() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new CrashHandleTree());
        }
    }


}
