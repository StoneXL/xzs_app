package com.yxld.xzs.entity;

/**
 * wwx手机开门获取二维码构建对象
 */
public class AppCode {

    private String code; //二维码
    private String timr; //时间

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTimr() {
        return timr;
    }

    public void setTimr(String timr) {
        this.timr = timr;
    }

    @Override
    public String toString() {
        return "OpenDoorCode{" +
                "code='" + code + '\'' +
                ", timr='" + timr + '\'' +
                '}';
    }
}
