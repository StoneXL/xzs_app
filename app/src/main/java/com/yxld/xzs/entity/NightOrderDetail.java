package com.yxld.xzs.entity;

import java.util.List;

/**
 * Created by William on 2017/11/29.
 */

public class NightOrderDetail extends BaseBack {

    /**
     * order : {"id":591,"xiangmuId":346,"xiangmuMing":null,"gongsiId":1,"gongsiMing":null,
     * "bianhao":"003419ps1511860184680","zhuangtai":3,"shifouYejian":1,"shouhuorenMing":"王晓燕",
     * "shouhuoDizhi":"中远公馆3栋3单元333","shouhuoDianhua":"18711001698","zongjine":50,"dianziquan":0,
     * "shijiJine":50,"payjiaoyihao":null,"fukuanFangshi":null,"peisongFangshi":1,
     * "anpaiPeisong":-1,"jiedanMoshi":1,"paidanren":239,"paidanrenMing":null,"peisongrenId":458,
     * "peisongrenMing":"晓晓","peisongrenTel":null,"peisongfei":1,"peisongfeiLaiyuan":2,
     * "tuikuanfangshi":null,"tuikuanJine":null,"tuikuanYuanyin":null,"xiadanShijian":"2017-11-28
     * 17:09:44.0","fukuanShijian":"2017-11-28 17:09:44.0","paisongShijian":"2017-11-29
     * 09:13:24","quhuoShijian":"2017-11-29 09:41:51","shouhuoShijian":null,
     * "pingjiaShijian":null,"tuikuanShenqingShijian":null,"tuihuanShijian":null,
     * "tuikuanYunxuShijian":null,"quxiaoShijian":null,"quxiaoYuanyin":null,
     * "wanchengShijian":null,"tuikuanpicihao":null,"tuikuanbiaozhi":null,"yezhuId":3419,
     * "yezhuZhanghao":"18711001698","yezhuShanchu":-1,"dajianorder":0,"beizhu":"",
     * "shangpinNum":null,"jiesuanPeisongfei":null,"isShouhou":null}
     * orderDetail : [{"id":1582,"xiangmuId":346,"gongsiId":1,"xiangmuMing":null,
     * "dingdanBianhao":"003419ps1511860184680","shangpinId":952,"shangpinMing":"颜家酱板鸭",
     * "shangpinShuliang":1,"shangpinGuige":"2斤装","shangpinShoujia":50,"shangpinZongjia":50,
     * "shangpinJinhuojia":10,"suoluetu":"upload/img/1510904227024,upload/img/1510904229823,
     * upload/img/1510904232505,upload/img/1510904235429","tiaoxingma":"0000000008",
     * "fenlei1":"147","fenlei2":"193","dingdanZhuangtai":2,"tuikuanPicihao":null,"cartId":null}]
     */

    private List<OrderDetailBean> rows;

    public List<OrderDetailBean> getOrderDetail() {
        return rows;
    }

    public void setOrderDetail(List<OrderDetailBean> rows) {
        this.rows = rows;
    }
}
