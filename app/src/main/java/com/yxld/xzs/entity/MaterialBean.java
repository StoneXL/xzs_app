package com.yxld.xzs.entity;

/**
 * 物资bean(夜间出库单详情
 * Created by William on 2017/11/28.
 */

public class MaterialBean {

    /**
     * id : 170
     * shangpinId : 916
     * shangpinMing : 小米手机9
     * yingchuShuliang : 0
     * shijiShuliang : null
     * guige : 个
     * tiaoxingma : 23565541548
     * yejianKucun : 10
     * kucun : null
     * wuziBianhao : null
     */

    private int id;
    private int shangpinId;//商品id
    private String shangpinMing;//商品名
    private int yingchuShuliang;//应出数量
    private String shijiShuliang;//实际数量
    private String guige;//规格
    private String tiaoxingma;//条形码
    private int yejianKucun;//夜间库存
    private String kucun;//库存
    private String wuziBianhao;//物资编号

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getShangpinId() {
        return shangpinId;
    }

    public void setShangpinId(int shangpinId) {
        this.shangpinId = shangpinId;
    }

    public String getShangpinMing() {
        return shangpinMing;
    }

    public void setShangpinMing(String shangpinMing) {
        this.shangpinMing = shangpinMing;
    }

    public int getYingchuShuliang() {
        return yingchuShuliang;
    }

    public void setYingchuShuliang(int yingchuShuliang) {
        this.yingchuShuliang = yingchuShuliang;
    }

    public String getShijiShuliang() {
        return shijiShuliang;
    }

    public void setShijiShuliang(String shijiShuliang) {
        this.shijiShuliang = shijiShuliang;
    }

    public String getGuige() {
        return guige;
    }

    public void setGuige(String guige) {
        this.guige = guige;
    }

    public String getTiaoxingma() {
        return tiaoxingma;
    }

    public void setTiaoxingma(String tiaoxingma) {
        this.tiaoxingma = tiaoxingma;
    }

    public int getYejianKucun() {
        return yejianKucun;
    }

    public void setYejianKucun(int yejianKucun) {
        this.yejianKucun = yejianKucun;
    }

    public String getKucun() {
        return kucun;
    }

    public void setKucun(String kucun) {
        this.kucun = kucun;
    }

    public String getWuziBianhao() {
        return wuziBianhao;
    }

    public void setWuziBianhao(String wuziBianhao) {
        this.wuziBianhao = wuziBianhao;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "id=" + id +
                ", shangpinId=" + shangpinId +
                ", shangpinMing='" + shangpinMing + '\'' +
                ", yingchuShuliang=" + yingchuShuliang +
                ", shijiShuliang='" + shijiShuliang + '\'' +
                ", guige='" + guige + '\'' +
                ", tiaoxingma='" + tiaoxingma + '\'' +
                ", yejianKucun=" + yejianKucun +
                ", kucun='" + kucun + '\'' +
                ", wuziBianhao='" + wuziBianhao + '\'' +
                '}';
    }
}
