package com.yxld.xzs.http.exception;


/**
 * 作者：yishangfei on 2016/12/31 0031 10:16
 * 邮箱：yishangfei@foxmail.com
 */
public class ApiException extends Exception {
    // 异常处理，为速度，不必要设置getter和setter
    public int code;
    public String message;

    public ApiException(Throwable throwable, int code) {
        super(throwable);
        this.code = code;
    }
}
