package com.yxld.xzs.activity.camera;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.socks.library.KLog;
import com.yxld.xzs.R;
import com.yxld.xzs.adapter.HostAdapter;
import com.yxld.xzs.base.BaseActivity;
import com.yxld.xzs.contain.Contains;
import com.yxld.xzs.entity.BaseBack;
import com.yxld.xzs.entity.CxwyYezhu;
import com.yxld.xzs.entity.Host;
import com.yxld.xzs.entity.XiangMu;
import com.yxld.xzs.http.api.HttpAPIWrapper;
import com.yxld.xzs.utils.UIUtils;
import com.yxld.xzs.view.CustomPopWindow;
import com.yxld.xzs.view.datepicker.NumericWheelAdapter;
import com.yxld.xzs.view.datepicker.WheelView;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


/**
 * 作者：Android on 2017/9/8
 * 邮箱：365941593@qq.com
 * 描述：
 */

public class AlarmListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshlayout)
    SwipeRefreshLayout refreshlayout;

    private String xiangmuId;
    private String fwLoudong = "";
    private String fwDanyuan = "";
    private String fwFanghao = "";
    private String fwGuanliyuan = "";
    private String fwMac = "";

    ArrayList<Host.DataBean> data;
    HostAdapter hostAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        setContentView(R.layout.activity_alarm_list);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        refreshlayout.setOnRefreshListener(this);
        //从搜素界面过来的搜索条件
        searchTiaojiao();
        KLog.e(xiangmuId + "-1" + fwLoudong + "-" + fwDanyuan + "-" + fwFanghao + "-" + fwMac);
        searchHost();
    }

    private void searchTiaojiao() {
        xiangmuId = getIntent().getStringExtra("xiangmuId");
        fwLoudong = getIntent().getStringExtra("fwLoudong");
        fwDanyuan = getIntent().getStringExtra("fwDanyuan");
        fwFanghao = getIntent().getStringExtra("fwFanghao");
        fwGuanliyuan = getIntent().getStringExtra("fwGuanliyuan");
        fwMac = getIntent().getStringExtra("fwMac");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        KLog.i("activity返回");
        if (requestCode == 0 && resultCode == 2) {
            //getHostList();
            xiangmuId = data.getStringExtra("xiangmuId");
            fwLoudong = data.getStringExtra("fwLoudong");
            fwDanyuan = data.getStringExtra("fwDanyuan");
            fwFanghao = data.getStringExtra("fwFanghao");
            if (data.getStringExtra("fwGuanliyuan").contains(" ")) {
                fwGuanliyuan = data.getStringExtra("fwGuanliyuan").split(" ")[1];
            } else {
                fwGuanliyuan = data.getStringExtra("fwGuanliyuan");
            }
            fwMac = getIntent().getStringExtra("fwMac");
            searchHost();
        }
    }

    @Override
    public void onRefresh() {
        searchHost();
    }

    //搜索主机
    private void searchHost() {
        Map<String, String> map = new HashMap<>();
        map.put("paianZhuji.zhujiMac", fwMac);
        map.put("paianZhuji.zhujiHaoma", fwGuanliyuan);
        map.put("paianZhuji.zhujiXiangmuId", xiangmuId);
        map.put("paianZhuji.zhujiLoudong", fwLoudong);
        map.put("paianZhuji.zhujiDanyuan", fwDanyuan);
        map.put("paianZhuji.zhujiFanghao", fwFanghao);
        map.put("uuid", Contains.uuid);
        Disposable disposable = HttpAPIWrapper.getInstance(HttpAPIWrapper.getOkHttpClient()).searchZhuji(map)
                .subscribe(new Consumer<Host>() {
                    @Override
                    public void accept(@NonNull final Host host) throws Exception {
                        if (host.status == 0) {
                            refreshlayout.setRefreshing(false);
                            Log.e("TAG", host.getData().toString());
                            hostAdapter = new HostAdapter(host.getData());
                            recyclerView.setAdapter(hostAdapter);
                            hostAdapter.setOnItemChildClickListener(new BaseQuickAdapter
                                    .OnItemChildClickListener() {
                                @Override
                                public void onItemChildClick(BaseQuickAdapter baseQuickAdapter,
                                                             View view, int i) {
                                    switch (view.getId()) {
                                        case R.id.xiugai:
//                                        dataBean=hostAdapter.getData().get(i);
//                                        showXiugaiDilog();
                                            Intent intent = new Intent(AlarmListActivity.this,
                                                    AddHostActivity.class);
                                            intent.putExtra("data", hostAdapter.getData().get(i));
                                            intent.putExtra("flag", "xiugai");
                                            startActivityForResult(intent, 0);
                                            // this.overridePendingTransition(R.anim
                                            // .activity_translate_in, R.anim
                                            // .activity_translate_out);
                                            break;
                                        case R.id.fangqu_liebiao:
                                            Intent intent1 = new Intent(AlarmListActivity.this,
                                                    FangQuActivity.class);
                                            intent1.putExtra("data", hostAdapter.getData().get(i));
                                            startActivity(intent1);
                                            break;
                                    }
                                }
                            });
                            //长按删除
                            hostAdapter.setOnItemLongClickListener(new BaseQuickAdapter
                                    .OnItemLongClickListener() {
                                @Override
                                public boolean onItemLongClick(BaseQuickAdapter baseQuickAdapter,
                                                               View view, int i) {
                                    showDeleteDialog(i);
                                    return false;
                                }
                            });
                        } else {
                            onError(host.status, host.msg);
                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        refreshlayout.setRefreshing(false);
                    }
                });
        disposables.add(disposable);
    }

    private void showDeleteDialog(final int i) {
        new AlertDialog.Builder(this).setTitle("删除该主机？")
                .setMessage("是否删除该主机")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deletDevice(i);
                    }
                }).show();
    }

    private void deletDevice(int i) {
        Map<String, String> map = new HashMap<>();
        map.put("uuid", Contains.uuid);
        map.put("paianZhuji.zhujiMac", hostAdapter.getItem(i).getZhujiMac());
        Disposable disposable = HttpAPIWrapper.getInstance(HttpAPIWrapper.getOkHttpClient()).deleteZhuji(map)
                .subscribe(new Consumer<BaseBack>() {
                    @Override
                    public void accept(@NonNull BaseBack fangQu) throws Exception {
                        Toast.makeText(AlarmListActivity.this, fangQu.getMSG(), Toast.LENGTH_SHORT).show();
                        searchHost();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                    }
                });
        disposables.add(disposable);
    }

    TextView tvXiangmu;
    EditText etZhujiming;
    TextView tvLoudong;
    TextView tvDanyaun;
    TextView tvFanghao;
    EditText etBeizhu;
    TextView tvConfirm;
    TextView tvCancel;
    EditText etMac;
    TextView tvGuanliyuan;
    private XiangMu mXiangMu;
    private WheelView wheelView;
    private NumericWheelAdapter xiangmuAdapter;
    private ArrayList<String> xiangmuList;
    private ArrayList<String> louPanList;
    private ArrayList<String> danYuanList;
    private ArrayList<String> fangHaoList;
    private ArrayList<String> fangwuBianhao;
    private ArrayList<String> fangwuhao;
    private ArrayList<String> guanliyuan;
    private Host.DataBean dataBean;
    AlertDialog dialog;

    private void showXiugaiDilog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        View contentView1 = LayoutInflater.from(this).inflate(
                R.layout.dialog_xiugai_host, null);
        builder.setView(contentView1);
        dialog = builder.show();
        tvXiangmu = (TextView) contentView1.findViewById(R.id.tv_xiangmu);
        tvGuanliyuan = (TextView) contentView1.findViewById(R.id.tv_guanliyuan);
        etMac = (EditText) contentView1.findViewById(R.id.et_mac);
        etBeizhu = (EditText) contentView1.findViewById(R.id.et_beizhu);
        etZhujiming = (EditText) contentView1.findViewById(R.id.et_zhujiming);
        tvLoudong = (TextView) contentView1.findViewById(R.id.tv_loudong);
        tvDanyaun = (TextView) contentView1.findViewById(R.id.tv_danyaun);
        tvFanghao = (TextView) contentView1.findViewById(R.id.tv_fanghao);
        tvConfirm = (TextView) contentView1.findViewById(R.id.tv_confirm);
        tvCancel = (TextView) contentView1.findViewById(R.id.tv_cancel);
        etMac.setText(dataBean.getZhujiMac());
        etBeizhu.setText(dataBean.getZhujiBeizhu());
        etZhujiming.setText(dataBean.getZhujiShebeiName());
        tvXiangmu.setText(dataBean.getZhujiXiangmuName());
        tvDanyaun.setText(dataBean.getZhujiDanyuan());
        tvLoudong.setText(dataBean.getZhujiLoudong());
        tvFanghao.setText(dataBean.getZhujiFanghao());
        tvGuanliyuan.setText(dataBean.getZhujiHaoma());
        tvCancel.setOnClickListener(this);
        tvXiangmu.setOnClickListener(this);
        tvLoudong.setOnClickListener(this);
        tvFanghao.setOnClickListener(this);
        tvDanyaun.setOnClickListener(this);
        tvConfirm.setOnClickListener(this);
        tvGuanliyuan.setOnClickListener(this);
    }


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
                if (xiangmuId == null || "".equals(xiangmuId)) {
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
                if (xiangmuId == null || "".equals(xiangmuId)) {
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
                tvXiangmu.setText(mXiangMu.getData().get(position).getXiangmuName().trim());
                if (xiangmuId.equals(mXiangMu.getData().get(position).getXiangmuId() + "")) {
                    fwLoudong = "";
                    fwDanyuan = "";
                    fwFanghao = "";
                    tvLoudong.setText("");
                    tvDanyaun.setText("");
                    tvFanghao.setText("");
                    fwGuanliyuan = "";
                    tvGuanliyuan.setText("");
                }
                xiangmuId = mXiangMu.getData().get(position).getXiangmuId() + "";

                break;
            case 2:
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
                tvFanghao.setText(fangwuhao.get(position).trim());
                if (!tvFanghao.getText().toString().equals(fwFanghao)) {
                    fwGuanliyuan = "";
                    tvGuanliyuan.setText("");
                }
                fwFanghao = fangwuhao.get(position).trim();
                break;
            case 5:
                if (guanliyuan == null) return;
                tvGuanliyuan.setText(guanliyuan.get(position).trim());
                fwGuanliyuan = guanliyuan.get(position).trim();
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
                            guanliyuan.add(i, content.getData().get(i).getYezhuName() + " " + content.getData().get(i).getYezhuShouji());
                        }
                        xiangmuAdapter = new NumericWheelAdapter(AlarmListActivity.this, 0, guanliyuan.size() - 1, "", guanliyuan);
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
                        KLog.i(content);             //[[321,"1"],[321,"2"],[321,"5"]]
                        String after = content.replace("[", "").replace("]", "").replace(xiangmuId + ",", "").replace("\"", "");
                        String[] stringArr = after.split(",");
                        louPanList = new ArrayList<String>(Arrays.asList(stringArr));
//                        louPanList = Arrays.asList(stringArr);                   //错误
                        xiangmuAdapter = new NumericWheelAdapter(AlarmListActivity.this, 0, louPanList.size() - 1, "", louPanList);
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
                        xiangmuAdapter = new NumericWheelAdapter(AlarmListActivity.this, 0, danYuanList.size() - 1, "", danYuanList);
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
                            xiangmuAdapter = new NumericWheelAdapter(AlarmListActivity.this, 0, xiangmuList.size() - 1, "", xiangmuList);
                            xiangmuAdapter.setTextSize(15);
                            wheelView.setViewAdapter(xiangmuAdapter);
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
                        xiangmuAdapter = new NumericWheelAdapter(AlarmListActivity.this, 0, fangwuhao.size() - 1, "", fangwuhao);
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

    private void xiugaiZhuji() {
        progressDialog.show();
        Map<String, String> map = new HashMap<>();
        map.put("paianZhuji.zhujiMac", etMac.getText().toString().trim());
        map.put("paianZhuji.zhujiShebeiName", etZhujiming.getText().toString().trim());
        map.put("paianZhuji.zhujiXiangmuId", xiangmuId + "");
        map.put("paianZhuji.zhujiLoudong", fwLoudong);
        map.put("paianZhuji.zhujiDanyuan", fwDanyuan);
        map.put("paianZhuji.zhujiFanghao", fwFanghao);
        map.put("paianZhuji.zhujiBeizhu", etBeizhu.getText().toString().trim());
        map.put("paianZhuji.zhujiId", dataBean.getZhujiId() + "");
        map.put("paianZhuji.zhujiHaoma", tvGuanliyuan.getText().toString());
        map.put("uuid", Contains.uuid);
        KLog.i(map);
        Disposable disposable = HttpAPIWrapper.getInstance(HttpAPIWrapper.getOkHttpClient()).xiugaiShebei(map)
                .subscribe(new Consumer<BaseBack>() {
                    @Override
                    public void accept(@NonNull BaseBack baseBack) throws Exception {
                        progressDialog.hide();
                        dialog.dismiss();
                        Toast.makeText(AlarmListActivity.this, baseBack.getMSG(), Toast.LENGTH_SHORT).show();
                        if (baseBack.getStatus() == 0) {
                            //修改成功后 重新搜索  搜索条件需要重新赋值（。。。），只有Mac地址未重新赋值
                            fwMac = etMac.getText().toString();
                            KLog.e(xiangmuId + "-2" + fwLoudong + "-" + fwDanyuan + "-" + fwFanghao + "-" + fwMac);
                            searchHost();
//                            Intent intent=new Intent();
//                            // intent.putParcelableArrayListExtra(etMac.getText().toString().trim())
//                            intent.putExtra("xiangmuId", xiangmuId+"");
//                            intent.putExtra("fwLoudong", fwLoudong);
//                            intent.putExtra("fwDanyuan", fwDanyuan);
//                            intent.putExtra("fwFanghao", fwFanghao);
//                            intent.putExtra("fwGuanliyuan", tvGuanliyuan.getText().toString());
//                            intent.putExtra("fwMac", etMac.getText().toString().trim());
//                            setResult(2 ,intent);
//                            finish();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        progressDialog.hide();
                        searchTiaojiao();
                    }
                });
        disposables.add(disposable);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
            case R.id.tv_guanliyuan:
                showWheelView(tvGuanliyuan, 5);
                break;
            case R.id.tv_confirm:
                xiugaiZhuji();
                break;
            case R.id.tv_cancel:
                //修改失败或取消按原来的值进行搜索
                dialog.dismiss();
                KLog.e(xiangmuId + "-3" + fwLoudong + "-" + fwDanyuan + "-" + fwFanghao + "-" + fwMac);
                searchTiaojiao();
                KLog.e(xiangmuId + "-4" + fwLoudong + "-" + fwDanyuan + "-" + fwFanghao + "-" + fwMac);
                break;
        }
    }

}
