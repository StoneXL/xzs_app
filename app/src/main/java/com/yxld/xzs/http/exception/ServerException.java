package com.yxld.xzs.http.exception;

/**
 * 作者：yishangfei on 2016/12/31 0031 10:16
 * 邮箱：yishangfei@foxmail.com
 */
public class ServerException extends RuntimeException {
    // 异常处理，为速度，不必要设置getter和setter
    public int code;
    public String message;

    public ServerException(String message, int code) {
        super(message);
        this.code = code;
        this.message = message;
    }
}
