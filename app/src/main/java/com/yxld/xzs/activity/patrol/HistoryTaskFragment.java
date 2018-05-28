package com.yxld.xzs.activity.patrol;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bigkoo.alertview.AlertView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.socks.library.KLog;
import com.yxld.xzs.R;
import com.yxld.xzs.contain.Contains;
import com.yxld.xzs.db.DBUtil;
import com.yxld.xzs.entity.BaseBack;
import com.yxld.xzs.entity.XunJianJiLuEntity;
import com.yxld.xzs.entity.XunJianJiluRemoteEntity;
import com.yxld.xzs.http.api.HttpAPIWrapper;
import com.yxld.xzs.utils.NfcPatrolUtil;
import com.yxld.xzs.utils.StringUitls;
import com.yxld.xzs.utils.ToastUtil;
import com.yxld.xzs.utils.UIUtils;
import com.yxld.xzs.view.AlignLeftRightTextView;
import com.yxld.xzs.view.LeftRightTextView;
import com.zhy.autolayout.AutoLinearLayout;

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
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 * 历史任务,包含两个任务,一个是本地的历史任务,一个是服务器上的历史任务
 */
public class HistoryTaskFragment extends BasePatrolFragment implements BaseQuickAdapter.RequestLoadMoreListener {


    @BindView(R.id.recycler_local_history)
    RecyclerView recyclerLocalHistory;
    @BindView(R.id.recycler_remote_history)
    RecyclerView recyclerRemoteHistory;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;
    Unbinder unbinder;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    private HistoryTaskAdapter mLocalHistoryAdapter;
    private HistoryTaskAdapter mRemoteHistoryAdapter;
    private static final int ONE_PAGE_SIZE = 5;
    private int mNextPage = 1;
    private int mTotalRemoteJilu;

    public HistoryTaskFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history_task, container, false);
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
        UIUtils.configSwipeRefreshLayoutColors(swipeLayout);
        swipeLayout.setOnRefreshListener(this);
        scrollView.smoothScrollTo(0, 0);
        recyclerLocalHistory.setNestedScrollingEnabled(false);
        recyclerRemoteHistory.setNestedScrollingEnabled(false);

        mLocalHistoryAdapter = new HistoryTaskAdapter(new ArrayList<XunJianJiLuEntity>(), true);
        recyclerLocalHistory.setAdapter(mLocalHistoryAdapter);

        mLocalHistoryAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {

                if ("check".equals(view.getTag())) {
                    Intent intent = new Intent(getActivity(), HistoryRecordActivity.class);
                    intent.putExtra(HistoryRecordActivity.KEY_JILUID, mLocalHistoryAdapter.getData().get(i).jiluId);
                    intent.putExtra(HistoryRecordActivity.KEY_POSITION, i);
                    startActivityForResult(intent, 0x2001);
                } else if ("upload".equals(view.getTag())) {
                    //// TODO: 2017/10/23 这里会崩溃！！！
                    try {
                        XunJianJiLuEntity xunJianJiLuEntity = mLocalHistoryAdapter.getData().get(i);
                        KLog.i(xunJianJiLuEntity.jiluId);
                        uploadData(xunJianJiLuEntity.jiluId + "", i);

                    } catch (Exception e) {
                        e.printStackTrace();
                        ToastUtil.show(getActivity(), "该条记录上传出错，请将该手机和该条记录移交给创欣物业开发人员查找具体原因");
                    }
                }
            }
        });


        mRemoteHistoryAdapter = new HistoryTaskAdapter(new ArrayList<XunJianJiLuEntity>(), false);
        recyclerRemoteHistory.setAdapter(mRemoteHistoryAdapter);
        mRemoteHistoryAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {

            @Override
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                XunJianJiLuEntity jiLuEntity = mRemoteHistoryAdapter.getData().get(i);
                if (jiLuEntity.getJiluWancheng()==-2){
                    showDialog();
                    return;
                }
                if ("0秒".equals(StringUitls.calculteDiffTime(jiLuEntity.jiluJieshuShijiShijian - jiLuEntity.jiluKaishiShijiShijian))){
                    showDialog2();
                    return;
                }
                Intent intent = new Intent(getActivity(), RemoteHistoryDetailActivity.class);
                intent.putExtra(RemoteHistoryDetailActivity.KEY_JILU_ID, jiLuEntity.jiluId);
                intent.putExtra(RemoteHistoryDetailActivity.KEY_XIANLU_ID, jiLuEntity.jiluXianluId);
                startActivity(intent);
            }

        });
        mRemoteHistoryAdapter.setOnLoadMoreListener(this, recyclerRemoteHistory);

    }
    private void showDialog() {
        new AlertView.Builder().setTitle("该巡检任务已取消！")
                .setStyle(AlertView.Style.Alert)
                .setContext(getActivity()).setCancelText("确定")
                .build()
                .show();
    }

    /**
     * 用时0秒
     */
    private void showDialog2() {
        new AlertView.Builder().setTitle("该任务未执行，巡检结果为空，无法查看！")
                .setStyle(AlertView.Style.Alert)
                .setContext(getActivity()).setCancelText("确定")
                .build()
                .show();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0x2001 && resultCode == 0x2001) {
            int position = data.getExtras().getInt(HistoryRecordActivity.KEY_POSITION);
            refreshData(position);
        }
    }

    private void uploadData(final String jiluid, final int position) {
        swipeLayout.setRefreshing(true);
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                DBUtil dbUtil = DBUtil.getInstance(getContext().getApplicationContext());
                XunJianJiLuEntity jiLuById = dbUtil.getJiLuById(Contains.appLogin.getAdminId() + "", jiluid);
                e.onNext(NfcPatrolUtil.handlerXunJianJiLu(jiLuById));
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        doUpload(s, position);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        swipeLayout.setRefreshing(false);
                    }
                });
    }

    private void doUpload(String s, final int position) {
        Map<String, String> params = new HashMap<>();
        params.put("uuid", Contains.uuid);
        params.put("result", s);
        Disposable disposable = HttpAPIWrapper.getInstance().uploadPatrolResult(params)
                .subscribe(new Consumer<BaseBack>() {
                    @Override
                    public void accept(@NonNull BaseBack baseBack) throws Exception {
                        swipeLayout.setRefreshing(false);
                        if (baseBack.status == STATUS_CODE_OK) {
                            Toast.makeText(getContext(), "上传成功", Toast.LENGTH_SHORT).show();
                            refreshData(position);
                        } else {
                            onError(baseBack.status, baseBack.error);
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

    private void refreshData(int position) {
        XunJianJiLuEntity jiLuEntity = mLocalHistoryAdapter.getData().get(position);
        DBUtil dbUtil = DBUtil.getInstance(getContext().getApplicationContext());
        dbUtil.deleteJiluById(jiLuEntity.jiluId + "");
        mLocalHistoryAdapter.remove(position);


        swipeLayout.setRefreshing(true);
        mNextPage = 1;
        loadRemoteJilu();
    }

    @Override
    public void fetchData() {
        swipeLayout.setRefreshing(true);
        loadLocalData();
        loadRemoteJilu();
    }

    private void loadLocalData() {
        Disposable disposable = Observable.create(new ObservableOnSubscribe<List<XunJianJiLuEntity>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<XunJianJiLuEntity>> e) throws Exception {
                DBUtil dbUtil = DBUtil.getInstance(getContext().getApplicationContext());
                List<XunJianJiLuEntity> jiLuEntities = dbUtil.getAllCompleteXunJianJiLuByUserId(Contains.appLogin.getAdminId() + "");
                Collections.sort(jiLuEntities, new Comparator<XunJianJiLuEntity>() {
                    @Override
                    public int compare(XunJianJiLuEntity o1, XunJianJiLuEntity o2) {
                        return (int) (o2.jiluJieshuShijiShijian - o1.jiluJieshuShijiShijian);
                    }
                });
                e.onNext(jiLuEntities);
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<XunJianJiLuEntity>>() {
                    @Override
                    public void accept(@NonNull List<XunJianJiLuEntity> xunJianJiLuEntities) throws Exception {
                        mLocalHistoryAdapter.setNewData(xunJianJiLuEntities);
//                        if (xunJianJiLuEntities.size() == 0) {
//                            Toast.makeText(getContext(), "未查询到任何本地未提交的任务", Toast.LENGTH_SHORT).show();
//                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        swipeLayout.setRefreshing(false);
                        Toast.makeText(getContext(), "获取本地未提交的任务失败", Toast.LENGTH_SHORT).show();
                    }
                });
        disposables.add(disposable);
    }


    private void loadRemoteJilu() {
        Map<String, String> params = new HashMap<>();
        params.put("uuid", Contains.uuid);
        params.put("rows", ONE_PAGE_SIZE + "");
        params.put("page", mNextPage + "");
        HttpAPIWrapper.getInstance().getAllRomoteJilu(params)
                .subscribe(new Consumer<XunJianJiluRemoteEntity>() {
                    @Override
                    public void accept(@NonNull XunJianJiluRemoteEntity entity) throws Exception {
                        swipeLayout.setRefreshing(false);
                        mRemoteHistoryAdapter.loadMoreComplete();
                        if (entity.status == STATUS_CODE_OK) {
                            mTotalRemoteJilu = entity.total;
                            if (mNextPage == 1) {
                                mRemoteHistoryAdapter.setNewData(entity.data);
                            } else {
                                mRemoteHistoryAdapter.addData(entity.data);
                            }
                            if (mRemoteHistoryAdapter.getData().size() < entity.total) {
                                mNextPage++;
                            }
                            if (entity.data == null || entity.data.size() == 0) {
                                Toast.makeText(getActivity(), "没有更多数据了", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            onError(entity.status, entity.error);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        if (swipeLayout != null) {
                            swipeLayout.setRefreshing(false);
                        }
                    }
                });
    }

    @Override
    public void onRefresh() {
        mNextPage = 1;
        fetchData();
    }

    @Override
    public void onLoadMoreRequested() {
        if (mRemoteHistoryAdapter.getData().size() < ONE_PAGE_SIZE || mRemoteHistoryAdapter.getData().size() >= mTotalRemoteJilu) {
            mRemoteHistoryAdapter.loadMoreEnd(false);
            return;
        }
        loadRemoteJilu();
    }

    private static class HistoryTaskAdapter extends BaseQuickAdapter<XunJianJiLuEntity, BaseViewHolder> {
        private boolean mIsLocalData;
        private SimpleDateFormat mFormat;

        public HistoryTaskAdapter(@Nullable List<XunJianJiLuEntity> data, boolean isLocalData) {
            super(R.layout.item_history_task, data);
            mIsLocalData = isLocalData;
            mFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, XunJianJiLuEntity entity) {
            baseViewHolder.addOnClickListener(R.id.tv_time_and_check_result)
                    .setTag(R.id.tv_time_and_check_result, "check");

            AutoLinearLayout autoLinear = baseViewHolder.getView(R.id.auto_linear);
            AlignLeftRightTextView tvTime = baseViewHolder.getView(R.id.tv_time_and_check_result);
            LeftRightTextView tvPatrolWay = baseViewHolder.getView(R.id.tv_patrol_way);
            LeftRightTextView tvUseTime = baseViewHolder.getView(R.id.tv_patrol_use_time);
            LeftRightTextView tvPeople = baseViewHolder.getView(R.id.tv_patrol_people);
            LeftRightTextView tvJihua = baseViewHolder.getView(R.id.tv_xunjian_jihua);

            tvTime.setLeftText(mFormat.format(new Date(entity.jiluKaishiJihuaShijian)));
            tvPatrolWay.setRightText(entity.jiluXianluName);
            String diffTime = StringUitls.calculteDiffTime(entity.jiluJieshuShijiShijian - entity.jiluKaishiShijiShijian);
            tvUseTime.setRightText(diffTime);
            tvPeople.setRightText(entity.jiluXunjianXungengrenName);
            tvJihua.setRightText(entity.jiluJihuaName);
            if (entity.getJiluWancheng() == -2) {
                autoLinear.setBackgroundColor(mContext.getResources().getColor(R.color.color_b4b4b4));
            }else {
                autoLinear.setBackgroundColor(mContext.getResources().getColor(R.color.white));
            }
            if (mIsLocalData) {
                baseViewHolder.setVisible(R.id.tv_upload_data, true)
                        .addOnClickListener(R.id.tv_upload_data)
                        .setTag(R.id.tv_upload_data, "upload");

                tvTime.setBadge("!");
            }

        }
    }
}
