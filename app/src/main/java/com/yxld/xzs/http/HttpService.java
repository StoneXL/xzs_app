package com.yxld.xzs.http;


import com.yxld.xzs.base.BaseResultEntity;
import com.yxld.xzs.base.BaseResultEntity1;
import com.yxld.xzs.base.BaseResultEntity2;
import com.yxld.xzs.base.BaseResultEntity3;
import com.yxld.xzs.entity.APPCamera;
import com.yxld.xzs.entity.AppAssign;
import com.yxld.xzs.entity.AppCode;
import com.yxld.xzs.entity.AppHome;
import com.yxld.xzs.entity.AppLogin;
import com.yxld.xzs.entity.AppOrder;
import com.yxld.xzs.entity.AppPeisongorder;
import com.yxld.xzs.entity.AppWork;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

import static com.yxld.xzs.http.api.API.IP_Camera;
import static com.yxld.xzs.http.api.API.IP_PRODUCT;
import static com.yxld.xzs.http.api.API.MENJINIP;

/**
 * 作者：yishangfei on 2016/12/31 0031 10:16
 * 邮箱：yishangfei@foxmail.com
 */
public interface HttpService {
    //登陆接口
    @GET("xzs/xzs_login?")
    Observable<BaseResultEntity3<AppLogin>> login(@Query("userName") String phone, @Query("passWord") String pwd, @Query("macAddr") String macAddress);

    //修改密码接口
    @GET("admin/androidAdmin_adminUpdatePwd?")
    Observable<BaseResultEntity<AppLogin>> change(@Query("userName") String phone, @Query("passWord") String pwd,@Query("newPassWord") String npwd);

    //修改配送人接单状态
    @GET("mall/androidPeisong_mergePeisongState")
    Observable<BaseResultEntity<String>> state(@Query("peisong.cxwyPeisongId") int peisongid, @Query("peisong.cxwyPeisongState") String state);

    //待抢单接口
    @GET("mall/androidPeisongOrder_findDaiQiangDanOrder")
    Observable<BaseResultEntity1<AppHome>> grab(@Query("peisong.cxwyPeisongId") int peisongid,@Query("xiaoquId") int xiaoquId);

    //抢单按钮接口
    @GET("mall/androidPeisongOrder_peiSongQiangDan")
    Observable<BaseResultEntity<AppOrder>> single(@Query("peisongorder.peisongOrderPeisongId") int peisongid, @Query("peisongorder.peisongOrderOrderBianhao") int bianhao,@Query("xiaoquId") int xiaoquId);

    //待取货接口
    @GET("mall/androidPeisongOrder_findDaiQuHuoOrder")
    Observable<BaseResultEntity1<AppHome>> pick(@Query("peisong.cxwyPeisongId") int peisongid, @Query("xiaoquId") int xiaoquId);

    //取货按钮接口
    @GET("mall/androidPeisongOrder_peiSongQuHuo")
    Observable<BaseResultEntity<AppOrder>> take(@Query("peisongorder.peisongOrderPeisongId") int peisongid, @Query("peisongorder.peisongOrderOrderBianhao") int bianhao);

    //待送达接口
    @GET("mall/androidPeisongOrder_findDaiSongdaOrder")
    Observable<BaseResultEntity1<AppHome>> send(@Query("peisong.cxwyPeisongId") int peisongid,@Query("xiaoquId") int xiaoquId);

    //送达按钮接口
    @GET("mall/androidPeisongOrder_peiSongSongDa")
    Observable<BaseResultEntity<AppOrder>> reach(@Query("peisongorder.peisongOrderPeisongId") int peisongid, @Query("peisongorder.peisongOrderOrderBianhao") int bianhao);

    //获取配送人员总收入接口
    @GET("mall/androidPeisong_PeisongrenTotalShouruById")
    Observable<BaseResultEntity<String>> total(@Query("id") int peisongid);

    //获取工作汇总已完成接口和取配送明细接口
    @GET("mall/androidPeisongOrder_PeisongOrderDestail")
    Observable<BaseResultEntity<List<AppPeisongorder>>> complete(@Query("id") int peisongid);

    //获取工作汇总取消单接口
    @GET("mall/androidPeisongOrder_PeisongOrderCancelOrder")
    Observable<BaseResultEntity<List<AppPeisongorder>>> cancel(@Query("id") int peisongid);

    // 获取工作汇总异常单接口
    @GET("mall/androidPeisongOrder_PeisongOrderYiChangOrder")
    Observable<BaseResultEntity<List<AppPeisongorder>>> abnormal(@Query("id") int peisongid);

    // 获取客服电话
    @GET("mall/androidPeisongOrder_getKefuPhoneByXiaoqu")
    Observable<BaseResultEntity<String>> hotline(@Query("xiaoqu") String xiaoqu);

    //夜间管理
    @GET("mall/androidPeisongOrder_findTodayQuhuoByFuzeren")
    Observable<BaseResultEntity<List<AppPeisongorder>>> night(@Query("id") int id);

    //特殊门禁
    @GET(MENJINIP)
    Observable<BaseResultEntity<AppCode>> quard(@Query("bName") String name, @Query("bPhone") String tel);

    //报修  工单验收
    @GET("daily/androidBaoxiu_andriodBaoxiulist.action")
    Observable<BaseResultEntity<List<AppWork>>> work(@Query("adminId") int adminId);

    //报修  获取指派维修人
    @GET("daily/androidBaoxiu_androidRepair.action")
    Observable<BaseResultEntity<List<AppAssign>>> assign(@Query("answer") int answer);

    //报修  项目主管指派维修人
    @GET("daily/androidBaoxiu_andriodAssign.action")
    Observable<BaseResultEntity<String>> point(@Query("answer") int answer,@Query("repair") int repair,@Query("baoxiuId")int id);

    //报修 项目主管验收
    @GET("daily/androidBaoxiu_androidCheck.action")
    Observable<BaseResultEntity<String>> check(@Query("answer") int answer,@Query("opinion") String opinion,@Query("order") String order,@Query("baoxiuId") int baoxiuId);

    //报修七牛token
    @GET("qiniu/qiniu_getQiniuToken.action?")
    Observable<BaseResultEntity2<String>> token();

    //报修  维修人维修
    @GET("daily/androidBaoxiu_androidFinish.action")
    Observable<BaseResultEntity<String>> maintain(@Query("baoxiuId") int baoxiuId, @Query("opinion") String opinion,@Query("order") String order);

    @FormUrlEncoded
    @POST(URL_GET_CAMERA_DEL)
    Observable<BaseResultEntity<String>> deletCamera(@FieldMap Map<String, RequestBody> params);

    @GET(URL_GET_CAMERA_All)
    Observable<BaseResultEntity<String>> getAllCamera(@QueryMap Map<String, RequestBody> params);

    @FormUrlEncoded
    @POST(URL_GET_CAMERA)
    Observable<APPCamera> getCameraLogin(@FieldMap Map<String, RequestBody> params);

    /**
     * 摄像头登录
     */
    String URL_GET_CAMERA = IP_Camera + "/Users/ThirdLogin.ashx?";

    /**
     * 摄像头添加    -0223已增加uuid验证
     */
    String URL_GET_CAMERA_ADD = IP_PRODUCT + "/xzs/equip_save?";

    /*
     * 获取全部摄像头列表  -0223已增加uuid验证
     */
//    String URL_GET_CAMERA_All = IP_PRODUCT + "/xzs/equip_find?";
    String URL_GET_CAMERA_All = IP_PRODUCT + "/xzs/equip_listCamera?";


    /*
     * 摄像头修改密码         ?sb.sb_ipc_id=%1$s&sb.sb_ipc_pwd=%2$s&uuid=%3$s
    */
    String URL_GET_CAMERA_UPDATE = IP_PRODUCT + "/xzs/equip_merge?";


    /*
     * 删除摄像头  -0223已增加uuid验证
     */
    String URL_GET_CAMERA_DEL = IP_PRODUCT + "/xzs/equip_remove?";

    String URLFINDXM = IP_PRODUCT + "/xzs/equip_findXm";


    //*******************************************************************************
    String LOUPAN = IP_PRODUCT + "/daily/fangwu_findloudong.action";
    String DANYUAN = IP_PRODUCT + "/daily/fangwu_finddanyuan.action";
    String FANGHAO = IP_PRODUCT + "/daily/fangwu_findfanghao.action";


    //****************************************派安************************************//
    //管理员
    String GUANLIYUAN = "paian/xinzhushou_findYezhuInfos";
    //添加主机
    String TIANJIA_ZHUJI = "paian/xinzhushou_addZhuji";
    //搜索主机
    String SEARCH_ZHUJI = "paian/xinzhushou_zhujiList";
    //删除主机
    String DELETE_ZHUJI = "paian/xinzhushou_deleteZhuji";
    //主机列表
    String ZHUJI_LIEBIAO = "paian/xinzhushou_findPaianZhujiList";
    //查找防区
    String CHAZHAO_FANGQU = "paian/xinzhushou_findPaianZhuji";
    //学习设备
    String XUEXI_SHEBEI ="paian/xinzhushou_xuexiShebei";
    //修改设备
    String XIUGAI_FANGQU ="paian/xinzhushou_updateFangqu";
    //删除设备
    String SHANCHU_SHEBEI = "paian/xinzhushou_deletePaianShebei";
    //修改主机
    String XIUGAI_SHEBEI = "paian/xinzhushou_updateZhuji";

    String KEFFU = "mall/androidPeisongOrder_getKefuPhoneByXiaoqu";
    //****************************************派安************************************//
}
