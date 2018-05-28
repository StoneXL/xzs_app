package com.yxld.xzs.transformer;


import com.yxld.xzs.base.BaseResultEntity3;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 作者：yishangfei on 2016/12/31 0031 10:16
 * 邮箱：yishangfei@foxmail.com
 */
public class DefaultTransformer2<T>
        implements Observable.Transformer<BaseResultEntity3<T>, T> {

    @Override
    public Observable<T> call(Observable<BaseResultEntity3<T>> observable) {
        return observable
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .compose(ErrorTransformer2.<T>getInstance())
                .observeOn(AndroidSchedulers.mainThread());
    }
}