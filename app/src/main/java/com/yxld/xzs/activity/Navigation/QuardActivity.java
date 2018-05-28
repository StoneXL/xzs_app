package com.yxld.xzs.activity.Navigation;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;
import com.yxld.xzs.R;
import com.yxld.xzs.base.BaseActivity;
import com.yxld.xzs.contain.Contains;
import com.yxld.xzs.entity.AppCode;
import com.yxld.xzs.http.exception.ApiException;
import com.yxld.xzs.http.exception.ServiceFactory1;
import com.yxld.xzs.subscribers.RxSubscriber;
import com.yxld.xzs.transformer.DefaultTransformer;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：yishangfei on 2017/1/5 0005 17:57
 * 邮箱：yishangfei@foxmail.com
 * 特殊门禁
 */
public class QuardActivity extends BaseActivity {
    @BindView(R.id.code_image)
    ImageView codeImage;
    @BindView(R.id.code_text)
    TextView codeText;

    public Bitmap mBitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quard);
        ButterKnife.bind(this);
        ZXingLibrary.initDisplayOpinion(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Quard();
    }



    //网络请求
    private void Quard() {
        Logger.d("姓名"+Contains.appLogin.getAdminNickName());
        Logger.d("密码"+Contains.appLogin.getCxwyPeisongPhone());
        ServiceFactory1.httpService()
                .quard(Contains.appLogin.getAdminNickName(),Contains.appLogin.getCxwyPeisongPhone())
                .compose(new DefaultTransformer<AppCode>())
                .subscribe(new RxSubscriber<AppCode>(this) {
                    @Override
                    public void onNext(AppCode quard) {
                        String textContent = quard.getCode().toString();
                        if (quard.getTimr().equals("") || quard.getCode().equals("您沒有有效的授权二维码")) {
                            Toast.makeText(QuardActivity.this, "您没有有效的授权二维码", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        mBitmap = CodeUtils.createImage(textContent, 380, 380, BitmapFactory.decodeResource(getResources(), R.mipmap.icon_launcher));
                        codeImage.setImageBitmap(mBitmap);
                        codeText.setText(quard.getTimr());
                    }

                    @Override
                    protected void onError(ApiException ex) {
                        super.onError(ex);
                        Toast.makeText(QuardActivity.this, "获取失败,请稍后再试", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}

