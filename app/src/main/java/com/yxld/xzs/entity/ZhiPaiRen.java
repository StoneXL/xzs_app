package com.yxld.xzs.entity;

import java.util.List;

/**
 * @author xlei
 * @Date 2017/12/1.
 */

public class ZhiPaiRen extends BaseBack {
    private List<ZhiPaiRen.DataBean> row;

    public List<DataBean> getRow() {
        return row;
    }

    public void setRow(List<DataBean> row) {
        this.row = row;
    }

    public static class DataBean {

        /**
         * adminDepartId : 211
         * adminId : 440
         * adminLevel : 8
         * adminMgr : 0
         * adminNickName : 向磊
         * adminPassWord : e10adc3949ba59abbe56e057f20f883e
         * adminPhone : 15243648097
         * adminState : 4
         * adminUserName : xianglei
         * adminXiangmuId : 346
         * adminXiangmuName : 中远公馆
         * cyId : 1
         * cyName : 湖南创欣物业有限公司
         * departName :
         * mgrName :
         * postId : 0
         * postName :
         * xmArrId :
         */

        private int adminDepartId;
        private int adminId;
        private int adminLevel;
        private int adminMgr;
        private String adminNickName;
        private String adminPassWord;
        private String adminPhone;
        private int adminState;
        private String adminUserName;
        private int adminXiangmuId;
        private String adminXiangmuName;
        private int cyId;
        private String cyName;
        private String departName;
        private String mgrName;
        private int postId;
        private String postName;
        private String xmArrId;

        public int getAdminDepartId() {
            return adminDepartId;
        }

        public void setAdminDepartId(int adminDepartId) {
            this.adminDepartId = adminDepartId;
        }

        public int getAdminId() {
            return adminId;
        }

        public void setAdminId(int adminId) {
            this.adminId = adminId;
        }

        public int getAdminLevel() {
            return adminLevel;
        }

        public void setAdminLevel(int adminLevel) {
            this.adminLevel = adminLevel;
        }

        public int getAdminMgr() {
            return adminMgr;
        }

        public void setAdminMgr(int adminMgr) {
            this.adminMgr = adminMgr;
        }

        public String getAdminNickName() {
            return adminNickName;
        }

        public void setAdminNickName(String adminNickName) {
            this.adminNickName = adminNickName;
        }

        public String getAdminPassWord() {
            return adminPassWord;
        }

        public void setAdminPassWord(String adminPassWord) {
            this.adminPassWord = adminPassWord;
        }

        public String getAdminPhone() {
            return adminPhone;
        }

        public void setAdminPhone(String adminPhone) {
            this.adminPhone = adminPhone;
        }

        public int getAdminState() {
            return adminState;
        }

        public void setAdminState(int adminState) {
            this.adminState = adminState;
        }

        public String getAdminUserName() {
            return adminUserName;
        }

        public void setAdminUserName(String adminUserName) {
            this.adminUserName = adminUserName;
        }

        public int getAdminXiangmuId() {
            return adminXiangmuId;
        }

        public void setAdminXiangmuId(int adminXiangmuId) {
            this.adminXiangmuId = adminXiangmuId;
        }

        public String getAdminXiangmuName() {
            return adminXiangmuName;
        }

        public void setAdminXiangmuName(String adminXiangmuName) {
            this.adminXiangmuName = adminXiangmuName;
        }

        public int getCyId() {
            return cyId;
        }

        public void setCyId(int cyId) {
            this.cyId = cyId;
        }

        public String getCyName() {
            return cyName;
        }

        public void setCyName(String cyName) {
            this.cyName = cyName;
        }

        public String getDepartName() {
            return departName;
        }

        public void setDepartName(String departName) {
            this.departName = departName;
        }

        public String getMgrName() {
            return mgrName;
        }

        public void setMgrName(String mgrName) {
            this.mgrName = mgrName;
        }

        public int getPostId() {
            return postId;
        }

        public void setPostId(int postId) {
            this.postId = postId;
        }

        public String getPostName() {
            return postName;
        }

        public void setPostName(String postName) {
            this.postName = postName;
        }

        public String getXmArrId() {
            return xmArrId;
        }

        public void setXmArrId(String xmArrId) {
            this.xmArrId = xmArrId;
        }
    }
}
