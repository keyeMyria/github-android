package com.sxq.github.ui.modules.repos.release;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.sxq.github.R;
import com.sxq.github.ui.base.BaseFragment;

public class ReposReleasesFragment extends BaseFragment {

    public static ReposReleasesFragment newInstance() {
        Bundle args = new Bundle();
        ReposReleasesFragment fragment = new ReposReleasesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int fragmentLayout() {
        return R.layout.fragment_repos_releases;
    }

    @Override
    protected void onFragmentCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }
}



