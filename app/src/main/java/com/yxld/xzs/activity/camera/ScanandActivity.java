package com.yxld.xzs.activity.camera;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.yxld.xzs.R;
import com.yxld.xzs.base.BaseActivity;
import com.yxld.xzs.contain.Contains;
import com.yxld.xzs.entity.BaseBack;
import com.yxld.xzs.http.api.HttpAPIWrapper;
import com.yxld.xzs.utils.ToastUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import static com.yxld.xzs.http.api.API.IP_PRODUCT;

/**
 * Created by William on 2018/1/25.
 */

public class ScanandActivity extends BaseActivity {
    @BindView(R.id.bt_confirm)
    Button btConfirm;
    @BindView(R.id.bt_cancel)
    Button btCancel;

    private String url2;
    private int REQUEST_CODE_SCANAND = 111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acanand);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        url = getIntent().getStringExtra("result");

//        Intent intent = new Intent(this, CaptureActivity.class);
        Intent intent = new Intent(this, CaptureWhActivity.class);
        startActivityForResult(intent, REQUEST_CODE_SCANAND);
    }

    @OnClick({R.id.bt_confirm, R.id.bt_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_confirm:
                secondScan(url2);
//                Intent intent = new Intent(this, CaptureActivity.class);
//                startActivityForResult(intent, REQUEST_CODE_SCANAND);
                break;
            case R.id.bt_cancel:
                finish();
                break;
        }
    }

    /**
     * 扫码成功之后第二次通知
     *
     * @param result
     */
    private void secondScan(String result) {
        progressDialog.show();
        String url = IP_PRODUCT + "/notice/" + result + "/" + Contains.uuid + ".mvc";
//        Log.e("wh", "url " + url);
        Map<String, String> map = new HashMap<>();
        Disposable disposable = HttpAPIWrapper.getInstance(HttpAPIWrapper.getOkHttpClient())
                .firstScan(url, map)
                .subscribe(new Consumer<BaseBack>() {
                    @Override
                    public void accept(@NonNull BaseBack data) throws Exception {
                        progressDialog.hide();
                        if (data.status == 1) {
                            ToastUtil.showInfo(ScanandActivity.this, "成功");
                            finish();
                        } else {
                            ToastUtil.showInfo(ScanandActivity.this, data.msg);
                            finish();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        progressDialog.hide();
                        finish();
                    }
                });
        disposables.add(disposable);
    }

    /**
     * 扫码成功之后第一次通知
     *
     * @param result
     */
    private void firstScan(final String result) {
        progressDialog.show();
        String url = IP_PRODUCT + "/notice/" + result + ".mvc";
//        Log.e("wh", "url " + url);
        Map<String, String> map = new HashMap<>();
        Disposable disposable = HttpAPIWrapper.getInstance(HttpAPIWrapper.getOkHttpClient())
                .firstScan(url, map)
                .subscribe(new Consumer<BaseBack>() {
                    @Override
                    public void accept(@NonNull BaseBack data) throws Exception {
                        progressDialog.hide();
                        if (data.status == 1) {
                            ToastUtil.showInfo(ScanandActivity.this, data.msg);
//                            secondScan(url2);
//                            Intent intent = new Intent(ScanandActivity.this, ScanandActivity
// .class);
//                            intent.putExtra("result", result);
//                            startActivity(intent);
                        } else {
                            ToastUtil.showInfo(ScanandActivity.this, data.msg);
                            finish();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        progressDialog.hide();
                        finish();
                    }
                });
        disposables.add(disposable);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_SCANAND) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
//                    Toast.makeText(this, "解析结果:" + result, Toast.LENGTH_LONG).show();
                    if (result.contains(";@token==")) {
                        int i = result.indexOf(";@token==");
                        String substring = result.substring(0,i);
//                        Log.e("wh", "result " + result + " i " + i + " substring " + substring);
                        url2 = substring;
                        firstScan(substring);
                    } else {
                        ToastUtil.showInfo(ScanandActivity.this, "无效二维码");
                        finish();
                    }
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(ScanandActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
                    finish();
                }
            } else {
                finish();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!disposables.isDisposed()) {
            disposables.dispose();
        }
    }

}
