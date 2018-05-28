package com.yxld.xzs.activity.camera;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.socks.library.KLog;
import com.yxld.xzs.R;
import com.yxld.xzs.adapter.FangquAdapter;
import com.yxld.xzs.base.BaseActivity;
import com.yxld.xzs.contain.Contains;
import com.yxld.xzs.entity.BaseBack;
import com.yxld.xzs.entity.FangQu;
import com.yxld.xzs.entity.Host;
import com.yxld.xzs.http.api.HttpAPIWrapper;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * 作者：Android on 2017/9/11
 * 邮箱：365941593@qq.com
 * 描述：
 */

public class FangQuActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshlayout)
    SwipeRefreshLayout refreshlayout;

    Host.DataBean dataBean;

    FangquAdapter fangQuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        setContentView(R.layout.activity_fangqu);
        ButterKnife.bind(this);
        dataBean = getIntent().getParcelableExtra("data");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        refreshlayout.setOnRefreshListener(this);
        refreshlayout.setRefreshing(true);
        getFangQu();
    }

    private void getFangQu() {
        refreshlayout.setRefreshing(true);
        Map<String, String> map = new HashMap<>();
        map.put("uuid", Contains.uuid);
        map.put("paianZhuji.zhujiMac", dataBean.getZhujiMac());
        Disposable disposable = HttpAPIWrapper.getInstance(HttpAPIWrapper.getOkHttpClient()).chazhaoFangqu(map)
                .subscribe(new Consumer<FangQu>() {
                    @Override
                    public void accept(@NonNull final FangQu fangQu) throws Exception {
                        refreshlayout.setRefreshing(false);
                        progressDialog.hide();
                        fangQuAdapter = new FangquAdapter(fangQu.getData());
                        recyclerView.setAdapter(fangQuAdapter);
                        fangQuAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                            @Override
                            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                                switch (view.getId()) {
                                    case R.id.tv_xuexi:
                                        Intent intent = new Intent(FangQuActivity.this, LearnDeviceActivity.class);
                                        intent.putExtra("flag",0);//表示学习
                                        intent.putExtra("data", fangQuAdapter.getData().get(i).getPaianShebei());
                                        intent.putExtra("mac", dataBean.getZhujiMac());
                                        startActivityForResult(intent, 0);
                                        break;
                                    case R.id.tv_xiugai:
                                        Intent intent1 = new Intent(FangQuActivity.this, LearnDeviceActivity.class);
                                        intent1.putExtra("flag",1);//表示修改
                                        intent1.putExtra("data", fangQuAdapter.getData().get(i).getPaianShebei());
                                        intent1.putExtra("mac", dataBean.getZhujiMac());
                                        startActivityForResult(intent1, 3);
                                      //  Toast.makeText(FangQuActivity.this,"修改",Toast.LENGTH_SHORT).show();
                                        break;
                                }
                            }
                        });
                        fangQuAdapter.setOnItemChildLongClickListener(new BaseQuickAdapter.OnItemChildLongClickListener() {
                            @Override
                            public boolean onItemChildLongClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                                KLog.i("长按生效");
                                showDeleteDialog(i);
                                return false;
                            }
                        });
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        refreshlayout.setRefreshing(false);
                    }
                });
        disposables.add(disposable);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == 2) {
            getFangQu();
        }else {
            getFangQu();
        }
    }

    @Override
    public void onRefresh() {
        getFangQu();
    }
    private void showDeleteDialog(final int i) {
        new AlertDialog.Builder(this).setTitle("删除该设备？")
                .setMessage("删除该设备需要重新学习")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deletDevice(i);
                    }
                }).show();
    }
    private void deletDevice(int i) {
        Map<String, String> map = new HashMap<>();
        map.put("uuid", Contains.uuid);
        map.put("paianShebei2.shebeiZhujiMac", dataBean.getZhujiMac());
        map.put("paianShebei2.shebeiFangquBianhao", fangQuAdapter.getData().get(i).getPaianShebei().getShebeiFangquBianhao());
        Disposable disposable = HttpAPIWrapper.getInstance(HttpAPIWrapper.getOkHttpClient()).shanchuSheBei(map)
                .subscribe(new Consumer<BaseBack>() {
                    @Override
                    public void accept(@NonNull BaseBack fangQu) throws Exception {
                        Toast.makeText(FangQuActivity.this, fangQu.getMSG(), Toast.LENGTH_SHORT).show();
                        getFangQu();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                    }
                });
        disposables.add(disposable);
    }
}
