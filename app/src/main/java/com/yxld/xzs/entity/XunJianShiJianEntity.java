package com.yxld.xzs.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author Yuan.Y.Q
 * @Date 2017/8/5.
 */

public class XunJianShiJianEntity implements Parcelable,Cloneable{
    public List<XunJianShiJianEntity> data;
    public int dianId;//巡检点id
    public int jiluId; //巡检记录了id
    public String shijianName;
    public int shijianId;
    public int clazzId;
    public int isAnswer; //1是

    public int shijianGongsiId;
    public int shijianXiangmuId;
    public String shijianXiangmuName;
    public int total;

    public XunJianShiJianEntity() {
    }


    protected XunJianShiJianEntity(Parcel in) {
        data = in.createTypedArrayList(XunJianShiJianEntity.CREATOR);
        dianId = in.readInt();
        jiluId = in.readInt();
        shijianName = in.readString();
        shijianId = in.readInt();
        clazzId = in.readInt();
        isAnswer = in.readInt();
        shijianGongsiId = in.readInt();
        shijianXiangmuId = in.readInt();
        shijianXiangmuName = in.readString();
        total = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(data);
        dest.writeInt(dianId);
        dest.writeInt(jiluId);
        dest.writeString(shijianName);
        dest.writeInt(shijianId);
        dest.writeInt(clazzId);
        dest.writeInt(isAnswer);
        dest.writeInt(shijianGongsiId);
        dest.writeInt(shijianXiangmuId);
        dest.writeString(shijianXiangmuName);
        dest.writeInt(total);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<XunJianShiJianEntity> CREATOR = new Creator<XunJianShiJianEntity>() {
        @Override
        public XunJianShiJianEntity createFromParcel(Parcel in) {
            return new XunJianShiJianEntity(in);
        }

        @Override
        public XunJianShiJianEntity[] newArray(int size) {
            return new XunJianShiJianEntity[size];
        }
    };

    @Override
    public XunJianShiJianEntity clone() throws CloneNotSupportedException {
        return (XunJianShiJianEntity) super.clone();
    }

}
