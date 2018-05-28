package com.yxld.xzs.activity.Main;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.orhanobut.logger.Logger;
import com.tbruyelle.rxpermissions.Permission;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.yxld.xzs.MainActivity;
import com.yxld.xzs.R;
import com.yxld.xzs.activity.workbench.NightOrderListActivity;
import com.yxld.xzs.adapter.SendAdapter;
import com.yxld.xzs.contain.Contains;
import com.yxld.xzs.entity.AppHome;
import com.yxld.xzs.entity.AppOrder;
import com.yxld.xzs.http.ServiceFactory;
import com.yxld.xzs.http.exception.ApiException;
import com.yxld.xzs.subscribers.RxSubscriber;
import com.yxld.xzs.transformer.DefaultTransformer;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;

/**
 * Created by yishangfei on 2016/10/10 0010.
 * 待送达界面
 */
public class SendFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout mSendRefresh;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private boolean mNoData;
    private AppHome appHome;
    private SendAdapter sendAdapter;
    private View loadingView, notDataView;// 加载view  没数据view
    private MainActivity mMainActivity;
    private NightOrderListActivity nightOrderListActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof MainActivity){
            mMainActivity = (MainActivity) context;
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
        initView();
        return view;
    }

    private void initView() {
        loadingView = getActivity().getLayoutInflater().inflate(R.layout.loading_view, (ViewGroup) recyclerView.getParent(), false);
        notDataView = getActivity().getLayoutInflater().inflate(R.layout.empty_view, (ViewGroup) recyclerView.getParent(), false);
        mSendRefresh.setOnRefreshListener(this);
        mSendRefresh.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        Send();
        recyclerView.addOnItemTouchListener(listener);
    }

    //网络请求
    private void Send() {
      /*  ServiceFactory.httpService()
                .send(Contains.appLogin.getAdminId(), Contains.appLogin.getAdminXiangmuId())
                .compose(new DorderTransformer<AppHome>())
                .subscribe(new RxSubscriber<AppHome>(getActivity()) {
                               @Override
                               public void onNext(AppHome home) {
                                   BadgedTabCustomView customView = mMainActivity.tabLayout.getTabCustomViewAt(2);
                                   if (mSendRefresh != null) {
                                       mSendRefresh.setRefreshing(false);
                                   }
                                   if (customView != null) {
                                       customView.setTabCount(home.getRow().size());
                                   }
                                   Collections.reverse(home.getRow()); // 倒序排列
                                   appHome = home;
                                   sendAdapter = new SendAdapter(home.getRow(), home.getSaleList());
                                   if (home.getRow().size() == 0) {
                                       mNoData = true;
                                       refresh();
                                   }
                                   recyclerView.setHasFixedSize(true);
                                   recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                                   recyclerView.setAdapter(sendAdapter);
                               }

                               @Override
                               protected void onError(ApiException ex) {
                                   super.onError(ex);
                                   if (mSendRefresh != null) {
                                       mSendRefresh.setRefreshing(false);
                                   }
                               }
                           }
                );*/
    }

    @Override
    public void onRefresh() {
        Send();
    }

    public void refresh() {
        loadingView = getActivity().getLayoutInflater().inflate(R.layout.loading_view, (ViewGroup) recyclerView.getParent(), false);
        notDataView = getActivity().getLayoutInflater().inflate(R.layout.empty_view, (ViewGroup) recyclerView.getParent(), false);
        notDataView.setOnClickListener(this);
        sendAdapter.setEmptyView(loadingView);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mNoData) {
                    sendAdapter.setEmptyView(notDataView);
                    mNoData = false;
                }
            }
        }, 1000);
    }

    @Override
    public void onClick(View view) {
        Send();
    }


    private OnItemChildClickListener listener = new OnItemChildClickListener() {
        @Override
        public void onSimpleItemChildClick(final BaseQuickAdapter adapter, View view, final int position) {
            Logger.d(view);
            final Button tel = (Button) view.findViewById(R.id.send_tel);
            switch (view.getId()) {
                case R.id.send_tel://待送达电话
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
                                        intent.setData(Uri.parse("tel:" + tel.getText().toString()));
                                        //开启系统拨号器
                                        startActivity(intent);
                                    } else if (permission.shouldShowRequestPermissionRationale) {
                                        // Denied permission without ask never again
                                        Toast.makeText(getActivity(),
                                                "没有访问也没有拒绝",
                                                Toast.LENGTH_SHORT).show();
                                    } else {
                                        // Denied permission with ask never again
                                        // Need to go to the settings
                                        Toast.makeText(getActivity(),
                                                "没有权限,您不能打电话",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                    break;
                case R.id.send_button://确定送达
                    AppOrder order = appHome.getRow().get(position);
                    ServiceFactory.httpService()
                            .reach(Contains.appLogin.getAdminId(), order.getDingdanId())
                            .compose(new DefaultTransformer<AppOrder>())
                            .subscribe(new RxSubscriber<AppOrder>(getActivity()) {
                                           @Override
                                           public void onNext(AppOrder order) {
                                               Send();
                                               recyclerView.addOnItemTouchListener(listener);
                                           }

                                           @Override
                                           protected void onError(ApiException ex) {
                                               super.onError(ex);
                                               Toast.makeText(getActivity(), "抢单失败,请刷新后重试", Toast.LENGTH_SHORT).show();
                                           }
                                       }
                            );
                    break;
            }
        }
    };

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser == true) {
            initView();
        }
    }
}
