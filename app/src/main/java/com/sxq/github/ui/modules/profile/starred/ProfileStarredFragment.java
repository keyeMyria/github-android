package com.sxq.github.ui.modules.profile.starred;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sxq.github.R;

/**
 * Created by shixiaoqiang01 on 2018/5/19.
 */

public class ProfileStarredFragment extends Fragment {

    private static String TAG_LOGIN = "tag_login";

    @NonNull
    private String mLogin;

    public static ProfileStarredFragment newInstance(String login) {

        Bundle args = new Bundle();
        args.putString(TAG_LOGIN, login);
        ProfileStarredFragment fragment = new ProfileStarredFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getArguments() != null) {
            mLogin = getArguments().getString(TAG_LOGIN);
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_starred, container, false);
    }

}
