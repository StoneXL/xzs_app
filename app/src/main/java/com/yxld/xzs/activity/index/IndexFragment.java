package com.yxld.xzs.activity.index;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.socks.library.KLog;
import com.yxld.xzs.R;
import com.yxld.xzs.activity.Repair.RepairActivity;
import com.yxld.xzs.activity.patrol.PatrolManagerActivity;
import com.yxld.xzs.activity.workbench.DeliveryActivity;
import com.yxld.xzs.activity.workbench.NightOrderListActivity;
import com.yxld.xzs.activity.workbench.RobActivity;
import com.yxld.xzs.activity.workbench.SendActivity;
import com.yxld.xzs.adapter.IndexAdapter;
import com.yxld.xzs.base.BaseFragment;
import com.yxld.xzs.contain.Contains;
import com.yxld.xzs.entity.IndexMessageBean;
import com.yxld.xzs.http.api.HttpAPIWrapper;
import com.yxld.xzs.utils.SPUtil;
import com.yxld.xzs.utils.UIUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

import static com.yxld.xzs.activity.index.HomeActivity.REQUESTCODE_DELIVERY;
import static com.yxld.xzs.activity.index.HomeActivity.REQUESTCODE_ROB;
import static com.yxld.xzs.activity.index.HomeActivity.REQUESTCODE_SEND;

/**
 * @author xlei
 * @Date 2017/11/14.
 */

public class IndexFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout mSwipeLayout;
    Unbinder unbinder;
    @BindView(R.id.ly_empty_data)
    LinearLayout mLyEmptyData;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    private IndexAdapter mAdapter;
    private List<IndexMessageBean.DataBean> mDataBeenList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);

        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_index, null);
        unbinder = ButterKnife.bind(this, view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setNestedScrollingEnabled(false);
        mSwipeLayout.setOnRefreshListener(this);
        mRefreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mRefreshLayout.finishRefresh();
                initData();

            }
        });
        UIUtils.configSwipeRefreshLayoutColors(mSwipeLayout);
        initData();
        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onOutLogin(String outlogin) {
        KLog.i("evenbus消息:" + outlogin);
        if (outlogin.equals("刷新数据")) {
            KLog.i("阿里推送：刷新数据");
            onRefresh();
        }
    }

    private void initData() {
        if (mDataBeenList == null) {
            mDataBeenList = new ArrayList<>();
        }
        mAdapter = new IndexAdapter(mDataBeenList);
        mRecyclerView.setAdapter(mAdapter);
        //初始化消息的数据
        Contains.indexMessageList.set(0, 0);
        Contains.indexMessageList.set(1, 0);
        Contains.indexMessageList.set(2, 0);
        setEvent();
        loadDataFromServer();

    }

    private void setEvent() {
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                Intent intent = new Intent();
                IndexMessageBean.DataBean dataBean = (IndexMessageBean.DataBean) baseQuickAdapter.getData().get(i);
                switch (dataBean.getType()) {
                    //待抢单
                    case 1:
                        intent.setClass(getActivity(), RobActivity.class);
                        startActivityForResult(intent, REQUESTCODE_ROB);
                        break;
                    //待取货
                    case 2:
                        intent.setClass(getActivity(), DeliveryActivity.class);
                        startActivityForResult(intent, REQUESTCODE_DELIVERY);
                        break;
                    //待送达
                    case 3:
                        intent.setClass(getActivity(), SendActivity.class);
                        startActivityForResult(intent, REQUESTCODE_SEND);
                        break;
                    case 4:
                        //报修申请
                        SPUtil spUtil = new SPUtil(getActivity());
                        spUtil.put(SPUtil.KEY_BAOXIU, 1);
                        intent.setClass(getActivity(), RepairActivity.class);
                        startActivityForResult(intent, REQUESTCODE_SEND);
                        break;
                    //巡检任务
                    case 5:
                        intent.setClass(getActivity(), PatrolManagerActivity.class);
                        startActivityForResult(intent, REQUESTCODE_SEND);
                        break;
                    //夜间订单
                    case 6:
                        intent.setClass(getActivity(), NightOrderListActivity.class);
                        startActivityForResult(intent, REQUESTCODE_SEND);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void loadDataFromServer() {
        Map<String, String> map = new HashMap<>(16);
        map.put("uuid", Contains.uuid);
        HttpAPIWrapper.getInstance().shouYeXiaoXi(map).subscribe(new Consumer<IndexMessageBean>() {
            @Override
            public void accept(@NonNull IndexMessageBean baseBack) throws Exception {
                if (mSwipeLayout != null) {
                    mSwipeLayout.setRefreshing(false);
                }
                if (baseBack.status != 0) {
                    onError(baseBack.status, baseBack.MSG);
                    return;
                }
                mDataBeenList = baseBack.getRows();
                //显示空页面
                if (mDataBeenList.size() == 0) {
                    mLyEmptyData.setVisibility(View.VISIBLE);
                    mRecyclerView.setVisibility(View.GONE);
                    Contains.indexMessageList.set(0, 0);
                    Contains.indexMessageList.set(1, 0);
                    Contains.indexMessageList.set(2, 0);
                } else {
                    mLyEmptyData.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                }
                mAdapter.setNewData(mDataBeenList);
                setIndexMessageList(mDataBeenList);

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
    }

    private void setIndexMessageList(List<IndexMessageBean.DataBean> dataBeenList) {
        for (int i = 0; i < dataBeenList.size(); i++) {
            if (dataBeenList.get(i).getType() == 1 && dataBeenList.get(i).getTotal() != 0) {
                Contains.indexMessageList.set(0, dataBeenList.get(i).getTotal());
            }
            if (dataBeenList.get(i).getType() == 2 && dataBeenList.get(i).getTotal() != 0) {
                Contains.indexMessageList.set(1, dataBeenList.get(i).getTotal());
            }
            if (dataBeenList.get(i).getType() == 3 && dataBeenList.get(i).getTotal() != 0) {
                Contains.indexMessageList.set(2, dataBeenList.get(i).getTotal());
            }
        }
        KLog.i("Contains.indexMessageList-----index>" + Contains.indexMessageList.size() + "valus" +
                Contains.indexMessageList.get(0) + "-" + Contains.indexMessageList.get(1) + "-"
                + Contains.indexMessageList.get(2));
    }

    @Override
    public void onRefresh() {
        loadDataFromServer();
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        KLog.e("onDestroy");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUESTCODE_ROB:
                onRefresh();
                break;
            case REQUESTCODE_DELIVERY:
                onRefresh();
                break;
            case REQUESTCODE_SEND:
                onRefresh();
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

}
