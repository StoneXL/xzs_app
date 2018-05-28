package com.yxld.xzs.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yuan.Y.Q
 * @Date 2017/8/15.
 */

public class XunJianShijianClassifyEntity extends BaseBack implements Parcelable,Cloneable{
    public String clazz;
    public int clazzId;
    public int jiluId;
    public int dianId;
    public List<XunJianShiJianEntity> list;
    public int isSelected;

    public List<XunJianShijianClassifyEntity> data;

    public XunJianShijianClassifyEntity(String clazz, int clazzId, int jiluId, int dianId, List<XunJianShiJianEntity> list, int isSelected, List<XunJianShijianClassifyEntity> data) {
        this.clazz = clazz;
        this.clazzId = clazzId;
        this.jiluId = jiluId;
        this.dianId = dianId;
        this.list = list;
        this.isSelected = isSelected;
        this.data = data;
    }


    public XunJianShijianClassifyEntity(XunJianShijianClassifyEntity entity) {
        clazz = entity.clazz;
        clazzId = entity.clazzId;
        jiluId = entity.jiluId;
        dianId = entity.dianId;
        isSelected = 0;
        list = new ArrayList<>();
        for (XunJianShiJianEntity shiJianEntity : entity.list){
            try {
                list.add(shiJianEntity.clone());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
    }

    protected XunJianShijianClassifyEntity(Parcel in) {
        clazz = in.readString();
        clazzId = in.readInt();
        jiluId = in.readInt();
        dianId = in.readInt();
        list = in.createTypedArrayList(XunJianShiJianEntity.CREATOR);
        isSelected = in.readInt();
        data = in.createTypedArrayList(XunJianShijianClassifyEntity.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(clazz);
        dest.writeInt(clazzId);
        dest.writeInt(jiluId);
        dest.writeInt(dianId);
        dest.writeTypedList(list);
        dest.writeInt(isSelected);
        dest.writeTypedList(data);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<XunJianShijianClassifyEntity> CREATOR = new Creator<XunJianShijianClassifyEntity>() {
        @Override
        public XunJianShijianClassifyEntity createFromParcel(Parcel in) {
            return new XunJianShijianClassifyEntity(in);
        }

        @Override
        public XunJianShijianClassifyEntity[] newArray(int size) {
            return new XunJianShijianClassifyEntity[size];
        }
    };

    @Override
    public XunJianShijianClassifyEntity clone() throws CloneNotSupportedException {
        XunJianShijianClassifyEntity entity = (XunJianShijianClassifyEntity) super.clone();
        //深克隆
//        List<XunJianShiJianEntity> shiJianEntities = new ArrayList<>();
//        for (XunJianShiJianEntity shiJianEntity : list){
//            shiJianEntities.add(shiJianEntity.clone());
//        }
//        entity.list = shiJianEntities;
        return entity;
    }
}
