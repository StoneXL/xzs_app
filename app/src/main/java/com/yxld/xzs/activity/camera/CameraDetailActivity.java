package com.yxld.xzs.activity.camera;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.socks.library.KLog;
import com.yxld.xzs.R;
import com.yxld.xzs.adapter.AddCameraAdapter;
import com.yxld.xzs.base.BaseActivity;
import com.yxld.xzs.contain.Contains;
import com.yxld.xzs.entity.CameraDetail;
import com.yxld.xzs.http.api.HttpAPIWrapper;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;


/**
 * @author xlei
 * @Date 2018/1/9.
 */

public class CameraDetailActivity extends BaseActivity implements OnRefreshListener {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout mSmartRefresh;
    @BindView(R.id.ly_empty_data)
    AutoLinearLayout mLyEmptyData;
    private AddCameraAdapter mAdapter;
    private List<CameraDetail.DataBean> dataList;
    private String xiangmuId;
    private String fwLoudong;
    private String fwDanyuan;
    private String fwFanghao;
    private String mXuLieHao;
    private String mPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initView() {
        setContentView(R.layout.activity_add_camera);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSmartRefresh.setRefreshHeader(new ClassicsHeader(this));

    }

    private void initData() {
        if (dataList == null) {
            dataList = new ArrayList<>();
        }
        if (mAdapter == null) {
            mAdapter = new AddCameraAdapter(dataList);
        }
        mRecyclerView.setAdapter(mAdapter);
        mSmartRefresh.setOnRefreshListener(this);
        mAdapter.setNewData(dataList);
        xiangmuId = getIntent().getStringExtra("xiangmuId");
        fwLoudong = getIntent().getStringExtra("fwLoudong");
        fwDanyuan = getIntent().getStringExtra("fwDanyuan");
        fwFanghao = getIntent().getStringExtra("fwFanghao");
        mXuLieHao = getIntent().getStringExtra("mXuLieHao");
        mPhone = getIntent().getStringExtra("mPhone");
        loadData();
    }

    private void loadData() {
        Map<String, String> map = new HashMap<>();
        map.put("uuid", Contains.uuid);
        map.put("xiangmuId", xiangmuId);
        map.put("loudong", fwLoudong);
        map.put("danyuan", fwDanyuan);
        map.put("fanghao", fwFanghao);
        map.put("phone", mPhone);
        map.put("sb_ipc_id", mXuLieHao);
        KLog.i("CameraDetailActivity项目" + xiangmuId + "楼栋" + fwLoudong + "单元" + fwDanyuan + "房号" + fwFanghao + "业主手机" + mPhone + "序列号" + mXuLieHao);

        Disposable subscribe = HttpAPIWrapper.getInstance().searchCamera(map).subscribe(new Consumer<CameraDetail>() {
            @Override
            public void accept(@NonNull CameraDetail cameraDetail) throws Exception {
                if (cameraDetail.status == 1) {
                    dataList = cameraDetail.getData();
                    if (dataList.size() <= 0) {
                        mLyEmptyData.setVisibility(View.VISIBLE);
                    } else {
                        mLyEmptyData.setVisibility(View.GONE);
                    }
                    mAdapter.setNewData(dataList);

                } else {
                    mLyEmptyData.setVisibility(View.VISIBLE);
                    onError(cameraDetail.status, cameraDetail.msg);
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                mLyEmptyData.setVisibility(View.VISIBLE);
            }
        }, new Action() {
            @Override
            public void run() throws Exception {

            }
        });
        disposables.add(subscribe);
    }


    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        loadData();
        refreshlayout.finishRefresh();
    }


}
