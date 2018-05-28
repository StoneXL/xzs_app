package com.yxld.xzs.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author Yuan.Y.Q
 * @Date 2017/8/5.
 */

public class XunJianXiangEntity implements Parcelable{
    public List<XunJianXiangEntity> data;
    public int dianId;//巡检点id
    public int jiluId; //巡检记录了id
    public int xunjianxiangId;//巡检项id
    public String xunjianxiangName;
    public int xunjianxiangLeixin; //1为逻辑型2为数值型
    public String xunjianxiangLuojiName;
    public String xunjianxiangLuojiName2;
    public String xunjianxiangZhengchangzhi;
    public String xunjianxiangDanwei;//巡检项单位
    public String xunjianxiangDaAn; //答案
    public int isReplied;


    public XunJianXiangEntity() {
    }

    protected XunJianXiangEntity(Parcel in) {
        data = in.createTypedArrayList(XunJianXiangEntity.CREATOR);
        dianId = in.readInt();
        jiluId = in.readInt();
        xunjianxiangId = in.readInt();
        xunjianxiangName = in.readString();
        xunjianxiangLeixin = in.readInt();
        xunjianxiangLuojiName = in.readString();
        xunjianxiangLuojiName2 = in.readString();
        xunjianxiangZhengchangzhi = in.readString();
        xunjianxiangDanwei = in.readString();
        isReplied = in.readInt();
    }

    public static final Creator<XunJianXiangEntity> CREATOR = new Creator<XunJianXiangEntity>() {
        @Override
        public XunJianXiangEntity createFromParcel(Parcel in) {
            return new XunJianXiangEntity(in);
        }

        @Override
        public XunJianXiangEntity[] newArray(int size) {
            return new XunJianXiangEntity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(data);
        dest.writeInt(dianId);
        dest.writeInt(jiluId);
        dest.writeInt(xunjianxiangId);
        dest.writeString(xunjianxiangName);
        dest.writeInt(xunjianxiangLeixin);
        dest.writeString(xunjianxiangLuojiName);
        dest.writeString(xunjianxiangLuojiName2);
        dest.writeString(xunjianxiangZhengchangzhi);
        dest.writeString(xunjianxiangDanwei);
        dest.writeInt(isReplied);
    }
}
