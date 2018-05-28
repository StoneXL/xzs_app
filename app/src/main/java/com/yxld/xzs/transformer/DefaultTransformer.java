package com.yxld.xzs.transformer;


import com.yxld.xzs.base.BaseResultEntity;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 作者：yishangfei on 2016/12/31 0031 10:16
 * 邮箱：yishangfei@foxmail.com
 */
public class DefaultTransformer<T>
        implements Observable.Transformer<BaseResultEntity<T>, T> {

    @Override
    public Observable<T> call(Observable<BaseResultEntity<T>> observable) {
        return observable
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .compose(ErrorTransformer.<T>getInstance())
                .observeOn(AndroidSchedulers.mainThread());
    }
}