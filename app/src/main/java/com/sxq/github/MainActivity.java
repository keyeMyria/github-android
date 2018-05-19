package com.sxq.github;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sxq.github.ui.modules.profile.overview.ProfileOverViewFragment;
import com.sxq.github.utils.ActivityUtil;

/**
 * Created by shixiaoqiang01 on 2018/5/10.
 */


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ProfileOverViewFragment profileOverViewFragment =
                (ProfileOverViewFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (profileOverViewFragment == null) {
            profileOverViewFragment = ProfileOverViewFragment.newInstance();
            ActivityUtil.addFragmentToActivity(getSupportFragmentManager(), profileOverViewFragment, R.id.contentFrame);
        }
    }
}
