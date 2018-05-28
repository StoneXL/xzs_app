package com.yxld.xzs.transformer;


import com.yxld.xzs.base.BaseResultEntity2;
import com.yxld.xzs.http.exception.ExceptionEngine;

import rx.Observable;
import rx.functions.Func1;

/**
 * 对错误处理
 * 作者：yishangfei on 2016/12/31 0031 10:16
 * 邮箱：yishangfei@foxmail.com
 */
public class ErrorTransformer1<T> implements Observable.Transformer<BaseResultEntity2<T>, T>{

    @Override
    public Observable<T> call(Observable<BaseResultEntity2<T>> responseObservable) {
        return responseObservable.map(new Func1<BaseResultEntity2<T>, T>() {
            @Override
            public T call(BaseResultEntity2<T> httpResult) {
                // 通过对返回码进行业务判断决定是返回错误还是正常取数据
//                if (httpResult.getCode() != 200) throw new RuntimeException(httpResult.getMessage());
                return httpResult.getUptoken();
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

    public static <T> ErrorTransformer1<T> create() {
        return new ErrorTransformer1<>();
    }

    private static ErrorTransformer1 instance = null;

    private ErrorTransformer1(){
    }
    /**
     * 双重校验锁单例(线程安全)
     */
    public static <T>ErrorTransformer1<T> getInstance() {
        if (instance == null) {
            synchronized (ErrorTransformer1.class) {
                if (instance == null) {
                    instance = new ErrorTransformer1<>();
                }
            }
        }
        return instance;
    }
}
