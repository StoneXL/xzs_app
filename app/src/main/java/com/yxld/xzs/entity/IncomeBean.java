package com.yxld.xzs.entity;

/**
 * @author xlei
 * @Date 2017/11/22.
 */

public class IncomeBean extends BaseBack {
    private DataBean rows;

    public DataBean getRows() {
        return rows;
    }

    public void setRows(DataBean rows) {
        this.rows = rows;
    }

    public static class DataBean {

        /**
         * yijiesuanjine : 24.75
         * weijiesuanjine : 13.0
         * zonge : 37.75
         */

        private double yijiesuanjine;
        private double weijiesuanjine;
        private double zonge;

        public double getYijiesuanjine() {
            return yijiesuanjine;
        }

        public void setYijiesuanjine(double yijiesuanjine) {
            this.yijiesuanjine = yijiesuanjine;
        }

        public double getWeijiesuanjine() {
            return weijiesuanjine;
        }

        public void setWeijiesuanjine(double weijiesuanjine) {
            this.weijiesuanjine = weijiesuanjine;
        }

        public double getZonge() {
            return zonge;
        }

        public void setZonge(double zonge) {
            this.zonge = zonge;
        }
    }
}
