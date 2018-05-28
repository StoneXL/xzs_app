package com.yxld.xzs.entity;

import java.util.List;

/**
 * Created by William on 2017/11/28.
 */

public class NightOrderList extends BaseBack {

    /**
     * order : [{"id":593,"xiangmuId":346,"xiangmuMing":null,"gongsiId":1,"gongsiMing":null,
     * "bianhao":"003419ps1511860196867","zhuangtai":2,"shifouYejian":-1,"shouhuorenMing":"王晓燕",
     * "shouhuoDizhi":"中远公馆3栋3单元333","shouhuoDianhua":"18711001698","zongjine":15,"dianziquan":0,
     * "shijiJine":16,"payjiaoyihao":null,"fukuanFangshi":1,"peisongFangshi":1,"anpaiPeisong":-1,
     * "jiedanMoshi":1,"paidanren":null,"paidanrenMing":null,"peisongrenId":null,
     * "peisongrenMing":null,"peisongrenTel":null,"peisongfei":1,"peisongfeiLaiyuan":3,
     * "tuikuanfangshi":null,"tuikuanJine":null,"tuikuanYuanyin":null,"xiadanShijian":"2017-11-28
     * 17:09:56","fukuanShijian":"2017-11-28 17:09:56","paisongShijian":null,"quhuoShijian":null,
     * "shouhuoShijian":null,"pingjiaShijian":null,"tuikuanShenqingShijian":null,
     * "tuihuanShijian":null,"tuikuanYunxuShijian":null,"quxiaoShijian":null,
     * "quxiaoYuanyin":null,"wanchengShijian":null,"tuikuanpicihao":null,"tuikuanbiaozhi":null,
     * "yezhuId":3419,"yezhuZhanghao":"18711001698","yezhuShanchu":-1,"dajianorder":0,
     * "beizhu":"","shangpinNum":null,"jiesuanPeisongfei":null,"isShouhou":null},{"id":592,
     * "xiangmuId":346,"xiangmuMing":null,"gongsiId":1,"gongsiMing":null,
     * "bianhao":"003419ps1511860191354","zhuangtai":2,"shifouYejian":-1,"shouhuorenMing":"王晓燕",
     * "shouhuoDizhi":"中远公馆3栋3单元333","shouhuoDianhua":"18711001698","zongjine":24,"dianziquan":0,
     * "shijiJine":26,"payjiaoyihao":null,"fukuanFangshi":1,"peisongFangshi":1,"anpaiPeisong":-1,
     * "jiedanMoshi":1,"paidanren":null,"paidanrenMing":null,"peisongrenId":null,
     * "peisongrenMing":null,"peisongrenTel":null,"peisongfei":2,"peisongfeiLaiyuan":3,
     * "tuikuanfangshi":null,"tuikuanJine":null,"tuikuanYuanyin":null,"xiadanShijian":"2017-11-28
     * 17:09:51","fukuanShijian":"2017-11-28 17:09:51","paisongShijian":null,"quhuoShijian":null,
     * "shouhuoShijian":null,"pingjiaShijian":null,"tuikuanShenqingShijian":null,
     * "tuihuanShijian":null,"tuikuanYunxuShijian":null,"quxiaoShijian":null,
     * "quxiaoYuanyin":null,"wanchengShijian":null,"tuikuanpicihao":null,"tuikuanbiaozhi":null,
     * "yezhuId":3419,"yezhuZhanghao":"18711001698","yezhuShanchu":-1,"dajianorder":1,
     * "beizhu":"","shangpinNum":null,"jiesuanPeisongfei":null,"isShouhou":null},{"id":591,
     * "xiangmuId":346,"xiangmuMing":null,"gongsiId":1,"gongsiMing":null,
     * "bianhao":"003419ps1511860184680","zhuangtai":2,"shifouYejian":-1,"shouhuorenMing":"王晓燕",
     * "shouhuoDizhi":"中远公馆3栋3单元333","shouhuoDianhua":"18711001698","zongjine":50,"dianziquan":0,
     * "shijiJine":50,"payjiaoyihao":null,"fukuanFangshi":1,"peisongFangshi":1,"anpaiPeisong":-1,
     * "jiedanMoshi":1,"paidanren":null,"paidanrenMing":null,"peisongrenId":null,
     * "peisongrenMing":null,"peisongrenTel":null,"peisongfei":1,"peisongfeiLaiyuan":2,
     * "tuikuanfangshi":null,"tuikuanJine":null,"tuikuanYuanyin":null,"xiadanShijian":"2017-11-28
     * 17:09:44","fukuanShijian":"2017-11-28 17:09:44","paisongShijian":null,"quhuoShijian":null,
     * "shouhuoShijian":null,"pingjiaShijian":null,"tuikuanShenqingShijian":null,
     * "tuihuanShijian":null,"tuikuanYunxuShijian":null,"quxiaoShijian":null,
     * "quxiaoYuanyin":null,"wanchengShijian":null,"tuikuanpicihao":null,"tuikuanbiaozhi":null,
     * "yezhuId":3419,"yezhuZhanghao":"18711001698","yezhuShanchu":-1,"dajianorder":0,
     * "beizhu":"","shangpinNum":null,"jiesuanPeisongfei":null,"isShouhou":null},{"id":590,
     * "xiangmuId":346,"xiangmuMing":null,"gongsiId":1,"gongsiMing":null,
     * "bianhao":"003419ps1511860178139","zhuangtai":2,"shifouYejian":-1,"shouhuorenMing":"王晓燕",
     * "shouhuoDizhi":"中远公馆3栋3单元333","shouhuoDianhua":"18711001698","zongjine":50,"dianziquan":0,
     * "shijiJine":50,"payjiaoyihao":null,"fukuanFangshi":1,"peisongFangshi":1,"anpaiPeisong":-1,
     * "jiedanMoshi":1,"paidanren":null,"paidanrenMing":null,"peisongrenId":null,
     * "peisongrenMing":null,"peisongrenTel":null,"peisongfei":1,"peisongfeiLaiyuan":2,
     * "tuikuanfangshi":null,"tuikuanJine":null,"tuikuanYuanyin":null,"xiadanShijian":"2017-11-28
     * 17:09:38","fukuanShijian":"2017-11-28 17:09:38","paisongShijian":null,"quShijian":null,
     * "tuikuanShenqingShijian":null,"tuihuanShijian":null,"tuikuanYunxuShijian":null,
     * "quxiaoShijian":null,"quxiaoYuanyin":null,"wanchengShijian":null,"tuikuanpicihao":null,
     * "tuikuanbiaozhi":null,"yezhuId":3419,"yezhuZhanghao":"18711001698","yezhuShanchu":-1,
     * "dajianorder":0,"beizhu":"","shangpinNum":null,"jiesuanPeisongfei":null,"isShouhou":null},
     * {"id":589,"xiangmuId":346,"xiangmuMing":null,"gongsiId":1,"gongsiMing":null,
     * "bianhao":"003419ps1511859131794","zhuangtai":2,"shifouYejian":1,"shouhuorenMing":"王晓燕",
     * "shouhuoDizhi":"中远公馆3栋3单元333","shouhuoDianhua":"18711001698","zongjine":98,"dianziquan":1,
     * "shijiJine":97,"payjiaoyihao":null,"fukuanFangshi":1,"peisongFangshi":1,"anpaiPeisong":-1,
     * "jiedanMoshi":2,"paidanren":null,"paidanrenMing":null,"peisongrenId":null,
     * "peisongrenMing":null,"peisongrenTel":null,"peisongfei":1,"peisongfeiLaiyuan":2,
     * "tuikuanfangshi":null,"tuikuanJine":null,"tuikuanYuanyin":null,"xiadanShijian":"2017-11-28
     * 16:52:11","fukuanShijian":"2017-11-28 16:52:11","paisongShijian":null,"quhuoShijian":null,
     * "shouhuoShijian":null,"pingjiaShijian":null,"tuikuanShenqingShijian":null,
     * "tuihuanShijian":null,"tuikuanYunxuShijian":null,"quxiaoShijian":null,
     * "quxiaoYuanyin":null,"wanchengShijian":null,"tuikuanpicihao":null,"tuikuanbiaozhi":null,
     * "yezhuId":3419,"yezhuZhanghao":"18711001698","yezhuShanchu":-1,"dajianorder":0,
     * "beizhu":"","shangpinNum":null,"jiesuanPeisongfei":null,"isShouhou":null},{"id":584,
     * "xiangmuId":346,"xiangmuMing":null,"gongsiId":1,"gongsiMing":null,
     * "bianhao":"003419ps1511831858249","zhuangtai":2,"shifouYejian":-1,"shouhuorenMing":"王晓燕",
     * "shouhuoDizhi":"中远公馆3栋3单元333","shouhuoDianhua":"18711001698","zongjine":30,"dianziquan":0,
     * "shijiJine":30,"payjiaoyihao":null,"fukuanFangshi":1,"peisongFangshi":1,"anpaiPeisong":-1,
     * "jiedanMoshi":1,"paidanren":null,"paidanrenMing":null,"peisongrenId":null,
     * "peisongrenMing":null,"peisongrenTel":null,"peisongfei":1,"peisongfeiLaiyuan":2,
     * "tuikuanfangshi":null,"tuikuanJine":null,"tuikuanYuanyin":null,"xiadanShijian":"2017-11-28
     * 09:17:38","fukuanShijian":"2017-11-28 09:17:38","paisongShijian":null,"quhuoShijian":null,
     * "shouhuoShijian":null,"pingjiaShijian":null,"tuikuanShenqingShijian":null,
     * "tuihuanShijian":null,"tuikuanYunxuShijian":null,"quxiaoShijian":null,
     * "quxiaoYuanyin":null,"wanchengShijian":null,"tuikuanpicihao":null,"tuikuanbiaozhi":null,
     * "yezhuId":3419,"yezhuZhanghao":"18711001698","yezhuShanchu":-1,"dajianorder":0,
     * "beizhu":"","shangpinNum":null,"jiesuanPeisongfei":null,"isShouhou":null}]
     * total : 6
     */

    private int total;
    private List<OrderBean> rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<OrderBean> getOrder() {
        return rows;
    }

    public void setOrder(List<OrderBean> rows) {
        this.rows = rows;
    }

}
