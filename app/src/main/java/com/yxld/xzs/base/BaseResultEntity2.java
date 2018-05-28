package com.yxld.xzs.base;

/**
 * 回调信息统一封装类
 * 作者：yishangfei on 2016/12/31 0031 10:16
 * 邮箱：yishangfei@foxmail.com
 */
public class BaseResultEntity2<T> {

    //显示数据（用户需要关心的数据）
    private T uptoken;

    public T getUptoken() {
        return uptoken;
    }

    public void setUptoken(T uptoken) {
        this.uptoken = uptoken;
    }
}




