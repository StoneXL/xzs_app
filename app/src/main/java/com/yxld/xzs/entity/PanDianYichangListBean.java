package com.yxld.xzs.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by William on 2018/5/28.
 */

public class PanDianYichangListBean extends BaseBack{

    /**
     * data : [{"yichangId":2,"yichangPandianId":2,"yichangKucunId":1,"yichangShuliang":2,"yichangZhuangtai":2,
     * "wuziId":1,"wuziBianhao":"66","wuziPinpai":"66","wuziGuige":"66","wuziDanwei":"阿斯顿","wuziMingcheng":"66"},
     * {"yichangId":3,"yichangPandianId":2,"yichangKucunId":2,"yichangShuliang":1,"yichangZhuangtai":2,"wuziId":2,
     * "wuziBianhao":"213123","wuziPinpai":"哇哈哈","wuziGuige":"1*4","wuziDanwei":"瓶","wuziMingcheng":"AD钙奶"}]
     * total : 2
     * error : null
     */

    private int total;

    private List<PanDianYiChangBean> data;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<PanDianYiChangBean> getData() {
        return data;
    }

    public void setData(List<PanDianYiChangBean> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "PanDianYichangListBean{" +
                "total=" + total +
                ", data=" + data +
                '}';
    }
}
