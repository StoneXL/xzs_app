package com.yxld.xzs.activity.patrol;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
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
import com.yxld.xzs.adapter.PatrolRecordQuestionaireAdapter;
import com.yxld.xzs.base.BaseActivity;
import com.yxld.xzs.contain.Contains;
import com.yxld.xzs.db.DBUtil;
import com.yxld.xzs.entity.BaseBack;
import com.yxld.xzs.entity.ImageItem;
import com.yxld.xzs.entity.XunJianDianEntity;
import com.yxld.xzs.entity.XunJianJiLuEntity;
import com.yxld.xzs.http.api.HttpAPIWrapper;
import com.yxld.xzs.utils.NfcPatrolUtil;
import com.yxld.xzs.view.AlignLeftRightTextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 历史记录界面
 */
public class HistoryRecordActivity extends BaseActivity {
    public static final String KEY_JILUID = "key_jilu_id";
    public static final String KEY_POSITION = "key_position";
    @BindView(R.id.map_view)
    MapView mBaiduMapView;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.tv_exception_save_data)
    TextView tvExceptionSaveData;
    private XunJianJiLuEntity mJiluEntity;
    private boolean mIsUpload;
    private int mPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_record);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        scrollView.smoothScrollTo(0, 0);
        recyclerView.setNestedScrollingEnabled(false);

        final int jiluId = getIntent().getExtras().getInt(KEY_JILUID);
        mPosition = getIntent().getExtras().getInt(KEY_POSITION);

        showProgressDialog();
        Observable.create(new ObservableOnSubscribe<XunJianJiLuEntity>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<XunJianJiLuEntity> e) throws Exception {
                DBUtil dbUtil = DBUtil.getInstance(getApplicationContext());
                XunJianJiLuEntity jiLuEntity = dbUtil.getJiLuById(Contains.appLogin.getAdminId() + "", jiluId + "");
                if(jiLuEntity.xunJianDianDatas!=null && jiLuEntity.xunJianDianDatas.size()>0){
                    Collections.sort(jiLuEntity.xunJianDianDatas, new Comparator<XunJianDianEntity>() {
                        @Override
                        public int compare(XunJianDianEntity o1, XunJianDianEntity o2) {
                            return o1.xuliehao-o2.xuliehao;
                        }
                    });

                    for (XunJianDianEntity dianEntity : jiLuEntity.xunJianDianDatas){
                        if(TextUtils.isEmpty(dianEntity.remarkImgsUrls))
                            continue;

                        String[] imgs = dianEntity.remarkImgsUrls.split(",");
                        for (String s : imgs){
                            ImageItem item = new ImageItem();
                            item.isSelected = true;
                            item.path = s;
                            dianEntity.remarkImgsUrlTemp.add(item);
                        }
                    }
                }
                e.onNext(jiLuEntity);
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<XunJianJiLuEntity>() {
                    @Override
                    public void accept(@NonNull XunJianJiLuEntity entity) throws Exception {
                        mJiluEntity = entity;
                        HistoryRecordAdapter adapter = new HistoryRecordAdapter(mJiluEntity.xunJianDianDatas);
                        recyclerView.setAdapter(adapter);
                        initMapView(mJiluEntity.xunJianDianDatas);
                        dismissProgressDialog();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        dismissProgressDialog();
                        Toast.makeText(HistoryRecordActivity.this,"获取数据失败",Toast.LENGTH_SHORT).show();
                    }
                });

    }


    private void initMapView(List<XunJianDianEntity> entities) {
        if (mBaiduMapView.getChildCount() > 0) {
            View view = mBaiduMapView.getChildAt(0);
            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        scrollView.requestDisallowInterceptTouchEvent(false);
                    } else {
                        scrollView.requestDisallowInterceptTouchEvent(true);
                    }
                    return false;
                }
            });
        }

        if (entities.size() == 0) {
            return;
        }


        BaiduMap mBaiduMap = mBaiduMapView.getMap();
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
        if (TextUtils.isEmpty(zuobiao)) {
            return new double[]{0, 0};
        }
        String[] splits = zuobiao.split(",");
        if (splits.length != 2) {
            return new double[]{0, 0};
        }
        return new double[]{Double.parseDouble(splits[0]), Double.parseDouble(splits[1])};
    }

    @OnClick(R.id.tv_exception_save_data)
    public void onViewClicked() {
        uploadData();
    }


    private void uploadData() {
        showProgressDialog();
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                e.onNext(NfcPatrolUtil.handlerXunJianJiLu(mJiluEntity));
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String result) throws Exception {
                        doUpload(result);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        dismissProgressDialog();
                        Toast.makeText(HistoryRecordActivity.this,"上传失败,请重新再试",Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void doUpload(String xunJianUploadEntity){
        Map<String,String> params = new HashMap<>();
        params.put("uuid",Contains.uuid);
        params.put("result",xunJianUploadEntity);
        Disposable disposable = HttpAPIWrapper.getInstance().uploadPatrolResult(params)
                .subscribe(new Consumer<BaseBack>() {
                    @Override
                    public void accept(@NonNull BaseBack baseBack) throws Exception {
                        dismissProgressDialog();
                        if (baseBack.status == STATUS_CODE_OK) {
                            mIsUpload = true;
                            Toast.makeText(HistoryRecordActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                            onUploadSucceed();

                        } else {
                            onError(baseBack.status, baseBack.error);
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

    private void onUploadSucceed() {
        DBUtil dbUtil = DBUtil.getInstance(getApplicationContext());
        dbUtil.deleteJiluById(mJiluEntity.jiluId+"");
        finish();
    }

    @Override
    public void finish() {
        if (mIsUpload){
            Intent intent = new Intent();
            intent.putExtra(KEY_POSITION,mPosition);
            setResult(0x2001,intent);
        }
        super.finish();

    }

    private static class HistoryRecordAdapter extends BaseQuickAdapter<XunJianDianEntity, BaseViewHolder> {
        private SimpleDateFormat mFormat;

        public HistoryRecordAdapter(@Nullable List<XunJianDianEntity> data) {
            super(R.layout.item_patrol_record_full, data);
            mFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, XunJianDianEntity dianEntity) {

            AlignLeftRightTextView lineName = baseViewHolder.getView(R.id.tv_line_name);
            AlignLeftRightTextView serialNum = baseViewHolder.getView(R.id.tv_serial_num);
            AlignLeftRightTextView bianMa = baseViewHolder.getView(R.id.tv_check_point_charset);
            AlignLeftRightTextView dizhi = baseViewHolder.getView(R.id.tv_check_point_address);
            AlignLeftRightTextView dakaTime = baseViewHolder.getView(R.id.tv_check_point_time);
            lineName.setRightText(dianEntity.jiluXianluName + "");
            serialNum.setRightText(dianEntity.xuliehao + "");
            bianMa.setRightText(dianEntity.dianNfcBianma + "");
            dizhi.setRightText(dianEntity.dianDizhi + "");
            if (TextUtils.isEmpty(dianEntity.checkTime)) {
                dakaTime.setRightText("设备故障");
            } else {
                dakaTime.setRightText(mFormat.format(new Date(Long.parseLong(dianEntity.checkTime))));
            }

            RecyclerView rvXiang = baseViewHolder.getView(R.id.recyclerView);
            PatrolRecordQuestionaireAdapter adapter = new PatrolRecordQuestionaireAdapter(dianEntity.xunJianXiangDatas, true);//巡检项
            rvXiang.setAdapter(adapter);

            List<XunJianDianEntity> dianData = new ArrayList<>();
            boolean flag = false;
            if (dianEntity.xunJianShijianClassifies.size() > 0) {
                flag = true;
                dianData.add(dianEntity);
                RecyclerView rvShiJian = baseViewHolder.getView(R.id.recyclerView_Shijian);
                PatrolRecordActivity.PatrolRecordShiJianAdapter shijianAdapter = new PatrolRecordActivity.PatrolRecordShiJianAdapter(dianData, dianEntity.xunJianXiangDatas.size() + 1);
                rvShiJian.setAdapter(shijianAdapter);
            }

            RecyclerView rvRemark = baseViewHolder.getView(R.id.recyclerView_remark);
            PatrolRecordActivity.PatrolRecordRemarkAdapter remarkAdapter = new PatrolRecordActivity.PatrolRecordRemarkAdapter(dianData,
                    flag ? dianEntity.xunJianXiangDatas.size() + 2 : dianEntity.xunJianXiangDatas.size() + 1,true);
            rvRemark.setAdapter(remarkAdapter);
        }
    }
}
