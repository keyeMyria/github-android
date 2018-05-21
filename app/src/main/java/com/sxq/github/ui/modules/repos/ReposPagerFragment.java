package com.sxq.github.ui.modules.repos;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.sxq.github.R;
import com.sxq.github.data.model.FragmentPagerAdapterModel;
import com.sxq.github.ui.adapter.FragmentPagerAdapter;
import com.sxq.github.ui.base.BaseFragment;

import butterknife.BindView;

public class ReposPagerFragment extends BaseFragment {

    public static String TAG_LOGIN = "tag_login";
    public static String TAG_REPOS_NAME = "tag_repos_name";

    @BindView(R.id.tabs)
    TabLayout mTabs;
    @BindView(R.id.pager)
    ViewPager mViewPager;

    @NonNull
    private String mLogin;
    @NonNull
    private String mReposName;

    public static ReposPagerFragment newInstance(@NonNull String login, @NonNull String name) {
        Bundle args = new Bundle();
        ReposPagerFragment fragment = new ReposPagerFragment();
        args.putString(TAG_LOGIN, login);
        args.putString(TAG_REPOS_NAME, name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int fragmentLayout() {
        return R.layout.fragment_repos_pager;
    }

    @Override
    protected void onFragmentCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            mLogin = getArguments().getString(TAG_LOGIN);
            mReposName = getArguments().getString(TAG_REPOS_NAME);
        }

        onRestoreData(savedInstanceState);

        FragmentPagerAdapter fragmentPagerAdapter = new FragmentPagerAdapter(getFragmentManager(),
                FragmentPagerAdapterModel.buildForRepository(getContext(), mLogin, mReposName));
        mViewPager.setAdapter(fragmentPagerAdapter);
        mTabs.setTabGravity(TabLayout.GRAVITY_FILL);
        mTabs.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabs.setupWithViewPager(mViewPager);
        if (savedInstanceState == null) {
            mViewPager.setCurrentItem(0);
        }
        mTabs.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager) {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                super.onTabSelected(tab);
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(TAG_LOGIN, mLogin);
        outState.putString(TAG_REPOS_NAME, mReposName);
        super.onSaveInstanceState(outState);
    }


    private void onRestoreData(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mLogin = savedInstanceState.getString(TAG_LOGIN);
            mReposName = savedInstanceState.getString(TAG_REPOS_NAME);
        }
    }
}