package com.sxq.github.data.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.sxq.github.R;
import com.sxq.github.ui.modules.profile.feeds.ProfileFeedsFragment;
import com.sxq.github.ui.modules.profile.followers.ProfileFollowerFragment;
import com.sxq.github.ui.modules.profile.following.ProfileFollowingFragment;
import com.sxq.github.ui.modules.profile.overview.ProfileOverViewFragment;
import com.sxq.github.ui.modules.profile.repos.ProfileReposFragment;
import com.sxq.github.ui.modules.profile.starred.ProfileStarredFragment;
import com.sxq.github.ui.modules.repos.commit.ReposCommitFragment;
import com.sxq.github.ui.modules.repos.contributors.ReposContributorsFragment;
import com.sxq.github.ui.modules.repos.files.ReposFilesFragment;
import com.sxq.github.ui.modules.repos.release.ReposReleasesFragment;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by shixiaoqiang01 on 2018/5/19.
 */

public class FragmentPagerAdapterModel {

    private String mTitle;

    private Fragment mFragment;

    private String mKey;

    public FragmentPagerAdapterModel(String title, Fragment fragment) {
        this(title, fragment, null);
    }

    public FragmentPagerAdapterModel(String title, Fragment fragment, String key) {
        mTitle = title;
        mFragment = fragment;
        mKey = key;
    }

    public static List<FragmentPagerAdapterModel> buildForProfile(@NonNull Context context, @NonNull String login) {
        return Stream.of(
                new FragmentPagerAdapterModel(context.getString(R.string.overview), ProfileOverViewFragment.newInstance(login)),
                new FragmentPagerAdapterModel(context.getString(R.string.feeds), ProfileFeedsFragment.newInstance(login)),
                new FragmentPagerAdapterModel(context.getString(R.string.repos), ProfileReposFragment.newInstance(login)),
                new FragmentPagerAdapterModel(context.getString(R.string.starred), ProfileStarredFragment.newInstance(login)),
                new FragmentPagerAdapterModel(context.getString(R.string.followers), ProfileFollowerFragment.newInstance(login)),
                new FragmentPagerAdapterModel(context.getString(R.string.following), ProfileFollowingFragment.newInstance(login)))
                .collect(Collectors.toList());
    }

    public static List<FragmentPagerAdapterModel> buildForRepository(@NonNull Context context, @NonNull String login, @NonNull String reposName) {
        return Stream.of(
                new FragmentPagerAdapterModel(context.getString(R.string.files), ReposFilesFragment.newInstance()),
                new FragmentPagerAdapterModel(context.getString(R.string.commits), ReposCommitFragment.newInstance(login, reposName)),
                new FragmentPagerAdapterModel(context.getString(R.string.releases), ReposReleasesFragment.newInstance()),
                new FragmentPagerAdapterModel(context.getString(R.string.contributors), ReposContributorsFragment.newInstance(login, reposName)))
                .collect(Collectors.toList());
    }


    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Fragment getFragment() {
        return mFragment;
    }

    public void setFragment(Fragment fragment) {
        mFragment = fragment;
    }

    public String getKey() {
        return mKey;
    }

    public void setKey(String key) {
        mKey = key;
    }
}


