package com.yxld.xzs.entity;

/**
 * CxwyMallPeisongorder entity. @author MyEclipse Persistence Tools
 * 配送人订单信息
 */

public class AppPeisongorder implements java.io.Serializable {

	// Fields
	/** 配送明细表id */
	private Integer peisongOrderId;
	/** 配送人id */
	private Integer peisongOrderPeisongId;
	/** 配送单配送人姓名 */
	private String peisongOrderPeisongName;
	/** 订单编号 */
	private String peisongOrderOrderBianhao;
	/** 完成时间 */
	private String peisongOrderWanchengTime;
	/** 订单收入 */
	private String peisongOrderShouru;
	/** 所属项目 */
	private String peisongOrderProject;
	/** 配送地址 */
	private String peisongOrderAddress;
	/** 订单状态 */
	private String peisongOrderOrderState;
	/** 配送开始时间 */
	private String peisongOrderKaishiTime;
	/** 备用1 */
	private String peisongOrderBeiyong1;
	/** 备用2 */
	private String peisongOrderBeiyong2;
	/** 备用3 */
	private String peisongOrderBeiyong3;

	// Constructors

	/** default constructor */
	public AppPeisongorder() {
	}

	/** minimal constructor */
	public AppPeisongorder(Integer peisongOrderId) {
		this.peisongOrderId = peisongOrderId;
	}

	/** full constructor */
	public AppPeisongorder(Integer peisongOrderId,
						   Integer peisongOrderPeisongId, String peisongOrderPeisongName,
						   String peisongOrderOrderBianhao, String peisongOrderWanchengTime,
						   String peisongOrderShouru, String peisongOrderProject,
						   String peisongOrderAddress, String peisongOrderOrderState,
						   String peisongOrderKaishiTime, String peisongOrderBeiyong1,
						   String peisongOrderBeiyong2, String peisongOrderBeiyong3) {
		this.peisongOrderId = peisongOrderId;
		this.peisongOrderPeisongId = peisongOrderPeisongId;
		this.peisongOrderPeisongName = peisongOrderPeisongName;
		this.peisongOrderOrderBianhao = peisongOrderOrderBianhao;
		this.peisongOrderWanchengTime = peisongOrderWanchengTime;
		this.peisongOrderShouru = peisongOrderShouru;
		this.peisongOrderProject = peisongOrderProject;
		this.peisongOrderAddress = peisongOrderAddress;
		this.peisongOrderOrderState = peisongOrderOrderState;
		this.peisongOrderKaishiTime = peisongOrderKaishiTime;
		this.peisongOrderBeiyong1 = peisongOrderBeiyong1;
		this.peisongOrderBeiyong2 = peisongOrderBeiyong2;
		this.peisongOrderBeiyong3 = peisongOrderBeiyong3;
	}

	// Property accessors

	public Integer getPeisongOrderId() {
		return this.peisongOrderId;
	}

	public void setPeisongOrderId(Integer peisongOrderId) {
		this.peisongOrderId = peisongOrderId;
	}

	public Integer getPeisongOrderPeisongId() {
		return this.peisongOrderPeisongId;
	}

	public void setPeisongOrderPeisongId(Integer peisongOrderPeisongId) {
		this.peisongOrderPeisongId = peisongOrderPeisongId;
	}

	public String getPeisongOrderPeisongName() {
		return this.peisongOrderPeisongName;
	}

	public void setPeisongOrderPeisongName(String peisongOrderPeisongName) {
		this.peisongOrderPeisongName = peisongOrderPeisongName;
	}

	public String getPeisongOrderOrderBianhao() {
		return this.peisongOrderOrderBianhao;
	}

	public void setPeisongOrderOrderBianhao(String peisongOrderOrderBianhao) {
		this.peisongOrderOrderBianhao = peisongOrderOrderBianhao;
	}

	public String getPeisongOrderWanchengTime() {
		return this.peisongOrderWanchengTime;
	}

	public void setPeisongOrderWanchengTime(String peisongOrderWanchengTime) {
		this.peisongOrderWanchengTime = peisongOrderWanchengTime;
	}

	public String getPeisongOrderShouru() {
		return this.peisongOrderShouru;
	}

	public void setPeisongOrderShouru(String peisongOrderShouru) {
		this.peisongOrderShouru = peisongOrderShouru;
	}

	public String getPeisongOrderProject() {
		return this.peisongOrderProject;
	}

	public void setPeisongOrderProject(String peisongOrderProject) {
		this.peisongOrderProject = peisongOrderProject;
	}

	public String getPeisongOrderAddress() {
		return this.peisongOrderAddress;
	}

	public void setPeisongOrderAddress(String peisongOrderAddress) {
		this.peisongOrderAddress = peisongOrderAddress;
	}

	public String getPeisongOrderOrderState() {
		return this.peisongOrderOrderState;
	}

	public void setPeisongOrderOrderState(String peisongOrderOrderState) {
		this.peisongOrderOrderState = peisongOrderOrderState;
	}

	public String getPeisongOrderKaishiTime() {
		return this.peisongOrderKaishiTime;
	}

	public void setPeisongOrderKaishiTime(String peisongOrderKaishiTime) {
		this.peisongOrderKaishiTime = peisongOrderKaishiTime;
	}

	public String getPeisongOrderBeiyong1() {
		return this.peisongOrderBeiyong1;
	}

	public void setPeisongOrderBeiyong1(String peisongOrderBeiyong1) {
		this.peisongOrderBeiyong1 = peisongOrderBeiyong1;
	}

	public String getPeisongOrderBeiyong2() {
		return this.peisongOrderBeiyong2;
	}

	public void setPeisongOrderBeiyong2(String peisongOrderBeiyong2) {
		this.peisongOrderBeiyong2 = peisongOrderBeiyong2;
	}

	public String getPeisongOrderBeiyong3() {
		return this.peisongOrderBeiyong3;
	}

	public void setPeisongOrderBeiyong3(String peisongOrderBeiyong3) {
		this.peisongOrderBeiyong3 = peisongOrderBeiyong3;
	}

	@Override
	public String toString() {
		return "CxwyMallPeisongorder{" +
				"peisongOrderId=" + peisongOrderId +
				", peisongOrderPeisongId=" + peisongOrderPeisongId +
				", peisongOrderPeisongName='" + peisongOrderPeisongName + '\'' +
				", peisongOrderOrderBianhao='" + peisongOrderOrderBianhao + '\'' +
				", peisongOrderWanchengTime='" + peisongOrderWanchengTime + '\'' +
				", peisongOrderShouru='" + peisongOrderShouru + '\'' +
				", peisongOrderProject='" + peisongOrderProject + '\'' +
				", peisongOrderAddress='" + peisongOrderAddress + '\'' +
				", peisongOrderOrderState='" + peisongOrderOrderState + '\'' +
				", peisongOrderKaishiTime='" + peisongOrderKaishiTime + '\'' +
				", peisongOrderBeiyong1='" + peisongOrderBeiyong1 + '\'' +
				", peisongOrderBeiyong2='" + peisongOrderBeiyong2 + '\'' +
				", peisongOrderBeiyong3='" + peisongOrderBeiyong3 + '\'' +
				'}';
	}
}