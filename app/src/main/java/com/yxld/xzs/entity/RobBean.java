package com.yxld.xzs.entity;

import java.util.List;

/**
 * 待抢单、待取货、待送达都是同一个实体类
 * Created by William on 2017/11/23.
 */

public class RobBean extends BaseBack{

    /**
     * order : [{"id":446,"xiangmuId":346,"xiangmuMing":null,"gongsiId":1,"gongsiMing":null,
     * "bianhao":"003401ps1511398551370","zhuangtai":2,"shifouYejian":1,"shouhuorenMing":"向磊",
     * "shouhuoDizhi":"中远公馆1栋1单元301","shouhuoDianhua":"15243648097","zongjine":125,
     * "dianziquan":0,"shijiJine":125,"payjiaoyihao":null,"fukuanFangshi":1,"peisongFangshi":1,
     * "anpaiPeisong":-1,"jiedanMoshi":2,"paidanren":null,"paidanrenMing":null,
     * "peisongrenId":136,"peisongrenMing":"刘诗中远","peisongrenTel":null,"peisongfei":1,
     * "peisongfeiLaiyuan":2,"tuikuanfangshi":null,"tuikuanJine":null,"tuikuanYuanyin":null,
     * "xiadanShijian":"2017-11-23 08:55:51","fukuanShijian":"2017-11-23 08:55:51.48",
     * "paisongShijian":"2017-11-23 09:28:18.986","quhuoShijian":null,"shouhuoShijian":null,
     * "pingjiaShijian":null,"tuikuanShenqingShijian":null,"tuihuanShijian":null,
     * "tuikuanYunxuShijian":null,"quxiaoShijian":null,"quxiaoYuanyin":null,
     * "wanchengShijian":null,"tuikuanpicihao":null,"tuikuanbiaozhi":null,"yezhuId":3401,
     * "yezhuZhanghao":"15243648097","yezhuShanchu":-1,"dajianorder":0,"beizhu":"",
     * "shangpinNum":null,"jiesuanPeisongfei":null,"isShouhou":null}]
     * orderDetail : [{"id":null,"xiangmuId":null,"gongsiId":null,"xiangmuMing":null,
     * "dingdanBianhao":null,"shangpinId":null,"shangpinMing":null,"shangpinShuliang":null,
     * "shangpinGuige":null,"shangpinShoujia":null,"shangpinZongjia":null,
     * "shangpinJinhuojia":null,"suoluetu":null,"tiaoxingma":null,"fenlei1":null,"fenlei2":null,
     * "dingdanZhuangtai":null,"tuikuanPicihao":null,"cartId":null}]
     * status : 0
     * total : 1
     * MSG : 成功获取待抢单列表信息
     */

    private int total;
    private List<OrderBean> order;
    private List<OrderDetailBean> orderDetail;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<OrderBean> getOrder() {
        return order;
    }

    public void setOrder(List<OrderBean> order) {
        this.order = order;
    }

    public List<OrderDetailBean> getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(List<OrderDetailBean> orderDetail) {
        this.orderDetail = orderDetail;
    }


    @Override
    public String toString() {
        return "RobBean{" +
                "total=" + total +
                ", order=" + order +
                ", orderDetail=" + orderDetail +
                '}';
    }
}
