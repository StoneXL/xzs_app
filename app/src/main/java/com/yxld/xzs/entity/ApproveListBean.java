package com.yxld.xzs.entity;

import java.util.List;

/**
 * Created by William on 2017/11/25.
 */

public class ApproveListBean extends BaseBack {

    private List<ApproveBean> rows;
    private String total;

    public List<ApproveBean> getRows() {
        return rows;
    }

    public void setRows(List<ApproveBean> rows) {
        this.rows = rows;
    }

}
