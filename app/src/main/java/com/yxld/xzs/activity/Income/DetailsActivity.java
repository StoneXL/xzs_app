package com.yxld.xzs.activity.Income;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.yxld.xzs.R;
import com.yxld.xzs.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yishangfei on 2016/10/11 0011.
 * 明细
 */
public class DetailsActivity extends BaseActivity {

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    private TabLayout.Tab Distribution,Activities,Withdraw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewPager.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(viewPager);
        Distribution = tabLayout.getTabAt(0);
        Activities = tabLayout.getTabAt(1);
        Withdraw = tabLayout.getTabAt(2);
    }


    FragmentPagerAdapter mAdapter=new FragmentPagerAdapter(getSupportFragmentManager()) {

        private String[] mTitles = new String[]{"收入明细", "活动明细","提现明细"};
        @Override
        public Fragment getItem(int position) {
            if (position == 1) {
                return new MovableFragment();
            }else if (position == 2){
                return new PoseFragment();
            }
            return new ProfitFragment();
        }

        @Override
        public int getCount() {
            return mTitles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

    };
}
