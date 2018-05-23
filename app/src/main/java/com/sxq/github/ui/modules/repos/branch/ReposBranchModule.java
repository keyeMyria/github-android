package com.sxq.github.ui.modules.repos.branch;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;

import com.sxq.github.data.source.interfaze.ReposSourceRepository;
import com.sxq.github.data.source.local.ReposLocalDataSource;
import com.sxq.github.data.source.remote.ReposRemoteDataSource;

public class ReposBranchModule {

    public interface BranchSelectedListener {
        void onBranchSelected(String selectedBranch);
    }

    public static ReposBranchViewModel createViewModel(@Nullable String login,
                                                       @Nullable String reposName) {
        return new ReposBranchViewModel(ReposSourceRepository.getInstance(
                ReposLocalDataSource.getInstance(),
                ReposRemoteDataSource.getInstance()),
                new ReposBranchNavigator(),
                login,
                reposName);
    }

    public static void launch(@NonNull FragmentManager fragmentManager, @NonNull String login, @NonNull String reposName, @NonNull BranchSelectedListener branchSelectedListener) {
        ReposBranchFragment reposBranchFragment = ReposBranchFragment.newInstance(login, reposName);
        reposBranchFragment.setBranchSelectedListener(branchSelectedListener);
        reposBranchFragment.show(fragmentManager, ReposBranchFragment.TAG);
    }
}
