package com.sxq.github.utils.log;

import android.util.Log;

import timber.log.Timber;

/**
 * Created by shixiaoqiang01 on 2018/5/17.
 * <p>
 * A tree which logs important information for crash reporting.
 */
public class CrashHandleTree extends Timber.Tree {

    @Override
    protected void log(int priority, String tag, String message, Throwable t) {
        if (priority == Log.VERBOSE || priority == Log.DEBUG || priority == Log.INFO) {
            return;
        }
        CrashReporter.log(priority, tag, message);
        if (t != null) {
            if (priority == Log.WARN) {
                CrashReporter.logWarning(t);
            } else if (priority == Log.ERROR) {
                CrashReporter.logError(t);
            }
        }
    }
}
