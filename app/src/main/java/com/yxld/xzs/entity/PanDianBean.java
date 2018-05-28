package com.yxld.xzs.entity;

/**
 * Created by William on 2018/5/28.
 */

public class PanDianBean {

    /**
     * detailId : 1
     * detailKucunId : 1
     * detailPandian : 1
     * detailShengchanRiqi : 2018-02-27
     * detailYouxiaoqi : 11
     * detailGuoqiRiqi : 2018-03-10
     * detailShuliang : 9
     * kucunXiangmuMingcheng : null
     * kucunFenlei : null
     * kucunZhuangtai : null
     * wuziBianhao : 66
     * wuziPinpai : 66
     * wuziGuige : 66
     * wuziDanwei : 阿斯顿
     * wuziMingcheng : 66
     * wuziDanjia : 11
     * wuziFenlei : 1
     * wuziChaifen : null
     */

    private int detailId;//
    private int detailKucunId;//库存ID
    private int detailPandian;//盘点
    private String detailShengchanRiqi;//生产日期
    private int detailYouxiaoqi;//有效期
    private String detailGuoqiRiqi;//过期日期
    private int detailShuliang;//数量
    private Object kucunXiangmuMingcheng;//库存项目名称
    private int kucunFenlei;//1为内部使用，2为商城可售
    private Object kucunZhuangtai;//库存状态
    private String wuziBianhao;//物资编号
    private String wuziPinpai;//物资品牌
    private String wuziGuige;//物资规格
    private String wuziDanwei;//物资单位
    private String wuziMingcheng;//物资名称
    private int wuziDanjia;//物资单价
    private int wuziFenlei;//物资分类
    private Object wuziChaifen;

    public int getDetailId() {
        return detailId;
    }

    public void setDetailId(int detailId) {
        this.detailId = detailId;
    }

    public int getDetailKucunId() {
        return detailKucunId;
    }

    public void setDetailKucunId(int detailKucunId) {
        this.detailKucunId = detailKucunId;
    }

    public int getDetailPandian() {
        return detailPandian;
    }

    public void setDetailPandian(int detailPandian) {
        this.detailPandian = detailPandian;
    }

    public String getDetailShengchanRiqi() {
        return detailShengchanRiqi;
    }

    public void setDetailShengchanRiqi(String detailShengchanRiqi) {
        this.detailShengchanRiqi = detailShengchanRiqi;
    }

    public int getDetailYouxiaoqi() {
        return detailYouxiaoqi;
    }

    public void setDetailYouxiaoqi(int detailYouxiaoqi) {
        this.detailYouxiaoqi = detailYouxiaoqi;
    }

    public String getDetailGuoqiRiqi() {
        return detailGuoqiRiqi;
    }

    public void setDetailGuoqiRiqi(String detailGuoqiRiqi) {
        this.detailGuoqiRiqi = detailGuoqiRiqi;
    }

    public int getDetailShuliang() {
        return detailShuliang;
    }

    public void setDetailShuliang(int detailShuliang) {
        this.detailShuliang = detailShuliang;
    }

    public Object getKucunXiangmuMingcheng() {
        return kucunXiangmuMingcheng;
    }

    public void setKucunXiangmuMingcheng(Object kucunXiangmuMingcheng) {
        this.kucunXiangmuMingcheng = kucunXiangmuMingcheng;
    }

    public int getKucunFenlei() {
        return kucunFenlei;
    }

    public void setKucunFenlei(int kucunFenlei) {
        this.kucunFenlei = kucunFenlei;
    }

    public Object getKucunZhuangtai() {
        return kucunZhuangtai;
    }

    public void setKucunZhuangtai(Object kucunZhuangtai) {
        this.kucunZhuangtai = kucunZhuangtai;
    }

    public String getWuziBianhao() {
        return wuziBianhao;
    }

    public void setWuziBianhao(String wuziBianhao) {
        this.wuziBianhao = wuziBianhao;
    }

    public String getWuziPinpai() {
        return wuziPinpai;
    }

    public void setWuziPinpai(String wuziPinpai) {
        this.wuziPinpai = wuziPinpai;
    }

    public String getWuziGuige() {
        return wuziGuige;
    }

    public void setWuziGuige(String wuziGuige) {
        this.wuziGuige = wuziGuige;
    }

    public String getWuziDanwei() {
        return wuziDanwei;
    }

    public void setWuziDanwei(String wuziDanwei) {
        this.wuziDanwei = wuziDanwei;
    }

    public String getWuziMingcheng() {
        return wuziMingcheng;
    }

    public void setWuziMingcheng(String wuziMingcheng) {
        this.wuziMingcheng = wuziMingcheng;
    }

    public int getWuziDanjia() {
        return wuziDanjia;
    }

    public void setWuziDanjia(int wuziDanjia) {
        this.wuziDanjia = wuziDanjia;
    }

    public int getWuziFenlei() {
        return wuziFenlei;
    }

    public void setWuziFenlei(int wuziFenlei) {
        this.wuziFenlei = wuziFenlei;
    }

    public Object getWuziChaifen() {
        return wuziChaifen;
    }

    public void setWuziChaifen(Object wuziChaifen) {
        this.wuziChaifen = wuziChaifen;
    }

    @Override
    public String toString() {
        return "PanDianBean{" +
                "detailId=" + detailId +
                ", detailKucunId=" + detailKucunId +
                ", detailPandian=" + detailPandian +
                ", detailShengchanRiqi='" + detailShengchanRiqi + '\'' +
                ", detailYouxiaoqi=" + detailYouxiaoqi +
                ", detailGuoqiRiqi='" + detailGuoqiRiqi + '\'' +
                ", detailShuliang=" + detailShuliang +
                ", kucunXiangmuMingcheng=" + kucunXiangmuMingcheng +
                ", kucunFenlei=" + kucunFenlei +
                ", kucunZhuangtai=" + kucunZhuangtai +
                ", wuziBianhao='" + wuziBianhao + '\'' +
                ", wuziPinpai='" + wuziPinpai + '\'' +
                ", wuziGuige='" + wuziGuige + '\'' +
                ", wuziDanwei='" + wuziDanwei + '\'' +
                ", wuziMingcheng='" + wuziMingcheng + '\'' +
                ", wuziDanjia=" + wuziDanjia +
                ", wuziFenlei=" + wuziFenlei +
                ", wuziChaifen=" + wuziChaifen +
                '}';
    }
}
