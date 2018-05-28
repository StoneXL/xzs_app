package com.yxld.xzs.activity.camera;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yxld.xzs.R;
import com.yxld.xzs.adapter.ProjectListAdapter;
import com.yxld.xzs.base.BaseFragment;
import com.yxld.xzs.contain.Contains;
import com.yxld.xzs.entity.XiangMu;
import com.yxld.xzs.http.api.HttpAPIWrapper;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * 作者：Android on 2017/9/8
 * 邮箱：365941593@qq.com
 * 描述：
 */

public class DeviceListFragment extends BaseFragment {
    @Override
    protected void lazyLoad() {

    }

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshlayout)
    SwipeRefreshLayout refreshlayout;

    ProjectListAdapter projectListAdapter;

    Unbinder unbinder;

    protected CompositeDisposable disposables = new CompositeDisposable();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_project_list, null);
        unbinder = ButterKnife.bind(this, view);
//        EventBus.getDefault().register(this);
        getXiangMu();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        refreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getXiangMu();
            }
        });
        return view;
    }

    private void getXiangMu() {
        Map<String, String> map = new HashMap<>();
        map.put("uuId", Contains.uuid);
        Disposable disposable = HttpAPIWrapper.getInstance(HttpAPIWrapper.getOkHttpClient()).findXm(map)
                .subscribe(new Consumer<XiangMu>() {
                    @Override
                    public void accept(@NonNull XiangMu xiangMu) throws Exception {
                        refreshlayout.setRefreshing(false);
                        if (xiangMu.status == 0) {
                            projectListAdapter = new ProjectListAdapter(xiangMu.getData());
                            recyclerView.setAdapter(projectListAdapter);
                            projectListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                                    Intent intent = new Intent(getActivity(), DeviceActivity.class);
                                    intent.putExtra("xiangmuId", projectListAdapter.getData().get(i).getXiangmuId() + "");
                                    intent.putExtra("xiangmu", projectListAdapter.getData().get(i).getXiangmuName()+ "");
                                    startActivity(intent);
                                }
                            });
                        } else {
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                    }
                });
        disposables.add(disposable);
    }


//    @BindView(R.id.iv_search_icon)
//    ImageView ivSearchIcon;
//    @BindView(R.id.et_name)
//    EditText etName;
//    @BindView(R.id.market_search)
//    AutoRelativeLayout marketSearch;
//    @BindView(R.id.search)
//    TextView search;
//    @BindView(R.id.recyclerView)
//    RecyclerView recyclerView;
//    @BindView(R.id.refresh_layout)
//    SwipeRefreshLayout refreshLayout;
//    Unbinder unbinder;
//    private View notDataView;
//
//    public static String LoginID;//登录返回
//    protected CompositeDisposable disposables = new CompositeDisposable();
//
//    private String[] Deviceid;
//    private AppCameraList camear;
//
//    private OptionOprate mOptionOprate;
//
//    Handler myHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what) {
//                case 0:
//                    refreshLayout.setRefreshing(false);
////                    progressDialog.hide();
//                    Toast.makeText(getActivity(), camear.msg, Toast.LENGTH_SHORT).show();
//                    if (camear.getStatus() != 0) {
//                        return;
//                    }
//
//                    if (camear.getData() == null || camear.getData().size() == 0) {
//                        ToastUtil.show(getActivity(), camear.msg);
//                        cameraListAdapter.setNewData(new ArrayList<AppCameraList.DataBean>());
//                        cameraListAdapter.notifyDataSetChanged();
//                        return;
//                    }
//                    mOptionOprate.getCamera(camear);
//                    Deviceid = new String[camear.getData().size()];
//                    for (int i = 0; i < camear.getData().size(); i++) {
//                        Deviceid[i] = camear.getData().get(i).getEquipSerial() + "";
//                    }
//                    P2PHandler.getInstance().getFriendStatus(Deviceid, P2PConstants.P2P_Server.SERVER_INDEX);
//                    break;
//                case 1:
//                    ToastUtil.show(getActivity(), removeDevice.getMsg());
////                    progressDialog.hide();
//                    getAllCamera();
//                    break;
//                case 2:
//                    loginCallBack(camera);
//                    break;
//                case 100:
//                    ToastUtil.show(getActivity(), "数据解析出错");
//                    refreshLayout.setRefreshing(false);
////                    progressDialog.hide();
//                    break;
//            }
//        }
//    };
//    private RemoveDevice removeDevice;
//    private APPCamera camera;
//    private CameraListAdapter cameraListAdapter;
//
//    @Override
//    protected void lazyLoad() {
//
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.activity_project_list, null);
//        unbinder = ButterKnife.bind(this, view);
//        EventBus.getDefault().register(this);
//        notDataView = getActivity().getLayoutInflater().inflate(R.layout.layout_empty_data, (ViewGroup) recyclerView.getParent(), false);
//        List<AppCameraList.DataBean> list = new ArrayList<>();
//        cameraListAdapter = new CameraListAdapter(list);
//        cameraListAdapter.setEmptyView(notDataView);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.addItemDecoration(new MyItemDecoration());
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        recyclerView.setAdapter(cameraListAdapter);
//        recyclerView.addOnItemTouchListener(LongClickListener);
//        recyclerView.addOnItemTouchListener(ClickListener);
//        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                getAllCamera();
//            }
//        });
////        P2PHandler.getInstance().p2pDisconnect();
////        Login();
//        StringUitl.setInputName(etName);
//        return view;
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        unbinder.unbind();
//    }
//
//    @Override
//    public void onResume() {
//        if (isVisible) {
//            getAllCamera();
//        }
//        super.onResume();
//    }
//
//    public void Login() {
////        progressDialog.show();
//        String sha256 = StringUitl.encryptSHA256ToString(APPID + Contains.appLogin.getCxwyPeisongPhone());
//        String md5 = StringUitl.Md5(sha256 + 3 + sha256);
//        KLog.i("我的包名是：" + "com.yxld.xzs");
//        KLog.i("SHA256加密 ：" + sha256);
//        KLog.i("MD5 32bit：" + md5);
////        Map<String, String> map = new HashMap<String, String>();
////        map.put("AppVersion", "56492291");
////        map.put("AppOS", "3");//0.其它 1.windows 2.iOS 3.android 4.arm_linux
////        map.put("AppName", "xinshequ");//app的英文名
////        map.put("Language", "zh-Hans");//语言的缩写
////        map.put("ApiVersion", "1");//固定传1
////        map.put("AppID", APPID);//技威公司分配
////        map.put("AppToken", APPToken);//技威公司分配
////        map.put("PackageName", "com.yxld.yxchuangxin");//包名
////        map.put("Option", "3");//传3即可
////        map.put("PlatformType", "3");//传3即可
////        map.put("UnionID", sha256);//UnionID=sha256(AppID+唯一序列号);
////        map.put("User", ""); //传空即可
////        map.put("UserPwd", ""); //传空即可
////        map.put("Token", ""); //传空即可
////        map.put("StoreID", ""); //传空即可
////        map.put("Sign", md5);//Sign=md5(UnionID+PlatformType+UnionID),md5是md5_32bit的加密算法
//        //mView.showProgressDialog();
//        KLog.i(05 << 24 | 14 << 16 | 01 << 8 | 00);
//        FormBody body = new FormBody.Builder()
//                .add("AppVersion", (05 << 24 | 14 << 16 | 01 << 8 | 00) + "")     //05 和14 为技威给的版本号,最后的00位app版本
//                .add("AppOS", "3")//0.其它 1.windows 2.iOS 3.android 4.arm_linux
//                .add("AppName", "xinzhushou")//app的英文名
//                .add("Language", "zh-Hans")//语言的缩写
//                .add("ApiVersion", "1")//固定传1
//                .add("AppID", APPID)//技威公司分配
//                .add("AppToken", APPToken)//技威公司分配
//                .add("PackageName", "com.yxld.xzs")//包名
//                .add("Option", "3")//传3即可
//                .add("PlatformType", "3")//传3即可
//                .add("UnionID", sha256)//UnionID=sha256(AppID+唯一序列号);
//                .add("User", "") //传空即可
//                .add("UserPwd", "") //传空即可
//                .add("Token", "") //传空即可
//                .add("StoreID", "") //传空即可
//                .add("Sign", md5)//Sign=md5(UnionID+PlatformType+UnionID),md5是md5_32bit的加密算法
//                .build();
//        Request request = new Request.Builder().url(URL_GET_CAMERA).post(body).build();
//        OkHttpClient client = new OkHttpClient.Builder()
//                .addInterceptor(new ErrorHandlerInterceptor())
//                .connectTimeout(10, TimeUnit.SECONDS)
//                .build();
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) {
//                String result = null;
//                try {
//                    result = response.body().string();
//                    KLog.i(result);
//                    Gson gson = new Gson();
//                    camera = gson.fromJson(result, APPCamera.class);
//                    myHandler.sendEmptyMessage(2);
//                } catch (Exception e) {
//
//                    e.printStackTrace();
//                }
//            }
//        });
////        ServiceFactory.httpService()
////                .getCameraLogin((Map)map)
////                .compose(new DefaultTransformer4<APPCamera>())
////                .subscribe(new RxSubscriber<APPCamera>(this) {
////                    @Override
////                    public void onNext(APPCamera camera) {
////                        KLog.i("onSuccesse");
////                        loginCallBack(camera);
////                    }
////                });
//
//    }
//
//    private void loginCallBack(APPCamera info) {
////        progressDialog.show();
//        switch (info.getError_code()) {
//            case HttpErrorCode.ERROR_0:
//                int code1 = Integer.parseInt(info.getP2PVerifyCode1());
//                int code2 = Integer.parseInt(info.getP2PVerifyCode2());
//                boolean connect = P2PHandler.getInstance().p2pConnect(info.getUserID(), code1, code2);
//                KLog.i("connect===========" + info.getUserID() + "+" + code1 + "+" + code2);
//                if (connect) {
//                    P2PHandler.getInstance().p2pInit(DemoApplicationLike.getInstance(), new P2PListener(), new SettingListener());
//                    LoginID = info.getUserID();
//                    getAllCamera();
//                    KLog.i(LoginID + "-----------------");
//                } else {
//                    ToastUtil.show(getActivity(), "连接失败,请重新进入居家安防");
//                }
//                break;
//            case HttpErrorCode.ERROR_998:
//                break;
//            case HttpErrorCode.ERROR_10902011:
//                ToastUtil.show(getActivity(), "用户不存在");
//                break;
//            default:
//                String msg = String.format("摄像头登录失败测试版(%s)", info.getError_code());
//                ToastUtil.show(getActivity(), msg);
//                break;
//        }
//    }
//
//    public void getAllCamera() {       //?apptoken=%1$s
////        progressDialog.show();
//        KLog.i("开始获取列表");
//        Map<String, String> map = new HashMap<>();
//        map.put("uuId", Contains.uuid); //http://www.hnchxwl.com/wygl/security/findAllShebeiByYzid?&apptoken=efa74a75-86a4-44ff-97ad-ecd0f1746441
////        FormBody body = new FormBody.Builder()
////                .add("apptoken", "69c14500-4a64-429c-a4f2-0c51924c548e")
////                .build();
//        Request request = new Request.Builder().url(URL_GET_CAMERA_All + "uuId=" + Contains.uuid).get().build();
//        OkHttpClient client = new OkHttpClient.Builder()
//                .addInterceptor(new ErrorHandlerInterceptor())
//                .connectTimeout(10, TimeUnit.SECONDS)
//                .build();
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) {
//                String result = null;
//                try {
//                    result = response.body().string();
//                    KLog.i(result);
//                    Gson gson = new Gson();
//                    camear = gson.fromJson(result, AppCameraList.class);
//                    myHandler.sendEmptyMessage(0);
//                } catch (Exception e) {
//                    myHandler.sendEmptyMessage(100);
//                    e.printStackTrace();
//                }
//            }
//        });
////        ServiceFactory.httpService()
////                .getAllCamera((Map)map)
////                .compose(new DefaultTransformer2<AppCameraList>())
////                .subscribe(new RxSubscriber<AppCameraList>(this) {
////                    @Override
////                    public void onNext(AppCameraList list) {
////                        KLog.i("onSuccesse");
////                        progressDialog.hide();
////                        if (list.getStatus() != 0) {
////                            ToastUtil.show(DeviceActivity.this, list.getMSG());
////                            return;
////                        }
////                        camear = list;
////                        Deviceid = new String[list.getData().size()];
////                        for (int i = 0; i < list.getData().size(); i++) {
////                            Deviceid[i] = list.getData().get(i).getSb_ipc_id();
////                        }
////                        P2PHandler.getInstance().getFriendStatus(Deviceid, P2PConstants.P2P_Server.SERVER_INDEX);
////                    }
////                });
//    }
//
//
//    //获取设备状态
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void DeviceStatus(FriendStatus msg) {
////        progressDialog.hide();
//        if (msg.bRequestResult == 0) {
//            P2PHandler.getInstance().getFriendStatus(Deviceid, P2PConstants.P2P_Server.SERVER_INDEX);
//            ToastUtil.show(getActivity(), "状态获取失败~  重试中");
//        } else {
//            for (int i = 0; i < msg.status.length; i++) {
////                camear.getData().get(i).setSb_status(msg.status[i]);
//                Log.d("...", "vRetGetIndexFriendStatus: status" + msg.status[i]);
//            }
//            setCameraList(camear);
//        }
//    }
//
//    public void setCameraList(AppCameraList list) {
//        cameraListAdapter.setNewData(list.getData());
//    }
//
//    @Override
//    public void onDestroy() {
//        EventBus.getDefault().unregister(this);
//        super.onDestroy();
//    }
//
//    //item长按事件
//    private OnItemChildLongClickListener LongClickListener = new OnItemChildLongClickListener() {
//        @Override
//        public void onSimpleItemChildLongClick(BaseQuickAdapter adapter, View view, final int position) {
//            if (Contains.appLogin.getAdminXiangmuId() == -1) {
//                showDeletCameraDalog(position);
//            }
////            positions = position;
////            if (cameraController == null) {
////                cameraController = new CameraControllerImpl();
////            }
////            Map<String, String> map = new HashMap<String, String>();
////            map.put("sb_ipc_id", list.get(position).getSb_ipc_id());//设备ID
////            map.put("apptoken", Contains.uuid);//用户TOKEN
////            cameraController.getCameraDel(mRequestQueue, map, dellistener);
//        }
//    };
//
//    private void showDeletCameraDalog(final int position) {
//        ConfirmDialog.showDialog(getActivity(), "提示", "删除摄像头", new ConfirmDialog.OnConfirmListener() {
//            @Override
//            public void onCancel() {
//
//            }
//
//            @Override
//            public void onConfirm() {
//                Map<String, String> map = new HashMap<String, String>();
//                map.put("equipId", cameraListAdapter.getData().get(position).getEquipId() + "");//设备ID
//                map.put("uuId", Contains.uuid);//用户TOKEN
//                deletCamera(map, position);
//            }
//        });
//    }
//
//    public void deletCamera(Map map, final int postion) {
////        progressDialog.show();
//        Disposable disposable = HttpAPIWrapper.getInstance(HttpAPIWrapper.getOkHttpClient()).deletCamera(map)
//                .subscribe(new Consumer<BaseBack>() {
//                    @Override
//                    public void accept(@NonNull BaseBack removeDevice) throws Exception {
////                        progressDialog.hide();
//                        Toast.makeText(getActivity(), removeDevice.getMsg(), Toast.LENGTH_SHORT).show();
//                        cameraListAdapter.getData().remove(postion);
//                        if (removeDevice.getStatus() == 0) {
//                            getAllCamera();
//                            refreshLayout.setRefreshing(true);
//                        }
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(@NonNull Throwable throwable) throws Exception {
//                    }
//                });
//        disposables.add(disposable);
//    }
//
//    //item点击事件
//    private OnItemChildClickListener ClickListener = new OnItemChildClickListener() {
//        @Override
//        public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//            CameraListAdapter listAdapter = (CameraListAdapter) adapter;
//            switch (view.getId()) {
//                case R.id.camera_image:
//                    Intent device = new Intent(getActivity(), CameraActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("deviceId", listAdapter.getData().get(position).getEquipSerial() + "");
//                    bundle.putString("devicePwd", listAdapter.getData().get(position).getEquipPassword());
//                    bundle.putString("deviceName", listAdapter.getData().get(position).getEquipName());
//                    bundle.putString("deviceId2", listAdapter.getData().get(position).getEquipId() + "");
//                    bundle.putString("xiangmuId", listAdapter.getData().get(position).getEquipXiangmuId() + "");
//                    bundle.putString("deviceXm", listAdapter.getData().get(position).getEquipXiangmuName());
////                    bundle.putString("deviceId", listAdapter.getData().get(position).getSb_ipc_id());
////                    bundle.putString("devicePwd", listAdapter.getData().get(position).getSb_ipc_pwd());
////                    bundle.putString("deviceName", listAdapter.getData().get(position).getSb_name());
//                    device.putExtras(bundle);
//                    startActivity(device);
//                    break;
//                case R.id.camera_video:
////                    Intent video = new Intent(DeviceActivity.this, RecordFilesActivity.class);
////                    Bundle bundle1 = new Bundle();
////                    bundle1.putString("deviceId", adapter.getData().get(position).getSb_ipc_id());
////                    bundle1.putString("devicePwd", adapter.getData().get(position).getSb_ipc_pwd());
////                    video.putExtras(bundle1);
////                    startActivity(video);
//                    Intent video = new Intent(getActivity(), RecordFilesActivity.class);
//                    Bundle bundle1 = new Bundle();
//                    bundle1.putString("deviceId", listAdapter.getData().get(position).getEquipSerial() + "");
//                    bundle1.putString("devicePwd", listAdapter.getData().get(position).getEquipPassword());
//                    video.putExtras(bundle1);
//                    startActivity(video);
//                    break;
//            }
//
//        }
//    };
//
//    private void searchByXm(String projectName) {
//        if (camear.getData() == null) {
//            Toast.makeText(getActivity(), "没有可以搜索的设备", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if (projectName.equals("")) {
//            cameraListAdapter.setNewData(camear.getData());
//            return;
//        }
//        List<AppCameraList.DataBean> list = new ArrayList<>();
//        for (int i = 0; i < camear.getData().size(); i++) {
//            if (camear.getData().get(i).getEquipXiangmuName().contains(projectName)) {
//                list.add(camear.getData().get(i));
//            }
//        }
//        cameraListAdapter.setNewData(list);
//    }
//    @OnClick(R.id.search)
//    public void onViewClicked() {
//        ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS); //(WidgetSearchActivity是当前的Activity)
////        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
////        imm.toggleSoftInput(0, InputMethodManager.RESULT_HIDDEN);
//        if (etName.getText().toString().equals("")) {
//            searchByXm("");
//        } else {
//            searchByXm(etName.getText().toString());
//        }
//    }
//
//    public interface OptionOprate {
//        void getCamera(AppCameraList camera);
//    }
//
//    public void setOption(OptionOprate option) {
//        mOptionOprate = option;
//    }
}
