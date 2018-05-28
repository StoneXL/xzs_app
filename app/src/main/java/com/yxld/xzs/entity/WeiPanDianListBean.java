package com.yxld.xzs.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by William on 2018/5/28.
 */

public class WeiPanDianListBean extends BaseBack {

    /**
     * data : [{"detailId":1,"detailKucunId":1,"detailPandian":1,"detailShengchanRiqi":"2018-02-27",
     * "detailYouxiaoqi":11,"detailGuoqiRiqi":"2018-03-10","detailShuliang":9,"kucunXiangmuMingcheng":null,
     * "kucunFenlei":null,"kucunZhuangtai":null,"wuziBianhao":"66","wuziPinpai":"66","wuziGuige":"66",
     * "wuziDanwei":"阿斯顿","wuziMingcheng":"66","wuziDanjia":11,"wuziFenlei":1,"wuziChaifen":null}]
     * total : 1
     * error : null
     */

    private int total;

    private List<PanDianBean> data;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<PanDianBean> getData() {
        return data;
    }

    public void setData(List<PanDianBean> data) {
        this.data = data;
    }

}
