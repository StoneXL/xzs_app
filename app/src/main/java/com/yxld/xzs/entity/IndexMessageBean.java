package com.yxld.xzs.entity;

import java.util.List;

/**
 * @author xlei
 * @Date 2017/11/23.
 */

public class IndexMessageBean extends BaseBack {
    private int total;
    private List<DataBean> rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<DataBean> getRows() {
        return rows;
    }

    public void setRows(List<DataBean> rows) {
        this.rows = rows;
    }

    public static class DataBean {

        /**
         * total : 1
         * bianhao : 003401ps1511336084533
         * fukuanShijian : 2017-11-22 15:34:59
         * shouhuoDizhi : 中远公馆1栋1单元301
         * type : 1
         */

        private int total;
        private String bianhao;
        private String fukuanShijian;
        private String shouhuoDizhi;
        private int type;
        private String paidanShijian;

        public String getPaidanShijian() {
            return paidanShijian;
        }

        public void setPaidanShijian(String paidanShijian) {
            this.paidanShijian = paidanShijian;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public String getBianhao() {
            return bianhao;
        }

        public void setBianhao(String bianhao) {
            this.bianhao = bianhao;
        }

        public String getFukuanShijian() {
            return fukuanShijian;
        }

        public void setFukuanShijian(String fukuanShijian) {
            this.fukuanShijian = fukuanShijian;
        }

        public String getShouhuoDizhi() {
            return shouhuoDizhi;
        }

        public void setShouhuoDizhi(String shouhuoDizhi) {
            this.shouhuoDizhi = shouhuoDizhi;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
