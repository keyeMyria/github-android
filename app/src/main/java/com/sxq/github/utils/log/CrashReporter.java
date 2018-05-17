package com.sxq.github.utils.log;

/**
 * Created by shixiaoqiang01 on 2018/5/17.
 */

public class CrashReporter {

    private CrashReporter() {
        throw new AssertionError("No instances.");
    }

    public static void log(int priority, String tag, String message) {
        // TODO add log entry to circular buffer
    }

    public static void logWarning(Throwable t) {
        // TODO report non-warning error.
    }

    public static void logError(Throwable t) {
        // TODO report non-fatal error.
    }
}
