package com.yxld.xzs.activity.Login;

import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.github.ihsg.patternlocker.OnPatternChangeListener;
import com.github.ihsg.patternlocker.PatternLockerView;
import com.socks.library.KLog;
import com.yxld.xzs.R;
import com.yxld.xzs.activity.index.HomeActivity;
import com.yxld.xzs.base.BaseActivity;
import com.yxld.xzs.utils.PatternHelper;
import com.yxld.xzs.utils.ToastUtil;
import com.yxld.xzs.utils.fingerlogin.FingerListener;
import com.yxld.xzs.utils.fingerlogin.JsFingerUtils;
import com.yxld.xzs.utils.fingerlogin.KeyguardLockScreenManager;
import com.yxld.xzs.view.FingerDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.yxld.xzs.activity.Navigation.AccountSafeActivity.SP_FINGER_STATE;
import static com.yxld.xzs.activity.Navigation.AccountSafeActivity.SP_PATTERN_STATE;

/**
 * @author xlei
 * @Date 2018/4/9.
 */

public class LoginPatternActivity extends BaseActivity implements FingerListener {
    @BindView(R.id.text_msg)
    TextView textMsg;
    @BindView(R.id.pattern_lock_view)
    PatternLockerView patternLockerView;
    @BindView(R.id.img_finger)
    TextView mImgFinger;
    @BindView(R.id.text_fenge)
    TextView mTextFenge;
    @BindView(R.id.img_bg)
    ImageView mImgBg;
    private FingerDialog dialog;
    private JsFingerUtils jsFingerUtils;
    private int error_num = 0;//识别失败次数
    private KeyguardLockScreenManager mKeyguardLockScreenManager;
    private PatternHelper patternHelper;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
            switch (msg.what) {
                //成功
                case 1:
                    startActivity(HomeActivity.class);
                    finish();
                    break;
                //不支持指纹
                case 2:
                    sp.edit().putBoolean(SP_FINGER_STATE, false).apply();
                    break;
                //验证失败
                case 3:
                    error_num = 0;
                    jsFingerUtils.cancelListening();
                    break;
                default:
                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_pattern);
        ButterKnife.bind(this);
        toolbar.setVisibility(View.GONE);
        initBg();
        initData();
        initDialog();
        if (sp.getBoolean(SP_FINGER_STATE, false)) {
            dialog.show();
            dialog.setConfirmVisible();
        } else {
            mImgFinger.setVisibility(View.GONE);
            mTextFenge.setVisibility(View.GONE);
        }
        if (sp.getBoolean(SP_PATTERN_STATE, false)) {
            this.patternLockerView.setOnPatternChangedListener(new OnPatternChangeListener() {
                @Override
                public void onStart(PatternLockerView view) {
                }

                @Override
                public void onChange(PatternLockerView view, List<Integer> hitList) {
                }

                @Override
                public void onComplete(PatternLockerView view, List<Integer> hitList) {
                    boolean isError = !isPatternOk(hitList);
                    view.updateStatus(isError);
                    updateMsg();
                }

                @Override
                public void onClear(PatternLockerView view) {
                    finishIfNeeded();
                }
            });

            this.textMsg.setText("绘制手势密码图案");
            this.patternHelper = new PatternHelper();
        } else {
            patternLockerView.setVisibility(View.GONE);
            textMsg.setVisibility(View.GONE);
        }

    }

    private void initBg() {
        //蒙层效果
        Glide.with(this).load(R.mipmap.xzs_bg).bitmapTransform(new BlurTransformation
                (this, 15))
                .into(new SimpleTarget<GlideDrawable>() {
                    @TargetApi(Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super
                            GlideDrawable> glideAnimation) {
                        mImgBg.setBackground(resource);
                    }
                });

    }

    private boolean isPatternOk(List<Integer> hitList) {
        this.patternHelper.validateForChecking(hitList);
        return this.patternHelper.isOk();
    }

    private void updateMsg() {
        this.textMsg.setText(this.patternHelper.getMessage());
        this.textMsg.setTextColor(this.patternHelper.isOk() ?
                getResources().getColor(R.color.blue) :
                getResources().getColor(R.color.red));
    }

    private void finishIfNeeded() {
        if (this.patternHelper.isFinish()) {
            if (this.patternHelper.isOk()) {
                startActivity(HomeActivity.class);
                finish();
            } else {
                this.textMsg.setText("验证手势密码图案失败");
                this.textMsg.setTextColor(getResources().getColor(R.color.red));
                ToastUtil.showToast(LoginPatternActivity.this, "验证手势密码图案失败");
                patternLockerView.setEnabled(false);
                //关闭指纹登录 和手势登录
                sp.edit().putBoolean(SP_FINGER_STATE, false).apply();
                sp.edit().putBoolean(SP_PATTERN_STATE, false).apply();
                //其他登录 清除账号信息
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("NAME", "");
                editor.putString("PASSWORD", "");
                editor.putBoolean("ISCHECK", false);
                editor.putBoolean("AUTO_ISCHECK", false);
                editor.commit();
                startActivity(LoginNewActivity.class);
                finish();
            }

        }
    }

    private void initDialog() {
        dialog = new FingerDialog(this);
        dialog.setCancelable(false);
        dialog.setOnConfirmListener(new FingerDialog.OnConfirmListener() {
            @Override
            public void onCancel() {
                dialog.dismiss();
                jsFingerUtils.cancelListening();
            }

            @Override
            public void onConfirm() {

            }
        });
    }

    protected void initData() {
        jsFingerUtils = new JsFingerUtils(this);
        mKeyguardLockScreenManager = new KeyguardLockScreenManager(this);
    }

    @OnClick({R.id.img_finger, R.id.tv_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_finger:
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
                dialog.show();
                jsFingerUtils.startListening(this);
                break;
            case R.id.tv_login:
                //关闭指纹登录 和手势登录
                sp.edit().putBoolean(SP_FINGER_STATE, false).apply();
                sp.edit().putBoolean(SP_PATTERN_STATE, false).apply();
                //其他登录 清除账号信息
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("NAME", "");
                editor.putString("PASSWORD", "");
                editor.putBoolean("ISCHECK", false);
                editor.putBoolean("AUTO_ISCHECK", false);
                editor.commit();
                startActivity(LoginNewActivity.class);
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onStartListening() {
        dialog.setMessage("请进行指纹验证...");
    }

    @Override
    public void onStopListening() {

    }

    @Override
    public void onSuccess(FingerprintManager.AuthenticationResult result) {
        dialog.setMessage("指纹验证成功");
        // 认证成功，开启指纹登录
        handler.sendEmptyMessageDelayed(1, 1000);
    }

    @Override
    public void onFail(boolean isNormal, String info) {

        KLog.i("isNormal" + isNormal);
        if (isNormal) {
            if (error_num == 5) {
                dialog.setErrMessageColor("尝试次数过多，请稍后重试");
                jsFingerUtils.cancelListening();
                handler.sendEmptyMessageDelayed(3, 1000);
            } else {
                error_num++;
                dialog.setErrMessageColor("指纹验证失败请重试");

            }
        } else {
            KLog.i(isNormal + "" + info);
            dialog.setErrMessageColor(info);
            handler.sendEmptyMessageDelayed(2, 2000);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        KLog.i("onPause");
        jsFingerUtils.cancelListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (dialog != null && dialog.isShowing()) {
            jsFingerUtils.startListening(LoginPatternActivity.this);
        }

    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
        if (errorCode == 7) {
            KLog.i(errorCode + errString.toString() + "");
            dialog.setErrMessageColor(errString.toString());
            handler.sendEmptyMessageDelayed(3, 2000);
        }

    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {

    }

}
