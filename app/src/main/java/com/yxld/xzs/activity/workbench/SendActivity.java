package com.yxld.xzs.activity.workbench;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.orhanobut.logger.Logger;
import com.socks.library.KLog;
import com.tbruyelle.rxpermissions.Permission;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.yxld.xzs.R;
import com.yxld.xzs.adapter.SendnAdapter;
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
import rx.functions.Action1;

/**
 * 待送达
 * Created by William on 2017/11/16.
 */

public class SendActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;

    private List<OrderBean> orderBeanList;
    private int page;//分页数
    private int rows = 6;//每页加载数
    private View loadingView;//正在加载
    private View notDataView;//无数据
    private View errorView;//请求错误

    private SendnAdapter sendAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rob);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initView();
        initAdapter();
        refresh();
    }

    /**
     * 刷新数据
     */
    private void refresh() {
        page = 1;
        swipeLayout.setRefreshing(true);//显示加载进度条.要在主线程中执行
        sendAdapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载，设置为true就是自动加载更多
        initData(true);
    }


    /**
     * 数据请求
     */
    private void initData(final boolean isRefresh) {
        Map<String, String> map = new HashMap<>();
        map.put("uuid", Contains.uuid);
        map.put("page", page + "");
        map.put("rows", rows + "");
        Disposable disposable = HttpAPIWrapper.getInstance(HttpAPIWrapper.getOkHttpClient())
                .sendList(map)
                .subscribe(new Consumer<RobBean>() {
                    @Override
                    public void accept(@NonNull RobBean robBean) throws Exception {
                        swipeLayout.setRefreshing(false);
                        if (robBean.status == 0) {
                            if (isRefresh) {
                                setData(true, robBean);
                                sendAdapter.setEnableLoadMore(true);//自动加载更多
                                swipeLayout.setRefreshing(false);//加载完成,不显示进度条
                            } else {
                                setData(false, robBean);
                            }
                        } else {
                            sendAdapter.setEmptyView(notDataView);
                            sendAdapter.setNewData(new ArrayList<OrderBean>());
                            onError(robBean.status, robBean.MSG);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        sendAdapter.setEmptyView(notDataView);
                        sendAdapter.setEnableLoadMore(true);//自动加载更多
                        swipeLayout.setRefreshing(false);//加载完成,不显示进度条
                    }
                });
        disposables.add(disposable);
    }

    /**
     * 设置数据
     *
     * @param isRefresh
     * @param data
     */
    private void setData(boolean isRefresh, RobBean data) {
        regroup(data);
        page++;
        final int size = orderBeanList == null ? 0 : orderBeanList.size();
        setRedPoint(data.getTotal());
        if (isRefresh) {
            if (size > 0) {

                Log.e("wh", "1");
            } else {
                sendAdapter.setEmptyView(notDataView);
                Log.e("wh", "2");
            }
            sendAdapter.setNewData(orderBeanList);//将首次数据塞入适配器的方法
        } else {
            if (size > 0) {
                sendAdapter.addData(orderBeanList);//加载更多时直接将更多数据塞入适配器
            }
        }

        if (size < rows) {
            Log.e("wh", "3");
            //第一页如果不够一页就不显示没有更多数据布局
            sendAdapter.loadMoreEnd(isRefresh);//不传参数默认false,表示数据全部加载完毕没有更多数据  加载结束
            // 这里设置为false可用来显示没有更多数据item
        } else {
            sendAdapter.loadMoreComplete();//加载完成（注意不是加载结束，而是本次数据加载结束并且还有下页数据）
        }
    }

    /**
     * 设置小红点数据
     *
     * @param size
     */
    private void setRedPoint(int size) {
        if (Contains.indexMessageList != null && Contains.indexMessageList.size() > 0) {
            Contains.indexMessageList.set(2, size);
        }
    }

    /**
     * 数据重组（没开子线程）
     *
     * @param data
     */
    private void regroup(RobBean data) {
        orderBeanList = data.getOrder();
        List<OrderDetailBean> orderDetailBeanList = data.getOrderDetail();
        KLog.e("结果", "5 " + data.getOrder().size() + " " + data.getOrderDetail().size());
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

    /**
     * 初始化适配器
     */
    private void initAdapter() {
        sendAdapter = new SendnAdapter();
        sendAdapter.setEmptyView(loadingView);
        sendAdapter.setLoadMoreView(new CustomLoadMoreView());
        // 滑动最后一个Item的时候回调onLoadMoreRequested方法
        sendAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                initData(false);
            }
        }, recyclerView);
        recyclerView.setAdapter(sendAdapter);//绑定适配器
        //条目点击监听 不做recyclerView的 做adapter的监听
        sendAdapter.setOnItemClickListener(this);
        sendAdapter.setOnItemChildClickListener(this);
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

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        final OrderBean orderBean = (OrderBean) baseQuickAdapter.getItem(i);
        final String shouhuoDianhua = orderBean.getShouhuoDianhua();
        switch (view.getId()) {
            case R.id.bt_phone:
                new AlertView.Builder().setContext(this)
                        .setTitle("是否拨打电话？")
                        .setCancelText("取消")
                        .setOthers(new String[]{"确定"})
                        .setStyle(AlertView.Style.ActionSheet)
                        .setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(Object o, int position) {
                                if (position == 0) {
                                    makeCall(shouhuoDianhua);
                                }
                            }
                        })
                        .build().setCancelable(true).show();
                break;
            case R.id.bt_confirm:
//                ToastUtil.show(this, "确认送达");
                new AlertView.Builder().setTitle("是否确定送达？")
                        .setStyle(AlertView.Style.Alert)
                        .setOthers(new String[]{"取消", "确定"})
                        .setContext(this)
                        .setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(Object o, int position) {
                                if (position == 1) {
                                    confirmSend(orderBean);
                                }
                            }
                        })
                        .build()
                        .show();
                break;
            default:
                break;
        }
    }

    /**
     * 确认送达
     *
     * @param orderBean
     */
    private void confirmSend(OrderBean orderBean) {
        progressDialog.show();
        Map<String, String> map = new HashMap<>();
        map.put("uuid", Contains.uuid);
        map.put("bianhao", orderBean.getBianhao());
        Disposable disposable = HttpAPIWrapper.getInstance(HttpAPIWrapper.getOkHttpClient())
                .confirmSend(map)
                .subscribe(new Consumer<BaseBack>() {
                    @Override
                    public void accept(@NonNull BaseBack robBean) throws Exception {
                        progressDialog.hide();
                        if (robBean.status == 0) {
                            refresh();
                            ToastUtil.showInfo(SendActivity.this, "已送达");
                        } else {
                            onError(robBean.status, robBean.MSG);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        ToastUtil.show(SendActivity.this, "加载失败");
                    }
                });
        disposables.add(disposable);
    }


    private void makeCall(final String shouhuoDianhua) {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.setLogging(true);
        rxPermissions.requestEach(Manifest.permission.CALL_PHONE)
                .subscribe(new Action1<Permission>() {
                    @Override
                    public void call(Permission permission) {
                        Logger.d("Permission result " + permission);
                        if (permission.granted) {
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_CALL);
                            //url:统一资源定位符
                            //uri:统一资源标示符（更广）
                            intent.setData(Uri.parse("tel:" + shouhuoDianhua));
                            //开启系统拨号器
                            startActivity(intent);
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // Denied permission without ask never again
                            Toast.makeText(SendActivity.this, "没有访问也没有拒绝", Toast.LENGTH_SHORT)
                                    .show();
                        } else {
                            // Denied permission with ask never again
                            // Need to go to the settings
                            Toast.makeText(SendActivity.this, "没有权限,您不能打电话", Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                });
    }
}
