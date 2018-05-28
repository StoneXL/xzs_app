package com.yxld.xzs.activity.workbench;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.socks.library.KLog;
import com.yxld.xzs.R;
import com.yxld.xzs.adapter.RobAdapter;
import com.yxld.xzs.base.BaseActivity;
import com.yxld.xzs.contain.Contains;
import com.yxld.xzs.entity.BaseBack;
import com.yxld.xzs.entity.OrderBean;
import com.yxld.xzs.entity.OrderDetailBean;
import com.yxld.xzs.entity.RobBean;
import com.yxld.xzs.http.api.HttpAPIWrapper;
import com.yxld.xzs.utils.ToastUtil;
import com.yxld.xzs.utils.UIUtils;
import com.yxld.xzs.view.CustomLoadMoreView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


/**
 * 待抢单
 * Created by William on 2017/11/15.
 */

public class RobActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;

    //以下三个控件应该放在baseactivity中初始化作为公用，而不是在子类中
    private View loadingView;//正在加载
    private View notDataView;//无数据
    private View errorView;//请求错误

    private RobAdapter robAdapter;

    private List<OrderBean> orderBeanList;
    private int page;//分页数
    private int rows = 6;//每页加载数

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rob);
        ButterKnife.bind(this);
//        needFront = true;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initView();
        initAdapter();
        refresh();
    }

    private void initAdapter() {
        robAdapter = new RobAdapter();
        robAdapter.setEmptyView(loadingView);
        robAdapter.setLoadMoreView(new CustomLoadMoreView());//设置自定义加载布局
        // 滑动最后一个Item的时候回调onLoadMoreRequested方法
        robAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                initData(false);
            }
        }, recyclerView);
//      robAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);//设置item加载动画
//      mAdapter.setPreLoadNumber(N);// 当列表滑动到倒数第N个Item的时候(默认是1)回调onLoadMoreRequested方法
        recyclerView.setAdapter(robAdapter);//绑定适配器
        //条目点击监听 不做recyclerView的 做adapter的监听
     /*   recyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(final BaseQuickAdapter adapter, final View view, final
            int position) {
                *//*Toast.makeText(PullToRefreshUseActivity.this, Integer.toString(position), Toast
                        .LENGTH_LONG).show();*//*
            }
        });*/
        robAdapter.setOnItemChildClickListener(this);
        robAdapter.setOnItemClickListener(this);
    }

    /**
     * 刷新数据
     */
    private void refresh() {
        page = 1;
        swipeLayout.setRefreshing(true);//显示加载进度条.要在主线程中执行
        robAdapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载，设置为true就是自动加载更多
        initData(true);
    }

    /**
     * 数据请求
     *
     * @param isRefresh
     */
    private void initData(final boolean isRefresh) {
        Map<String, String> map = new HashMap<>();
        map.put("uuid", Contains.uuid);
        map.put("page", page + "");
        map.put("rows", rows + "");
        Disposable disposable = HttpAPIWrapper.getInstance(HttpAPIWrapper.getOkHttpClient())
                .robList(map)
                .subscribe(new Consumer<RobBean>() {
                    @Override
                    public void accept(@NonNull RobBean robBean) throws Exception {
                        swipeLayout.setRefreshing(false);
                        if (robBean.status == 0) {
                            if (isRefresh) {
                                setData(true, robBean);
                                robAdapter.setEnableLoadMore(true);//自动加载更多
                                swipeLayout.setRefreshing(false);//加载完成,不显示进度条
                            } else {
                                setData(false, robBean);
                            }
                        } else {
                            robAdapter.setEmptyView(notDataView);
                            robAdapter.setNewData(new ArrayList<OrderBean>());
                            onError(robBean.status, robBean.MSG);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        robAdapter.setEmptyView(notDataView);
                        robAdapter.setEnableLoadMore(true);//自动加载更多
                        swipeLayout.setRefreshing(false);//加载完成,不显示进度条
                        ToastUtil.show(RobActivity.this, "加载失败");
                    }
                });
        disposables.add(disposable);
    }

    private void setData(boolean isRefresh, RobBean data) {
        regroup(data);
        page++;
        final int size = orderBeanList == null ? 0 : orderBeanList.size();

        setRedPoint(data.getTotal(), false);

        if (isRefresh) {
            if (size > 0) {

            } else {
                robAdapter.setEmptyView(notDataView);
            }
            robAdapter.setNewData(orderBeanList);//将首次数据塞入适配器的方法
        } else {
            if (size > 0) {
                robAdapter.addData(orderBeanList);//加载更多时直接将更多数据塞入适配器
            }
        }

        if (size < rows) {
            //第一页如果不够一页就不显示没有更多数据布局
            robAdapter.loadMoreEnd(isRefresh);//不传参数默认false,表示数据全部加载完毕没有更多数据  加载结束
            // 这里设置为false可用来显示没有更多数据item
        } else {
            robAdapter.loadMoreComplete();//加载完成（注意不是加载结束，而是本次数据加载结束并且还有下页数据）
        }
    }

    /**
     * 设置小红点数据
     *
     * @param size
     */
    private void setRedPoint(int size, boolean change) {
        if (Contains.indexMessageList != null && Contains.indexMessageList.size() > 0) {
            if (change) {
                Contains.indexMessageList.set(1, size);
            } else {
                Contains.indexMessageList.set(0, size);
            }
        }

    }

    private void initView() {
        UIUtils.configSwipeRefreshLayoutColors(swipeLayout);
        swipeLayout.setOnRefreshListener(this);
        // important! setLayoutManager should be called after setAdapter
        // 设置layoutmanager必须在setAdapter之后?但是在demo中都不是这样？？？？？
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
//        ToastUtil.show(this, "没有详情，不能点击");
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
//        ToastUtil.show(this, "恭喜抢单成功");
        OrderBean orderBean = (OrderBean) baseQuickAdapter.getItem(i);
        confirmRob(orderBean);
    }

    private void confirmRob(OrderBean orderBean) {
        progressDialog.show();

        Map<String, String> map = new HashMap<>();
        map.put("uuid", Contains.uuid);
        map.put("bianhao", orderBean.getBianhao());

        Disposable disposable = HttpAPIWrapper.getInstance(HttpAPIWrapper.getOkHttpClient())
                .confirmRob(map)
                .subscribe(new Consumer<BaseBack>() {
                    @Override
                    public void accept(@NonNull BaseBack robBean) throws Exception {
                        progressDialog.hide();
                        if (robBean.status == 0) {
                            setRedPoint(Contains.indexMessageList.get(1) + 1, true);
                            refresh();
                            ToastUtil.showInfo(RobActivity.this, "抢单成功");
                        } else {
                            onError(robBean.status, robBean.MSG);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        ToastUtil.show(RobActivity.this, "加载失败");
                        KLog.e("onError" + throwable.toString());
                    }
                });
        disposables.add(disposable);
    }

    /**
     * 数据重组（没开子线程）
     *
     * @param data
     */
    private void regroup(RobBean data) {
        orderBeanList = data.getOrder();
        List<OrderDetailBean> orderDetailBeanList = data.getOrderDetail();
        String bianhao;
        List<OrderDetailBean> childList;
        for (OrderBean orderBean : orderBeanList) {
            bianhao = orderBean.getBianhao();
            childList = new ArrayList<>();
            Iterator<OrderDetailBean> it = orderDetailBeanList.iterator();
            while (it.hasNext()) {
                OrderDetailBean next = it.next();
                if (next.getDingdanBianhao().equals(bianhao)) {//根据订单编号匹配订单和对应商品集合数据
                    childList.add(next);
                    it.remove();
                }
            }
            orderBean.setOrderDetailList(childList);
        }
    }
}
