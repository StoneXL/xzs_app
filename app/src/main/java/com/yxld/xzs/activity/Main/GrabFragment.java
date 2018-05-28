package com.yxld.xzs.activity.Main;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
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
import com.yxld.xzs.adapter.GrabAdapter;
import com.yxld.xzs.base.BaseFragment;
import com.yxld.xzs.entity.AppHome;
import com.yxld.xzs.entity.AppOrder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yxld.xzs.R.id.recyclerView;

/**
 * Created by yishangfei on 2016/10/10 0010.
 * 待抢单界面
 */
public class GrabFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,
        View.OnClickListener {

    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout mGrabRefresh;
    @BindView(recyclerView)
    RecyclerView recyclerview;

    private boolean mNoData;
    private AppHome appHome;
    //标志已经初始化完成
    private boolean isPrepared;
    private GrabAdapter grabAdapter;
    private View loadingView, notDataView;//缓存Fragmen  加载view
    private MainActivity mMainActivity;
    private NightOrderListActivity nightOrderListActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            mMainActivity = (MainActivity) context;
        } else if (context instanceof NightOrderListActivity) {
            nightOrderListActivity = (NightOrderListActivity) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement PatrolBadgeCallback");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grab, null);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        isPrepared = true;
        lazyLoad();
        return view;
    }

    private void initView() {
        loadingView = getActivity().getLayoutInflater().inflate(R.layout.loading_view,
                (ViewGroup) recyclerview.getParent(), false);
        notDataView = getActivity().getLayoutInflater().inflate(R.layout.empty_view, (ViewGroup)
                recyclerview.getParent(), false);
        mGrabRefresh.setOnRefreshListener(this);
        mGrabRefresh.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        Grab();
        recyclerview.addOnItemTouchListener(listener);
    }


    //网络请求
    private void Grab() {
        /*ServiceFactory.httpService()
                .grab(Contains.appLogin.getAdminId(), Contains.appLogin.getAdminXiangmuId())
                .compose(new DorderTransformer<AppHome>())
                .subscribe(new RxSubscriber<AppHome>(getActivity()) {
                               @Override
                               public void onNext(AppHome home) {
                                   BadgedTabCustomView customView = mMainActivity.tabLayout
                                           .getTabCustomViewAt(0);
                                   if (mGrabRefresh != null) {
                                       mGrabRefresh.setRefreshing(false);
                                   }
                                   if (customView != null) {
                                       customView.setTabCount(home.getRow().size());
                                   }
                                   Collections.reverse(home.getRow()); // 倒序排列
                                   appHome = home;
                                   grabAdapter = new GrabAdapter(home.getRow(), home.getSaleList());
                                   if (home.getRow().size() == 0) {
                                       mNoData = true;
                                       refresh();
                                   }
                                   recyclerview.setHasFixedSize(true);
                                   recyclerview.setLayoutManager(new LinearLayoutManager
                                           (getActivity()));
                                   recyclerview.setAdapter(grabAdapter);
                               }

                               @Override
                               protected void onError(ApiException ex) {
                                   super.onError(ex);
                                   if (mGrabRefresh != null) {
                                       mGrabRefresh.setRefreshing(false);
                                   }
                               }
                           }
                );*/
    }

    @Override
    public void onRefresh() {
        Grab();
    }

    public void refresh() {
        loadingView = getActivity().getLayoutInflater().inflate(R.layout.loading_view,
                (ViewGroup) recyclerview.getParent(), false);
        notDataView = getActivity().getLayoutInflater().inflate(R.layout.empty_view, (ViewGroup)
                recyclerview.getParent(), false);
        notDataView.setOnClickListener(this);
        grabAdapter.setEmptyView(loadingView);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mNoData) {
                    grabAdapter.setEmptyView(notDataView);
                    mNoData = false;
                }
            }
        }, 1000);
    }

    @Override
    public void onClick(View view) {
        Grab();
    }


    private OnItemChildClickListener listener = new OnItemChildClickListener() {
        @Override
        public void onSimpleItemChildClick(final BaseQuickAdapter adapter, View view, final int
                position) {
            switch (view.getId()) {
                case R.id.grab_button:
                    AppOrder order = appHome.getRow().get(position);
                   /* ServiceFactory.httpService()
                            .single(Contains.appLogin.getAdminId(), order.getDingdanId(),
                                    Contains.appLogin.getAdminXiangmuId())
                            .compose(new DefaultTransformer<AppOrder>())
                            .subscribe(new RxSubscriber<AppOrder>(getActivity()) {
                                           @Override
                                           public void onNext(AppOrder order) {
                                               Grab();
                                               recyclerview.addOnItemTouchListener(listener);
                                               mMainActivity.viewPager.setCurrentItem(1);
                                           }

                                           @Override
                                           protected void onError(ApiException ex) {
                                               super.onError(ex);
                                               Toast.makeText(getActivity(), "抢单失败,请刷新后重试", Toast
                                                       .LENGTH_SHORT).show();
                                           }
                                       }
                            );*/
                    break;
            }
        }
    };

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        initView();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void EventBus(String message) {
        if (message.equals("来单了")) {
            initView();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
