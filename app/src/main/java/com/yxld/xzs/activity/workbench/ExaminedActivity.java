package com.yxld.xzs.activity.workbench;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yxld.xzs.R;
import com.yxld.xzs.adapter.ExaminedAdapter;
import com.yxld.xzs.base.BaseActivity;
import com.yxld.xzs.contain.Contains;
import com.yxld.xzs.entity.ApproveBean;
import com.yxld.xzs.entity.ApproveListBean;
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
 * 已审批
 * Created by William on 2017/11/21.
 */

public class ExaminedActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;

    private View loadingView, notDataView;
    private ExaminedAdapter examinedAdapter;
    private List<ApproveBean> approveBeanList = new ArrayList<>();
    private int page;//分页数
    private int rows = 6;//每页加载数

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examined);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
        examinedAdapter = new ExaminedAdapter(approveBeanList);//因为adapter和普通adapter不一样，所以直接传数据给构造方法
        examinedAdapter.setEmptyView(loadingView);
        examinedAdapter.setLoadMoreView(new CustomLoadMoreView());
        // 滑动最后一个Item的时候回调onLoadMoreRequested方法
        examinedAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                initData(false);
            }
        },recyclerView);
        recyclerView.setAdapter(examinedAdapter);//绑定适配器
        //条目点击监听 不做recyclerView的 做adapter的监听
        examinedAdapter.setOnItemClickListener(this);
    }

    /**
     * 刷新数据
     */
    private void refresh() {
        page = 1;
        swipeLayout.setRefreshing(true);//显示加载进度条.要在主线程中执行
        examinedAdapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载，设置为true就是自动加载更多
        initData(true);
    }

    /**
     * 数据请求
     */
    private void initData(final boolean isRefresh) {
        Map<String, String> map = new HashMap<>();
        map.put("uuid", Contains.uuid);
        map.put("type", "0");
        map.put("page", page + "");
        map.put("rows", rows + "");
        Disposable disposable = HttpAPIWrapper.getInstance(HttpAPIWrapper.getOkHttpClient())
                .approveList(map)
                .subscribe(new Consumer<ApproveListBean>() {
                    @Override
                    public void accept(@NonNull ApproveListBean data) throws Exception {
                        swipeLayout.setRefreshing(false);
                        if (data.status == 0) {
                            if (isRefresh) {
                                setData(true, data);
                                examinedAdapter.setEnableLoadMore(true);//自动加载更多
                                swipeLayout.setRefreshing(false);//加载完成,不显示进度条
                            } else {
                                setData(false, data);
                            }
                        } else {
                            examinedAdapter.setEmptyView(notDataView);
                            examinedAdapter.setNewData(new ArrayList<ApproveBean>());
                            onError(data.status, data.MSG);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        examinedAdapter.setEmptyView(notDataView);
                        examinedAdapter.setNewData(new ArrayList<ApproveBean>());
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
    private void setData(boolean isRefresh, ApproveListBean data) {
        page++;
        if (null != data.getRows() && data.getRows().size() != 0) {
            approveBeanList = data.getRows();
        } else {
            approveBeanList.clear();
        }
        final int size = approveBeanList == null ? 0 : approveBeanList.size();
        // TODO: 2017/11/25 小红点 暂时注释
//        setRedPoint(data.getTotal());
        if (isRefresh) {
            if (size > 0) {
            } else {
                examinedAdapter.setEmptyView(notDataView);
            }
            examinedAdapter.setNewData(approveBeanList);//将首次数据塞入适配器的方法
        } else {
            if (size > 0) {
                examinedAdapter.addData(approveBeanList);//加载更多时直接将更多数据塞入适配器
            }
        }

        if (size < rows) {
            //第一页如果不够一页就不显示没有更多数据布局
            examinedAdapter.loadMoreEnd(isRefresh);//不传参数默认false,表示数据全部加载完毕没有更多数据  加载结束
            // 这里设置为false可用来显示没有更多数据item
        } else {
            examinedAdapter.loadMoreComplete();//加载完成（注意不是加载结束，而是本次数据加载结束并且还有下页数据）
        }
    }

    @Override
    public void onRefresh() {
        refresh();
    }

    @Override
    public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
//        Toast.makeText(this, Integer.toString(i), Toast.LENGTH_LONG).show();
    }
}
