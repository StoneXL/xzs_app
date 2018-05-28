package com.yxld.xzs.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.yxld.xzs.activity.workbench.RimDeliveryFragment;
import com.yxld.xzs.activity.workbench.RimRobFragment;
import com.yxld.xzs.activity.workbench.RimSendFragment;

/**
 * Created by William on 2018/1/3.
 */

public class RimFragmentPagerAdapter extends FragmentStatePagerAdapter {

    private String[] mTitles;

    public RimFragmentPagerAdapter(FragmentManager fm, String[] mTitles) {
        super(fm);
        this.mTitles = mTitles;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new RimRobFragment();
        } else if (position == 1) {
            return new RimDeliveryFragment();
        } else{
            return new RimSendFragment();
        }
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
