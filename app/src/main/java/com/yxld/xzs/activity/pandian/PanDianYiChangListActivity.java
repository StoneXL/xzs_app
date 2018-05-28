package com.yxld.xzs.activity.pandian;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.yxld.xzs.R;
import com.yxld.xzs.base.BaseActivity;
import com.yxld.xzs.utils.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 异常列表
 * Created by William on 2018/5/28.
 */

public class PanDianYiChangListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;
    @BindView(R.id.bt_confirm_yichang)
    Button btConfirmYichang;

    private View loadingView, notDataView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pandianyichang_list);
        ButterKnife.bind(this);

        initView();
        initAdapter();
        refresh();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        UIUtils.configSwipeRefreshLayoutColors(swipeLayout);
        swipeLayout.setOnRefreshListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);

        loadingView = getLayoutInflater().inflate(R.layout.loading_view, (ViewGroup) recyclerView
                .getParent(), false);
        notDataView = getLayoutInflater().inflate(R.layout.empty_view, (ViewGroup) recyclerView
                .getParent(), false);
        notDataView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
            }
        });
    }

    /**
     * 初始化适配器
     */
    private void initAdapter() {
//        weiPanDianAdapter = new WeiPanDianAdapter(panDianList);
//        weiPanDianAdapter.setEmptyView(loadingView);
//        weiPanDianAdapter.setLoadMoreView(new CustomLoadMoreView());
//        // 滑动最后一个Item的时候回调onLoadMoreRequested方法
//        weiPanDianAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
//            @Override
//            public void onLoadMoreRequested() {
//                initData(false);
//            }
//        }, recyclerView);
//        recyclerView.setAdapter(weiPanDianAdapter);//绑定适配器
//        //条目点击监听 不做recyclerView的 做adapter的监听
//        weiPanDianAdapter.setOnItemClickListener(this);
    }

    /**
     * 刷新数据
     */
    private void refresh() {
//        page = 1;
//        swipeLayout.setRefreshing(true);//显示加载进度条.要在主线程中执行
//        weiPanDianAdapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载，设置为true就是自动加载更多
//        initData(true);
    }

    @OnClick(R.id.bt_confirm_yichang)
    public void onViewClicked() {

    }

    @Override
    public void onRefresh() {

    }
}
