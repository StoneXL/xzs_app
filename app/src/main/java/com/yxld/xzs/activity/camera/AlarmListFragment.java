package com.yxld.xzs.activity.camera;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.socks.library.KLog;
import com.yxld.xzs.R;
import com.yxld.xzs.adapter.HostAdapter;
import com.yxld.xzs.base.BaseFragment;
import com.yxld.xzs.contain.Contains;
import com.yxld.xzs.entity.Host;
import com.yxld.xzs.http.api.HttpAPIWrapper;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * 作者：Android on 2017/9/8
 * 邮箱：365941593@qq.com
 * 描述：
 */

public class AlarmListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshlayout)
    SwipeRefreshLayout refreshlayout;
    @BindView(R.id.new_host)
    TextView newHost;
    Unbinder unbinder;

    HostAdapter hostAdapter;

    protected CompositeDisposable disposables = new CompositeDisposable();

    @Override
    protected void lazyLoad() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alarm_list, null);
        unbinder = ButterKnife.bind(this, view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        refreshlayout.setOnRefreshListener(this);
        getHostList();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.new_host)
    public void onViewClicked() {
        Intent intent = new Intent(getActivity(), AddHostActivity.class);
        intent.putExtra("flag", "new");
        startActivityForResult(intent, 0);
        getActivity().overridePendingTransition(R.anim.activity_translate_in, R.anim.activity_translate_out);
    }

    private void getHostList() {
        Map<String, String> map = new HashMap<>();
        map.put("uuid", Contains.uuid);
        KLog.i(map);
        Disposable disposable = HttpAPIWrapper.getInstance(HttpAPIWrapper.getOkHttpClient()).getZhuJiLieBiao(map)
                .subscribe(new Consumer<Host>() {
                    @Override
                    public void accept(@NonNull final Host host) throws Exception {
                        refreshlayout.setRefreshing(false);
                        hostAdapter = new HostAdapter(host.getData());
                        recyclerView.setAdapter(hostAdapter);
                        hostAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                            @Override
                            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                                switch (view.getId()) {
                                    case R.id.xiugai:
                                        Intent intent = new Intent(getActivity(), AddHostActivity.class);
                                        intent.putExtra("data", hostAdapter.getData().get(i));
                                        intent.putExtra("flag", "xiugai");
                                        startActivityForResult(intent, 0);
                                        getActivity().overridePendingTransition(R.anim.activity_translate_in, R.anim.activity_translate_out);
                                        break;
                                    case R.id.fangqu_liebiao:
                                        Intent intent1 = new Intent(getActivity(), FangQuActivity.class);
                                        intent1.putExtra("data", hostAdapter.getData().get(i));
                                        startActivity(intent1);
                                        break;
                                }
                            }
                        });
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                    }
                });
        disposables.add(disposable);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        KLog.i("activity返回");
        if (requestCode == 0 && resultCode == 2) {
            getHostList();
        }
    }

    @Override
    public void onRefresh() {
        getHostList();
    }
}
