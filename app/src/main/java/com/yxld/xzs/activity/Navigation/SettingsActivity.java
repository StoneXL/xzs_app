package com.yxld.xzs.activity.Navigation;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.socks.library.KLog;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yxld.xzs.R;
import com.yxld.xzs.activity.Login.ChangePwdActivity;
import com.yxld.xzs.base.BaseActivity;
import com.yxld.xzs.contain.Contains;
import com.yxld.xzs.entity.BaseBack;
import com.yxld.xzs.entity.CxwyAppVersion;
import com.yxld.xzs.entity.CxwyAppVersionBean;
import com.yxld.xzs.http.api.HttpAPIWrapper;
import com.yxld.xzs.utils.ToastUtil;
import com.yxld.xzs.utils.UpdateManager;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import static com.yxld.xzs.activity.Login.SplashActivity.REQUEST_CODE_ASK_WRITE_EXTERNAL_STORAGE;

/**
 * Created by yishangfei on 2016/10/9 0009.
 * 欣助手应用设置
 */

public class SettingsActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {

    //// TODO: 2017/11/16 修改密码
    @BindView(R.id.setting_place)
    TextView settingPlace;
    @BindView(R.id.version_number)
    TextView versionNumber;
    @BindView(R.id.switch_ring)
    CheckBox switchRing;
    @BindView(R.id.switch_shock)
    CheckBox switchShock;
    @BindView(R.id.setting_update)
    AutoRelativeLayout settingUpdate;

    private CxwyAppVersion mVersion;
    private boolean getLastVersionBack = false;
    private boolean hasUpdate = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initview();
    }

    private void initview() {
        versionNumber.setText(getAppInfo());
        switchRing.setOnCheckedChangeListener(this);
        switchShock.setOnCheckedChangeListener(this);
        SharedPreferences sp = getSharedPreferences("ls", MODE_PRIVATE);
        boolean ring = sp.getBoolean("rememberPass", true);
        switchRing.setChecked(ring);
        SharedPreferences sp1 = getSharedPreferences("zd", MODE_PRIVATE);
        boolean shock = sp1.getBoolean("rememberPass", true);
        switchShock.setChecked(shock);
    }

    @OnClick({R.id.setting_place, R.id.setting_update, R.id.setting_anquan, R.id.btn_loginout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting_place://修改密码
                startActivity(ChangePwdActivity.class);
                break;
            case R.id.setting_update://检查更新
                // TODO: 2017/11/3 改成和欣社区一样的逻辑 UpdateManager
//                    UpdateFunGO.manualStart(this);
//                getLastVersion(); //判断有没有更新信息
                break;
            case R.id.btn_loginout://退出登陆
                showExitDialog();
                break;
            case R.id.setting_anquan:
                startActivity(AccountSafeActivity.class);
                break;
            default:
                break;
        }
    }

    /**
     * 获取网络版本号
     */
    private void getLastVersion() {
        //网络请求
        Map<String, String> map = new HashMap<>();
        Disposable disposable = HttpAPIWrapper.getInstance(HttpAPIWrapper.getOkHttpClient())
                .getAppVersionInfo(map)
                .subscribe(new Consumer<CxwyAppVersionBean>() {
                    @Override
                    public void accept(@NonNull CxwyAppVersionBean version) throws Exception {
                        if (version.status == 0) {
                            //isSuccesse
                            mVersion = version.getRows();
                            int i1 = Integer.valueOf(mVersion.getVersionUId().replace("" +
                                    ".", ""));
                            int i2 = Integer.valueOf(getVersion().replace(".",
                                    ""));

                            if (i1 > i2) {
                                getLastVersionBack = true;
                                hasUpdate = true;
                                getUpdatePermission();
                            } else {

                            }
                        } else {
                            onError(version.status, version.MSG);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        //onError
                        KLog.i("onError");
                        Log.e("sdfsdf", " onError");
                        throwable.printStackTrace();
                    }
                });
        disposables.add(disposable);
    }

    public void getUpdatePermission() {
        AndPermission.with(this)
                .requestCode(REQUEST_CODE_ASK_WRITE_EXTERNAL_STORAGE)
                .permission(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
//                .rationale((requestCode, rationale) -> {
//                            AndPermission.rationaleDialog(mActivity, rationale).show();
//                        }
//                )
                .callback(updateListener)
                .start();
    }

    private PermissionListener updateListener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, List<String> grantedPermissions) {
            // 权限申请成功回调。
            if (requestCode == REQUEST_CODE_ASK_WRITE_EXTERNAL_STORAGE) {
                alertUpdate();
            }
        }

        @Override
        public void onFailed(int requestCode, List<String> deniedPermissions) {
            // 权限申请失败回调。
            if (requestCode == REQUEST_CODE_ASK_WRITE_EXTERNAL_STORAGE) {
                // TODO ...
            }
        }
    };

    /**
     * 控制更新版本弹框
     */
    private void alertUpdate() {
        // 这里来检测版本是否需要更新
        UpdateManager mUpdateManager = new UpdateManager(this, mVersion.getVersionDownloadUrl());
        mUpdateManager.checkUpdateInfo(mVersion.getVersionUId(), mVersion.getVersionExplain(),
                mVersion.getVersionIsCompulsory());
        mUpdateManager.setOnYiHouOnClickListener(new UpdateManager.OnYiHouOnClickListener() {
            @Override
            public void onYihouClick() {
                //点击"以后再说"，什么操作都不做

             /*   if (jump == JUMPTOMAIN) {
                    loginSuccees();
                } else {
                    jumpToLogin();
                }*/
            }
        });
    }

    /**
     * 获取当前版本号
     *
     * @return
     */
    public String getVersion() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(
                    this.getPackageName(), 0);
            String version = info.versionName;
            KLog.d("geek", "version" + version);
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return this.getString(R.string.can_not_find_version_name);
        }
    }

    public void showExitDialog() {
        new AlertView("是否退出登录？", null, "取消", null, new String[]{"确定"}, this, AlertView.Style.ActionSheet, new OnItemClickListener() {
            @Override
            public void onItemClick(Object o, int position) {
                if (position == 0) {
                    exitLoginFromServer();
                }
            }
        }).setCancelable(true).show();
    }

    private void exitLoginFromServer() {
        showProgressDialog();
        Map<String, String> map = new HashMap<>(16);
        map.put("uuid", Contains.uuid);
        HttpAPIWrapper.getInstance().loginOut(map).subscribe(new Consumer<BaseBack>() {
            @Override
            public void accept(@NonNull BaseBack baseBack) throws Exception {
                dismissProgressDialog();
                if (baseBack.status != 99) {
                    ToastUtil.showToast(SettingsActivity.this, baseBack.getMSG());
                    return;
                }
                ToastUtil.showToast(SettingsActivity.this, "退出登录成功");
                exitLogin();
                SettingsActivity.this.finish();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                dismissProgressDialog();
                KLog.e("onError" + throwable.toString());
                ToastUtil.showToast(SettingsActivity.this, "退出登录失败");
            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.switch_ring:
                SharedPreferences sp = getSharedPreferences("ls", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                if (isChecked) {
                    editor.putBoolean("rememberPass", true);
                } else {
                    editor.putBoolean("rememberPass", false);
                }
                editor.apply();
                break;
            case R.id.switch_shock:
                SharedPreferences sp1 = getSharedPreferences("zd", MODE_PRIVATE);
                SharedPreferences.Editor editor1 = sp1.edit();
                if (isChecked) {
                    editor1.putBoolean("rememberPass", true);
                } else {
                    editor1.putBoolean("rememberPass", false);
                }
                editor1.apply();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        UpdateFunGO.onResume(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
//        UpdateFunGO.onStop(this);
    }

    private String getAppInfo() {
        try {
            String pkName = this.getPackageName();
            String versionName = this.getPackageManager().getPackageInfo(
                    pkName, 0).versionName;
            return versionName;
        } catch (Exception e) {
        }
        return null;
    }
}
