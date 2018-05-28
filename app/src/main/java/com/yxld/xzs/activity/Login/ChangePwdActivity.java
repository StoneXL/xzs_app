package com.yxld.xzs.activity.Login;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.sdk.android.push.common.util.ThreadPoolFactory;
import com.socks.library.KLog;
import com.tencent.bugly.crashreport.CrashReport;
import com.yxld.xzs.R;
import com.yxld.xzs.activity.Navigation.SettingsActivity;
import com.yxld.xzs.base.BaseActivity;
import com.yxld.xzs.contain.Contains;
import com.yxld.xzs.entity.BaseBack;
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

/**
 * @author xlei
 * @Date 2017/11/16.
 */

public class ChangePwdActivity extends BaseActivity {
    @BindView(R.id.et_ord_pwd)
    EditText mEtOrdPwd;
    @BindView(R.id.et_new_pwd1)
    EditText mEtNewPwd1;
    @BindView(R.id.et_new_pwd2)
    EditText mEtNewPwd2;


    private String mOldPwd;
    private String mNewPwd1;
    private String mNewPwd2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.btn_login)
    public void onViewClicked() {
        mOldPwd = mEtOrdPwd.getText().toString().trim();
        mNewPwd1 = mEtNewPwd1.getText().toString().trim();
        mNewPwd2 = mEtNewPwd2.getText().toString().trim();
        if (StringUitl.isEmpty(mOldPwd)) {
            Toast.makeText(this, "请输入原密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (StringUitl.isEmpty(mNewPwd1)) {
            Toast.makeText(this, "请输入新密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mNewPwd1.length() < 6 || mNewPwd1.length() > 16) {
            Toast.makeText(this, "新密码长度必须在6-16位之间", Toast.LENGTH_SHORT).show();
            return;
        }
        if (StringUitl.isEmpty(mNewPwd2)) {
            Toast.makeText(this, "请确认新密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!mNewPwd1.equals(mNewPwd2)) {
            Toast.makeText(this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
            return;
        }
        //网络请求接口
        Map<String, String> map = new HashMap<>();
        map.put("uuid", Contains.uuid);
        map.put("password", StringUitls.getMD5(mOldPwd));
        map.put("newPassword", StringUitls.getMD5(mNewPwd1));
        map.put("insurePassword", StringUitls.getMD5(mNewPwd2));
        KLog.i(StringUitls.getMD5(mOldPwd) + "--" + StringUitls.getMD5(mNewPwd1) + "--" + StringUitls.getMD5(mNewPwd2));
        Disposable disposable = HttpAPIWrapper.getInstance().changePwd(map).subscribe(new Consumer<BaseBack>() {
            @Override
            public void accept(@NonNull BaseBack baseBack) throws Exception {
                if (baseBack.status != 0) {
                    Toast.makeText(ChangePwdActivity.this, baseBack.getMSG(), Toast.LENGTH_SHORT).show();
                    return;
                }
                ToastUtil.show(ChangePwdActivity.this, "修改密码成功，请重新登录");
                exitLogin();
                finish();

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                KLog.e("onError" + throwable.toString());
            }
        });
        disposables.add(disposable);


    }
}
