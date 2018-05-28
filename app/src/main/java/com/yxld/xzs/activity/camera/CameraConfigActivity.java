package com.yxld.xzs.activity.camera;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hdl.udpsenderlib.UDPResult;
import com.jwkj.soundwave.ResultCallback;
import com.jwkj.soundwave.SoundWaveManager;
import com.jwkj.soundwave.SoundWaveSender;
import com.jwkj.soundwave.bean.NearbyDevice;
import com.socks.library.KLog;
import com.yxld.xzs.R;
import com.yxld.xzs.base.BaseActivity;
import com.yxld.xzs.yoosee.UDPHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author hu
 * @Package com.yxld.yxchuangxin.ui.activity.camera
 * @Description: $description
 * @date 2017/06/21 10:21:40
 */

public class CameraConfigActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.camera_config_name)
    TextView cameraConfigName;
    @BindView(R.id.camera_config_ssid)
    TextView cameraConfigSsid;
    @BindView(R.id.camera_config_pwd)
    TextInputEditText cameraConfigPwd;
    @BindView(R.id.camera_config_commit)
    Button cameraConfigCommit;
    private UDPHelper mHelper;
    private boolean isNeedSendWave = true;//是否需要发送声波，没有接到正确数据之前都需要发送哦
    private int mEnterType;//1表示岗位监控的配网 2 表示居家安防的配网
    private String ssid;
    private String pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    protected void initView() {
        setContentView(R.layout.activity_camera_config);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mEnterType = getIntent().getIntExtra("enter_type", 1);
        cameraConfigCommit.setOnClickListener(this);
        boolean isSuccess = SoundWaveManager.init(this);//初始化声波配置
        KLog.i("isSuccess=" + isSuccess);
        getConnectWifiSsid();
        mHelper = new UDPHelper(9988);
        listen();
        mHelper.StartListen();
    }


    private void sendWifi() {
        KLog.i("开始连接");
        ssid = cameraConfigSsid.getText().toString();
        pwd = cameraConfigPwd.getText().toString().trim();
        KLog.i("连接的wifi为" + ssid);
        KLog.i("连接的wifi密码为" + pwd);
        if (pwd.equals("") || ssid.equals("")) {
            Toast.makeText(CameraConfigActivity.this, "请输入连接wifi并输入密码", Toast.LENGTH_SHORT).show();
        } else {
            progressDialog.show();
            isNeedSendWave = true;//每次点击发送都要设置为可以继续发送
            KLog.i("声波发送中....");
            sendSoundWave();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (!isSendWifiStop) {
//            stopSendWifi();
//        }

        if (mHelper != null) {
            mHelper.StopListen();
        }
        SoundWaveSender.getInstance().with(this).stopSend();
        SoundWaveManager.onDestroy(this);
    }

    @Override
    public void onClick(View v) {
        hideKeyboard();
        sendWifi();
    }

    private void listen() {

        mHelper.setCallBack(new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                switch (msg.what) {
                    case UDPHelper.HANDLER_MESSAGE_BIND_ERROR:
                        progressDialog.hide();
                        Toast.makeText(CameraConfigActivity.this, "配网失败", Toast.LENGTH_SHORT).show();
                        KLog.i("配网失败");
                        break;
                    case UDPHelper.HANDLER_MESSAGE_RECEIVE_MSG:
                        isNeedSendWave=false;
                        if (mEnterType == 1) {
                            //根据flag跳转到对应的界面,flag为0就是没有初始密码,为1就是有初始密码
                            String ip = msg.getData().getString("ip");
                            String contactId = msg.getData().getString("contactId");
                            String frag = msg.getData().getString("frag");
                            String ipFlag = msg.getData().getString("ipFlag");
                            progressDialog.hide();
                            Toast.makeText(CameraConfigActivity.this, "配网成功 ip:" + ip + "contactId", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(CameraConfigActivity.this, CameraAddActivity.class);
                            intent.putExtra("ishasContactId", true);
                            intent.putExtra("ip", ip);
                            intent.putExtra("contactId", contactId);
                            intent.putExtra("frag", frag);
                            intent.putExtra("ipFlag", ipFlag);
                            startActivity(intent);
                            KLog.i("配网成功 ip:" + ip + "contactId:" + contactId + "frag:" + frag + "ipFlag:" + ipFlag);
                            finish();
                        } else if (mEnterType == 2) {
                            //根据flag跳转到对应的界面,flag为0就是没有初始密码,为1就是有初始密码
                            String ip = msg.getData().getString("ip");
                            String contactId = msg.getData().getString("contactId");
                            String frag = msg.getData().getString("frag");
                            String ipFlag = msg.getData().getString("ipFlag");
                            progressDialog.hide();
                            Toast.makeText(CameraConfigActivity.this, "配网成功 ip:" + ip + "contactId", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(CameraConfigActivity.this, CameraAddNewActivity.class);
                            intent.putExtra("ishasContactId", true);
                            intent.putExtra("ip", ip);
                            intent.putExtra("contactId", contactId);
                            intent.putExtra("frag", frag);
                            intent.putExtra("ipFlag", ipFlag);
                            startActivity(intent);
                            KLog.i("配网成功 ip:" + ip + "contactId:" + contactId + "frag:" + frag + "ipFlag:" + ipFlag);
                            finish();
                        }
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private String getConnectWifiSsid() {
        WifiManager wifiManager = (WifiManager) getApplication().getApplicationContext().getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        KLog.i("wifiInfo", wifiInfo.toString());
        KLog.i("SSID", wifiInfo.getSSID());
        if (wifiInfo.getSSID() == null) {
            Toast.makeText(CameraConfigActivity.this, "请连接wifi后进行首次发包", Toast.LENGTH_SHORT).show();
        } else {
            String ssid = wifiInfo.getSSID();
            ssid = ssid.substring(1, ssid.length() - 1);
            cameraConfigSsid.setText(ssid);
        }
        return wifiInfo.getSSID();
    }

    //隐藏键盘
    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getApplication().getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 开始发送声波
     */
    private void sendSoundWave() {
        SoundWaveSender.getInstance()
                .with(this)
                .setWifiSet(ssid, pwd)
                .send(new ResultCallback() {

                    @Override
                    public void onNext(UDPResult udpResult) {
                        NearbyDevice device = NearbyDevice.getDeviceInfoByByteArray(udpResult.getResultData());
                        device.setIp(udpResult.getIp());
                        KLog.i("设备联网成功：（设备信息）" + device.toString());
                        isNeedSendWave = false;
                        SoundWaveSender.getInstance().stopSend();//收到数据之后，需要发送
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        super.onError(throwable);
                        KLog.e("" + throwable);
                       // SoundWaveSender.getInstance().stopSend();//出错了就要停止任务，然后重启发送
                    }

                    /**
                     * 当声波停止的时候
                     */
                    @Override
                    public void onStopSend() {
                        if (isNeedSendWave) {//是否需要继续发送声波
                            KLog.i("继续发送声波...");
                            sendSoundWave();
                        } else {//结束了就需要将发送器关闭
                            SoundWaveSender.getInstance().stopSend();
                        }
                    }
                });
    }

}