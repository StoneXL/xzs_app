package com.yxld.xzs.subscribers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.yxld.xzs.http.exception.ApiException;
import com.yxld.xzs.utils.DialogHelper;
import com.yxld.xzs.utils.NetworskUtil;

/**
 * 作者：yishangfei on 2016/12/31 0031 10:16
 * 邮箱：yishangfei@foxmail.com
 */
public abstract class RxSubscriber<T> extends BaseSubscriber<T> {
    public RxSubscriber(Context context) {
        this.mContext = context;
    }
    private static final String TAG = RxSubscriber.class.getSimpleName();
    private Context mContext;
    @Override
    public void onStart() {
        super.onStart();
//         if  NetworkAvailable no !   must to call onCompleted
        if (!NetworskUtil.isNetworkAvailable(mContext)) {
            Toast.makeText(mContext, "当前无网络，请检查网络情况", Toast.LENGTH_SHORT).show();
            onCompleted();
        } else {
//            DialogHelper.showProgressDlg(mContext, "正在加载数据");
        }
    }

    @Override
    public void onCompleted() {
//        DialogHelper.stopProgressDlg();
    }

    @Override
    protected void onError(ApiException ex) {
        DialogHelper.stopProgressDlg();
        Log.d(TAG, "onError: " + ex.message + "code: " + ex.code);
        Toast.makeText(mContext, ex.message , Toast.LENGTH_SHORT).show();
    }

    @Override
    public abstract void onNext(T t);
}
