package com.yxld.xzs.activity.workbench;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tbruyelle.rxpermissions.Permission;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.yxld.xzs.R;
import com.yxld.xzs.adapter.RimSendAdapter;
import com.yxld.xzs.base.BaseFragment;
import com.yxld.xzs.contain.Contains;
import com.yxld.xzs.entity.RimOrderBean;
import com.yxld.xzs.entity.RimOrderListBean;
import com.yxld.xzs.http.api.HttpAPIWrapper;
import com.yxld.xzs.utils.ToastUtil;
import com.yxld.xzs.utils.UIUtils;
import com.yxld.xzs.view.CustomLoadMoreView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import rx.functions.Action1;

/**
 * Created by William on 2018/1/5.
 */

public class RimSendFragment extends BaseFragment implements BaseQuickAdapter.OnItemChildClickListener, OnRefreshListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeLayout)
    SmartRefreshLayout swipeLayout;
    Unbinder unbinder;

    //标志已经初始化完成
    private boolean isPrepared;

    private View loadingView;//正在加载
    private View notDataView;//无数据
    private View errorView;//请求错误

    private int page;//分页数
    private int rows = 6;//每页加载数
    private int orderStatus = 3;//订单状态码
    private RimSendAdapter rimSendAdapter;
    private List<RimOrderBean> orderBeanList;
    private CompositeDisposable disposables = new CompositeDisposable();
    private boolean isFirst = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rim_send, null);
        unbinder = ButterKnife.bind(this, view);
        isPrepared = true;
        initView();
        initAdapter();
        lazyLoad();

        return view;
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        refresh();
    }

    private void refresh() {
        page = 1;
       // swipeLayout.setRefreshing(true);//显示加载进度条.要在主线程中执行
        rimSendAdapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载，设置为true就是自动加载更多
        initData(true);
    }

    /**
     * 数据请求
     *
     * @param isRefresh
     */
    private void initData(final boolean isRefresh) {
        Map<String, String> map = new HashMap<>();
        map.put("uuId", Contains.uuid);
        map.put("page", page + "");
        map.put("rows", rows + "");
        map.put("orderStatus", orderStatus + "");
        Disposable disposable = HttpAPIWrapper.getInstance(HttpAPIWrapper.getOkHttpClient())
                .getRimRobList(map)
                .subscribe(new Consumer<RimOrderListBean>() {
                    @Override
                    public void accept(@NonNull RimOrderListBean robBean) throws Exception {
                      //  swipeLayout.setRefreshing(false);
                        if (robBean.status == 1) {
                            if (isRefresh) {
                                setData(true, robBean);
                                rimSendAdapter.setEnableLoadMore(true);//自动加载更多
                             //   swipeLayout.setRefreshing(false);//加载完成,不显示进度条
                            } else {
                                setData(false, robBean);
                            }
                        } else {
                            rimSendAdapter.setEmptyView(notDataView);
                            rimSendAdapter.setNewData(new ArrayList<RimOrderBean>());
                            onError(robBean.status, robBean.msg);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        rimSendAdapter.setEmptyView(notDataView);
                        rimSendAdapter.setEnableLoadMore(true);//自动加载更多
                        //swipeLayout.setRefreshing(false);//加载完成,不显示进度条
                        ToastUtil.show(getActivity(), "加载失败");
                    }
                });
        disposables.add(disposable);
    }

    private void setData(boolean isRefresh, RimOrderListBean data) {

        if (null != data.getData() && data.getData().size() != 0) {
            orderBeanList = data.getData();
        } else {
            orderBeanList = new ArrayList<>();
        }

        page++;
        final int size = orderBeanList == null ? 0 : orderBeanList.size();
//        setRedPoint(data.getTotal(), false);
        if (isRefresh) {
            if (size > 0) {

            } else {
                rimSendAdapter.setEmptyView(notDataView);
            }
            rimSendAdapter.setNewData(orderBeanList);//将首次数据塞入适配器的方法
        } else {
            if (size > 0) {
                rimSendAdapter.addData(orderBeanList);//加载更多时直接将更多数据塞入适配器
            }
        }

        if (size < rows) {
            //第一页如果不够一页就不显示没有更多数据布局
            rimSendAdapter.loadMoreEnd(isRefresh);//不传参数默认false,表示数据全部加载完毕没有更多数据  加载结束
            // 这里设置为false可用来显示没有更多数据item
        } else {
            rimSendAdapter.loadMoreComplete();//加载完成（注意不是加载结束，而是本次数据加载结束并且还有下页数据）
        }
    }

    private void initAdapter() {
        rimSendAdapter = new RimSendAdapter();
        rimSendAdapter.setEmptyView(loadingView);
        rimSendAdapter.setLoadMoreView(new CustomLoadMoreView());//设置自定义加载布局
        // 滑动最后一个Item的时候回调onLoadMoreRequested方法
        rimSendAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                initData(false);
            }
        }, recyclerView);
//      robAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);//设置item加载动画
//      mAdapter.setPreLoadNumber(N);// 当列表滑动到倒数第N个Item的时候(默认是1)回调onLoadMoreRequested方法
        recyclerView.setAdapter(rimSendAdapter);//绑定适配器
        //条目点击监听 不做recyclerView的 做adapter的监听
        rimSendAdapter.setOnItemChildClickListener(this);
//        rimRobAdapter.setOnItemClickListener(this);
    }

    private void initView() {
        swipeLayout.setRefreshHeader(new ClassicsHeader(getActivity()));
        swipeLayout.setOnRefreshListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);

        loadingView = getActivity().getLayoutInflater().inflate(R.layout.loading_view,
                (ViewGroup) recyclerView
                        .getParent(), false);
        notDataView = getActivity().getLayoutInflater().inflate(R.layout.empty_view, (ViewGroup)
                recyclerView
                        .getParent(), false);
        errorView = getActivity().getLayoutInflater().inflate(R.layout.error_view, (ViewGroup)
                recyclerView
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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (!disposables.isDisposed()) {
            disposables.dispose();
        }
    }


    @Override
    public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        final RimOrderBean orderBean = (RimOrderBean) baseQuickAdapter.getItem(i);
        final String userPhone = orderBean.getOrderUserPhone();//客户电话
        final String businessPhone = orderBean.getBusinessPhone();//客户电话

        switch (view.getId()) {
            case R.id.bt_phone_buy:
                new AlertView.Builder().setContext(getActivity())
                        .setTitle("是否拨打电话 " + userPhone + " ？")
                        .setCancelText("取消")
                        .setOthers(new String[]{"确定"})
                        .setStyle(AlertView.Style.ActionSheet)
                        .setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(Object o, int position) {
                                if (position == 0) {
                                    makeCall(userPhone);
                                }
                            }
                        })
                        .build().setCancelable(true).show();
                break;
            case R.id.bt_phone_sell:
                new AlertView.Builder().setContext(getActivity())
                        .setTitle("是否拨打电话 " + businessPhone + " ？")
                        .setCancelText("取消")
                        .setOthers(new String[]{"确定"})
                        .setStyle(AlertView.Style.ActionSheet)
                        .setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(Object o, int position) {
                                if (position == 0) {
                                    makeCall(businessPhone);
                                }
                            }
                        })
                        .build().setCancelable(true).show();
                break;
            default:
                break;
        }
    }

    private void makeCall(final String shouhuoDianhua) {
        RxPermissions rxPermissions = new RxPermissions(getActivity());
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
                            Toast.makeText(getActivity(), "没有访问也没有拒绝", Toast.LENGTH_SHORT)
                                    .show();
                        } else {
                            // Denied permission with ask never again
                            // Need to go to the settings
                            Toast.makeText(getActivity(), "没有权限,您不能打电话", Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                });
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        refreshlayout.finishRefresh();
        refresh();
    }
}
