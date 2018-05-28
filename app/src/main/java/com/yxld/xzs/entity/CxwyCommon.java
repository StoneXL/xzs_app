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
 * Created by 89876 on 2017/5/13 0013.
 * 个人主页：http://yishangfei.me
 * Github:https://github.com/yishangfei
 * <p>
 */
public class CxwyCommon extends BaseBack{
    /**
     * msg : 请求成功
     * code : 0
     * data : [{"cvoList":[{"shebeixuliehao":"744365348","sxtid":46,"tongdaohao":2,"tongdaoname":"摄像头二号"},{"shebeixuliehao":"744365348","sxtid":49,"tongdaohao":1,"tongdaoname":"摄像头test1"}],"shebeiName":"二号录像机","shebeiid":21,"shebeixuliehao":"744365348"},{"cvoList":[{"shebeixuliehao":"744365349","sxtid":47,"tongdaohao":1,"tongdaoname":"摄像机一号"},{"shebeixuliehao":"744365349","sxtid":48,"tongdaohao":1,"tongdaoname":"摄像头test2"},{"shebeixuliehao":"744365349","sxtid":50,"tongdaohao":11,"tongdaoname":"啊啊wq"}],"shebeiName":"一号录像机11","shebeiid":27,"shebeixuliehao":"744365349"}]
     */

    private int code;

    public List<CxwyCommon> getData() {
        return data;
    }

    public void setData(List<CxwyCommon> data) {
        this.data = data;
    }

    private List<CxwyCommon> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    /**
     * cvoList : [{"shebeixuliehao":"744365348","sxtid":46,"tongdaohao":2,"tongdaoname":"摄像头二号"},{"shebeixuliehao":"744365348","sxtid":49,"tongdaohao":1,"tongdaoname":"摄像头test1"}]
     * shebeiName : 二号录像机
     * shebeiid : 21
     * shebeixuliehao : 744365348
     */

    private String shebeiName;
    private int shebeiid;
    private String shebeixuliehao;
    private List<CvoListBean> cvoList;

    public String getShebeiName() {
        return shebeiName;
    }

    public void setShebeiName(String shebeiName) {
        this.shebeiName = shebeiName;
    }

    public int getShebeiid() {
        return shebeiid;
    }

    public void setShebeiid(int shebeiid) {
        this.shebeiid = shebeiid;
    }

    public String getShebeixuliehao() {
        return shebeixuliehao;
    }

    public void setShebeixuliehao(String shebeixuliehao) {
        this.shebeixuliehao = shebeixuliehao;
    }

    public List<CvoListBean> getCvoList() {
        return cvoList;
    }

    public void setCvoList(List<CvoListBean> cvoList) {
        this.cvoList = cvoList;
    }

    public static class CvoListBean {
        /**
         * shebeixuliehao : 744365348
         * sxtid : 46
         * tongdaohao : 2
         * tongdaoname : 摄像头二号
         */

        private String shebeixuliehao;
        private int sxtid;
        private int tongdaohao;
        private String tongdaoname;

        public String getShebeixuliehao() {
            return shebeixuliehao;
        }

        public void setShebeixuliehao(String shebeixuliehao) {
            this.shebeixuliehao = shebeixuliehao;
        }

        public int getSxtid() {
            return sxtid;
        }

        public void setSxtid(int sxtid) {
            this.sxtid = sxtid;
        }

        public int getTongdaohao() {
            return tongdaohao;
        }

        public void setTongdaohao(int tongdaohao) {
            this.tongdaohao = tongdaohao;
        }

        public String getTongdaoname() {
            return tongdaoname;
        }

        public void setTongdaoname(String tongdaoname) {
            this.tongdaoname = tongdaoname;
        }
    }
}
