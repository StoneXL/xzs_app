package com.yxld.xzs.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @author Yuan.Y.Q
 * @Date 2017/7/31.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragments;
    private List<String> mTitles;
    public MyFragmentPagerAdapter(FragmentManager fm,List<Fragment> fragments,List<String> titles) {
        super(fm);
        mFragments = fragments;
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return Math.min(mFragments.size(),mTitles.size());
    }


//    @Override
//    public CharSequence getPageTitle(int position) {
//        return mTitles.get(position);
//    }

}
