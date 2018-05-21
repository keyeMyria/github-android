package com.sxq.github.ui.modules.repos.commit;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.sxq.github.R;
import com.sxq.github.ui.base.BaseFragment;

public class ReposCommitFragment extends BaseFragment {

    public static ReposCommitFragment newInstance() {
        Bundle args = new Bundle();
        ReposCommitFragment fragment = new ReposCommitFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int fragmentLayout() {
        return R.layout.fragment_repos_commit;
    }

    @Override
    protected void onFragmentCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }
}



