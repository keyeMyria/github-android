package com.sxq.github.utils;

import android.support.annotation.NonNull;
import android.text.format.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import timber.log.Timber;

public class ParseDateFormat {

    private static final String GITHUB_DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    @NonNull
    public static CharSequence parseTimeAgo(Date parsedDate) {
        if (parsedDate != null) {
            long now = System.currentTimeMillis();
            return DateUtils.getRelativeTimeSpanString(parsedDate.getTime(), now, DateUtils.SECOND_IN_MILLIS);
        }
        return "N/A";
    }

    public static Date getGithubDateFrom(@NonNull String date) {
        try {
            return new SimpleDateFormat(GITHUB_DATE_PATTERN).parse(date);
        } catch (ParseException ex) {
            Timber.e(ex);
        }
        return null;
    }
}
