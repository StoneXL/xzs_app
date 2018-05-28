package com.yxld.xzs.activity.workbench;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.yxld.xzs.R;
import com.yxld.xzs.adapter.RepairMaterialAdapter;
import com.yxld.xzs.base.BaseActivity;
import com.yxld.xzs.contain.Contains;
import com.yxld.xzs.entity.BaseBack;
import com.yxld.xzs.entity.MaterialBean;
import com.yxld.xzs.entity.NightWarehouseBean;
import com.yxld.xzs.entity.NightWarehouseDetail;
import com.yxld.xzs.http.api.HttpAPIWrapper;
import com.yxld.xzs.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * 夜间商品出库单
 * Created by William on 2017/11/24.
 */

public class NightWarehouseDetailActivity extends BaseActivity {
    @BindView(R.id.tv_night_responsible)
    TextView tvNightResponsible;
    @BindView(R.id.tv_apply_time)
    TextView tvApplyTime;
    @BindView(R.id.rv_material)
    RecyclerView rvMaterial;
    @BindView(R.id.bt_confirm)
    Button btConfirm;

    private RepairMaterialAdapter repairMaterialAdapter;
    private View notDataView;//无数据

    private String warehouseNum;//夜间出库单编号
    private int cargoType;//备货状态 1未备货 2 已备货
    private String cargoMan;//备货人
    private String outWareMan;//出库人
    private NightWarehouseBean nightWarehouseBean;
    private List<MaterialBean> materialBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_night_warehouse_detail);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nightWarehouseBean = (NightWarehouseBean) getIntent().getSerializableExtra
                ("nightWarehouseBean");

        cargoType = nightWarehouseBean.getLinghuoZhuangtai();
        warehouseNum = nightWarehouseBean.getChukudanBianhao();
        cargoMan = nightWarehouseBean.getBeihuoren();
        outWareMan = nightWarehouseBean.getChukurenMing();

        initView();
        initAdapter();
        if (TextUtils.isEmpty(warehouseNum)) {
            ToastUtil.showInfo(this, "没有出库单编号，请重新进入");
        } else {
            initData();
        }
    }

    /**
     * 设置数据
     *
     * @param data
     */
    private void setData(NightWarehouseDetail data) {
        if (null != data.getRows() && data.getRows().size() != 0) {
            materialBeanList = data.getRows();
        }
        final int size = materialBeanList == null ? 0 : materialBeanList.size();
        if (size > 0) {
            repairMaterialAdapter.setNewData(materialBeanList);//将首次数据塞入适配器的方法
        } else {
            repairMaterialAdapter.setEmptyView(notDataView);
            repairMaterialAdapter.setNewData(new ArrayList<MaterialBean>());
        }
        repairMaterialAdapter.loadMoreEnd(true);//不显示没有更多数据布局

    }

    private void initData() {
        Map<String, String> map = new HashMap<>();
        map.put("uuid", Contains.uuid);
        map.put("chukudanBianhao", warehouseNum);
        Disposable disposable = HttpAPIWrapper.getInstance(HttpAPIWrapper.getOkHttpClient())
                .getNightWarehouse(map)
                .subscribe(new Consumer<NightWarehouseDetail>() {
                    @Override
                    public void accept(@NonNull NightWarehouseDetail data) throws Exception {
                        if (data.status == 0) {
                            setData(data);
                        } else {
                            repairMaterialAdapter.setEmptyView(notDataView);
                            repairMaterialAdapter.setNewData(new ArrayList<MaterialBean>());
                            onError(data.status, data.MSG);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        repairMaterialAdapter.setEmptyView(notDataView);
                        repairMaterialAdapter.setNewData(new ArrayList<MaterialBean>());

                    }
                });
        disposables.add(disposable);
    }

    private void initAdapter() {
        repairMaterialAdapter = new RepairMaterialAdapter();
        repairMaterialAdapter.setEmptyView(notDataView);
        rvMaterial.setAdapter(repairMaterialAdapter);
    }

    /**
     * 初始化控件
     */
    private void initView() {
        tvNightResponsible.setText(nightWarehouseBean.getLinghuoren());
        tvApplyTime.setText(nightWarehouseBean.getTijiaoShijian());

        rvMaterial.setLayoutManager(new LinearLayoutManager(this));
        notDataView = getLayoutInflater().inflate(R.layout.layout_empty_data_new, (ViewGroup)
                rvMaterial
                .getParent(), false);
        rvMaterial.setNestedScrollingEnabled(false);//设置嵌套滑动不能用
    }

    @OnClick({R.id.bt_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_confirm:
                if (cargoType == 1) {
                    ToastUtil.showInfo(this, "未备货，不能领取");
                } else {
                    confrimGetNightWarehouse();
                }
                break;
        }
    }

    /**
     * 确认领货
     */
    private void confrimGetNightWarehouse() {
        progressDialog.show();
        Map<String, String> map = new HashMap<>();
        map.put("uuid", Contains.uuid);
        map.put("chukudanBianhao", warehouseNum);
        map.put("linghuozhuangtai", cargoType + "");
        map.put("beihuoren", cargoMan);
        map.put("chukurenMing", outWareMan);

        Disposable disposable = HttpAPIWrapper.getInstance(HttpAPIWrapper.getOkHttpClient())
                .confrimNightWarehouse(map)
                .subscribe(new Consumer<BaseBack>() {
                    @Override
                    public void accept(@NonNull BaseBack data) throws Exception {
                        progressDialog.hide();
                        if (data.status == 0) {
                            ToastUtil.showInfo(NightWarehouseDetailActivity.this, "领取成功");
                            Intent intent = new Intent();
                            setResult(RESULT_OK, intent);
                            finish();
                        } else {
                            onError(data.status, data.MSG);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        ToastUtil.showInfo(NightWarehouseDetailActivity.this, "请求失败");
                    }
                });
        disposables.add(disposable);
    }
}
