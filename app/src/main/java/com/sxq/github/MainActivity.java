package com.sxq.github;
import android.os.Bundle;

import com.sxq.github.data.model.login.Login;
import com.sxq.github.ui.base.BaseActivity;
import com.sxq.github.ui.modules.repos.ReposActivity;
import com.sxq.github.ui.modules.user.UserActivity;

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
//        ReposActivity.startActivity(this, Login.getCurrentUser().getLogin(), "s-xq");
        ReposActivity.startActivity(this, "s-xq", "github-android");
//        ReposActivity.startActivity(this, "JakeWharton", "Retrofit");
        UserActivity.startActivity(this, "JakeWharton");
        UserActivity.startActivity(this, "s-xq");
    }
}
