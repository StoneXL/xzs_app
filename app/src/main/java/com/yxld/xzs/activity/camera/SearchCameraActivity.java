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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.p2p.core.P2PHandler;
import com.p2p.core.P2PSpecial.HttpErrorCode;
import com.socks.library.KLog;
import com.yxld.xzs.R;
import com.yxld.xzs.base.BaseActivity;
import com.yxld.xzs.base.DemoApplicationLike;
import com.yxld.xzs.contain.Contains;
import com.yxld.xzs.entity.APPCamera;
import com.yxld.xzs.entity.XiangMu;
import com.yxld.xzs.http.api.HttpAPIWrapper;
import com.yxld.xzs.http.api.support.ErrorHandlerInterceptor;
import com.yxld.xzs.utils.StringUitl;
import com.yxld.xzs.utils.ToastUtil;
import com.yxld.xzs.utils.UIUtils;
import com.yxld.xzs.view.CameraDialog;
import com.yxld.xzs.view.CustomPopWindow;
import com.yxld.xzs.view.datepicker.NumericWheelAdapter;
import com.yxld.xzs.view.datepicker.WheelView;
import com.yxld.xzs.yoosee.P2PListener;
import com.yxld.xzs.yoosee.SettingListener;
import com.zhy.autolayout.AutoLinearLayout;

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
 * @author xlei
 * @Date 2018/1/9.
 */

public class SearchCameraActivity extends BaseActivity {
    @BindView(R.id.tv_xiangmu)
    TextView tvXiangmu;
    @BindView(R.id.tv_loudong)
    TextView tvLoudong;
    @BindView(R.id.tv_danyaun)
    TextView tvDanyaun;
    @BindView(R.id.tv_fanghao)
    TextView tvFanghao;
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.et_xuliehao)
    EditText mEtXuliehao;
    /**
     * 滚轮 公用一个adpater
     */
    private WheelView wheelView;
    private NumericWheelAdapter xiangmuAdapter;
    /**
     * 项目数据
     */
    private ArrayList<String> xiangmuList;
    private XiangMu mXiangMu;
    private int xiangmuId;
    /**
     * 楼栋数据
     */
    private ArrayList<String> louPanList;
    private String fwLoudong = "";
    /**
     * 单元数据
     */
    private ArrayList<String> danYuanList;
    private String fwDanyuan = "";
    /**
     * 房屋号 和编号数据
     */
    private ArrayList<String> fangwuBianhao;
    private ArrayList<String> fangwuhao;
    private String fwFanghao = "";

    /**
     * 业主手机
     */
    private String mPhone;
    /**
     * 设备序列号
     */
    private String mXuLieHao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        setContentView(R.layout.activity_search_camera);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        P2PHandler.getInstance().p2pDisconnect();
        Login();

    }

    @OnClick({R.id.tv_xiangmu, R.id.tv_loudong, R.id.tv_danyaun, R.id.tv_fanghao, R.id.tv_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_xiangmu:
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
            case R.id.tv_confirm:
                startToDeDetail();
                break;
            default:
                break;
        }
    }

    private void startToDeDetail() {
        mPhone = mEtPhone.getText().toString().trim();
        mXuLieHao = mEtXuliehao.getText().toString().trim();
        //项目必选  其他三个条件必须一个
        if (xiangmuId == 0 && StringUitl.isEmpty(mPhone) && StringUitl.isEmpty(mXuLieHao)) {
            Toast.makeText(this, "信息不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (xiangmuId != 0
                && StringUitl.isEmpty(mXuLieHao)
                && StringUitl.isEmpty(fwLoudong)
                && StringUitl.isEmpty(mPhone)
                ) {
            Toast.makeText(this, "请选择楼栋！", Toast.LENGTH_SHORT).show();
            return;

        }
        if (xiangmuId != 0 && StringUitl.isEmpty(mXuLieHao) && !StringUitl.isEmpty(fwLoudong)
                && StringUitl.isEmpty(mPhone) && !StringUitl.isEmpty(fwLoudong)) {
            if (StringUitl.isEmpty(fwDanyuan)) {
                Toast.makeText(this, "请选择单元！", Toast.LENGTH_SHORT).show();
                return;
            } else if (StringUitl.isEmpty(fwFanghao)) {
                Toast.makeText(this, "请选择房号！", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        KLog.i("SearchCameraActivity项目" + xiangmuId + "楼栋" + fwLoudong + "单元" + fwDanyuan + "房号" + fwFanghao + "业主手机" + mPhone + "序列号" + mXuLieHao);
        Intent intent = new Intent(this, CameraDetailActivity.class);
        intent.putExtra("xiangmuId", xiangmuId == 0 ? "" : xiangmuId + "");
        intent.putExtra("fwLoudong", fwLoudong);
        intent.putExtra("fwDanyuan", fwDanyuan);
        intent.putExtra("fwFanghao", fwFanghao);
        intent.putExtra("mPhone", mPhone);
        intent.putExtra("mXuLieHao", mXuLieHao);
        startActivity(intent);
    }


    /**
     * 显示不同的wheelview
     *
     * @param showView view
     * @param flag     类型
     */
    private void showWheelView(View showView, final int flag) {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
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
                if (xiangmuId == 0) {
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
                    tvLoudong.setText("");
                    tvDanyaun.setText("");
                    tvFanghao.setText("");
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

                }
                fwLoudong = louPanList.get(position).trim();
                KLog.i(fwLoudong);
                break;
            case 3:
                if (danYuanList == null) {
                    return;
                }
                tvDanyaun.setText(danYuanList.get(position).trim());
                if (!tvDanyaun.getText().equals(fwDanyuan)) {
                    fwFanghao = "";
                    tvFanghao.setText("");

                }
                fwDanyuan = danYuanList.get(position).trim();
                break;
            case 4:
                if (fangwuhao == null) {
                    return;
                }
                tvFanghao.setText(fangwuhao.get(position).trim());
                if (!tvFanghao.getText().toString().equals(fwFanghao)) {

                }
                fwFanghao = fangwuhao.get(position).trim();
                break;
            default:
                break;
        }
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
                        xiangmuAdapter = new NumericWheelAdapter(SearchCameraActivity.this, 0, fangwuhao.size() - 1, "", fangwuhao);
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

    /**
     * 获取楼栋下所有单元数据
     */
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
                        xiangmuAdapter = new NumericWheelAdapter(SearchCameraActivity.this, 0, danYuanList.size() - 1, "", danYuanList);
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

    /**
     * 获取项目下所有楼栋数据
     */
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
                        xiangmuAdapter = new NumericWheelAdapter(SearchCameraActivity.this, 0, louPanList.size() - 1, "", louPanList);
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

    /**
     * 获取wheelview所有 项目数据
     */
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
                            xiangmuAdapter = new NumericWheelAdapter(SearchCameraActivity.this, 0, xiangmuList.size() - 1, "", xiangmuList);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_camera_device, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                CameraDialog.showDialog(this, new CameraDialog.OnAutoListener() {
                    @Override
                    public void onAutoOne() {
                        Intent config = new Intent(SearchCameraActivity.this, CameraConfigActivity.class);
                        config.putExtra("enter_type", 2);
                        startActivity(config);
                    }

                    @Override
                    public void onAutoTwo() {
                        Intent add = new Intent(SearchCameraActivity.this, CameraAddNewActivity.class);
                        add.putExtra("ishasContactId", false);
                        startActivity(add);
                    }

                    @Override
                    public void onAutoThree() {
                        Intent add = new Intent(SearchCameraActivity.this, WiredDeviceListActivity.class);
                        add.putExtra("isDeviceActivity", false);
                        startActivity(add);
                    }
                });
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void Login() {
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

            }

            @Override
            public void onResponse(Call call, Response response) {
                String result = null;
                try {
                    result = response.body().string();
                    KLog.i("登录成功" + result);
                    Gson gson = new Gson();
                    APPCamera camera = gson.fromJson(result, APPCamera.class);
                    switch (camera.getError_code()) {
                        case HttpErrorCode.ERROR_0:
                            int code1 = Integer.parseInt(camera.getP2PVerifyCode1());
                            int code2 = Integer.parseInt(camera.getP2PVerifyCode2());
                            boolean connect = P2PHandler.getInstance().p2pConnect(camera.getUserID(), code1, code2);
                            KLog.i("connect===========" + camera.getUserID() + "+" + code1 + "+" + code2);
                            if (connect) {
                                //c初始化怕p2p
                                P2PHandler.getInstance().p2pInit(DemoApplicationLike.getInstance(), new P2PListener(), new SettingListener());
                                KLog.i(camera.getUserID() + "-----------------");

                            } else {
                                ToastUtil.show(SearchCameraActivity.this, "连接失败,请重新进入居家安防");
                            }
                            break;
                        case HttpErrorCode.ERROR_998:
                            break;
                        case HttpErrorCode.ERROR_10902011:
                            ToastUtil.show(SearchCameraActivity.this, "用户不存在");
                            break;
                        default:
                            String msg = String.format("摄像头登录失败测试版(%s)", camera.getError_code());
                            ToastUtil.show(SearchCameraActivity.this, msg);
                            break;
                    }
                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        });
    }
}
