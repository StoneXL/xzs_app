package com.yxld.xzs.entity;

import java.util.List;

/**
 * 欣周边 订单实体类
 * Created by William on 2018/1/9.
 */

public class RimOrderBean {

    /**
     * orderId : 266
     * orderBusinessName : 快快洗
     * orderBusinessNumber : 1001
     * orderNumber : 003407sj1514343843555
     * orderUserId : null
     * orderUserName : null
     * orderUserPhone : null
     * orderUserAddress : 中远公馆1栋 1单元 301号
     * orderCompanyId : null
     * orderUserCompany : null
     * orderXiangmuId : null
     * orderUserXiangmu : null
     * orderStatus : 2
     * orderOrderTime : 2017-12-27 11:04:03.0
     * orderMoney : null
     * orderFactMoney : null
     * orderSettleMoney : null
     * orderSenderId : null
     * orderSenderName : null
     * orderSenderPhone : null
     * orderSendMoney : 2
     * orderEvaluateEvaTime : null
     * orderEvaluateEvaContent : null
     * orderEvaluateEvaLevel : null
     * orderEvaluateReplyContent : null
     * orderEvaluateReplyTime : null
     * orderEvaluateReplyPerson : null
     * orderCancelRemark : null
     * orderPayType : null
     * orderPayTime : null
     * orderPayTrading : null
     * orderBespeakTime : 周二  12月26日22:00~23:00
     * orderDeliverMethods : 2
     * orderSettleStatus : -1
     * orderPaydeliverfee : null
     * orderIsDelete : null
     * orderSettleTime : null
     * orderSettlePerson : null
     * orderRemark : null
     * sendStatus : null
     * business_logo : upload/img/1510126468656;123456
     * discountMoney : null
     * discountContent : null
     * produceType : 2
     * detailList : [{"orderDetailsId":366,"orderDetailsOrderNumber":"003407sj1514343843555",
     * "orderDetailsProductId":90,"orderDetailsProductName":"羽绒服短款",
     * "orderDetailsBusinessPrice":30,"orderDetailsPreferentialPrice":30,
     * "orderDetailsProductNumber":1,"orderDetailsProductPrice":30,
     * "orderDetailsProductImg":"upload/img/1510126468656,upload/img/1510126468656,
     * upload/img/classify/1.JPEG,upload/img/","orderDetailsBusinessNumber":""}]
     */

    private int orderId;
    private String orderBusinessName;//商家名称
    private String orderBusinessNumber;//订单编号
    private String orderNumber;
    private Object orderUserId;
    private Object orderUserName;
    private String  orderUserPhone;//买家电话号码
    private String orderUserAddress;//用户收货地址
    private Object orderCompanyId;
    private Object orderUserCompany;
    private Object orderXiangmuId;
    private Object orderUserXiangmu;
    private int orderStatus;
    private String orderOrderTime;//下单时间
    private Object orderMoney;
    private Object orderFactMoney;
    private Object orderSettleMoney;
    private Object orderSenderId;
    private Object orderSenderName;
    private Object orderSenderPhone;
    private double orderSendMoney;//配送费
    private Object orderEvaluateEvaTime;
    private Object orderEvaluateEvaContent;
    private Object orderEvaluateEvaLevel;
    private Object orderEvaluateReplyContent;
    private Object orderEvaluateReplyTime;
    private Object orderEvaluateReplyPerson;
    private Object orderCancelRemark;
    private Object orderPayType;
    private Object orderPayTime;
    private Object orderPayTrading;
    private String orderBespeakTime;//预约取件时间,送还时间
    private int orderDeliverMethods;//配送方式  （1.商家承运 2.平台承运）
    private int orderSettleStatus;
    private Object orderPaydeliverfee;
    private Object orderIsDelete;
    private Object orderSettleTime;
    private Object orderSettlePerson;
    private Object orderRemark;
    private Object sendStatus;
    private String business_logo;
    private Object discountMoney;
    private Object discountContent;
    private int produceType;//产品类别（1-商品；2-服务）
    private String shTime;//送还中的时间
    private String businessPhone;//卖家电话号码
    private List<RimOrderDetailBean> detailList;

    private boolean isShow;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getOrderBusinessName() {
        return orderBusinessName;
    }

    public void setOrderBusinessName(String orderBusinessName) {
        this.orderBusinessName = orderBusinessName;
    }

    public String getOrderBusinessNumber() {
        return orderBusinessNumber;
    }

    public void setOrderBusinessNumber(String orderBusinessNumber) {
        this.orderBusinessNumber = orderBusinessNumber;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Object getOrderUserId() {
        return orderUserId;
    }

    public void setOrderUserId(Object orderUserId) {
        this.orderUserId = orderUserId;
    }

    public Object getOrderUserName() {
        return orderUserName;
    }

    public void setOrderUserName(Object orderUserName) {
        this.orderUserName = orderUserName;
    }

    public String getOrderUserPhone() {
        return orderUserPhone;
    }

    public void setOrderUserPhone(String orderUserPhone) {
        this.orderUserPhone = orderUserPhone;
    }

    public String getOrderUserAddress() {
        return orderUserAddress;
    }

    public void setOrderUserAddress(String orderUserAddress) {
        this.orderUserAddress = orderUserAddress;
    }

    public Object getOrderCompanyId() {
        return orderCompanyId;
    }

    public void setOrderCompanyId(Object orderCompanyId) {
        this.orderCompanyId = orderCompanyId;
    }

    public Object getOrderUserCompany() {
        return orderUserCompany;
    }

    public void setOrderUserCompany(Object orderUserCompany) {
        this.orderUserCompany = orderUserCompany;
    }

    public Object getOrderXiangmuId() {
        return orderXiangmuId;
    }

    public void setOrderXiangmuId(Object orderXiangmuId) {
        this.orderXiangmuId = orderXiangmuId;
    }

    public Object getOrderUserXiangmu() {
        return orderUserXiangmu;
    }

    public void setOrderUserXiangmu(Object orderUserXiangmu) {
        this.orderUserXiangmu = orderUserXiangmu;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderOrderTime() {
        return orderOrderTime;
    }

    public void setOrderOrderTime(String orderOrderTime) {
        this.orderOrderTime = orderOrderTime;
    }

    public Object getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(Object orderMoney) {
        this.orderMoney = orderMoney;
    }

    public Object getOrderFactMoney() {
        return orderFactMoney;
    }

    public void setOrderFactMoney(Object orderFactMoney) {
        this.orderFactMoney = orderFactMoney;
    }

    public Object getOrderSettleMoney() {
        return orderSettleMoney;
    }

    public void setOrderSettleMoney(Object orderSettleMoney) {
        this.orderSettleMoney = orderSettleMoney;
    }

    public Object getOrderSenderId() {
        return orderSenderId;
    }

    public void setOrderSenderId(Object orderSenderId) {
        this.orderSenderId = orderSenderId;
    }

    public Object getOrderSenderName() {
        return orderSenderName;
    }

    public void setOrderSenderName(Object orderSenderName) {
        this.orderSenderName = orderSenderName;
    }

    public Object getOrderSenderPhone() {
        return orderSenderPhone;
    }

    public void setOrderSenderPhone(Object orderSenderPhone) {
        this.orderSenderPhone = orderSenderPhone;
    }

    public double getOrderSendMoney() {
        return orderSendMoney;
    }

    public void setOrderSendMoney(double orderSendMoney) {
        this.orderSendMoney = orderSendMoney;
    }

    public Object getOrderEvaluateEvaTime() {
        return orderEvaluateEvaTime;
    }

    public void setOrderEvaluateEvaTime(Object orderEvaluateEvaTime) {
        this.orderEvaluateEvaTime = orderEvaluateEvaTime;
    }

    public Object getOrderEvaluateEvaContent() {
        return orderEvaluateEvaContent;
    }

    public void setOrderEvaluateEvaContent(Object orderEvaluateEvaContent) {
        this.orderEvaluateEvaContent = orderEvaluateEvaContent;
    }

    public Object getOrderEvaluateEvaLevel() {
        return orderEvaluateEvaLevel;
    }

    public void setOrderEvaluateEvaLevel(Object orderEvaluateEvaLevel) {
        this.orderEvaluateEvaLevel = orderEvaluateEvaLevel;
    }

    public Object getOrderEvaluateReplyContent() {
        return orderEvaluateReplyContent;
    }

    public void setOrderEvaluateReplyContent(Object orderEvaluateReplyContent) {
        this.orderEvaluateReplyContent = orderEvaluateReplyContent;
    }

    public Object getOrderEvaluateReplyTime() {
        return orderEvaluateReplyTime;
    }

    public void setOrderEvaluateReplyTime(Object orderEvaluateReplyTime) {
        this.orderEvaluateReplyTime = orderEvaluateReplyTime;
    }

    public Object getOrderEvaluateReplyPerson() {
        return orderEvaluateReplyPerson;
    }

    public void setOrderEvaluateReplyPerson(Object orderEvaluateReplyPerson) {
        this.orderEvaluateReplyPerson = orderEvaluateReplyPerson;
    }

    public Object getOrderCancelRemark() {
        return orderCancelRemark;
    }

    public void setOrderCancelRemark(Object orderCancelRemark) {
        this.orderCancelRemark = orderCancelRemark;
    }

    public Object getOrderPayType() {
        return orderPayType;
    }

    public void setOrderPayType(Object orderPayType) {
        this.orderPayType = orderPayType;
    }

    public Object getOrderPayTime() {
        return orderPayTime;
    }

    public void setOrderPayTime(Object orderPayTime) {
        this.orderPayTime = orderPayTime;
    }

    public Object getOrderPayTrading() {
        return orderPayTrading;
    }

    public void setOrderPayTrading(Object orderPayTrading) {
        this.orderPayTrading = orderPayTrading;
    }

    public String getOrderBespeakTime() {
        return orderBespeakTime;
    }

    public void setOrderBespeakTime(String orderBespeakTime) {
        this.orderBespeakTime = orderBespeakTime;
    }

    public int getOrderDeliverMethods() {
        return orderDeliverMethods;
    }

    public void setOrderDeliverMethods(int orderDeliverMethods) {
        this.orderDeliverMethods = orderDeliverMethods;
    }

    public int getOrderSettleStatus() {
        return orderSettleStatus;
    }

    public void setOrderSettleStatus(int orderSettleStatus) {
        this.orderSettleStatus = orderSettleStatus;
    }

    public Object getOrderPaydeliverfee() {
        return orderPaydeliverfee;
    }

    public void setOrderPaydeliverfee(Object orderPaydeliverfee) {
        this.orderPaydeliverfee = orderPaydeliverfee;
    }

    public Object getOrderIsDelete() {
        return orderIsDelete;
    }

    public void setOrderIsDelete(Object orderIsDelete) {
        this.orderIsDelete = orderIsDelete;
    }

    public Object getOrderSettleTime() {
        return orderSettleTime;
    }

    public void setOrderSettleTime(Object orderSettleTime) {
        this.orderSettleTime = orderSettleTime;
    }

    public Object getOrderSettlePerson() {
        return orderSettlePerson;
    }

    public void setOrderSettlePerson(Object orderSettlePerson) {
        this.orderSettlePerson = orderSettlePerson;
    }

    public Object getOrderRemark() {
        return orderRemark;
    }

    public void setOrderRemark(Object orderRemark) {
        this.orderRemark = orderRemark;
    }

    public Object getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(Object sendStatus) {
        this.sendStatus = sendStatus;
    }

    public String getBusiness_logo() {
        return business_logo;
    }

    public void setBusiness_logo(String business_logo) {
        this.business_logo = business_logo;
    }

    public Object getDiscountMoney() {
        return discountMoney;
    }

    public void setDiscountMoney(Object discountMoney) {
        this.discountMoney = discountMoney;
    }

    public Object getDiscountContent() {
        return discountContent;
    }

    public void setDiscountContent(Object discountContent) {
        this.discountContent = discountContent;
    }

    public int getProduceType() {
        return produceType;
    }

    public void setProduceType(int produceType) {
        this.produceType = produceType;
    }

    public String getShTime() {
        return shTime;
    }

    public void setShTime(String shTime) {
        this.shTime = shTime;
    }

    public String getBusinessPhone() {
        return businessPhone;
    }

    public void setBusinessPhone(String businessPhone) {
        this.businessPhone = businessPhone;
    }

    public List<RimOrderDetailBean> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<RimOrderDetailBean> detailList) {
        this.detailList = detailList;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    @Override
    public String toString() {
        return "RimOrderBean{" +
                "orderId=" + orderId +
                ", orderBusinessName='" + orderBusinessName + '\'' +
                ", orderBusinessNumber='" + orderBusinessNumber + '\'' +
                ", orderNumber='" + orderNumber + '\'' +
                ", orderUserId=" + orderUserId +
                ", orderUserName=" + orderUserName +
                ", orderUserPhone='" + orderUserPhone + '\'' +
                ", orderUserAddress='" + orderUserAddress + '\'' +
                ", orderCompanyId=" + orderCompanyId +
                ", orderUserCompany=" + orderUserCompany +
                ", orderXiangmuId=" + orderXiangmuId +
                ", orderUserXiangmu=" + orderUserXiangmu +
                ", orderStatus=" + orderStatus +
                ", orderOrderTime='" + orderOrderTime + '\'' +
                ", orderMoney=" + orderMoney +
                ", orderFactMoney=" + orderFactMoney +
                ", orderSettleMoney=" + orderSettleMoney +
                ", orderSenderId=" + orderSenderId +
                ", orderSenderName=" + orderSenderName +
                ", orderSenderPhone=" + orderSenderPhone +
                ", orderSendMoney=" + orderSendMoney +
                ", orderEvaluateEvaTime=" + orderEvaluateEvaTime +
                ", orderEvaluateEvaContent=" + orderEvaluateEvaContent +
                ", orderEvaluateEvaLevel=" + orderEvaluateEvaLevel +
                ", orderEvaluateReplyContent=" + orderEvaluateReplyContent +
                ", orderEvaluateReplyTime=" + orderEvaluateReplyTime +
                ", orderEvaluateReplyPerson=" + orderEvaluateReplyPerson +
                ", orderCancelRemark=" + orderCancelRemark +
                ", orderPayType=" + orderPayType +
                ", orderPayTime=" + orderPayTime +
                ", orderPayTrading=" + orderPayTrading +
                ", orderBespeakTime='" + orderBespeakTime + '\'' +
                ", orderDeliverMethods=" + orderDeliverMethods +
                ", orderSettleStatus=" + orderSettleStatus +
                ", orderPaydeliverfee=" + orderPaydeliverfee +
                ", orderIsDelete=" + orderIsDelete +
                ", orderSettleTime=" + orderSettleTime +
                ", orderSettlePerson=" + orderSettlePerson +
                ", orderRemark=" + orderRemark +
                ", sendStatus=" + sendStatus +
                ", business_logo='" + business_logo + '\'' +
                ", discountMoney=" + discountMoney +
                ", discountContent=" + discountContent +
                ", produceType=" + produceType +
                ", shTime='" + shTime + '\'' +
                ", businessPhone='" + businessPhone + '\'' +
                ", detailList=" + detailList +
                ", isShow=" + isShow +
                '}';
    }
}
