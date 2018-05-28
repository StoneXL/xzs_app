package com.yxld.xzs.subscribers;

import com.yxld.xzs.http.exception.ApiException;

import rx.Subscriber;

/**
 * 作者：yishangfei on 2016/12/31 0031 10:16
 * 邮箱：yishangfei@foxmail.com
 */
public abstract class BaseSubscriber<T> extends Subscriber<T> {
    @Override
    public void onError(Throwable e) {
        if(e instanceof ApiException){
            onError((ApiException)e);
        }else{
            onError(new ApiException(e,1000));
        }
    }

    /**
     * 错误回调
     */
    protected abstract void onError(ApiException ex);
}