package com.yxld.xzs.activity.Income;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yxld.xzs.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：yishangfei on 2017/1/6 0006 16:34
 * 邮箱：yishangfei@foxmail.com
 * 提现明细
 */
public class PoseFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private View loadingView,notDataView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_pose, null);
        ButterKnife.bind(this, view);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        loadingView = getActivity().getLayoutInflater().inflate(R.layout.loading_view, (ViewGroup) recyclerView.getParent(), false);
        notDataView = getActivity().getLayoutInflater().inflate(R.layout.empty_view, (ViewGroup) recyclerView.getParent(), false);
        return view;
    }



    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeLayout.setRefreshing(false);
            }
        }, 5000);

    }
}