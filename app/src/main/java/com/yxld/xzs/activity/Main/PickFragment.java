package com.yxld.xzs.activity.Main;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.yxld.xzs.MainActivity;
import com.yxld.xzs.R;
import com.yxld.xzs.activity.workbench.NightOrderListActivity;
import com.yxld.xzs.adapter.PickAdapter;
import com.yxld.xzs.entity.AppHome;
import com.yxld.xzs.entity.AppOrder;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by yishangfei on 2016/10/10 0010.
 * 待取货界面
 */
public class PickFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout mPickRefresh;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerview;

    private boolean mNoData;
    private AppHome appHome;
    private PickAdapter pickAdapter;
    private View loadingView, notDataView;//缓存Fragmen  加载view
    private MainActivity mMianActivity;
    private NightOrderListActivity nightOrderListActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof MainActivity){
            mMianActivity = (MainActivity) context;
        }else if (context instanceof NightOrderListActivity) {
            nightOrderListActivity = (NightOrderListActivity) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement PatrolBadgeCallback");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_send, null);
        ButterKnife.bind(this, view);
//        initView();
        return view;
    }

    private void initView() {
        loadingView = getActivity().getLayoutInflater().inflate(R.layout.loading_view, (ViewGroup) recyclerview.getParent(), false);
        notDataView = getActivity().getLayoutInflater().inflate(R.layout.empty_view, (ViewGroup) recyclerview.getParent(), false);
        mPickRefresh.setOnRefreshListener(this);
        mPickRefresh.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        Pick();
        recyclerview.addOnItemTouchListener(listener);
    }

    //网络请求
    private void Pick() {
        /*ServiceFactory.httpService()
                .pick(Contains.appLogin.getAdminId(),Contains.appLogin.getAdminXiangmuId())
                .compose(new DorderTransformer<AppHome>())
                .subscribe(new RxSubscriber<AppHome>(getActivity()) {
                               @Override
                               public void onNext(AppHome home) {
                                   BadgedTabCustomView customView = mMianActivity.tabLayout.getTabCustomViewAt(1);
                                   if (mPickRefresh != null) {
                                       mPickRefresh.setRefreshing(false);
                                   }
                                   if (customView != null) {
                                       customView.setTabCount(home.getRow().size());
                                   }
                                   Collections.reverse(home.getRow()); // 倒序排列
                                   appHome = home;
                                   pickAdapter = new PickAdapter(home.getRow(),home.getSaleList());
                                   if (home.getRow().size() == 0) {
                                       mNoData = true;
                                       refresh();
                                   }
                                   recyclerview.setHasFixedSize(true);
                                   recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
                                   recyclerview.setAdapter(pickAdapter);
                               }

                               @Override
                               protected void onError(ApiException ex) {
                                   super.onError(ex);
                                   if (mPickRefresh != null) {
                                       mPickRefresh.setRefreshing(false);
                                   }
                               }
                           }
                );*/
    }

    @Override
    public void onRefresh() {
        Pick();
    }

    public void refresh() {
        loadingView = getActivity().getLayoutInflater().inflate(R.layout.loading_view, (ViewGroup) recyclerview.getParent(), false);
        notDataView = getActivity().getLayoutInflater().inflate(R.layout.empty_view, (ViewGroup) recyclerview.getParent(), false);
        notDataView.setOnClickListener(this);
        pickAdapter.setEmptyView(loadingView);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mNoData) {
                    pickAdapter.setEmptyView(notDataView);
                    mNoData = false;
                }
            }
        }, 1000);
    }

    @Override
    public void onClick(View view) {
        Pick();
    }


    private OnItemChildClickListener listener = new OnItemChildClickListener() {
        @Override
        public void onSimpleItemChildClick(final BaseQuickAdapter adapter, View view, final int position) {
            switch (view.getId()){
                case R.id.pick_button:
                    AppOrder order = appHome.getRow().get(position);
                  /*  ServiceFactory.httpService()
                            .take( Contains.appLogin.getAdminId(),order.getDingdanId())
                            .compose(new DefaultTransformer <AppOrder>())
                            .subscribe(new RxSubscriber <AppOrder>(getActivity()) {
                                           @Override
                                           public void onNext(AppOrder order) {
                                               Pick();
                                               recyclerview.addOnItemTouchListener(listener);
                                               mMianActivity.viewPager.setCurrentItem(2);
                                           }

                                           @Override
                                           protected void onError(ApiException ex) {
                                               super.onError(ex);
                                               Toast.makeText(getActivity(), "抢单失败,请刷新后重试", Toast.LENGTH_SHORT).show();
                                           }
                                       }
                            );*/
                    break;
            }
        }
    };

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser==true) {
           initView();
        }
    }
}
