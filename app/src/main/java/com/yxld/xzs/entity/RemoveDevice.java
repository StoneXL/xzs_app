package com.yxld.xzs.entity;

/**
 * 作者：Android on 2017/8/3
 * 邮箱：365941593@qq.com
 * 描述：
 */

public class RemoveDevice {

    /**
     * msg : 删除设备成功
     * success : true
     * status : 0
     */

    private String msg;
    private boolean success;
    private int status;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
