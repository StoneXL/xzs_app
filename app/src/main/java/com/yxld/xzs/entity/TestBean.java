package com.yxld.xzs.entity;

/**
 * @author xlei
 * @Date 2017/11/14.
 */

public class TestBean  {
    private int imgid;
    private String time;
    private String zhangtai;
    private String address;

    public TestBean(int imgid, String time, String zhangtai, String address) {
        this.imgid = imgid;
        this.time = time;
        this.zhangtai = zhangtai;
        this.address = address;
    }

    public int getImgid() {
        return imgid;
    }

    public void setImgid(int imgid) {
        this.imgid = imgid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getZhangtai() {
        return zhangtai;
    }

    public void setZhangtai(String zhangtai) {
        this.zhangtai = zhangtai;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
