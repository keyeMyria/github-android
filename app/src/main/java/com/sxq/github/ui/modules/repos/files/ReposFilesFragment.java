package com.sxq.github.ui.modules.repos.files;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.sxq.github.R;
import com.sxq.github.ui.base.BaseFragment;

public class ReposFilesFragment extends BaseFragment {

    public static ReposFilesFragment newInstance() {
        Bundle args = new Bundle();
        ReposFilesFragment fragment = new ReposFilesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int fragmentLayout() {
        return R.layout.fragment_repos_files;
    }

    @Override
    protected void onFragmentCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }
}
