package com.yxld.xzs.transformer;
import com.yxld.xzs.base.BaseResultEntity1;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yishangfei on 2017/2/27 0027.
 * 邮箱：yishangfei@foxmail.com
 */
public class DorderTransformer<T>
        implements Observable.Transformer<BaseResultEntity1<T>, T> {

    @Override
    public Observable<T> call(Observable<BaseResultEntity1<T>> observable) {
        return observable
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .compose(EorderTransformer.<T>getInstance())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
