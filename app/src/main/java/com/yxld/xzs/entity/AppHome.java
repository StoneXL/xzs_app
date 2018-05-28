package com.yxld.xzs.entity;

import java.util.List;

/**
 * Created by yishangfei on 2017/2/27 0027.
 * 邮箱：yishangfei@foxmail.com
 */
public class AppHome {
    private List<AppOrder> row;
    private List<AppSaleList> saleList;

    public AppHome(List<AppOrder> row, List<AppSaleList> saleList) {
        this.row = row;
        this.saleList = saleList;
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
}
