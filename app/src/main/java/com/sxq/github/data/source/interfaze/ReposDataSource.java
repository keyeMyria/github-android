package com.sxq.github.data.source.interfaze;

import android.support.annotation.NonNull;

import java.util.List;

import github.repos.GetBranchesQuery;
import io.reactivex.Observable;

public interface ReposDataSource {
    Observable<List<GetBranchesQuery.Node>> getBranches(@NonNull String owner, @NonNull String reposName);
}
