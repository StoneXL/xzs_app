package com.yxld.xzs.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 订单实体类(用于抢单，取货，送达,夜间订单)
 * Created by William on 2017/11/23.
 */

public class OrderBean implements Serializable {
    /**
     * id : 446
     * xiangmuId : 346
     * xiangmuMing : null
     * gongsiId : 1
     * gongsiMing : null
     * bianhao : 003401ps1511398551370
     * zhuangtai : 2
     * shifouYejian : 1
     * shouhuorenMing : 向磊
     * shouhuoDizhi : 中远公馆1栋1单元301
     * shouhuoDianhua : 15243648097
     * zongjine : 125
     * dianziquan : 0
     * shijiJine : 125
     * payjiaoyihao : null
     * fukuanFangshi : 1
     * peisongFangshi : 1
     * anpaiPeisong : -1
     * jiedanMoshi : 2
     * paidanren : null
     * paidanrenMing : null
     * peisongrenId : 136
     * peisongrenMing : 刘诗中远
     * peisongrenTel : null
     * peisongfei : 1
     * peisongfeiLaiyuan : 2
     * tuikuanfangshi : null
     * tuikuanJine : null
     * tuikuanYuanyin : null
     * xiadanShijian : 2017-11-23 08:55:51
     * fukuanShijian : 2017-11-23 08:55:51.48
     * paisongShijian : 2017-11-23 09:28:18.986
     * quhuoShijian : null
     * shouhuoShijian : null
     * pingjiaShijian : null
     * tuikuanShenqingShijian : null
     * tuihuanShijian : null
     * tuikuanYunxuShijian : null
     * quxiaoShijian : null
     * quxiaoYuanyin : null
     * wanchengShijian : null
     * tuikuanpicihao : null
     * tuikuanbiaozhi : null
     * yezhuId : 3401
     * yezhuZhanghao : 15243648097
     * yezhuShanchu : -1
     * dajianorder : 0
     * beizhu :
     * shangpinNum : null
     * jiesuanPeisongfei : null
     * isShouhou : null
     */

    private int id;//订单id
    private int xiangmuId;//项目id
    private String  xiangmuMing;//项目名
    private int gongsiId;//公司id
    private String  gongsiMing;//公司名
    private String bianhao;//编号
    private int zhuangtai;//订单状态 1待支付、2待发货、3待收货、4待评价、5已完成、6退货中、7退款中、8已退款、9已取消、10退货完成
    private int shifouYejian;//是否夜间订单 -1夜间，1日间
    private String shouhuorenMing;//收货人姓名
    private String shouhuoDizhi;//收货地址
    private String shouhuoDianhua;//收货电话
    private double zongjine;//订单总金额
    private int dianziquan;//电子券抵扣
    private double  shijiJine;//实付金额
    private String  payjiaoyihao;//第三方支付交易号
    private int fukuanFangshi;//付款方式:1支付宝，2微信，3银联
    private int peisongFangshi;//配送方式：1商城配送，2自提
    private int anpaiPeisong;//配送方式：1商城配送，2自提
    private int jiedanMoshi;//接单模式：1派单，2抢单
    private int paidanren;//订单派单人(后台分配配送员的管理员)
    private String paidanrenMing;//订单派单人名
    private int peisongrenId;//订单配送人id
    private String peisongrenMing;//订单配送人名
    private String  peisongrenTel;//配送人电话
    private double peisongfei;//订单配送费
    private int peisongfeiLaiyuan;//配送费来源：1自提，2商家，3买家
    private int tuikuanfangshi;//退款方式 -1部分，1全额
    private double tuikuanJine;//退款金额
    private int tuikuanYuanyin;//退款原因：1质量问题，2服务，3无理由
    private String xiadanShijian;//下单时间
    private String fukuanShijian;//付款时间
    private String paisongShijian;//派单时间
    private String  quhuoShijian;//取货时间
    private String  shouhuoShijian;//收货时间
    private String  pingjiaShijian;//评价时间
    private String  tuikuanShenqingShijian;//申请退款时间
    private String  tuihuanShijian;//退还时间
    private String  tuikuanYunxuShijian;//允许退款时间
    private String  quxiaoShijian;//取消时间
    private String  quxiaoYuanyin;//取消原因
    private String  wanchengShijian;//完成订单时间
    private String  tuikuanpicihao;//订单退款批次号
    private String  tuikuanbiaozhi;//是否需要退款: 无需退款 需要退款 已退款至支付宝/微信/银联
    private int yezhuId;//业主id
    private String yezhuZhanghao;//用户账号
    private int yezhuShanchu;//业主在APP中是否删除标记,-1是未删除，1为删除
    private int dajianorder;//大件配送，0为正常配送订单，1为大件配送订单
    private String beizhu;//业主备注
    private int shangpinNum;//订单商品总件数
    private double jiesuanPeisongfei;//结算配送费，给配送员的配送费
    private int isShouhou;//是否申请售后，1是已申请，2是未申请

    private List<OrderDetailBean> orderDetailList;
    private boolean isShow;

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

    public String getXiangmuMing() {
        return xiangmuMing;
    }

    public void setXiangmuMing(String xiangmuMing) {
        this.xiangmuMing = xiangmuMing;
    }

    public int getGongsiId() {
        return gongsiId;
    }

    public void setGongsiId(int gongsiId) {
        this.gongsiId = gongsiId;
    }

    public String getGongsiMing() {
        return gongsiMing;
    }

    public void setGongsiMing(String gongsiMing) {
        this.gongsiMing = gongsiMing;
    }

    public String getBianhao() {
        return bianhao;
    }

    public void setBianhao(String bianhao) {
        this.bianhao = bianhao;
    }

    public int getZhuangtai() {
        return zhuangtai;
    }

    public void setZhuangtai(int zhuangtai) {
        this.zhuangtai = zhuangtai;
    }

    public int getShifouYejian() {
        return shifouYejian;
    }

    public void setShifouYejian(int shifouYejian) {
        this.shifouYejian = shifouYejian;
    }

    public String getShouhuorenMing() {
        return shouhuorenMing;
    }

    public void setShouhuorenMing(String shouhuorenMing) {
        this.shouhuorenMing = shouhuorenMing;
    }

    public String getShouhuoDizhi() {
        return shouhuoDizhi;
    }

    public void setShouhuoDizhi(String shouhuoDizhi) {
        this.shouhuoDizhi = shouhuoDizhi;
    }

    public String getShouhuoDianhua() {
        return shouhuoDianhua;
    }

    public void setShouhuoDianhua(String shouhuoDianhua) {
        this.shouhuoDianhua = shouhuoDianhua;
    }

    public double getZongjine() {
        return zongjine;
    }

    public void setZongjine(double zongjine) {
        this.zongjine = zongjine;
    }

    public int getDianziquan() {
        return dianziquan;
    }

    public void setDianziquan(int dianziquan) {
        this.dianziquan = dianziquan;
    }

    public double getShijiJine() {
        return shijiJine;
    }

    public void setShijiJine(double shijiJine) {
        this.shijiJine = shijiJine;
    }

    public String getPayjiaoyihao() {
        return payjiaoyihao;
    }

    public void setPayjiaoyihao(String payjiaoyihao) {
        this.payjiaoyihao = payjiaoyihao;
    }

    public int getFukuanFangshi() {
        return fukuanFangshi;
    }

    public void setFukuanFangshi(int fukuanFangshi) {
        this.fukuanFangshi = fukuanFangshi;
    }

    public int getPeisongFangshi() {
        return peisongFangshi;
    }

    public void setPeisongFangshi(int peisongFangshi) {
        this.peisongFangshi = peisongFangshi;
    }

    public int getAnpaiPeisong() {
        return anpaiPeisong;
    }

    public void setAnpaiPeisong(int anpaiPeisong) {
        this.anpaiPeisong = anpaiPeisong;
    }

    public int getJiedanMoshi() {
        return jiedanMoshi;
    }

    public void setJiedanMoshi(int jiedanMoshi) {
        this.jiedanMoshi = jiedanMoshi;
    }

    public int getPaidanren() {
        return paidanren;
    }

    public void setPaidanren(int paidanren) {
        this.paidanren = paidanren;
    }

    public String getPaidanrenMing() {
        return paidanrenMing;
    }

    public void setPaidanrenMing(String paidanrenMing) {
        this.paidanrenMing = paidanrenMing;
    }

    public int getPeisongrenId() {
        return peisongrenId;
    }

    public void setPeisongrenId(int peisongrenId) {
        this.peisongrenId = peisongrenId;
    }

    public String getPeisongrenMing() {
        return peisongrenMing;
    }

    public void setPeisongrenMing(String peisongrenMing) {
        this.peisongrenMing = peisongrenMing;
    }

    public String getPeisongrenTel() {
        return peisongrenTel;
    }

    public void setPeisongrenTel(String peisongrenTel) {
        this.peisongrenTel = peisongrenTel;
    }

    public double getPeisongfei() {
        return peisongfei;
    }

    public void setPeisongfei(double peisongfei) {
        this.peisongfei = peisongfei;
    }

    public int getPeisongfeiLaiyuan() {
        return peisongfeiLaiyuan;
    }

    public void setPeisongfeiLaiyuan(int peisongfeiLaiyuan) {
        this.peisongfeiLaiyuan = peisongfeiLaiyuan;
    }

    public int getTuikuanfangshi() {
        return tuikuanfangshi;
    }

    public void setTuikuanfangshi(int tuikuanfangshi) {
        this.tuikuanfangshi = tuikuanfangshi;
    }

    public double getTuikuanJine() {
        return tuikuanJine;
    }

    public void setTuikuanJine(double tuikuanJine) {
        this.tuikuanJine = tuikuanJine;
    }

    public int getTuikuanYuanyin() {
        return tuikuanYuanyin;
    }

    public void setTuikuanYuanyin(int tuikuanYuanyin) {
        this.tuikuanYuanyin = tuikuanYuanyin;
    }

    public String getXiadanShijian() {
        return xiadanShijian;
    }

    public void setXiadanShijian(String xiadanShijian) {
        this.xiadanShijian = xiadanShijian;
    }

    public String getFukuanShijian() {
        return fukuanShijian;
    }

    public void setFukuanShijian(String fukuanShijian) {
        this.fukuanShijian = fukuanShijian;
    }

    public String getPaisongShijian() {
        return paisongShijian;
    }

    public void setPaisongShijian(String paisongShijian) {
        this.paisongShijian = paisongShijian;
    }

    public String getQuhuoShijian() {
        return quhuoShijian;
    }

    public void setQuhuoShijian(String quhuoShijian) {
        this.quhuoShijian = quhuoShijian;
    }

    public String getShouhuoShijian() {
        return shouhuoShijian;
    }

    public void setShouhuoShijian(String shouhuoShijian) {
        this.shouhuoShijian = shouhuoShijian;
    }

    public String getPingjiaShijian() {
        return pingjiaShijian;
    }

    public void setPingjiaShijian(String pingjiaShijian) {
        this.pingjiaShijian = pingjiaShijian;
    }

    public String getTuikuanShenqingShijian() {
        return tuikuanShenqingShijian;
    }

    public void setTuikuanShenqingShijian(String tuikuanShenqingShijian) {
        this.tuikuanShenqingShijian = tuikuanShenqingShijian;
    }

    public String getTuihuanShijian() {
        return tuihuanShijian;
    }

    public void setTuihuanShijian(String tuihuanShijian) {
        this.tuihuanShijian = tuihuanShijian;
    }

    public String getTuikuanYunxuShijian() {
        return tuikuanYunxuShijian;
    }

    public void setTuikuanYunxuShijian(String tuikuanYunxuShijian) {
        this.tuikuanYunxuShijian = tuikuanYunxuShijian;
    }

    public String getQuxiaoShijian() {
        return quxiaoShijian;
    }

    public void setQuxiaoShijian(String quxiaoShijian) {
        this.quxiaoShijian = quxiaoShijian;
    }

    public String getQuxiaoYuanyin() {
        return quxiaoYuanyin;
    }

    public void setQuxiaoYuanyin(String quxiaoYuanyin) {
        this.quxiaoYuanyin = quxiaoYuanyin;
    }

    public String getWanchengShijian() {
        return wanchengShijian;
    }

    public void setWanchengShijian(String wanchengShijian) {
        this.wanchengShijian = wanchengShijian;
    }

    public String getTuikuanpicihao() {
        return tuikuanpicihao;
    }

    public void setTuikuanpicihao(String tuikuanpicihao) {
        this.tuikuanpicihao = tuikuanpicihao;
    }

    public String getTuikuanbiaozhi() {
        return tuikuanbiaozhi;
    }

    public void setTuikuanbiaozhi(String tuikuanbiaozhi) {
        this.tuikuanbiaozhi = tuikuanbiaozhi;
    }

    public int getYezhuId() {
        return yezhuId;
    }

    public void setYezhuId(int yezhuId) {
        this.yezhuId = yezhuId;
    }

    public String getYezhuZhanghao() {
        return yezhuZhanghao;
    }

    public void setYezhuZhanghao(String yezhuZhanghao) {
        this.yezhuZhanghao = yezhuZhanghao;
    }

    public int getYezhuShanchu() {
        return yezhuShanchu;
    }

    public void setYezhuShanchu(int yezhuShanchu) {
        this.yezhuShanchu = yezhuShanchu;
    }

    public int getDajianorder() {
        return dajianorder;
    }

    public void setDajianorder(int dajianorder) {
        this.dajianorder = dajianorder;
    }

    public String getBeizhu() {
        return beizhu;
    }

    public void setBeizhu(String beizhu) {
        this.beizhu = beizhu;
    }

    public int getShangpinNum() {
        return shangpinNum;
    }

    public void setShangpinNum(int shangpinNum) {
        this.shangpinNum = shangpinNum;
    }

    public double getJiesuanPeisongfei() {
        return jiesuanPeisongfei;
    }

    public void setJiesuanPeisongfei(double jiesuanPeisongfei) {
        this.jiesuanPeisongfei = jiesuanPeisongfei;
    }

    public int getIsShouhou() {
        return isShouhou;
    }

    public void setIsShouhou(int isShouhou) {
        this.isShouhou = isShouhou;
    }

    public List<OrderDetailBean> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(List<OrderDetailBean> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    @Override
    public String toString() {
        return "OrderBean{" +
                "id=" + id +
                ", xiangmuId=" + xiangmuId +
                ", xiangmuMing='" + xiangmuMing + '\'' +
                ", gongsiId=" + gongsiId +
                ", gongsiMing='" + gongsiMing + '\'' +
                ", bianhao='" + bianhao + '\'' +
                ", zhuangtai=" + zhuangtai +
                ", shifouYejian=" + shifouYejian +
                ", shouhuorenMing='" + shouhuorenMing + '\'' +
                ", shouhuoDizhi='" + shouhuoDizhi + '\'' +
                ", shouhuoDianhua='" + shouhuoDianhua + '\'' +
                ", zongjine=" + zongjine +
                ", dianziquan=" + dianziquan +
                ", shijiJine=" + shijiJine +
                ", payjiaoyihao='" + payjiaoyihao + '\'' +
                ", fukuanFangshi=" + fukuanFangshi +
                ", peisongFangshi=" + peisongFangshi +
                ", anpaiPeisong=" + anpaiPeisong +
                ", jiedanMoshi=" + jiedanMoshi +
                ", paidanren=" + paidanren +
                ", paidanrenMing='" + paidanrenMing + '\'' +
                ", peisongrenId=" + peisongrenId +
                ", peisongrenMing='" + peisongrenMing + '\'' +
                ", peisongrenTel='" + peisongrenTel + '\'' +
                ", peisongfei=" + peisongfei +
                ", peisongfeiLaiyuan=" + peisongfeiLaiyuan +
                ", tuikuanfangshi=" + tuikuanfangshi +
                ", tuikuanJine=" + tuikuanJine +
                ", tuikuanYuanyin=" + tuikuanYuanyin +
                ", xiadanShijian='" + xiadanShijian + '\'' +
                ", fukuanShijian='" + fukuanShijian + '\'' +
                ", paisongShijian='" + paisongShijian + '\'' +
                ", quhuoShijian='" + quhuoShijian + '\'' +
                ", shouhuoShijian='" + shouhuoShijian + '\'' +
                ", pingjiaShijian='" + pingjiaShijian + '\'' +
                ", tuikuanShenqingShijian='" + tuikuanShenqingShijian + '\'' +
                ", tuihuanShijian='" + tuihuanShijian + '\'' +
                ", tuikuanYunxuShijian='" + tuikuanYunxuShijian + '\'' +
                ", quxiaoShijian='" + quxiaoShijian + '\'' +
                ", quxiaoYuanyin='" + quxiaoYuanyin + '\'' +
                ", wanchengShijian='" + wanchengShijian + '\'' +
                ", tuikuanpicihao='" + tuikuanpicihao + '\'' +
                ", tuikuanbiaozhi='" + tuikuanbiaozhi + '\'' +
                ", yezhuId=" + yezhuId +
                ", yezhuZhanghao='" + yezhuZhanghao + '\'' +
                ", yezhuShanchu=" + yezhuShanchu +
                ", dajianorder=" + dajianorder +
                ", beizhu='" + beizhu + '\'' +
                ", shangpinNum=" + shangpinNum +
                ", jiesuanPeisongfei=" + jiesuanPeisongfei +
                ", isShouhou=" + isShouhou +
                ", orderDetailList=" + orderDetailList +
                ", isShow=" + isShow +
                '}';
    }
}
