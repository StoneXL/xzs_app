package com.yxld.xzs.entity;

import java.util.List;

/**
 * @author xlei
 * @Date 2018/5/11.
 */

public class FuZe extends BaseBack {
    private List<FuZeBean> rows;

    public List<FuZeBean> getRows() {
        return rows;
    }

    public void setRows(List<FuZeBean> rows) {
        this.rows = rows;
    }

    public static class FuZeBean {

        /**
         * searchFileds : ["adminNickName","adminUserName"]
         * adminId : 96
         * adminUserName : xiangmu_jingli
         * adminPassWord : 670b14728ad9902aecba32e22fa4f6bd
         * adminLevel : 15
         * adminXiangmuId : 346
         * adminXiangmuName : 中远公馆
         * adminNickName : 中远项目经理
         * adminPhone : 13875231886
         * adminState : 4
         * adminDepartId : 211
         * adminMgr : 0
         * cyId : 1
         * cyName : 湖南创欣物业有限公司
         * xmArrId : null
         * departName : null
         * mgrName : null
         * postId : null
         * postName : null
         */

        private int adminId;
        private String adminUserName;
        private String adminPassWord;
        private int adminLevel;
        private int adminXiangmuId;
        private String adminXiangmuName;
        private String adminNickName;
        private String adminPhone;
        private int adminState;
        private int adminDepartId;
        private int adminMgr;
        private int cyId;
        private String cyName;
        private Object xmArrId;
        private Object departName;
        private Object mgrName;
        private Object postId;
        private Object postName;
        private List<String> searchFileds;

        public int getAdminId() {
            return adminId;
        }

        public void setAdminId(int adminId) {
            this.adminId = adminId;
        }

        public String getAdminUserName() {
            return adminUserName;
        }

        public void setAdminUserName(String adminUserName) {
            this.adminUserName = adminUserName;
        }

        public String getAdminPassWord() {
            return adminPassWord;
        }

        public void setAdminPassWord(String adminPassWord) {
            this.adminPassWord = adminPassWord;
        }

        public int getAdminLevel() {
            return adminLevel;
        }

        public void setAdminLevel(int adminLevel) {
            this.adminLevel = adminLevel;
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

        public String getAdminNickName() {
            return adminNickName;
        }

        public void setAdminNickName(String adminNickName) {
            this.adminNickName = adminNickName;
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

        public int getAdminDepartId() {
            return adminDepartId;
        }

        public void setAdminDepartId(int adminDepartId) {
            this.adminDepartId = adminDepartId;
        }

        public int getAdminMgr() {
            return adminMgr;
        }

        public void setAdminMgr(int adminMgr) {
            this.adminMgr = adminMgr;
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

        public Object getXmArrId() {
            return xmArrId;
        }

        public void setXmArrId(Object xmArrId) {
            this.xmArrId = xmArrId;
        }

        public Object getDepartName() {
            return departName;
        }

        public void setDepartName(Object departName) {
            this.departName = departName;
        }

        public Object getMgrName() {
            return mgrName;
        }

        public void setMgrName(Object mgrName) {
            this.mgrName = mgrName;
        }

        public Object getPostId() {
            return postId;
        }

        public void setPostId(Object postId) {
            this.postId = postId;
        }

        public Object getPostName() {
            return postName;
        }

        public void setPostName(Object postName) {
            this.postName = postName;
        }

        public List<String> getSearchFileds() {
            return searchFileds;
        }

        public void setSearchFileds(List<String> searchFileds) {
            this.searchFileds = searchFileds;
        }
    }
}
