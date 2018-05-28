package com.yxld.xzs.activity.index;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentTabHost;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.socks.library.KLog;
import com.tbruyelle.rxpermissions.Permission;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.yxld.xzs.R;
import com.yxld.xzs.activity.camera.ScanandActivity;
import com.yxld.xzs.base.BaseActivity;
import com.yxld.xzs.base.DemoApplicationLike;
import com.yxld.xzs.contain.Contains;
import com.yxld.xzs.entity.TabDataBean;
import com.yxld.xzs.utils.StringUitl;
import com.zhy.autolayout.AutoFrameLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;

/**
 * @author xlei
 * @Date 2017/11/14.
 */

public class HomeActivity extends BaseActivity {
    @BindView(R.id.realtabcontent)
    AutoFrameLayout realtabcontent;
    @BindView(android.R.id.tabcontent)
    AutoFrameLayout tabcontent;
    @BindView(android.R.id.tabhost)
    FragmentTabHost tabhost;
    private ArrayList<TabDataBean> tabDataList = new ArrayList<>();
    private MenuItem menuItem;

    /**
     * 请求码
     */
    public final static int REQUESTCODE_ROB = 100;
    public final static int REQUESTCODE_DELIVERY = 101;
    public final static int REQUESTCODE_SEND = 102;
    public final static int REQUESTCODE_APPROVE = 103;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        toolbar.setTitle("首页");
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        //fir更新下载
        //UpdateFunGO.init(this);
        initTabHost();
        initAliPush();
        initTinker();
        DemoApplicationLike.getApp().mAppActivityManager.addActivity(this);
        DemoApplicationLike.getApp().initSDK();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("uuid", Contains.uuid);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Contains.uuid = savedInstanceState.getString("uuid");
    }

    //微信Tinker热修复
    private void initTinker() {
        TinkerInstaller.onReceiveUpgradePatch(getApplicationContext(),
                Environment.getExternalStorageDirectory().getAbsolutePath() + "/patch_signed.apk");
    }

    //登陆失效
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onOutLogin(String outlogin) {
        KLog.i("homeActivity" + outlogin);
        if (outlogin.equals("退出登录")) {
            exitLogin();
        }
    }

    private void initAliPush() {
        //阿里设置别名
        PushServiceFactory.getCloudPushService().addAlias(StringUitl.getDeviceId(this), new
                CommonCallback() {
                    @Override
                    public void onSuccess(String s) {
                        KLog.i("阿里绑定设置别名成功 " + StringUitl.getDeviceId(HomeActivity.this) + "s:" +
                                s);
                    }

                    @Override
                    public void onFailed(String s, String s1) {

                    }
                });
        //阿里绑定手机号
        PushServiceFactory.getCloudPushService().bindAccount(Contains.appLogin.getAdminId() + "",
                new CommonCallback() {
                    @Override
                    public void onSuccess(String s) {
                        KLog.i("阿里绑定手机号成功： " + Contains.appLogin.getAdminId() + "s:" + s);
                    }

                    @Override
                    public void onFailed(String s, String s1) {

                    }
                });
        PushServiceFactory.getCloudPushService().listAliases(new CommonCallback() {
            @Override
            public void onSuccess(String s) {
                KLog.i("阿里别名集合" + s);
            }

            @Override
            public void onFailed(String s, String s1) {

            }
        });
    }

    private void initTabHost() {
        //初始化fTabHost, 第三个参数为内容容器
        tabhost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        /*初始化数据源*/
        TabDataBean tabHome = new TabDataBean("首页", R.drawable.home_index_selector, IndexFragment
                .class);
        TabDataBean tabHot = new TabDataBean("工作台", R.drawable.home_work_selector,
                WorkbenchFragment.class);
        TabDataBean tabCategory = new TabDataBean("个人中心", R.drawable.home_per_selector,
                PerFragment.class);
        tabDataList.add(tabHome);
        tabDataList.add(tabHot);
        tabDataList.add(tabCategory);
        //添加底部菜单项-tabSpec
        for (TabDataBean bean : tabDataList) {
            TabHost.TabSpec tabSpec = tabhost.newTabSpec(bean.getTabName());
            //给菜单项添加内容，indicator,其中indicator需要的参数View即为菜单项的布局
            tabSpec.setIndicator(getIndicatorView(bean));
            //第二参数就是该菜单项对应的页面内容
            tabhost.addTab(tabSpec, bean.getContent(), null);
        }
        tabhost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if ("首页".equals(tabId)) {
                    toolbar.setTitle("首页");
                    menuItem.setVisible(true);
                } else if ("工作台".equals(tabId)) {
                    toolbar.setTitle("工作台");
                    menuItem.setVisible(false);
                } else {
                    toolbar.setTitle("个人中心");
                    menuItem.setVisible(false);
                }
            }
        });
    }

    private View getIndicatorView(TabDataBean bean) {
        View view = LayoutInflater.from(this).inflate(R.layout.tabhost_tabspec_normal_layout, null);
        ImageView iconTab = (ImageView) view.findViewById(R.id.iv_tab_icon);
        TextView tvTab = (TextView) view.findViewById(R.id.tv_tab_label);
        iconTab.setImageResource(bean.getTabIcon());
        tvTab.setText(bean.getTabName());
        return view;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    Menu menu;
    boolean isMenu = false;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_scanand, menu);
        menuItem = menu.findItem(R.id.scanand);
        menuItem.setVisible(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        initPermission();
        return super.onOptionsItemSelected(item);
    }

    /**
     * 请求权限
     */
    private void initPermission() {
        RxPermissions rxPermissions = new RxPermissions(this);
        // 多权限申请要用request方法
        rxPermissions.requestEach(Manifest.permission.CAMERA)
                .subscribe(new Action1<Permission>() {
                    @Override
                    public void call(Permission permission) {
                        if (permission.granted) {
                            Intent intent = new Intent(HomeActivity.this, ScanandActivity.class);
                            startActivity(intent);
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // Denied permission without ask never again
                            Toast.makeText(HomeActivity.this, "没有访问也没有拒绝", Toast.LENGTH_SHORT)
                                    .show();
                        } else {
                            // Denied permission with ask never again
                            // Need to go to the settings
                            Toast.makeText(HomeActivity.this, "没有权限,您不能扫描二维码,请进入设置打开权限", Toast
                                    .LENGTH_SHORT)
                                    .show();
                        }
                    }
                });
    }

    private long exitTime = 0;
    //后退两次回到桌面

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次后台运行",
                        Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                Intent home = new Intent(Intent.ACTION_MAIN);
                home.addCategory(Intent.CATEGORY_HOME);
                startActivity(home);
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

}
