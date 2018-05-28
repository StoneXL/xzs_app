package com.yxld.xzs.activity.Login;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;

import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.socks.library.KLog;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;
import com.yxld.xzs.R;
import com.yxld.xzs.activity.index.HomeActivity;
import com.yxld.xzs.base.BaseActivity;
import com.yxld.xzs.contain.ContainValue;
import com.yxld.xzs.contain.Contains;
import com.yxld.xzs.entity.AppLogin;
import com.yxld.xzs.entity.CxwyAppVersion;
import com.yxld.xzs.entity.CxwyAppVersionBean;
import com.yxld.xzs.http.api.HttpAPIWrapper;
import com.yxld.xzs.utils.StringUitls;
import com.yxld.xzs.utils.ToastUtil;
import com.yxld.xzs.utils.UpdateManager;
import com.yxld.xzs.utils.util.VersionUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import cn.jpush.android.api.JPushInterface;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

import static com.yxld.xzs.activity.Navigation.AccountSafeActivity.SP_FINGER_STATE;
import static com.yxld.xzs.activity.Navigation.AccountSafeActivity.SP_PATTERN_STATE;
import static com.yxld.xzs.activity.Repair.TreatFragment.PHOTOTAKE;
import static com.yxld.xzs.utils.StringUitl.hasEmptyItem;

/**
 * 作者：Android on 2017/8/19
 * 邮箱：365941593@qq.com
 * 描述：
 */

public class SplashActivity extends BaseActivity {

    private static final int JUMPTOMAIN = 0;
    private static final int JUMPTOLOGIN = 1;
    public static int LOCATION_FINISH = 65;
    private int jump = JUMPTOLOGIN;
    private static int permissionState = -1;    //-1表示原始状态,0表示允许,1表示拒绝.
    private boolean timeOver = false;

    private CxwyAppVersion mVersion;
    private boolean getLastVersionBack = false;
    private boolean hasUpdate = false;

    /**
     * 动态获取定位权限
     */
    public final static int REQUEST_CODE_ASK_WRITE_EXTERNAL_STORAGE = 124;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        KLog.i("进入splash");

        jumpNext();
        queryShipperInfo();
        getPermission();
        getLastVersion(); //判断有没有更新信息


        rootLayout.setVisibility(View.GONE);
       /* if (!sp.getString("NAME", "").equals("") && !sp.getString("PASSWORD", "").equals("")) {
            KLog.i(sp.getString("NAME", ""));
            doLogin();
        }*/
    }

    private void queryShipperInfo() {
        if (isUpdate()) {
            return;
        }

        //判断是否已经登录过，已登录过跳首页，未登录跳登录页
        boolean savePsd = sp.getBoolean("ISCHECK", false);
        if (!savePsd) {
            jump = JUMPTOLOGIN;
        } else {
            AndPermission.with(this)
                    .requestCode(100)
                    .permission(
                            Manifest.permission.READ_PHONE_STATE
                    )
//                    .rationale((requestCode, rationale) -> {
//                                AndPermission.rationaleDialog(mActivity, rationale).show();
//                            }
//                    )
                    .callback(permissionListener)
                    .start();
        }
       /* if (!sp.getString("NAME", "").equals("") && !sp.getString("PASSWORD", "").equals("")) {
            KLog.i(sp.getString("NAME", ""));

            AndPermission.with(this)
                    .requestCode(100)
                    .permission(
                            Manifest.permission.READ_PHONE_STATE
                    )
//                    .rationale((requestCode, rationale) -> {
//                                AndPermission.rationaleDialog(mActivity, rationale).show();
//                            }
//                    )
                    .callback(permissionListener)
                    .start();
        } else {
            jump = JUMPTOLOGIN;//跳登录页
        }*/

    }

    private PermissionListener permissionListener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, List<String> grantedPermissions) {
            // 权限申请成功回调。
            if (requestCode == 100) {
                doLogin();//登录后跳首页
            }
        }

        @Override
        public void onFailed(int requestCode, List<String> deniedPermissions) {
            // 权限申请失败回调。
            if (requestCode == PHOTOTAKE) {
                // TODO ...
            }
        }
    };

    /**
     * 判断是否是更新过来的，如果是，就进入登录页面
     *
     * @return
     */
    public boolean isUpdate() {
        if (sp.getInt(ContainValue.CURVERSIONCODE, 0) < VersionUtil.getAppVersionCode(this)) {
            jump = JUMPTOLOGIN;
            SharedPreferences.Editor editor = sp.edit();
            editor.putInt(ContainValue.CURVERSIONCODE, VersionUtil.getAppVersionCode(this));
            editor.commit();
            return true;
        } else {
            return false;
        }
    }

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
                            //onError(version.status, version.MSG);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        //onError
                        KLog.i("onError");
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
                if (jump == JUMPTOMAIN) {
                    loginSuccees();
                } else {
                    jumpToLogin();
                }
            }
        });
    }

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

    private void jumpNext() {
        Observable.interval(0, 1, TimeUnit.SECONDS).take(2)
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(@NonNull Long aLong) throws Exception {
                        return 2 - aLong;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())//发射用的是observeOn
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        KLog.i("1");
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        KLog.i("2");
                    }

                    @Override
                    public void onNext(@NonNull Long remainTime) {
                        KLog.i("剩余时间" + remainTime);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        KLog.i("4");
                    }

                    @Override
                    public void onComplete() {
                        timeOver = true;
                        KLog.i("时间到，开始跳转");
                        if (permissionState != 0) {
                            return;
                        }
                        if (hasUpdate) {
                            return;
                        }
                        KLog.i("时间到，开始跳转");
                        if (jump == JUMPTOMAIN) {
                            loginSuccees();
                        } else {
                            jumpToLogin();
                        }
                    }
                });
    }

    private void doLogin() {
        StringBuilder deviceId = new StringBuilder();
        // 渠道标志
        deviceId.append("a");
        try {
            TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            String imei = tm.getDeviceId();
            if (!hasEmptyItem(imei)) {
                deviceId.append("m");
                deviceId.append(imei);
            } else {
                imei = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                        Settings.Secure.ANDROID_ID);
                deviceId.append("m");
                deviceId.append(imei);
            }
            KLog.i(imei);

            //序列号（sn）
            String sn = tm.getSimSerialNumber();
            if (!hasEmptyItem(sn)) {
                deviceId.append("s");
                deviceId.append(sn);
                Log.e("geek : ", "序列号（sn）=" + deviceId.toString());
            }

        } catch (Exception e) {
            Log.d("geek", "getDeviceId: e");
            deviceId.append("e" + deviceId.toString() + JPushInterface.getRegistrationID(this)
                    + PushServiceFactory.getCloudPushService().getDeviceId());
        }
        Map<String, String> map = new HashMap<>();
        map.put("username", sp.getString("NAME", ""));
        map.put("password", StringUitls.getMD5(sp.getString("PASSWORD", "")));
        map.put("macAddr", deviceId.toString());
        Disposable subscribe = HttpAPIWrapper.getInstance(HttpAPIWrapper.getOkHttpClient()).
                Login(map).
                subscribe(new Consumer<AppLogin>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull AppLogin appLogin)
                            throws Exception {
                        if (appLogin.status != 0) {
                            return;
                        }
                        Contains.appLogin = appLogin.getRow();
                        Contains.uuid = appLogin.getUuid();
                        KLog.i("splashActivity--------->" + Contains.appLogin.toString());
                        jump = JUMPTOMAIN;
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull Throwable throwable)
                            throws Exception {
                        KLog.i("onError");
                    }
                });
        disposables.add(subscribe);
    }

    private void loginSuccees() {
        if (sp.getBoolean(SP_FINGER_STATE, false) || sp.getBoolean(SP_PATTERN_STATE, false)) {
            startActivity(LoginPatternActivity.class);
            finish();
        } else {
            startActivity(HomeActivity.class);
            finish();
        }
    }

    private void jumpToLogin() {
        startActivity(LoginNewActivity.class);
        finish();
    }

    public void getPermission() {
        AndPermission.with(this)
                .requestCode(101)
                .permission(
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.INTERNET,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.NFC
                )
                .rationale(new RationaleListener() {
                    @Override
                    public void showRequestPermissionRationale(int requestCode, Rationale
                            rationale) {
                        AndPermission
                                .rationaleDialog(SplashActivity.this, rationale)
                                .setNegativeButton("关闭", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ToastUtil.show(SplashActivity.this, "权限申请失败," +
                                                "app部分功能将无法使用!!!");
                                        if (jump == JUMPTOMAIN) {
                                            loginSuccees();
                                        } else {
                                            jumpToLogin();
                                        }
                                    }
                                })
                                .show();
                    }
                })
                .callback(permission)
                .start();
    }

    private PermissionListener permission = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, List<String> grantedPermissions) {
            // 权限申请成功回调。
            if (requestCode == 101) {
                permissionState = 0;
                if (timeOver) {
                    if (jump == JUMPTOMAIN) {
                        loginSuccees();
                    } else {
                        jumpToLogin();
                    }
                }
            }
        }

        @Override
        public void onFailed(int requestCode, List<String> deniedPermissions) {
            // 权限申请失败回调。
            if (requestCode == 101) {
                KLog.i("权限申请失败");
                permissionState = 1;
                Log.e("wh", "权限申请失败" + deniedPermissions.toString());
                ToastUtil.show(SplashActivity.this, "权限申请失败,app部分功能将无法使用!!!");
                if (jump == JUMPTOMAIN) {
                    loginSuccees();
                } else {
                    jumpToLogin();
                }
            }
        }
    };
}
