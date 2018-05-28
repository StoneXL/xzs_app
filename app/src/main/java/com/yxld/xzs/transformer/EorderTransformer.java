package com.yxld.xzs.transformer;

import com.orhanobut.logger.Logger;
import com.yxld.xzs.base.BaseResultEntity;
import com.yxld.xzs.base.BaseResultEntity1;
import com.yxld.xzs.entity.AppHome;
import com.yxld.xzs.http.exception.ErrorType;
import com.yxld.xzs.http.exception.ExceptionEngine;
import com.yxld.xzs.http.exception.ServerException;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by yishangfei on 2017/2/27 0027.
 * 邮箱：yishangfei@foxmail.com
 */
public class EorderTransformer<T> implements Observable.Transformer<BaseResultEntity1<T>, T>{

    @Override
    public Observable<T> call(Observable<BaseResultEntity1<T>> responseObservable) {
        return responseObservable.map(new Func1<BaseResultEntity1<T>, T>() {
            @Override
            public T call(BaseResultEntity1<T> httpResult) {
                // 通过对返回码进行业务判断决定是返回错误还是正常取数据
                if (httpResult.getStatus() != ErrorType.SUCCESS)
                    throw new ServerException(httpResult.getMSG(), httpResult.getStatus());
                AppHome home = new AppHome(httpResult.getRow(),httpResult.getSaleList());
                return (T)home;
            }
        }).onErrorResumeNext(new Func1<Throwable, Observable<? extends T>>() {
            @Override
            public Observable<? extends T> call(Throwable throwable) {
                //ExceptionEngine为处理异常的驱动器
                throwable.printStackTrace();
                return Observable.error(ExceptionEngine.handleException(throwable));
            }
        });
    }

    public static <T> EorderTransformer<T> create() {
        return new EorderTransformer<>();
    }

    private static EorderTransformer instance = null;

    private EorderTransformer(){
    }
    /**
     * 双重校验锁单例(线程安全)
     */
    public static <T>EorderTransformer<T> getInstance() {
        if (instance == null) {
            synchronized (EorderTransformer.class) {
                if (instance == null) {
                    instance = new EorderTransformer<>();
                }
            }
        }
        return instance;
    }
}
