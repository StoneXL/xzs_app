package com.yxld.xzs.activity.Repair;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.yxld.xzs.R;
import com.yxld.xzs.adapter.RepairAdapter;
import com.yxld.xzs.base.BaseActivity;
import com.yxld.xzs.contain.Contains;
import com.yxld.xzs.entity.AppRepair;
import com.yxld.xzs.entity.AppWork;
import com.yxld.xzs.http.ServiceFactory;
import com.yxld.xzs.subscribers.RxSubscriber;
import com.yxld.xzs.transformer.DefaultTransformer;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.orhanobut.logger.Logger.init;

/**
 * 作者：yishangfei on 2016/12/13 0013 15:34
 * 邮箱：yishangfei@foxmail.com
 * 验收审核
 */
public class RepairActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;
    private boolean mNoData;
    private List<AppWork> works;
    private RepairAdapter repairAdapter;
    private View loadingView, notDataView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initData();
    }

    private void initData() {
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initView();
    }

    private void initView() {
        loadingView = getLayoutInflater().inflate(R.layout.loading_view, (ViewGroup) recyclerView.getParent(), false);
        notDataView = getLayoutInflater().inflate(R.layout.empty_view, (ViewGroup) recyclerView.getParent(), false);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        Work();

    }


    private void Work() {
        ServiceFactory.httpService()
                .work(Contains.appLogin.getAdminId())
                .compose(new DefaultTransformer<List<AppWork>>())
                .subscribe(new RxSubscriber<List<AppWork>>(this) {
                    @Override
                    public void onNext(List<AppWork> appWorks) {
                        if (swipeLayout != null) {
                            swipeLayout.setRefreshing(false);
                        }
                        works = appWorks;
                        repairAdapter = new RepairAdapter(appWorks);
                        if (appWorks.size() == 0) {
                            mNoData = true;
                            refresh();
                        }
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(RepairActivity.this));
                        recyclerView.setAdapter(repairAdapter);
                        recyclerView.addOnItemTouchListener(listener);
                    }
                });
    }


    private OnItemClickListener listener = new OnItemClickListener() {
        @Override
        public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
            AppWork appWork = works.get(position);
            Intent intent = new Intent();
            if ("2".equals(appWork.getBaoxiu_status())||"1".equals(appWork.getBaoxiu_status())) {
                intent.setClass(RepairActivity.this, WorkActivity.class);
            } else {
                intent.setClass(RepairActivity.this, NewActivity.class);
            }
            AppRepair sp = new AppRepair(
                    appWork.getBaoxiu_id(),
                    appWork.getBaoxiu_status(),
                    appWork.getBaoxiu_danhao(),
                    appWork.getBaoxiu_lrtime(),
                    appWork.getBaoxiu_paidantime(),
                    appWork.getBaoxiu_img(),
                    appWork.getBaoxiu_chayanimg(),
                    appWork.getBaoxiu_name(),
                    appWork.getBaoxiu_zx_weixiuren(),
                    appWork.getBaoxiu_fuzerenyijian(),
                    appWork.getBaoxiu_loupan(),
                    appWork.getBaoxiu_loudong(),
                    appWork.getBaoxiu_danyuan(),
                    appWork.getBaoxiu_fanghao(),
                    appWork.getBaoxiu_didian(),
                    appWork.getBaoxiu_dianhua(),
                    appWork.getBaoxiu_xiangmu(),
                    appWork.getBaoxiu_zx_weixiurenphone(),
                    appWork.getBaoxiu_weixiudanwei()
            );
            intent.putExtra("Parcelable", sp);
            //启动activity
            startActivity(intent);
        }
    };


    private void refresh() {
        loadingView = getLayoutInflater().inflate(R.layout.loading_view, (ViewGroup) recyclerView.getParent(), false);
        notDataView = getLayoutInflater().inflate(R.layout.empty_view, (ViewGroup) recyclerView.getParent(), false);
        notDataView.setOnClickListener(this);
        repairAdapter.setEmptyView(loadingView);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mNoData) {
                    repairAdapter.setEmptyView(notDataView);
                    mNoData = false;
                }
            }
        }, 1000);
    }

    @Override
    public void onRefresh() {
        Work();
    }

    @Override
    public void onClick(View v) {
        Work();
    }
}