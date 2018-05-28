package com.yxld.xzs.http.api;


import com.yxld.xzs.entity.AppLogin;
import com.yxld.xzs.entity.ApproveListBean;
import com.yxld.xzs.entity.BaseBack;
import com.yxld.xzs.entity.BaseBack1;
import com.yxld.xzs.entity.BuMen;
import com.yxld.xzs.entity.CameraDetail;
import com.yxld.xzs.entity.CxwyAppVersionBean;
import com.yxld.xzs.entity.CxwyCommon;
import com.yxld.xzs.entity.CxwyCommonToken;
import com.yxld.xzs.entity.CxwyYezhu;
import com.yxld.xzs.entity.FangQu;
import com.yxld.xzs.entity.FuZe;
import com.yxld.xzs.entity.Host;
import com.yxld.xzs.entity.IncomeBean;
import com.yxld.xzs.entity.IndexMessageBean;
import com.yxld.xzs.entity.NightOrderDetail;
import com.yxld.xzs.entity.NightOrderList;
import com.yxld.xzs.entity.NightWarehouseDetail;
import com.yxld.xzs.entity.NightWarehouseListBean;
import com.yxld.xzs.entity.PanDian;
import com.yxld.xzs.entity.PanDianDetail;
import com.yxld.xzs.entity.RimOrderListBean;
import com.yxld.xzs.entity.RobBean;
import com.yxld.xzs.entity.SenderListBean;
import com.yxld.xzs.entity.SuggestBean;
import com.yxld.xzs.entity.TeamManagerListEntity;
import com.yxld.xzs.entity.TeamMember;
import com.yxld.xzs.entity.WeiPanDianListBean;
import com.yxld.xzs.entity.XiangMu;
import com.yxld.xzs.entity.XunGengXiangQing;
import com.yxld.xzs.entity.XunJianDianEntity;
import com.yxld.xzs.entity.XunJianJiLuEntity;
import com.yxld.xzs.entity.XunJianJiluRemoteEntity;
import com.yxld.xzs.entity.XunJianShijianClassifyEntity;
import com.yxld.xzs.entity.XunJianStartEntity;
import com.yxld.xzs.entity.XunJianXianLuXunJianDianEntity;
import com.yxld.xzs.entity.YiJieSuanBean;
import com.yxld.xzs.entity.ZhiPaiRen;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

import static com.yxld.xzs.http.HttpService.CHAZHAO_FANGQU;
import static com.yxld.xzs.http.HttpService.DANYUAN;
import static com.yxld.xzs.http.HttpService.DELETE_ZHUJI;
import static com.yxld.xzs.http.HttpService.FANGHAO;
import static com.yxld.xzs.http.HttpService.GUANLIYUAN;
import static com.yxld.xzs.http.HttpService.KEFFU;
import static com.yxld.xzs.http.HttpService.LOUPAN;
import static com.yxld.xzs.http.HttpService.SEARCH_ZHUJI;
import static com.yxld.xzs.http.HttpService.SHANCHU_SHEBEI;
import static com.yxld.xzs.http.HttpService.TIANJIA_ZHUJI;
import static com.yxld.xzs.http.HttpService.URLFINDXM;
import static com.yxld.xzs.http.HttpService.URL_GET_CAMERA_ADD;
import static com.yxld.xzs.http.HttpService.URL_GET_CAMERA_DEL;
import static com.yxld.xzs.http.HttpService.URL_GET_CAMERA_UPDATE;
import static com.yxld.xzs.http.HttpService.XIUGAI_FANGQU;
import static com.yxld.xzs.http.HttpService.XIUGAI_SHEBEI;
import static com.yxld.xzs.http.HttpService.XUEXI_SHEBEI;
import static com.yxld.xzs.http.HttpService.ZHUJI_LIEBIAO;
import static com.yxld.xzs.http.api.API.ADD_CAMERA;
import static com.yxld.xzs.http.api.API.APPLYMATERIALSLIST;
import static com.yxld.xzs.http.api.API.APPROVELIST;
import static com.yxld.xzs.http.api.API.CHANGEPWD;
import static com.yxld.xzs.http.api.API.CONFIRMDELIVERY;
import static com.yxld.xzs.http.api.API.CONFIRMFUZE;
import static com.yxld.xzs.http.api.API.CONFIRMROB;
import static com.yxld.xzs.http.api.API.CONFIRMSEND;
import static com.yxld.xzs.http.api.API.CONFIRM_PANDIAN;
import static com.yxld.xzs.http.api.API.CONFRIMNIGHTWAREHOUSE;
import static com.yxld.xzs.http.api.API.DELIVERYLIST;
import static com.yxld.xzs.http.api.API.GETBUMEN;
import static com.yxld.xzs.http.api.API.GETFUZE;
import static com.yxld.xzs.http.api.API.GETFUZEREN;
import static com.yxld.xzs.http.api.API.GETNIGHTWAREHOUSE;
import static com.yxld.xzs.http.api.API.GETPRINT;
import static com.yxld.xzs.http.api.API.GETSENDPEOPLEMSG;
import static com.yxld.xzs.http.api.API.GETVERSION;
import static com.yxld.xzs.http.api.API.IP_XUNGENG;
import static com.yxld.xzs.http.api.API.KEFUDIANGHUA;
import static com.yxld.xzs.http.api.API.LOGIN;
import static com.yxld.xzs.http.api.API.LOGINOUT;
import static com.yxld.xzs.http.api.API.NIGHTORDERDETAIL;
import static com.yxld.xzs.http.api.API.NIGHTORDERLIST;
import static com.yxld.xzs.http.api.API.NIGHTWAREHOUSELIST;
import static com.yxld.xzs.http.api.API.PANDIAN_DETAIL;
import static com.yxld.xzs.http.api.API.PEISONGSTATE;
import static com.yxld.xzs.http.api.API.RIM_CONFIRM_DELIVERY;
import static com.yxld.xzs.http.api.API.RIM_RIM_ROB;
import static com.yxld.xzs.http.api.API.RIM_ROB_LIST;
import static com.yxld.xzs.http.api.API.ROBLIST;
import static com.yxld.xzs.http.api.API.SEARCH_CAMERA;
import static com.yxld.xzs.http.api.API.SENDLIST;
import static com.yxld.xzs.http.api.API.SETDELIVERYMAN;
import static com.yxld.xzs.http.api.API.SHOUYEXIAOXI;
import static com.yxld.xzs.http.api.API.START_PANDIAN;
import static com.yxld.xzs.http.api.API.TOUSU_LIST;
import static com.yxld.xzs.http.api.API.WEIJIESUAN;
import static com.yxld.xzs.http.api.API.WEI_PANDIAN_LIST;
import static com.yxld.xzs.http.api.API.YIJIESUAN;
import static com.yxld.xzs.http.api.API.ZONGSHOURU;

/**
 * Created by hu on 2017/5/16.
 */

public interface HttpApi {
    //获取摄像头列表

    @GET("wygl/vcr/vcr_findAllVcr")
        //?uuid=%1$s
    Observable<CxwyCommon> findAllVcr(@QueryMap HashMap<String, String> params);

    //获取公共安防的token
    @POST("https://open.ys7.com/api/lapp/token/get")
    //
    Observable<CxwyCommonToken> getCommonToken(@QueryMap HashMap<String, String> params);

    @GET(IP_XUNGENG + "wygl_xungeng_app/task/recently")
    Observable<XunJianJiLuEntity> getNextXunJianXiang(@QueryMap Map<String, String> params);

    //获取班组管理
    @GET(IP_XUNGENG + "wygl_xungeng_app/message/list")
    Observable<TeamManagerListEntity> getTeam(@QueryMap Map<String, String> params);

    //获取班组的所有人员
    @GET(IP_XUNGENG + "wygl_xungeng_app/message/empid")
    Observable<TeamMember> getTeamMember(@QueryMap Map<String, String> params);

    //更换巡更人员
    @FormUrlEncoded
    @POST(IP_XUNGENG + "wygl_xungeng_app/message/updateone")
    Observable<BaseBack> updateTeamMemberOne(@FieldMap Map<String, String> params);

    //更换巡更人员
    @POST(IP_XUNGENG + "wygl_xungeng_app/message/updatemonth")
    @FormUrlEncoded
    Observable<BaseBack> updateTeamMemberMonth(@FieldMap Map<String, String> params);

    @POST(IP_XUNGENG + "wygl_xungeng_app/task//line")
    Observable<XunJianDianEntity> getXunJianXianLu(@QueryMap Map<String, String> params);

    @GET(IP_XUNGENG + "wygl_xungeng_app/task/onedetails")
    Observable<XunJianXianLuXunJianDianEntity> getXunJianXianLuXunJianDian(@QueryMap Map<String, String> params);

    @POST(IP_XUNGENG + "wygl_xungeng_app/task/start")
    Observable<XunJianStartEntity> startPatrol(@QueryMap Map<String, String> params);


    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST(IP_XUNGENG + "wygl_xungeng_app/task//uploading")
    Observable<BaseBack> uploadPatrolResult(@Body Map<String, String> params);

    @GET(IP_XUNGENG + "wygl_xungeng_app/common/shijian")
    Observable<XunJianShijianClassifyEntity> getShiJian(@QueryMap Map<String, String> params);

    @FormUrlEncoded
    @POST(IP_XUNGENG + "wygl_xungeng_app/plan/time")
    Observable<XunGengXiangQing> getOneDayXunJianPlans(@FieldMap Map<String, String> params);


    @FormUrlEncoded
//    @POST(IP_XUNGENG + "wygl_xungeng_app/device/add")
    @POST(IP_XUNGENG + "wygl_xungeng_app/device/add")
    Observable<BaseBack> addDian(@FieldMap Map<String, String> params);

//    @GET("wygl_xungeng_app/plan/month")
//    Observable<XunGengXiangQing> getOneMonthPlan(@FieldMap Map<String, String> params);


    @FormUrlEncoded
    @POST(IP_XUNGENG + "wygl_xungeng_app/history/list")
    Observable<XunJianJiluRemoteEntity> getAllRemoteJilu(@FieldMap Map<String, String> params);

    @GET(URLFINDXM)
    Observable<XiangMu> findXm(@QueryMap Map<String, String> params);


    @GET(URL_GET_CAMERA_ADD)
    Observable<BaseBack> cameraAdd(@QueryMap Map<String, String> params);

    @GET(URL_GET_CAMERA_DEL)
    Observable<BaseBack> deletCamera(@QueryMap Map<String, String> params);

    @GET(URL_GET_CAMERA_UPDATE)
    Observable<BaseBack> updateDevice(@QueryMap Map<String, String> params);


    @GET(LOUPAN)
    Observable<String> findloupan(@QueryMap Map<String, String> params);

    @GET(DANYUAN)
    Observable<String> finddanyuan(@QueryMap Map<String, String> params);

    @GET(FANGHAO)
    Observable<String> findfanghao(@QueryMap Map<String, String> params);

    @GET(GUANLIYUAN)
    Observable<CxwyYezhu> findguanliyuan(@QueryMap Map<String, String> params);

    //****************************************派安************************************//
    @GET(ZHUJI_LIEBIAO)
    Observable<Host> getZhuJiLieBiao(@QueryMap Map<String, String> params);

    @GET(TIANJIA_ZHUJI)
    Observable<BaseBack> tianjiaZhuji(@QueryMap Map<String, String> params);

    @GET(SEARCH_ZHUJI)
    Observable<Host> searchZhuji(@QueryMap Map<String, String> params);

    @GET(DELETE_ZHUJI)
    Observable<Host> deleteZhuji(@QueryMap Map<String, String> params);

    @GET(CHAZHAO_FANGQU)
    Observable<FangQu> chazhaoFangqu(@QueryMap Map<String, String> params);

    @GET(XUEXI_SHEBEI)
    Observable<BaseBack> xuexiShebei(@QueryMap Map<String, String> params);

    @GET(XIUGAI_FANGQU)
    Observable<BaseBack> xiugaiFangqu(@QueryMap Map<String, String> params);

    @GET(SHANCHU_SHEBEI)
    Observable<BaseBack> shanchuSheBei(@QueryMap Map<String, String> params);

    @GET(XIUGAI_SHEBEI)
    Observable<BaseBack> xiugaiShebei(@QueryMap Map<String, String> params);

    // 获取客服电话
//    @GET("wygl/mall/androidPeisongOrder_getKefuPhoneByXiaoqu")
    @GET(KEFFU)
    Observable<BaseBack> hotline(@QueryMap Map<String, String> params);
    //****************************************派安************************************//


    //****************************************改版接口同一这个************************************//
    @GET(LOGIN)
    Observable<AppLogin> login(@QueryMap Map<String, String> params);

    @GET(PEISONGSTATE)
    Observable<BaseBack> peisongState(@QueryMap Map<String, String> params);

    @GET(KEFUDIANGHUA)
    Observable<BaseBack1> getPhone(@QueryMap Map<String, String> params);

    @GET(ZONGSHOURU)
    Observable<IncomeBean> getZongShouRu(@QueryMap Map<String, String> params);

    @FormUrlEncoded
    @POST(CHANGEPWD)
    Observable<BaseBack> changePwd(@FieldMap Map<String, String> params);

    @GET(LOGINOUT)
    Observable<BaseBack> loginOut(@QueryMap Map<String, String> params);
    @GET(YIJIESUAN)
    Observable<YiJieSuanBean> yiJieSuan(@QueryMap Map<String, String> params);
    @GET(WEIJIESUAN)
    Observable<YiJieSuanBean> weiJieSuan(@QueryMap Map<String, String> params);
    @GET(SHOUYEXIAOXI)
    Observable<IndexMessageBean> shouYeXiaoXi(@QueryMap Map<String, String> params);
    @GET(ROBLIST)
    Observable<RobBean> robList(@QueryMap Map<String, String> params);
    @GET(DELIVERYLIST)
    Observable<RobBean> deliveryList(@QueryMap Map<String, String> params);
    @GET(SENDLIST)
    Observable<RobBean> sendList(@QueryMap Map<String, String> params);
    @GET(CONFIRMROB)
    Observable<BaseBack> confirmRob(@QueryMap Map<String, String> params);
    @GET(CONFIRMDELIVERY)
    Observable<BaseBack> confirmDelivery(@QueryMap Map<String, String> params);
    @GET(CONFIRMSEND)
    Observable<BaseBack> confirmSend(@QueryMap Map<String, String> params);
    @GET(APPROVELIST)
    Observable<ApproveListBean> approveList(@QueryMap Map<String, String> params);
    @GET(NIGHTWAREHOUSELIST)
    Observable<NightWarehouseListBean> nightWarehouseList(@QueryMap Map<String, String> params);
    @GET(GETNIGHTWAREHOUSE)
    Observable<NightWarehouseDetail> getNightWarehouse(@QueryMap Map<String, String> params);
    @GET(CONFRIMNIGHTWAREHOUSE)
    Observable<BaseBack> confrimNightWarehouse(@QueryMap Map<String, String> params);
    @GET(NIGHTORDERLIST)
    Observable<NightOrderList> nightOrderList(@QueryMap Map<String, String> params);
    @GET(NIGHTORDERDETAIL)
    Observable<NightOrderDetail> nightOrderDetail(@QueryMap Map<String, String> params);
    @GET(GETSENDPEOPLEMSG)
    Observable<SenderListBean> getSendPeopleMsg(@QueryMap Map<String, String> params);
    @GET(APPLYMATERIALSLIST)
    Observable<BaseBack> applyMaterialsList(@QueryMap Map<String, String> params);
    @FormUrlEncoded
    @POST(SETDELIVERYMAN)
    Observable<BaseBack> setDeliveryman(@FieldMap Map<String, String> params);
    @GET(GETFUZEREN)
    Observable<ZhiPaiRen> getFuZeRen(@QueryMap Map<String, String> params);
    @GET(GETBUMEN)
    Observable<BuMen> getBumen(@QueryMap Map<String, String> params);
    @GET(GETFUZE)
    Observable<FuZe> getFuZe(@QueryMap Map<String, String> params);
    @FormUrlEncoded
    @POST(CONFIRMFUZE)
    Observable<BaseBack> confirmFuze(@FieldMap Map<String, String> params);
    @GET(GETVERSION)
    Observable<CxwyAppVersionBean> getAppVersionInfo(@QueryMap Map<String, String> params);
    @GET(GETPRINT)
    Observable<BaseBack> getPrint(@QueryMap Map<String, String> params);
    @FormUrlEncoded
    @POST(RIM_ROB_LIST)
    Observable<RimOrderListBean> getRimRobList(@FieldMap Map<String, String> params);
    @FormUrlEncoded
    @POST(RIM_RIM_ROB)
    Observable<BaseBack> confirmRimRob(@FieldMap Map<String, String> params);
    @FormUrlEncoded
    @POST(RIM_CONFIRM_DELIVERY)
    Observable<BaseBack> confirmRimDelivery(@FieldMap Map<String, String> params);
    @FormUrlEncoded
    @POST(SEARCH_CAMERA)
    Observable<CameraDetail> searchCamera(@FieldMap Map<String, String> params);
    @FormUrlEncoded
    @POST(ADD_CAMERA)
    Observable<BaseBack> addCamera(@FieldMap Map<String, String> params);
    @FormUrlEncoded
    @POST(TOUSU_LIST)
    Observable<SuggestBean> getTousuList(@FieldMap Map<String, String> params);
    @GET
    Observable<BaseBack> firstScan(@Url String url, @QueryMap Map<String, String> params);

    @FormUrlEncoded
    @POST(START_PANDIAN)
    Observable<PanDian> startPandian(@FieldMap Map<String, String> params);
    @GET(WEI_PANDIAN_LIST)
    Observable<WeiPanDianListBean> weiPanDianList(@QueryMap Map<String, String> params);

    @GET(PANDIAN_DETAIL+"{wuziBianhao}")
    Observable<PanDianDetail> getPandianDetail(@Path("wuziBianhao") String wuziBianhao , @QueryMap Map<String, String> params);

    @PUT(CONFIRM_PANDIAN)
    Observable<BaseBack> confirmPandian(@QueryMap Map<String, String> params);
}
