package com.yxld.xzs.entity;


import java.util.List;

/**
 * Created by yishangfei on 2017/2/22 0022.
 * 邮箱：yishangfei@foxmail.com
 */
public class AppCameraList extends BaseEntity {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * equipCompanyId : 1
         * equipCompanyName : 湖南创欣物业有限公司
         * equipCreatetime : 1502763251000
         * equipId : 70
         * equipName : 测试
         * equipPassword : 123
         * equipSerial : 6337533
         * equipXiangmuId : 321
         * equipXiangmuName : 凯姆国际
         */

        private int equipCompanyId;
        private String equipCompanyName;
        private long equipCreatetime;
        private int equipId;
        private String equipName;
        private String equipPassword;
        private int equipSerial;
        private int equipXiangmuId;
        private String equipXiangmuName;
        private int bufangStatus;

        public int getBufangStatus() {
            return bufangStatus;
        }

        public void setBufangStatus(int bufangStatus) {
            this.bufangStatus = bufangStatus;
        }

        public int getEquipStatus() {
            return equipStatus;
        }

        public void setEquipStatus(int equipStatus) {
            this.equipStatus = equipStatus;
        }

        public int getXiangmuName() {
            return xiangmuName;
        }

        public void setXiangmuName(int xiangmuName) {
            this.xiangmuName = xiangmuName;
        }

        private int equipStatus;
        private int xiangmuName;
        public int getEquipCompanyId() {
            return equipCompanyId;
        }

        public void setEquipCompanyId(int equipCompanyId) {
            this.equipCompanyId = equipCompanyId;
        }

        public String getEquipCompanyName() {
            return equipCompanyName;
        }

        public void setEquipCompanyName(String equipCompanyName) {
            this.equipCompanyName = equipCompanyName;
        }

        public long getEquipCreatetime() {
            return equipCreatetime;
        }

        public void setEquipCreatetime(long equipCreatetime) {
            this.equipCreatetime = equipCreatetime;
        }

        public int getEquipId() {
            return equipId;
        }

        public void setEquipId(int equipId) {
            this.equipId = equipId;
        }

        public String getEquipName() {
            return equipName;
        }

        public void setEquipName(String equipName) {
            this.equipName = equipName;
        }

        public String getEquipPassword() {
            return equipPassword;
        }

        public void setEquipPassword(String equipPassword) {
            this.equipPassword = equipPassword;
        }

        public int getEquipSerial() {
            return equipSerial;
        }

        public void setEquipSerial(int equipSerial) {
            this.equipSerial = equipSerial;
        }

        public int getEquipXiangmuId() {
            return equipXiangmuId;
        }

        public void setEquipXiangmuId(int equipXiangmuId) {
            this.equipXiangmuId = equipXiangmuId;
        }

        public String getEquipXiangmuName() {
            return equipXiangmuName;
        }

        public void setEquipXiangmuName(String equipXiangmuName) {
            this.equipXiangmuName = equipXiangmuName;
        }
    }
}
