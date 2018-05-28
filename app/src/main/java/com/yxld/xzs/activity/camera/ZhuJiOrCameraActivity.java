package com.yxld.xzs.activity.camera;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.yxld.xzs.R;
import com.yxld.xzs.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author xlei
 * @Date 2018/1/9.
 */

public class ZhuJiOrCameraActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        setContentView(R.layout.activity_zhuji_or_camera);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @OnClick({R.id.btn_xzsxt, R.id.bt_tjzj})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btn_xzsxt:
                intent = new Intent(this, StationActivity.class);
                intent.putExtra(StationActivity.ENTER_TYPE, 1);
                startActivity(intent);
                break;
            case R.id.bt_tjzj:
                startActivity(SearchCameraActivity.class);
                break;
            default:
                break;
        }
    }
}
