package com.yxld.xzs.http.api;

/**
 * Created by hu on 2017/5/16.
 */

public interface API {
    long CONNECT_TIMEOUT = 30 * 1000;
    long IO_TIMEOUT = 60 * 1000;

    String PIC = "http://img0.hnchxwl.com/";
    String IP_Camera = "http://api1.cloudlinks.cn";
    String IP_XUNGENG = "http://xungeng.hnchxwl.com/";     //
    //    String IP_XUNGENG = "http://192.168.8.127:8080/wygl/";     //


    /**
     * 测试 （晓燕）
     */
//    String IP_PRODUCT = "http://192.168.8.127:8080/wygl";
//    String BASE_URL_2 = "http://192.168.8.127:8080/wygl/";
    /**
     * 测试 （吴芳）
     */
//    String IP_PRODUCT = "http://120.25.73.87";
//    String BASE_URL_2 = "http://120.25.73.87/";
    /**
     * 测试 （askzc）
     */

//        String IP_PRODUCT = "http://119.23.162.25";
//        String BASE_URL_2 = "http://119.23.162.25/";
    /**
     * 线上
     */
    String IP_PRODUCT = "http://wy.iot.xin";
    String BASE_URL_2 = "http://wy.iot.xin/";


    String BASE_URL = IP_PRODUCT + "/";
    /**
     * 周边商家
     */
    String Xzs = IP_PRODUCT+"/cxwy_consumer_terminal/";
    /**
     * 电子券webview地址https
     */
    String DZQ_URL = "https://wy.iot.xin";
    //// TODO: 2017/11/17  修改网络请求的封住 统一用这里的
    //****************************************改版接口更换************************************//
    /**
     * 登陆接口
     */
    String LOGIN = "xzsapp/index/login.mvc";
    /**
     * 配送状态
     */
    String PEISONGSTATE = "xzsapp/index/isqiangdan.mvc";
    /**
     * 获取客服电话
     */
    String KEFUDIANGHUA = "mall/androidPeisongOrder_getKefuPhoneByXiaoqu";
    /**
     * 获取门禁
     */
    String MENJINIP = "http://dz.hnchxwl.com:81/door/coeds/tsqrcode";
    /**
     * 获取配送员总收入入口
     */
    String ZONGSHOURU = "xzsapp/shouru/zonge.mvc";
    /**
     * 修改密码
     */
    String CHANGEPWD = "xzsapp/index/update.mvc";
    /**
     * 退出登录
     */
    String LOGINOUT = "xzsapp/index/outlogin.mvc";
    /**
     * 已结算列表
     */
    String YIJIESUAN = "xzsapp/shouru/yijiesuan.mvc";
    /**
     * 未结算列表
     */
    String WEIJIESUAN = "xzsapp/shouru/weijiesuan.mvc";
    /**
     * 获取首页订单消息
     */
    String SHOUYEXIAOXI = "xzsapp/shouye/xiaoxi.mvc";
    /**
     * 待抢单列表
     */
    String ROBLIST = "xzsapp/dingdan/daiqiang.mvc";
    /**
     * 待取货列表
     */
    String DELIVERYLIST = "xzsapp/dingdan/daiqu.mvc";
    /**
     * 待送达列表
     */
    String SENDLIST = "xzsapp/dingdan/daisong.mvc";
    /**
     * 配送员抢单
     */
    String CONFIRMROB = "xzsapp/dingdan/qiang.mvc";
    /**
     * 确认取货
     */
    String CONFIRMDELIVERY = "xzsapp/dingdan/qu.mvc";
    /**
     * 确认送达
     */
    String CONFIRMSEND = "xzsapp/dingdan/song.mvc";
    /**
     * 未审批列表
     */
    String APPROVELIST = "stockApp/afsApp_shenpiList";
    /**
     * 夜间出库单列表
     */
    String NIGHTWAREHOUSELIST = "xzsapp/yejianchuku/list.mvc";
    /**
     * 夜间出库单详情
     */
    String GETNIGHTWAREHOUSE = "xzsapp/yejianchuku/details.mvc";
    /**
     * 确认领取夜间出库单
     */
    String CONFRIMNIGHTWAREHOUSE = "xzsapp/yejianchuku/conffirm.mvc";
    /**
     * 夜间订单列表
     */
    String NIGHTORDERLIST = "xzsapp/dingdan/yejian.mvc";
    /**
     * 夜间订单详情
     */
    String NIGHTORDERDETAIL = "xzsapp/dingdan/yejianxq.mvc";
    /**
     * 获取配送人信息
     */
    String GETSENDPEOPLEMSG = "xzsapp/dingdan/getsenders.mvc";
    /**
     * 显示申请的材料列表
     */
    String APPLYMATERIALSLIST = "http://192.168.8.122:8080/cxwyInventory_web/fkc";
    /**
     * 设置夜间配送人
     */
    String SETDELIVERYMAN = "xzsapp/dingdan/setsender.mvc";
    /**
     * 获取指派维修人
     */
    String GETFUZEREN = "daily/androidBaoxiu_androidRepair.action";
    /**
     * 指派部门
     */
    String GETBUMEN = "xzsapp/repair/department.mvc";
    /**
     * 指派负责人
     */
    String GETFUZE = "xzsapp/repair/chargeadmin.mvc";

    /**
     * 确定指派负责人
     */
    String CONFIRMFUZE = "xzsapp/repair/assign.mvc";
    /**
     * 获取网络版本信息
     */
    String GETVERSION = "xzsapp/version/versioninfo.mvc";
    /**
     * 打印小票
     */
    String GETPRINT = "xzsapp/dingdan/print.mvc";
    /**
     * 欣周边 待抢单、待取货、待送达列表
     */
    String RIM_ROB_LIST = Xzs + "app/orderListByXzs";
    /**
     * 欣周边 抢单
     */
    String RIM_RIM_ROB = Xzs + "app/grabOrder";
    /**
     * 欣周边 确认取货
     */
    String RIM_CONFIRM_DELIVERY = Xzs + "app/confirmGetOrder";

    /**
     * 居家安防 搜索摄像头
     */
    String SEARCH_CAMERA = "af/list.mvc";
    /**
     * 居家安防 搜索摄像头(post)
     */
    String ADD_CAMERA = "af/save.mvc";

    /**
     * 工作台 投诉列表(POST)
     */
    String TOUSU_LIST = "tousu/list.mvc";
}
