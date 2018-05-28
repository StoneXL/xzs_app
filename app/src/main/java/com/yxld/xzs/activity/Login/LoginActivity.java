package com.yxld.xzs.activity.Login;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.marlonmafra.android.widget.EditTextPassword;
import com.socks.library.KLog;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;
import com.yxld.xzs.MainActivity;
import com.yxld.xzs.R;
import com.yxld.xzs.base.BaseActivity;
import com.yxld.xzs.base.DemoApplicationLike;
import com.yxld.xzs.contain.Contains;
import com.yxld.xzs.entity.AppLogin;
import com.yxld.xzs.http.ServiceFactory;
import com.yxld.xzs.subscribers.RxSubscriber;
import com.yxld.xzs.transformer.DefaultTransformer2;
import com.yxld.xzs.utils.StringUitls;
import com.yxld.xzs.view.DeleteEditText;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;
import tyrantgit.explosionfield.ExplosionField;

import static com.yxld.xzs.utils.StringUitl.hasEmptyItem;

/**
 * 登陆界面
 */

public class LoginActivity extends BaseActivity {
    private static final String KEY_RELOGIN = "key_relogin";
    @BindView(R.id.phone)
    DeleteEditText phone;
    @BindView(R.id.password)
    EditTextPassword password;
    @BindView(R.id.login_button)
    Button loginButton;

    private SharedPreferences sp;
    private String NameValue, PwdValue;
    private ExplosionField mExplosionField;

    public static void startMeWhenRelogin(Context from){
        Intent intent = new Intent(from,LoginActivity.class);
        intent.putExtra(KEY_RELOGIN,1);
        from.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if(extras!=null && extras.containsKey(KEY_RELOGIN)){
            DemoApplicationLike.getApp().mAppActivityManager.finishAllActivityWithoutThis();
        }
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("登录");
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        //queryShipperInfo();
        mExplosionField = ExplosionField.attach2Window(LoginActivity.this);
        addListener(findViewById(R.id.login_button));
        sp = this.getSharedPreferences("userInfo", MODE_PRIVATE);
        //判断记住密码多选框的状态
        if (!sp.getString("NAME", "").equals("") && !sp.getString("PASSWORD", "").equals("")) {
            //设置默认是记录密码状态
            phone.setText(sp.getString("NAME", ""));
            password.setText(sp.getString("PASSWORD", ""));
            phone.setSelection(phone.getText().length());
            Login();
            //判断自动登陆多选框状态
//            if (sp.getBoolean("AUTO_ISCHECK", false)) {
//                //保存用户名和密码
//                NameValue = phone.getText().toString();
//                PwdValue = password.getText().toString();
//            }
        }

    }

    public void queryShipperInfo() {
            AndPermission.with(this)
                    .requestCode(100)
                    .permission(
                            Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.CALL_PHONE,
                            Manifest.permission.CAMERA,
                            Manifest.permission.INTERNET
                    )
                   .rationale(new RationaleListener() {
                       @Override
                       public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                           AndPermission.rationaleDialog(LoginActivity.this, rationale).show();
                       }
                   })
                    .callback(permissionListener)
                    .start();
    }

    private PermissionListener permissionListener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, List<String> grantedPermissions) {
            // 权限申请成功回调。
            if (requestCode == 100) {
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
                        imei = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
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
                    deviceId.append("e"+deviceId.toString()+ JPushInterface.getRegistrationID(LoginActivity.this)
                    + PushServiceFactory.getCloudPushService().getDeviceId());
                }
                ServiceFactory.httpService()
                        .login(phone.getText().toString(), StringUitls.getMD5(password.getText().toString()), deviceId.toString())
//                        .compose(new DefaultTransformer2<AppLogin>())
                        .compose(new DefaultTransformer2<AppLogin>())
                        .subscribe(new RxSubscriber<AppLogin>(LoginActivity.this) {
                            @Override
                            public void onNext(AppLogin login) {
                                Contains.appLogin = login;
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putString("NAME", NameValue);
                                editor.putString("PASSWORD", PwdValue);
                                editor.putBoolean("ISCHECK", true);
                                editor.putBoolean("AUTO_ISCHECK", true);
                                editor.commit();
                                mExplosionField.explode(loginButton);
                                loginButton.setOnClickListener(null);
                                new Thread() {
                                    public void run() {
                                        try {
                                            sleep(1000);
                                            startActivity(MainActivity.class);
                                            finish();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }.start();
                            }

                        });
            }
        }

        @Override
        public void onFailed(int requestCode, List<String> deniedPermissions) {
            // 权限申请失败回调。
        }
    };

    private void Login() {
        //queryShipperInfo();
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
                imei = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
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
            deviceId.append("e"+deviceId.toString()+ JPushInterface.getRegistrationID(LoginActivity.this)
            +PushServiceFactory.getCloudPushService().getDeviceId());
        }
        ServiceFactory.httpService()
                .login(phone.getText().toString(), StringUitls.getMD5(password.getText().toString()), deviceId.toString())
                .compose(new DefaultTransformer2<AppLogin>())
                .subscribe(new RxSubscriber<AppLogin>(LoginActivity.this) {
                    @Override
                    public void onNext(AppLogin login) {
                        Contains.appLogin = login;
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("NAME", NameValue);
                        editor.putString("PASSWORD", PwdValue);
                        editor.putBoolean("ISCHECK", true);
                        editor.putBoolean("AUTO_ISCHECK", true);
                        editor.commit();
                        mExplosionField.explode(loginButton);
                        loginButton.setOnClickListener(null);
                        new Thread() {
                            public void run() {
                                try {
                                    sleep(1000);
                                    startActivity(MainActivity.class);
                                    finish();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }.start();
                    }

                });
    }

    private void addListener(final View loginButton) {
        if (loginButton instanceof ViewGroup) {
            ViewGroup parent = (ViewGroup) loginButton;
            for (int i = 0; i < parent.getChildCount(); i++) {
                addListener(parent.getChildAt(i));
            }
        } else {
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String tel = phone.getText().toString().trim();
                    String pwd = password.getText().toString().trim();
                    if (tel == null || tel.equals("")) {
                        phone.requestFocus();
                        Toast.makeText(LoginActivity.this, "请输入账号", Toast.LENGTH_SHORT).show();
                    } else if (pwd == null || pwd.equals("")) {
                        password.requestFocus();
                        Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    } else {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(loginButton, InputMethodManager.SHOW_FORCED);
                        imm.hideSoftInputFromWindow(loginButton.getWindowToken(), 0); //强制隐藏键盘
                        //保存用户名和密码
                        NameValue = phone.getText().toString();
                        PwdValue = password.getText().toString();
                        Login();
                    }
                }
            });
        }
    }
}

