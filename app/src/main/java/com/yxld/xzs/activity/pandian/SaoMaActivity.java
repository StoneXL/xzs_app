package com.yxld.xzs.activity.pandian;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.socks.library.KLog;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.yxld.xzs.R;
import com.yxld.xzs.base.BaseActivity;
import com.yxld.xzs.contain.Contains;
import com.yxld.xzs.entity.BaseBack;
import com.yxld.xzs.entity.RimOrderBean;
import com.yxld.xzs.entity.RimOrderListBean;
import com.yxld.xzs.http.api.HttpAPIWrapper;
import com.yxld.xzs.utils.StringUitl;
import com.yxld.xzs.utils.ToastUtil;
import com.zhy.autolayout.AutoFrameLayout;

import java.util.ArrayList;
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
 * @Date 2018/5/8.
 */

public class SaoMaActivity extends BaseActivity {
    @BindView(R.id.fl_my_container)
    AutoFrameLayout mFlMyContainer;
    @BindView(R.id.iv_shoudiantong)
    ImageView mIvShoudiantong;
    @BindView(R.id.tv_content)
    TextView mTvContent;
    @BindView(R.id.tv_one)
    TextView mTvOne;
    @BindView(R.id.tv_two)
    TextView mTvTwo;
    @BindView(R.id.et_input)
    EditText mEtInput;
    private CaptureFragment captureFragment;
    private String pandianId; //盘点id
    private String tiaoXingMa;//条形码编号
    private boolean isLight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saoma);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pandianId = getIntent().getStringExtra("pandianId");
        initView();
    }

    private void initView() {
        captureFragment = new CaptureFragment();
        // 为二维码扫描界面设置定制化界面
        CodeUtils.setFragmentArgs(captureFragment, R.layout.my_saoma);
        captureFragment.setAnalyzeCallback(analyzeCallback);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_my_container, captureFragment).commit();
    }


    /**
     * 二维码解析回调函数
     */
    CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
            KLog.i("扫码结果：" + result);
            tiaoXingMa = result;
        }

        @Override
        public void onAnalyzeFailed() {
            KLog.i("解析失败");

        }
    };

    @OnClick({R.id.iv_shoudiantong, R.id.et_input, R.id.tv_queding, R.id.tv_one, R.id.tv_two})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.iv_shoudiantong:
                isLight = !isLight;
                if (isLight) {
                    CodeUtils.isLightEnable(true);//开闪光灯
                    mIvShoudiantong.setImageResource(R.mipmap.shoudiantong_on);

                } else {
                    CodeUtils.isLightEnable(false);//关闪光灯
                    mIvShoudiantong.setImageResource(R.mipmap.shoudiantong_off);
                }
                break;
            case R.id.et_input:
                mEtInput.setCursorVisible(true);
                break;
            case R.id.tv_queding:
                tiaoXingMa = mEtInput.getText().toString().trim();
                if (!StringUitl.isNoEmpty(tiaoXingMa)){
                    ToastUtil.showToast(this,"请扫描条形码或输入条形码");
                    return;
                }
                intent = new Intent(this, PanDianDetailActivity.class);
                intent.putExtra("wuziBianhao", tiaoXingMa);
                intent.putExtra("pandianId", pandianId);
                startActivity(intent);
                break;
            case R.id.tv_one:
                yichangConfirm();
                break;
            case R.id.tv_two:
                intent = new Intent(this, WeiPanDianListActivity.class);
                intent.putExtra("pandianId", pandianId);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    /**
     * 盘点异常确认
     */
    private void yichangConfirm() {
        Map<String, String> map = new HashMap<>();
        map.put("uuId", Contains.uuid);
        map.put("pandianId", pandianId);
        Disposable disposable = HttpAPIWrapper.getInstance(HttpAPIWrapper.getOkHttpClient())
                .yiChangBeforConfirm(map)
                .subscribe(new Consumer<BaseBack>() {
                    @Override
                    public void accept(@NonNull BaseBack data) throws Exception {

                        Log.e("wh", "加载完成" + data.toString());
                        if (data.status == 1) {
                            // TODO: 2018/5/28 成功跳异常列表页面
                            startYiChang();
                        } else {
                            // TODO: 2018/5/28  还有未盘点列表未完成
                            onError(data.status, data.msg);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        Log.e("wh", "结束盘点, 准备进行盘点异常确认 "+"失败");
                    }
                });
        disposables.add(disposable);
    }

    private void startYiChang() {
        Intent intent = new Intent(this, PanDianYiChangListActivity.class);
        intent.putExtra("pandianId", pandianId);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isLight) {
            CodeUtils.isLightEnable(false);//关闪光灯
        }
    }
}
