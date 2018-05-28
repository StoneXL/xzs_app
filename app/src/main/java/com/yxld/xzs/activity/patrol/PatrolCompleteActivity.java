package com.yxld.xzs.activity.patrol;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yxld.xzs.R;
import com.yxld.xzs.base.BaseActivity;
import com.yxld.xzs.contain.Contains;
import com.yxld.xzs.db.DBUtil;
import com.yxld.xzs.entity.BaseBack;
import com.yxld.xzs.http.api.HttpAPIWrapper;
import com.yxld.xzs.utils.NfcPatrolUtil;

import java.util.HashMap;
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
 * 巡更完成的界面
 */
public class PatrolCompleteActivity extends BaseActivity {


    @BindView(R.id.tv_save_data)
    TextView tvSaveData;
    @BindView(R.id.tv_upload_data)
    TextView tvUploadData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patrol_complete);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.tv_save_data, R.id.tv_upload_data})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_save_data:
                saveData();
                break;
            case R.id.tv_upload_data:
                uploadData();
                break;
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
                        Toast.makeText(PatrolCompleteActivity.this,"上传失败,请重新再试",Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(PatrolCompleteActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
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
        dbUtil.deleteJiluById(Contains.jilu.jiluId+"");
        Contains.jilu = null;
        finish();
    }

    private void saveData() {
        Contains.jilu = null;
        finish();
    }

    @Override
    public void finish() {
        sendBroadcast(new Intent(getResources().getString(R.string.nfc_patrol_complete)));
        super.finish();
    }
}
