package com.yxld.xzs.entity;

import java.util.List;

/**
 * @author xlei
 * @Date 2017/11/22.
 */

public class YiJieSuanBean extends BaseBack {
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
         * dingdanBianhao : 003401ps1509620050890
         * shouhuodizhi : 中远公馆1栋1单元301
         * shouhuoshijian : 2017-11-02 17:08:15
         * jiesuanshijian : 2017-11-06 16:58:39
         * jiesuanjine : 2.56
         */

        private String dingdanBianhao;
        private String shouhuodizhi;
        private String shouhuoshijian;
        private String jiesuanshijian;
        private double jiesuanjine;

        public String getDingdanBianhao() {
            return dingdanBianhao;
        }

        public void setDingdanBianhao(String dingdanBianhao) {
            this.dingdanBianhao = dingdanBianhao;
        }

        public String getShouhuodizhi() {
            return shouhuodizhi;
        }

        public void setShouhuodizhi(String shouhuodizhi) {
            this.shouhuodizhi = shouhuodizhi;
        }

        public String getShouhuoshijian() {
            return shouhuoshijian;
        }

        public void setShouhuoshijian(String shouhuoshijian) {
            this.shouhuoshijian = shouhuoshijian;
        }

        public String getJiesuanshijian() {
            return jiesuanshijian;
        }

        public void setJiesuanshijian(String jiesuanshijian) {
            this.jiesuanshijian = jiesuanshijian;
        }

        public double getJiesuanjine() {
            return jiesuanjine;
        }

        public void setJiesuanjine(double jiesuanjine) {
            this.jiesuanjine = jiesuanjine;
        }
    }
}
