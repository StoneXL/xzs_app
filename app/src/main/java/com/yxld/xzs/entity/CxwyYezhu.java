package com.yxld.xzs.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author Yuan.Y.Q
 * @Date 2017/9/20.
 */

public class CxwyYezhu extends BaseBack {
    private List<DataBean> data;
    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }


public static class DataBean implements Parcelable{
    /**
     * {"yezhuBeizhu":"","yezhuBiezhu1":null,"yezhuCardNum":"",
     * "yezhuChuangxinhao":"","yezhuGzdw":"","yezhuId":2157,
     * "yezhuIsUse":0,"yezhuLoupan":"","yezhuName":"肖楠",
     * "yezhuPhone":"","yezhuPwd":"","yezhuSex":"","yezhuSfzSrc1":"",
     * "yezhuSfzSrc2":"","yezhuShouji":"13677407464","yezhuType":0,"yezhuTypeValue":"","yezhuXmId":0
     */
    //[{"yezhuBeizhu":"","yezhuBiezhu1":null,"yezhuCardNum":"","yezhuChuangxinhao":"","yezhuGzdw":"","yezhuId":160,"yezhuIsUse":0,"yezhuLoupan":"","yezhuName":"向际群","yezhuPhone":"","yezhuPwd":"","yezhuSex":"","yezhuSfzSrc1":"","yezhuSfzSrc2":"","yezhuShouji":"18975885588","yezhuType":0,"yezhuTypeValue":"","yezhuXmId":0}]
    private String yezhuBeizhu;
    private String yezhuBiezhu1;
    private String yezhuCardNum;
    private String yezhuChuangxinhao;
    private String yezhuGzdw;
    private int    yezhuId;
    private int yezhuIsUse;
    private String yezhuLoupan;
    private String yezhuName;
    private String yezhuPhone;
    private String yezhuPwd;
    private String yezhuSex;
    private String yezhuSfzSrc1;
    private String yezhuSfzSrc2;
    private String yezhuShouji;
    private String yezhuTypeValue;
    private int yezhuType;
    private int yezhuXmId;

    protected DataBean(Parcel in) {
        yezhuBeizhu = in.readString();
        yezhuBiezhu1 = in.readString();
        yezhuCardNum = in.readString();
        yezhuChuangxinhao = in.readString();
        yezhuGzdw = in.readString();
        yezhuId = in.readInt();
        yezhuIsUse = in.readInt();
        yezhuLoupan = in.readString();
        yezhuName = in.readString();
        yezhuPhone = in.readString();
        yezhuPwd = in.readString();
        yezhuSex = in.readString();
        yezhuSfzSrc1 = in.readString();
        yezhuSfzSrc2 = in.readString();
        yezhuShouji = in.readString();
        yezhuTypeValue=in.readString();
        yezhuType = in.readInt();
        yezhuXmId = in.readInt();
    }

    public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
        @Override
        public DataBean createFromParcel(Parcel in) {
            return new DataBean(in);
        }

        @Override
        public DataBean[] newArray(int size) {
            return new DataBean[size];
        }
    };

    public String getYezhuBeizhu() {
        return yezhuBeizhu;
    }

    public void setYezhuBeizhu(String yezhuBeizhu) {
        this.yezhuBeizhu = yezhuBeizhu;
    }

    public String getYezhuBiezhu1() {
        return yezhuBiezhu1;
    }

    public void setYezhuBiezhu1(String yezhuBiezhu1) {
        this.yezhuBiezhu1 = yezhuBiezhu1;
    }

    public String getYezhuCardNum() {
        return yezhuCardNum;
    }

    public void setYezhuCardNum(String yezhuCardNum) {
        this.yezhuCardNum = yezhuCardNum;
    }

    public String getYezhuChuangxinhao() {
        return yezhuChuangxinhao;
    }

    public void setYezhuChuangxinhao(String yezhuChuangxinhao) {
        this.yezhuChuangxinhao = yezhuChuangxinhao;
    }

    public String getYezhuGzdw() {
        return yezhuGzdw;
    }

    public void setYezhuGzdw(String yezhuGzdw) {
        this.yezhuGzdw = yezhuGzdw;
    }

    public int getYezhuId() {
        return yezhuId;
    }

    public void setYezhuId(int yezhuId) {
        this.yezhuId = yezhuId;
    }

    public int getYezhuIsUse() {
        return yezhuIsUse;
    }

    public void setYezhuIsUse(int yezhuIsUse) {
        this.yezhuIsUse = yezhuIsUse;
    }

    public String getYezhuLoupan() {
        return yezhuLoupan;
    }

    public void setYezhuLoupan(String yezhuLoupan) {
        this.yezhuLoupan = yezhuLoupan;
    }

    public String getYezhuName() {
        return yezhuName;
    }

    public void setYezhuName(String yezhuName) {
        this.yezhuName = yezhuName;
    }

    public String getYezhuPhone() {
        return yezhuPhone;
    }

    public void setYezhuPhone(String yezhuPhone) {
        this.yezhuPhone = yezhuPhone;
    }

    public String getYezhuPwd() {
        return yezhuPwd;
    }

    public void setYezhuPwd(String yezhuPwd) {
        this.yezhuPwd = yezhuPwd;
    }

    public String getYezhuSex() {
        return yezhuSex;
    }

    public void setYezhuSex(String yezhuSex) {
        this.yezhuSex = yezhuSex;
    }

    public String getYezhuSfzSrc1() {
        return yezhuSfzSrc1;
    }

    public void setYezhuSfzSrc1(String yezhuSfzSrc1) {
        this.yezhuSfzSrc1 = yezhuSfzSrc1;
    }

    public String getYezhuSfzSrc2() {
        return yezhuSfzSrc2;
    }

    public void setYezhuSfzSrc2(String yezhuSfzSrc2) {
        this.yezhuSfzSrc2 = yezhuSfzSrc2;
    }

    public String getYezhuShouji() {
        return yezhuShouji;
    }

    public void setYezhuShouji(String yezhuShouji) {
        this.yezhuShouji = yezhuShouji;
    }

    public int getYezhuType() {
        return yezhuType;
    }

    public void setYezhuType(int yezhuType) {
        this.yezhuType = yezhuType;
    }

    public String getYezhuTypeValue() {
        return yezhuTypeValue;
    }

    public void setYezhuTypeValue(String yezhuTypeValue) {
        this.yezhuTypeValue = yezhuTypeValue;
    }

    public int getYezhuXmId() {
        return yezhuXmId;
    }

    public void setYezhuXmId(int yezhuXmId) {
        this.yezhuXmId = yezhuXmId;
    }

    @Override
    public String toString() {
        return "Yezhu{" +
                "yezhuBeizhu='" + yezhuBeizhu + '\'' +
                ", yezhuBiezhu1='" + yezhuBiezhu1 + '\'' +
                ", yezhuCardNum='" + yezhuCardNum + '\'' +
                ", yezhuChuangxinhao='" + yezhuChuangxinhao + '\'' +
                ", yezhuGzdw='" + yezhuGzdw + '\'' +
                ", yezhuId=" + yezhuId +
                ", yezhuIsUse=" + yezhuIsUse +
                ", yezhuLoupan='" + yezhuLoupan + '\'' +
                ", yezhuName='" + yezhuName + '\'' +
                ", yezhuPhone='" + yezhuPhone + '\'' +
                ", yezhuPwd='" + yezhuPwd + '\'' +
                ", yezhuSex='" + yezhuSex + '\'' +
                ", yezhuSfzSrc1='" + yezhuSfzSrc1 + '\'' +
                ", yezhuSfzSrc2='" + yezhuSfzSrc2 + '\'' +
                ", yezhuShouji='" + yezhuShouji + '\'' +
                ", yezhuType=" + yezhuType +
                ", yezhuXmId=" + yezhuXmId +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(yezhuBeizhu);
        dest.writeString(yezhuBiezhu1);
        dest.writeString(yezhuCardNum);
        dest.writeString(yezhuChuangxinhao);
        dest.writeString(yezhuGzdw);
        dest.writeInt(yezhuId);
        dest.writeInt(yezhuIsUse);
        dest.writeString(yezhuLoupan);
        dest.writeString(yezhuName);
        dest.writeString(yezhuPhone);
        dest.writeString(yezhuPwd);
        dest.writeString(yezhuSex);
        dest.writeString(yezhuSfzSrc1);
        dest.writeString(yezhuSfzSrc2);
        dest.writeString(yezhuShouji);
        dest.writeString(yezhuTypeValue);
        dest.writeInt(yezhuType);
        dest.writeInt(yezhuXmId);
    }
}
}
