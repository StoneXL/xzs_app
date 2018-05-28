package com.yxld.xzs.activity.Login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.marlonmafra.android.widget.EditTextPassword;
import com.yxld.xzs.R;
import com.yxld.xzs.base.BaseActivity;
import com.yxld.xzs.entity.AppLogin;
import com.yxld.xzs.http.ServiceFactory;
import com.yxld.xzs.subscribers.RxSubscriber;
import com.yxld.xzs.transformer.DefaultTransformer;
import com.yxld.xzs.transformer.DefaultTransformer2;
import com.yxld.xzs.utils.StringUitl;
import com.yxld.xzs.utils.StringUitls;
import com.yxld.xzs.view.DeleteEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yishangfei on 2017/2/14 0014.
 * 邮箱：yishangfei@foxmail.com
 * 修改密码
 */
public class ChangeActivity extends BaseActivity {
    @BindView(R.id.change_phone)
    DeleteEditText changePhone;//账号
    @BindView(R.id.change_password)
    EditTextPassword changePassword;//原密码
    @BindView(R.id.change_new_password)
    EditTextPassword changeNewPassword;//新密码
    @BindView(R.id.change_new1_password)
    EditTextPassword changeNew1Password;//重复新密码
    @BindView(R.id.change_account)
    LinearLayout changeAccount;
    @BindView(R.id.change_pwd)
    LinearLayout changePwd;
    @BindView(R.id.change_button)
    Button changeButton;//修改密码


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.change_button)
    public void onClick() {
        if (changeButton.getText().equals("下一步")){
            Check();
        }else {

        }

    }

    //验证要修改的账号是否正确
    private void Check() {
        String mac = StringUitl.getDeviceId(this);
        ServiceFactory.httpService()
                .login(changePhone.getText().toString(), StringUitls.getMD5(changePassword.getText().toString()), mac)
                .compose(new DefaultTransformer2<AppLogin>())
                .subscribe(new RxSubscriber<AppLogin>(this) {
                    @Override
                    public void onNext(AppLogin login) {
                        Toast.makeText(ChangeActivity.this, "账号校准成功。", Toast.LENGTH_SHORT).show();
                        changeAccount.setVisibility(View.GONE);
                        changePwd.setVisibility(View.VISIBLE);
                        changeButton.setText("确认修改");
                    }
                });
    }


    private void Change() {
        ServiceFactory.httpService()
                .change(changePhone.getText().toString(), StringUitls.getMD5(changePassword.getText().toString()), StringUitls.getMD5(changePassword.getText().toString()))
                .compose(new DefaultTransformer<AppLogin>())
                .subscribe(new RxSubscriber<AppLogin>(this) {
                    @Override
                    public void onNext(AppLogin login) {
                        new Thread() {
                            public void run() {
                                try {
                                    sleep(1000);
                                    finish();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            ;
                        }.start();
                    }
                });
    }
}
