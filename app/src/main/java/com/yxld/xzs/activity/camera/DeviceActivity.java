package com.yxld.xzs.activity.camera;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemChildLongClickListener;
import com.google.gson.Gson;
import com.p2p.core.P2PHandler;
import com.p2p.core.P2PSpecial.HttpErrorCode;
import com.p2p.core.global.P2PConstants;
import com.socks.library.KLog;
import com.yxld.xzs.R;
import com.yxld.xzs.adapter.CameraListAdapter;
import com.yxld.xzs.adapter.MyItemDecoration;
import com.yxld.xzs.adapter.SpacesItemDecoration;
import com.yxld.xzs.base.BaseActivity;
import com.yxld.xzs.base.DemoApplicationLike;
import com.yxld.xzs.contain.Contains;
import com.yxld.xzs.entity.APPCamera;
import com.yxld.xzs.entity.AppCameraList;
import com.yxld.xzs.entity.BaseBack;
import com.yxld.xzs.entity.RemoveDevice;
import com.yxld.xzs.http.api.HttpAPIWrapper;
import com.yxld.xzs.http.api.support.ErrorHandlerInterceptor;
import com.yxld.xzs.utils.StringUitl;
import com.yxld.xzs.utils.ToastUtil;
import com.yxld.xzs.utils.UIUtils;
import com.yxld.xzs.view.CameraDialog;
import com.yxld.xzs.view.ConfirmDialog;
import com.yxld.xzs.yoosee.FriendStatus;
import com.yxld.xzs.yoosee.P2PListener;
import com.yxld.xzs.yoosee.SettingListener;
import com.zaaach.toprightmenu.TopRightMenu;
import com.zhy.autolayout.AutoRelativeLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import static com.yxld.xzs.http.HttpService.URL_GET_CAMERA_All;

/**
 * @author hu
 * @Package com.yxld.yxchuangxin.ui.activity.camera
 * @Description: $description
 * @date 2017/06/20 17:26:32
 */

public class DeviceActivity extends BaseActivity {

    @BindView(R.id.iv_search_icon)
    ImageView ivSearchIcon;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.market_search)
    AutoRelativeLayout marketSearch;
    @BindView(R.id.search)
    TextView search;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    private TopRightMenu mTopRightMenu;

    private AppCameraList camear;

    private RemoveDevice removeDevice;
    private APPCamera camera;
    private CameraListAdapter cameraListAdapter;
    private View notDataView;

    public static String LoginID = "";

    private String[] Deviceid;

    Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    refreshLayout.setRefreshing(false);
                    progressDialog.hide();
                    onCreateOptionsMenu(menu);
                    Toast.makeText(DeviceActivity.this, camear.msg + ",摄像头数量为：" + camear.getData().size(), Toast.LENGTH_SHORT).show();
                    if (camear.getStatus() != 0) {
                        return;
                    }

                    if (camear.getData() == null || camear.getData().size() == 0) {
                        cameraListAdapter.setNewData(new ArrayList<AppCameraList.DataBean>());
                        cameraListAdapter.notifyDataSetChanged();
                        return;
                    }
//                    mOptionOprate.getCamera(camear);
                    Deviceid = new String[camear.getData().size()];
                    for (int i = 0; i < camear.getData().size(); i++) {
                        Deviceid[i] = camear.getData().get(i).getEquipSerial() + "";
                    }
                    P2PHandler.getInstance().getFriendStatus(Deviceid, P2PConstants.P2P_Server.SERVER_INDEX);
                    break;
                case 1:
                    ToastUtil.show(DeviceActivity.this, removeDevice.getMsg());
//                    progressDialog.hide();
                    getAllCamera();
                    break;
                case 2:
                    loginCallBack(camera);
                    break;
                case 100:
                    ToastUtil.show(DeviceActivity.this, "数据解析出错");
                    refreshLayout.setRefreshing(false);
//                    progressDialog.hide();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        camera = null;
    }

    protected void initView() {
        setContentView(R.layout.activity_device);
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);
//        ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS); //(WidgetSearchActivity是当前的Activity)
//        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.toggleSoftInput(0, InputMethodManager.RESULT_HIDDEN);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        notDataView = getLayoutInflater().inflate(R.layout.layout_empty_data, (ViewGroup) recyclerView.getParent(), false);
        List<AppCameraList.DataBean> list = new ArrayList<>();
        cameraListAdapter = new CameraListAdapter(list);
        cameraListAdapter.setEmptyView(notDataView);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new MyItemDecoration());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(new SpacesItemDecoration(UIUtils.getDisplayWidth(this) / 1080 * 20));
        recyclerView.setAdapter(cameraListAdapter);
        cameraListAdapter.setOnItemChildLongClickListener(new BaseQuickAdapter.OnItemChildLongClickListener() {
            @Override
            public boolean onItemChildLongClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                showDeletCameraDalog(i);
                return false;
            }
        });
        recyclerView.addOnItemTouchListener(ClickListener);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAllCamera();
            }
        });
        P2PHandler.getInstance().p2pDisconnect();
        Login();

        StringUitl.setInputName(etName);
    }


    Menu menu;
    boolean isMenu = false;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        if ((camear != null && camear.getData() != null) && camear.getStatus() == 0) {
            if (!isMenu) {
                getMenuInflater().inflate(R.menu.menu_camera_device, menu);
                isMenu = true;
            }
            return true;
        } else {
            return true;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            System.gc();
        }
        KLog.i(Contains.appLogin.getAdminXiangmuId());
        // TODO: 2017/11/29 修改了权限设置
        View tianjia = findViewById(R.id.add);
        switch (item.getItemId()) {
            case R.id.add:
                CameraDialog.showDialog(DeviceActivity.this, new CameraDialog.OnAutoListener() {
                    @Override
                    public void onAutoOne() {
                        Intent config = new Intent(DeviceActivity.this, CameraConfigActivity.class);
                        config.putExtra("enter_type", 1);
                        startActivity(config);
                    }

                    @Override
                    public void onAutoTwo() {
                        Intent add = new Intent(DeviceActivity.this, CameraAddActivity.class);
                        add.putExtra("ishasContactId", false);
                        startActivity(add);
                    }

                    @Override
                    public void onAutoThree() {
                        Intent add = new Intent(DeviceActivity.this, WiredDeviceListActivity.class);
                        add.putExtra("isDeviceActivity", true);
                        startActivity(add);
                    }
                });
//                    mTopRightMenu = new TopRightMenu(DeviceActivity.this);
//                    mTopRightMenu
//                            .setHeight(RecyclerView.LayoutParams.WRAP_CONTENT)     //默认高度480
//                            .setWidth(RecyclerView.LayoutParams.WRAP_CONTENT)      //默认宽度wrap_content
////                        .showIcon(true)     //显示菜单图标，默认为true
//                            .dimBackground(true)           //背景变暗，默认为true
//                            .needAnimationStyle(true)   //显示动画，默认为true
//                            .setAnimationStyle(R.style.TRM_ANIM_STYLE)  //默认为R.style.TRM_ANIM_STYLE
//                            .addMenuItem(new com.zaaach.toprightmenu.MenuItem(R.mipmap.icon_peiwang, "设备配网"))
//                            .addMenuItem(new com.zaaach.toprightmenu.MenuItem(R.mipmap.icon_add_device, "添加设备"))
//                            .setOnMenuItemClickListener(new TopRightMenu.OnMenuItemClickListener() {
//                                @Override
//                                public void onMenuItemClick(int position) {
//                                    switch (position) {
//                                        case 0:
//                                            Intent config = new Intent(DeviceActivity.this, CameraConfigActivity.class);
//                                            startActivity(config);
//                                            break;
//                                        case 1:
//                                            Intent add = new Intent(DeviceActivity.this, CameraAddActivity.class);
//                                            add.putExtra("ishasContactId", false);
//                                            startActivity(add);
//                                            break;
//                                        default:break;
//                                    }
//                                }
//                            })
//                            .showAsDropDown(tianjia, 0, 0);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @OnClick(R.id.search)
    public void onViewClicked() {
        searchByXm(etName.getText().toString().trim());
    }

    public void Login() {
//        progressDialog.show();
        String sha256 = StringUitl.encryptSHA256ToString(APPID + Contains.appLogin.getCxwyPeisongPhone());
        String md5 = StringUitl.Md5(sha256 + 3 + sha256);
        KLog.i("我的包名是：" + "com.yxld.xzs");
        KLog.i("SHA256加密 ：" + sha256);
        KLog.i("MD5 32bit：" + md5);
        //mView.showProgressDialog();
        KLog.i(05 << 24 | 14 << 16 | 01 << 8 | 00);
        FormBody body = new FormBody.Builder()
                .add("AppVersion", (05 << 24 | 14 << 16 | 01 << 8 | 00) + "")     //05 和14 为技威给的版本号,最后的00位app版本
                .add("AppOS", "3")//0.其它 1.windows 2.iOS 3.android 4.arm_linux
                .add("AppName", "xinzhushou")//app的英文名
                .add("Language", "zh-Hans")//语言的缩写
                .add("ApiVersion", "1")//固定传1
                .add("AppID", APPID)//技威公司分配
                .add("AppToken", APPToken)//技威公司分配
                .add("PackageName", "com.yxld.xzs")//包名
                .add("Option", "3")//传3即可
                .add("PlatformType", "3")//传3即可
                .add("UnionID", sha256)//UnionID=sha256(AppID+唯一序列号);
                .add("User", "") //传空即可
                .add("UserPwd", "") //传空即可
                .add("Token", "") //传空即可
                .add("StoreID", "") //传空即可
                .add("Sign", md5)//Sign=md5(UnionID+PlatformType+UnionID),md5是md5_32bit的加密算法
                .build();
        Request request = new Request.Builder().url(URL_GET_CAMERA).post(body).build();
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new ErrorHandlerInterceptor())
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                KLog.e(e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) {
                KLog.e(response.toString());
                String result = null;
                try {
                    result = response.body().string();
                    KLog.i(result);
                    Gson gson = new Gson();
                    camera = gson.fromJson(result, APPCamera.class);
                    myHandler.sendEmptyMessage(2);
                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        });
    }

    private void loginCallBack(APPCamera info) {
//        progressDialog.show();
        switch (info.getError_code()) {
            case HttpErrorCode.ERROR_0:
                int code1 = Integer.parseInt(info.getP2PVerifyCode1());
                int code2 = Integer.parseInt(info.getP2PVerifyCode2());
                boolean connect = P2PHandler.getInstance().p2pConnect(info.getUserID(), code1, code2);
                KLog.i("connect===========" + info.getUserID() + "+" + code1 + "+" + code2);
                if (connect) {
                    P2PHandler.getInstance().p2pInit(DemoApplicationLike.getInstance(), new P2PListener(), new SettingListener());
                    LoginID = info.getUserID();
                    getAllCamera();
                    KLog.i(LoginID + "-----------------");
                } else {
                    ToastUtil.show(this, "连接失败,请重新进入居家安防");
                }
                break;
            case HttpErrorCode.ERROR_998:
                break;
            case HttpErrorCode.ERROR_10902011:
                ToastUtil.show(this, "用户不存在");
                break;
            default:
                String msg = String.format("摄像头登录失败测试版(%s)", info.getError_code());
                ToastUtil.show(this, msg);
                break;
        }
    }

    public void getAllCamera() {       //?apptoken=%1$s
        progressDialog.show();
        KLog.i("开始获取列表");
        Map<String, String> map = new HashMap<>();
        map.put("uuId", Contains.uuid); //http://www.hnchxwl.com/wygl/security/findAllShebeiByYzid?&apptoken=efa74a75-86a4-44ff-97ad-ecd0f1746441
//        FormBody body = new FormBody.Builder()
//                .add("apptoken", "69c14500-4a64-429c-a4f2-0c51924c548e")
//                .build();
        Request request = new Request.Builder().url(URL_GET_CAMERA_All + "uuId=" + Contains.uuid + "&xmid=" + getIntent().getStringExtra("xiangmuId")).get().build();
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new ErrorHandlerInterceptor())
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) {
                String result = null;
                try {
                    result = response.body().string();
                    KLog.i(result);
                    Gson gson = new Gson();
                    camear = gson.fromJson(result, AppCameraList.class);
                    myHandler.sendEmptyMessage(0);
                } catch (Exception e) {
                    myHandler.sendEmptyMessage(100);
                    e.printStackTrace();
                }
            }
        });
    }

    //获取设备状态
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void DeviceStatus(FriendStatus msg) {
//        progressDialog.hide();
        if (msg.bRequestResult == 0) {
            P2PHandler.getInstance().getFriendStatus(Deviceid, P2PConstants.P2P_Server.SERVER_INDEX);
            ToastUtil.show(this, "状态获取失败~  重试中");
        } else {
            if (msg.status.length == 0||camear.getData().size()<=0) {
                return;
            }
            for (int i = 0; i < msg.status.length; i++) {
                camear.getData().get(i).setEquipStatus(msg.status[i]);
                camear.getData().get(i).setBufangStatus(msg.defenceState[i]);
                camear.getData().get(i).setEquipXiangmuName(getIntent().getStringExtra("xiangmu"));
                Log.d("...", "vRetGetIndexFriendStatus: status" + msg.status[i]);
            }
            setCameraList(camear);
        }
    }

    public void setCameraList(AppCameraList list) {
        if (list == null) {
            KLog.i("数据看到的是空。。。。。。。。。。。。。。");
        }
        cameraListAdapter.setNewData(list.getData());
    }

    //item长按事件
    private OnItemChildLongClickListener LongClickListener = new OnItemChildLongClickListener() {
        @Override
        public void onSimpleItemChildLongClick(BaseQuickAdapter adapter, View view, final int position) {
            if (Contains.appLogin.getAdminXiangmuId() == -1) {
                showDeletCameraDalog(position);
            }
//            positions = position;
//            if (cameraController == null) {
//                cameraController = new CameraControllerImpl();
//            }
//            Map<String, String> map = new HashMap<String, String>();
//            map.put("sb_ipc_id", list.get(position).getSb_ipc_id());//设备ID
//            map.put("apptoken", Contains.uuid);//用户TOKEN
//            cameraController.getCameraDel(mRequestQueue, map, dellistener);
        }
    };

    private void showDeletCameraDalog(final int position) {
        ConfirmDialog.showDialog(this, "提示", "删除摄像头", new ConfirmDialog.OnConfirmListener() {
            @Override
            public void onCancel() {

            }

            @Override
            public void onConfirm() {
                Map<String, String> map = new HashMap<String, String>();
                map.put("equipId", cameraListAdapter.getData().get(position).getEquipId() + "");//设备ID
                map.put("uuId", Contains.uuid);//用户TOKEN
                deletCamera(map, position);
            }
        });
    }

    public void deletCamera(Map map, final int postion) {
//        progressDialog.show();
        Disposable disposable = HttpAPIWrapper.getInstance(HttpAPIWrapper.getOkHttpClient()).deletCamera(map)
                .subscribe(new Consumer<BaseBack>() {
                    @Override
                    public void accept(@NonNull BaseBack removeDevice) throws Exception {
//                        progressDialog.hide();
                        Toast.makeText(DeviceActivity.this, removeDevice.getMsg(), Toast.LENGTH_SHORT).show();

                        if (removeDevice.getStatus() == 0) {
                            getAllCamera();
                            cameraListAdapter.getData().remove(postion);
                            refreshLayout.setRefreshing(true);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                    }
                });
        disposables.add(disposable);
    }

    //item点击事件
    private OnItemChildClickListener ClickListener = new OnItemChildClickListener() {
        @Override
        public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
            CameraListAdapter listAdapter = (CameraListAdapter) adapter;
            switch (view.getId()) {
                case R.id.camera_image:
                    Intent device = new Intent(DeviceActivity.this, CameraActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("deviceId", listAdapter.getData().get(position).getEquipSerial() + "");
                    bundle.putString("devicePwd", listAdapter.getData().get(position).getEquipPassword());
                    bundle.putString("deviceName", listAdapter.getData().get(position).getEquipName());
                    bundle.putString("deviceId2", listAdapter.getData().get(position).getEquipId() + "");
                    bundle.putString("xiangmuId", listAdapter.getData().get(position).getEquipXiangmuId() + "");
                    bundle.putString("deviceXm", listAdapter.getData().get(position).getEquipXiangmuName());
//                    bundle.putString("deviceId", listAdapter.getData().get(position).getSb_ipc_id());
//                    bundle.putString("devicePwd", listAdapter.getData().get(position).getSb_ipc_pwd());
//                    bundle.putString("deviceName", listAdapter.getData().get(position).getSb_name());
                    device.putExtras(bundle);
                    startActivity(device);
                    break;
                case R.id.camera_video:
//                    Intent video = new Intent(DeviceActivity.this, RecordFilesActivity.class);
//                    Bundle bundle1 = new Bundle();
//                    bundle1.putString("deviceId", adapter.getData().get(position).getSb_ipc_id());
//                    bundle1.putString("devicePwd", adapter.getData().get(position).getSb_ipc_pwd());
//                    video.putExtras(bundle1);
//                    startActivity(video);
                    Intent video = new Intent(DeviceActivity.this, RecordFilesActivity.class);
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("deviceId", listAdapter.getData().get(position).getEquipSerial() + "");
                    bundle1.putString("devicePwd", listAdapter.getData().get(position).getEquipPassword());
                    video.putExtras(bundle1);
                    startActivity(video);
                    break;
            }

        }
    };

    private void searchByXm(String projectName) {
        if (camear.getData() == null) {
            Toast.makeText(this, "没有可以搜索的设备", Toast.LENGTH_SHORT).show();
            return;
        }
        if (projectName.equals("")) {
            cameraListAdapter.setNewData(camear.getData());
            return;
        }
        List<AppCameraList.DataBean> list = new ArrayList<>();
        for (int i = 0; i < camear.getData().size(); i++) {
            if (camear.getData().get(i).getEquipXiangmuName().contains(projectName)) {
                list.add(camear.getData().get(i));
            }
        }
        cameraListAdapter.setNewData(list);
    }
}