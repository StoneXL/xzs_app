package com.yxld.xzs.activity.camera;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.p2p.core.BasePlayBackActivity;
import com.p2p.core.P2PHandler;
import com.p2p.core.P2PView;
import com.socks.library.KLog;
import com.yxld.xzs.R;
import com.yxld.xzs.entity.RecordFile;
import com.yxld.xzs.utils.UIUtils;
import com.yxld.xzs.view.ProgressDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.p2p.core.P2PView.scale;

/**
 * @author hu
 * @Package com.yxld.yxchuangxin.ui.activity.camera
 * @Description: $description
 * @date 2017/06/21 10:22:52
 */

public class PlayBackActivity extends BasePlayBackActivity {

    @BindView(R.id.toolbarBusiness)
    Toolbar toolbarBusiness;
    @BindView(R.id.pview)
    P2PView pview;
    @BindView(R.id.rl_p2pview)
    RelativeLayout rlP2pview;
    //    @BindView(R.id.btn_palyback)
//    Button btnPalyback;
//    @BindView(R.id.tx_text)
//    TextView txText;
    private MediaPlayer mediaPlayer;
    private RecordFile recordFile;
    private String deviceId;
    private String devicePwd;
    private boolean isMute = false;
    private AudioManager manager;
    public static String P2P_ACCEPT = "com.yxld.P2P_ACCEPT";
    public static String P2P_READY = "com.yxld.P2P_READY";
    public static String P2P_REJECT = "com.yxld.P2P_REJECT";
    public ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    protected void initView() {
        setContentView(R.layout.activity_play_back);
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        toolbarBusiness.setPadding(0, UIUtils.getStatusBarHeight(this), 0, 0);
        setSupportActionBar(toolbarBusiness);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarBusiness.setVisibility(View.GONE);
        progressDialog = new ProgressDialog(this);
        Bundle bundle = getIntent().getBundleExtra("recordFile");
        recordFile = (RecordFile) bundle.getSerializable("file");
        deviceId = getIntent().getStringExtra("deviceId");
        devicePwd = getIntent().getStringExtra("devicePwd");
        Logger.d(recordFile + "=====" + deviceId + "----------" + devicePwd);
        initp2pView();
        regFilter();
        play();
    }


    /**
     * 切换视频全屏/小视频窗口(以切横竖屏切换替代)
     */
    private void switchOrientation() {
        if (deviceId.equals("") && devicePwd.equals("")) {
            Toast.makeText(this, "请先选择设备进行连接", Toast.LENGTH_SHORT).show();
        } else {
//            String pwd = P2PHandler.getInstance().EntryPassword(devicePwd);//经过转换后的设备密码
//            P2PHandler.getInstance().call(LoginID, pwd, true, 1, deviceId, "", "", 2, deviceId);
            // 横竖屏切换
            if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                    && getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE) {
                //横屏
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
            } else if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
                //竖屏
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        }
    }

    public void initp2pView() {
        pView = (P2PView) findViewById(R.id.pview);
        //7是设备类型(技威定义的)
        //LAYOUTTYPE_TOGGEDER 录像回放连接命令和P2P_ACCEPT、P2P_READY、P2P_REJECT等命令在同一界面
        //LAYOUTTYPE_SEPARATION 录像回放连接命令和P2P_ACCEPT、P2P_READY、P2P_REJECT等命令不在同一界面
        this.initP2PView(7, P2PView.LAYOUTTYPE_TOGGEDER);
        mediaPlayer = new MediaPlayer();
        pView.halfScreen();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCaptureScreenResult(boolean isSuccess, int prePoint) {

    }

    @Override
    protected void onVideoPTS(long videoPTS) {

    }

    @Override
    protected void onP2PViewSingleTap() {

    }


    public void regFilter() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(P2P_REJECT);
        filter.addAction(P2P_ACCEPT);
        filter.addAction(P2P_READY);
        registerReceiver(mReceiver, filter);
    }

    BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(P2P_ACCEPT)) {
                int[] type = intent.getIntArrayExtra("type");
                P2PView.type = type[0];
                scale = type[1];
                //  txText.append("\n 监控数据接收");
               KLog.i("dxsTest"+ "监控数据接收:" + deviceId);
                P2PHandler.getInstance().openAudioAndStartPlaying(2);//打开音频并准备播放，calllType与call时type一致
            } else if (intent.getAction().equals(P2P_READY)) {
                //  txText.append("\n 监控准备,开始监控");
                progressDialog.hide();
                KLog.i("dxsTest"+ "监控准备,开始监控" + deviceId);
                pView.sendStartBrod();
            } else if (intent.getAction().equals(P2P_REJECT)) {
                //    txText.append("\n 监控挂断");
                progressDialog.hide();
            }
        }
    };

    public void play() {
        progressDialog.show();
        String filename = recordFile.getName();
        //录像回放连接
        P2PHandler.getInstance().playbackConnect(deviceId,
                devicePwd, filename, recordFile.getPosition(), 0, 0, 896, 896, 0);
    }

    @Override
    public int getActivityInfo() {
        return 33;
    }

    @Override
    protected void onGoBack() {

    }

    @Override
    protected void onGoFront() {

    }

    @Override
    protected void onExit() {

    }


    @Override
    public void onDestroy() {
        progressDialog.hide();
        //防止内存泄漏
        mediaPlayer.release();
        mediaPlayer = null;
        pview = null;
        unregisterReceiver(mReceiver);
        P2PHandler.getInstance().reject();
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        KLog.i("onConfigurationChanged:" + newConfig.orientation);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            RelativeLayout.LayoutParams parames = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            pview.setLayoutParams(parames);//调整画布容器宽高(比例)
            toolbarBusiness.setVisibility(View.GONE);
        } else {
            //这里简写,只考虑了16:9的画面类型  大部分设备画面比例是这种
            int Heigh = UIUtils.getDisplayWidth(this) * 9 / 16;
            LinearLayout.LayoutParams parames = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            parames.height = Heigh;
            pview.setLayoutParams(parames);
            toolbarBusiness.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }
}