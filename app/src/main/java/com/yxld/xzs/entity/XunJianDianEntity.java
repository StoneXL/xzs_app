package com.yxld.xzs.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yuan.Y.Q
 * @Date 2017/8/5.
 */

public class XunJianDianEntity extends BaseBack implements Parcelable{

    public List<XunJianDianEntity> data;

    public String dianDizhi;//地址
    public int dianGongsiId;
    public int dianXiangmuId;
    public String dianNfcBianma;
    public int dianId;
    public int jiluId;
    public String dianJingweiduZuobiao; //坐标
    public int xuliehao;

    public String jiluXianluName;//线路名称

    public String latitude;
    public String longitude;
    public List<XunJianXiangEntity> xunJianXiangDatas;
    public List<XunJianShijianClassifyEntity> xunJianShijianClassifies;
    public String remark;
    public String remarkImgsUrls; //使用,分隔
    public String remarkRecoderUrl;
    public List<ImageItem> remarkImgsUrlTemp = new ArrayList<>();

    public int isException;//是否异常 -1为异常
    public int hasChecked; //是否已经打卡
    public int hasSaveData;
    public String checkTime;

    public XunJianDianEntity() {
    }

    protected XunJianDianEntity(Parcel in) {
        data = in.createTypedArrayList(XunJianDianEntity.CREATOR);
        dianDizhi = in.readString();
        dianGongsiId = in.readInt();
        dianXiangmuId = in.readInt();
        dianNfcBianma = in.readString();
        dianId = in.readInt();
        jiluId = in.readInt();
        dianJingweiduZuobiao = in.readString();
        xuliehao = in.readInt();
        jiluXianluName = in.readString();
        latitude = in.readString();
        longitude = in.readString();
        xunJianXiangDatas = in.createTypedArrayList(XunJianXiangEntity.CREATOR);
        xunJianShijianClassifies = in.createTypedArrayList(XunJianShijianClassifyEntity.CREATOR);
        remark = in.readString();
        remarkImgsUrls = in.readString();
        remarkRecoderUrl = in.readString();
        isException = in.readInt();
        hasChecked = in.readInt();
        hasSaveData = in.readInt();
        checkTime = in.readString();
    }

    public static final Creator<XunJianDianEntity> CREATOR = new Creator<XunJianDianEntity>() {
        @Override
        public XunJianDianEntity createFromParcel(Parcel in) {
            return new XunJianDianEntity(in);
        }

        @Override
        public XunJianDianEntity[] newArray(int size) {
            return new XunJianDianEntity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(data);
        dest.writeString(dianDizhi);
        dest.writeInt(dianGongsiId);
        dest.writeInt(dianXiangmuId);
        dest.writeString(dianNfcBianma);
        dest.writeInt(dianId);
        dest.writeInt(jiluId);
        dest.writeString(dianJingweiduZuobiao);
        dest.writeInt(xuliehao);
        dest.writeString(jiluXianluName);
        dest.writeString(latitude);
        dest.writeString(longitude);
        dest.writeTypedList(xunJianXiangDatas);
        dest.writeTypedList(xunJianShijianClassifies);
        dest.writeString(remark);
        dest.writeString(remarkImgsUrls);
        dest.writeString(remarkRecoderUrl);
        dest.writeInt(isException);
        dest.writeInt(hasChecked);
        dest.writeInt(hasSaveData);
        dest.writeString(checkTime);
    }
}
