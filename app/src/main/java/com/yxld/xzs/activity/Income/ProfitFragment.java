package com.yxld.xzs.activity.Income;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.yxld.xzs.R;
import com.yxld.xzs.activity.Navigation.NightActivity;
import com.yxld.xzs.adapter.ProfitAdapter;
import com.yxld.xzs.contain.Contains;
import com.yxld.xzs.entity.AppPeisongorder;
import com.yxld.xzs.http.ServiceFactory;
import com.yxld.xzs.http.exception.ApiException;
import com.yxld.xzs.subscribers.RxSubscriber;
import com.yxld.xzs.transformer.DefaultTransformer;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.attr.order;

/**
 * 作者：yishangfei on 2017/1/6 0006 16:33
 * 邮箱：yishangfei@foxmail.com
 * 收入明细
 */
public class ProfitFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,View.OnClickListener {
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private ProfitAdapter profitAdapter;
    private View loadingView,notDataView;
    private boolean mNoData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_profit, null);
        ButterKnife.bind(this, view);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        loadingView = getActivity().getLayoutInflater().inflate(R.layout.loading_view, (ViewGroup) recyclerView.getParent(), false);
        notDataView = getActivity().getLayoutInflater().inflate(R.layout.empty_view, (ViewGroup) recyclerView.getParent(), false);
        Details();
        return view;
    }


    //网络请求
    private void Details() {
        ServiceFactory.httpService()
                .complete(Contains.appLogin.getAdminId())
                .compose(new DefaultTransformer<List<AppPeisongorder>>())
                .subscribe(new RxSubscriber<List<AppPeisongorder>>(getActivity()) {
                    @Override
                    public void onNext(List<AppPeisongorder> complete) {
                        if (swipeLayout != null) {
                            swipeLayout.setRefreshing(false);
                        }
                        profitAdapter = new ProfitAdapter(complete);
                        if (complete.size() == 0) {
                            mNoData = true;
                            refresh();
                        }
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        recyclerView.setAdapter(profitAdapter);
                    }

                    @Override
                    protected void onError(ApiException ex) {
                        super.onError(ex);
                        if (swipeLayout != null) {
                            swipeLayout.setRefreshing(false);
                        }
                    }
                });
    }


    @Override
    public void onClick(View view) {
        Details();
    }


    private void refresh() {
        loadingView = getActivity().getLayoutInflater().inflate(R.layout.loading_view, (ViewGroup) recyclerView.getParent(), false);
        notDataView = getActivity().getLayoutInflater().inflate(R.layout.empty_view, (ViewGroup) recyclerView.getParent(), false);
        notDataView.setOnClickListener(this);
        profitAdapter.setEmptyView(loadingView);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mNoData) {
                    profitAdapter.setEmptyView(notDataView);
                    mNoData = false;
                }
            }
        }, 1000);
    }

    @Override
    public void onRefresh() {
        Details();
    }
}
