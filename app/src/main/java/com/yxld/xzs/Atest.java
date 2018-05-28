package com.yxld.xzs;

import java.util.List;

/**
 * Created by William on 2017/11/14.
 */

public class Atest {

    /**
     * data : [{"orderId":266,"orderBusinessName":"快快洗","orderBusinessNumber":"1001",
     * "orderNumber":"003407sj1514343843555","orderUserId":null,"orderUserName":null,
     * "orderUserPhone":null,"orderUserAddress":"中远公馆1栋 1单元 301号","orderCompanyId":null,
     * "orderUserCompany":null,"orderXiangmuId":null,"orderUserXiangmu":null,"orderStatus":2,
     * "orderOrderTime":"2017-12-27 11:04:03.0","orderMoney":null,"orderFactMoney":null,
     * "orderSettleMoney":null,"orderSenderId":null,"orderSenderName":null,
     * "orderSenderPhone":null,"orderSendMoney":2,"orderEvaluateEvaTime":null,
     * "orderEvaluateEvaContent":null,"orderEvaluateEvaLevel":null,
     * "orderEvaluateReplyContent":null,"orderEvaluateReplyTime":null,
     * "orderEvaluateReplyPerson":null,"orderCancelRemark":null,"orderPayType":null,
     * "orderPayTime":null,"orderPayTrading":null,"orderBespeakTime":"周二  12月26日22:00~23:00",
     * "orderDeliverMethods":2,"orderSettleStatus":-1,"orderPaydeliverfee":null,
     * "orderIsDelete":null,"orderSettleTime":null,"orderSettlePerson":null,"orderRemark":null,
     * "sendStatus":null,"business_logo":"upload/img/1510126468656;123456","discountMoney":null,
     * "discountContent":null,"produceType":2,"detailList":[{"orderDetailsId":366,
     * "orderDetailsOrderNumber":"003407sj1514343843555","orderDetailsProductId":90,
     * "orderDetailsProductName":"羽绒服短款","orderDetailsBusinessPrice":30,
     * "orderDetailsPreferentialPrice":30,"orderDetailsProductNumber":1,
     * "orderDetailsProductPrice":30,"orderDetailsProductImg":"upload/img/1510126468656,
     * upload/img/1510126468656,upload/img/classify/1.JPEG,upload/img/",
     * "orderDetailsBusinessNumber":""}]},{"orderId":268,"orderBusinessName":"快快洗",
     * "orderBusinessNumber":"1001","orderNumber":"003407sj1514343895260","orderUserId":null,
     * "orderUserName":null,"orderUserPhone":null,"orderUserAddress":"中远公馆1栋 1单元 301号",
     * "orderCompanyId":null,"orderUserCompany":null,"orderXiangmuId":null,
     * "orderUserXiangmu":null,"orderStatus":2,"orderOrderTime":"2017-12-27 11:04:55.0",
     * "orderMoney":null,"orderFactMoney":null,"orderSettleMoney":null,"orderSenderId":null,
     * "orderSenderName":null,"orderSenderPhone":null,"orderSendMoney":2,
     * "orderEvaluateEvaTime":null,"orderEvaluateEvaContent":null,"orderEvaluateEvaLevel":null,
     * "orderEvaluateReplyContent":null,"orderEvaluateReplyTime":null,
     * "orderEvaluateReplyPerson":null,"orderCancelRemark":null,"orderPayType":null,
     * "orderPayTime":null,"orderPayTrading":null,"orderBespeakTime":"周二  12月26日22:00~23:00",
     * "orderDeliverMethods":2,"orderSettleStatus":-1,"orderPaydeliverfee":null,
     * "orderIsDelete":null,"orderSettleTime":null,"orderSettlePerson":null,"orderRemark":null,
     * "sendStatus":null,"business_logo":"upload/img/1510126468656;123456","discountMoney":null,
     * "discountContent":null,"produceType":2,"detailList":[{"orderDetailsId":368,
     * "orderDetailsOrderNumber":"003407sj1514343895260","orderDetailsProductId":90,
     * "orderDetailsProductName":"羽绒服短款","orderDetailsBusinessPrice":30,
     * "orderDetailsPreferentialPrice":30,"orderDetailsProductNumber":1,
     * "orderDetailsProductPrice":30,"orderDetailsProductImg":"upload/img/1510126468656,
     * upload/img/1510126468656,upload/img/classify/1.JPEG,upload/img/",
     * "orderDetailsBusinessNumber":""}]},{"orderId":274,"orderBusinessName":"快快洗",
     * "orderBusinessNumber":"1001","orderNumber":"003407sj1514343945277","orderUserId":null,
     * "orderUserName":null,"orderUserPhone":null,"orderUserAddress":"中远公馆1栋 1单元 301号",
     * "orderCompanyId":null,"orderUserCompany":null,"orderXiangmuId":null,
     * "orderUserXiangmu":null,"orderStatus":2,"orderOrderTime":"2017-12-27 11:05:45.0",
     * "orderMoney":null,"orderFactMoney":null,"orderSettleMoney":null,"orderSenderId":null,
     * "orderSenderName":null,"orderSenderPhone":null,"orderSendMoney":2,
     * "orderEvaluateEvaTime":null,"orderEvaluateEvaContent":null,"orderEvaluateEvaLevel":null,
     * "orderEvaluateReplyContent":null,"orderEvaluateReplyTime":null,
     * "orderEvaluateReplyPerson":null,"orderCancelRemark":null,"orderPayType":null,
     * "orderPayTime":null,"orderPayTrading":null,"orderBespeakTime":"周二  12月26日22:00~23:00",
     * "orderDeliverMethods":2,"orderSettleStatus":-1,"orderPaydeliverfee":null,
     * "orderIsDelete":null,"orderSettleTime":null,"orderSettlePerson":null,"orderRemark":null,
     * "sendStatus":null,"business_logo":"upload/img/1510126468656;123456","discountMoney":null,
     * "discountContent":null,"produceType":2,"detailList":[{"orderDetailsId":374,
     * "orderDetailsOrderNumber":"003407sj1514343945277","orderDetailsProductId":90,
     * "orderDetailsProductName":"羽绒服短款","orderDetailsBusinessPrice":30,
     * "orderDetailsPreferentialPrice":30,"orderDetailsProductNumber":1,
     * "orderDetailsProductPrice":30,"orderDetailsProductImg":"upload/img/1510126468656,
     * upload/img/1510126468656,upload/img/classify/1.JPEG,upload/img/",
     * "orderDetailsBusinessNumber":""}]},{"orderId":276,"orderBusinessName":"快快洗",
     * "orderBusinessNumber":"1001","orderNumber":"003407sj1514343966163","orderUserId":null,
     * "orderUserName":null,"orderUserPhone":null,"orderUserAddress":"中远公馆1栋 1单元 301号",
     * "orderCompanyId":null,"orderUserCompany":null,"orderXiangmuId":null,
     * "orderUserXiangmu":null,"orderStatus":2,"orderOrderTime":"2017-12-27 11:06:06.0",
     * "orderMoney":null,"orderFactMoney":null,"orderSettleMoney":null,"orderSenderId":null,
     * "orderSenderName":null,"orderSenderPhone":null,"orderSendMoney":2,
     * "orderEvaluateEvaTime":null,"orderEvaluateEvaContent":null,"orderEvaluateEvaLevel":null,
     * "orderEvaluateReplyContent":null,"orderEvaluateReplyTime":null,
     * "orderEvaluateReplyPerson":null,"orderCancelRemark":null,"orderPayType":null,
     * "orderPayTime":null,"orderPayTrading":null,"orderBespeakTime":"周二  12月26日22:00~23:00",
     * "orderDeliverMethods":2,"orderSettleStatus":-1,"orderPaydeliverfee":null,
     * "orderIsDelete":null,"orderSettleTime":null,"orderSettlePerson":null,"orderRemark":null,
     * "sendStatus":null,"business_logo":"upload/img/1510126468656;123456","discountMoney":null,
     * "discountContent":null,"produceType":2,"detailList":[{"orderDetailsId":376,
     * "orderDetailsOrderNumber":"003407sj1514343966163","orderDetailsProductId":90,
     * "orderDetailsProductName":"羽绒服短款","orderDetailsBusinessPrice":30,
     * "orderDetailsPreferentialPrice":30,"orderDetailsProductNumber":1,
     * "orderDetailsProductPrice":30,"orderDetailsProductImg":"upload/img/1510126468656,
     * upload/img/1510126468656,upload/img/classify/1.JPEG,upload/img/",
     * "orderDetailsBusinessNumber":""}]},{"orderId":277,"orderBusinessName":"快快洗",
     * "orderBusinessNumber":"1001","orderNumber":"003407sj1514343975889","orderUserId":null,
     * "orderUserName":null,"orderUserPhone":null,"orderUserAddress":"中远公馆1栋 1单元 301号",
     * "orderCompanyId":null,"orderUserCompany":null,"orderXiangmuId":null,
     * "orderUserXiangmu":null,"orderStatus":2,"orderOrderTime":"2017-12-27 11:06:15.0",
     * "orderMoney":null,"orderFactMoney":null,"orderSettleMoney":null,"orderSenderId":null,
     * "orderSenderName":null,"orderSenderPhone":null,"orderSendMoney":2,
     * "orderEvaluateEvaTime":null,"orderEvaluateEvaContent":null,"orderEvaluateEvaLevel":null,
     * "orderEvaluateReplyContent":null,"orderEvaluateReplyTime":null,
     * "orderEvaluateReplyPerson":null,"orderCancelRemark":null,"orderPayType":null,
     * "orderPayTime":null,"orderPayTrading":null,"orderBespeakTime":"周二  12月26日22:00~23:00",
     * "orderDeliverMethods":2,"orderSettleStatus":-1,"orderPaydeliverfee":null,
     * "orderIsDelete":null,"orderSettleTime":null,"orderSettlePerson":null,"orderRemark":null,
     * "sendStatus":null,"business_logo":"upload/img/1510126468656;123456","discountMoney":null,
     * "discountContent":null,"produceType":2,"detailList":[{"orderDetailsId":377,
     * "orderDetailsOrderNumber":"003407sj1514343975889","orderDetailsProductId":90,
     * "orderDetailsProductName":"羽绒服短款","orderDetailsBusinessPrice":30,
     * "orderDetailsPreferentialPrice":30,"orderDetailsProductNumber":1,
     * "orderDetailsProductPrice":30,"orderDetailsProductImg":"upload/img/1510126468656,
     * upload/img/1510126468656,upload/img/classify/1.JPEG,upload/img/",
     * "orderDetailsBusinessNumber":""}]},{"orderId":282,"orderBusinessName":"快快洗",
     * "orderBusinessNumber":"1001","orderNumber":"003407sj1514344101238","orderUserId":null,
     * "orderUserName":null,"orderUserPhone":null,"orderUserAddress":"中远公馆1栋 1单元 301号",
     * "orderCompanyId":null,"orderUserCompany":null,"orderXiangmuId":null,
     * "orderUserXiangmu":null,"orderStatus":2,"orderOrderTime":"2017-12-27 11:08:21.0",
     * "orderMoney":null,"orderFactMoney":null,"orderSettleMoney":null,"orderSenderId":null,
     * "orderSenderName":null,"orderSenderPhone":null,"orderSendMoney":2,
     * "orderEvaluateEvaTime":null,"orderEvaluateEvaContent":null,"orderEvaluateEvaLevel":null,
     * "orderEvaluateReplyContent":null,"orderEvaluateReplyTime":null,
     * "orderEvaluateReplyPerson":null,"orderCancelRemark":null,"orderPayType":null,
     * "orderPayTime":null,"orderPayTrading":null,"orderBespeakTime":"周二  12月26日22:00~23:00",
     * "orderDeliverMethods":2,"orderSettleStatus":-1,"orderPaydeliverfee":null,
     * "orderIsDelete":null,"orderSettleTime":null,"orderSettlePerson":null,"orderRemark":null,
     * "sendStatus":null,"business_logo":"upload/img/1510126468656;123456","discountMoney":null,
     * "discountContent":null,"produceType":2,"detailList":[{"orderDetailsId":382,
     * "orderDetailsOrderNumber":"003407sj1514344101238","orderDetailsProductId":90,
     * "orderDetailsProductName":"羽绒服短款","orderDetailsBusinessPrice":30,
     * "orderDetailsPreferentialPrice":30,"orderDetailsProductNumber":1,
     * "orderDetailsProductPrice":30,"orderDetailsProductImg":"upload/img/1510126468656,
     * upload/img/1510126468656,upload/img/classify/1.JPEG,upload/img/",
     * "orderDetailsBusinessNumber":""}]}]
     * msg : 查询成功
     * status : 1
     * success : 1
     * total : 0
     */

    private String msg;
    private int status;
    private String success;
    private int total;
    private List<DataBean> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
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
        private String orderBusinessName;
        private String orderBusinessNumber;
        private String orderNumber;
        private Object orderUserId;
        private Object orderUserName;
        private Object orderUserPhone;
        private String orderUserAddress;
        private Object orderCompanyId;
        private Object orderUserCompany;
        private Object orderXiangmuId;
        private Object orderUserXiangmu;
        private int orderStatus;
        private String orderOrderTime;
        private Object orderMoney;
        private Object orderFactMoney;
        private Object orderSettleMoney;
        private Object orderSenderId;
        private Object orderSenderName;
        private Object orderSenderPhone;
        private int orderSendMoney;
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
        private String orderBespeakTime;
        private int orderDeliverMethods;
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
        private int produceType;
        private List<DetailListBean> detailList;

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

        public Object getOrderUserPhone() {
            return orderUserPhone;
        }

        public void setOrderUserPhone(Object orderUserPhone) {
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

        public int getOrderSendMoney() {
            return orderSendMoney;
        }

        public void setOrderSendMoney(int orderSendMoney) {
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

        public List<DetailListBean> getDetailList() {
            return detailList;
        }

        public void setDetailList(List<DetailListBean> detailList) {
            this.detailList = detailList;
        }

        public static class DetailListBean {
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
            private String orderDetailsOrderNumber;
            private int orderDetailsProductId;
            private String orderDetailsProductName;
            private int orderDetailsBusinessPrice;
            private int orderDetailsPreferentialPrice;
            private int orderDetailsProductNumber;
            private int orderDetailsProductPrice;
            private String orderDetailsProductImg;
            private String orderDetailsBusinessNumber;

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

            public int getOrderDetailsProductId() {
                return orderDetailsProductId;
            }

            public void setOrderDetailsProductId(int orderDetailsProductId) {
                this.orderDetailsProductId = orderDetailsProductId;
            }

            public String getOrderDetailsProductName() {
                return orderDetailsProductName;
            }

            public void setOrderDetailsProductName(String orderDetailsProductName) {
                this.orderDetailsProductName = orderDetailsProductName;
            }

            public int getOrderDetailsBusinessPrice() {
                return orderDetailsBusinessPrice;
            }

            public void setOrderDetailsBusinessPrice(int orderDetailsBusinessPrice) {
                this.orderDetailsBusinessPrice = orderDetailsBusinessPrice;
            }

            public int getOrderDetailsPreferentialPrice() {
                return orderDetailsPreferentialPrice;
            }

            public void setOrderDetailsPreferentialPrice(int orderDetailsPreferentialPrice) {
                this.orderDetailsPreferentialPrice = orderDetailsPreferentialPrice;
            }

            public int getOrderDetailsProductNumber() {
                return orderDetailsProductNumber;
            }

            public void setOrderDetailsProductNumber(int orderDetailsProductNumber) {
                this.orderDetailsProductNumber = orderDetailsProductNumber;
            }

            public int getOrderDetailsProductPrice() {
                return orderDetailsProductPrice;
            }

            public void setOrderDetailsProductPrice(int orderDetailsProductPrice) {
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
        }
    }
}
