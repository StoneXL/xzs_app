package com.yxld.xzs.activity.camera;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.p2p.core.P2PHandler;
import com.socks.library.KLog;
import com.yxld.xzs.R;
import com.yxld.xzs.base.BaseActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author hu
 * @Package com.yxld.yxchuangxin.ui.activity.camera
 * @Description: $description
 * @date 2017/06/21 10:22:39
 */

public class CameraVideoActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.switch_video)
    Switch switchVideo;
    @BindView(R.id.setting_ring)
    TextView settingRing;
    @BindView(R.id.alarm_reverse)
    TextView alarmReverse;
    @BindView(R.id.switch_reverse)
    Switch switchReverse;

    private String deviceId, devicePwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        initView();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        deviceId = bundle.getString("deviceId");
        devicePwd = bundle.getString("devicePwd");
//        P2PHandler.getInstance().setRecordType(deviceId, devicePwd, 0);
        devicePwd = P2PHandler.getInstance().EntryPassword(devicePwd);
        P2PHandler.getInstance().getNpcSettings(deviceId, devicePwd);
    }

    protected void initView() {
        setContentView(R.layout.activity_camera_video);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressDialog.show();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Logger.e("------------"+buttonView.getId()+isChecked);
        switch (buttonView.getId()) {
            case R.id.switch_video:
                if (isChecked) {
                    P2PHandler.getInstance().setRemoteRecord(deviceId, devicePwd, 1);
                    KLog.i("设置开");
                } else {
                    P2PHandler.getInstance().setRemoteRecord(deviceId, devicePwd, 0);
                    KLog.i("设置关");
                }
                break;
            case R.id.switch_reverse:
                if (isChecked) {
                    P2PHandler.getInstance().setImageReverse(deviceId, devicePwd, 1);
                    KLog.i("设置图像翻转1");
                } else {
                    P2PHandler.getInstance().setImageReverse(deviceId, devicePwd, 0);
                    KLog.i("设置图像翻转0");
                }
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void SettingEventBus(String state) {
        progressDialog.hide();
        KLog.i(state);
        switch (state) {
            case "GetRemoteRecordResult1":
                switchVideo.setChecked(true);
                switchVideo.setOnCheckedChangeListener(this);
                break;
            case "GetRemoteRecordResult0":
                switchVideo.setChecked(false);
                switchVideo.setOnCheckedChangeListener(this);
                break;
            case "ImageReverse0":    //0是不翻转
                KLog.i("ImageReverse0是不翻转");
                switchReverse.setChecked(false);
                break;
            case "ImageReverse1":       //1是翻转
                KLog.i("ImageReverse1是翻转");
                switchReverse.setChecked(true);
                break;
            case "SetImageReverse0":
                Toast.makeText(this, "操作成功", Toast.LENGTH_SHORT).show();
                break;
        }
        switchReverse.setOnCheckedChangeListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}