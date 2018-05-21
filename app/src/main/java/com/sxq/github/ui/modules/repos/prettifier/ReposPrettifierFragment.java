package com.sxq.github.ui.modules.repos.prettifier;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.sxq.github.R;
import com.sxq.github.ui.base.BaseFragment;

public class ReposPrettifierFragment extends BaseFragment {

    public static ReposPrettifierFragment newInstance() {
        Bundle args = new Bundle();
        ReposPrettifierFragment fragment = new ReposPrettifierFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int fragmentLayout() {
        return R.layout.fragment_repos_prettifier;
    }

    @Override
    protected void onFragmentCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }
}
