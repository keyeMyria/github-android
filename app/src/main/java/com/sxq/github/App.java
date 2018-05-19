package com.sxq.github;

import android.app.Application;

import com.sxq.github.utils.TypeFaceHelper;
import com.sxq.github.utils.log.CrashHandleTree;

import timber.log.Timber;

/**
 * Created by shixiaoqiang01 on 2018/5/17.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        TypeFaceHelper.generateTypeface(this);
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new CrashHandleTree());
        }
    }
}
