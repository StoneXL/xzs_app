package com.yxld.xzs.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by William on 2017/11/16.
 */

public class ApproveBean implements MultiItemEntity {

    public static final int CLICK_ITEM_VIEW = 1;
    public static final int CLICK_ITEM_CHILD_VIEW = 2;
    public static final int LONG_CLICK_ITEM_VIEW = 3;
    public static final int LONG_CLICK_ITEM_CHILD_VIEW = 4;

//    private int Type;
    /**
     * shenqingren : 吴惠娟
     * baoxiuDanhao : 7753821410948445
     * xiangmuName : 云顶翠峰
     * shenpiName : 报修材料审批
     * id : 307
     * time : 2017-08-24 09:31:44
     * shijian : 1503538304000
     * shenpiLeixin : 未出库
     * status : 1
     */

    private String shenqingren;//申请人
    private String baoxiuDanhao;//报修单号
    private String xiangmuName;//项目名
    private String shenpiName;//审批名称:报修材料审批，报废审批
    private int shenpiId;//id
    private String time;//时间
    private long shijian;//毫秒值时间
    private String shenpiLeixin;//审批类型:未出库，已出库，待审批，已审批
    private int status;//0为已审批1为待审批
    private String shengouLeixinName;//申购审批类型名称:SQB申请表、BCB补充申购表
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int getItemType() {
        /*switch (getShenpiName()) {
            case "报修材料审批":
                setType(1);
                break;
            case "报废审批":
                setType(2);
                break;
            case "申购审批":
                setType(3);
                break;
            case "报修审批":
                setType(4);
                break;
        }*/
        return type;
    }

    public String getShenqingren() {
        return shenqingren;
    }

    public void setShenqingren(String shenqingren) {
        this.shenqingren = shenqingren;
    }

    public String getBaoxiuDanhao() {
        return baoxiuDanhao;
    }

    public void setBaoxiuDanhao(String baoxiuDanhao) {
        this.baoxiuDanhao = baoxiuDanhao;
    }

    public String getXiangmuName() {
        return xiangmuName;
    }

    public void setXiangmuName(String xiangmuName) {
        this.xiangmuName = xiangmuName;
    }

    public String getShenpiName() {
        return shenpiName;
    }

    public void setShenpiName(String shenpiName) {
        this.shenpiName = shenpiName;
    }

    public int getShenpiId() {
        return shenpiId;
    }

    public void setShenpiId(int shenpiId) {
        this.shenpiId = shenpiId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getShijian() {
        return shijian;
    }

    public void setShijian(long shijian) {
        this.shijian = shijian;
    }

    public String getShenpiLeixin() {
        return shenpiLeixin;
    }

    public void setShenpiLeixin(String shenpiLeixin) {
        this.shenpiLeixin = shenpiLeixin;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getShengouLeixinName() {
        return shengouLeixinName;
    }

    public void setShengouLeixinName(String shengouLeixinName) {
        this.shengouLeixinName = shengouLeixinName;
    }
}
