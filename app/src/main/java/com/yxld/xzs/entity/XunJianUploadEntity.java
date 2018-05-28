package com.yxld.xzs.entity;

import java.util.List;

/**
 * @author Yuan.Y.Q
 * @Date 2017/8/8.
 */

public class XunJianUploadEntity {

    /**
     * jiluWenti : 1
     * jiluId : 1
     * jiluJieshuShijiShijian : 2017-08-08 12:30:02
     * dianxiang : [{"xiangqingJiluId":"1","xiangqingBianma":"bianma222","xiangqingDidian":"dizhi","xiangqingShuakaName":"万文秀","xiangqingDakaChenggong":"1","xiangqingDakaShijian":"2017-08-08 12:30:02","xiangqingBuchong":"补充","jieguo":[{"jieguoXungenxiangName":"天花板是否漏水","jieguoXungenJieguoName":"漏水","jieguoYichang":"1"},{"jieguoXungenxiangName":"有电","jieguoXungenJieguoName":"没电","jieguoYichang":"2"},{"jieguoXungenxiangName":"垃圾有吗","jieguoXungenJieguoName":"有","jieguoYichang":"3"}]},{"xiangqingJiluId":"1","xiangqingBianma":"bianma3333","xiangqingDidian":"dizhi333","xiangqingShuakaName":"万文秀","xiangqingDakaChenggong":"1","xiangqingDakaShijian":"2017-08-08 12:30:02","xiangqingBuchong":"补充","jieguo":[{"jieguoXungenxiangName":"天花板是否漏水","jieguoXungenJieguoName":"漏水","jieguoYichang":"1"},{"jieguoXungenxiangName":"有电","jieguoXungenJieguoName":"没电","jieguoYichang":"2"}]}]
     */

    public String jiluWenti;
    public String jiluId;
    public String jiluJieshuShijiShijian;
    public List<DianxiangBean> dianxiang;


    public static class DianxiangBean {
        /**
         * xiangqingJiluId : 1
         * xiangqingBianma : bianma222
         * xiangqingDidian : dizhi
         * xiangqingShuakaName : 万文秀
         * xiangqingDakaChenggong : 1
         * xiangqingDakaShijian : 2017-08-08 12:30:02
         * xiangqingBuchong : 补充
         * jieguo : [{"jieguoXungenxiangName":"天花板是否漏水","jieguoXungenJieguoName":"漏水","jieguoYichang":"1"},{"jieguoXungenxiangName":"有电","jieguoXungenJieguoName":"没电","jieguoYichang":"2"},{"jieguoXungenxiangName":"垃圾有吗","jieguoXungenJieguoName":"有","jieguoYichang":"3"}]
         */

        public String xiangqingJiluId;
        public String xiangqingBianma;
        public String xiangqingDidian;
        public String xiangqingShuakaName;
        public String xiangqingDakaChenggong;
        public String xiangqingDakaShijian;
        public String xiangqingBuchong;
        public String xiangqingDianId;
        public String xiangqingYuyin;
        public String xiangqingTupian;
        public List<JieguoBean> jieguo;


        public static class JieguoBean {
            /**
             * jieguoXungenxiangName : 天花板是否漏水
             * jieguoXungenJieguoName : 漏水
             * jieguoYichang : 1
             */

            public String jieguoXungenxiangName;
            public String jieguoXungenJieguoName;
            public String jieguoYichang;
            public String jieguoType; //1为巡检项 2为事件
            public String jieguoXiangId; //巡检项id或者事件id
        }
    }
}
