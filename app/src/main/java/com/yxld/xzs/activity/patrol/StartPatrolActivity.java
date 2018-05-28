package com.yxld.xzs.activity.patrol;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yxld.xzs.R;
import com.yxld.xzs.adapter.PatrolFlowAdapter;
import com.yxld.xzs.contain.Contains;
import com.yxld.xzs.db.DBUtil;
import com.yxld.xzs.entity.BaseBack;
import com.yxld.xzs.entity.XunJianDianEntity;
import com.yxld.xzs.http.api.HttpAPIWrapper;
import com.yxld.xzs.utils.NfcPatrolUtil;
import com.yxld.xzs.utils.StringUitls;
import com.yxld.xzs.view.AlignLeftRightTextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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
 * 开始巡更
 */
public class StartPatrolActivity extends NfcBaseActivity {
    public static final int REQUEST_CODE_PATROL = 0x001;

    @BindView(R.id.tv_start_time)
    AlignLeftRightTextView tvStartTime;
    @BindView(R.id.tv_end_time)
    AlignLeftRightTextView tvEndTime;
    @BindView(R.id.tv_doing_time)
    AlignLeftRightTextView tvDoingTime;
    @BindView(R.id.tv_rest_check_point)
    AlignLeftRightTextView tvRestCheckPoint;
    @BindView(R.id.tv_uncompleted_record)
    AlignLeftRightTextView tvUncompletedRecord;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.tv_upload_data)
    TextView tvUploadData;


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                String diffTime = StringUitls.calculteDiffTime(System.currentTimeMillis() - Contains.jilu.jiluKaishiShijiShijian);
                tvDoingTime.setRightText(diffTime);
                mHandler.sendEmptyMessageDelayed(1, 1000);
            }
        }
    };
    private PatrolFlowAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_patrol);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        scrollView.smoothScrollTo(0, 0);
        recyclerView.setNestedScrollingEnabled(false);
        initData();
        initAdapter();
        mHandler.sendEmptyMessage(1);
        checkIsEmpty();
    }

    private void checkIsEmpty() {
        if(!hasRemain()){
            onPatrolComplete();
        }

    }

    private void initAdapter() {
        mAdapter = new PatrolFlowAdapter(Contains.jilu.xunJianDianDatas);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                Intent intent = new Intent(StartPatrolActivity.this, PatrolRecordActivity.class);
                intent.putExtra(PatrolRecordActivity.KEY_XUN_JIAN_DIAN_ID, i);
                startActivityForResult(intent, REQUEST_CODE_PATROL);
            }
        });
    }

    private void initData() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        tvStartTime.setRightText(format.format(new Date(Contains.jilu.jiluKaishiShijiShijian)));
        tvEndTime.setRightText(format.format(new Date(Contains.jilu.jiluJieshuJihuaShijian)));
        handlerEvent();
    }

    private void handlerEvent() {
        int remain = 0;
        int remainRecord = 0;
        for (XunJianDianEntity entity : Contains.jilu.xunJianDianDatas) {
            if (entity.hasChecked != 1 && entity.hasSaveData != 1) {
                remain += 1;
            }
            if (entity.hasSaveData != 1) {
                remainRecord += 1;
            }
        }
        tvRestCheckPoint.setRightText(remain + "");
        tvUncompletedRecord.setRightText(String.valueOf(remainRecord));
    }

    @Override
    public void onNfcTagChecked(XunJianDianEntity entity) {
        refreshData();
        Intent intent = new Intent(StartPatrolActivity.this, PatrolRecordActivity.class);
        intent.putExtra(PatrolRecordActivity.KEY_XUN_JIAN_DIAN_ID, Contains.jilu.xunJianDianDatas.indexOf(entity));
        startActivityForResult(intent, REQUEST_CODE_PATROL);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PATROL) {
            refreshData();
            if (resultCode == RESULT_OK) {
                if (!hasRemain()) {
                    onPatrolComplete();
                }
            } else if (resultCode == PatrolRecordActivity.RESULT_CODE_NOT_THIS_POINT) {
                int position = data.getExtras().getInt("nfc_position");
                Intent intent = new Intent(StartPatrolActivity.this, PatrolRecordActivity.class);
                intent.putExtra(PatrolRecordActivity.KEY_XUN_JIAN_DIAN_ID, position);
                startActivityForResult(intent, REQUEST_CODE_PATROL);
            }
        }
    }

    private void onPatrolComplete(){
        //跳转到完成界面
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                Contains.jilu.jiluJieshuShijiShijian = System.currentTimeMillis();
                if(Contains.jilu.jiluWenti!=-1 && Contains.jilu.jiluJieshuJihuaShijian<Contains.jilu.jiluJieshuShijiShijian){
                    Contains.jilu.jiluWenti =-1;
                }
                Contains.jilu.jiluWancheng = 2;
                Contains.jilu.jiluXunjianXungengrenName = Contains.appLogin.getAdminNickName();
                dbUtil.updateOneJiLu(Contains.jilu);
                NfcPatrolUtil.writeOnCompleted();
                e.onNext(1);
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {
                        startActivity(new Intent(StartPatrolActivity.this, PatrolCompleteActivity.class));
                        finish();
                    }
                });
    }

    private void refreshData() {
        initData();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeMessages(1);
        mHandler = null;
    }

    @Override
    public void onBackClick() {
        //检查还有巡检点未打卡
        //如果有的话
        if (hasRemain()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("提示:还有巡检点未巡检完成，确定退出?");
            builder.setNeutralButton("取消", null);
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.show();
        } else {
            finish();
        }


    }

    private boolean hasRemain() {
        if(Contains.jilu.xunJianDianDatas.size() ==0){
            return true;
        }
        for (XunJianDianEntity entity : Contains.jilu.xunJianDianDatas) {
            if (entity.hasSaveData != 1) {
                return true;
            }
        }
        return false;
    }

    @OnClick(R.id.tv_upload_data)
    public void onViewClicked() {
        if (hasRemain()) {
            Toast.makeText(this, "还有巡检点未打卡或者巡检项未保存", Toast.LENGTH_SHORT).show();
        } else {
            uploadData();
        }
    }

    private void uploadData() {
        showProgressDialog();
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                e.onNext(NfcPatrolUtil.handlerXunJianJiLu(Contains.jilu));
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
                        Toast.makeText(StartPatrolActivity.this, "上传失败,请重新再试", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void doUpload(String xunJianUploadEntity) {
        Map<String, String> params = new HashMap<>();
        params.put("uuid", Contains.uuid);
        params.put("result", xunJianUploadEntity);
        Disposable disposable = HttpAPIWrapper.getInstance().uploadPatrolResult(params)
                .subscribe(new Consumer<BaseBack>() {
                    @Override
                    public void accept(@NonNull BaseBack baseBack) throws Exception {
                        dismissProgressDialog();
                        if (baseBack.status == STATUS_CODE_OK) {
                            Toast.makeText(StartPatrolActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
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
        dbUtil.deleteJiluById(Contains.jilu.jiluId + "");
        Contains.jilu = null;
        finish();
    }
}
