package com.yxld.xzs.subscribers;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.yxld.xzs.http.exception.ApiException;
import com.yxld.xzs.utils.NetworskUtil;


/**
 * 作者：yishangfei on 2016/12/31 0031 10:16
 * 邮箱：yishangfei@foxmail.com
 */
public abstract class CommonSubscriber<T> extends BaseSubscriber<T> {
    public CommonSubscriber(Context context) {
        this.mContext = context;
    }

    private static final String TAG = CommonSubscriber.class.getSimpleName();
    private Context mContext;

    @Override
    public void onStart() {
        super.onStart();
//        if (!NetworskUtil.isNetworkAvailable(mContext)) {
//            Toast.makeText(mContext, "当前无网络，请检查网络情况", Toast.LENGTH_SHORT).show();
//            onCompleted();
//        } else {
//            Log.d(TAG, "network available");
//        }
    }

    @Override
    public void onCompleted() {
        Log.d(TAG, "onCompleted~ ");
    }

    @Override
    protected void onError(ApiException ex) {
        Log.e(TAG, "onError: " + ex.message + "code: " + ex.code);
        Toast.makeText(mContext, ex.message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public abstract void onNext(T t);
}
