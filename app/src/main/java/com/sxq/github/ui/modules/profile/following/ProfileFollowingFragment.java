package com.sxq.github.ui.modules.profile.following;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.sxq.github.R;
import com.sxq.github.ui.base.BaseFragment;

/**
 * Created by shixiaoqiang01 on 2018/5/19.
 */

public class ProfileFollowingFragment extends BaseFragment {

    private static String TAG_LOGIN = "tag_login";

    @NonNull
    private String mLogin;

    public static ProfileFollowingFragment newInstance(String login) {
        Bundle args = new Bundle();
        args.putString(TAG_LOGIN, login);
        ProfileFollowingFragment fragment = new ProfileFollowingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int fragmentLayout() {
        return R.layout.fragment_profile_following;
    }

    @Override
    protected void onFragmentCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            mLogin = getArguments().getString(TAG_LOGIN);
        }
    }

}
