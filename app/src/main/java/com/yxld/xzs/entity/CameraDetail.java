package com.yxld.xzs.entity;

import java.util.List;

/**
 * @author xlei
 * @Date 2018/1/10.
 */

public class CameraDetail extends BaseBack {
    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class  DataBean {

        /**
         * sb_id : 51
         * sb_name : eq_6338764
         * sb_zhanghao :
         * sb_ipc_id : 6338764
         * sb_ipc_pwd : 123
         * sb_jointime : 1489475109000
         * jiashu : null
         * gongsiId : 1
         * xiangmuId : 321
         * loudong : 1
         * danyuan : 2
         * fanghao : 1
         * phone : 666666
         * xiangmu : 凯姆国际
         */

        private int sb_id;
        private String sb_name;
        private String sb_zhanghao;
        private String sb_ipc_id;
        private String sb_ipc_pwd;
        private long sb_jointime;
        private Object jiashu;
        private int gongsiId;
        private int xiangmuId;
        private String loudong;
        private String danyuan;
        private String fanghao;
        private String phone;
        private String xiangmu;

        public int getSb_id() {
            return sb_id;
        }

        public void setSb_id(int sb_id) {
            this.sb_id = sb_id;
        }

        public String getSb_name() {
            return sb_name;
        }

        public void setSb_name(String sb_name) {
            this.sb_name = sb_name;
        }

        public String getSb_zhanghao() {
            return sb_zhanghao;
        }

        public void setSb_zhanghao(String sb_zhanghao) {
            this.sb_zhanghao = sb_zhanghao;
        }

        public String getSb_ipc_id() {
            return sb_ipc_id;
        }

        public void setSb_ipc_id(String sb_ipc_id) {
            this.sb_ipc_id = sb_ipc_id;
        }

        public String getSb_ipc_pwd() {
            return sb_ipc_pwd;
        }

        public void setSb_ipc_pwd(String sb_ipc_pwd) {
            this.sb_ipc_pwd = sb_ipc_pwd;
        }

        public long getSb_jointime() {
            return sb_jointime;
        }

        public void setSb_jointime(long sb_jointime) {
            this.sb_jointime = sb_jointime;
        }

        public Object getJiashu() {
            return jiashu;
        }

        public void setJiashu(Object jiashu) {
            this.jiashu = jiashu;
        }

        public int getGongsiId() {
            return gongsiId;
        }

        public void setGongsiId(int gongsiId) {
            this.gongsiId = gongsiId;
        }

        public int getXiangmuId() {
            return xiangmuId;
        }

        public void setXiangmuId(int xiangmuId) {
            this.xiangmuId = xiangmuId;
        }

        public String getLoudong() {
            return loudong;
        }

        public void setLoudong(String loudong) {
            this.loudong = loudong;
        }

        public String getDanyuan() {
            return danyuan;
        }

        public void setDanyuan(String danyuan) {
            this.danyuan = danyuan;
        }

        public String getFanghao() {
            return fanghao;
        }

        public void setFanghao(String fanghao) {
            this.fanghao = fanghao;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getXiangmu() {
            return xiangmu;
        }

        public void setXiangmu(String xiangmu) {
            this.xiangmu = xiangmu;
        }
    }
}
