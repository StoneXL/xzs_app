package com.yxld.xzs.entity;

/**
 * 订单详情bean
 * Created by William on 2017/11/23.
 */

public class OrderDetailBean {
    /**
     * id : null
     * xiangmuId : null
     * gongsiId : null
     * xiangmuMing : null
     * dingdanBianhao : null
     * shangpinId : null
     * shangpinMing : null
     * shangpinShuliang : null
     * shangpinGuige : null
     * shangpinShoujia : null
     * shangpinZongjia : null
     * shangpinJinhuojia : null
     * suoluetu : null
     * tiaoxingma : null
     * fenlei1 : null
     * fenlei2 : null
     * dingdanZhuangtai : null
     * tuikuanPicihao : null
     * cartId : null
     */

    private int id;//订单详情id
    private int xiangmuId;//项目id
    private int gongsiId;//公司id
    private String xiangmuMing;//项目名
    private String dingdanBianhao;//订单编号
    private int shangpinId;//商品id
    private String shangpinMing;//商品名
    private int shangpinShuliang;//商品数量
    private String shangpinGuige;//商品规格
    private double shangpinShoujia;//商品售价
    private double shangpinZongjia;//商品总价
    private double shangpinJinhuojia;//商品进货价
    private String suoluetu;//缩略图
    private String tiaoxingma;//条形码
    private String fenlei1;//一级分类
    private String fenlei2;//二级分类
    private int dingdanZhuangtai;//订单商品状态:1待支付、2待发货、3待收货、4待评价、5已完成、6退货中、7退款中、8已退款、9已取消、10退货完成
    private String tuikuanPicihao;//退款批次号
    private int cartId;//购物车id 仅开发时使用（下单时根据购物车id删除购物车记录）??

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getXiangmuId() {
        return xiangmuId;
    }

    public void setXiangmuId(int xiangmuId) {
        this.xiangmuId = xiangmuId;
    }

    public int getGongsiId() {
        return gongsiId;
    }

    public void setGongsiId(int gongsiId) {
        this.gongsiId = gongsiId;
    }

    public String getXiangmuMing() {
        return xiangmuMing;
    }

    public void setXiangmuMing(String xiangmuMing) {
        this.xiangmuMing = xiangmuMing;
    }

    public String getDingdanBianhao() {
        return dingdanBianhao;
    }

    public void setDingdanBianhao(String dingdanBianhao) {
        this.dingdanBianhao = dingdanBianhao;
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

    public int getShangpinShuliang() {
        return shangpinShuliang;
    }

    public void setShangpinShuliang(int shangpinShuliang) {
        this.shangpinShuliang = shangpinShuliang;
    }

    public String getShangpinGuige() {
        return shangpinGuige;
    }

    public void setShangpinGuige(String shangpinGuige) {
        this.shangpinGuige = shangpinGuige;
    }

    public double getShangpinShoujia() {
        return shangpinShoujia;
    }

    public void setShangpinShoujia(double shangpinShoujia) {
        this.shangpinShoujia = shangpinShoujia;
    }

    public double getShangpinZongjia() {
        return shangpinZongjia;
    }

    public void setShangpinZongjia(double shangpinZongjia) {
        this.shangpinZongjia = shangpinZongjia;
    }

    public double getShangpinJinhuojia() {
        return shangpinJinhuojia;
    }

    public void setShangpinJinhuojia(double shangpinJinhuojia) {
        this.shangpinJinhuojia = shangpinJinhuojia;
    }

    public String getSuoluetu() {
        return suoluetu;
    }

    public void setSuoluetu(String suoluetu) {
        this.suoluetu = suoluetu;
    }

    public String getTiaoxingma() {
        return tiaoxingma;
    }

    public void setTiaoxingma(String tiaoxingma) {
        this.tiaoxingma = tiaoxingma;
    }

    public String getFenlei1() {
        return fenlei1;
    }

    public void setFenlei1(String fenlei1) {
        this.fenlei1 = fenlei1;
    }

    public String getFenlei2() {
        return fenlei2;
    }

    public void setFenlei2(String fenlei2) {
        this.fenlei2 = fenlei2;
    }

    public int getDingdanZhuangtai() {
        return dingdanZhuangtai;
    }

    public void setDingdanZhuangtai(int dingdanZhuangtai) {
        this.dingdanZhuangtai = dingdanZhuangtai;
    }

    public String getTuikuanPicihao() {
        return tuikuanPicihao;
    }

    public void setTuikuanPicihao(String tuikuanPicihao) {
        this.tuikuanPicihao = tuikuanPicihao;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    @Override
    public String toString() {
        return "OrderDetailBean{" +
                "id=" + id +
                ", xiangmuId=" + xiangmuId +
                ", gongsiId=" + gongsiId +
                ", xiangmuMing='" + xiangmuMing + '\'' +
                ", dingdanBianhao='" + dingdanBianhao + '\'' +
                ", shangpinId=" + shangpinId +
                ", shangpinMing='" + shangpinMing + '\'' +
                ", shangpinShuliang=" + shangpinShuliang +
                ", shangpinGuige='" + shangpinGuige + '\'' +
                ", shangpinShoujia=" + shangpinShoujia +
                ", shangpinZongjia=" + shangpinZongjia +
                ", shangpinJinhuojia=" + shangpinJinhuojia +
                ", suoluetu='" + suoluetu + '\'' +
                ", tiaoxingma='" + tiaoxingma + '\'' +
                ", fenlei1='" + fenlei1 + '\'' +
                ", fenlei2='" + fenlei2 + '\'' +
                ", dingdanZhuangtai=" + dingdanZhuangtai +
                ", tuikuanPicihao='" + tuikuanPicihao + '\'' +
                ", cartId=" + cartId +
                '}';
    }
}
