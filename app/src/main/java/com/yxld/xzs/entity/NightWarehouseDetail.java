package com.yxld.xzs.entity;

import java.util.List;

/**
 * 夜间出库单详情
 * Created by William on 2017/11/28.
 */

public class NightWarehouseDetail extends BaseBack {

    private List<MaterialBean> rows;

    public List<MaterialBean> getRows() {
        return rows;
    }

    public void setRows(List<MaterialBean> rows) {
        this.rows = rows;
    }
}
