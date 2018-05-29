package com.sxq.github;

import android.os.Bundle;
import android.util.Log;

import com.sxq.github.data.model.login.Login;
import com.sxq.github.ui.base.BaseActivity;
import com.sxq.github.ui.modules.login.LoginFragment;
import com.sxq.github.ui.modules.user.UserActivity;
import com.sxq.github.utils.ActivityUtil;
import com.sxq.github.utils.InputHelper;
import com.sxq.github.utils.PrefGetter;

import timber.log.Timber;

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
//        ReposActivity.startActivity(this, "s-xq", "github-android");
//        ReposActivity.startActivity(this, "JakeWharton", "Retrofit");
//        UserActivity.startActivity(this, "JakeWharton");
//        UserActivity.startActivity(this, "s-xq");
        if (InputHelper.isEmpty(PrefGetter.getCurrentUserName()) || InputHelper.isEmpty(PrefGetter.getCurrentUserToken())) {
            LoginFragment loginFragment = (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
            if (loginFragment == null) {
                loginFragment = LoginFragment.newInstance();
                ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), loginFragment, R.id.contentFrame);
            }
        } else {
            Timber.d("Current user: %s", (InputHelper.isEmpty(Login.getCurrentUser().getLogin())) ? "" :
                    Login.getCurrentUser().getLogin());
            UserActivity.startActivity(this, Login.getCurrentUser().getLogin());
            finish();
        }
    }
}
