package com.yxld.xzs.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by AI on 2017/7/21.
 */

public class MainMenuEntity implements Parcelable{
    public String title;
    public int iconResId;

    public MainMenuEntity(String title, int iconResId) {
        this.title = title;
        this.iconResId = iconResId;
    }

    protected MainMenuEntity(Parcel in) {
        title = in.readString();
        iconResId = in.readInt();
    }

    public static final Creator<MainMenuEntity> CREATOR = new Creator<MainMenuEntity>() {
        @Override
        public MainMenuEntity createFromParcel(Parcel in) {
            return new MainMenuEntity(in);
        }

        @Override
        public MainMenuEntity[] newArray(int size) {
            return new MainMenuEntity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeInt(iconResId);
    }
}
