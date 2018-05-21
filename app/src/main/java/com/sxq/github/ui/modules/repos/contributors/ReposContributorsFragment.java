package com.sxq.github.ui.modules.repos.contributors;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.sxq.github.R;
import com.sxq.github.ui.base.BaseFragment;

public class ReposContributorsFragment extends BaseFragment {

    public static ReposContributorsFragment newInstance() {
        Bundle args = new Bundle();
        ReposContributorsFragment fragment = new ReposContributorsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int fragmentLayout() {
        return R.layout.fragment_repos_contributors;
    }

    @Override
    protected void onFragmentCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }
}

