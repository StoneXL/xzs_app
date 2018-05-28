package com.yxld.xzs.activity.camera;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.socks.library.KLog;
import com.yxld.xzs.R;
import com.yxld.xzs.base.BaseActivity;
import com.yxld.xzs.contain.Contains;
import com.yxld.xzs.entity.BaseBack;
import com.yxld.xzs.entity.FangQu;
import com.yxld.xzs.http.api.HttpAPIWrapper;
import com.yxld.xzs.utils.UIUtils;
import com.yxld.xzs.view.CustomPopWindow;
import com.yxld.xzs.view.datepicker.NumericWheelAdapter;
import com.yxld.xzs.view.datepicker.WheelView;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * 作者：Android on 2017/9/11
 * 邮箱：365941593@qq.com
 * 描述：
 */

public class LearnDeviceActivity extends BaseActivity {
    @BindView(R.id.tv_fangqu_bianhao)
    TextView tvFangquBianhao;
    @BindView(R.id.et_mingcheng)
    EditText etMingcheng;
    @BindView(R.id.et_beizhu)
    EditText etBeizhu;
    @BindView(R.id.tv_confirm)
    TextView tvConfirm;

    FangQu.DataBean.PaianShebeiBean dataBean;
    @BindView(R.id.tv_fangqu_leixing)
    TextView tvFangquLeixing;
    @BindView(R.id.tv_fangqu_kaiguan)
    TextView tvFangquKaiguan;
    private WheelView wheelView;
    private NumericWheelAdapter xiangmuAdapter;
    private ArrayList<String> kaiguanList = new ArrayList<>();
    private String mac;
    private int flag;//0表示学习界面  1表示修改界面

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        setContentView(R.layout.activity_learn_device);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        flag = getIntent().getIntExtra("flag", 0);
        if (flag == 0) {
            toolbar.setTitle("学习设备");
            tvConfirm.setText("学习设备");
        } else {
            toolbar.setTitle("修改");
            tvConfirm.setText("确认修改");
        }
        dataBean = getIntent().getParcelableExtra("data");

        mac = getIntent().getStringExtra("mac");
        tvFangquBianhao.setText(dataBean.getShebeiFangquBianhao());
        etMingcheng.setText(dataBean.getShebeiName());
        etBeizhu.setText(dataBean.getShebeiBeizhu());
        switch (dataBean.getShebeiFangquLeixin()) {
            case "0":
                tvFangquLeixing.setText("普通防区");
                checkedFangqu=0;
                break;
            case "1":
                tvFangquLeixing.setText("紧急防区");
                checkedFangqu=1;
                break;
            case "2":
                tvFangquLeixing.setText("留守防区");
                checkedFangqu=2;
                break;
            default:checkedFangqu=-1;
        }
      //  tvFangquLeixing.setText(dataBean.getShebeiFangquLeixin().equals("0") ?);
        tvFangquKaiguan.setText(dataBean.getShebeiMingliKaiguan().equals("1") ? "开" : "关");
        fangquList.add("普通防区");
        fangquList.add("紧急防区");
        fangquList.add("留守防区");
        kaiguanList.add("开");
        kaiguanList.add("关");
//        fangquList.add("智能防区");
//        fangquList.add("关闭防区");
//        fangquList.add("门铃防区");
//        fangquList.add("迎宾防区");
//        fangquList.add("求助防区");
    }

    //学习设备
    private void learnDevice() {
        progressDialog.show();
        Map<String, String> map = new HashMap<>();
        map.put("uuid", Contains.uuid);
        map.put("paianShebei.shebeiZhujiMac", mac);
        map.put("paianShebei.shebeiName", etMingcheng.getText().toString().trim());
        map.put("paianShebei.shebeiFangquBianhao", dataBean.getShebeiFangquBianhao());
        map.put("paianShebei.shebeiFangquLeixin", checkedFangqu + "");
        map.put("paianShebei.shebeiMingliKaiguan", "开".equals(tvFangquKaiguan.getText()) ? "1" : "0");
        map.put("paianShebei.shebeiBeizhu", etBeizhu.getText().toString().trim());
        KLog.i(map);
        Disposable disposable = HttpAPIWrapper.getInstance(HttpAPIWrapper.getOkHttpClient()).xuexiShebei(map)
                .subscribe(new Consumer<BaseBack>() {
                    @Override
                    public void accept(@NonNull BaseBack baseBack) throws Exception {
                        Toast.makeText(LearnDeviceActivity.this, baseBack.getMSG(), Toast.LENGTH_SHORT).show();
                        if (baseBack.getStatus() == 1) {
                            mHandler.sendEmptyMessageDelayed(0, 5000);
                        } else {
                            progressDialog.hide();
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

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            progressDialog.hide();
            switch (msg.what) {
                case 0:
                    setResult(2);
                    finish();
                    break;
                case 1:
                    setResult(3);
                    finish();
                    break;
            }

        }
    };

    //修改防区
    private void xiugai() {
        progressDialog.show();
        Map<String, String> map = new HashMap<>();
        map.put("uuid", Contains.uuid);
        map.put("paianShebei3.shebeiName", etMingcheng.getText().toString().trim());
        map.put("paianShebei3.shebeiId", dataBean.getShebeiId()+"");
        map.put("paianShebei3.shebeiFangquLeixin", checkedFangqu + "");
        map.put("paianShebei3.shebeiMingliKaiguan", "开".equals(tvFangquKaiguan.getText()) ? "1" : "0");
        map.put("paianShebei3.shebeiBeizhu", etBeizhu.getText().toString().trim());
        map.put("paianShebei3.shebeiZhujiMac", mac);
        map.put("paianShebei3.shebeiBeizhu", etBeizhu.getText().toString().trim());
        map.put("paianShebei3.shebeiFangquBianhao", tvFangquBianhao.getText().toString());
        KLog.i(map);
        Disposable disposable = HttpAPIWrapper.getInstance(HttpAPIWrapper.getOkHttpClient()).xiugaiFangqu(map)
                .subscribe(new Consumer<BaseBack>() {
                    @Override
                    public void accept(@NonNull BaseBack baseBack) throws Exception {
                        Toast.makeText(LearnDeviceActivity.this, baseBack.getMSG(), Toast.LENGTH_SHORT).show();
                        if (baseBack.getStatus() == 1) {
                            mHandler.sendEmptyMessageDelayed(1, 5000);
                        } else {

                            progressDialog.hide();
                            setResult(3);
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

    /**
     * * 			0:普通防区
     * 1:紧急防区
     * 2:留守防区
     * 3:智能防区
     * 4:关闭防区
     * 5:门铃防区
     * 6:迎宾防区
     * 7:求助防区
     *
     * @param showView
     */
    int[] fangqu = new int[]{0, 1, 2, 3, 4, 5, 6, 7};
    ArrayList<String> fangquList = new ArrayList<>();
    int checkedFangqu = -1;

    private void showWheelView(View showView) {
        View view = LayoutInflater.from(this).inflate(R.layout.picker_xiangmu, null);
        AutoLinearLayout ll_content = (AutoLinearLayout) view.findViewById(R.id.ll_content);
        ll_content.setAnimation(AnimationUtils.loadAnimation(this, R.anim.pop_manage_product_in));
        TextView submit = (TextView) ll_content.findViewById(R.id.submit);
        TextView tv_title = (TextView) ll_content.findViewById(R.id.tv_title);
        tv_title.setText("请选择项目");
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomPopWindow.onBackPressed();
                tvFangquLeixing.setText(fangquList.get(wheelView.getCurrentItem()));
                checkedFangqu = fangqu[wheelView.getCurrentItem()];
            }
        });
        wheelView = (WheelView) ll_content.findViewById(R.id.xiangmu);
        xiangmuAdapter = new NumericWheelAdapter(LearnDeviceActivity.this, 0, fangquList.size() - 1, "", fangquList);
        xiangmuAdapter.setTextSize(15);
        wheelView.setViewAdapter(xiangmuAdapter);
        new CustomPopWindow.PopupWindowBuilder(this)
                .setClippingEnable(false)
                .setFocusable(true)
                .setView(view)
                .setContenView(ll_content)
                .size(UIUtils.getDisplayWidth(this), UIUtils.getDisplayHeigh(this))
                .create()
                .showAtLocation(showView, Gravity.CENTER, 0, 0);
    }

    private void showWheelView1(View showView) {
        View view = LayoutInflater.from(this).inflate(R.layout.picker_xiangmu, null);
        AutoLinearLayout ll_content = (AutoLinearLayout) view.findViewById(R.id.ll_content);
        ll_content.setAnimation(AnimationUtils.loadAnimation(this, R.anim.pop_manage_product_in));
        TextView submit = (TextView) ll_content.findViewById(R.id.submit);
        TextView tv_title = (TextView) ll_content.findViewById(R.id.tv_title);
        tv_title.setText("请选择开关");
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomPopWindow.onBackPressed();
                tvFangquKaiguan.setText(kaiguanList.get(wheelView.getCurrentItem()));
            }
        });
        wheelView = (WheelView) ll_content.findViewById(R.id.xiangmu);
        xiangmuAdapter = new NumericWheelAdapter(LearnDeviceActivity.this, 0, kaiguanList.size() - 1, "", kaiguanList);
        xiangmuAdapter.setTextSize(15);
        wheelView.setViewAdapter(xiangmuAdapter);
        new CustomPopWindow.PopupWindowBuilder(this)
                .setClippingEnable(false)
                .setFocusable(true)
                .setView(view)
                .setContenView(ll_content)
                .size(UIUtils.getDisplayWidth(this), UIUtils.getDisplayHeigh(this))
                .create()
                .showAtLocation(showView, Gravity.CENTER, 0, 0);
    }

    @OnClick({R.id.tv_fangqu_leixing, R.id.tv_confirm, R.id.tv_fangqu_kaiguan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_fangqu_leixing:
                showWheelView(tvFangquLeixing);
                break;
            case R.id.tv_fangqu_kaiguan:
                showWheelView1(tvFangquKaiguan);
                break;
            case R.id.tv_confirm:
//                if (checkedFangqu == -1) {
//                    Toast.makeText(this, "请选择防区类型", Toast.LENGTH_SHORT).show();
//                    return;
//                } else if (etMingcheng.getText().toString().trim().equals("")) {
//                    Toast.makeText(this, "请输入防区名称", Toast.LENGTH_SHORT).show();
//                    return;
//                } else if (tvFangquKaiguan.getText().toString().trim().equals("") || tvFangquKaiguan.getText() == null) {
//                    Toast.makeText(this, "请选择鸣笛开关", Toast.LENGTH_SHORT).show();
//                    return;
//                }
                if (flag == 0) {
                    if (checkedFangqu == -1) {
                    Toast.makeText(this, "请选择防区类型", Toast.LENGTH_SHORT).show();
                    return;
                } else if (etMingcheng.getText().toString().trim().equals("")) {
                    Toast.makeText(this, "请输入防区名称", Toast.LENGTH_SHORT).show();
                    return;
                } else if (tvFangquKaiguan.getText().toString().trim().equals("") || tvFangquKaiguan.getText() == null) {
                    Toast.makeText(this, "请选择鸣笛开关", Toast.LENGTH_SHORT).show();
                    return;
                }
                    learnDevice();
                } else {


                    xiugai();
                }
                break;
            default:break;
        }
    }


}
