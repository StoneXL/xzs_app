package com.yxld.xzs;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jaeger.library.StatusBarUtil;
import com.socks.library.KLog;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.yxld.xzs.activity.Income.IncomeActivity;
import com.yxld.xzs.activity.Login.LoginActivity;
import com.yxld.xzs.activity.Login.LoginNewActivity;
import com.yxld.xzs.activity.Main.GrabFragment;
import com.yxld.xzs.activity.Main.PickFragment;
import com.yxld.xzs.activity.Main.SendFragment;
import com.yxld.xzs.activity.Navigation.ApplyBuyActivity;
import com.yxld.xzs.activity.Navigation.ApplyRepairActivity;
import com.yxld.xzs.activity.Navigation.ApplyScrapActivity;
import com.yxld.xzs.activity.Navigation.NightActivity;
import com.yxld.xzs.activity.Navigation.ProjectListActivity;
import com.yxld.xzs.activity.Navigation.QuardActivity;
import com.yxld.xzs.activity.Navigation.SettingsActivity;
import com.yxld.xzs.activity.Navigation.SummaryActivity;
import com.yxld.xzs.activity.Navigation.TicketApplyActivity;
import com.yxld.xzs.activity.Repair.RepairActivity;
import com.yxld.xzs.activity.camera.StationActivity;
import com.yxld.xzs.activity.index.HomeActivity;
import com.yxld.xzs.activity.patrol.PatrolManagerActivity;
import com.yxld.xzs.activity.patrol.PatrolPotEnteringActivity;
import com.yxld.xzs.adapter.MainMenuAdapter;
import com.yxld.xzs.base.ActivityDelegate;
import com.yxld.xzs.base.DemoApplicationLike;
import com.yxld.xzs.contain.Contains;
import com.yxld.xzs.entity.BaseBack;
import com.yxld.xzs.entity.MainMenuEntity;
import com.yxld.xzs.http.ServiceFactory;
import com.yxld.xzs.http.api.HttpAPIWrapper;
import com.yxld.xzs.http.exception.ApiException;
import com.yxld.xzs.subscribers.RxSubscriber;
import com.yxld.xzs.transformer.DefaultTransformer;
import com.yxld.xzs.utils.StringUitl;
import com.yxld.xzs.utils.UIUtils;
import com.yxld.xzs.view.TabLayoutPlus;
import com.zhy.autolayout.AutoLinearLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ch.ielse.view.SwitchView;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SwitchView.OnStateChangedListener, ActivityDelegate {

    @BindView(R.id.exit)
    Button exit;
    @BindView(R.id.settings)
    Button settings;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    //    @BindView(R.id.navigation)
//    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.recycler_menu)
    RecyclerView recyclerMenu;

    private ImageView load_push;
    private SwitchView switchButton;
    private AutoLinearLayout headerLayout;
    private View nav_header_view;
    private TextView nav_header_text, nav_header_name, nav_header_fuzeren;
    public ViewPager viewPager;
    public TabLayoutPlus tabLayout;
    private TabLayout.Tab Grab, Pick, Service;

    private long exitTime = 0;
    public static List<String> logList = new CopyOnWriteArrayList<String>();
    private String[] mTitles = new String[]{"待抢单", "待取货", "待送达"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
        setSupportActionBar(toolbar);
        StatusBarUtil.setColorForDrawerLayout(this, (DrawerLayout) findViewById(R.id.drawer_layout), ContextCompat.getColor(this, R.color.toolbar_color), 112);
        //极光推送
//        String topic = Contains.appLogin.getAdminNickName();
        String topic = Contains.appLogin.getAdminId() + "";
        KLog.i("别名" + Contains.appLogin.getAdminId() + "");
        JPushInterface.setAlias(this, topic, new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                KLog.i("JPushInterface setAlias  gotResult: " + i);
                KLog.i("JPushInterface setAlias  gotResult: " + s);
                KLog.i("JPushInterface setAlias  gotResult: " + set);
            }
        });
        //阿里设置别名
        PushServiceFactory.getCloudPushService().addAlias(StringUitl.getDeviceId(this), new CommonCallback() {
            @Override
            public void onSuccess(String s) {
                KLog.i("aliyunpush addAlias  gotResult: " + s);
            }

            @Override
            public void onFailed(String s, String s1) {

            }
        });
        //阿里绑定手机号
        PushServiceFactory.getCloudPushService().bindAccount(Contains.appLogin.getAdminXiangmuId()+"", new CommonCallback() {
            @Override
            public void onSuccess(String s) {
                KLog.i("aliyunpush bindAccount  gotResult: " + s);
            }

            @Override
            public void onFailed(String s, String s1) {

            }
        });
        //fir更新下载
//        UpdateFunGO.init(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        initview();
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

    private void initview() {
        tabLayout = (TabLayoutPlus) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
//        navigationView.setNavigationItemSelectedListener(this);
//        headerLayout = navigationView.getHeaderView(0);
        headerLayout = (AutoLinearLayout) findViewById(R.id.headerLayout);
        nav_header_view = findViewById(R.id.nav_header_view);
        switchButton = (SwitchView) findViewById(R.id.SwitchButton);
        load_push = (ImageView) findViewById(R.id.load_push);
        nav_header_text = (TextView) findViewById(R.id.nav_header_text);
        nav_header_name = (TextView) findViewById(R.id.nav_header_name);
        nav_header_fuzeren = (TextView) findViewById(R.id.nav_header_fuzeren);
        switchButton.setOnStateChangedListener(this);
        nav_header_name.setText(Contains.appLogin.getAdminNickName());
        if (Contains.appLogin.getCxwyPeisongFuzeren() != null && Contains.appLogin.getCxwyPeisongFuzeren() == 0) {
            nav_header_fuzeren.setText("值班负责人");
        } else {
            nav_header_fuzeren.setText("");
        }

        viewPager.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        Grab = tabLayout.getTabAt(0);
        Pick = tabLayout.getTabAt(1);
        Service = tabLayout.getTabAt(2);
        viewPager.setOffscreenPageLimit(2);

        recyclerMenu.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        MainMenuAdapter adapter = new MainMenuAdapter(UIUtils.getMainMenu());
        recyclerMenu.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                handlerMenuItemClick((MainMenuEntity) adapter.getItem(position));
            }
        });

    }

    FragmentPagerAdapter mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

        @Override
        public Fragment getItem(int position) {
            if (position == 1) {
                return new PickFragment();
            } else if (position == 2) {
                return new SendFragment();
            }
            return new GrabFragment();
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

    @OnClick({R.id.settings, R.id.exit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.settings:
                startActivity(SettingsActivity.class);
                break;
            case R.id.exit:
                //注销极光推送
                JPushInterface.setAlias(this, "", new TagAliasCallback() {
                    @Override
                    public void gotResult(int i, String s, Set<String> set) {
                        Log.d("geek", "JPushInterface setAlias  gotResult: " + i);
                    }
                });
                //注销极阿里云
                PushServiceFactory.getCloudPushService().removeAlias(StringUitl.getDeviceId(this) + "", new CommonCallback() {
                    @Override
                    public void onSuccess(String s) {
                        KLog.i("aliyunpush removeAlias  gotResult: " + s);
                    }

                    @Override
                    public void onFailed(String s, String s1) {

                    }
                });
                PushServiceFactory.getCloudPushService().unbindAccount( new CommonCallback() {
                    @Override
                    public void onSuccess(String s) {
                        KLog.i("aliyunpush unbindAccount  gotResult: " + s);
                    }

                    @Override
                    public void onFailed(String s, String s1) {

                    }
                });
                //清除SharedPreferences里的账号数据
                SharedPreferences sp = this.getSharedPreferences("userInfo", 0);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("NAME", "");
                editor.putString("PASSWORD", "");
                editor.putBoolean("ISCHECK", false);
                editor.putBoolean("AUTO_ISCHECK", false);
                editor.commit();
                Contains.appLogin = null;
                logList.clear();
                finish();
                Intent mine = new Intent(this, LoginActivity.class);
                startActivity(mine);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onOutLogin(String outlogin) {
        if (outlogin.equals("退出登录")) {
            PushServiceFactory.getCloudPushService().removeAlias(Contains.uuid, new CommonCallback() {
                @Override
                public void onSuccess(String s) {
                    Log.d("geek","阿里推送设置removeAlias成功" +  "removeAlias success"+s);
                }

                @Override
                public void onFailed(String s, String s1) {

                }
            });
            PushServiceFactory.getCloudPushService().unbindAccount(new CommonCallback() {
                @Override
                public void onSuccess(String s) {

                }

                @Override
                public void onFailed(String s, String s1) {

                }
            });
            //清除SharedPreferences里的账号数据
            SharedPreferences sp = this.getSharedPreferences("userInfo", 0);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("NAME", "");
            editor.putString("PASSWORD", "");
            editor.putBoolean("ISCHECK", false);
            editor.putBoolean("AUTO_ISCHECK", false);
            editor.commit();
            Contains.appLogin = null;
            logList.clear();
            finish();
            Intent mine = new Intent(this, LoginActivity.class);
            startActivity(mine);
        }
    }
    private void handlerMenuItemClick(MainMenuEntity entity) {
        switch (entity.title) {
            case "我的收入":
                //我的收入
                startOtherActitvity(HomeActivity.class);
               //startOtherActitvity(IncomeActivity.class);
                break;
            case "工作汇总":
                //工作汇总
                startOtherActitvity(LoginNewActivity.class);
              //  startOtherActitvity(SummaryActivity.class);
                break;
            case "特殊门禁":
                //特殊门禁
                startOtherActitvity(QuardActivity.class);
                break;
            case "通知管理":
                //通知管理
                if (Contains.appLogin.getCxwyPeisongFuzeren() == 0) {
                    startOtherActitvity(NightActivity.class);
                } else {
                    Toast.makeText(MainActivity.this, "您暂时没有权限无法进入", Toast.LENGTH_SHORT).show();
                }
                break;
            case "报修审批":
                //报修审批
                startOtherActitvity(RepairActivity.class);
                break;
            case "报修申请":
                //报修申请
                startOtherActitvity(ApplyRepairActivity.class);
                break;
            case "报废申请":
                //报废申请
                startOtherActitvity(ApplyScrapActivity.class);
                break;
            case "申购列表":
                startOtherActitvity(ApplyBuyActivity.class);
                break;
            case "电子券审批":
                startOtherActitvity(TicketApplyActivity.class);
                break;
            case "区域监控":
                //区域监控
                startOtherActitvity(ProjectListActivity.class);
                break;
            case "巡检系统":
                //巡检
                startOtherActitvity(PatrolManagerActivity.class);
                break;
            case "联系客服":
                //联系客服
                Hotline();
                break;
            case "岗位监控":
                startActivity(StationActivity.class);
                break;
            case "巡检点录入":
                startActivity(PatrolPotEnteringActivity.class);
                break;
            default:break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    private void startOtherActitvity(final Class<?> clazz) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    sleep(300);
                    startActivity(clazz);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_income) {
            //我的收入
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    try {
                        sleep(300);
                        startActivity(IncomeActivity.class);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();

        } else if (id == R.id.nav_summary) {
            //工作汇总
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    try {
                        sleep(300);
                        startActivity(SummaryActivity.class);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        } else if (id == R.id.nav_invitation) {
            //邀请好友  nav_invitation 改为进入公共监控
//            Toast.makeText(MainActivity.this, "尽快更新中", Toast.LENGTH_SHORT).show();
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    try {
                        sleep(300);
                        startActivity(ProjectListActivity.class);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        } else if (id == R.id.nav_customer) {
            //联系客服
            Hotline();
        } else if (id == R.id.nav_night) {
            //通知管理
            if (Contains.appLogin.getCxwyPeisongFuzeren() == 0) {
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {
                            sleep(300);
                            startActivity(NightActivity.class);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            } else {
                Toast.makeText(MainActivity.this, "您暂时没有权限无法进入", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.nav_quard) {
            //特殊门禁
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    try {
                        sleep(300);
                        startActivity(QuardActivity.class);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        } else if (id == R.id.nav_approval) {
            //报修审批
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    try {
                        sleep(300);
                        startActivity(RepairActivity.class);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    //获取客服电话网络请求
    private void Hotline() {
        /*ServiceFactory.httpService()
                .hotline(Contains.appLogin.getAdminXiangmuId().toString())
                .compose(new DefaultTransformer<String>())
                .subscribe(new RxSubscriber<String>(MainActivity.this) {
                    @Override
                    public void onNext(final String hotline) {
                        Logger.d(hotline);
                        RxPermissions rxPermissions = new RxPermissions(MainActivity.this);
                        rxPermissions.setLogging(true);
                        rxPermissions.requestEach(Manifest.permission.CALL_PHONE)
                                .subscribe(new Action1<Permission>() {
                                    @Override
                                    public void call(Permission permission) {
                                        Logger.d("Permission result " + permission);
                                        if (permission.granted) {
                                            Intent intent = new Intent();
                                            intent.setAction(Intent.ACTION_CALL);
                                            //url:统一资源定位符
                                            //uri:统一资源标示符（更广）
                                            intent.setData(Uri.parse("tel:" + hotline));
                                            //开启系统拨号器
//                                            startActivity(intent);
                                        } else if (permission.shouldShowRequestPermissionRationale) {
                                            // Denied permission without ask never again
                                            Toast.makeText(MainActivity.this,
                                                    "没有访问也没有拒绝",
                                                    Toast.LENGTH_SHORT).show();
                                        } else {
                                            // Denied permission with ask never again
                                            // Need to go to the settings
                                            Toast.makeText(MainActivity.this,
                                                    "没有权限,您不能打电话",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                });*/
//        拦截到的okhttp结果:{"MSG":"获取成功","row":"0731-1234567","status":"0"}
        Map<String, String> map = new HashMap<>();
        map.put("xiaoqu", Contains.appLogin.getAdminXiangmuId().toString());
        Disposable disposable = HttpAPIWrapper.getInstance(HttpAPIWrapper.getOkHttpClient()).hotLine(map)
                .subscribe(new Consumer<BaseBack>() {
                    @Override
                    public void accept(@NonNull BaseBack teamManage) throws Exception {
                        Log.e("wh", "打印");

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        Log.e("wh", "错误");
                    }
                });
//        disposables.add(disposable);
    }

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


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        UpdateFunGO.onResume(this);
        //判断开关状态
        if (Contains.appLogin == null)
            return;
        if ("开启".equals(Contains.appLogin.getCxwyPeisongState())) {
            headerLayout.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.drawer_blue));
            nav_header_view.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.drawer_light_blue));
            nav_header_text.setText("接单中");
            switchButton.setOpened(true);
        } else {
            headerLayout.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.drawer_gray));
            nav_header_view.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.drawer_light_gray));
            nav_header_text.setText("未开工");
            switchButton.setOpened(false);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
//        UpdateFunGO.onStop(this);
    }

    /**
     * 跳转到指定的Activity
     *
     * @param targetActivity 要跳转的目标Activity
     */
    private void startActivity(@NonNull Class<?> targetActivity) {
        startActivity(new Intent(this, targetActivity));
    }

    //状态开关
    @Override
    public void toggleToOn(SwitchView view) {
        load_push.setVisibility(View.VISIBLE);
        ((AnimationDrawable) load_push.getBackground()).start();
        ServiceFactory.httpService()
                .state(Contains.appLogin.getAdminId(), "开启")
                .compose(new DefaultTransformer<String>())
                .subscribe(new RxSubscriber<String>(MainActivity.this) {
                    @Override
                    public void onNext(String state) {
                        ((AnimationDrawable) load_push.getBackground()).stop();
                        load_push.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "接单开启成功", Toast.LENGTH_SHORT).show();
                        switchButton.toggleSwitch(true);
                        headerLayout.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.drawer_blue));
                        nav_header_view.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.drawer_light_blue));
                        nav_header_text.setText("接单中");
                    }

                    @Override
                    protected void onError(ApiException ex) {
                        super.onError(ex);
                        ((AnimationDrawable) load_push.getBackground()).stop();
                        load_push.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "接单开启失败，请稍后重新尝试", Toast.LENGTH_SHORT).show();
                        switchButton.toggleSwitch(false);
                    }
                });
    }

    @Override
    public void toggleToOff(SwitchView view) {
        load_push.setVisibility(View.VISIBLE);
        ((AnimationDrawable) load_push.getBackground()).start();
        ServiceFactory.httpService()
                .state(Contains.appLogin.getAdminId(), "关闭")
                .compose(new DefaultTransformer<String>())
                .subscribe(new RxSubscriber<String>(MainActivity.this) {
                    @Override
                    public void onNext(String state) {
                        ((AnimationDrawable) load_push.getBackground()).stop();
                        load_push.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "接单关闭成功", Toast.LENGTH_SHORT).show();
                        switchButton.toggleSwitch(false);
                        headerLayout.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.drawer_gray));
                        nav_header_view.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.drawer_light_gray));
                        nav_header_text.setText("未开工");
                    }

                    @Override
                    protected void onError(ApiException ex) {
                        super.onError(ex);
                        ((AnimationDrawable) load_push.getBackground()).stop();
                        load_push.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "接单关闭失败，请稍后重新尝试", Toast.LENGTH_SHORT).show();
                        switchButton.toggleSwitch(true);
                    }
                });
    }

    @Override
    public void destoryContainer() {
        finish();
    }

    @Override
    public Activity getContainerActivity() {
        return this;
    }

    @Override
    public boolean isContainerDead() {
        if (Build.VERSION.SDK_INT > 16) {
            return this.isDestroyed();
        } else {
            return this.isFinishing();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        DemoApplicationLike.getApp().mAppActivityManager.removeActivity(this);
    }
}
