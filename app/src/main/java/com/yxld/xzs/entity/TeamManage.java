package com.yxld.xzs.entity;

import java.util.List;

/**
 * 作者：Android on 2017/8/7
 * 邮箱：365941593@qq.com
 * 描述：
 */

public class TeamManage extends BaseBack {

    /**
     * data : [{"list":[{"jiluBanzuId":1,"jiluBanzuName":"1号班组","jiluGongsiId":1,"jiluId":2,"jiluJieshuJihuaShijian":1501993800000,"jiluJihuaName":"测试线路","jiluKaishiJihuaShijian":1501992000000,"jiluTijiaoShijian":1501901733000,"jiluTijiaoXungengrenId":73,"jiluWancheng":-1,"jiluXiangmuId":321,"jiluXianluId":1,"jiluXianluName":"凯姆国际1号楼","jiluXunjianXungengrenName":"万文秀"}],"time":1501992000000},{"list":[{"jiluBanzuId":1,"jiluBanzuName":"1号班组","jiluGongsiId":1,"jiluId":3,"jiluJieshuJihuaShijian":1501912800000,"jiluJihuaName":"测试线路","jiluKaishiJihuaShijian":1501911000000,"jiluKaishiShijiShijian":1501934605000,"jiluTijiaoShijian":1501901689000,"jiluTijiaoXungengrenId":73,"jiluWancheng":-1,"jiluXiangmuId":321,"jiluXianluId":1,"jiluXianluName":"凯姆国际1号楼","jiluXunjianXungengrenName":"万文秀"}],"time":1501911000000}]
     * total : 2
     */

    private int total;
    private List<DataBean> data;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * list : [{"jiluBanzuId":1,"jiluBanzuName":"1号班组","jiluGongsiId":1,"jiluId":2,"jiluJieshuJihuaShijian":1501993800000,"jiluJihuaName":"测试线路","jiluKaishiJihuaShijian":1501992000000,"jiluTijiaoShijian":1501901733000,"jiluTijiaoXungengrenId":73,"jiluWancheng":-1,"jiluXiangmuId":321,"jiluXianluId":1,"jiluXianluName":"凯姆国际1号楼","jiluXunjianXungengrenName":"万文秀"}]
         * time : 1501992000000
         */

        private long time;
        private List<ListBean> list;

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * jiluBanzuId : 1
             * jiluBanzuName : 1号班组
             * jiluGongsiId : 1
             * jiluId : 2
             * jiluJieshuJihuaShijian : 1501993800000
             * jiluJihuaName : 测试线路
             * jiluKaishiJihuaShijian : 1501992000000
             * jiluTijiaoShijian : 1501901733000
             * jiluTijiaoXungengrenId : 73
             * jiluWancheng : -1
             * jiluXiangmuId : 321
             * jiluXianluId : 1
             * jiluXianluName : 凯姆国际1号楼
             * jiluXunjianXungengrenName : 万文秀
             */

            private int jiluBanzuId;
            private String jiluBanzuName;
            private int jiluGongsiId;
            private int jiluId;
            private long jiluJieshuJihuaShijian;
            private String jiluJihuaName;
            private long jiluKaishiJihuaShijian;
            private long jiluTijiaoShijian;
            private int jiluTijiaoXungengrenId;
            private int jiluWancheng;
            private int jiluXiangmuId;
            private int jiluXianluId;
            private String jiluXianluName;
            private String jiluXunjianXungengrenName;

            public int getJiluBanzuId() {
                return jiluBanzuId;
            }

            public void setJiluBanzuId(int jiluBanzuId) {
                this.jiluBanzuId = jiluBanzuId;
            }

            public String getJiluBanzuName() {
                return jiluBanzuName;
            }

            public void setJiluBanzuName(String jiluBanzuName) {
                this.jiluBanzuName = jiluBanzuName;
            }

            public int getJiluGongsiId() {
                return jiluGongsiId;
            }

            public void setJiluGongsiId(int jiluGongsiId) {
                this.jiluGongsiId = jiluGongsiId;
            }

            public int getJiluId() {
                return jiluId;
            }

            public void setJiluId(int jiluId) {
                this.jiluId = jiluId;
            }

            public long getJiluJieshuJihuaShijian() {
                return jiluJieshuJihuaShijian;
            }

            public void setJiluJieshuJihuaShijian(long jiluJieshuJihuaShijian) {
                this.jiluJieshuJihuaShijian = jiluJieshuJihuaShijian;
            }

            public String getJiluJihuaName() {
                return jiluJihuaName;
            }

            public void setJiluJihuaName(String jiluJihuaName) {
                this.jiluJihuaName = jiluJihuaName;
            }

            public long getJiluKaishiJihuaShijian() {
                return jiluKaishiJihuaShijian;
            }

            public void setJiluKaishiJihuaShijian(long jiluKaishiJihuaShijian) {
                this.jiluKaishiJihuaShijian = jiluKaishiJihuaShijian;
            }

            public long getJiluTijiaoShijian() {
                return jiluTijiaoShijian;
            }

            public void setJiluTijiaoShijian(long jiluTijiaoShijian) {
                this.jiluTijiaoShijian = jiluTijiaoShijian;
            }

            public int getJiluTijiaoXungengrenId() {
                return jiluTijiaoXungengrenId;
            }

            public void setJiluTijiaoXungengrenId(int jiluTijiaoXungengrenId) {
                this.jiluTijiaoXungengrenId = jiluTijiaoXungengrenId;
            }

            public int getJiluWancheng() {
                return jiluWancheng;
            }

            public void setJiluWancheng(int jiluWancheng) {
                this.jiluWancheng = jiluWancheng;
            }

            public int getJiluXiangmuId() {
                return jiluXiangmuId;
            }

            public void setJiluXiangmuId(int jiluXiangmuId) {
                this.jiluXiangmuId = jiluXiangmuId;
            }

            public int getJiluXianluId() {
                return jiluXianluId;
            }

            public void setJiluXianluId(int jiluXianluId) {
                this.jiluXianluId = jiluXianluId;
            }

            public String getJiluXianluName() {
                return jiluXianluName;
            }

            public void setJiluXianluName(String jiluXianluName) {
                this.jiluXianluName = jiluXianluName;
            }

            public String getJiluXunjianXungengrenName() {
                return jiluXunjianXungengrenName;
            }

            public void setJiluXunjianXungengrenName(String jiluXunjianXungengrenName) {
                this.jiluXunjianXungengrenName = jiluXunjianXungengrenName;
            }
        }
    }
}
