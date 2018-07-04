package com.yxld.xzs.activity.pandian;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yxld.xzs.R;
import com.yxld.xzs.adapter.PanDianYiChangAdapter;
import com.yxld.xzs.base.BaseActivity;
import com.yxld.xzs.contain.Contains;
import com.yxld.xzs.entity.BaseBack;
import com.yxld.xzs.entity.PanDianBean;
import com.yxld.xzs.entity.PanDianYiChangBean;
import com.yxld.xzs.entity.PanDianYichangListBean;
import com.yxld.xzs.entity.WeiPanDianListBean;
import com.yxld.xzs.http.api.HttpAPIWrapper;
import com.yxld.xzs.utils.UIUtils;
import com.yxld.xzs.view.CustomLoadMoreView;
import com.yxld.xzs.view.YiChangDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * 异常列表
 * Created by William on 2018/5/28.
 */

public class PanDianYiChangListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.OnItemChildClickListener {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;
    @BindView(R.id.bt_confirm_yichang)
    Button btConfirmYichang;

    private YiChangDialog yiChangDialog;

    private View loadingView, notDataView;
    private PanDianYiChangAdapter panDianYiChangAdapter;
    private List<PanDianYiChangBean> panDianList = new ArrayList<>();
    private int page;//分页数
    private int rows = 3;//每页加载数
    private String pandianId;//盘点ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pandianyichang_list);
        ButterKnife.bind(this);

        pandianId = getIntent().getStringExtra("pandianId");

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
        panDianYiChangAdapter = new PanDianYiChangAdapter(panDianList);
        panDianYiChangAdapter.setEmptyView(loadingView);
        panDianYiChangAdapter.setLoadMoreView(new CustomLoadMoreView());
        // 滑动最后一个Item的时候回调onLoadMoreRequested方法
        panDianYiChangAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                initData(false);
            }
        }, recyclerView);
        recyclerView.setAdapter(panDianYiChangAdapter);//绑定适配器
        //条目子控件点击监听 不做recyclerView的 做adapter的监听
        panDianYiChangAdapter.setOnItemChildClickListener(this);
    }

    /**
     * 刷新数据
     */
    private void refresh() {
        page = 1;
        swipeLayout.setRefreshing(true);//显示加载进度条.要在主线程中执行
        panDianYiChangAdapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载，设置为true就是自动加载更多
        initData(true);
    }

    /**
     * 异常列表数据请求
     */
    private void initData(final boolean isRefresh) {
        // TODO: 2018/5/28 接口没调
        Map<String, String> map = new HashMap<>();
        map.put("uuid", Contains.uuid);
        map.put("pandianId", "");
        map.put("page", page + "");
        map.put("rows", rows + "");
        Disposable disposable = HttpAPIWrapper.getInstance(HttpAPIWrapper.getOkHttpClient())
                .yiChangPanDianList(map)
                .subscribe(new Consumer<PanDianYichangListBean>() {
                    @Override
                    public void accept(@NonNull PanDianYichangListBean data) throws Exception {
                        swipeLayout.setRefreshing(false);
                        if (data.status == 1) {
                            if (isRefresh) {
                                setData(true, data);
                                panDianYiChangAdapter.setEnableLoadMore(true);//自动加载更多
                                swipeLayout.setRefreshing(false);//加载完成,不显示进度条
                            } else {
                                setData(false, data);
                            }
                        } else {
                            panDianYiChangAdapter.setEmptyView(notDataView);
                            panDianYiChangAdapter.setNewData(new ArrayList<PanDianYiChangBean>());
                            onError(data.status, data.msg);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        panDianYiChangAdapter.setEmptyView(notDataView);
                        panDianYiChangAdapter.setNewData(new ArrayList<PanDianYiChangBean>());
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
    private void setData(boolean isRefresh, PanDianYichangListBean data) {
        page++;
        if (null != data.getData() && data.getData() .size() != 0) {
            panDianList = data.getData();
        } else {
            panDianList.clear();
        }
        final int size = panDianList == null ? 0 : panDianList.size();

        if (isRefresh) {
            if (size > 0) {
            } else {
                panDianYiChangAdapter.setEmptyView(notDataView);
            }
            panDianYiChangAdapter.setNewData(panDianList);//将首次数据塞入适配器的方法
        } else {
            if (size > 0) {
                panDianYiChangAdapter.addData(panDianList);//加载更多时直接将更多数据塞入适配器
            }
        }

        if (size < rows) {
            //第一页如果不够一页就不显示没有更多数据布局
            panDianYiChangAdapter.loadMoreEnd(isRefresh);//不传参数默认false,表示数据全部加载完毕没有更多数据  加载结束
            // 这里设置为false可用来显示没有更多数据item
        } else {
            panDianYiChangAdapter.loadMoreComplete();//加载完成（注意不是加载结束，而是本次数据加载结束并且还有下页数据）
        }
    }

    @OnClick(R.id.bt_confirm_yichang)
    public void onViewClicked() {
        finishYiChang();
    }

    /**
     * 结束异常确认
     */
    private void finishYiChang() {
        // TODO: 2018/5/28 接口没调
        Map<String, String> map = new HashMap<>();
        map.put("uuid", Contains.uuid);//登陆token
        map.put("pandianId", "");//盘点id

        String yichangId = "";

        Disposable disposable = HttpAPIWrapper.getInstance(HttpAPIWrapper.getOkHttpClient())
                .finishYiChang(map)
                .subscribe(new Consumer<BaseBack>() {
                    @Override
                    public void accept(@NonNull BaseBack data) throws Exception {
                        if (data.status == 1) {
                            Log.e("wh", data.msg);
                        } else {

                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {

                    }
                });
        disposables.add(disposable);
    }

    @Override
    public void onRefresh() {
        refresh();
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        Log.e("wh", "点击生效");
        yiChangDialog = new YiChangDialog(PanDianYiChangListActivity.this, 33+"");
        yiChangDialog.setCanceledOnTouchOutside(false);
        yiChangDialog.setConfirmListener(new YiChangDialog.OnConfirmListener() {
            @Override
            public void onCancel() {
                Log.e("wh", "取消");
            }

            @Override
            public void onConfirm(String num) {
                if (!TextUtils.isEmpty(num.trim())) {
                    Log.e("wh", "确定" + num);
                    confirmYiChang(num);
                }
            }
        });
        yiChangDialog.show();
    }

    /**
     * 确认单条异常
     * @param num 修改后数字
     */
    private void confirmYiChang(String num) {
        // TODO: 2018/5/28 接口没调
        Map<String, String> map = new HashMap<>();
        map.put("uuid", Contains.uuid);//登陆token
        map.put("detailKucunDetailId", "");//int 盘点库存详情id
        map.put("detailPandianId", "");//int	盘点id
        map.put("detailQianShuliang", "");//int	盘点详情前数量
        map.put("detailHouShuliang", "");//int	盘点详情后数量
        map.put("detailChaShuliang", "");//int	盘点详情数量差
        map.put("detailId", "");//int	盘点详情id
        map.put("yichangId", "");//int	异常id
        String yichangId = "";

        Disposable disposable = HttpAPIWrapper.getInstance(HttpAPIWrapper.getOkHttpClient())
                .confirmYiChang(yichangId,map)
                .subscribe(new Consumer<BaseBack>() {
                    @Override
                    public void accept(@NonNull BaseBack data) throws Exception {
                        swipeLayout.setRefreshing(false);
                        if (data.status == 1) {
                            refresh();//刷新页面和数据
                        } else {

                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {

                    }
                });
        disposables.add(disposable);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
//            onBackPressed();//在确认盘点接口请求之后调用
            //overridePendingTransition(R.anim.translate_left_to_center, R.anim.translate_center_to_right);

            confirmPanDian();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 确认盘点
     */
    private void confirmPanDian() {
        Map<String, String> map = new HashMap<>();
        map.put("uuid", Contains.uuid);//登陆token
        map.put("pandianId", "");//int 盘点id

        Disposable disposable = HttpAPIWrapper.getInstance(HttpAPIWrapper.getOkHttpClient())
                .confirmPanDian(pandianId,map)
                .subscribe(new Consumer<BaseBack>() {
                    @Override
                    public void accept(@NonNull BaseBack data) throws Exception {
                        swipeLayout.setRefreshing(false);
                        if (data.status == 1) {
                            refresh();//刷新页面和数据
                        } else {

                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {

                    }
                });
        disposables.add(disposable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (yiChangDialog != null) {
            yiChangDialog = null;
        }
    }
}
