package com.sxq.github.data.source.interfaze;

import android.support.annotation.NonNull;


import java.util.List;

import github.GetPinnedReposQuery;
import io.reactivex.Observable;

/**
 * Created by shixiaoqiang01 on 2018/5/18.
 */

public interface UserDataSource {

    public Observable<List<GetPinnedReposQuery.Node>> getProfileOverViewUiModel(@NonNull String login);
}
