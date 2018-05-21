package com.sxq.github;

import android.os.Bundle;

import com.sxq.github.data.model.login.Login;
import com.sxq.github.ui.base.BaseActivity;
import com.sxq.github.ui.modules.profile.overview.ProfileOverViewFragment;
import com.sxq.github.ui.modules.user.UserActivity;
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
        ProfileOverViewFragment profileOverViewFragment =
                (ProfileOverViewFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (profileOverViewFragment == null) {
            profileOverViewFragment = ProfileOverViewFragment.newInstance(Login.getCurrentUser().getLogin());
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), profileOverViewFragment, R.id.contentFrame);
        }
        UserActivity.startActivity(this, Login.getCurrentUser().getLogin());
    }
}
