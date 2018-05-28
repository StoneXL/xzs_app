package com.yxld.xzs.activity.workbench;

import android.os.Bundle;
import android.support.design.widget.TabLayout;

import com.yxld.xzs.R;
import com.yxld.xzs.adapter.RimFragmentPagerAdapter;
import com.yxld.xzs.base.BaseActivity;
import com.yxld.xzs.view.AudioViewPage;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by William on 2018/1/3.
 */

public class RimActivity extends BaseActivity {
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    AudioViewPage viewPager;

    private TabLayout.Tab Detail, Process, List;
    private RimFragmentPagerAdapter mAdapter;
    private String[] mTitles = new String[]{"待抢单", "待取货", "待送达"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewPager.setNoScroll(false);
        mAdapter = new RimFragmentPagerAdapter(getSupportFragmentManager(), mTitles);
        viewPager.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
//        Detail = tabLayout.getTabAt(0);
//        Process = tabLayout.getTabAt(1);
//        List = tabLayout.getTabAt(2);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setCurrentItem(0);

    }

    public AudioViewPage getViewPager() {
        return viewPager;
    }
}
