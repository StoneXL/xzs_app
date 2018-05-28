package com.yxld.xzs.entity;

import java.util.List;

/**
 * 作者：Android on 2017/8/7
 * 邮箱：365941593@qq.com
 * 描述：
 */

public class TeamMember extends BaseBack {

    /**
     * data : [{"renyuanId":9,"renyuanBanzuId":1,"renyuanAdminId":4,"renyuanZuzhang":-1,"renyuanName":"人员4"},{"renyuanId":22,"renyuanBanzuId":1,"renyuanAdminId":73,"renyuanZuzhang":1,"renyuanName":"万文秀"}]
     * total : 2
     * error : null
     */

    private int total;
    private List<DataBean> data;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * renyuanId : 9
         * renyuanBanzuId : 1
         * renyuanAdminId : 4
         * renyuanZuzhang : -1
         * renyuanName : 人员4
         */

        private int renyuanId;
        private int renyuanBanzuId;
        private int renyuanAdminId;
        private int renyuanZuzhang;
        private String renyuanName;

        public int getRenyuanId() {
            return renyuanId;
        }

        public void setRenyuanId(int renyuanId) {
            this.renyuanId = renyuanId;
        }

        public int getRenyuanBanzuId() {
            return renyuanBanzuId;
        }

        public void setRenyuanBanzuId(int renyuanBanzuId) {
            this.renyuanBanzuId = renyuanBanzuId;
        }

        public int getRenyuanAdminId() {
            return renyuanAdminId;
        }

        public void setRenyuanAdminId(int renyuanAdminId) {
            this.renyuanAdminId = renyuanAdminId;
        }

        public int getRenyuanZuzhang() {
            return renyuanZuzhang;
        }

        public void setRenyuanZuzhang(int renyuanZuzhang) {
            this.renyuanZuzhang = renyuanZuzhang;
        }

        public String getRenyuanName() {
            return renyuanName;
        }

        public void setRenyuanName(String renyuanName) {
            this.renyuanName = renyuanName;
        }
    }
}
