package com.yxld.xzs.activity.camera;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.socks.library.KLog;
import com.yxld.xzs.R;
import com.yxld.xzs.base.BaseFragment;
import com.yxld.xzs.contain.Contains;
import com.yxld.xzs.entity.BaseBack;
import com.yxld.xzs.entity.CxwyYezhu;
import com.yxld.xzs.entity.Host;
import com.yxld.xzs.entity.XiangMu;
import com.yxld.xzs.http.api.HttpAPIWrapper;
import com.yxld.xzs.utils.UIUtils;
import com.yxld.xzs.view.CustomPopWindow;
import com.yxld.xzs.view.ProgressDialog;
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

public class AddHostFragment extends BaseFragment {

    Unbinder unbinder;
    @BindView(R.id.tv_xiangmu)
    TextView tvXiangmu;
    @BindView(R.id.et_zhujiming)
    EditText etZhujiming;
    @BindView(R.id.et_mac)
    EditText etMac;
    @BindView(R.id.tv_loudong)
    TextView tvLoudong;
    @BindView(R.id.tv_danyaun)
    TextView tvDanyaun;
    @BindView(R.id.tv_fanghao)
    TextView tvFanghao;
    @BindView(R.id.et_beizhu)
    EditText etBeizhu;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;
    @BindView(R.id.tv_guanliyuan)
    TextView tvGuanliyuan;
    @BindView(R.id.ck_wifi)
    CheckBox CkWifi;
    @BindView(R.id.ck_liuliang)
    CheckBox CkLiuliang;
    @BindView(R.id.ck_gouji)
    CheckBox CkGouji;
    @BindView(R.id.ck_zulin)
    CheckBox CkZulin;
    @BindView(R.id.et_liuliangka)
    EditText etLiuliangka;
    private WheelView wheelView;
    public ProgressDialog progressDialog;
    protected CompositeDisposable disposables = new CompositeDisposable();

    private NumericWheelAdapter xiangmuAdapter;
    private ArrayList<String> xiangmuList;
    private XiangMu mXiangMu;
    private int xiangmuId;
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
    private Host.DataBean dataBean;

    private int zhujiLeiXing = 1;//-1表示未选择 2表示wifi版本  1表示流量版本 默认流量版本
    private int kaitongLeiXing = 2;//-1表示未选择 2表示购机开通  1表示租赁开通  默认购机开通

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_host, null);
        unbinder = ButterKnife.bind(this, view);
        progressDialog = new ProgressDialog(getActivity());
        return view;
    }

    @Override
    protected void lazyLoad() {

    }

    @OnClick({R.id.tv_xiangmu, R.id.tv_loudong, R.id.tv_danyaun, R.id.tv_fanghao, R.id.tv_confirm, R.id.tv_guanliyuan, R.id.tv_search, R.id.ck_gouji, R.id.ck_liuliang,
            R.id.ck_zulin, R.id.ck_wifi})
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
            case R.id.tv_guanliyuan:
                showWheelView(tvGuanliyuan, 5);
                break;
            case R.id.tv_confirm:
                addHost();
                break;
            case R.id.tv_search:
                startActivity(new Intent(getActivity(), SearchHostActivity.class));
                break;
            case R.id.ck_gouji:
                if (CkGouji.isChecked()) {
                    CkZulin.setChecked(false);
                    kaitongLeiXing = 2;
                } else {
                    kaitongLeiXing = -1;
                }
                break;
            case R.id.ck_zulin:
                if (CkZulin.isChecked()) {
                    CkGouji.setChecked(false);
                    kaitongLeiXing = 1;
                } else {
                    kaitongLeiXing = -1;
                }
                break;
            case R.id.ck_wifi:
                if (CkWifi.isChecked()) {
                    CkLiuliang.setChecked(false);
                    zhujiLeiXing = 2;
                } else {
                    zhujiLeiXing = -1;
                }
                break;
            case R.id.ck_liuliang:
                if (CkLiuliang.isChecked()) {
                    zhujiLeiXing = 1;
                    CkWifi.setChecked(false);
                } else {
                    zhujiLeiXing = -1;
                }
                break;
            default:
                break;
        }
    }

    private void addHost() {
        if (tvXiangmu.getText().toString().trim().equals("")) {
            Toast.makeText(getActivity(), "请先选择项目！", Toast.LENGTH_SHORT).show();
            return;
        } else if (etZhujiming.getText().toString().trim().equals("")) {
            Toast.makeText(getActivity(), "请输入主机名！", Toast.LENGTH_SHORT).show();
            return;
        } else if (zhujiLeiXing == -1) {
            Toast.makeText(getActivity(), "请选择主机类型！", Toast.LENGTH_SHORT).show();
            return;
        } else if (etMac.getText().toString().trim().equals("")) {
            Toast.makeText(getActivity(), "请输入mac地址！", Toast.LENGTH_SHORT).show();
            return;
        } else if (tvLoudong.getText().toString().trim().equals("")) {
            Toast.makeText(getActivity(), "请选择楼栋！", Toast.LENGTH_SHORT).show();
            return;
        } else if (tvDanyaun.getText().toString().trim().equals("")) {
            Toast.makeText(getActivity(), "请选择单元！", Toast.LENGTH_SHORT).show();
            return;
        } else if (tvFanghao.getText().toString().trim().equals("")) {
            Toast.makeText(getActivity(), "请选择房号！", Toast.LENGTH_SHORT).show();
            return;
        } else if (kaitongLeiXing == -1) {
            Toast.makeText(getActivity(), "请选择开通类型", Toast.LENGTH_SHORT).show();
            return;
        } else if (zhujiLeiXing==1&&etLiuliangka.getText().toString().trim().equals("")) {
            Toast.makeText(getActivity(), "请输入流量卡卡号", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.show();
        Map<String, String> map = new HashMap<>();
        map.put("paianZhuji.zhujiMac", etMac.getText().toString().trim());
        map.put("paianZhuji.zhujiShebeiName", etZhujiming.getText().toString().trim());
        map.put("paianZhuji.zhujiXiangmuId", xiangmuId + "");
        map.put("paianZhuji.zhujiLoudong", fwLoudong);
        map.put("paianZhuji.zhujiDanyuan", fwDanyuan);
        map.put("paianZhuji.zhujiFanghao", fwFanghao);
        map.put("paianZhuji.zhujiBeizhu", etBeizhu.getText().toString().trim());
        map.put("uuid", Contains.uuid);
        map.put("yezhu.yezhuPhone", fwGuanliyuan);
        map.put("paianZhuji.zhujiYonghuLeixin", kaitongLeiXing + "");
        map.put("paianZhuji.zhujiLeixin", zhujiLeiXing + "");
        map.put("paianZhuji.zhujiKahao", etLiuliangka.getText().toString());

        KLog.i(map);
        Disposable disposable = HttpAPIWrapper.getInstance(HttpAPIWrapper.getOkHttpClient()).tianjiaZhuji(map)
                .subscribe(new Consumer<BaseBack>() {
                    @Override
                    public void accept(@NonNull BaseBack baseBack) throws Exception {
                        progressDialog.hide();
                        if (baseBack.getStatus() == 0) {
                            Toast.makeText(getActivity(), "添加主机成功", Toast.LENGTH_SHORT).show();
                            //新增接口 之后的搜素字段fwGuanliyuan传空
                            fwGuanliyuan = "";
                            tvGuanliyuan.setText("");
                            //setResult(2);
                            //新增主机之后 调搜索主机接口 在显示
                            Intent intent = new Intent(getActivity(), AlarmListActivity.class);
                            intent.putExtra("xiangmuId", xiangmuId + "");
                            intent.putExtra("fwLoudong", fwLoudong);
                            intent.putExtra("fwDanyuan", fwDanyuan);
                            intent.putExtra("fwFanghao", fwFanghao);
                            intent.putExtra("fwGuanliyuan", fwGuanliyuan);
                            intent.putExtra("fwMac", etMac.getText().toString().trim());
                            // intent1.putExtra("data", hostAdapter.getData().get(i));
                            startActivity(intent);
                            //新增成功后清除选择记录
                            tvXiangmu.setText("");
                            fwLoudong = "";
                            fwDanyuan = "";
                            fwFanghao = "";
                            tvLoudong.setText("");
                            tvDanyaun.setText("");
                            tvFanghao.setText("");
                            etBeizhu.setText("");
                            etZhujiming.setText("");
                            etMac.setText("");
                            etLiuliangka.setText("");
                        } else {
                            Toast.makeText(getActivity(), baseBack.getMSG(), Toast.LENGTH_SHORT).show();
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

    private void showWheelView(View showView, final int flag) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(showView.getWindowToken(), 0);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.picker_xiangmu, null);
        AutoLinearLayout ll_content = (AutoLinearLayout) view.findViewById(R.id.ll_content);
        ll_content.setAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.pop_manage_product_in));
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
                    Toast.makeText(getActivity(), "请先选择项目！", Toast.LENGTH_SHORT).show();
                    return;
                }
                getLouPan();
                tv_title.setText("请选择楼栋");
                break;
            case 3:
                if (fwLoudong.equals("")) {
                    Toast.makeText(getActivity(), "请先选择楼栋！", Toast.LENGTH_SHORT).show();
                    return;
                }
                getDanYuan();
                tv_title.setText("请选择单元");
                break;
            case 4:
                if (fwLoudong.equals("")) {
                    Toast.makeText(getActivity(), "请先选择楼栋！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (fwDanyuan.equals("")) {
                    Toast.makeText(getActivity(), "请先选择单元！", Toast.LENGTH_SHORT).show();
                    return;
                }
                getFangHao();
                tv_title.setText("请选择房号");
                break;
            case 5:
                if (xiangmuId == 0) {
                    Toast.makeText(getActivity(), "请先选择项目！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (fwLoudong.equals("")) {
                    Toast.makeText(getActivity(), "请先选择楼栋！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (fwDanyuan.equals("")) {
                    Toast.makeText(getActivity(), "请先选择单元！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (fwFanghao.equals("")) {
                    Toast.makeText(getActivity(), "请先选择房号！", Toast.LENGTH_SHORT).show();
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

        new CustomPopWindow.PopupWindowBuilder(getActivity())
                .setClippingEnable(false)
                .setFocusable(true)
                .setView(view)
                .setContenView(ll_content)
                .size(UIUtils.getDisplayWidth(getActivity()), UIUtils.getDisplayHeigh(getActivity()))
                .create()
                .showAtLocation(showView, Gravity.CENTER, 0, 0);
    }


    private void onWheelViewOnconfirm(int flag) {
        //         1为 选择项目， 2 为选择楼栋  3为单元   4为房号

        int position = wheelView.getCurrentItem();
        switch (flag) {
            case 1:
                if (mXiangMu.getData()== null) return;
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
            case 5:
                if (guanliyuan == null || guanliyuan.size() == 0) return;
                tvGuanliyuan.setText(guanliyuan.get(position).trim());
                fwGuanliyuan = guanliyuan.get(position).trim();
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
                            guanliyuan.add(i, content.getData().get(i).getYezhuName() + " " + content.getData().get(i).getYezhuShouji());
                        }
                        xiangmuAdapter = new NumericWheelAdapter(getActivity(), 0, guanliyuan.size() - 1, "", guanliyuan);
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
                        xiangmuAdapter = new NumericWheelAdapter(getActivity(), 0, fangwuhao.size() - 1, "", fangwuhao);
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
                        xiangmuAdapter = new NumericWheelAdapter(getActivity(), 0, danYuanList.size() - 1, "", danYuanList);
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
                        xiangmuAdapter = new NumericWheelAdapter(getActivity(), 0, louPanList.size() - 1, "", louPanList);
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
                            xiangmuAdapter = new NumericWheelAdapter(getActivity(), 0, xiangmuList.size() - 1, "", xiangmuList);
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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
       /* if (!disposables.isDisposed()) {
            disposables.dispose();
        }*/
    }


}
