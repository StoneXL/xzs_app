package com.yxld.xzs.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by AI on 2017/7/21.
 */

public class ApplyRepairEntity extends BaseEntity implements Parcelable{
    public String repairNum;
    public String repairTime;
    public String repairPerson;
    public int repairStatus;
    public String address;

    public ApplyRepairEntity() {
    }

    protected ApplyRepairEntity(Parcel in) {
        repairNum = in.readString();
        repairTime = in.readString();
        repairPerson = in.readString();
        repairStatus = in.readInt();
        address = in.readString();
    }

    public static final Creator<ApplyRepairEntity> CREATOR = new Creator<ApplyRepairEntity>() {
        @Override
        public ApplyRepairEntity createFromParcel(Parcel in) {
            return new ApplyRepairEntity(in);
        }

        @Override
        public ApplyRepairEntity[] newArray(int size) {
            return new ApplyRepairEntity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(repairNum);
        dest.writeString(repairTime);
        dest.writeString(repairPerson);
        dest.writeInt(repairStatus);
        dest.writeString(address);
    }
}
