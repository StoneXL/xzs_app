package com.yxld.xzs.activity.patrol;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yxld.xzs.R;
import com.yxld.xzs.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * 消息管理界面
 */
public class MsgManagerFragment extends BasePatrolFragment {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_view)
    SwipeRefreshLayout refreshView;
    Unbinder unbinder;

    public MsgManagerFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_msg_manager, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        UIUtils.configSwipeRefreshLayoutColors(refreshView);
        refreshView.setOnRefreshListener(this);
    }

    @Override
    public void fetchData() {
        initData();
    }

    private void initData() {
        List<String> datas = new ArrayList<>();
        for (int i = 0 ;i < 10 ;i++){
            datas.add(""+i);
        }
        MsgManagerAdapter adapter = new MsgManagerAdapter(datas);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onRefresh() {
        refreshView.setRefreshing(false);
    }


    private static class MsgManagerAdapter extends BaseQuickAdapter<String,BaseViewHolder>{

        public MsgManagerAdapter(@Nullable List<String> data) {
            super(R.layout.layout_msg_manager,data);

        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, String s) {

        }
    }
}
