package com.yxld.xzs.entity;

import java.util.List;

/**
 * @author Yuan.Y.Q
 * @Date 2017/8/5.
 */

public class XunJianJiLuEntity extends BaseBack {

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
    public XunJianJiLuEntity  data;
    public int nextXunjianDian; //当按顺序巡检时 下一个巡检点的index
    public int jiluPaixu; //1 按顺序巡检 2可以随意巡检
    public int isSelected; //班组管理需要用到 1为选中


    public int getJiluGongsiId() {
        return jiluGongsiId;
    }

    public void setJiluGongsiId(int jiluGongsiId) {
        this.jiluGongsiId = jiluGongsiId;
    }

    public int getJiluXiangmuId() {
        return jiluXiangmuId;
    }

    public void setJiluXiangmuId(int jiluXiangmuId) {
        this.jiluXiangmuId = jiluXiangmuId;
    }

    public int getJiluId() {
        return jiluId;
    }

    public void setJiluId(int jiluId) {
        this.jiluId = jiluId;
    }

    public String getJiluXianluName() {
        return jiluXianluName;
    }

    public void setJiluXianluName(String jiluXianluName) {
        this.jiluXianluName = jiluXianluName;
    }

    public String getJiluJihuaName() {
        return jiluJihuaName;
    }

    public void setJiluJihuaName(String jiluJihuaName) {
        this.jiluJihuaName = jiluJihuaName;
    }

    public int getJiluTijiaoAdminId() {
        return jiluTijiaoAdminId;
    }

    public void setJiluTijiaoAdminId(int jiluTijiaoAdminId) {
        this.jiluTijiaoAdminId = jiluTijiaoAdminId;
    }

    public String getJiluTijiaoAdminName() {
        return jiluTijiaoAdminName;
    }

    public void setJiluTijiaoAdminName(String jiluTijiaoAdminName) {
        this.jiluTijiaoAdminName = jiluTijiaoAdminName;
    }

    public long getJiluTijiaoShijian() {
        return jiluTijiaoShijian;
    }

    public void setJiluTijiaoShijian(long jiluTijiaoShijian) {
        this.jiluTijiaoShijian = jiluTijiaoShijian;
    }

    public int getJiluBanzuId() {
        return jiluBanzuId;
    }

    public void setJiluBanzuId(int jiluBanzuId) {
        this.jiluBanzuId = jiluBanzuId;
    }

    public String getJiluBanzuName() {
        return jiluBanzuName;
    }

    public void setJiluBanzuName(String jiluBanzuName) {
        this.jiluBanzuName = jiluBanzuName;
    }

    public int getJiluTijiaoXungengrenId() {
        return jiluTijiaoXungengrenId;
    }

    public void setJiluTijiaoXungengrenId(int jiluTijiaoXungengrenId) {
        this.jiluTijiaoXungengrenId = jiluTijiaoXungengrenId;
    }

    public String getJiluXunjianXungengrenName() {
        return jiluXunjianXungengrenName;
    }

    public void setJiluXunjianXungengrenName(String jiluXunjianXungengrenName) {
        this.jiluXunjianXungengrenName = jiluXunjianXungengrenName;
    }

    public int getJiluWenti() {
        return jiluWenti;
    }

    public void setJiluWenti(int jiluWenti) {
        this.jiluWenti = jiluWenti;
    }

    public long getJiluKaishiJihuaShijian() {
        return jiluKaishiJihuaShijian;
    }

    public void setJiluKaishiJihuaShijian(long jiluKaishiJihuaShijian) {
        this.jiluKaishiJihuaShijian = jiluKaishiJihuaShijian;
    }

    public long getJiluJieshuJihuaShijian() {
        return jiluJieshuJihuaShijian;
    }

    public void setJiluJieshuJihuaShijian(long jiluJieshuJihuaShijian) {
        this.jiluJieshuJihuaShijian = jiluJieshuJihuaShijian;
    }

    public long getJiluKaishiShijiShijian() {
        return jiluKaishiShijiShijian;
    }

    public void setJiluKaishiShijiShijian(long jiluKaishiShijiShijian) {
        this.jiluKaishiShijiShijian = jiluKaishiShijiShijian;
    }

    public long getJiluJieshuShijiShijian() {
        return jiluJieshuShijiShijian;
    }

    public void setJiluJieshuShijiShijian(long jiluJieshuShijiShijian) {
        this.jiluJieshuShijiShijian = jiluJieshuShijiShijian;
    }

    public int getJiluWancheng() {
        return jiluWancheng;
    }

    public void setJiluWancheng(int jiluWancheng) {
        this.jiluWancheng = jiluWancheng;
    }

    public int getJiluXianluId() {
        return jiluXianluId;
    }

    public void setJiluXianluId(int jiluXianluId) {
        this.jiluXianluId = jiluXianluId;
    }

    public List<XunJianDianEntity> getXunJianDianDatas() {
        return xunJianDianDatas;
    }

    public void setXunJianDianDatas(List<XunJianDianEntity> xunJianDianDatas) {
        this.xunJianDianDatas = xunJianDianDatas;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public XunJianJiLuEntity getData() {
        return data;
    }

    public void setData(XunJianJiLuEntity data) {
        this.data = data;
    }
}
