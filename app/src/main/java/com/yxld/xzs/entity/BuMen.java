package com.yxld.xzs.entity;

import java.util.List;

/**
 * @author xlei
 * @Date 2018/5/11.
 */

public class BuMen extends BaseBack {
    private List<BumenBean> rows;

    public List<BumenBean> getRows() {
        return rows;
    }

    public void setRows(List<BumenBean> rows) {
        this.rows = rows;
    }

    public static class BumenBean{

        /**
         * departId : 211
         * departXiangmu : 346
         * departName : 研发部
         * departPost : null
         * departParentid : null
         * departBiezhu1 : null
         * departBiezhu2 : null
         * departIsUse : 0
         * xmName : null
         */

        private int departId;
        private int departXiangmu;
        private String departName;
        private Object departPost;
        private Object departParentid;
        private Object departBiezhu1;
        private Object departBiezhu2;
        private int departIsUse;
        private Object xmName;

        public int getDepartId() {
            return departId;
        }

        public void setDepartId(int departId) {
            this.departId = departId;
        }

        public int getDepartXiangmu() {
            return departXiangmu;
        }

        public void setDepartXiangmu(int departXiangmu) {
            this.departXiangmu = departXiangmu;
        }

        public String getDepartName() {
            return departName;
        }

        public void setDepartName(String departName) {
            this.departName = departName;
        }

        public Object getDepartPost() {
            return departPost;
        }

        public void setDepartPost(Object departPost) {
            this.departPost = departPost;
        }

        public Object getDepartParentid() {
            return departParentid;
        }

        public void setDepartParentid(Object departParentid) {
            this.departParentid = departParentid;
        }

        public Object getDepartBiezhu1() {
            return departBiezhu1;
        }

        public void setDepartBiezhu1(Object departBiezhu1) {
            this.departBiezhu1 = departBiezhu1;
        }

        public Object getDepartBiezhu2() {
            return departBiezhu2;
        }

        public void setDepartBiezhu2(Object departBiezhu2) {
            this.departBiezhu2 = departBiezhu2;
        }

        public int getDepartIsUse() {
            return departIsUse;
        }

        public void setDepartIsUse(int departIsUse) {
            this.departIsUse = departIsUse;
        }

        public Object getXmName() {
            return xmName;
        }

        public void setXmName(Object xmName) {
            this.xmName = xmName;
        }
    }
}
