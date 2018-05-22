package com.sxq.github;

import android.os.Bundle;

import com.sxq.github.data.model.login.Login;
import com.sxq.github.ui.base.BaseActivity;
import com.sxq.github.ui.modules.profile.followers.ProfileFollowerFragment;
import com.sxq.github.utils.ActivityUtil;

/**
 * Created by shixiaoqiang01 on 2018/5/10.
 */


public class MainActivity extends BaseActivity {

    @Override
    protected int activityLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ProfileFollowerFragment profileFollowerFragment =
                (ProfileFollowerFragment)getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if(profileFollowerFragment==null){
            profileFollowerFragment = ProfileFollowerFragment.newInstance(Login.getCurrentUser().getLogin());
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), profileFollowerFragment, R.id.contentFrame);
        }
    }
}
