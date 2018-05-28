package com.yxld.xzs.transformer;


import com.yxld.xzs.base.BaseResultEntity4;
import com.yxld.xzs.http.exception.ExceptionEngine;

import rx.Observable;
import rx.functions.Func1;

/**
 * 对错误处理
 * 作者：yishangfei on 2016/12/31 0031 10:16
 * 邮箱：yishangfei@foxmail.com
 */
public class ErrorTransformer4<T> implements Observable.Transformer<BaseResultEntity4<T>, T>{

    @Override
    public Observable<T> call(Observable<BaseResultEntity4<T>> responseObservable) {
        return responseObservable.map(new Func1<BaseResultEntity4<T>, T>() {
            @Override
            public T call(BaseResultEntity4<T> httpResult) {
                // 通过对返回码进行业务判断决定是返回错误还是正常取数据
//                if (httpResult.getCode() != 200) throw new RuntimeException(httpResult.getMessage());
                return httpResult.getBaseResultEntity4();
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

    public static <T> ErrorTransformer4<T> create() {
        return new ErrorTransformer4<>();
    }

    private static ErrorTransformer4 instance = null;

    private ErrorTransformer4(){
    }
    /**
     * 双重校验锁单例(线程安全)
     */
    public static <T>ErrorTransformer4<T> getInstance() {
        if (instance == null) {
            synchronized (ErrorTransformer4.class) {
                if (instance == null) {
                    instance = new ErrorTransformer4<T>();
                }
            }
        }
        return instance;
    }
}
