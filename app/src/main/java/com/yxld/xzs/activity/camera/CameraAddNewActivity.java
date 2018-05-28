package com.yxld.xzs.activity.camera;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.p2p.core.P2PHandler;
import com.p2p.core.P2PSpecial.HttpErrorCode;
import com.p2p.core.global.P2PConstants;
import com.socks.library.KLog;
import com.yxld.xzs.R;
import com.yxld.xzs.base.BaseActivity;
import com.yxld.xzs.base.DemoApplicationLike;
import com.yxld.xzs.contain.Contains;
import com.yxld.xzs.entity.APPCamera;
import com.yxld.xzs.entity.BaseBack;
import com.yxld.xzs.entity.CameraDetail;
import com.yxld.xzs.entity.CxwyYezhu;
import com.yxld.xzs.entity.XiangMu;
import com.yxld.xzs.http.api.HttpAPIWrapper;
import com.yxld.xzs.http.api.support.ErrorHandlerInterceptor;
import com.yxld.xzs.utils.StringUitl;
import com.yxld.xzs.utils.ToastUtil;
import com.yxld.xzs.utils.UIUtils;
import com.yxld.xzs.view.ConfirmDialog;
import com.yxld.xzs.view.CustomPopWindow;
import com.yxld.xzs.view.DeleteEditText;
import com.yxld.xzs.view.datepicker.NumericWheelAdapter;
import com.yxld.xzs.view.datepicker.WheelView;
import com.yxld.xzs.yoosee.CheckEvent;
import com.yxld.xzs.yoosee.FriendStatus;
import com.yxld.xzs.yoosee.InitPasswordEvent;
import com.yxld.xzs.yoosee.P2PListener;
import com.yxld.xzs.yoosee.SettingListener;
import com.zhy.autolayout.AutoLinearLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.yxld.xzs.base.DemoApplicationLike.APPID;
import static com.yxld.xzs.base.DemoApplicationLike.APPToken;
import static com.yxld.xzs.http.HttpService.URL_GET_CAMERA;

/**
 * @author hu
 * @Package com.yxld.yxchuangxin.ui.activity.camera
 * @Description: $description
 * @date 2017/06/21 10:21:55
 */

public class CameraAddNewActivity extends BaseActivity {

    @BindView(R.id.device_name)
    DeleteEditText deviceName;
    @BindView(R.id.device_id)
    DeleteEditText deviceId;
    @BindView(R.id.device_pwd)
    DeleteEditText devicePwd;
    @BindView(R.id.tv_reset)
    TextView tvReset;
    @BindView(R.id.xiangmu)
    TextView tvXiangmu;
    @BindView(R.id.tv_loudong)
    TextView tvLoudong;
    @BindView(R.id.tv_danyaun)
    TextView tvDanyaun;
    @BindView(R.id.tv_fanghao)
    TextView tvFanghao;
    @BindView(R.id.tv_phone)
    TextView tvGuanliyuan;
    private String ip;
    private String contactId;
    private String frag;
    private String ipFlag;


    private int[] ids;

    private WheelView wheelView;
    private NumericWheelAdapter xiangmuAdapter;
    private ArrayList<String> xiangmuList;
    private XiangMu mXiangMu;
    private int xiangmuId = -1;
    private String fwLoudong = "";
    private String fwDanyuan = "";
    private String fwFanghao = "";
    private String fwGuanliyuan = "";
    private ArrayList<String> louPanList;
    private ArrayList<String> danYuanList;
    private ArrayList<String> fangHaoList;
    private ArrayList<String> fangwuBianhao;
    private ArrayList<String> fangwuhao;
    private ArrayList<String> guanliyuan;
    private long firstTime;
    private long secondTime;

    /**
     * flag true表示设置配网后进来WiFi后 设置密码 在线  fasle 直接添加 不在要检查在线状态
     */
    private boolean mflag = false;
    private String[] Deviceid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    protected void initView() {
        setContentView(R.layout.activity_camera_add_new);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        EventBus.getDefault().register(this);
        mflag = getIntent().getBooleanExtra("ishasContactId", false);
        if (mflag) {
            ip = getIntent().getStringExtra("ip");
            contactId = getIntent().getStringExtra("contactId");
            frag = getIntent().getStringExtra("frag");
            ipFlag = getIntent().getStringExtra("ipFlag");
            if (frag.equals("0")) {
                tvReset.setVisibility(View.VISIBLE);
            } else {

            }
            deviceId.setText(contactId);
            deviceId.setEnabled(false);
        }
    }


    @OnClick({R.id.xiangmu, R.id.tv_loudong, R.id.tv_danyaun, R.id.tv_fanghao, R.id.tv_phone, R.id.tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.xiangmu:
                showWheelView(tvXiangmu, 1);
                break;
            case R.id.tv_loudong:
                showWheelView(tvLoudong, 2);
                break;
            case R.id.tv_danyaun:
                showWheelView(tvDanyaun, 3);
                break;
            case R.id.tv_fanghao:
                showWheelView(tvFanghao, 4);
                break;
            case R.id.tv_phone:
                showWheelView(tvGuanliyuan, 5);
                break;
            case R.id.tv_confirm:
                if (deviceId.getText().toString().equals("")) {
                    Toast.makeText(CameraAddNewActivity.this, "请输入设备ID", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (xiangmuId == -1) {
                    Toast.makeText(CameraAddNewActivity.this, "请选择项目", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (StringUitl.isEmpty(fwLoudong)) {
                    Toast.makeText(CameraAddNewActivity.this, "请选择楼栋", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (StringUitl.isEmpty(fwDanyuan)) {
                    Toast.makeText(CameraAddNewActivity.this, "请选择单元", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (StringUitl.isEmpty(fwFanghao)) {
                    Toast.makeText(CameraAddNewActivity.this, "请选择房号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (StringUitl.isEmpty(fwGuanliyuan)) {
                    Toast.makeText(CameraAddNewActivity.this, "请选择业主电话", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (StringUitl.isEmpty(deviceId.getText().toString())) {
                    Toast.makeText(CameraAddNewActivity.this, "请输入设备ID", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (deviceName.getText().toString().trim().equals("") || deviceName.getText().toString().trim().length() <= 3) {
                    Toast.makeText(CameraAddNewActivity.this, "设备名字不合法,名字为4-15位", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (StringUitl.isEmpty(devicePwd.getText().toString())) {
                    Toast.makeText(CameraAddNewActivity.this, "请输入设备密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                KLog.i("开始获取设备状态");
                Deviceid = new String[]{deviceId.getText().toString()};
                progressDialog.show();
                //需要检查设备是否在线
                P2PHandler.getInstance().getFriendStatus(Deviceid, P2PConstants.P2P_Server.SERVER_INDEX);
                break;
            default:
                break;

        }
    }

    //获取设备状态
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void DeviceStatus(FriendStatus msg) {

        KLog.i("设备状态" + msg.bRequestResult );
        if (msg.bRequestResult == 0) {
            ToastUtil.show(this, "当前设备不在线，请添加在线设备");
            progressDialog.hide();
        } else {
            // TODO: 2018/1/12 不知道什么原因回调会进入两次
            if (msg.bRequestResult == 1) {
                firstTime = System.currentTimeMillis();
            }
            if (msg.bRequestResult == 2) {
                secondTime = System.currentTimeMillis();
            }
            KLog.i("firstTime" + firstTime+"secondTime"+secondTime+"Math.abs(secondTime-firstTime)" +Math.abs(secondTime-firstTime));
            if (Math.abs(secondTime-firstTime)<=1000){
                firstTime=0;
                secondTime=0;
                return;
            }
            if (msg.status.length == 0) {
                return;
            }
            if (msg.status[0] == 0) {
                progressDialog.hide();
                ToastUtil.show(this, "当前设备不在线，请添加在线设备");
            } else if (msg.status[0] == 1) {
                KLog.i("设备在线");
                String callID = deviceId.getText().toString().trim();//设备号
                String pwd = P2PHandler.getInstance().EntryPassword(devicePwd.getText().toString().trim());//经过转换后的设备密码
                if (mflag || (mflag && frag.equals("0"))) {
                    KLog.i("设置初始密码");
                    P2PHandler.getInstance().setInitPassword(ipFlag, pwd, "", "");
                } else {
                    KLog.i("确认密码");
                    P2PHandler.getInstance().checkPassword(callID, pwd);
                }
            }

        }
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
                KLog.i("密码正确");
                cameraAdd();
//                AppConfig.getInstance().mAppActivityManager.finishActivity(CameraConfigActivity.class);
                break;
            case 9996:
                progressDialog.hide();
                Toast.makeText(this, "访客密码正确", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    public void cameraAdd() {
        // progressDialog.show();
        Map<String, String> map = new HashMap<>();
        map.put("uuid", Contains.uuid);
        map.put("xiangmuId", xiangmuId + "");
        map.put("loudong", fwLoudong);
        map.put("danyuan", fwDanyuan);
        map.put("fanghao", fwFanghao);
        map.put("phone", fwGuanliyuan);
        map.put("sb_ipc_id", deviceId.getText().toString());
        map.put("sb_ipc_pwd", devicePwd.getText().toString());
        map.put("sb_name", deviceName.getText().toString().trim());

        Disposable disposable = HttpAPIWrapper.getInstance(HttpAPIWrapper.getOkHttpClient()).addCamera(map)
                .subscribe(new Consumer<BaseBack>() {
                    @Override
                    public void accept(@NonNull BaseBack removeDevice) throws Exception {
                        progressDialog.hide();
                        Toast.makeText(CameraAddNewActivity.this, removeDevice.getMsg(), Toast.LENGTH_SHORT).show();
                        if (removeDevice.getStatus() == 1) {
                            KLog.i("CameraAddNewActivity项目" + xiangmuId + "楼栋" + fwLoudong + "单元" + fwDanyuan + "房号" + fwFanghao + "业主手机" + fwGuanliyuan + "序列号" + deviceId.getText().toString());
                            Intent intent = new Intent(CameraAddNewActivity.this, CameraDetailActivity.class);
                            intent.putExtra("xiangmuId", xiangmuId == 0 ? "" : xiangmuId + "");
                            intent.putExtra("fwLoudong", fwLoudong);
                            intent.putExtra("fwDanyuan", fwDanyuan);
                            intent.putExtra("fwFanghao", fwFanghao);
                            intent.putExtra("mPhone", fwGuanliyuan);
                            intent.putExtra("mXuLieHao", deviceId.getText().toString());
                            startActivity(intent);
                            finish();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        progressDialog.hide();
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
            default:
                break;
        }
    }


    private void showWheelView(View showView, final int flag) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(showView.getWindowToken(), 0);
        View view = LayoutInflater.from(this).inflate(R.layout.picker_xiangmu, null);
        AutoLinearLayout ll_content = (AutoLinearLayout) view.findViewById(R.id.ll_content);
        ll_content.setAnimation(AnimationUtils.loadAnimation(this, R.anim.pop_manage_product_in));
        TextView submit = (TextView) ll_content.findViewById(R.id.submit);
        TextView tv_title = (TextView) ll_content.findViewById(R.id.tv_title);
        wheelView = (WheelView) ll_content.findViewById(R.id.xiangmu);
        switch (flag) {
            case 1:
                getXiangMu();
                tv_title.setText("请选择项目");
                break;
            case 2:
                if (xiangmuId == -1) {
                    Toast.makeText(this, "请先选择项目！", Toast.LENGTH_SHORT).show();
                    return;
                }
                getLouPan();
                tv_title.setText("请选择楼栋");
                break;
            case 3:
                if (fwLoudong.equals("")) {
                    Toast.makeText(this, "请先选择楼栋！", Toast.LENGTH_SHORT).show();
                    return;
                }
                getDanYuan();
                tv_title.setText("请选择单元");
                break;
            case 4:
                if (fwLoudong.equals("")) {
                    Toast.makeText(this, "请先选择楼栋！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (fwDanyuan.equals("")) {
                    Toast.makeText(this, "请先选择单元！", Toast.LENGTH_SHORT).show();
                    return;
                }
                getFangHao();
                tv_title.setText("请选择房号");
                break;
            case 5:
                if (xiangmuId == -1) {
                    Toast.makeText(this, "请先选择项目！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (fwLoudong.equals("")) {
                    Toast.makeText(this, "请先选择楼栋！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (fwDanyuan.equals("")) {
                    Toast.makeText(this, "请先选择单元！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (fwFanghao.equals("")) {
                    Toast.makeText(this, "请先选择房号！", Toast.LENGTH_SHORT).show();
                    return;
                }
                getGuanLiYuan();
                tv_title.setText("请选择管理员");
                break;
            default:
                break;
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomPopWindow.onBackPressed();
                onWheelViewOnconfirm(flag);
            }
        });
        new CustomPopWindow.PopupWindowBuilder(this)
                .setClippingEnable(false)
                .setFocusable(true)
                .setView(view)
                .setContenView(ll_content)
                .size(UIUtils.getDisplayWidth(this), UIUtils.getDisplayHeigh(this))
                .create()
                .showAtLocation(showView, Gravity.CENTER, 0, 0);
    }

    private void onWheelViewOnconfirm(int flag) {
        //         1为 选择项目， 2 为选择楼栋  3为单元   4为房号

        int position = wheelView.getCurrentItem();
        switch (flag) {
            case 1:
                if (mXiangMu == null || mXiangMu.getData() == null) {
                    return;
                }
                tvXiangmu.setText(mXiangMu.getData().get(position).getXiangmuName().trim());
                if (xiangmuId != mXiangMu.getData().get(position).getXiangmuId()) {
                    fwLoudong = "";
                    fwDanyuan = "";
                    fwFanghao = "";
                    fwGuanliyuan = "";
                    tvLoudong.setText("");
                    tvDanyaun.setText("");
                    tvFanghao.setText("");
                    tvGuanliyuan.setText("");
                }
                xiangmuId = mXiangMu.getData().get(position).getXiangmuId();

                break;
            case 2:
                if (louPanList == null) {
                    return;
                }
                tvLoudong.setText(louPanList.get(position).trim());
                if (!tvLoudong.getText().toString().equals(fwLoudong)) {
                    fwDanyuan = "";
                    fwFanghao = "";
                    tvDanyaun.setText("");
                    tvFanghao.setText("");
                    fwGuanliyuan = "";
                    tvGuanliyuan.setText("");
                }
                fwLoudong = louPanList.get(position).trim();
                KLog.i(fwLoudong);
                break;
            case 3:
                if (danYuanList == null || danYuanList.size() == 0) {
                    return;
                }
                tvDanyaun.setText(danYuanList.get(position).trim());
                if (!tvDanyaun.getText().equals(fwDanyuan)) {
                    fwFanghao = "";
                    tvFanghao.setText("");
                    fwGuanliyuan = "";
                    tvGuanliyuan.setText("");
                }
                fwDanyuan = danYuanList.get(position).trim();
                break;
            case 4:
                if (fangwuhao == null || danYuanList.size() == 0) {
                    return;
                }
                tvFanghao.setText(fangwuhao.get(position).trim());
                if (!tvFanghao.getText().toString().equals(fwFanghao)) {
                    fwGuanliyuan = "";
                    tvGuanliyuan.setText("");
                }
                fwFanghao = fangwuhao.get(position).trim();
                break;
            case 5:
                if (guanliyuan == null || guanliyuan.size() == 0) {
                    return;
                }
                tvGuanliyuan.setText(guanliyuan.get(position).trim());
                fwGuanliyuan = guanliyuan.get(position).split(" ")[1];
                break;
            default:
                break;
        }
    }

    //获取管理员
    private void getGuanLiYuan() {

        Map<String, String> map = new HashMap<>();
        map.put("uuid", Contains.uuid);
        map.put("loudong", fwLoudong);
        map.put("danyuan", fwDanyuan);
        map.put("fanghao", fwFanghao);
        map.put("xmid", xiangmuId + "");
        Disposable disposable = HttpAPIWrapper.getInstance(HttpAPIWrapper.getOkHttpClient()).findguanliyuan(map)
                .subscribe(new Consumer<CxwyYezhu>() {
                    @Override
                    public void accept(@NonNull CxwyYezhu content) throws Exception {        //[2490,"12"]   第一个是房屋编号，第二个房号，。显示的房号，传的是房屋编号
                        progressDialog.hide();
                        KLog.i("TAG", content.getData().toString());
                        guanliyuan = new ArrayList<>();
                        for (int i = 0; i < content.getData().size(); i++) {
                            guanliyuan.add(i,content.getData().get(i).getYezhuName() + " " + content.getData().get(i).getYezhuShouji());
                        }
                        xiangmuAdapter = new NumericWheelAdapter(CameraAddNewActivity.this, 0, guanliyuan.size() - 1, "", guanliyuan);
                        xiangmuAdapter.setTextSize(15);
                        wheelView.setViewAdapter(xiangmuAdapter);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                    }
                });
        disposables.add(disposable);
    }

    private void getFangHao() {
        Map<String, String> map = new HashMap<>();
        map.put("lpid", xiangmuId + "");
        map.put("fwLoudong", fwLoudong + "");
        map.put("fwDanyuan", fwDanyuan + "");
        Disposable disposable = HttpAPIWrapper.getInstance(HttpAPIWrapper.getOkHttpClient(), "").findfanghao(map)
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String content) throws Exception {        //[2490,"12"]   第一个是房屋编号，第二个房号，。显示的房号，传的是房屋编号
                        progressDialog.hide();
//                        String after = xiangMu.replace("[", "").replace("]", "").replace(xiangmuId + ",", "").replace("\"", "");
//                        String [] stringArr= after.split(",");
//                        fangHaoList = new ArrayList<String>(Arrays.asList(stringArr));
                        KLog.i(content.length());
                        content = content.trim().substring(2, content.length() - 4);
                        KLog.i(content.length());
                        KLog.i(content);
                        String[] stringArr = content.split("\\],\\[");
                        fangwuBianhao = new ArrayList<String>();
                        fangwuhao = new ArrayList<String>();

                        for (int i = 0; i < stringArr.length; i++) {
                            String[] temp = stringArr[i].split(",");
                            fangwuBianhao.add(temp[0]);
                            fangwuhao.add(temp[1].replace("\"", ""));
                        }
//                        louPanList = Arrays.asList(stringArr);                   //错误
                        xiangmuAdapter = new NumericWheelAdapter(CameraAddNewActivity.this, 0, fangwuhao.size() - 1, "", fangwuhao);
                        xiangmuAdapter.setTextSize(15);
                        wheelView.setViewAdapter(xiangmuAdapter);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                    }
                });
        disposables.add(disposable);
    }

    private void getDanYuan() {
        Map<String, String> map = new HashMap<>();
        map.put("lpid", xiangmuId + "");
        map.put("fwLoudong", fwLoudong + "");
        Disposable disposable = HttpAPIWrapper.getInstance(HttpAPIWrapper.getOkHttpClient(), "").finddanyuan(map)
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String xiangMu) throws Exception {
                        progressDialog.hide();
                        String after = xiangMu.replace("[", "").replace("]", "").replace(xiangmuId + ",\"" + fwLoudong + "\",", "").replace("\"", "");
                        String[] stringArr = after.split(",");
                        danYuanList = new ArrayList<String>(Arrays.asList(stringArr));
//                        louPanList = Arrays.asList(stringArr);                   //错误
                        xiangmuAdapter = new NumericWheelAdapter(CameraAddNewActivity.this, 0, danYuanList.size() - 1, "", danYuanList);
                        xiangmuAdapter.setTextSize(15);
                        wheelView.setViewAdapter(xiangmuAdapter);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                    }
                });
        disposables.add(disposable);
    }

    private void getLouPan() {
        Map<String, String> map = new HashMap<>();
        map.put("lpid", xiangmuId + "");
        Disposable disposable = HttpAPIWrapper.getInstance(HttpAPIWrapper.getOkHttpClient(), "").findloupan(map)
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String content) throws Exception {
                        KLog.i(content);
                        Log.e("TAG", content);//[[321,"1"],[321,"2"],[321,"5"]]
                        String after = content.replace("[", "").replace("]", "").replace(xiangmuId + ",", "").replace("\"", "");
                        String[] stringArr = after.split(",");
                        louPanList = new ArrayList<String>(Arrays.asList(stringArr));
//                        louPanList = Arrays.asList(stringArr);                   //错误
                        xiangmuAdapter = new NumericWheelAdapter(CameraAddNewActivity.this, 0, louPanList.size() - 1, "", louPanList);
                        xiangmuAdapter.setTextSize(15);
                        wheelView.setViewAdapter(xiangmuAdapter);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                    }
                });
        disposables.add(disposable);
    }

    private void getXiangMu() {
        Map<String, String> map = new HashMap<>();
        map.put("uuId", Contains.uuid);
        Disposable disposable = HttpAPIWrapper.getInstance(HttpAPIWrapper.getOkHttpClient()).findXm(map)
                .subscribe(new Consumer<XiangMu>() {
                    @Override
                    public void accept(@NonNull XiangMu xiangMu) throws Exception {
                        progressDialog.hide();
                        mXiangMu = xiangMu;
                        if (xiangMu.status == 0) {
                            xiangmuList = new ArrayList<String>();
                            for (int i = 0; i < xiangMu.getData().size(); i++) {
                                if (xiangMu.getData().get(i) != null) {
                                    xiangmuList.add(xiangMu.getData().get(i).getXiangmuName());
                                }
                            }
                            xiangmuAdapter = new NumericWheelAdapter(CameraAddNewActivity.this, 0, xiangmuList.size() - 1, "", xiangmuList);
                            xiangmuAdapter.setTextSize(15);
                            wheelView.setViewAdapter(xiangmuAdapter);
                        } else {
                            // onError(xiangMu.status, xiangMu.error);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                    }
                });
        disposables.add(disposable);
    }


    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

/* @Override
    public void onBackPressed() {
        super.onBackPressed();
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
                    Toast.makeText(CameraAddNewActivity.this, "设备名字不合法,名字为4-15位", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (deviceId.getText().toString().equals("")) {
                    Toast.makeText(CameraAddNewActivity.this, "请输入设备ID", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (xiangmuId == -1) {
                    Toast.makeText(CameraAddNewActivity.this, "请选择项目", Toast.LENGTH_SHORT).show();
                    break;
                }
                progressDialog.show();
                String callID = deviceId.getText().toString().trim();//设备号
                String pwd = P2PHandler.getInstance().EntryPassword(devicePwd.getText().toString().trim());//经过转换后的设备密码
                if (mflag || (mflag && frag.equals("0"))) {
                    KLog.i("设置初始密码");
                    P2PHandler.getInstance().setInitPassword(ipFlag, pwd, "", "");
                } else {
                    KLog.i("确认密码");
                    P2PHandler.getInstance().checkPassword(callID, pwd);
                }
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
*/
}