package com.yxld.xzs.activity.camera;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.socks.library.KLog;
import com.yxld.xzs.R;
import com.yxld.xzs.base.BaseActivity;
import com.yxld.xzs.contain.Contains;
import com.yxld.xzs.entity.Host;
import com.yxld.xzs.entity.XiangMu;
import com.yxld.xzs.http.api.HttpAPIWrapper;
import com.yxld.xzs.utils.StringUitl;
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
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * 作者：Android on 2017/9/8
 * 邮箱：365941593@qq.com
 * 描述：
 */

public class SearchHostActivity extends BaseActivity {
    @BindView(R.id.tv_xiangmu)
    TextView tvXiangmu;
    @BindView(R.id.tv_loudong)
    TextView tvLoudong;
    @BindView(R.id.tv_danyaun)
    TextView tvDanyaun;
    @BindView(R.id.tv_fanghao)
    TextView tvFanghao;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;
    @BindView(R.id.et_mac)
    EditText etMac;
    @BindView(R.id.tv_guanliyuan)
    EditText tvGuanliyuan;

    private WheelView wheelView;
    private NumericWheelAdapter xiangmuAdapter;
    private ArrayList<String> xiangmuList;
    private XiangMu mXiangMu;
    private int xiangmuId;
    private String fwLoudong = "";
    private String fwDanyuan = "";
    private String fwFanghao = "";
    private String fwGuanliyuan = "";
    private String fwMac = "";
    private ArrayList<String> louPanList;
    private ArrayList<String> danYuanList;
    private ArrayList<String> fangHaoList;
    private ArrayList<String> fangwuBianhao;
    private ArrayList<String> fangwuhao;
    private Host.DataBean dataBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        setContentView(R.layout.activity_search_host);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
                check();
                break;
        }
    }

    //搜索主机
    private void searchHost() {
        if (xiangmuId == 0) {
            Toast.makeText(this, "请先选择项目！", Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("paianZhuji.zhujiMac", etMac.getText().toString().trim());
        map.put("paianZhuji.zhujiHaoma", tvGuanliyuan.getText().toString().trim());
        map.put("paianZhuji.zhujiXiangmuId", xiangmuId + "");
        map.put("paianZhuji.zhujiLoudong", fwLoudong);
        map.put("paianZhuji.zhujiDanyuan", fwDanyuan);
        map.put("paianZhuji.zhujiFanghao", fwFanghao);
        map.put("uuid", Contains.uuid);
        Disposable disposable = HttpAPIWrapper.getInstance(HttpAPIWrapper.getOkHttpClient()).searchZhuji(map)
                .subscribe(new Consumer<Host>() {
                    @Override
                    public void accept(@NonNull final Host host) throws Exception {
                        Intent intent = new Intent(SearchHostActivity.this, AlarmListActivity.class);
                        intent.putExtra("flag", 0);
                        intent.putParcelableArrayListExtra("data", (ArrayList<? extends Parcelable>) host.getData());
                        startActivity(intent);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                    }
                });
        disposables.add(disposable);
    }

    //检查条件
    private void check() {
        fwGuanliyuan = tvGuanliyuan.getText().toString();
        fwMac = etMac.getText().toString();
        //项目必选  其他三个条件必须一个
        if (xiangmuId == 0) {
            Toast.makeText(this, "请先选择项目！", Toast.LENGTH_SHORT).show();
            return;
        }
        if (StringUitl.isEmpty(fwMac)
                && StringUitl.isEmpty(fwGuanliyuan)
                && StringUitl.isEmpty(fwLoudong)
                && StringUitl.isEmpty(fwDanyuan)
                && StringUitl.isEmpty(fwFanghao)) {
            Toast.makeText(this, "信息不能为空！", Toast.LENGTH_SHORT).show();
            return;

        }
        if (!StringUitl.isEmpty(fwLoudong)) {
            if (StringUitl.isEmpty(fwDanyuan)) {
                Toast.makeText(this, "请选择单元！", Toast.LENGTH_SHORT).show();
                return;
            } else if (StringUitl.isEmpty(fwFanghao)) {
                Toast.makeText(this, "请选择房号！", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        Intent intent = new Intent(SearchHostActivity.this, AlarmListActivity.class);
        intent.putExtra("xiangmuId", xiangmuId + "");
        intent.putExtra("fwLoudong", fwLoudong);
        intent.putExtra("fwDanyuan", fwDanyuan);
        intent.putExtra("fwFanghao", fwFanghao);
        intent.putExtra("fwGuanliyuan", fwGuanliyuan);
        intent.putExtra("fwMac", fwMac);
        startActivity(intent);

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
                if (mXiangMu.getData() == null) return;
                tvXiangmu.setText(mXiangMu.getData().get(position).getXiangmuName().trim());
                if (xiangmuId != mXiangMu.getData().get(position).getXiangmuId()) {
                    fwLoudong = "";
                    fwDanyuan = "";
                    fwFanghao = "";
                    tvLoudong.setText("");
                    tvDanyaun.setText("");
                    tvFanghao.setText("");
                    fwGuanliyuan = "";
                    tvGuanliyuan.setText("");
                }
                xiangmuId = mXiangMu.getData().get(position).getXiangmuId();

                break;
            case 2:
                if (louPanList == null) return;
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
                if (danYuanList == null) return;
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
                if (fangwuhao == null) return;
                tvFanghao.setText(fangwuhao.get(position).trim());
                if (!tvFanghao.getText().toString().equals(fwFanghao)) {
                    fwGuanliyuan = "";
                    tvGuanliyuan.setText("");
                }
                fwFanghao = fangwuhao.get(position).trim();
                break;
        }
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
                        xiangmuAdapter = new NumericWheelAdapter(SearchHostActivity.this, 0, louPanList.size() - 1, "", louPanList);
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
                        xiangmuAdapter = new NumericWheelAdapter(SearchHostActivity.this, 0, danYuanList.size() - 1, "", danYuanList);
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
                        xiangmuAdapter = new NumericWheelAdapter(SearchHostActivity.this, 0, fangwuhao.size() - 1, "", fangwuhao);
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
                            xiangmuAdapter = new NumericWheelAdapter(SearchHostActivity.this, 0, xiangmuList.size() - 1, "", xiangmuList);
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
}
