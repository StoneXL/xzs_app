package com.yxld.xzs.activity.pandian;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.socks.library.KLog;
import com.yxld.xzs.R;
import com.yxld.xzs.base.BaseActivity;
import com.yxld.xzs.contain.Contains;
import com.yxld.xzs.entity.BaseBack;
import com.yxld.xzs.entity.PanDianDetail;
import com.yxld.xzs.http.api.HttpAPIWrapper;
import com.yxld.xzs.utils.StringUitl;
import com.yxld.xzs.utils.ToastUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * @author xlei
 * @Date 2018/5/9.
 */

public class PanDianDetailActivity extends BaseActivity {

    @BindView(R.id.tv_kucun_leibie)
    TextView mTvKucunLeibie;
    @BindView(R.id.tv_tiaoxingma)
    TextView mTvTiaoxingma;
    @BindView(R.id.tv_mingcheng)
    TextView mTvMingcheng;
    @BindView(R.id.tv_guige)
    TextView mTvGuige;
    @BindView(R.id.tv_danwei)
    TextView mTvDanwei;
    @BindView(R.id.tv_shijian)
    TextView mTvShijian;
    @BindView(R.id.tv_shuliang)
    TextView mTvShuliang;
    @BindView(R.id.tv_danjia)
    TextView mTvDanjia;
    @BindView(R.id.tv_shijian2)
    TextView mTvShijian2;
    @BindView(R.id.et_shuliang)
    EditText mEtShuliang;
    @BindView(R.id.btn_confirm)
    Button mBtnConfirm;
    private String pandianId;
    private String wuziBianhao;
    private PanDianDetail panDianDetail;
    private LinearLayout mEmptyLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pandian_detail);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();
        getDatafromSever();
    }

    private void initView() {
        mEmptyLayout = (LinearLayout) findViewById(R.id.empty_layout);
        pandianId = getIntent().getStringExtra("pandianId");
        wuziBianhao = getIntent().getStringExtra("wuziBianhao");
        mTvTiaoxingma.setText(wuziBianhao);

    }

    private void getDatafromSever() {

        Map<String, String> map = new HashMap<>();
        map.put("pandianId", pandianId);
        map.put("uuid", Contains.uuid);
        if (StringUitl.isNoEmpty(pandianId) && StringUitl.isNoEmpty(wuziBianhao)) {
            HttpAPIWrapper.getInstance().getPandianDetail(wuziBianhao, map).subscribe(new Consumer<PanDianDetail>() {
                @Override
                public void accept(@NonNull PanDianDetail panDian) throws Exception {
                    KLog.e("onsuccess");
                    if (panDian.success) {
                        mEmptyLayout.setVisibility(View.GONE);
                        panDianDetail = panDian;
                        mTvMingcheng.setText(panDian.getData().getWuzi().getWuziMingcheng());
                        mTvGuige.setText(panDian.getData().getWuzi().getWuziGuige());
                        mTvDanwei.setText(panDian.getData().getWuzi().getWuziDanwei());
                        mTvDanjia.setText(panDian.getData().getWuzi().getWuziDanjia() + "");
                        mTvShuliang.setText(panDian.getData().getDetails().get(0).getDetailShuliang() + "");
                        mTvShijian.setText(panDian.getData().getDetails().get(0).getDetailShengchanRiqi());
                        mTvShijian2.setText(panDian.getData().getDetails().get(0).getDetailGuoqiRiqi());
                        mTvKucunLeibie.setText(panDian.getData().getWuzi().getWuziFenlei() == 1 ? "内部使用" : "商城可售");
                    } else {
                        onError(panDian.status, panDian.getMsg());
                        mEmptyLayout.setVisibility(View.VISIBLE);
                    }
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(@NonNull Throwable throwable) throws Exception {
                    KLog.e("onError" + throwable.toString());
                    mEmptyLayout.setVisibility(View.VISIBLE);
                }
            }, new Action() {
                @Override
                public void run() throws Exception {
                    KLog.e("oncomplete");
                }
            });
        }
    }

    @OnClick(R.id.btn_confirm)
    public void onViewClicked() {
        if (!StringUitl.isNoEmpty(mEtShuliang.getText().toString().trim())) {
            ToastUtil.showToast(this, "请输入盘点数量");
            return;
        }
        if (!StringUitl.isNoEmpty(pandianId)) {
            return;
        }
        if (panDianDetail == null) {
            return;
        }
        progressDialog.show();
        Map<String, String> map = new HashMap<>();
        map.put("uuid", Contains.uuid);
        map.put("detailKucunDetailId", panDianDetail.getData().getDetails().get(0).getDetailKucunId() + "");
        map.put("detailPandianId", pandianId);
        map.put("detailQianShuliang", panDianDetail.getData().getDetails().get(0).getDetailShuliang() + "");
        map.put("detailHouShuliang", mEtShuliang.getText().toString().trim());
        map.put("detailChaShuliang", (Integer.parseInt(mEtShuliang.getText().toString().trim()) - panDianDetail
                .getData().getDetails().get(0).getDetailShuliang()) + "");

        HttpAPIWrapper.getInstance().confirmPandian(map).subscribe(new Consumer<BaseBack>() {
            @Override
            public void accept(@NonNull BaseBack panDian) throws Exception {
                progressDialog.hide();
                KLog.i("success");
                if (panDian.success){
                    ToastUtil.showToast(PanDianDetailActivity.this,panDian.getMsg());
                    finish();

                }else {
                    onError(panDian.status,panDian.msg);
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                progressDialog.hide();
                KLog.i("onerror");

            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                KLog.i("oncomplete");
                progressDialog.hide();
            }
        });
    }
}
