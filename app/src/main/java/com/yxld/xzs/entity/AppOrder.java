package com.yxld.xzs.entity;


/**
 * CxwyMallOrder entity. @author MyEclipse Persistence Tools
 * 商城订单信息
 */

public class AppOrder {

	// Fields
	private Integer dingdanId;//订单id

	private String dingdanName;//姓名

	private String dingdanDizhi;//地址

	private String dingdanTel;//电话

	private String dingdanXiadanTime;//下单时间

	private String dingdanArrivedTime;//到达时间

	private String dingdanOverTime;//使用时间

	private Float dingdanTotalRmb;//总金额

	private String dingdanUserName;//用户名

	private String dingdanZhuangtai;//订单状态

	private String dingdanImgSrc;//订单图片
	private Integer dingdanUserId;//用户id

	private String dingdanBeiyong1;//付款方式

	private String dingdanBeiyong2;//前台删除标记,0是未删除，1为删除

	private String dingdanBeiyong3;//订单备注

	private String dingdanBeiyong4;//取消原因

	private String dingdanVillage;  //订单小区

	private String dingdanBianhao; //订单编号

	private Integer dingdanGoodsnum; //订单中商品数量

	private String dingdanShouliren; //订单受理人

	private Integer dingdanPeisongrenid; //订单配送人id

	private String dingdanBeiyong5; //订单配送人名

	private String dingdanPaisongtime; //派单时间

	/** 订单中使用优惠券id*/
	private Integer dingdanYouhuiquanId;

	/** 订单中使用优惠价格*/
	private String dingdanYouhuijia;

	private String dingdanPayOrderhao; //订单退款批次号
	private String dingdanPayJiaoyihao; //第三方支付交易号

	private String dingdanBeiyong6;  //是否需要退款: 无需退款  需要退款  已退款至xx

	private String dingdanPayTime; //支付时间

	// Constructors

	/** default constructor */
	public AppOrder() {
	}

	/** full constructor */
	public AppOrder(String dingdanVillage, String dingdanName,
					String dingdanDizhi, String dingdanTel, String dingdanXiadanTime,
					String dingdanArrivedTime, String dingdanOverTime,
					Float dingdanTotalRmb, String dingdanUserName,
					String dingdanZhuangtai, String dingdanImgSrc,
					Integer dingdanUserId, String dingdanBeiyong1,
					String dingdanBeiyong2, String dingdanBeiyong3,
					String dingdanBeiyong4, String dingdanBianhao,
					Integer dingdanGoodsnum, String dingdanShouliren,
					Integer dingdanPeisongrenid, String dingdanBeiyong5,
					String dingdanPaisongtime, String dingdanBeiyong6,
					Integer dingdanYouhuiquanId, String dingdanYouhuijia) {
		this.dingdanVillage = dingdanVillage;
		this.dingdanName = dingdanName;
		this.dingdanDizhi = dingdanDizhi;
		this.dingdanTel = dingdanTel;
		this.dingdanXiadanTime = dingdanXiadanTime;
		this.dingdanArrivedTime = dingdanArrivedTime;
		this.dingdanOverTime = dingdanOverTime;
		this.dingdanTotalRmb = dingdanTotalRmb;
		this.dingdanUserName = dingdanUserName;
		this.dingdanZhuangtai = dingdanZhuangtai;
		this.dingdanImgSrc = dingdanImgSrc;
		this.dingdanUserId = dingdanUserId;
		this.dingdanBeiyong1 = dingdanBeiyong1;
		this.dingdanBeiyong2 = dingdanBeiyong2;
		this.dingdanBeiyong3 = dingdanBeiyong3;
		this.dingdanBeiyong4 = dingdanBeiyong4;
		this.dingdanBianhao = dingdanBianhao;
		this.dingdanGoodsnum = dingdanGoodsnum;
		this.dingdanShouliren = dingdanShouliren;
		this.dingdanPeisongrenid = dingdanPeisongrenid;
		this.dingdanBeiyong5 = dingdanBeiyong5;
		this.dingdanPaisongtime = dingdanPaisongtime;
		this.dingdanBeiyong6 = dingdanBeiyong6;
		this.dingdanYouhuiquanId = dingdanYouhuiquanId;
		this.dingdanYouhuijia = dingdanYouhuijia;
	}

	// Property accessors

	public Integer getDingdanId() {
		return this.dingdanId;
	}

	public void setDingdanId(Integer dingdanId) {
		this.dingdanId = dingdanId;
	}

	public String getDingdanVillage() {
		return this.dingdanVillage;
	}

	public void setDingdanVillage(String dingdanVillage) {
		this.dingdanVillage = dingdanVillage;
	}

	public String getDingdanName() {
		return this.dingdanName;
	}

	public void setDingdanName(String dingdanName) {
		this.dingdanName = dingdanName;
	}

	public String getDingdanDizhi() {
		return this.dingdanDizhi;
	}

	public void setDingdanDizhi(String dingdanDizhi) {
		this.dingdanDizhi = dingdanDizhi;
	}

	public String getDingdanTel() {
		return this.dingdanTel;
	}

	public void setDingdanTel(String dingdanTel) {
		this.dingdanTel = dingdanTel;
	}

	public String getDingdanXiadanTime() {
		return this.dingdanXiadanTime;
	}

	public void setDingdanXiadanTime(String dingdanXiadanTime) {
		this.dingdanXiadanTime = dingdanXiadanTime;
	}

	public String getDingdanArrivedTime() {
		return this.dingdanArrivedTime;
	}

	public void setDingdanArrivedTime(String dingdanArrivedTime) {
		this.dingdanArrivedTime = dingdanArrivedTime;
	}

	public String getDingdanOverTime() {
		return this.dingdanOverTime;
	}

	public void setDingdanOverTime(String dingdanOverTime) {
		this.dingdanOverTime = dingdanOverTime;
	}

	public Float getDingdanTotalRmb() {
		return this.dingdanTotalRmb;
	}

	public void setDingdanTotalRmb(Float dingdanTotalRmb) {
		this.dingdanTotalRmb = dingdanTotalRmb;
	}

	public String getDingdanUserName() {
		return this.dingdanUserName;
	}

	public void setDingdanUserName(String dingdanUserName) {
		this.dingdanUserName = dingdanUserName;
	}

	public String getDingdanZhuangtai() {
		return this.dingdanZhuangtai;
	}

	public void setDingdanZhuangtai(String dingdanZhuangtai) {
		this.dingdanZhuangtai = dingdanZhuangtai;
	}

	public String getDingdanImgSrc() {
		return this.dingdanImgSrc;
	}

	public void setDingdanImgSrc(String dingdanImgSrc) {
		this.dingdanImgSrc = dingdanImgSrc;
	}

	public Integer getDingdanUserId() {
		return this.dingdanUserId;
	}

	public void setDingdanUserId(Integer dingdanUserId) {
		this.dingdanUserId = dingdanUserId;
	}

	public String getDingdanBeiyong1() {
		return this.dingdanBeiyong1;
	}

	public void setDingdanBeiyong1(String dingdanBeiyong1) {
		this.dingdanBeiyong1 = dingdanBeiyong1;
	}

	public String getDingdanBeiyong2() {
		return this.dingdanBeiyong2;
	}

	public void setDingdanBeiyong2(String dingdanBeiyong2) {
		this.dingdanBeiyong2 = dingdanBeiyong2;
	}

	public String getDingdanBeiyong3() {
		return this.dingdanBeiyong3;
	}

	public void setDingdanBeiyong3(String dingdanBeiyong3) {
		this.dingdanBeiyong3 = dingdanBeiyong3;
	}

	public String getDingdanBeiyong4() {
		return this.dingdanBeiyong4;
	}

	public void setDingdanBeiyong4(String dingdanBeiyong4) {
		this.dingdanBeiyong4 = dingdanBeiyong4;
	}

	public String getDingdanBianhao() {
		return this.dingdanBianhao;
	}

	public void setDingdanBianhao(String dingdanBianhao) {
		this.dingdanBianhao = dingdanBianhao;
	}

	public Integer getDingdanGoodsnum() {
		return this.dingdanGoodsnum;
	}

	public void setDingdanGoodsnum(Integer dingdanGoodsnum) {
		this.dingdanGoodsnum = dingdanGoodsnum;
	}

	public String getDingdanShouliren() {
		return this.dingdanShouliren;
	}

	public void setDingdanShouliren(String dingdanShouliren) {
		this.dingdanShouliren = dingdanShouliren;
	}

	public Integer getDingdanPeisongrenid() {
		return this.dingdanPeisongrenid;
	}

	public void setDingdanPeisongrenid(Integer dingdanPeisongrenid) {
		this.dingdanPeisongrenid = dingdanPeisongrenid;
	}

	public String getDingdanBeiyong5() {
		return this.dingdanBeiyong5;
	}

	public void setDingdanBeiyong5(String dingdanBeiyong5) {
		this.dingdanBeiyong5 = dingdanBeiyong5;
	}

	public String getDingdanPaisongtime() {
		return this.dingdanPaisongtime;
	}

	public void setDingdanPaisongtime(String dingdanPaisongtime) {
		this.dingdanPaisongtime = dingdanPaisongtime;
	}

	public String getDingdanBeiyong6() {
		return this.dingdanBeiyong6;
	}

	public void setDingdanBeiyong6(String dingdanBeiyong6) {
		this.dingdanBeiyong6 = dingdanBeiyong6;
	}

	public Integer getDingdanYouhuiquanId() {
		return this.dingdanYouhuiquanId;
	}

	public void setDingdanYouhuiquanId(Integer dingdanYouhuiquanId) {
		this.dingdanYouhuiquanId = dingdanYouhuiquanId;
	}

	public String getDingdanYouhuijia() {
		return this.dingdanYouhuijia;
	}

	public void setDingdanYouhuijia(String dingdanYouhuijia) {
		this.dingdanYouhuijia = dingdanYouhuijia;
	}

	public String getDingdanPayOrderhao() {
		return dingdanPayOrderhao;
	}

	public void setDingdanPayOrderhao(String dingdanPayOrderhao) {
		this.dingdanPayOrderhao = dingdanPayOrderhao;
	}

	public String getDingdanPayJiaoyihao() {
		return dingdanPayJiaoyihao;
	}

	public void setDingdanPayJiaoyihao(String dingdanPayJiaoyihao) {
		this.dingdanPayJiaoyihao = dingdanPayJiaoyihao;
	}

	public String getDingdanPayTime() {
		return dingdanPayTime;
	}

	public void setDingdanPayTime(String dingdanPayTime) {
		this.dingdanPayTime = dingdanPayTime;
	}

	@Override
	public String toString() {
		return "CxwyMallOrder [dingdanArrivedTime=" + dingdanArrivedTime
				+ ", dingdanBeiyong1=" + dingdanBeiyong1 + ", dingdanBeiyong2="
				+ dingdanBeiyong2 + ", dingdanBeiyong3=" + dingdanBeiyong3
				+ ", dingdanBeiyong4=" + dingdanBeiyong4 + ", dingdanBeiyong5="
				+ dingdanBeiyong5 + ", dingdanBeiyong6=" + dingdanBeiyong6
				+ ", dingdanBianhao=" + dingdanBianhao + ", dingdanDizhi="
				+ dingdanDizhi + ", dingdanGoodsnum=" + dingdanGoodsnum
				+ ", dingdanId=" + dingdanId + ", dingdanImgSrc="
				+ dingdanImgSrc + ", dingdanName=" + dingdanName
				+ ", dingdanOverTime=" + dingdanOverTime
				+ ", dingdanPaisongtime=" + dingdanPaisongtime
				+ ", dingdanPayJiaoyihao=" + dingdanPayJiaoyihao
				+ ", dingdanPayOrderhao=" + dingdanPayOrderhao
				+ ", dingdanPayTime=" + dingdanPayTime
				+ ", dingdanPeisongrenid=" + dingdanPeisongrenid
				+ ", dingdanShouliren=" + dingdanShouliren + ", dingdanTel="
				+ dingdanTel + ", dingdanTotalRmb=" + dingdanTotalRmb
				+ ", dingdanUserId=" + dingdanUserId + ", dingdanUserName="
				+ dingdanUserName + ", dingdanVillage=" + dingdanVillage
				+ ", dingdanXiadanTime=" + dingdanXiadanTime
				+ ", dingdanYouhuijia=" + dingdanYouhuijia
				+ ", dingdanYouhuiquanId=" + dingdanYouhuiquanId
				+ ", dingdanZhuangtai=" + dingdanZhuangtai + "]";
	}


}