package com.yxld.xzs.entity;

import java.util.List;

/**
 * 作者：Android on 2017/8/14
 * 邮箱：365941593@qq.com
 * 描述：
 */

public class XiangMu extends BaseBack {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }
    public static class DataBean {
        public int getXiangmuId() {
            return xiangmuId;
        }

        public void setXiangmuId(int xiangmuId) {
            this.xiangmuId = xiangmuId;
        }

        public String getXiangmuName() {
            return xiangmuName;
        }

        public void setXiangmuName(String xiangmuName) {
            this.xiangmuName = xiangmuName;
        }

        /**

         * xiangmuId : 1
         * xiangmuName : 创欣物业总部
         */

        private int xiangmuId;
        private String xiangmuName;


    }
}
