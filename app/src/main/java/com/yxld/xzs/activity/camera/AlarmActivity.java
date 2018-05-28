package com.yxld.xzs.activity.camera;

import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.p2p.core.P2PHandler;
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
 * @date 2017/06/21 10:23:03
 */

public class AlarmActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.alarm_buzzer)
    TextView alarmBuzzer;
    @BindView(R.id.switch_buzzer)
    Switch switchBuzzer;
    @BindView(R.id.alarm_motion)
    TextView alarmMotion;
    @BindView(R.id.switch_motion)
    Switch switchMotion;

    private String deviceId, devicePwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        deviceId = bundle.getString("deviceId");
        devicePwd = bundle.getString("devicePwd");
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        initView();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void NpcSettingEventBus(String msg) {
        Log.d("...", "NpcSettingEventBus: " + msg);
        switch (msg) {
            case "Buzzer" + 0:
                switchBuzzer.setChecked(false);
                break;
            case "Buzzer" + 1:
                switchBuzzer.setChecked(true);
            case "Buzzer" + 2:
                switchBuzzer.setChecked(true);
            case "Buzzer" + 3:
                switchBuzzer.setChecked(true);
                break;
            case "Motion" + 0:
                switchMotion.setChecked(false);
                break;
            case "Motion" + 1:
                switchMotion.setChecked(true);
                break;
            case "SetBuzzer"+0:
                Toast.makeText(this, "操作成功", Toast.LENGTH_SHORT).show();
                break;
            case "SetMotion"+0:
                Toast.makeText(this, "操作成功", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.switch_buzzer:
                if (isChecked) {
                    P2PHandler.getInstance().setBuzzer(deviceId,devicePwd,3);
                } else {
                    P2PHandler.getInstance().setBuzzer(deviceId,devicePwd,0);
                }
                break;
            case R.id.switch_motion:
                if (isChecked) {
                    P2PHandler.getInstance().setMotion(deviceId,devicePwd,1);
                } else {
                    P2PHandler.getInstance().setMotion(deviceId,devicePwd,0);
                }
                break;
        }
    }

    protected void initView() {
        setContentView(R.layout.activity_alarm);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        P2PHandler.getInstance().getNpcSettings(deviceId, devicePwd);
        switchBuzzer.setOnCheckedChangeListener(this);
        switchMotion.setOnCheckedChangeListener(this);
    }
}