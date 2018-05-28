package com.yxld.xzs.activity.Income;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.socks.library.KLog;
import com.yxld.xzs.R;
import com.yxld.xzs.base.BaseActivity;
import com.yxld.xzs.contain.Contains;
import com.yxld.xzs.entity.BaseBack1;
import com.yxld.xzs.entity.IncomeBean;
import com.yxld.xzs.http.api.HttpAPIWrapper;
import com.yxld.xzs.utils.UIUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

import static com.yxld.xzs.activity.Income.YiJieSuanActivity.ENTER_TYPE;

/**
 * Created by yishangfei on 2016/10/11 0011.
 * 我的收入
 */

public class IncomeActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout incomeRefresh;
    @BindView(R.id.tv_shouru)
    TextView mTvShouru;
    @BindView(R.id.tv_yijiesuan)
    TextView mTvYijiesuan;
    @BindView(R.id.tv_weijiesuan)
    TextView mTvWeijiesuan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        incomeRefresh.setOnRefreshListener(this);
        UIUtils.configSwipeRefreshLayoutColors(incomeRefresh);
        Income();
    }

    /**
     * 配送员总收入网络请求
     */
    private void Income() {
        Map<String, String> map = new HashMap<>(16);
        map.put("uuid", Contains.uuid + "");
        HttpAPIWrapper.getInstance().getZongShouRu(map).subscribe(new Consumer<IncomeBean>() {
            @Override
            public void accept(@NonNull IncomeBean baseBack1) throws Exception {
                if (incomeRefresh != null) {
                    incomeRefresh.setRefreshing(false);
                }
                if (baseBack1.status != 0) {
                    onError(baseBack1.status, baseBack1.MSG);
                    return;
                }
                mTvShouru.setText("¥ " + String.format("%.2f", baseBack1.getRows().getZonge()));
                mTvWeijiesuan.setText("未结算：" + "¥ " + String.format("%.2f", baseBack1.getRows().getWeijiesuanjine()));
                mTvYijiesuan.setText("已结算：" + "¥ " + String.format("%.2f", baseBack1.getRows().getYijiesuanjine()));

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                if (incomeRefresh != null) {
                    incomeRefresh.setRefreshing(false);
                }
                KLog.i("onError" + throwable.toString());
            }
        });
    }


    @Override
    public void onRefresh() {
        Income();
    }

    @OnClick({R.id.tv_yijiesuan, R.id.tv_weijiesuan})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.tv_yijiesuan:
                //已结算点击
                intent = new Intent(IncomeActivity.this, YiJieSuanActivity.class);
                intent.putExtra(ENTER_TYPE, 1);
                startActivity(intent);
                break;
            case R.id.tv_weijiesuan:
                //未结算点击
                intent = new Intent(IncomeActivity.this, YiJieSuanActivity.class);
                intent.putExtra(ENTER_TYPE, 2);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
