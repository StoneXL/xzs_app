package com.yxld.xzs.activity.workbench;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.socks.library.KLog;
import com.yxld.xzs.R;
import com.yxld.xzs.activity.pandian.SaoMaActivity;
import com.yxld.xzs.base.BaseActivity;
import com.yxld.xzs.contain.Contains;
import com.yxld.xzs.entity.BaseBack;
import com.yxld.xzs.entity.PanDian;
import com.yxld.xzs.entity.XiangMu;
import com.yxld.xzs.http.api.HttpAPIWrapper;
import com.yxld.xzs.utils.PopWindowUtil;
import com.yxld.xzs.utils.UIUtils;
import com.yxld.xzs.view.CustomPopWindow;
import com.yxld.xzs.view.datepicker.NumericWheelAdapter;
import com.yxld.xzs.view.datepicker.WheelView;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * @author xlei
 * @Date 2018/5/4.
 */

public class PanDianActivity extends BaseActivity {
    @BindView(R.id.tv_xiangmu)
    TextView mTvXiangmu;
    @BindView(R.id.tv_leixing)
    TextView mTvLeixing;

    private WheelView wheelView;
    private NumericWheelAdapter xiangmuAdapter;
    private ArrayList<String> xiangmuList;
    private XiangMu mXiangMu;
    private int xiangmuId;
    private int pandianType;//盘点分类 1为内部使用，2为商城可售

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pandian);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initData();
    }

    private void initData() {
        xiangmuList = new ArrayList<String>();
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
        wheelView.setViewAdapter(xiangmuAdapter);
        switch (flag) {
            case 1:
                tv_title.setText("请选择盘点项目");
                break;
            case 2:
                if (xiangmuId == 0) {
                    Toast.makeText(this, "请选择盘点项目！", Toast.LENGTH_SHORT).show();
                    return;
                }
                tv_title.setText("请选择类型");
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

        new CustomPopWindow.PopupWindowBuilder(this).setClippingEnable(false).setFocusable(true).setView(view)
                .setContenView(ll_content).size(UIUtils.getDisplayWidth(this), UIUtils.getDisplayHeigh(this)).create
                ().showAtLocation(showView, Gravity.CENTER, 0, 0);
    }

    private void onWheelViewOnconfirm(int flag) {
        //         1为 选择项目， 2 为选择类型
        int position = wheelView.getCurrentItem();
        switch (flag) {
            case 1:
                if (mXiangMu.getData() == null) {
                    return;
                }
                mTvXiangmu.setText(mXiangMu.getData().get(position).getXiangmuName().trim());
                if (xiangmuId != mXiangMu.getData().get(position).getXiangmuId()) {
                    pandianType = 0;
                    mTvLeixing.setText("");
                }
                xiangmuId = mXiangMu.getData().get(position).getXiangmuId();
            default:
                break;
        }
    }

    private void getXiangMu() {
        progressDialog.show();
        Map<String, String> map = new HashMap<>();
        map.put("uuId", Contains.uuid);
        Disposable disposable = HttpAPIWrapper.getInstance(HttpAPIWrapper.getOkHttpClient()).findXm(map).subscribe
                (new Consumer<XiangMu>() {
            @Override
            public void accept(@NonNull XiangMu xiangMu) throws Exception {
                progressDialog.hide();
                mXiangMu = xiangMu;
                if (xiangMu.status == 0) {
                    xiangmuList.clear();
                    for (int i = 0; i < xiangMu.getData().size(); i++) {
                        if (xiangMu.getData().get(i) != null) {
                            xiangmuList.add(xiangMu.getData().get(i).getXiangmuName());
                        }
                    }
                    xiangmuAdapter = new NumericWheelAdapter(PanDianActivity.this, 0, xiangmuList.size() - 1, "",
                            xiangmuList);
                    xiangmuAdapter.setTextSize(15);
                    showWheelView(mTvXiangmu, 1);
                } else {
                    onError(xiangMu.status, xiangMu.msg);
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                progressDialog.hide();
            }
        }, new Action() {
            @Override
            public void run() throws Exception {

            }
        });
        disposables.add(disposable);
    }

    private void getLeiXing() {
        View view = this.getLayoutInflater().inflate(R.layout.view_pop_add_img, null);
        AutoLinearLayout contentView = (AutoLinearLayout) view.findViewById(R.id.ll_pop_root);
        TextView neibu = (TextView) view.findViewById(R.id.tv_take_photo);
        TextView shangcheng = (TextView) view.findViewById(R.id.tv_album);
        TextView quxiao = (TextView) view.findViewById(R.id.tv_cancel);
        neibu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomPopWindow.onBackPressed();
                pandianType = 1;
                mTvLeixing.setText("内部使用");

            }
        });
        shangcheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomPopWindow.onBackPressed();
                pandianType = 2;
                mTvLeixing.setText("商城可售");
            }
        });
        quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomPopWindow.onBackPressed();
                pandianType = 0;
                mTvLeixing.setText("");
            }
        });
        PopWindowUtil.showFullScreenPopWindow(this, mTvLeixing, view, contentView);
    }


    @OnClick({R.id.tv_xiangmu, R.id.tv_leixing, R.id.btn_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_xiangmu:
                getXiangMu();
                break;
            case R.id.tv_leixing:
                getLeiXing();
                break;
            case R.id.btn_confirm:
                if (xiangmuId == 0) {
                    Toast.makeText(this, "请选择盘点项目！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (pandianType == 0) {
                    Toast.makeText(this, "请选择盘点类型！", Toast.LENGTH_SHORT).show();
                    return;
                }
                Map<String, String> map = new HashMap<>();
                map.put("uuid", Contains.uuid);
                map.put("pandianXiangmuId", xiangmuId + "");
                map.put("pandianFenlei",pandianType+"");
                startPandian(map);
//                startActivity(SaoMaActivity.class);
                break;
            default:
                break;
        }
    }

    private void startPandian(Map<String, String> map) {
        progressDialog.show();
        HttpAPIWrapper.getInstance().startPandian(map).subscribe(new Consumer<PanDian>() {
            @Override
            public void accept(@NonNull PanDian baseBack) throws Exception {
                progressDialog.hide();
              if (baseBack.success){
                  Intent intent = new Intent(PanDianActivity.this, SaoMaActivity.class);
                  intent.putExtra("pandianId",baseBack.getData().getPandianId()+"");
                  startActivity(intent);
              }else {
                  onError(baseBack.status,baseBack.msg);
              }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                progressDialog.hide();
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                progressDialog.hide();
            }
        });
    }

}
