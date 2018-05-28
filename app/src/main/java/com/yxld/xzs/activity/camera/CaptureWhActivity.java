package com.yxld.xzs.activity.camera;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.tbruyelle.rxpermissions.Permission;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.yxld.xzs.R;
import com.yxld.xzs.base.BaseActivity;
import com.yxld.xzs.utils.ImageUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;

/**
 * Created by William on 2018/1/26.
 */

public class CaptureWhActivity extends BaseActivity {
    @BindView(R.id.fl_my_container)
    FrameLayout flMyContainer;

    private CaptureFragment captureFragment;

    private String url2;
    private int REQUEST_IMAGE = 160;//相册

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        captureFragment = new CaptureFragment();
        // 为二维码扫描界面设置定制化界面
        CodeUtils.setFragmentArgs(captureFragment, R.layout.my_camera);
        captureFragment.setAnalyzeCallback(analyzeCallback);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_my_container,
                captureFragment).commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_scanand_alubm, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.camera_save) {
            //overridePendingTransition(R.anim.translate_left_to_center, R.anim
            // .translate_center_to_right);
            initPermission();
        } else if (id == android.R.id.home) {
            onBackPressed();
            //overridePendingTransition(R.anim.translate_left_to_center, R.anim
            // .translate_center_to_right);
        }
        return true;
    }

    /**
     * 请求权限
     */
    private void initPermission() {
        RxPermissions rxPermissions = new RxPermissions(this);
        // 多权限申请要用request方法
        rxPermissions.requestEach(Manifest.permission.READ_EXTERNAL_STORAGE).subscribe(new Action1<Permission>() {
            @Override
            public void call(Permission permission) {
                if (permission.granted) {
//                    Toast.makeText(CaptureWhActivity.this, "有权限了", Toast.LENGTH_SHORT)
//                            .show();
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, REQUEST_IMAGE);
                } else if (permission.shouldShowRequestPermissionRationale) {
                    // Denied permission without ask never again
                    Toast.makeText(CaptureWhActivity.this, "没有访问也没有拒绝", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    // Denied permission with ask never again
                    // Need to go to the settings
                    Toast.makeText(CaptureWhActivity.this, "没有权限,您不能进入相册,请进入设置打开权限", Toast
                            .LENGTH_SHORT)
                            .show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE) {
            if (data != null) {
                Uri uri = data.getData();
                try {
                    CodeUtils.analyzeBitmap(ImageUtil.getImageAbsolutePath(this, uri), new
                            CodeUtils.AnalyzeCallback() {
                        @Override
                        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
//                            Toast.makeText(CaptureWhActivity.this, "解析结果:" + result, Toast
// .LENGTH_LONG).show();
                            if (result.contains(";@token==")) {
                                int i = result.indexOf(";@token==");
                                String substring = result.substring(0, i);

                                Intent resultIntent = new Intent();
                                Bundle bundle = new Bundle();
                                bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_SUCCESS);
                                bundle.putString(CodeUtils.RESULT_STRING, substring);
                                resultIntent.putExtras(bundle);
                                CaptureWhActivity.this.setResult(RESULT_OK, resultIntent);
                                CaptureWhActivity.this.finish();
                            } else {
                                Toast.makeText(CaptureWhActivity.this, "解析二维码失败", Toast.LENGTH_LONG)
                                        .show();
                            }
                        }
                        @Override
                        public void onAnalyzeFailed() {
                            Toast.makeText(CaptureWhActivity.this, "解析二维码失败", Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public boolean supportSlideBack() {
        //禁用滑动返回
        return false;
    }

    /**
     * 二维码解析回调函数
     */
    CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_SUCCESS);
            bundle.putString(CodeUtils.RESULT_STRING, result);
            resultIntent.putExtras(bundle);
            CaptureWhActivity.this.setResult(RESULT_OK, resultIntent);
            CaptureWhActivity.this.finish();
        }

        @Override
        public void onAnalyzeFailed() {
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_FAILED);
            bundle.putString(CodeUtils.RESULT_STRING, "");
            resultIntent.putExtras(bundle);
            CaptureWhActivity.this.setResult(RESULT_OK, resultIntent);
            CaptureWhActivity.this.finish();
        }
    };
}
