package com.yxld.xzs.activity.Income;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.socks.library.KLog;
import com.yxld.xzs.R;
import com.yxld.xzs.adapter.JieSuanAdapter;
import com.yxld.xzs.base.BaseActivity;
import com.yxld.xzs.contain.Contains;
import com.yxld.xzs.entity.YiJieSuanBean;
import com.yxld.xzs.http.api.HttpAPIWrapper;
import com.yxld.xzs.utils.ToastUtil;
import com.yxld.xzs.utils.UIUtils;

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
 * @author xlei
 * @Date 2017/11/22.
 * <p>
 * 已结算明细和未结算明细公用同一个界面
 */

public class YiJieSuanActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener, View.OnClickListener {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout mSwipeLayout;
    /**
     * 进入的类型
     * 1表示已结算2表示未结算
     */
    public static final String ENTER_TYPE = "enter_type";
    private int type;
    /**
     * 页数page
     */
    private int page;
    /**
     * 一次加载多少条rows
     */
    private int rows = 5;
    /**
     * 总共多少条
     */
    private int totleNum;
    private JieSuanAdapter mAdapter;
    private List<YiJieSuanBean.DataBean> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yijiesuan);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        UIUtils.configSwipeRefreshLayoutColors(mSwipeLayout);
        type = getIntent().getIntExtra(ENTER_TYPE, 1);
        if (type == 1) {
            toolbar.setTitle("已结算明细");
        } else {
            toolbar.setTitle("未结算明细");
        }
        if (mList == null) {
            mList = new ArrayList<>();
        }
        page = 1;
        totleNum = 0;
        mSwipeLayout.setOnRefreshListener(this);
        mAdapter = new JieSuanAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setNestedScrollingEnabled(false);
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);
        if (type == 1) {
            loadYiJieSuanFromServer();
        } else {
            loadWeiJieSuanFromServer();
        }
    }

    private void loadYiJieSuanFromServer() {
        Map<String, String> map = new HashMap<>(16);
        map.put("uuid", Contains.uuid);
        map.put("page", page + "");
        map.put("rows", rows + "");
        Disposable subscribe = HttpAPIWrapper.getInstance().yiJieSuan(map).subscribe(new Consumer<YiJieSuanBean>() {
            @Override
            public void accept(@NonNull YiJieSuanBean baseBack) throws Exception {
                mAdapter.loadMoreComplete();
                if (mSwipeLayout != null) {
                    mSwipeLayout.setRefreshing(false);
                }
                if (baseBack.status != 0) {
                    onError(baseBack.status, baseBack.MSG);
                    return;
                }
                if (page == 1) {
                    mList.clear();
                }
                totleNum = baseBack.getTotal();
                mList.addAll(baseBack.getRows());
                if (mList.size() == 0) {
                    mNoData = true;
                    refresh();
                }
                if (mList.size() < totleNum) {
                    page++;
                }
                mAdapter.setNewData(mList);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                if (mSwipeLayout != null) {
                    mSwipeLayout.setRefreshing(false);
                }
                KLog.e("onError" + throwable.toString());
            }
        });
        disposables.add(subscribe);

    }

    private void loadWeiJieSuanFromServer() {
        Map<String, String> map = new HashMap<>(16);
        map.put("uuid", Contains.uuid);
        map.put("page", page + "");
        map.put("rows", rows + "");
        Disposable subscribe = HttpAPIWrapper.getInstance().weiJieSuan(map).subscribe(new Consumer<YiJieSuanBean>() {
            @Override
            public void accept(@NonNull YiJieSuanBean baseBack) throws Exception {
                mAdapter.loadMoreComplete();
                if (mSwipeLayout != null) {
                    mSwipeLayout.setRefreshing(false);
                }
                if (baseBack.status != 0) {
                    onError(baseBack.status, baseBack.MSG);
                    return;
                }
                if (page == 1) {
                    mList.clear();
                }
                totleNum = baseBack.getTotal();
                mList.addAll(baseBack.getRows());
                if (mList.size() == 0) {
                    mNoData = true;
                    refresh();
                }
                if (mList.size() < totleNum) {
                    page++;
                }
                mAdapter.setNewData(mList);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                if (mSwipeLayout != null) {
                    mSwipeLayout.setRefreshing(false);
                }
                KLog.e("onError" + throwable.toString());
            }
        });
        disposables.add(subscribe);

    }

    @Override
    public void onRefresh() {
        page = 1;
        totleNum = 0;
        if (type == 1) {
            loadYiJieSuanFromServer();
        } else {
            loadWeiJieSuanFromServer();
        }
    }

    private View loadingView, notDataView;
    private boolean mNoData;

    private void refresh() {
        loadingView = getLayoutInflater().inflate(R.layout.loading_view, (ViewGroup) mRecyclerView.getParent(), false);
        notDataView = getLayoutInflater().inflate(R.layout.empty_view, (ViewGroup) mRecyclerView.getParent(), false);
        notDataView.setOnClickListener(this);
        mAdapter.setEmptyView(loadingView);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mNoData) {
                    mAdapter.setEmptyView(notDataView);
                    mNoData = false;
                }
            }
        }, 1000);
    }

    @Override
    public void onClick(View v) {
        if (type == 1) {
            loadYiJieSuanFromServer();
        } else {
            loadWeiJieSuanFromServer();
        }
    }

    @Override
    public void onLoadMoreRequested() {
        if (mList.size() < rows || mList.size() >= totleNum) {
            mAdapter.loadMoreEnd();
            return;
        }
        if (type == 1) {
            loadYiJieSuanFromServer();
        } else {
            loadWeiJieSuanFromServer();
        }
    }


}
