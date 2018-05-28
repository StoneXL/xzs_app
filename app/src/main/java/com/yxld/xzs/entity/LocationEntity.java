package com.yxld.xzs.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author: Yuan.Y.Q
 * @date: 2017/7/19
 * @descprition:
 */

public class LocationEntity {
    public double latitude;
    public double longitude;
    public int jiluId;


    public LocationEntity(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
