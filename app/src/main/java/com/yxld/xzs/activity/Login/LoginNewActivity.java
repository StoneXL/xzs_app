package com.yxld.xzs.activity.Login;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.sdk.android.push.common.util.ThreadPoolFactory;
import com.socks.library.KLog;
import com.yxld.xzs.R;
import com.yxld.xzs.activity.index.HomeActivity;
import com.yxld.xzs.base.BaseActivity;
import com.yxld.xzs.base.DemoApplicationLike;
import com.yxld.xzs.contain.Contains;
import com.yxld.xzs.entity.AppLogin;
import com.yxld.xzs.http.api.HttpAPIWrapper;
import com.yxld.xzs.utils.StringUitl;
import com.yxld.xzs.utils.StringUitls;
import com.yxld.xzs.utils.ToastUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import tyrantgit.explosionfield.ExplosionField;

import static java.lang.Thread.sleep;

/**
 * @author xlei
 * @Date 2017/11/15.
 */

public class LoginNewActivity extends BaseActivity {
    private static final String KEY_RELOGIN_NEW = "key_relogin";
    @BindView(R.id.et_account)
    EditText mEtAccount;
    @BindView(R.id.et_password)
    EditText mEtPassword;
    @BindView(R.id.btn_login)
    Button mBtnLogin;

    /**
     * 粒子爆炸效果
     */
    private ExplosionField mExplosionField;
    /**
     * 账号和密码
     */
    private String mAccount, mPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_new);
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey("RELOGIN")) {
            DemoApplicationLike.getApp().mAppActivityManager.finishAllActivityWithoutThis();
        }
        ButterKnife.bind(this);
        toolbar.setVisibility(View.GONE);
        initView();
    }

    private void initView() {
        mExplosionField = ExplosionField.attach2Window(this);
        if (!StringUitl.isEmpty(sp.getString("NAME", "")) && !StringUitl.isEmpty(sp.getString("PASSWORD", ""))) {
            //设置默认是记录密码状态
            mEtAccount.setText(sp.getString("NAME", ""));
            mEtPassword.setText(sp.getString("PASSWORD", ""));
            //主动去调用控件的点击事件（模拟人手去触摸控件）
            mBtnLogin.performClick();
        }
    }

    /**
     * 登录
     */
    private void Login() {
        showProgressDialog(false);
        Map<String, String> map = new HashMap<>();
        map.put("username", mEtAccount.getText().toString());
        map.put("password", StringUitls.getMD5(mEtPassword.getText().toString()));
        map.put("macAddr", StringUitl.getDeviceId(this));
        Disposable disposable = HttpAPIWrapper.getInstance(HttpAPIWrapper.getOkHttpClient()).Login(map).
                subscribe(new Consumer<AppLogin>() {
                    @Override
                    public void accept(@NonNull AppLogin appLogin) throws Exception {
                        dismissProgressDialog();
                        if (appLogin.status != 0) {
                            ToastUtil.showInfo(LoginNewActivity.this, appLogin.getMSG());
                            return;
                        }
                        Contains.appLogin = appLogin.getRow();
                        Contains.uuid = appLogin.getUuid();
                        KLog.i("LoginNewActivity--------->" + Contains.appLogin.toString());
                        KLog.i("uuid" + Contains.uuid);
                        KLog.i("uuid" + StringUitl.getDeviceId(LoginNewActivity.this));
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("NAME", mAccount);
                        editor.putString("PASSWORD", mPwd);
                        // TODO: 2017/12/2 这两个检查状态用来判断什么了呢?
                        editor.putBoolean("ISCHECK", true);
                        editor.putBoolean("AUTO_ISCHECK", true);
                        editor.commit();
                        mExplosionField.explode(mBtnLogin);
                        mBtnLogin.setOnClickListener(null);
                        ThreadPoolFactory.execute(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    sleep(1000);
                                    startActivity(HomeActivity.class);
                                    finish();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        dismissProgressDialog();
                        KLog.i("onError" + throwable.toString());
                    }
                });
        disposables.add(disposable);
    }

    @OnClick(R.id.btn_login)
    public void onViewClicked() {
        String tel = mEtAccount.getText().toString().trim();
        String pwd = mEtPassword.getText().toString().trim();
        if (tel == null || tel.equals("")) {
            mEtAccount.requestFocus();
            Toast.makeText(LoginNewActivity.this, "请输入账号", Toast.LENGTH_SHORT).show();
        } else if (pwd == null || pwd.equals("")) {
            mEtPassword.requestFocus();
            Toast.makeText(LoginNewActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
        } else {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(mBtnLogin, InputMethodManager.SHOW_FORCED);
            imm.hideSoftInputFromWindow(mBtnLogin.getWindowToken(), 0); //强制隐藏键盘
            //保存用户名和密码
            mAccount = mEtAccount.getText().toString();
            mPwd = mEtPassword.getText().toString();
            Login();
        }
    }

}
