package com.yxld.xzs.activity.Navigation;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.yxld.xzs.R;
import com.yxld.xzs.adapter.SummaryAdapter;
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
 * 作者：yishangfei on 2017/1/6 0006 17:07
 * 邮箱：yishangfei@foxmail.com
 * 工作汇总
 */
public class SummaryActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener,View.OnClickListener{
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout mSummaryRefresh;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerview;

    private SummaryAdapter summaryAdapter;
    private View loadingView,notDataView;
    private boolean mNoData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mSummaryRefresh.setOnRefreshListener(this);
        mSummaryRefresh.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        loadingView = getLayoutInflater().inflate(R.layout.loading_view, (ViewGroup) recyclerview.getParent(), false);
        notDataView = getLayoutInflater().inflate(R.layout.empty_view, (ViewGroup) recyclerview.getParent(), false);
        Summary();
    }

    private void Summary() {

        ServiceFactory.httpService()
                .complete(Contains.appLogin.getAdminId())
                .compose(new DefaultTransformer<List<AppPeisongorder>>())
                .subscribe(new RxSubscriber<List<AppPeisongorder>>(this) {
                    @Override
                    public void onNext(List<AppPeisongorder> complete) {
                        if (mSummaryRefresh != null) {
                            mSummaryRefresh.setRefreshing(false);
                        }
                        summaryAdapter = new SummaryAdapter(complete);
                        if (complete.size() == 0) {
                            mNoData = true;
                            refresh();
                        }
                        recyclerview.setHasFixedSize(true);
                        recyclerview.setLayoutManager(new LinearLayoutManager(SummaryActivity.this));
                        recyclerview.setAdapter(summaryAdapter);
                    }

                    @Override
                    protected void onError(ApiException ex) {
                        super.onError(ex);
                        if (mSummaryRefresh != null) {
                            mSummaryRefresh.setRefreshing(false);
                        }
                    }
                });
    }


    private void refresh() {
        loadingView = getLayoutInflater().inflate(R.layout.loading_view, (ViewGroup) recyclerview.getParent(), false);
        notDataView = getLayoutInflater().inflate(R.layout.empty_view, (ViewGroup) recyclerview.getParent(), false);
        notDataView.setOnClickListener(this);
        summaryAdapter.setEmptyView(loadingView);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mNoData) {
                    summaryAdapter.setEmptyView(notDataView);
                    mNoData = false;
                }
            }
        }, 1000);
    }


    @Override
    public void onRefresh() {
        Summary();
    }

    @Override
    public void onClick(View view) {
        Summary();
    }
}
