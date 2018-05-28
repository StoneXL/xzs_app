package com.yxld.xzs.activity.workbench;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yxld.xzs.R;
import com.yxld.xzs.adapter.NightOrderAdapter;
import com.yxld.xzs.base.BaseActivity;
import com.yxld.xzs.contain.Contains;
import com.yxld.xzs.entity.NightOrderList;
import com.yxld.xzs.entity.OrderBean;
import com.yxld.xzs.http.api.HttpAPIWrapper;
import com.yxld.xzs.utils.UIUtils;
import com.yxld.xzs.view.CustomLoadMoreView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * 夜间订单处理
 * Created by William on 2017/11/24.
 */

public class NightOrderListActivity extends BaseActivity implements SwipeRefreshLayout
        .OnRefreshListener, BaseQuickAdapter.OnItemClickListener {

    private final int REQUESTCODE_DETAIL = 1;//请求码
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;

    private NightOrderAdapter nightOrderAdapter;

    private List<OrderBean> nightOrderList;
    private int page;//分页数
    private int rows = 6;//每页加载数

    private View loadingView;//正在加载
    private View notDataView;//无数据
    private View errorView;//请求错误

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nightorder_list);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initView();
        initAdapter();
        refresh();
    }

    /**
     * 设置数据
     *
     * @param isRefresh
     * @param data
     */
    private void setData(boolean isRefresh, NightOrderList data) {
        page++;
        if (null != data.getOrder() && data.getOrder().size() != 0) {
            nightOrderList = data.getOrder();
        } else {
            nightOrderList = new ArrayList<>();
        }
        final int size = nightOrderList == null ? 0 : nightOrderList.size();
//        setRedPoint(data.getTotal(), false);
        if (isRefresh) {
            if (size > 0) {
                nightOrderAdapter.setNewData(nightOrderList);//将首次数据塞入适配器的方法
            } else {
                nightOrderAdapter.setEmptyView(notDataView);
                nightOrderAdapter.setNewData(new ArrayList<OrderBean>());//将首次数据塞入适配器的方法
            }
        } else {
            if (size > 0) {
                nightOrderAdapter.addData(nightOrderList);//加载更多时直接将更多数据塞入适配器
            }
        }

        if (size < rows) {
            //第一页如果不够一页就不显示没有更多数据布局
            nightOrderAdapter.loadMoreEnd(isRefresh);//不传参数默认false,表示数据全部加载完毕没有更多数据  加载结束
            // 这里设置为false可用来显示没有更多数据item
        } else {
            nightOrderAdapter.loadMoreComplete();//加载完成（注意不是加载结束，而是本次数据加载结束并且还有下页数据）
        }
    }

    /**
     * 数据请求
     */
    private void initData(final boolean isRefresh) {
        Map<String, String> map = new HashMap<>();
        map.put("uuid", Contains.uuid);
        map.put("page", page + "");
        map.put("rows", rows + "");
        Log.e("whmap", map.toString());
        Disposable disposable = HttpAPIWrapper.getInstance(HttpAPIWrapper.getOkHttpClient())
                .nightOrderList(map)
                .subscribe(new Consumer<NightOrderList>() {
                    @Override
                    public void accept(@NonNull NightOrderList data) throws Exception {
                        swipeLayout.setRefreshing(false);
                        if (data.status == 0) {
                            if (isRefresh) {
                                setData(true, data);
                                nightOrderAdapter.setEnableLoadMore(true);//自动加载更多
                                swipeLayout.setRefreshing(false);//加载完成,不显示进度条
                            } else {
                                setData(false, data);
                            }
                        } else {
                            nightOrderAdapter.setEmptyView(notDataView);
                            nightOrderAdapter.setNewData(new ArrayList<OrderBean>());
                            onError(data.status, data.MSG);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        nightOrderAdapter.setEmptyView(notDataView);
                        nightOrderAdapter.setNewData(new ArrayList<OrderBean>());
                        nightOrderAdapter.setEnableLoadMore(true);//自动加载更多
                        swipeLayout.setRefreshing(false);//加载完成,不显示进度条
                    }
                });
        disposables.add(disposable);
    }

    /**
     * 刷新数据
     */
    private void refresh() {
        page = 1;
        swipeLayout.setRefreshing(true);//显示加载进度条.要在主线程中执行
        nightOrderAdapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载，设置为true就是自动加载更多
        initData(true);
    }

    /**
     * 初始化适配器
     */
    private void initAdapter() {
        nightOrderAdapter = new NightOrderAdapter();
        nightOrderAdapter.setEmptyView(loadingView);
        nightOrderAdapter.setLoadMoreView(new CustomLoadMoreView());
        // 滑动最后一个Item的时候回调onLoadMoreRequested方法
        nightOrderAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                initData(false);
            }
        }, recyclerView);
        recyclerView.setAdapter(nightOrderAdapter);//绑定适配器
        //条目点击监听 不做recyclerView的 做adapter的监听
        nightOrderAdapter.setOnItemClickListener(this);
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
        errorView = getLayoutInflater().inflate(R.layout.error_view, (ViewGroup) recyclerView
                .getParent(), false);
        notDataView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
            }
        });
        errorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
            }
        });
    }

    @Override
    public void onRefresh() {
        refresh();
    }

    @Override
    public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        OrderBean item = (OrderBean) baseQuickAdapter.getItem(i);
        Intent intent = new Intent(this, NightOrderDetailActivity.class);
        intent.putExtra("nightOrderBean", item);
        startActivityForResult(intent, REQUESTCODE_DETAIL);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

//        Log.e("wh", "requestCode" + requestCode + "resultCode" + resultCode);
        switch (requestCode) {
            case REQUESTCODE_DETAIL:
                if (resultCode == RESULT_OK) {
//                    Log.e("wh", "这里");
                    refresh();
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }
}
