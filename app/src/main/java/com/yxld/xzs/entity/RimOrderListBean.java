package com.yxld.xzs.entity;

import java.util.List;

/**
 * 欣周边 订单列表
 * Created by William on 2018/1/9.
 */

public class RimOrderListBean extends BaseBack {
//    private String msg;
//    private int status;//1 是成功 -1 失败
//    private String success;
    private int total;
    private List<RimOrderBean> data;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<RimOrderBean> getData() {
        return data;
    }

    public void setData(List<RimOrderBean> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RimOrderListBean{" +
                "total=" + total +
                ", data=" + data +
                '}';
    }
}
