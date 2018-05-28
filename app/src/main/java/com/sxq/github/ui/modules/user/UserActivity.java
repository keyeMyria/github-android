package com.sxq.github.ui.modules.user;

import android.app.ActivityManager;
import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.sxq.github.R;
import com.sxq.github.data.model.FragmentPagerAdapterModel;
import com.sxq.github.data.model.login.Login;
import com.sxq.github.ui.adapter.FragmentPagerAdapter;
import com.sxq.github.ui.base.BaseActivity;
import com.sxq.github.utils.ViewHelper;


import butterknife.BindView;
import timber.log.Timber;

/**
 * Created by shixiaoqiang01 on 2018/5/19.
 */

public class UserActivity extends BaseActivity {

    private static String TAG_INDEX = "tag_index";
    private static String TAG_LOGIN = "tag_login";


    @BindView(R.id.tabs)
    TabLayout mTabLayout;

    @BindView(R.id.fab)
    protected FloatingActionButton mFloatingActionButton;

    @BindView(R.id.viewPager)
    protected ViewPager mViewPager;

    @BindView(R.id.toolbar)
    protected Toolbar mToolbar;

    private int mIndex;
    private String mLogin;

    public static void startActivity(@NonNull Context context, @NonNull String login) {
        context.startActivity(createIntent(context, login));
    }

    private static Intent createIntent(@NonNull Context context, @NonNull String login) {
        Intent intent = new Intent(context, UserActivity.class);
        intent.putExtra(TAG_LOGIN, login);
        if (context instanceof Service || context instanceof Application) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        return intent;
    }

    @Override
    protected int activityLayout() {
        return R.layout.activity_user;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            mIndex = savedInstanceState.getInt(TAG_INDEX);
            mLogin = savedInstanceState.getString(TAG_LOGIN);
        }

        mLogin = getIntent().getStringExtra(TAG_LOGIN);

        setSupportActionBar(mToolbar);

        FragmentPagerAdapter fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager(),
                FragmentPagerAdapterModel.buildForProfile(this, mLogin));
        mViewPager.setAdapter(fragmentPagerAdapter);
        mViewPager.setOffscreenPageLimit(6);
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.setupWithViewPager(mViewPager);
        if (savedInstanceState == null) {
            mViewPager.setCurrentItem(0);
        }
        mTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager) {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                super.onTabSelected(tab);
                hideShowFab(tab.getPosition());
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                hideShowFab(position);
            }
        });

        hideShowFab(mViewPager.getCurrentItem());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt(TAG_INDEX, mIndex);
        savedInstanceState.putString(TAG_LOGIN, mLogin);
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void hideShowFab(int position) {
        mFloatingActionButton.show();
    }
}
