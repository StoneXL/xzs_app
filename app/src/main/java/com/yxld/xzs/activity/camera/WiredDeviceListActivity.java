package com.yxld.xzs.activity.camera;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jwkj.shakmanger.LocalDevice;
import com.jwkj.shakmanger.ShakeListener;
import com.jwkj.shakmanger.ShakeManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.socks.library.KLog;
import com.yxld.xzs.R;
import com.yxld.xzs.adapter.WiredDeviceListAdapter;
import com.yxld.xzs.base.BaseActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author xlei
 * @Date 2018/2/24.
 */

public class WiredDeviceListActivity extends BaseActivity {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout mSmartRefresh;

    private List<LocalDevice> devices = new ArrayList<>();
    private WiredDeviceListAdapter mAdapter;
    private boolean isDeviceActivity;//判断是居家安防还是岗位监控配网

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        getLocalDevice();

    }

    private void initView() {
        setContentView(R.layout.activity_wired_device_list);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        isDeviceActivity = getIntent().getBooleanExtra("isDeviceActivity", false);
        mSmartRefresh.setRefreshHeader(new ClassicsHeader(this));
        mSmartRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mSmartRefresh.finishRefresh();
                getLocalDevice();
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new WiredDeviceListAdapter(devices);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {

                if (isDeviceActivity) {
                    int flag = devices.get(i).getFlag();
                    KLog.e(devices.get(i).toString());
                    Intent intent = new Intent(WiredDeviceListActivity.this, CameraAddActivity.class);
                    intent.putExtra("ishasContactId", true);
                    intent.putExtra("ip", devices.get(i).getIP());
                    intent.putExtra("contactId", devices.get(i).getId());
                    intent.putExtra("frag", devices.get(i).getFlag() + "");
                    intent.putExtra("ipFlag", devices.get(i).getIP().substring(
                            devices.get(i).getIP().lastIndexOf(".") + 1,
                            devices.get(i).getIP().length()));
                    startActivity(intent);
                } else {
                    //0是没有密码 1是有密码
                    int flag = devices.get(i).getFlag();
                    KLog.e(devices.get(i).toString());
                    Intent intent = new Intent(WiredDeviceListActivity.this, CameraAddNewActivity.class);
                    intent.putExtra("ishasContactId", true);
                    intent.putExtra("ip", devices.get(i).getIP());
                    intent.putExtra("contactId", devices.get(i).getId());
                    intent.putExtra("frag", devices.get(i).getFlag() + "");
                    intent.putExtra("ipFlag", devices.get(i).getIP().substring(
                            devices.get(i).getIP().lastIndexOf(".") + 1,
                            devices.get(i).getIP().length()));
                    startActivity(intent);
                }

            }
        });
    }

    /**
     * 获取局域网内的设备
     */
    private void getLocalDevice() {
        if (devices.size() > 0) {
            devices.clear();//清除当前设备（保持每次搜索都能拿到最新的设备）
        }
        ShakeManager.getInstance()
                .setSearchTime(5000)//设置搜索时间（时间的毫秒值），默认10s
//                .schedule(2, 3000)//（默认只会执行一次）执行两次扫描任务，间隔3s执行（上一次执行结束到下一次开始之间的时间）
                .shaking(new ShakeListener() {//开始搜索，并回调
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onNext(LocalDevice device) {
                        //去重处理
                        boolean isExisted = false;
                        if (devices != null && devices.size() > 0) {
                            for (LocalDevice localDevice : devices) {
                                if (localDevice.getId().equals(device.getId())) {
                                    isExisted = true;
                                    break;
                                }
                            }
                        }
                        if (!isExisted) {
                            devices.add(device);
                            Collections.sort(devices);
                            mAdapter.notifyDataSetChanged();//搜索到一个就刷新一次列表
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        KLog.e("error msg :" + throwable);
                        //   showMsg(getString(R.string.shake_task_running));
                    }

                    @Override
                    public void onCompleted() {
                        //hideProgress();
                        // showSuccessMsg(getString(R.string.shake_task_complete));
                        mAdapter.notifyDataSetChanged();//可以搜索完成之后再刷新列表
                    }
                });
    }
}
