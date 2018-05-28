package com.yxld.xzs.activity.workbench;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yxld.xzs.R;
import com.yxld.xzs.adapter.NightWarehouseAdapter;
import com.yxld.xzs.base.BaseActivity;
import com.yxld.xzs.contain.Contains;
import com.yxld.xzs.entity.NightWarehouseBean;
import com.yxld.xzs.entity.NightWarehouseListBean;
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
 * 夜间出库单列表
 * Created by William on 2017/11/28.
 */

public class NightWarehouseListActivity extends BaseActivity implements BaseQuickAdapter
        .OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;

    private NightWarehouseAdapter nightWarehouseAdapter;

    private List<NightWarehouseBean> nightWarehouseList;
    private int page;//分页数
    private int rows = 6;//每页加载数

    private View loadingView;//正在加载
    private View notDataView;//无数据
    private View errorView;//请求错误

    private final int REQUESTCODE_DETAIL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_night_warehouse);
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
    private void setData(boolean isRefresh, NightWarehouseListBean data) {
        if (null!=data.getRows()&&data.getRows().size()!=0) {
            nightWarehouseList = data.getRows();
        } else {
            nightWarehouseList = new ArrayList<>();
        }

        page++;
        final int size = nightWarehouseList == null ? 0 : nightWarehouseList.size();
//        setRedPoint(data.getTotal(), false);
        if (isRefresh) {
            if (size > 0) {
                nightWarehouseAdapter.setNewData(nightWarehouseList);//将首次数据塞入适配器的方法
            } else {
                nightWarehouseAdapter.setEmptyView(notDataView);
                nightWarehouseAdapter.setNewData(new ArrayList<NightWarehouseBean>());
            }
        } else {
            if (size > 0) {
                nightWarehouseAdapter.addData(nightWarehouseList);//加载更多时直接将更多数据塞入适配器
            }
        }

        if (size < rows) {
            //第一页如果不够一页就不显示没有更多数据布局
            nightWarehouseAdapter.loadMoreEnd(isRefresh);//不传参数默认false,表示数据全部加载完毕没有更多数据  加载结束
            // 这里设置为false可用来显示没有更多数据item
        } else {
            nightWarehouseAdapter.loadMoreComplete();//加载完成（注意不是加载结束，而是本次数据加载结束并且还有下页数据）
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
        Disposable disposable = HttpAPIWrapper.getInstance(HttpAPIWrapper.getOkHttpClient())
                .nightWarehouseList(map)
                .subscribe(new Consumer<NightWarehouseListBean>() {
                    @Override
                    public void accept(@NonNull NightWarehouseListBean data) throws Exception {
                        swipeLayout.setRefreshing(false);
                        if (data.status == 0) {
                            if (isRefresh) {
                                setData(true, data);
                                nightWarehouseAdapter.setEnableLoadMore(true);//自动加载更多
                                swipeLayout.setRefreshing(false);//加载完成,不显示进度条
                            } else {
                                setData(false, data);
                            }
                        } else {
                            nightWarehouseAdapter.setEmptyView(notDataView);
                            nightWarehouseAdapter.setNewData(new ArrayList<NightWarehouseBean>());
                            onError(data.status, data.MSG);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        nightWarehouseAdapter.setEmptyView(notDataView);
                        nightWarehouseAdapter.setNewData(new ArrayList<NightWarehouseBean>());
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
        nightWarehouseAdapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载，设置为true就是自动加载更多
        initData(true);
    }

    /**
     * 初始化适配器
     */
    private void initAdapter() {
        nightWarehouseAdapter = new NightWarehouseAdapter();
        nightWarehouseAdapter.setEmptyView(loadingView);
        nightWarehouseAdapter.setLoadMoreView(new CustomLoadMoreView());
        // 滑动最后一个Item的时候回调onLoadMoreRequested方法
        nightWarehouseAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                initData(false);
            }
        }, recyclerView);
        recyclerView.setAdapter(nightWarehouseAdapter);//绑定适配器
        //条目点击监听 不做recyclerView的 做adapter的监听
        nightWarehouseAdapter.setOnItemClickListener(this);
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
        NightWarehouseBean item = (NightWarehouseBean) baseQuickAdapter.getItem(i);
        Intent intent = new Intent(this, NightWarehouseDetailActivity.class);
        intent.putExtra("nightWarehouseBean",item);
        startActivityForResult(intent,REQUESTCODE_DETAIL);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUESTCODE_DETAIL:
                if (resultCode == RESULT_OK) {
                    refresh();
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }
}
