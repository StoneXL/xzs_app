package com.yxld.xzs.entity;

/**
 * 欣周边 订单中商品实体类
 * Created by William on 2018/1/9.
 */

public class RimOrderDetailBean {
    /**
     * orderDetailsId : 366
     * orderDetailsOrderNumber : 003407sj1514343843555
     * orderDetailsProductId : 90
     * orderDetailsProductName : 羽绒服短款
     * orderDetailsBusinessPrice : 30
     * orderDetailsPreferentialPrice : 30
     * orderDetailsProductNumber : 1
     * orderDetailsProductPrice : 30
     * orderDetailsProductImg : upload/img/1510126468656,upload/img/1510126468656,upload/img/classify/1.JPEG,upload/img/
     * orderDetailsBusinessNumber :
     */

    private int orderDetailsId;
    private String orderDetailsOrderNumber;//订单编号
    private String  orderDetailsProductId;//商品编号
    private String orderDetailsProductName;// 商品名称
    private double orderDetailsBusinessPrice;// 商品价格
    private double orderDetailsPreferentialPrice;// 商品优惠价格
    private int orderDetailsProductNumber;// 商品数量
    private double orderDetailsProductPrice;// 商品总价
    private String orderDetailsProductImg;//商品图片
    private String orderDetailsBusinessNumber;//商家编号

    public int getOrderDetailsId() {
        return orderDetailsId;
    }

    public void setOrderDetailsId(int orderDetailsId) {
        this.orderDetailsId = orderDetailsId;
    }

    public String getOrderDetailsOrderNumber() {
        return orderDetailsOrderNumber;
    }

    public void setOrderDetailsOrderNumber(String orderDetailsOrderNumber) {
        this.orderDetailsOrderNumber = orderDetailsOrderNumber;
    }

    public String getOrderDetailsProductId() {
        return orderDetailsProductId;
    }

    public void setOrderDetailsProductId(String orderDetailsProductId) {
        this.orderDetailsProductId = orderDetailsProductId;
    }

    public String getOrderDetailsProductName() {
        return orderDetailsProductName;
    }

    public void setOrderDetailsProductName(String orderDetailsProductName) {
        this.orderDetailsProductName = orderDetailsProductName;
    }

    public double getOrderDetailsBusinessPrice() {
        return orderDetailsBusinessPrice;
    }

    public void setOrderDetailsBusinessPrice(double orderDetailsBusinessPrice) {
        this.orderDetailsBusinessPrice = orderDetailsBusinessPrice;
    }

    public double getOrderDetailsPreferentialPrice() {
        return orderDetailsPreferentialPrice;
    }

    public void setOrderDetailsPreferentialPrice(double orderDetailsPreferentialPrice) {
        this.orderDetailsPreferentialPrice = orderDetailsPreferentialPrice;
    }

    public int getOrderDetailsProductNumber() {
        return orderDetailsProductNumber;
    }

    public void setOrderDetailsProductNumber(int orderDetailsProductNumber) {
        this.orderDetailsProductNumber = orderDetailsProductNumber;
    }

    public double getOrderDetailsProductPrice() {
        return orderDetailsProductPrice;
    }

    public void setOrderDetailsProductPrice(double orderDetailsProductPrice) {
        this.orderDetailsProductPrice = orderDetailsProductPrice;
    }

    public String getOrderDetailsProductImg() {
        return orderDetailsProductImg;
    }

    public void setOrderDetailsProductImg(String orderDetailsProductImg) {
        this.orderDetailsProductImg = orderDetailsProductImg;
    }

    public String getOrderDetailsBusinessNumber() {
        return orderDetailsBusinessNumber;
    }

    public void setOrderDetailsBusinessNumber(String orderDetailsBusinessNumber) {
        this.orderDetailsBusinessNumber = orderDetailsBusinessNumber;
    }

    @Override
    public String toString() {
        return "RimOrderDetailBean{" +
                "orderDetailsId=" + orderDetailsId +
                ", orderDetailsOrderNumber='" + orderDetailsOrderNumber + '\'' +
                ", orderDetailsProductId='" + orderDetailsProductId + '\'' +
                ", orderDetailsProductName='" + orderDetailsProductName + '\'' +
                ", orderDetailsBusinessPrice=" + orderDetailsBusinessPrice +
                ", orderDetailsPreferentialPrice=" + orderDetailsPreferentialPrice +
                ", orderDetailsProductNumber=" + orderDetailsProductNumber +
                ", orderDetailsProductPrice=" + orderDetailsProductPrice +
                ", orderDetailsProductImg='" + orderDetailsProductImg + '\'' +
                ", orderDetailsBusinessNumber='" + orderDetailsBusinessNumber + '\'' +
                '}';
    }
}
