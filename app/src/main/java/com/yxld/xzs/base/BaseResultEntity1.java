package com.yxld.xzs.base;

import com.yxld.xzs.entity.AppOrder;
import com.yxld.xzs.entity.AppSaleList;

import java.util.List;

/**
 * 回调信息统一封装类
 * 作者：yishangfei on 2016/12/31 0031 10:16
 * 邮箱：yishangfei@foxmail.com
 */
public class BaseResultEntity1<T> {
    //    提示信息
    private String MSG;
    //  判断标示
    private int status;

    private List<AppOrder> row;
    private List<AppSaleList> saleList;

    public String getMSG() {
        return MSG;
    }

    public void setMSG(String MSG) {
        this.MSG = MSG;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<AppOrder> getRow() {
        return row;
    }

    public void setRow(List<AppOrder> row) {
        this.row = row;
    }

    public List<AppSaleList> getSaleList() {
        return saleList;
    }

    public void setSaleList(List<AppSaleList> saleList) {
        this.saleList = saleList;
    }

    @Override
    public String toString() {
        return "BaseResultEntity{" +
                "MSG='" + MSG + '\'' +
                ", status=" + status +
                ", row=" + row +
                '}';
    }
}
