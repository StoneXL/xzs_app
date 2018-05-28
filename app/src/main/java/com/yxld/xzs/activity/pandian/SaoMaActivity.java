package com.yxld.xzs.activity.pandian;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.socks.library.KLog;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.yxld.xzs.R;
import com.yxld.xzs.base.BaseActivity;
import com.zhy.autolayout.AutoFrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


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
            tiaoXingMa=result;
        }

        @Override
        public void onAnalyzeFailed() {
            KLog.i("解析失败");

        }
    };

    @OnClick({R.id.iv_shoudiantong, R.id.et_input, R.id.tv_queding, R.id.tv_one, R.id.tv_two})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_shoudiantong:
                break;
            case R.id.et_input:
                mEtInput.setCursorVisible(true);
                break;
            case R.id.tv_queding:
                tiaoXingMa = mEtInput.getText().toString().trim();
                Intent intent = new Intent(this, PanDianDetailActivity.class);
                intent.putExtra("wuziBianhao", tiaoXingMa);
                intent.putExtra("pandianId", pandianId);
                startActivity(intent);
                break;
            case R.id.tv_one:
                break;
            case R.id.tv_two:
                startActivity(WeiPanDianListActivity.class);
                break;
            default:
                break;
        }
    }
}
