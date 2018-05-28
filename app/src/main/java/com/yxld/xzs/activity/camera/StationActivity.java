package com.yxld.xzs.activity.camera;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.socks.library.KLog;
import com.yxld.xzs.R;
import com.yxld.xzs.base.BaseActivity;
import com.yxld.xzs.contain.Contains;
import com.yxld.xzs.entity.AppCameraList;
import com.zaaach.toprightmenu.TopRightMenu;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：Android on 2017/9/13
 * 邮箱：365941593@qq.com
 * 描述：
 */

public class StationActivity extends BaseActivity {

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private TopRightMenu mTopRightMenu;

    public static final String ENTER_TYPE = "enter_type";//1居家安防 2岗位监控

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    ArrayList<String> titles = new ArrayList<>();

    //// TODO: 2017/11/17 修改页面
    protected void initView() {
        setContentView(R.layout.activity_station);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final int type = getIntent().getIntExtra(ENTER_TYPE, 1);
        tabLayout.setVisibility(View.GONE);
        if (type == 1) {
            titles.add("报警器");
            toolbar.setTitle("新增主机");
        } else {
            titles.add("摄像头");
        }
        FragmentPagerAdapter mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if (type == 1) {
                    // AlarmListFragment fragment = new AlarmListFragment();
                    AddHostFragment fragment = new AddHostFragment();
                    Bundle bundle = new Bundle();
                    fragment.setArguments(bundle);
                    return fragment;
                } else {
                    DeviceListFragment fragment = new DeviceListFragment();
                    Bundle bundle = new Bundle();
                    fragment.setArguments(bundle);
                    return fragment;
                }
            }

            @Override
            public int getCount() {
                return titles.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles.get(position);
            }

        };
        viewPager.setAdapter(mAdapter);
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager);
    }


    Menu menu;
    boolean isMenu = false;

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        this.menu = menu;
//        if (Contains.appLogin.getAdminXiangmuId() == -1 || (camear != null && camear.getData() != null)) {
//            if (!isMenu) {
//                getMenuInflater().inflate(R.menu.menu_camera_device, menu);
//                isMenu = true;
//            }
//            return true;
//        } else {
//            return true;
//        }
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == android.R.id.home) {
//            finish();
//            System.gc();
//        }
//        KLog.i(Contains.appLogin.getAdminXiangmuId());
////// TODO: 2017/11/29 取消了权限设置
//        View tianjia = findViewById(R.id.add);
//        switch (item.getItemId()) {
//            case R.id.add:
//                mTopRightMenu = new TopRightMenu(StationActivity.this);
//                mTopRightMenu
//                        .setHeight(RecyclerView.LayoutParams.WRAP_CONTENT)     //默认高度480
//                        .setWidth(RecyclerView.LayoutParams.WRAP_CONTENT)      //默认宽度wrap_content
////                        .showIcon(true)     //显示菜单图标，默认为true
//                        .dimBackground(true)           //背景变暗，默认为true
//                        .needAnimationStyle(true)   //显示动画，默认为true
//                        .setAnimationStyle(R.style.TRM_ANIM_STYLE)  //默认为R.style.TRM_ANIM_STYLE
//                        .addMenuItem(new com.zaaach.toprightmenu.MenuItem(R.mipmap.icon_peiwang, "设备配网"))
//                        .addMenuItem(new com.zaaach.toprightmenu.MenuItem(R.mipmap.icon_add_device, "添加设备"))
//                        .setOnMenuItemClickListener(new TopRightMenu.OnMenuItemClickListener() {
//                            @Override
//                            public void onMenuItemClick(int position) {
//                                switch (position) {
//                                    case 0:
//                                        Intent config = new Intent(StationActivity.this, CameraConfigActivity.class);
//                                        startActivity(config);
//                                        break;
//                                    case 1:
//                                        Intent add = new Intent(StationActivity.this, CameraAddActivity.class);
//                                        add.putExtra("ishasContactId", false);
//                                        startActivity(add);
//                                        break;
//                                    default:
//                                        break;
//                                }
//                            }
//                        })
//                        .showAsDropDown(tianjia, 0, 0);
//                break;
//            default:
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
