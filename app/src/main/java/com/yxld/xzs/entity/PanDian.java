package com.yxld.xzs.entity;

/**
 * @author xlei
 * @Date 2018/5/28.
 */

public class PanDian extends BaseBack {

    /**
     * pandianId : 2
     * pandianZhuangtai : 1
     */

    private int pandianId;
    private int pandianZhuangtai;
    private  PanDian data;

    public int getPandianId() {
        return pandianId;
    }

    public void setPandianId(int pandianId) {
        this.pandianId = pandianId;
    }

    public int getPandianZhuangtai() {
        return pandianZhuangtai;
    }

    public void setPandianZhuangtai(int pandianZhuangtai) {
        this.pandianZhuangtai = pandianZhuangtai;
    }

    public PanDian getData() {
        return data;
    }

    public void setData(PanDian data) {
        this.data = data;
    }
}
