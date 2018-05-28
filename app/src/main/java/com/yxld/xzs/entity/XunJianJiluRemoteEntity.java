package com.yxld.xzs.entity;

import java.util.List;

/**
 * @author Yuan.Y.Q
 * @Date 2017/8/9.
 */

public class XunJianJiluRemoteEntity extends BaseBack {
    public int jiluGongsiId;
    public int jiluXiangmuId; //项目id
    public int jiluId; //记录id
    public String jiluXianluName; //线路名称
    public String jiluJihuaName; //计划名称
    public int jiluTijiaoAdminId; //提交人id
    public String jiluTijiaoAdminName;//提交人姓名
    public long jiluTijiaoShijian; //提交时间 时间戳
    public int jiluBanzuId; //班组id
    public String jiluBanzuName;
    public int jiluTijiaoXungengrenId; //巡更人id
    public String jiluXunjianXungengrenName; //巡更人姓名
    public int jiluWenti; //是否有问题，1没问题 -1有问题
    public long jiluKaishiJihuaShijian; //记录开始时间
    public long jiluJieshuJihuaShijian; //记录结束时间
    public long jiluKaishiShijiShijian;  //实际开始时间
    public long jiluJieshuShijiShijian; //实际结束时间
    public int jiluWancheng; //是否巡检完成 1开始 -1 未开始'2 完成
    public int jiluXianluId;

    public List<XunJianDianEntity> xunJianDianDatas;
    public int userId; //需要自己填写

    public int total;
    public List<XunJianJiLuEntity>  data;
}
