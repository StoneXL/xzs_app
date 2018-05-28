package com.yxld.xzs.activity.patrol;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yxld.xzs.R;
import com.yxld.xzs.base.BaseActivity;
import com.yxld.xzs.contain.Contains;
import com.yxld.xzs.entity.XunJianDianEntity;
import com.yxld.xzs.entity.XunJianJiLuEntity;
import com.yxld.xzs.entity.XunJianShijianClassifyEntity;
import com.yxld.xzs.entity.XunJianXianLuXunJianDianEntity;
import com.yxld.xzs.entity.XunJianXiangEntity;
import com.yxld.xzs.http.api.HttpAPIWrapper;
import com.yxld.xzs.utils.UIUtils;
import com.yxld.xzs.view.LeftRightTextView;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * 具体线路界面
 */
public class ConcreteCircuitActivity extends BaseActivity {
    public static final String KEY_JILU_ID = "key_jilu_id";
    public static final String KEY_XIANLU_ID = "key_xianlu_id";
    public static final String KEY_XIANLU_NAME = "key_xianlu_name";

    @BindView(R.id.tv_title)
    LeftRightTextView tvTitle;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.map_view)
    MapView mBaiduMapView;
    @BindView(R.id.linearLayout)
    AutoLinearLayout linearLayout;

    private BaiduMap mBaiduMap;
    private int mXiangmuId;
    private int mXianLuId;
    private XunJianXianLuAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concrete_circuit);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView.setNestedScrollingEnabled(true);
        initData();
    }

    private void initData() {
        Bundle bundle = getIntent().getExtras();
        mXiangmuId = bundle.getInt(KEY_JILU_ID);
        mXianLuId = bundle.getInt(KEY_XIANLU_ID);
        String mXianLuName = bundle.getString(KEY_XIANLU_NAME);
        tvTitle.setRightText(mXianLuName);

        mAdapter = new XunJianXianLuAdapter(new ArrayList<XunJianDianEntity>());
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                Intent intent = new Intent(ConcreteCircuitActivity.this, PatrolRecordActivity.class);
                intent.putExtra(PatrolRecordActivity.KEY_IN_TYPE, PatrolRecordActivity.IN_TYPE_LOOK);
                intent.putExtra(PatrolRecordActivity.KEY_ENTITY,mAdapter.getData().get(i));
                startActivity(intent);
            }
        });

        loadDataFromServer();
    }

    private void loadDataFromServer() {
        showProgressDialog();
        Map<String, String> map = new HashMap<>();
        map.put("uuid", Contains.uuid);
        map.put("jiluId", mXiangmuId + "");
        map.put("xianluid", mXianLuId+"");

        Disposable disposable = HttpAPIWrapper.getInstance().getXunJianXianLuXunJianDian(map)
                .map(new Function<XunJianXianLuXunJianDianEntity, XunJianJiLuEntity>() {
                    @Override
                    public XunJianJiLuEntity apply(@NonNull XunJianXianLuXunJianDianEntity xianluEntity) throws Exception {
                        XunJianJiLuEntity jiLuEntity = new XunJianJiLuEntity();
                        jiLuEntity.status = xianluEntity.status;
                        jiLuEntity.msg = xianluEntity.msg;
                        jiLuEntity.MSG = xianluEntity.MSG;
                        jiLuEntity.error = xianluEntity.error;
                        jiLuEntity.success = xianluEntity.success;

                        if (xianluEntity.status == STATUS_CODE_OK) {
                            List<XunJianDianEntity> dianEntities = new ArrayList<XunJianDianEntity>();
                            for (XunJianXianLuXunJianDianEntity xianLuDianEntity : xianluEntity.data){
                                XunJianDianEntity dianEntity = new XunJianDianEntity();
                                dianEntity.dianDizhi = xianLuDianEntity.dianAddress;
                                dianEntity.dianId = xianLuDianEntity.dianId;
                                dianEntity.dianNfcBianma = xianLuDianEntity.dianName;
                                dianEntity.xuliehao = xianLuDianEntity.dianPaixu;
                                dianEntity.dianJingweiduZuobiao = xianLuDianEntity.dianZuobiao;
                                dianEntity.jiluId = xianLuDianEntity.jiluId;
                                dianEntity.xunJianXiangDatas = xianLuDianEntity.listXunjianxiang==null?new ArrayList<XunJianXiangEntity>():xianLuDianEntity.listXunjianxiang;
                                dianEntity.xunJianShijianClassifies = new ArrayList<XunJianShijianClassifyEntity>();
                                dianEntity.jiluXianluName = xianluEntity.total.jiluXianluName;
                                dianEntities.add(dianEntity);
                            }


                            Collections.sort(dianEntities, new Comparator<XunJianDianEntity>() {
                                @Override
                                public int compare(XunJianDianEntity o1, XunJianDianEntity o2) {
                                    return o1.xuliehao-o2.xuliehao;
                                }
                            });

                            jiLuEntity.xunJianDianDatas = dianEntities;
                        }

                        return jiLuEntity;
                    }
                })
                .subscribe(new Consumer<XunJianJiLuEntity>() {
                    @Override
                    public void accept(@NonNull XunJianJiLuEntity jiLuEntity) throws Exception {
                        dismissProgressDialog();
                        if (jiLuEntity.status == STATUS_CODE_OK) {
                            if(jiLuEntity.xunJianDianDatas ==null || jiLuEntity.xunJianDianDatas.size() ==0){

                                Toast.makeText(ConcreteCircuitActivity.this,"未获取到任何数据",Toast.LENGTH_SHORT).show();
                                return;
                            }
                            loadShijian(jiLuEntity.xunJianDianDatas);
                        } else {
                            onError(jiLuEntity.status, jiLuEntity.error);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        dismissProgressDialog();
                        Toast.makeText(ConcreteCircuitActivity.this, "网络请求出错了", Toast.LENGTH_SHORT).show();
                    }
                });
        disposables.add(disposable);
    }

    private void loadShijian(final List<XunJianDianEntity> entities) {
        Map<String,String> params = new HashMap<>();
        params.put("uuid",Contains.uuid);
        Disposable disposable = HttpAPIWrapper.getInstance().getShijian(params)
                .subscribe(new Consumer<XunJianShijianClassifyEntity>() {
                    @Override
                    public void accept(@NonNull XunJianShijianClassifyEntity entity) throws Exception {
                        dismissProgressDialog();
                        if (entity.status == STATUS_CODE_OK) {
                            handlerShijian(entities, entity.data);
                        } else {
                            onError(entity.status, "");
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        dismissProgressDialog();
                    }
                });
        disposables.add(disposable);
    }

    private void handlerShijian(List<XunJianDianEntity> dianEntities, List<XunJianShijianClassifyEntity> shiJianEntities) {

        for (XunJianDianEntity dianEntity : dianEntities){
            List<XunJianShijianClassifyEntity> entities = new ArrayList<>();
            for (XunJianShijianClassifyEntity classifyEntity : shiJianEntities){
                try {
                    entities.add(classifyEntity.clone());
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
            }
            dianEntity.xunJianShijianClassifies = entities;
        }

        mAdapter.addData(dianEntities);
        mAdapter.notifyDataSetChanged();

        linearLayout.measure(0, 0);
        if (linearLayout.getMeasuredHeight() >= UIUtils.getScreenHeight(this) / 2) {
            AutoLinearLayout.LayoutParams params = new AutoLinearLayout.LayoutParams(AutoLinearLayout.LayoutParams.MATCH_PARENT, 0);
            params.weight = 1;
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setLayoutParams(params);
        }

        if (dianEntities.size() == 0) {
            return;
        }
        initMap(dianEntities);
    }



    //********************************************地图相关********************************************************
    private void initMap(List<XunJianDianEntity> entities) {
        mBaiduMapView.setVisibility(View.VISIBLE);
        mBaiduMap = mBaiduMapView.getMap();
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mBaiduMap.setMyLocationEnabled(true);

        double[] zuobiao = handlerZuoBiao(entities.get(0).dianJingweiduZuobiao);
        LatLng ll = new LatLng(zuobiao[0],
                zuobiao[1]);
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(ll).zoom(15.0f);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

        for (XunJianDianEntity entity : entities) {
            double[] xy = handlerZuoBiao(entity.dianJingweiduZuobiao);
            LatLng location = new LatLng(xy[0], xy[1]);
            //构建Marker图标
            BitmapDescriptor bitmap = BitmapDescriptorFactory
                    .fromResource(R.mipmap.icon_gcoding);
            //构建MarkerOption，用于在地图上添加Marker
            OverlayOptions option = new MarkerOptions()
                    .position(location)
                    .icon(bitmap).period(1).animateType(MarkerOptions.MarkerAnimateType.drop);
            //在地图上添加Marker，并显示
            mBaiduMap.addOverlay(option);
        }
    }

    private double[] handlerZuoBiao(String zuobiao) {
        String[] splits = zuobiao.split(",");
        return new double[]{Double.parseDouble(splits[0]), Double.parseDouble(splits[1])};
    }


    @Override
    protected void onResume() {
        mBaiduMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mBaiduMapView.onPause();
        super.onPause();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        mBaiduMapView.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        mBaiduMapView.onDestroy();

        if (mBaiduMap != null) {
            mBaiduMap.setMyLocationEnabled(false);
            mBaiduMap = null;
        }
        super.onDestroy();
    }


    private static class XunJianXianLuAdapter extends BaseQuickAdapter<XunJianDianEntity, BaseViewHolder> {

        public XunJianXianLuAdapter(@Nullable List<XunJianDianEntity> data) {
            super(R.layout.item_patrol_flow, data);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, XunJianDianEntity dianEntity) {
            //查询
            baseViewHolder.getView(R.id.tv_check_status).setVisibility(View.INVISIBLE);
            ImageView ivEye = baseViewHolder.getView(R.id.iv_eye);
            ivEye.setColorFilter(mContext.getResources().getColor(R.color.color_007bc2));
            if (baseViewHolder.getLayoutPosition() == 0) {
                baseViewHolder.setVisible(R.id.line_up, false);
                View lineUp = baseViewHolder.getView(R.id.line_up);
                lineUp.setVisibility(View.INVISIBLE);
            }
            if (baseViewHolder.getLayoutPosition() == getData().size() - 1) {
                baseViewHolder.getView(R.id.line_dowm).setVisibility(View.INVISIBLE);
            }
            baseViewHolder.setText(R.id.tv_serial_num, String.valueOf(baseViewHolder.getLayoutPosition() + 1))
                    .setText(R.id.tv_address, dianEntity.dianDizhi);
        }
    }
}
