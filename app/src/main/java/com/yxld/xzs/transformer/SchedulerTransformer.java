package com.yxld.xzs.transformer;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 作者：yishangfei on 2016/12/31 0031 10:16
 * 邮箱：yishangfei@foxmail.com
 */
public class SchedulerTransformer<T> implements Observable.Transformer<T, T> {
    @Override
    public Observable<T> call(Observable<T> observable) {
        return observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> SchedulerTransformer<T> create() {
        return new SchedulerTransformer<>();
    }

    private static SchedulerTransformer instance = null;

    private SchedulerTransformer(){
    }
    /**
     * 双重校验锁单例(线程安全)
     */
    public static<T> SchedulerTransformer<T> getInstance() {
        if (instance == null) {
            synchronized (SchedulerTransformer.class) {
                if (instance == null) {
                    instance = new SchedulerTransformer<>();
                }
            }
        }
        return instance;
    }
}