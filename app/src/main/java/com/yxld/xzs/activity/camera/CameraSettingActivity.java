package com.yxld.xzs.activity.camera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.p2p.core.P2PHandler;
import com.socks.library.KLog;
import com.yxld.xzs.R;
import com.yxld.xzs.base.BaseActivity;
import com.yxld.xzs.contain.Contains;
import com.yxld.xzs.entity.BaseBack;
import com.yxld.xzs.entity.XiangMu;
import com.yxld.xzs.entity.eventbus.VideoVolume;
import com.yxld.xzs.http.api.HttpAPIWrapper;
import com.yxld.xzs.utils.SPUtil;
import com.yxld.xzs.utils.StringUitl;
import com.yxld.xzs.utils.UIUtils;
import com.yxld.xzs.view.CustomPopWindow;
import com.yxld.xzs.view.datepicker.NumericWheelAdapter;
import com.yxld.xzs.view.datepicker.WheelView;
import com.yxld.xzs.yoosee.CheckUpdate;
import com.yxld.xzs.yoosee.UpdateEvent;
import com.zhy.autolayout.AutoLinearLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import static com.yxld.xzs.activity.camera.DeviceActivity.LoginID;

/**
 * @author hu
 * @Package com.yxld.yxchuangxin.ui.activity.camera
 * @Description: $description
 * @date 2017/06/21 10:21:20
 */

public class CameraSettingActivity extends BaseActivity implements OnItemClickListener {

    @BindView(R.id.camera_header)
    ImageView cameraHeader;
    @BindView(R.id.camera_deviceId)
    TextView cameraDeviceId;
    @BindView(R.id.camera_deviceName)
    TextView cameraDeviceName;
    @BindView(R.id.s_update)
    TextView sUpdate;
    @BindView(R.id.s_video)
    TextView sVideo;
    @BindView(R.id.s_firmware)
    TextView sFirmware;
    @BindView(R.id.tv_device_xiangmu)
    TextView tvDeviceXiangmu;
    @BindView(R.id.tv_device_name)
    TextView tvDeviceName;
    @BindView(R.id.view1)
    View view1;
    @BindView(R.id.view2)
    View view2;
    @BindView(R.id.seekBar)
    SeekBar seekBar;
    @BindView(R.id.video_size)
    TextView videoSize;
    private AlertView mAlertViewExt;
    private AlertView mAlertView;
    private InputMethodManager imm;
    private String new_pwd, deviceId, devicePwd, deviceName, filepath; //输入的新密码  设备号  设备密码 设备名称 文件地址
    private String xiangmuId;
    private String deviceId2;
    private String deviceXm = "";

    private EditText password;
    private EditText new_password;
    private WheelView xiangmu;
    private ArrayList<String> xiangmuList;
    private NumericWheelAdapter xiangmuAdapter;
    private int[] ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    protected void initView() {
        setContentView(R.layout.activity_camera_setting);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sUpdate.setVisibility(View.VISIBLE);
        tvDeviceXiangmu.setVisibility(View.VISIBLE);
        tvDeviceName.setVisibility(View.VISIBLE);
        view1.setVisibility(View.VISIBLE);
        view2.setVisibility(View.VISIBLE);

    }

    protected void initData() {
        EventBus.getDefault().register(this);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        deviceId = bundle.getString("deviceId");
        devicePwd = bundle.getString("devicePwd");
        deviceName = bundle.getString("deviceName");
        xiangmuId = bundle.getString("xiangmuId");
        deviceId2 = bundle.getString("deviceId2");
        deviceXm = bundle.getString("deviceXm");
        filepath = Environment.getExternalStorageDirectory() + "/screenshot/tempHead/" + getUserID() + "/" + deviceId + ".jpg";
        cameraDeviceId.setText("设备ID:" + deviceId);
        cameraDeviceName.setText("设备名称:" + deviceName);
        P2PHandler.getInstance().getNpcSettings(deviceId, devicePwd);
        final SPUtil spUtil = new SPUtil(this);
        int videoVolume = (int) spUtil.get("videoVolume", 0);
        seekBar.setProgress(videoVolume);
        videoSize.setText("(" + videoVolume + ")");
// seekBar.setProgress(bundle.getInt("videoVolume"));
//        videoSize.setText("(" + bundle.getInt("videoVolume") + ")");
        File file = new File(filepath);
        if (file.exists()) {
            Bitmap bm = BitmapFactory.decodeFile(filepath);
            //将图片显示到ImageView中
            cameraHeader.setImageBitmap(bm);
        }
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                KLog.i("开始设置");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                KLog.i("结束设置" + seekBar.getProgress());
                P2PHandler.getInstance().setVideoVolume(deviceId, P2PHandler.getInstance().EntryPassword(devicePwd), seekBar.getProgress());
                videoSize.setText("(" + seekBar.getProgress() + ")");
                spUtil.put("videoVolume", seekBar.getProgress());
            }
        });
    }

    //转换Loginid
    public String getUserID() {
        String usId;
        try {
            usId = "0" + String.valueOf((Integer.parseInt(LoginID) & 0x7fffffff));
            return usId;
        } catch (NumberFormatException e) {
            return LoginID;
        }
    }

    private void findXm() {
        Map<String, String> map = new HashMap<>();
        map.put("uuId", Contains.uuid);
        Disposable disposable = HttpAPIWrapper.getInstance(HttpAPIWrapper.getOkHttpClient()).findXm(map)
                .subscribe(new Consumer<XiangMu>() {
                    @Override
                    public void accept(@NonNull XiangMu xiangMu) throws Exception {
                        if (xiangMu.status == 0) {
                            ids = new int[xiangMu.getData().size()];
                            xiangmuList = new ArrayList<String>();
                            for (int i = 0; i < xiangMu.getData().size(); i++) {
                                if (xiangMu.getData().get(i) != null) {
                                    ids[i] = xiangMu.getData().get(i).getXiangmuId();
                                    xiangmuList.add(xiangMu.getData().get(i).getXiangmuName());
                                }
                            }
                            xiangmuAdapter = new NumericWheelAdapter(CameraSettingActivity.this, 0, xiangmuList.size() - 1, "", xiangmuList);
                            xiangmuAdapter.setTextSize(15);
                            xiangmu.setViewAdapter(xiangmuAdapter);
                        } else {
                            onError(xiangMu.status, xiangMu.error);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                    }
                });
        disposables.add(disposable);
    }

    @OnClick({R.id.tv_device_name, R.id.tv_device_xiangmu, R.id.s_update, R.id.s_video, R.id.s_firmware})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_device_name:
                showupdaeNameDialog();
                break;
            case R.id.tv_device_xiangmu:
                showWheelView(tvDeviceName);
                break;
            case R.id.s_update:
                showupdatePassWordDialog();
//                imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                mAlertViewExt = new AlertView("修改密码", null, "取消", null, new String[]{"完成"}, this, AlertView.Style.Alert, this);
//                ViewGroup layout = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.activity_cameras_dialog, null);
//                //  对布局中的控件监听
//                password = (EditText) layout.findViewById(R.id.password);
//                new_password = (EditText) layout.findViewById(R.id.new_password);
//                password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//                    @Override
//                    public void onFocusChange(View view, boolean focus) {
//                        //输入框出来则往上移动
//                        boolean isOpen = imm.isActive();
//                        mAlertViewExt.setMarginBottom(isOpen && focus ? 120 : 0);
//                        System.out.println(isOpen);
//                    }
//                });
//                new_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//                    @Override
//                    public void onFocusChange(View view, boolean focus) {
//                        //输入框出来则往上移动
//                        boolean isOpen = imm.isActive();
//                        mAlertViewExt.setMarginBottom(isOpen && focus ? 120 : 0);
//                        System.out.println(isOpen);
//                    }
//                });
//                mAlertViewExt.addExtView(layout);
//                mAlertViewExt.show();
                break;
            case R.id.s_video://录像设置
                Intent video = new Intent(this, CameraVideoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("deviceId", deviceId);
                bundle.putString("devicePwd", devicePwd);
                video.putExtras(bundle);
                startActivity(video);
                break;
            case R.id.s_firmware://固件更新
                P2PHandler.getInstance().checkDeviceUpdate(deviceId, devicePwd);
                break;
        }
    }


    private void showupdaeNameDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog alertDialog = builder.create();
        View view = View.inflate(this, R.layout.layout_dialog_update_device_name, null);
        TextView tv_confirm = (TextView) view.findViewById(R.id.tv_confirm);
        TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        final EditText et_name = (EditText) view.findViewById(R.id.et_name);
        StringUitl.setInputName(et_name);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.hide();
            }
        });
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_name.getText().toString().trim().equals("")) {
                    Toast.makeText(CameraSettingActivity.this, "请输入设备名", Toast.LENGTH_SHORT).show();
                    //ToastUtil.show(CameraSettingActivity.this, "请输入设备名");
                    return;
                }
                if (et_name.getText().toString().trim().length() < 4 || et_name.getText().toString().trim().length() >= 15) {
                    Toast.makeText(CameraSettingActivity.this, "名字长度不合法", Toast.LENGTH_SHORT).show();
                    //ToastUtil.show(CameraSettingActivity.this, "名字长度不合法");
                    return;
                }
                Map<String, String> map = new HashMap<>();
                map.put("equip.equipId", deviceId2);
                map.put("equip.equipSerial", deviceId);
                map.put("equip.equipName", et_name.getText().toString());
                map.put("equip.equipXiangmuId", xiangmuId);
                map.put("equip.equipPassword", devicePwd);
                map.put("uuId", Contains.uuid);
                deviceName = et_name.getText().toString();
                getCameraUpdate(map, 1);
                alertDialog.hide();
            }
        });
        alertDialog.setView(view);
        alertDialog.show();
    }

    private void showupdatePassWordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog alertDialog = builder.create();
        View view = View.inflate(this, R.layout.layout_dialog_update_device_password, null);
        TextView tv_confirm = (TextView) view.findViewById(R.id.tv_confirm);
        TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        final EditText et_old_name = (EditText) view.findViewById(R.id.et_old_name);
        final EditText et_new_name = (EditText) view.findViewById(R.id.et_new_name);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.hide();
            }
        });
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!et_old_name.getText().toString().equals("") && !et_new_name.getText().toString().equals("")) {
                    if (et_new_name.getText().toString().trim().length() < 3 || et_new_name.getText().toString().trim().length() >= 19) {
                        Toast.makeText(CameraSettingActivity.this, "密码长度不合法", Toast.LENGTH_SHORT).show();
//                        ToastUtil.show(CameraSettingActivity.this, "密码长度不合法");
                        return;
                    }
                    new_pwd = et_new_name.getText().toString();
                    P2PHandler.getInstance().setDevicePassword(deviceId, et_old_name.getText().toString(), new_pwd, new_pwd, new_pwd);
//                    if (et_old_name.getText().toString().equals(et_new_name.getText().toString())) {
//                    } else {
//                        Toast.makeText(CameraSettingActivity.this, "新旧密码不一致", Toast.LENGTH_SHORT).show();
//                        return;
//                    }
                } else {
                    Toast.makeText(CameraSettingActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                alertDialog.hide();
            }
        });
        alertDialog.setView(view);
        alertDialog.show();
    }

    private void showWheelView(View showView) {
        if (xiangmuList == null) {
            findXm();
        }
        View view = LayoutInflater.from(this).inflate(R.layout.picker_xiangmu, null);
        AutoLinearLayout ll_content = (AutoLinearLayout) view.findViewById(R.id.ll_content);
        ll_content.setAnimation(AnimationUtils.loadAnimation(this, R.anim.pop_manage_product_in));
        TextView submit = (TextView) ll_content.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomPopWindow.onBackPressed();
                Map<String, String> map = new HashMap<>();
                map.put("equip.equipId", deviceId2);
                map.put("equip.equipName", deviceName);
                map.put("equip.equipXiangmuId", ids[xiangmu.getCurrentItem()] + "");
                map.put("equip.equipPassword", devicePwd);
                map.put("uuId", Contains.uuid);
                map.put("equip.equipSerial", deviceId);
                getCameraUpdate(map, 0);
            }
        });
        xiangmu = (WheelView) ll_content.findViewById(R.id.xiangmu);
        if (xiangmuList != null) {
            xiangmu.setViewAdapter(xiangmuAdapter);
        }
        new CustomPopWindow.PopupWindowBuilder(this)
                .setClippingEnable(false)
                .setFocusable(true)
                .setView(view)
                .setContenView(ll_content)
                .size(UIUtils.getDisplayWidth(this), UIUtils.getDisplayHeigh(this))
                .create()
                .showAtLocation(showView, Gravity.CENTER, 0, 0);
    }

    //修改密码点击事件
    @Override
    public void onItemClick(Object o, int position) {
        //判断是否是拓展窗口View，而且点击的是非取消按钮
        if (o == mAlertViewExt && position != AlertView.CANCELPOSITION) {
            closeKeyboard();
            String pwd = password.getText().toString();
            new_pwd = new_password.getText().toString();
            if (pwd.isEmpty() && new_pwd.isEmpty()) {
                Toast.makeText(this, "请输入好密码和新密码", Toast.LENGTH_SHORT).show();
            } else {
                P2PHandler.getInstance().setDevicePassword(deviceId, pwd, new_pwd, new_pwd, new_pwd);
            }
        } else if (o == mAlertView && position != AlertView.CANCELPOSITION) {
            P2PHandler.getInstance().doDeviceUpdate(deviceId, devicePwd);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void UpdateEventBus(UpdateEvent message) {
        if (message.result == 0) {
            //?sb.sb_ipc_id=%1$s&sb.sb_ipc_pwd=%2$s&uuid=%3$s
            Map<String, String> map = new HashMap<>();
            map.put("equip.equipId", deviceId2);
            map.put("equip.equipName", deviceName);
            map.put("equip.equipXiangmuId", xiangmuId);
            map.put("equip.equipPassword", new_pwd);
            map.put("uuId", Contains.uuid);
            map.put("equip.equipSerial", deviceId);
            getCameraUpdate(map, 2);
        } else {
            Toast.makeText(this, "修改失败", Toast.LENGTH_SHORT).show();
        }
    }

    public void getCameraUpdate(Map map, final int flag) {
        KLog.i(map);
        progressDialog.show();
        Disposable disposable = HttpAPIWrapper.getInstance(HttpAPIWrapper.getOkHttpClient()).updateDevice(map)
                .subscribe(new Consumer<BaseBack>() {
                    @Override
                    public void accept(@NonNull BaseBack updateDevice) throws Exception {
                        progressDialog.hide();
                        Toast.makeText(CameraSettingActivity.this, updateDevice.getMsg(), Toast.LENGTH_SHORT).show();
                        if (updateDevice.getStatus() == 0) {
                            if (flag == 0) {
                                tvDeviceXiangmu.setText("修改设备项目(" + xiangmuList.get(xiangmu.getCurrentItem()) + ")");
                            }
                            if (flag == 1) {
                                cameraDeviceName.setText("设备名称:" + deviceName);
                            }
                            if (flag == 2) {
                                devicePwd = new_pwd;
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                    }
                });
        disposables.add(disposable);
    }

    //固件更新版本
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void CheckUpdateEventBus(CheckUpdate msg) {
        if (msg.result == 1) {
            mAlertView = new AlertView("固件更新", "固件版本" + msg.cur_version + ",可更新至" + msg.upg_version, "下次再说", null, new String[]{"立即更新"}, this, AlertView.Style.Alert, this);
            mAlertView.show();
        } else {
            Toast.makeText(this, "已是最新版本", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void closeKeyboard() {
        //关闭软键盘
        imm.hideSoftInputFromWindow(password.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(new_password.getWindowToken(), 0);
        //恢复位置
        mAlertViewExt.setMarginBottom(0);
    }
}