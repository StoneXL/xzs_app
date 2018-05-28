package com.yxld.xzs.activity.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yxld.xzs.R;
import com.yxld.xzs.adapter.ProjectListAdapter;
import com.yxld.xzs.base.BaseActivity;
import com.yxld.xzs.contain.Contains;
import com.yxld.xzs.entity.XiangMu;
import com.yxld.xzs.http.api.HttpAPIWrapper;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * 作者：Android on 2017/9/13
 * 邮箱：365941593@qq.com
 * 描述：
 */

public class ProjectListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshlayout)
    SwipeRefreshLayout refreshlayout;

    ProjectListAdapter projectListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_list);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getXiangMu();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        refreshlayout.setOnRefreshListener(this);
    }

    private void getXiangMu() {
        Map<String, String> map = new HashMap<>();
        map.put("uuId", Contains.uuid);
        Disposable disposable = HttpAPIWrapper.getInstance(HttpAPIWrapper.getOkHttpClient()).findXm(map)
                .subscribe(new Consumer<XiangMu>() {
                    @Override
                    public void accept(@NonNull XiangMu xiangMu) throws Exception {
                        progressDialog.hide();
                        refreshlayout.setRefreshing(false);
                        if (xiangMu.status == 0) {
                            projectListAdapter = new ProjectListAdapter(xiangMu.getData());
                            recyclerView.setAdapter(projectListAdapter);
                            projectListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                                    Intent intent = new Intent(ProjectListActivity.this, AisleActivity.class);
                                    intent.putExtra("xiangmuId", projectListAdapter.getData().get(i).getXiangmuId() + "");
                                    startActivity(intent);
                                }
                            });
                        } else {
                            onError(xiangMu.status, xiangMu.error);
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
        getXiangMu();
    }
}
