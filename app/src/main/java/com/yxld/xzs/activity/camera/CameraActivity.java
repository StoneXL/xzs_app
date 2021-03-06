package com.yxld.xzs.activity.camera;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.p2p.core.BaseMonitorActivity;
import com.p2p.core.P2PHandler;
import com.p2p.core.P2PValue;
import com.p2p.core.P2PView;
import com.socks.library.KLog;
import com.videogo.util.Utils;
import com.yxld.xzs.R;
import com.yxld.xzs.entity.eventbus.VideoVolume;
import com.yxld.xzs.utils.UIUtils;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.p2p.core.P2PView.scale;
import static com.yxld.xzs.activity.camera.DeviceActivity.LoginID;

/**
 * Created by yishangfei on 2016/8/9 0009.
 */

public class CameraActivity extends BaseMonitorActivity implements View.OnTouchListener {
    public static String P2P_ACCEPT = "com.yxld.P2P_ACCEPT";
    public static String P2P_READY = "com.yxld.P2P_READY";
    public static String P2P_REJECT = "com.yxld.P2P_REJECT";
    @BindView(R.id.image_link)
    ImageView imageLink;
    @BindView(R.id.image_mute)
    ImageView imageMute;
    @BindView(R.id.image_screen)
    ImageView imageScreen;
    @BindView(R.id.image_link1)
    ImageView imageLink1;
    @BindView(R.id.image_mute1)
    ImageView imageMute1;
    @BindView(R.id.image_screen1)
    ImageView imageScreen1;
    @BindView(R.id.bufang)
    TextView bufang;
    @BindView(R.id.yuyin)
    TextView yuyin;
    @BindView(R.id.baojing)
    TextView baojing;
    @BindView(R.id.zhuatu)
    TextView zhuatu;
    @BindView(R.id.zhuatu1)
    Button zhuatu1;
    @BindView(R.id.camera_set)
    AutoLinearLayout cameraSet;
    @BindView(R.id.p2pview)
    AutoRelativeLayout P2pview;
    @BindView(R.id.features)
    AutoRelativeLayout features;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.realplay_video_btn)
    ImageButton realplayVideoBtn;
    @BindView(R.id.realplay_video_btn1)
    Button realplayVideoBtn1;
    @BindView(R.id.camera_quality_btn)
    Button cameraQualityBtn;
    @BindView(R.id.camera_quality_btn1)
    Button cameraQualityBtn1;

    private MediaPlayer mediaPlayer;
    private ProgressDialog progressDialog;
    private int screenWidth, screenHeigh;
    private boolean isMute = false;
    private String deviceId, devicePwd, deviceName;//设备号  设备密码
    private boolean isRecord = false; //记录是否在录像
    private String pathName = "";
    private PopupWindow mQualityPopupWindow;
    private int videoMode = 5;   //VIDEO_MODE_SD = 5(标清); VIDEO_MODE_LD = 6(流畅); VIDEO_MODE_HD = 7;
    private AudioManager manager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        regFilter();
        devicePwd = P2PHandler.getInstance().EntryPassword(devicePwd);//经过转换后的设备密码
        P2PHandler.getInstance().call(LoginID, devicePwd, true, 1, deviceId, "", "", 2, deviceId);
    }

    private void initView() {
        initUI();
        getScreenWithHeigh();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
//        AutoLinearLayout.LayoutParams lp = new AutoLinearLayout.LayoutParams(UIUtils.getDisplayWidth(this), (int)(UIUtils.getStatusBarHeight(this) * 3));
//        toolbar.setLayoutParams(lp);
//        toolbar.setPadding(0, (int) (UIUtils.getStatusBarHeight(this) * 0.6), 0, 0);
//        toolbar.setTitleMarginTop((int) (UIUtils.getStatusBarHeight(this) *0.55));
        toolbar.setPadding(0, UIUtils.getStatusBarHeight(this), 0, 0);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        realplayVideoBtn.setEnabled(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initUI() {
        progressDialog = new ProgressDialog(this);
        progressDialog.show();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        deviceId = bundle.getString("deviceId");
        devicePwd = bundle.getString("devicePwd");
        deviceName = bundle.getString("deviceName");
        KLog.i(deviceId);
        KLog.i(devicePwd);
        KLog.i(deviceName);
        KLog.i(LoginID);
        pView = (P2PView) findViewById(R.id.pview);
        initP2PView(7, P2PView.LAYOUTTYPE_TOGGEDER);//7是设备类型(技威定义的)
        mediaPlayer = new MediaPlayer();
        yuyin.setOnTouchListener(this);
    }

    public void getScreenWithHeigh() {
        DisplayMetrics dm = new DisplayMetrics();
        // 获取屏幕信息
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        screenHeigh = dm.heightPixels;
    }

    @OnClick({R.id.image_link, R.id.image_mute, R.id.image_screen, R.id.image_link1, R.id.image_mute1, R.id.image_screen1, R.id.bufang, R.id.baojing, R.id.zhuatu, R.id.realplay_video_btn, R.id.realplay_video_btn1, R.id.zhuatu1, R.id.camera_quality_btn, R.id.camera_quality_btn1})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_link://播放
                if (mediaPlayer.isPlaying() == true) {
                    Toast.makeText(this, "请先选择设备进行连接", Toast.LENGTH_SHORT).show();
                } else {
                        progressDialog.show();
                        String pwd = P2PHandler.getInstance().EntryPassword(devicePwd);//经过转换后的设备密码
                        P2PHandler.getInstance().call(LoginID, pwd, true, 1, deviceId, "", "", 2, deviceId);
                }
                break;
            case R.id.image_mute://声音开关
                if (mediaPlayer.isPlaying() == true) {
                    Toast.makeText(this, "请先连接设备", Toast.LENGTH_SHORT).show();
                } else {
                    changeMuteState();
                }
                break;
            case R.id.image_screen://全屏
                if (mediaPlayer.isPlaying() == true) {
                    Toast.makeText(this, "请先连接设备", Toast.LENGTH_SHORT).show();
                } else {
                    switchOrientation();
                }
                break;
            case R.id.image_link1://全屏播放
                if (mediaPlayer.isPlaying() == true) {
                    Toast.makeText(this, "请先连接设备", Toast.LENGTH_SHORT).show();
                } else {
                    String pwd = P2PHandler.getInstance().EntryPassword(devicePwd);//经过转换后的设备密码
                    P2PHandler.getInstance().call(LoginID, pwd, true, 1, deviceId, "", "", 2, deviceId);
                }
                break;
            case R.id.image_mute1://全屏声音开关
                changeMuteState();
                break;
            case R.id.image_screen1://退出全屏
                switchOrientation();
                break;
            case R.id.bufang://布防
                if (mediaPlayer.isPlaying() == true) {
                    Toast.makeText(this, "请先连接设备", Toast.LENGTH_SHORT).show();
                } else {
                    if (bufang.getText().equals("布防")) {
                        P2PHandler.getInstance().setRemoteDefence(deviceId, devicePwd, 1);
                        bufang.setText("撤防");
                        Drawable weather = ContextCompat.getDrawable(this, R.mipmap.camera_disarmed);
                        weather.setBounds(0, 0, weather.getMinimumWidth(), weather.getMinimumWidth());
                        bufang.setCompoundDrawables(null, weather, null, null);
                        Toast.makeText(this, "布防成功", Toast.LENGTH_SHORT).show();
                    } else {
                        P2PHandler.getInstance().setRemoteDefence(deviceId, devicePwd, 0);
                        bufang.setText("布防");
                        Drawable weather = ContextCompat.getDrawable(this, R.mipmap.camera_laying);
                        weather.setBounds(0, 0, weather.getMinimumWidth(), weather.getMinimumWidth());
                        bufang.setCompoundDrawables(null, weather, null, null);
                        Toast.makeText(this, "撤防成功", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.baojing://报警
                if (mediaPlayer.isPlaying() == true) {
                    Toast.makeText(this, "请先连接设备", Toast.LENGTH_SHORT).show();
                } else {
                    if (baojing.getText().equals("报警")) {
                        P2PHandler.getInstance().setBuzzer(deviceId, devicePwd, 1);
                        P2PHandler.getInstance().setMotion(deviceId, devicePwd, 1);
                        showSimpleListDialog();
                    } else {
                        P2PHandler.getInstance().setBuzzer(deviceId, devicePwd, 0);
                        P2PHandler.getInstance().setMotion(deviceId, devicePwd, 0);
                    }
                }
                break;
            case R.id.zhuatu1:
            case R.id.zhuatu://抓图
                if (mediaPlayer.isPlaying() == true) {
                    Toast.makeText(this, "请先连接设备", Toast.LENGTH_SHORT).show();
                } else {
                    captureScreen(-1);
                }
                break;
            case R.id.realplay_video_btn1:
            case R.id.realplay_video_btn:
                if (isRecord) {
                    realplayVideoBtn1.setText("录像");
                    isRecord = false;
                    realplayVideoBtn.setBackground(getResources().getDrawable(R.mipmap.play_video));
                    //停止录像
                    stopMoniterReocding();
                } else {
                    realplayVideoBtn1.setText("录像中...");
                    isRecord = true;
                    realplayVideoBtn.setBackground(getResources().getDrawable(R.mipmap.play_video_sel));
                    //开始录像
                    startMoniterRecoding();
                }
                break;
            case R.id.camera_quality_btn1:
                openQualityPopupWindow(cameraQualityBtn1);
                break;
            case R.id.camera_quality_btn:
                openQualityPopupWindow(cameraQualityBtn);
                break;
        }
    }

    public void startMoniterRecoding() {
        try {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ||
                    Environment.getExternalStorageState().equals(Environment.MEDIA_SHARED)) {
                String path = Environment.getExternalStorageDirectory().getPath() + File.separator + "xzs" + File.separator + deviceId;
                File file = new File(path);
                if (!file.exists()) {
                    file.mkdirs();
                }
                long time = System.currentTimeMillis();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault());// 制定日期的显示格式
                String name = "gwell" + "_" + sdf.format(new Date(time));
                pathName = file.getPath() + File.separator + name + ".mp4";
            } else {
                throw new NoSuchFieldException("sd卡");
            }
        } catch (NoSuchFieldException | NullPointerException e) {
            Toast.makeText(CameraActivity.this, " 没有内存卡", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        Log.e("dxsTest", "pathName:" + pathName);
        if (P2PHandler.getInstance().starRecoding(pathName)) {
            Toast.makeText(CameraActivity.this, " 正在录像", Toast.LENGTH_SHORT).show();
            animHandler.sendEmptyMessage(0);
        } else {
            //录像初始化失败
            Toast.makeText(CameraActivity.this, " 初始化录像失败", Toast.LENGTH_SHORT).show();
        }
    }

    Handler animHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    recordAnim(realplayVideoBtn);
                    animHandler.sendEmptyMessageDelayed(0, 2000);
                    break;
                case 1:
                    recordAnim(yuyin);
                    animHandler.sendEmptyMessageDelayed(1, 2000);
                    break;
            }
        }
    };

    private void recordAnim(View v) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(v, "scaleX", 1.0f, 1.2f, 1.0f);
        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(v, "scaleY", 1.0f, 1.2f, 1.0f);
        final AnimatorSet set = new AnimatorSet();
        set.play(objectAnimator).with(objectAnimator1);
        set.setDuration(2000);
        set.start();
    }

    public void stopMoniterReocding() {
        if (P2PHandler.getInstance().stopRecoding() == 0) {
            //录像不正常
            Toast.makeText(CameraActivity.this, " 录像不正常", Toast.LENGTH_SHORT).show();
        } else {
            //正常停止
            Toast.makeText(CameraActivity.this, " 停止录像", Toast.LENGTH_SHORT).show();
            animHandler.removeMessages(0);
            realplayVideoBtn.clearAnimation();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.e("dxsTest", "config:" + newConfig.orientation);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setToorBar(false);
            setHalfScreen(false);
            cameraSet.setVisibility(View.GONE);
            features.setVisibility(View.VISIBLE);
            features.getBackground().setAlpha(80);
            //以下代码是因为 方案商设备类型很多,视频比例也比较多
            //客户更具自己的视频比例调整画布大小
            //这里的实现比较绕,如果能弄清楚这部分原理,客户可自行修改此处代码
            if (P2PView.type == 1) {
                if (scale == 0) {
                    isFullScreen = false;
                    pView.halfScreen();//刷新画布比例
                } else {
                    isFullScreen = true;
                    pView.fullScreen();
                }
            } else {
                //这里本应该用设备类型判断,如果只有一种类型可不用这么麻烦
                if (7 == P2PValue.DeviceType.NPC) {
                    isFullScreen = false;
                    pView.halfScreen();
                } else {
                    isFullScreen = true;
                    pView.fullScreen();
                }
            }
            LinearLayout.LayoutParams parames = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            P2pview.setLayoutParams(parames);//调整画布容器宽高(比例)
        } else {
            setToorBar(true);
            setHalfScreen(true);
            cameraSet.setVisibility(View.VISIBLE);
            features.setVisibility(View.GONE);
            if (isFullScreen) {
                isFullScreen = false;
                pView.halfScreen();
            }
            //这里简写,只考虑了16:9的画面类型  大部分设备画面比例是这种
            int Heigh = screenWidth * 9 / 16;
            LinearLayout.LayoutParams parames = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            parames.height = Heigh;
            P2pview.setLayoutParams(parames);
        }
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_camera, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                //P2PHandler.getInstance().p2pDisconnect();
                break;
            case R.id.setting:
                Intent s = new Intent(this, CameraSettingActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("deviceId", deviceId);
                bundle.putString("devicePwd", devicePwd);
                bundle.putString("deviceName", deviceName);
                bundle.putInt("videoVolume", videoVolume);
                bundle.putString("deviceXm", getIntent().getStringExtra("deviceXm"));
                bundle.putString("deviceId2", getIntent().getStringExtra("deviceId2"));
                bundle.putString("xiangmuId", getIntent().getStringExtra("xiangmuId"));
                s.putExtras(bundle);
                startActivity(s);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            super.onBackPressed();
        }
    }

    int videoVolume;
    //得到对讲音量的回调
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetVideoVolume(VideoVolume videoVolume) {
        this.videoVolume = videoVolume.getSize();
    }


    private void changeMuteState() {
        isMute = !isMute;
        manager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        if (manager != null) {
            if (isMute) {
                Log.d("666", "静音");
                imageMute.setImageResource(R.mipmap.camera_mute);
                manager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
            } else {
                Log.d("666", "声音 ");
                imageMute.setImageResource(R.mipmap.camera_sound);
                manager.setStreamVolume(AudioManager.STREAM_MUSIC, manager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
            }
        }
    }


    public void regFilter() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(P2P_REJECT);
        filter.addAction(P2P_ACCEPT);
        filter.addAction(P2P_READY);
        registerReceiver(mReceiver, filter);
    }

    public BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(P2P_ACCEPT)) {
                progressDialog.dismiss();
                int[] type = intent.getIntArrayExtra("type");
                P2PView.type = type[0];
                scale = type[1];
                Toast.makeText(context, "监控准备,开始监控", Toast.LENGTH_SHORT).show();
                realplayVideoBtn.setEnabled(true);
                P2PHandler.getInstance().openAudioAndStartPlaying(1);//打开音频并准备播放，calllType与call时type一致
                KLog.i(videoMode);
                P2PHandler.getInstance().setVideoMode(videoMode);


            } else if (intent.getAction().equals(P2P_READY)) {
                KLog.i("P2P_READY...................");
                progressDialog.dismiss();
                pView.sendStartBrod();
            } else if (intent.getAction().equals(P2P_REJECT)) {
                progressDialog.dismiss();
                Toast.makeText(context, "监控挂断,请重新点击播放进行连接", Toast.LENGTH_SHORT).show();
                realplayVideoBtn.setEnabled(false);
            }
        }
    };


    @Override
    protected void onCaptureScreenResult(boolean isSuccess, int prePoint) {
        if (isSuccess) {
            Toast.makeText(this, "截图成功,图片在screenshot目录下", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, R.string.screenshot_error, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(mReceiver);
        //防止内存泄漏
        mediaPlayer.release();
        mediaPlayer = null;
        pView = null;
//        manager.
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        KLog.i("onpause...............");
        P2PHandler.getInstance().reject();
        if (isRecord) {
            stopMoniterReocding();
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new ContextWrapper(newBase) {
            @Override
            public Object getSystemService(String name) {
                if (Context.AUDIO_SERVICE.equals(name))
                    return getApplicationContext().getSystemService(name);
                return super.getSystemService(name);
            }
        });
    }

    @Override
    protected void onP2PViewSingleTap() {

    }

    @Override
    protected void onP2PViewFilling() {

    }

    @Override
    protected void onVideoPTS(long videoPTS) {

    }

    @Override
    public int getActivityInfo() {
        return 0;
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

    private void showSimpleListDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setIcon(R.mipmap.login_icon_bg);
//        builder.setTitle("拨打报警电话");
//        builder.setMessage(Contains.appYezhuFangwus.get(0).getXiangmuTelphone());
//
//        //监听下方button点击事件
//        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                Intent intent = new Intent(Intent.ACTION_DIAL);
//                Uri data = Uri.parse("tel:" + Contains.appYezhuFangwus.get(0).getXiangmuTelphone());
//                intent.setData(data);
//                startActivity(intent);
//            }
//        });
//        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//            }
//        });
//
//        //设置对话框是可取消的
//        builder.setCancelable(true);
//        AlertDialog dialog = builder.create();
//        dialog.show();
    }

    public void setToorBar(boolean isVisitiy) {
        if (toolbar != null) {
            if (isVisitiy) {
                toolbar.setVisibility(View.VISIBLE);
            } else {
                toolbar.setVisibility(View.GONE);
            }
        }
    }


    //语音按钮
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (mediaPlayer.isPlaying() == true) {
            Toast.makeText(this, "请先选择设备进行连接", Toast.LENGTH_SHORT).show();
        } else {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    animHandler.sendEmptyMessage(1);
                    setMute(false);
                    return true;
                case MotionEvent.ACTION_UP:
                    animHandler.removeMessages(1);
                    yuyin.clearAnimation();
                    setMute(true);
                    return true;
            }
        }
        return false;
    }

    /**
     * 视频清晰度
     *
     * @param anchor
     */
    private void openQualityPopupWindow(View anchor) {
        if (mediaPlayer.isPlaying()) {
            return;
        }
        closeQualityPopupWindow();
        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup layoutView = (ViewGroup) layoutInflater.inflate(R.layout.realplay_quality_items, null, true);

        Button qualityHdBtn = (Button) layoutView.findViewById(R.id.quality_hd_btn);
        qualityHdBtn.setOnClickListener(mOnPopWndClickListener);
        Button qualityBalancedBtn = (Button) layoutView.findViewById(R.id.quality_balanced_btn);
        qualityBalancedBtn.setOnClickListener(mOnPopWndClickListener);
        Button qualityFlunetBtn = (Button) layoutView.findViewById(R.id.quality_flunet_btn);
        qualityFlunetBtn.setOnClickListener(mOnPopWndClickListener);

        // 视频质量，
        if (videoMode == P2PValue.VideoMode.VIDEO_MODE_SD) {
            qualityBalancedBtn.setVisibility(View.GONE);
        } else if (videoMode == P2PValue.VideoMode.VIDEO_MODE_LD) {
            qualityFlunetBtn.setVisibility(View.GONE);
        } else if (videoMode == P2PValue.VideoMode.VIDEO_MODE_HD) {
            qualityHdBtn.setVisibility(View.GONE);
        }

        int height = 105;

        qualityFlunetBtn.setVisibility(View.VISIBLE);
        qualityBalancedBtn.setVisibility(View.VISIBLE);
        qualityHdBtn.setVisibility(View.VISIBLE);

        height = Utils.dip2px(this, height);
        mQualityPopupWindow = new PopupWindow(layoutView, RelativeLayout.LayoutParams.WRAP_CONTENT, height, true);
        mQualityPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mQualityPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                mQualityPopupWindow = null;
                closeQualityPopupWindow();
            }
        });
        try {
            mQualityPopupWindow.showAsDropDown(anchor, -Utils.dip2px(this, 5),
                    -(height + anchor.getHeight() + Utils.dip2px(this, 8)));
        } catch (Exception e) {
            e.printStackTrace();
            closeQualityPopupWindow();
        }
    }

    private void closeQualityPopupWindow() {
        if (mQualityPopupWindow != null) {
            dismissPopWindow(mQualityPopupWindow);
            mQualityPopupWindow = null;
        }
    }

    private View.OnClickListener mOnPopWndClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            closeQualityPopupWindow();
            switch (v.getId()) {
                case R.id.quality_hd_btn:
                    //高清
                    if (P2PHandler.getInstance().setVideoMode(P2PValue.VideoMode.VIDEO_MODE_HD) == 1) {
                        videoMode = P2PValue.VideoMode.VIDEO_MODE_HD;
                        cameraQualityBtn.setText("高清");
                        cameraQualityBtn1.setText("高清");
                    }
                    break;
                case R.id.quality_balanced_btn:
                    //标清
                    if (P2PHandler.getInstance().setVideoMode(P2PValue.VideoMode.VIDEO_MODE_SD) == 1) {
                        videoMode = P2PValue.VideoMode.VIDEO_MODE_SD;
                        cameraQualityBtn.setText("标清");
                        cameraQualityBtn1.setText("标清");
                    }
                    break;
                case R.id.quality_flunet_btn:
                    //流畅
                    if (P2PHandler.getInstance().setVideoMode(P2PValue.VideoMode.VIDEO_MODE_LD) == 1) {
                        videoMode = P2PValue.VideoMode.VIDEO_MODE_LD;
                        cameraQualityBtn.setText("流畅");
                        cameraQualityBtn1.setText("流畅");
                    }
                    break;
                default:
                    break;
            }
        }
    };

    private void dismissPopWindow(PopupWindow popupWindow) {
        if (popupWindow != null && !isFinishing()) {
            try {
                popupWindow.dismiss();
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }
}
