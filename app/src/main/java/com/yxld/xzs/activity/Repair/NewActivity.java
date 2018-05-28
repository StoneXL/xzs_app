package com.yxld.xzs.activity.Repair;
////////////////////////////////////////////////////////////////////
//                          _ooOoo_                               //
//                         o8888888o                              //
//                         88" . "88                              //
//                         (| ^_^ |)                              //
//                         O\  =  /O                              //
//                      ____/`---'\____                           //
//                    .'  \\|     |//  `.                         //
//                   /  \\|||  :  |||//  \                        //
//                  /  _||||| -:- |||||-  \                       //
//                  |   | \\\  -  /// |   |                       //
//                  | \_|  ''\---/''  |   |                       //
//                  \  .-\__  `-`  ___/-. /                       //
//                ___`. .'  /--.--\  `. . ___                     //
//              ."" '<  `.___\_<|>_/___.'  >'"".                  //
//            | | :  `- \`.;`\ _ /`;.`/ - ` : | |                 //
//            \  \ `-.   \_ __\ /__ _/   .-` /  /                 //
//      ========`-.____`-.___\_____/___.-`____.-'========         //
//                           `=---='                              //
//      ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^        //
//         佛祖保佑       永无BUG     永不修改                  //
////////////////////////////////////////////////////////////////////

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import com.yxld.xzs.R;
import com.yxld.xzs.base.BaseActivity;
import com.yxld.xzs.view.AudioViewPage;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yishangfei on 2017/3/23 0023.
 * 个人主页：http://yishangfei.me
 * Github:https://github.com/yishangfei
 */
public class NewActivity extends BaseActivity {
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    AudioViewPage viewPager;

    private TabLayout.Tab Detail, Process, List;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(viewPager);
        Detail = tabLayout.getTabAt(0);
        Process = tabLayout.getTabAt(1);
        List = tabLayout.getTabAt(2);
    }


    FragmentPagerAdapter mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

    //  private String[] mTitles = new String[]{"报修详情", "维修详情", "物料清单"};
       private String[] mTitles = new String[]{"报修详情", "维修详情"};

        @Override
        public Fragment getItem(int position) {
            if (position == 1) {
                return new TreatFragment();
//            } else if (position == 2) {
//                return new ListFragment();
          }
            return new DetailFragment();
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
