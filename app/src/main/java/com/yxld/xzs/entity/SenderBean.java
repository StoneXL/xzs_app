package com.yxld.xzs.entity;

/**
 * 配送人实体类
 * Created by William on 2017/11/29.
 */

public class SenderBean {

    /**
     * cxwyPeisongId : 458
     * cxwyPeisongName : 晓晓
     * cxwyPeisongPhone : 18711001698
     * cxwyPeisongTotal : null
     * cxwyPeisongProject : null
     * cxwyPeisongPassword : null
     * cxwyPeisongTotalPrice : null
     * cxwyPeisongState : null
     * cxwyPeisongFuzeren : null
     * cxwyPeisongBackup : null
     * cxwyType : null
     * xnName : null
     */

    private int cxwyPeisongId;//配送主键id --管理员ID
    private String cxwyPeisongName;//配送人名称
    private String cxwyPeisongPhone;//配送人电话
    private String cxwyPeisongTotal;//配送次数
    private String cxwyPeisongProject;//配送员所属小区
    private String cxwyPeisongPassword;//配送人密码
    private String cxwyPeisongTotalPrice;//配送人总收入
    private String cxwyPeisongState;// 配送人是否接单状态   开启 关闭
    private int cxwyPeisongFuzeren;// 是否是负责人：0负责人 1非负责人
    private String cxwyPeisongBackup;//备注（指派人ID-）
    private int cxwyType;//用状态0使用 1禁用
    private String xnName;////项目名称
    private boolean isChoose;//是否被选择的标志

    public int getCxwyPeisongId() {
        return cxwyPeisongId;
    }

    public void setCxwyPeisongId(int cxwyPeisongId) {
        this.cxwyPeisongId = cxwyPeisongId;
    }

    public String getCxwyPeisongName() {
        return cxwyPeisongName;
    }

    public void setCxwyPeisongName(String cxwyPeisongName) {
        this.cxwyPeisongName = cxwyPeisongName;
    }

    public String getCxwyPeisongPhone() {
        return cxwyPeisongPhone;
    }

    public void setCxwyPeisongPhone(String cxwyPeisongPhone) {
        this.cxwyPeisongPhone = cxwyPeisongPhone;
    }

    public String getCxwyPeisongTotal() {
        return cxwyPeisongTotal;
    }

    public void setCxwyPeisongTotal(String cxwyPeisongTotal) {
        this.cxwyPeisongTotal = cxwyPeisongTotal;
    }

    public String getCxwyPeisongProject() {
        return cxwyPeisongProject;
    }

    public void setCxwyPeisongProject(String cxwyPeisongProject) {
        this.cxwyPeisongProject = cxwyPeisongProject;
    }

    public String getCxwyPeisongPassword() {
        return cxwyPeisongPassword;
    }

    public void setCxwyPeisongPassword(String cxwyPeisongPassword) {
        this.cxwyPeisongPassword = cxwyPeisongPassword;
    }

    public String getCxwyPeisongTotalPrice() {
        return cxwyPeisongTotalPrice;
    }

    public void setCxwyPeisongTotalPrice(String cxwyPeisongTotalPrice) {
        this.cxwyPeisongTotalPrice = cxwyPeisongTotalPrice;
    }

    public String getCxwyPeisongState() {
        return cxwyPeisongState;
    }

    public void setCxwyPeisongState(String cxwyPeisongState) {
        this.cxwyPeisongState = cxwyPeisongState;
    }

    public int getCxwyPeisongFuzeren() {
        return cxwyPeisongFuzeren;
    }

    public void setCxwyPeisongFuzeren(int cxwyPeisongFuzeren) {
        this.cxwyPeisongFuzeren = cxwyPeisongFuzeren;
    }

    public String getCxwyPeisongBackup() {
        return cxwyPeisongBackup;
    }

    public void setCxwyPeisongBackup(String cxwyPeisongBackup) {
        this.cxwyPeisongBackup = cxwyPeisongBackup;
    }

    public int getCxwyType() {
        return cxwyType;
    }

    public void setCxwyType(int cxwyType) {
        this.cxwyType = cxwyType;
    }

    public String getXnName() {
        return xnName;
    }

    public void setXnName(String xnName) {
        this.xnName = xnName;
    }

    public boolean isChecked() {
        return isChoose;
    }

    public void setChecked(boolean checked) {
        isChoose = checked;
    }

    @Override
    public String toString() {
        return "SenderBean{" +
                "cxwyPeisongId=" + cxwyPeisongId +
                ", cxwyPeisongName='" + cxwyPeisongName + '\'' +
                ", cxwyPeisongPhone='" + cxwyPeisongPhone + '\'' +
                ", cxwyPeisongTotal='" + cxwyPeisongTotal + '\'' +
                ", cxwyPeisongProject='" + cxwyPeisongProject + '\'' +
                ", cxwyPeisongPassword='" + cxwyPeisongPassword + '\'' +
                ", cxwyPeisongTotalPrice='" + cxwyPeisongTotalPrice + '\'' +
                ", cxwyPeisongState='" + cxwyPeisongState + '\'' +
                ", cxwyPeisongFuzeren=" + cxwyPeisongFuzeren +
                ", cxwyPeisongBackup='" + cxwyPeisongBackup + '\'' +
                ", cxwyType=" + cxwyType +
                ", xnName='" + xnName + '\'' +
                ", isChoose=" + isChoose +
                '}';
    }
}
