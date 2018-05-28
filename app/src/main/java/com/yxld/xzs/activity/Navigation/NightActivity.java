package com.yxld.xzs.activity.Navigation;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;
import com.yxld.xzs.R;
import com.yxld.xzs.adapter.NightAdapter;
import com.yxld.xzs.base.BaseActivity;
import com.yxld.xzs.contain.Contains;
import com.yxld.xzs.entity.AppPeisongorder;
import com.yxld.xzs.http.ServiceFactory;
import com.yxld.xzs.http.exception.ApiException;
import com.yxld.xzs.subscribers.RxSubscriber;
import com.yxld.xzs.transformer.DefaultTransformer;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yishangfei on 2016/11/10 0010.
 * 通知管理
 */
public class NightActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener,View.OnClickListener {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout mNightRefresh;


    private NightAdapter nightAdapter;
    private View loadingView,notDataView;
    private boolean mNoData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_night);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mNightRefresh.setOnRefreshListener(this);
        mNightRefresh.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        loadingView = getLayoutInflater().inflate(R.layout.loading_view, (ViewGroup) recyclerView.getParent(), false);
        notDataView = getLayoutInflater().inflate(R.layout.empty_view, (ViewGroup) recyclerView.getParent(), false);
        Night();
    }


    //网络请求
    private void Night() {
        ServiceFactory.httpService()
                .night(Contains.appLogin.getAdminId())
                .compose(new DefaultTransformer<List<AppPeisongorder>>())
                .subscribe(new RxSubscriber<List<AppPeisongorder>>(this) {
                    @Override
                    public void onNext(List<AppPeisongorder> night) {
                        if (mNightRefresh != null) {
                            mNightRefresh.setRefreshing(false);

                        }
                        nightAdapter = new NightAdapter(night);
                        if (night.size() == 0) {
                            mNoData = true;
                            refresh();
                        }
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(NightActivity.this));
                        recyclerView.setAdapter(nightAdapter);
                    }

                    @Override
                    protected void onError(ApiException ex) {
                        super.onError(ex);
                        if (mNightRefresh != null) {
                            mNightRefresh.setRefreshing(false);
                        }
                    }
                });
    }


    private void refresh() {
        loadingView = getLayoutInflater().inflate(R.layout.loading_view, (ViewGroup) recyclerView.getParent(), false);
        notDataView = getLayoutInflater().inflate(R.layout.empty_view, (ViewGroup) recyclerView.getParent(), false);
        notDataView.setOnClickListener(this);
        nightAdapter.setEmptyView(loadingView);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mNoData) {
                    nightAdapter.setEmptyView(notDataView);
                    mNoData = false;
                }
            }
        }, 1000);
    }


    @Override
    public void onRefresh() {
        Night();
    }

    @Override
    public void onClick(View view) {
        Night();
    }
}
