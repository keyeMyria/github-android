package com.sxq.github.utils;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.sxq.github.App;

/**
 * Helper of the {@link SharedPreferences}
 */
public class PrefHelper {

    public static <T> void set(@NonNull String key, @NonNull T value) {
        if (InputHelper.isEmpty(key)) {
            throw new NullPointerException("Key must not be null! (key = " + key + "), (value = " + value + ")");
        }
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(App.getInstance()).edit();
        if (InputHelper.isEmpty(value)) {
            clearKey(key);
            return;
        }
        if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else {
            editor.putString(key, value.toString());
        }
        editor.commit();
    }

    @Nullable
    public static String getString(@NonNull String key) {
        return PreferenceManager.getDefaultSharedPreferences(App.getInstance()).getString(key, null);
    }

    public static boolean getBoolean(@NonNull String key) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(App.getInstance());
        return preferences.getAll().get(key) instanceof Boolean && preferences.getBoolean(key, false);
    }

    public static int getInt(@NonNull String key) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(App.getInstance());
        return preferences.getAll().get(key) instanceof Integer ? preferences.getInt(key, 0) : -1;
    }

    public static long getLong(@NonNull String key) {
        return PreferenceManager.getDefaultSharedPreferences(App.getInstance()).getLong(key, 0);
    }


    public static float getFloat(@NonNull String key) {
        return PreferenceManager.getDefaultSharedPreferences(App.getInstance()).getFloat(key, 0);
    }

    public static void clearKey(@NonNull String key) {
        PreferenceManager.getDefaultSharedPreferences(App.getInstance()).edit().remove(key).apply();
    }

}
