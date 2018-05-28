package com.yxld.xzs.entity;

public class BaseEntity{
    public int status;
    public String MSG;
    public boolean success;
    public String msg;
    public String error;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMSG() {
        return MSG;
    }

    public void setMSG(String MSG) {
        this.MSG = MSG;
    }
}
