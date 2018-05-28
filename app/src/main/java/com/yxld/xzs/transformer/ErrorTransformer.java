package com.yxld.xzs.transformer;


import com.yxld.xzs.base.BaseResultEntity;
import com.yxld.xzs.http.exception.ErrorType;
import com.yxld.xzs.http.exception.ExceptionEngine;
import com.yxld.xzs.http.exception.ServerException;

import rx.Observable;
import rx.functions.Func1;

/**
 * 对错误处理
 * 作者：yishangfei on 2016/12/31 0031 10:16
 * 邮箱：yishangfei@foxmail.com
 */
public class ErrorTransformer<T> implements Observable.Transformer<BaseResultEntity<T>, T>{

    @Override
    public Observable<T> call(Observable<BaseResultEntity<T>> responseObservable) {
        return responseObservable.map(new Func1<BaseResultEntity<T>, T>() {
            @Override
            public T call(BaseResultEntity<T> httpResult) {
                // 通过对返回码进行业务判断决定是返回错误还是正常取数据
//                if (httpResult.getCode() != 200) throw new RuntimeException(httpResult.getMessage());
                if (httpResult.getStatus() != ErrorType.SUCCESS) {
                    throw new ServerException(httpResult.getMSG(), httpResult.getStatus());

                }
                return httpResult.getRow();
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

    public static <T> ErrorTransformer<T> create() {
        return new ErrorTransformer<>();
    }

    private static ErrorTransformer instance = null;

    private ErrorTransformer(){
    }
    /**
     * 双重校验锁单例(线程安全)
     */
    public static <T>ErrorTransformer<T> getInstance() {
        if (instance == null) {
            synchronized (ErrorTransformer.class) {
                if (instance == null) {
                    instance = new ErrorTransformer<>();
                }
            }
        }
        return instance;
    }
}
