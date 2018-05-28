package com.yxld.xzs.activity.patrol;


import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.socks.library.KLog;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;
import com.yxld.xzs.R;
import com.yxld.xzs.contain.Contains;
import com.yxld.xzs.db.DBUtil;
import com.yxld.xzs.entity.XunJianDianEntity;
import com.yxld.xzs.entity.XunJianJiLuEntity;
import com.yxld.xzs.entity.XunJianShijianClassifyEntity;
import com.yxld.xzs.entity.XunJianStartEntity;
import com.yxld.xzs.entity.XunJianXianLuXunJianDianEntity;
import com.yxld.xzs.entity.XunJianXiangEntity;
import com.yxld.xzs.http.api.HttpAPIWrapper;
import com.yxld.xzs.utils.NfcPatrolUtil;
import com.yxld.xzs.utils.UIUtils;
import com.yxld.xzs.view.AlignLeftRightTextView;
import com.yxld.xzs.view.LeftImgTextView;
import com.yxld.xzs.view.StartPatrolView;
import com.zhy.autolayout.AutoRelativeLayout;

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
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThisTimeTaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThisTimeTaskFragment extends BasePatrolFragment {


    @BindView(R.id.tv_start_time)
    AlignLeftRightTextView tvStartTime;
    @BindView(R.id.tv_end_time)
    AlignLeftRightTextView tvEndTime;
    @BindView(R.id.tv_patrol_way)
    AlignLeftRightTextView tvPatrolWay;
    @BindView(R.id.tv_plan_name)
    AlignLeftRightTextView tvPlanName;
    @BindView(R.id.tv_patrol_people)
    AlignLeftRightTextView tvPatrolPeople;
    @BindView(R.id.btn_detail_way)
    AutoRelativeLayout btnDetailWay;
    @BindView(R.id.start_patrol_view)
    StartPatrolView startPatrolView;
    Unbinder unbinder;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;
    @BindView(R.id.tv_title)
    LeftImgTextView tvTitle;


    private BroadcastReceiver mBroadcastReceiver;
    private XunJianJiLuEntity mJiLuEntity;


    public ThisTimeTaskFragment() {
        // Required empty public constructor
    }


    public static ThisTimeTaskFragment newInstance() {
        return new ThisTimeTaskFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_this_time_task, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getContext().unregisterReceiver(mBroadcastReceiver);
        startPatrolView.cancelCountDown();
        unbinder.unbind();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        UIUtils.configSwipeRefreshLayoutColors(swipeLayout);
        swipeLayout.setOnRefreshListener(this);
        registerBroadcast();
    }

    private void registerBroadcast() {
        IntentFilter filter = new IntentFilter(getContext().getResources().getString(R.string.nfc_patrol_complete));
        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                tvStartTime.setRightText("");
                tvEndTime.setRightText("");
                tvPatrolWay.setRightText("");
                tvPatrolPeople.setRightText("");
                tvPlanName.setRightText("");
                startPatrolView.cancelCountDown();
                startPatrolView.onInit();
                tvTitle.setRightTextView("距离本次巡检开始还有 : ");
                btnDetailWay.setClickable(false);

                //防止setClickable(false)无效
                btnDetailWay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                loadNextTaskFromServer();
            }
        };
        getContext().registerReceiver(mBroadcastReceiver, filter);
    }

    @Override
    public void fetchData() {
        if (NfcPatrolUtil.hasRemainTask()) {
            //还有正在执行的任务
            KLog.i("还有正在执行的任务.........,");
            swipeLayout.setRefreshing(true);
            Observable.create(new ObservableOnSubscribe<XunJianJiLuEntity>() {
                @Override
                public void subscribe(@NonNull ObservableEmitter<XunJianJiLuEntity> e) throws Exception {
                    DBUtil dbUtil = DBUtil.getInstance(getContext());
                    XunJianJiLuEntity entity = dbUtil.getJiLuById(Contains.appLogin.getAdminId() + "", NfcPatrolUtil.getCurrentJiLuId());
                    if (entity.xunJianDianDatas != null && entity.xunJianDianDatas.size() > 0) {
                        Collections.sort(entity.xunJianDianDatas, new Comparator<XunJianDianEntity>() {
                            @Override
                            public int compare(XunJianDianEntity o1, XunJianDianEntity o2) {
                                return o1.xuliehao - o2.xuliehao;
                            }
                        });
                    }
                    e.onNext(entity);
                    e.onComplete();
                }
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<XunJianJiLuEntity>() {
                        @Override
                        public void accept(@NonNull XunJianJiLuEntity entity) throws Exception {
                            startPatrolView.onInit();
                            swipeLayout.setRefreshing(false);
                            Contains.jilu = entity;
                            mJiLuEntity = entity;
                            onLoadDataSucceed();
                            ask2XunJian();
                        }
                    });

        } else {
            //没有正在执行的任务
            KLog.i("没有正在执行的任务,从网络获取任务");
            loadNextTaskFromServer();
        }
    }

    private void ask2XunJian() {
        new AlertDialog.Builder(getContext())
                .setMessage("您当前还有正在执行的任务，是否继续任务?")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startPatrol();
                    }
                }).show();
    }

    /**
     * 获取下次巡检...
     */
    private void loadNextTaskFromServer() {
        swipeLayout.setRefreshing(true);
        Map<String, String> params = new HashMap<>();
        params.put("uuid", Contains.uuid);
        Disposable disposable = HttpAPIWrapper.getInstance().getNextXunJianXiang(params)
                .subscribe(new Consumer<XunJianJiLuEntity>() {
                    @Override
                    public void accept(@NonNull XunJianJiLuEntity entity) throws Exception {
                        swipeLayout.setRefreshing(false);
                        startPatrolView.onInit();
                        if (entity.status == STATUS_CODE_OK) {
                            mJiLuEntity = entity.data;
                            tvTitle.setRightTextView("距离本次巡检开始还有 : ");
                            onLoadDataSucceed();
                        } else {
                            onError(entity.status, entity.error);
                            tvStartTime.setRightText("");
                            tvEndTime.setRightText("");
                            tvPatrolWay.setRightText("");
                            tvPatrolPeople.setRightText("");
                            tvPlanName.setRightText("");
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        swipeLayout.setRefreshing(false);
                    }
                });
        disposables.add(disposable);
    }

    @Override
    public void onRefresh() {
        fetchData();
    }

    private void onLoadDataSucceed() {
        if (mJiLuEntity.jiluKaishiJihuaShijian == 0) {
            Toast.makeText(getContext(), "没有获取到计划开始时间,请刷新", Toast.LENGTH_SHORT).show();
            return;
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        tvStartTime.setRightText(format.format(new Date(mJiLuEntity.jiluKaishiJihuaShijian)));
        tvEndTime.setRightText(format.format(new Date(mJiLuEntity.jiluJieshuJihuaShijian)));
        tvPatrolWay.setRightText(mJiLuEntity.jiluXianluName);
        tvPatrolPeople.setRightText(mJiLuEntity.jiluXunjianXungengrenName);
        tvPlanName.setRightText(mJiLuEntity.jiluJihuaName);
        btnDetailWay.setClickable(true);
        btnDetailWay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ConcreteCircuitActivity.class);
                intent.putExtra(ConcreteCircuitActivity.KEY_JILU_ID, mJiLuEntity.jiluId);
                intent.putExtra(ConcreteCircuitActivity.KEY_XIANLU_NAME, mJiLuEntity.jiluXianluName);
                intent.putExtra(ConcreteCircuitActivity.KEY_XIANLU_ID, mJiLuEntity.jiluXianluId);
                startActivity(intent);
            }
        });


        startPatrolView.setOnStartPatrolClickListener(new StartPatrolView.OnStartPatrolClickListener() {
            @Override
            public void onClick(View view) {
                onStartPatrol();
            }

            @Override
            public void onCountDownComplete() {
                tvTitle.post(new Runnable() {
                    @Override
                    public void run() {
                        tvTitle.setRightTextView("距离本次巡检结束还有 : ");
                    }
                });

            }
        });

        startPatrolView.setStartTime(mJiLuEntity.jiluKaishiJihuaShijian, mJiLuEntity.jiluJieshuJihuaShijian);
    }

    //开始巡检，去服务器获取本次巡检的巡检数据以及巡检事件
    private void onStartPatrol() {
        if (NfcPatrolUtil.hasRemainTask()) {
            startPatrol();
        } else if (supportNfc()) {
            AndPermission.with(getActivity())
                    .requestCode(0x10001)
                    .permission(Manifest.permission.NFC)
                    .rationale(new RationaleListener() {
                        @Override
                        public void showRequestPermissionRationale(int requestCode, Rationale rationale) {
                            AndPermission.rationaleDialog(getActivity(), rationale).show();
                        }
                    })
                    .callback(new PermissionListener() {
                        @Override
                        public void onSucceed(int requestCode, @android.support.annotation.NonNull List<String> grantPermissions) {
//                            tellServerStart();
                            loadShijian();
                        }

                        @Override
                        public void onFailed(int requestCode, @android.support.annotation.NonNull List<String> deniedPermissions) {
                            Toast.makeText(getContext(), "请开启NFC的权限", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .start();

        }
    }

    /**
     * 检测机型是否支持nfc
     * @return
     */
    private boolean supportNfc() {
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(getContext().getApplicationContext());
        if (nfcAdapter == null) {
            Toast.makeText(getContext(), "抱歉，该机器不支持NFC功能，请更换机器再开始", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!nfcAdapter.isEnabled()) {
            Toast.makeText(getContext(), "请在设置中开启NFC功能再开始", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * 载入事件
     */
    private void loadShijian() {
        swipeLayout.setRefreshing(true);
        Toast.makeText(getContext(), "正在获取事件数据", Toast.LENGTH_SHORT).show();
        Map<String, String> params = new HashMap<>();
        params.put("uuid", Contains.uuid);
        Disposable disposable = HttpAPIWrapper.getInstance().getShijian(params)
                .subscribe(new Consumer<XunJianShijianClassifyEntity>() {
                    @Override
                    public void accept(@NonNull XunJianShijianClassifyEntity entity) throws Exception {
                        if (entity.status == STATUS_CODE_OK) {
                            KLog.i("获取事件成功...来自网络!!!");
                            loadXunjianDianInfos(entity.data);
                        } else {
                            swipeLayout.setRefreshing(false);
                            onError(entity.status, entity.error);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        Toast.makeText(getContext(),"获取事件失败,请重新开始",Toast.LENGTH_SHORT).show();
                        swipeLayout.setRefreshing(false);
                    }
                });
        disposables.add(disposable);
    }

    private void loadXunjianDianInfos(final List<XunJianShijianClassifyEntity> shijianDatas) {
        Toast.makeText(getContext(), "正在获取巡检点数据", Toast.LENGTH_SHORT).show();
        Map<String, String> params = new HashMap<>();
        params.put("uuid", Contains.uuid);
        params.put("jiluId", mJiLuEntity.jiluId + "");
        params.put("xianluid", mJiLuEntity.jiluXianluId + "");
        //获取记录 然后获取事件
        Disposable disposable = HttpAPIWrapper.getInstance().startPatrol(params)
                .map(new Function<XunJianStartEntity, XunJianJiLuEntity>() {
                    @Override
                    public XunJianJiLuEntity apply(@NonNull XunJianStartEntity xianluEntity) throws Exception {
                        XunJianJiLuEntity jiLuEntity = mJiLuEntity;
                        jiLuEntity.status = xianluEntity.status;
                        jiLuEntity.msg = xianluEntity.msg;
                        jiLuEntity.MSG = xianluEntity.MSG;
                        jiLuEntity.error = xianluEntity.error;
                        jiLuEntity.success = xianluEntity.success;

                        if (xianluEntity.status == STATUS_CODE_OK) {
                            List<XunJianDianEntity> dianEntities = new ArrayList<XunJianDianEntity>();
                            for (XunJianXianLuXunJianDianEntity xianLuDianEntity : xianluEntity.data) {
                                XunJianDianEntity dianEntity = new XunJianDianEntity();
                                dianEntity.dianDizhi = xianLuDianEntity.dianAddress;
                                dianEntity.dianId = xianLuDianEntity.dianId;
                                dianEntity.dianNfcBianma = xianLuDianEntity.dianName;
                                dianEntity.xuliehao = xianLuDianEntity.dianPaixu;
                                dianEntity.dianJingweiduZuobiao = xianLuDianEntity.dianZuobiao;
                                dianEntity.jiluId = xianLuDianEntity.jiluId;
                                dianEntity.xunJianXiangDatas = xianLuDianEntity.listXunjianxiang == null ? new ArrayList<XunJianXiangEntity>() : xianLuDianEntity.listXunjianxiang;
                                dianEntity.xunJianShijianClassifies = new ArrayList<XunJianShijianClassifyEntity>();
                                dianEntity.jiluXianluName = jiLuEntity.jiluXianluName;
                                dianEntities.add(dianEntity);
                            }

                            Collections.sort(dianEntities, new Comparator<XunJianDianEntity>() {
                                @Override
                                public int compare(XunJianDianEntity o1, XunJianDianEntity o2) {
                                    return o1.xuliehao - o2.xuliehao;
                                }
                            });

                            for (XunJianDianEntity dianEntity : dianEntities) {
                                List<XunJianShijianClassifyEntity> shiJianEntities = new ArrayList<>();
                                for (XunJianShijianClassifyEntity classifyEntity : shijianDatas) {
                                    XunJianShijianClassifyEntity newClassifyEntity = new XunJianShijianClassifyEntity(classifyEntity);
                                    shiJianEntities.add(newClassifyEntity);
                                }
                                dianEntity.xunJianShijianClassifies = shiJianEntities;
                            }
                            jiLuEntity.xunJianDianDatas = dianEntities;
                            jiLuEntity.jiluXunjianXungengrenName = Contains.appLogin.getAdminNickName();
                            jiLuEntity.jiluTijiaoXungengrenId = Contains.appLogin.getAdminId();
                            jiLuEntity.jiluKaishiShijiShijian = System.currentTimeMillis();
                            jiLuEntity.jiluWancheng = 1;
                            jiLuEntity.userId = Contains.appLogin.getAdminId();
                            NfcPatrolUtil.writeStartPatrol(jiLuEntity.jiluId + "");
                            DBUtil dbUtil = DBUtil.getInstance(getContext().getApplicationContext());
                            boolean succeed = dbUtil.insertOneJiLu(jiLuEntity);
                            if (!succeed) {
                                jiLuEntity.status = -2;
                                jiLuEntity.error ="数据库建立失败，请联系工作人员";
                            }
                        }

                        return jiLuEntity;
                    }
                })
                .subscribe(new Consumer<XunJianJiLuEntity>() {
                    @Override
                    public void accept(@NonNull XunJianJiLuEntity jiLuEntity) throws Exception {
                        swipeLayout.setRefreshing(false);
                        if (jiLuEntity.status == STATUS_CODE_OK) {
                            Contains.jilu = jiLuEntity;
                            startPatrol();
                        }else if (jiLuEntity.status ==-2 && "数据库建立失败，请联系工作人员".equals(jiLuEntity.error)){
                            //Todo 通知服务器本次巡检本地数据库建立失败，需要重新获取数据
                            Toast.makeText(getContext(), jiLuEntity.error, Toast.LENGTH_SHORT).show();
                        } else {
                            onError(jiLuEntity.status, jiLuEntity.error);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        swipeLayout.setRefreshing(false);
                    }
                });
        disposables.add(disposable);
    }

    private void startPatrol() {
        startPatrolView.setXunjianStatusText("正在巡检");
        startActivity(new Intent(getActivity(), StartPatrolActivity.class));
    }
}
