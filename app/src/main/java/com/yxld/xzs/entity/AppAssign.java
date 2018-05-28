package com.yxld.xzs.entity;
////////////////////////////////////////////////////////////////////
//                          _ooOoo_                               //
//                         o8888888o                              //
//                         88" . "88                              //
//                         (| ^_^ |)                              //
//                         O\  =  /O                              //
//                      ____/`---'\____                           //
//                    .'  \\|     |//  `.                         //
//                   /  \\|||  :  |||//  \                        //
//                  /  _||||| -:- |||||-  \                       //
//                  |   | \\\  -  /// |   |                       //
//                  | \_|  ''\---/''  |   |                       //
//                  \  .-\__  `-`  ___/-. /                       //
//                ___`. .'  /--.--\  `. . ___                     //
//              ."" '<  `.___\_<|>_/___.'  >'"".                  //
//            | | :  `- \`.;`\ _ /`;.`/ - ` : | |                 //
//            \  \ `-.   \_ __\ /__ _/   .-` /  /                 //
//      ========`-.____`-.___\_____/___.-`____.-'========         //
//                           `=---='                              //
//      ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^        //
//         佛祖保佑       永无BUG     永不修改                  //
////////////////////////////////////////////////////////////////////

import java.util.List;

/**
 * Created by yishangfei on 2017/3/10 0010.
 * 个人主页：http://yishangfei.me
 * Github:https://github.com/yishangfei
 */
public class AppAssign {
        /**
         * adminDepartId : 204
         * adminId : 161
         * adminLevel : 22
         * adminMgr : 212
         * adminNickName : xiaonan
         * adminPassWord : e10adc3949ba59abbe56e057f20f883e
         * adminState : 4
         * adminUserName : xiaonan
         * adminXiangmuId : 350
         * adminXiangmuName : 云顶翠峰
         * cyId : 1
         * cyName : 湖南创欣物业有限公司
         * departName :
         * mgrName :
         * xmArrId :
         */

        private int adminDepartId;
        private int adminId;
        private int adminLevel;
        private int adminMgr;
        private String adminNickName;
        private String adminPassWord;
        private int adminState;
        private String adminUserName;
        private int adminXiangmuId;
        private String adminXiangmuName;
        private int cyId;
        private String cyName;
        private String departName;
        private String mgrName;
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

        public String getXmArrId() {
            return xmArrId;
        }

        public void setXmArrId(String xmArrId) {
            this.xmArrId = xmArrId;
        }

    @Override
    public String toString() {
        return "AppAssign{" +
                "adminDepartId=" + adminDepartId +
                ", adminId=" + adminId +
                ", adminLevel=" + adminLevel +
                ", adminMgr=" + adminMgr +
                ", adminNickName='" + adminNickName + '\'' +
                ", adminPassWord='" + adminPassWord + '\'' +
                ", adminState=" + adminState +
                ", adminUserName='" + adminUserName + '\'' +
                ", adminXiangmuId=" + adminXiangmuId +
                ", adminXiangmuName='" + adminXiangmuName + '\'' +
                ", cyId=" + cyId +
                ", cyName='" + cyName + '\'' +
                ", departName='" + departName + '\'' +
                ", mgrName='" + mgrName + '\'' +
                ", xmArrId='" + xmArrId + '\'' +
                '}';
    }
}
