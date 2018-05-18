package com.sxq.github.data.source.local;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.sxq.github.data.source.interfaze.UserDataSource;

import java.util.List;

import github.GetPinnedReposQuery;
import io.reactivex.Observable;

/**
 * Created by shixiaoqiang01 on 2018/5/18.
 */

public class UserLocalDataSource implements UserDataSource {

    @Nullable
    private static UserLocalDataSource INSTANCE = null;


    private UserLocalDataSource() {

    }

    public static UserLocalDataSource getInstance() {
        if (INSTANCE == null) {
            synchronized (UserLocalDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new UserLocalDataSource();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public Observable<List<GetPinnedReposQuery.Node>> getProfileOverViewUiModel(@NonNull String login) {
        return null;
    }
}
