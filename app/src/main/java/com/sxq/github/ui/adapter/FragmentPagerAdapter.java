package com.sxq.github.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.sxq.github.data.model.FragmentPagerAdapterModel;

import java.util.List;

public class FragmentPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {

    private List<FragmentPagerAdapterModel> mFragmentPagerAdapterModelList = null;

    public FragmentPagerAdapter(FragmentManager fm, @NonNull List<FragmentPagerAdapterModel> fragmentPagerAdapterModelList) {
        super(fm);
        mFragmentPagerAdapterModelList = fragmentPagerAdapterModelList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentPagerAdapterModelList.get(position).getFragment();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentPagerAdapterModelList.get(position).getTitle();
    }

    @Override
    public float getPageWidth(int position) {
        return super.getPageWidth(position);
    }

    @Override
    public int getCount() {
        return mFragmentPagerAdapterModelList.size();
    }
}
