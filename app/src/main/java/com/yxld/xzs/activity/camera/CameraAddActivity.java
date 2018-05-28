package com.yxld.xzs.activity.camera;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.p2p.core.P2PHandler;
import com.socks.library.KLog;
import com.yxld.xzs.R;
import com.yxld.xzs.base.BaseActivity;
import com.yxld.xzs.contain.Contains;
import com.yxld.xzs.entity.BaseBack;
import com.yxld.xzs.entity.XiangMu;
import com.yxld.xzs.http.api.HttpAPIWrapper;
import com.yxld.xzs.utils.UIUtils;
import com.yxld.xzs.view.CustomPopWindow;
import com.yxld.xzs.view.DeleteEditText;
import com.yxld.xzs.view.datepicker.NumericWheelAdapter;
import com.yxld.xzs.view.datepicker.WheelView;
import com.yxld.xzs.yoosee.CheckEvent;
import com.yxld.xzs.yoosee.InitPasswordEvent;
import com.zhy.autolayout.AutoLinearLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author hu
 * @Package com.yxld.yxchuangxin.ui.activity.camera
 * @Description: $description
 * @date 2017/06/21 10:21:55
 */

public class CameraAddActivity extends BaseActivity {

    @BindView(R.id.device_name)
    DeleteEditText deviceName;
    @BindView(R.id.device_id)
    DeleteEditText deviceId;
    @BindView(R.id.device_pwd)
    DeleteEditText devicePwd;
    @BindView(R.id.tv_reset)
    TextView tvReset;
    @BindView(R.id.xiangmu)
    TextView tvxiangmu;
    private String ip;
    private String contactId;
    private String frag;
    private String ipFlag;
    private int xiangmuId = -1;

    private WheelView xiangmu;
    private ArrayList<String> xiangmuList;
    private NumericWheelAdapter xiangmuAdapter;
    private int[] ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    protected void initView() {
        setContentView(R.layout.activity_camera_add);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        EventBus.getDefault().register(this);
        if (getIntent().getBooleanExtra("ishasContactId", false)) {
            ip = getIntent().getStringExtra("ip");
            contactId = getIntent().getStringExtra("contactId");
            frag = getIntent().getStringExtra("frag");
            ipFlag = getIntent().getStringExtra("ipFlag");
            if (frag.equals("0")) {
                tvReset.setVisibility(View.VISIBLE);
            } else {

            }
            deviceId.setText(contactId);
        }
    }

    private void findXm() {
        Map<String, String> map = new HashMap<>();
        map.put("uuId", Contains.uuid);
        Disposable disposable = HttpAPIWrapper.getInstance(HttpAPIWrapper.getOkHttpClient()).findXm(map)
                .subscribe(new Consumer<XiangMu>() {
                    @Override
                    public void accept(@NonNull XiangMu xiangMu) throws Exception {
                        progressDialog.hide();
                        if (xiangMu.status == 0) {
                            ids = new int[xiangMu.getData().size()];
                            xiangmuList = new ArrayList<String>();
                            for (int i = 0; i < xiangMu.getData().size(); i++) {
                                if (xiangMu.getData().get(i) != null) {
                                    ids[i] = xiangMu.getData().get(i).getXiangmuId();
                                    xiangmuList.add(xiangMu.getData().get(i).getXiangmuName());
                                }
                            }
                            xiangmuAdapter = new NumericWheelAdapter(CameraAddActivity.this, 0, xiangmuList.size() - 1, "", xiangmuList);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void CameraEventBus(CheckEvent checkEvent) {
        switch (checkEvent.result) {
            case 9999:
                progressDialog.hide();
                Toast.makeText(this, "密码错误", Toast.LENGTH_SHORT).show();
                break;
            case 9998:
                progressDialog.hide();
                Toast.makeText(this, "网络连接错误", Toast.LENGTH_SHORT).show();
                break;
            case 9997:
                Toast.makeText(this, "密码正确", Toast.LENGTH_SHORT).show();
                Map<String, String> map = new HashMap<String, String>();
                map.put("sb_name", deviceName.getText().toString());//用户自定义设备名称
                map.put("sb_zhanghao", "");//设备账号
                map.put("sb_ipc_id", deviceId.getText().toString());//设备ID
                map.put("sb_ipc_pwd", devicePwd.getText().toString());//设备密码
                map.put("apptoken", Contains.uuid);//用户TOKEN
                cameraAdd();
//                AppConfig.getInstance().mAppActivityManager.finishActivity(CameraConfigActivity.class);
                break;
            case 9996:
                progressDialog.hide();
                Toast.makeText(this, "访客密码正确", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void cameraAdd() {
        progressDialog.show();
        Map<String, String> map = new HashMap<>();
        map.put("uuId", Contains.uuid);
        map.put("equip.equipName", deviceName.getText().toString());
        map.put("equip.equipPassword", devicePwd.getText().toString());
        map.put("equip.equipSerial", deviceId.getText().toString());
        map.put("equip.equipXiangmuId", xiangmuId + "");
        Disposable disposable = HttpAPIWrapper.getInstance(HttpAPIWrapper.getOkHttpClient()).cameraAdd(map)
                .subscribe(new Consumer<BaseBack>() {
                    @Override
                    public void accept(@NonNull BaseBack removeDevice) throws Exception {
                        progressDialog.hide();
                        Toast.makeText(CameraAddActivity.this, removeDevice.getMsg(), Toast.LENGTH_SHORT).show();
                        if (removeDevice.getStatus() == 0) {
                            finish();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                    }
                });
        disposables.add(disposable);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void CameraEventBusInitPassWord(InitPasswordEvent checkEvent) {
        switch (checkEvent.result) {
            case 9999:
                progressDialog.hide();
                break;
            case 9998:
                progressDialog.hide();
                Toast.makeText(this, "网络连接错误", Toast.LENGTH_SHORT).show();
                break;
            case 9997:
                Toast.makeText(this, "密码设置成功", Toast.LENGTH_SHORT).show();
                progressDialog.show();
                String callID = deviceId.getText().toString().trim();//设备号
                String pwd = P2PHandler.getInstance().EntryPassword(devicePwd.getText().toString().trim());//经过转换后的设备密码
                KLog.i("确认密码");
                P2PHandler.getInstance().checkPassword(callID, pwd);
                break;
            case 9996:
                progressDialog.hide();
                Toast.makeText(this, "访客密码正确", Toast.LENGTH_SHORT).show();

                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_camera_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.camera_save:
                if (deviceName.getText().toString().trim().equals("") || deviceName.getText().toString().trim().length() <= 3) {
                    Toast.makeText(CameraAddActivity.this, "设备名字不合法,名字为4-15位", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (deviceId.getText().toString().equals("")) {
                    Toast.makeText(CameraAddActivity.this, "请输入设备ID", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (xiangmuId == -1) {
                    Toast.makeText(CameraAddActivity.this, "请选择项目", Toast.LENGTH_SHORT).show();
                    break;
                }
                progressDialog.show();
                String callID = deviceId.getText().toString().trim();//设备号
                String pwd = P2PHandler.getInstance().EntryPassword(devicePwd.getText().toString().trim());//经过转换后的设备密码
                if (getIntent().getBooleanExtra("ishasContactId", false) || (getIntent().getBooleanExtra("ishasContactId", false) && frag.equals("0"))) {
                    KLog.i("设置初始密码");
                    P2PHandler.getInstance().setInitPassword(ipFlag, pwd, "", "");
                } else {
                    KLog.i("确认密码");
                    P2PHandler.getInstance().checkPassword(callID, pwd);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
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
                xiangmuId = ids[xiangmu.getCurrentItem()];
                tvxiangmu.setText(xiangmuList.get(xiangmu.getCurrentItem()));
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

    @OnClick(R.id.xiangmu)
    public void onViewClicked() {
        showWheelView(tvxiangmu);
    }
}