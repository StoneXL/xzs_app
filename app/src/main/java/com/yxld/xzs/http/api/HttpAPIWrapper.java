package com.yxld.xzs.http.api;

import com.google.gson.JsonObject;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.socks.library.KLog;
import com.yxld.xzs.BuildConfig;
import com.yxld.xzs.base.DemoApplicationLike;
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
import com.yxld.xzs.http.api.stringconverter.StringConverterFactory;
import com.yxld.xzs.http.exception.ErrorHandlerInterceptor;
import com.yxld.xzs.utils.ToastUtil;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.HttpException;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author hu
 * @desc 对Request实体(不执行)在执行时所调度的线程，以及得到ResponseBody后根据retCode对Result进行进一步处理
 * @date 2017/5/31 16:56
 */
public class HttpAPIWrapper {

    private HttpApi mHttpAPI;
    public static HttpAPIWrapper instance;
    public static HttpAPIWrapper instance2;
    private static OkHttpClient exportClient;

    static {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(logging);
            builder.addInterceptor(new ErrorHandlerInterceptor());
        }
        builder.connectTimeout(API.CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(API.IO_TIMEOUT, TimeUnit.MILLISECONDS)
                .addInterceptor(new RequestBodyInterceptor());
        exportClient = builder.build();
    }

    public static OkHttpClient getOkHttpClient() {
        return exportClient;
    }

    public HttpAPIWrapper(OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.BASE_URL_2)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 添加Rx适配器
                .addConverterFactory(GsonConverterFactory.create()) // 添加Gson转换器
                .client(okHttpClient)
                .build();
        mHttpAPI = retrofit.create(HttpApi.class);
    }

    public HttpAPIWrapper(OkHttpClient okHttpClient, String array) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.BASE_URL_2)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 添加Rx适配器
                .addConverterFactory(StringConverterFactory.create())  //添加字符串解析器
                .client(okHttpClient)
                .build();
        mHttpAPI = retrofit.create(HttpApi.class);
    }

    public static HttpAPIWrapper getInstance() {
        return getInstance(getOkHttpClient());
    }

    public static HttpAPIWrapper getInstance(OkHttpClient okHttpClient) {
        if (instance == null) {
            instance = new HttpAPIWrapper(okHttpClient);
        }
        return instance;
    }

    public static HttpAPIWrapper getInstance(OkHttpClient okHttpClient, String array) {
        if (instance2 == null) {
            instance2 = new HttpAPIWrapper(okHttpClient, array);
        }
        return instance2;
    }

    public Observable<CxwyCommon> findAllVcr(HashMap data) {
        return wrapper(mHttpAPI.findAllVcr(data)).compose(SCHEDULERS_TRANSFORMER);
    }

    public Observable<CxwyCommonToken> getCommonToken(HashMap data) {
        return wrapper(mHttpAPI.getCommonToken(data)).compose(SCHEDULERS_TRANSFORMER);
    }

    public Observable<XunJianJiLuEntity> getNextXunJianXiang(Map data) {
        return wrapper(mHttpAPI.getNextXunJianXiang(data).compose(SCHEDULERS_TRANSFORMER));
    }

    public Observable<XunJianDianEntity> getXunJianXianLu(Map data) {
        return wrapper(mHttpAPI.getXunJianXianLu(data).compose(SCHEDULERS_TRANSFORMER));
    }

    public Observable<XunJianXianLuXunJianDianEntity> getXunJianXianLuXunJianDian(Map data) {
        return wrapper(mHttpAPI.getXunJianXianLuXunJianDian(data).compose(SCHEDULERS_TRANSFORMER));
    }

    public Observable<XunJianStartEntity> startPatrol(Map data) {
        return wrapper(mHttpAPI.startPatrol(data).compose(SCHEDULERS_TRANSFORMER));
    }


    public Observable<BaseBack> uploadPatrolResult(Map data) {
        return wrapper(mHttpAPI.uploadPatrolResult(data).compose(SCHEDULERS_TRANSFORMER));
    }

    public Observable<XunJianShijianClassifyEntity> getShijian(Map data) {
        return wrapper(mHttpAPI.getShiJian(data).compose(SCHEDULERS_TRANSFORMER));
    }

    public Observable<XunGengXiangQing> getOneXunJianPlans(Map data) {
        return wrapper(mHttpAPI.getOneDayXunJianPlans(data).compose(SCHEDULERS_TRANSFORMER));
    }

    public Observable<BaseBack> addDian(Map data) {
        return wrapper(mHttpAPI.addDian(data).compose(SCHEDULERS_TRANSFORMER));
    }

    public Observable<XunJianJiluRemoteEntity> getAllRomoteJilu(Map data) {
        return wrapper(mHttpAPI.getAllRemoteJilu(data).compose(SCHEDULERS_TRANSFORMER));
    }


    public Observable<TeamManagerListEntity> getTeamManage(Map data) {
        return wrapper(mHttpAPI.getTeam(data).compose(SCHEDULERS_TRANSFORMER));
    }

    public Observable<TeamMember> getTeamMember(Map data) {
        return wrapper(mHttpAPI.getTeamMember(data).compose(SCHEDULERS_TRANSFORMER));
    }

    public Observable<BaseBack> updateTeamMemberOne(Map data) {
        return wrapper(mHttpAPI.updateTeamMemberOne(data).compose(SCHEDULERS_TRANSFORMER));
    }

    public Observable<BaseBack> updateTeamMemberMonth(Map data) {
        return wrapper(mHttpAPI.updateTeamMemberMonth(data).compose(SCHEDULERS_TRANSFORMER));
    }

    public Observable<XiangMu> findXm(Map data) {
        return wrapper(mHttpAPI.findXm(data).compose(SCHEDULERS_TRANSFORMER));
    }

    public Observable<BaseBack> cameraAdd(Map data) {
        return wrapper(mHttpAPI.cameraAdd(data).compose(SCHEDULERS_TRANSFORMER));
    }

    public Observable<BaseBack> deletCamera(Map data) {
        return wrapper(mHttpAPI.deletCamera(data).compose(SCHEDULERS_TRANSFORMER));
    }

    public Observable<BaseBack> updateDevice(Map data) {
        return wrapper(mHttpAPI.updateDevice(data).compose(SCHEDULERS_TRANSFORMER));
    }

    //*************************************派安*********************************//
    public Observable<String> findloupan(Map data) {
        return wrapperObject(mHttpAPI.findloupan(data).compose(SCHEDULERS_TRANSFORMER));
    }

    public Observable<String> finddanyuan(Map data) {
        return wrapperObject(mHttpAPI.finddanyuan(data).compose(SCHEDULERS_TRANSFORMER));
    }

    public Observable<String> findfanghao(Map data) {
        return wrapperObject(mHttpAPI.findfanghao(data).compose(SCHEDULERS_TRANSFORMER));
    }

    public Observable<CxwyYezhu> findguanliyuan(Map data) {
        return wrapper(mHttpAPI.findguanliyuan(data).compose(SCHEDULERS_TRANSFORMER));
    }

    public Observable<BaseBack> tianjiaZhuji(Map data) {
        return wrapper(mHttpAPI.tianjiaZhuji(data).compose(SCHEDULERS_TRANSFORMER));
    }

    //搜索主机
    public Observable<Host> searchZhuji(Map data) {
        return wrapper(mHttpAPI.searchZhuji(data).compose(SCHEDULERS_TRANSFORMER));
    }

    //删除主机
    public Observable<Host> deleteZhuji(Map data) {
        return wrapper(mHttpAPI.deleteZhuji(data).compose(SCHEDULERS_TRANSFORMER));
    }

    public Observable<Host> getZhuJiLieBiao(Map data) {
        return wrapper(mHttpAPI.getZhuJiLieBiao(data).compose(SCHEDULERS_TRANSFORMER));
    }

    public Observable<FangQu> chazhaoFangqu(Map data) {
        return wrapper(mHttpAPI.chazhaoFangqu(data).compose(SCHEDULERS_TRANSFORMER));
    }

    public Observable<BaseBack> xuexiShebei(Map data) {
        return wrapper(mHttpAPI.xuexiShebei(data).compose(SCHEDULERS_TRANSFORMER));
    }

    public Observable<BaseBack> xiugaiFangqu(Map data) {
        return wrapper(mHttpAPI.xiugaiFangqu(data).compose(SCHEDULERS_TRANSFORMER));
    }

    public Observable<BaseBack> shanchuSheBei(Map data) {
        return wrapper(mHttpAPI.shanchuSheBei(data).compose(SCHEDULERS_TRANSFORMER));
    }

    public Observable<BaseBack> xiugaiShebei(Map data) {
        return wrapper(mHttpAPI.xiugaiShebei(data).compose(SCHEDULERS_TRANSFORMER));
    }


    /**
     * 获得客服电话 更换网络请求封装方式（Test)
     * @param data
     * @return
     */
    public Observable<BaseBack> hotLine(Map data){
        return wrapper(mHttpAPI.hotline(data).compose(SCHEDULERS_TRANSFORMER));
    }
    //*************************************派安*********************************//
    //*************************************更新的接口*********************************//


    public Observable<AppLogin> Login(Map data) {
        return wrapper(mHttpAPI.login(data).compose(SCHEDULERS_TRANSFORMER));
    }
    public Observable<BaseBack> peisongState(Map data) {
        return wrapper(mHttpAPI.peisongState(data).compose(SCHEDULERS_TRANSFORMER));
    }
    public Observable<BaseBack1> getPhone(Map data) {
        return wrapper(mHttpAPI.getPhone(data).compose(SCHEDULERS_TRANSFORMER));
    }
    public Observable<IncomeBean> getZongShouRu(Map data) {
        return wrapper(mHttpAPI.getZongShouRu(data).compose(SCHEDULERS_TRANSFORMER));
    }
    public Observable<BaseBack> changePwd(Map data) {
        return wrapper(mHttpAPI.changePwd(data).compose(SCHEDULERS_TRANSFORMER));
    }
    public Observable<BaseBack> loginOut(Map data) {
        return wrapper(mHttpAPI.loginOut(data).compose(SCHEDULERS_TRANSFORMER));
    }
    public Observable<YiJieSuanBean> yiJieSuan(Map data) {
        return wrapper(mHttpAPI.yiJieSuan(data).compose(SCHEDULERS_TRANSFORMER));
    }
    public Observable<YiJieSuanBean> weiJieSuan(Map data) {
        return wrapper(mHttpAPI.weiJieSuan(data).compose(SCHEDULERS_TRANSFORMER));
    }
    public Observable<IndexMessageBean> shouYeXiaoXi(Map data) {
        return wrapper(mHttpAPI.shouYeXiaoXi(data).compose(SCHEDULERS_TRANSFORMER));
    }
    public Observable<RobBean> robList(Map data) {
        return wrapper(mHttpAPI.robList(data).compose(SCHEDULERS_TRANSFORMER));
    }
    public Observable<RobBean> deliveryList(Map data) {
        return wrapper(mHttpAPI.deliveryList(data).compose(SCHEDULERS_TRANSFORMER));
    }
    public Observable<RobBean> sendList(Map data) {
        return wrapper(mHttpAPI.sendList(data).compose(SCHEDULERS_TRANSFORMER));
    }
    public Observable<BaseBack> confirmRob(Map data) {
        return wrapper(mHttpAPI.confirmRob(data).compose(SCHEDULERS_TRANSFORMER));
    }
    public Observable<BaseBack> confirmDelivery(Map data) {
        return wrapper(mHttpAPI.confirmDelivery(data).compose(SCHEDULERS_TRANSFORMER));
    }
    public Observable<BaseBack> confirmSend(Map data) {
        return wrapper(mHttpAPI.confirmSend(data).compose(SCHEDULERS_TRANSFORMER));
    }
    public Observable<ApproveListBean> approveList(Map data) {
        return wrapper(mHttpAPI.approveList(data).compose(SCHEDULERS_TRANSFORMER));
    }
    public Observable<NightWarehouseListBean> nightWarehouseList(Map data) {
        return wrapper(mHttpAPI.nightWarehouseList(data).compose(SCHEDULERS_TRANSFORMER));
    }
    public Observable<NightWarehouseDetail> getNightWarehouse(Map data) {
        return wrapper(mHttpAPI.getNightWarehouse(data).compose(SCHEDULERS_TRANSFORMER));
    }
    public Observable<BaseBack> confrimNightWarehouse(Map data) {
        return wrapper(mHttpAPI.confrimNightWarehouse(data).compose(SCHEDULERS_TRANSFORMER));
    }
    public Observable<NightOrderList> nightOrderList(Map data) {
        return wrapper(mHttpAPI.nightOrderList(data).compose(SCHEDULERS_TRANSFORMER));
    }
    public Observable<BaseBack> applyMaterialsList(Map data) {
        return wrapper(mHttpAPI.applyMaterialsList(data).compose(SCHEDULERS_TRANSFORMER));
    }
    public Observable<NightOrderDetail> nightOrderDetail(Map data) {
        return wrapper(mHttpAPI.nightOrderDetail(data).compose(SCHEDULERS_TRANSFORMER));
    }
    public Observable<SenderListBean> getSendPeopleMsg(Map data) {
        return wrapper(mHttpAPI.getSendPeopleMsg(data).compose(SCHEDULERS_TRANSFORMER));
    }
    public Observable<BaseBack> setDeliveryman(Map data) {
        return wrapper(mHttpAPI.setDeliveryman(data).compose(SCHEDULERS_TRANSFORMER));
    }
    public Observable<ZhiPaiRen> getFuZeRen(Map data) {
        return wrapper(mHttpAPI.getFuZeRen(data).compose(SCHEDULERS_TRANSFORMER));
    }
    public Observable<BuMen> getBumen(Map data) {
        return wrapper(mHttpAPI.getBumen(data).compose(SCHEDULERS_TRANSFORMER));
    }
    public Observable<FuZe> getFuZe(Map data) {
        return wrapper(mHttpAPI.getFuZe(data).compose(SCHEDULERS_TRANSFORMER));
    }
    public Observable<BaseBack> confirmFuze(Map data) {
        return wrapper(mHttpAPI.confirmFuze(data).compose(SCHEDULERS_TRANSFORMER));
    }
    public Observable<CxwyAppVersionBean> getAppVersionInfo(Map data) {
        return wrapper(mHttpAPI.getAppVersionInfo(data).compose(SCHEDULERS_TRANSFORMER));
    }
    public Observable<BaseBack> getPrint(Map data) {
        return wrapper(mHttpAPI.getPrint(data).compose(SCHEDULERS_TRANSFORMER));
    }
    public Observable<RimOrderListBean> getRimRobList(Map data) {
        return wrapper(mHttpAPI.getRimRobList(data).compose(SCHEDULERS_TRANSFORMER));
    }
    public Observable<BaseBack> confirmRimRob(Map data) {
        return wrapper(mHttpAPI.confirmRimRob(data).compose(SCHEDULERS_TRANSFORMER));
    }
    public Observable<BaseBack> confirmRimDelivery(Map data) {
        return wrapper(mHttpAPI.confirmRimDelivery(data).compose(SCHEDULERS_TRANSFORMER));
    }
    public Observable<CameraDetail> searchCamera(Map data) {
        return wrapper(mHttpAPI.searchCamera(data).compose(SCHEDULERS_TRANSFORMER));
    }
    public Observable<BaseBack> addCamera(Map data) {
        return wrapper(mHttpAPI.addCamera(data).compose(SCHEDULERS_TRANSFORMER));
    }
    public Observable<SuggestBean> getTousuList(Map data) {
        return wrapper(mHttpAPI.getTousuList(data).compose(SCHEDULERS_TRANSFORMER));
    }
    public Observable<BaseBack> firstScan(String url,Map data) {
        return wrapper(mHttpAPI.firstScan(url,data).compose(SCHEDULERS_TRANSFORMER));
    }
    public Observable<PanDian> startPandian(Map data) {
        return wrapper(mHttpAPI.startPandian(data).compose(SCHEDULERS_TRANSFORMER));
    }
    public Observable<WeiPanDianListBean> weiPanDianList(Map data) {
        return wrapper(mHttpAPI.weiPanDianList(data).compose(SCHEDULERS_TRANSFORMER));
    }
    public Observable<PanDianDetail> getPandianDetail(String wuziBianhao, Map data) {
        return wrapper(mHttpAPI.getPandianDetail(wuziBianhao,data).compose(SCHEDULERS_TRANSFORMER));
    }
    public Observable<BaseBack> confirmPandian(Map data) {
        return wrapper(mHttpAPI.confirmPandian(data).compose(SCHEDULERS_TRANSFORMER));
    }
    //*************************************更新的接口*********************************//
    /**
     * 给任何Http的Observable加上通用的线程调度器
     */
    private static final ObservableTransformer SCHEDULERS_TRANSFORMER = new ObservableTransformer() {
        @Override
        public ObservableSource apply(@NonNull Observable upstream) {
            return upstream.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    };

    /**
     * 根据Http的response中关于状态码的描述，自定义生成本地的Exception
     *
     * @param resourceObservable
     * @param <T>
     * @return
     */
    private <T extends BaseBack> Observable<T> wrapper(Observable<T> resourceObservable) {
        return resourceObservable
                .flatMap(new Function<T, ObservableSource<? extends T>>() {
                    @Override
                    public ObservableSource<? extends T> apply(@NonNull final T baseResponse) throws Exception {
                        return Observable.create(
                                new ObservableOnSubscribe<T>() {
                                    @Override
                                    public void subscribe(@NonNull ObservableEmitter<T> e) throws Exception {
                                        if (baseResponse == null) {

                                        } else {
                                            e.onNext(baseResponse);
                                        }
                                    }
                                });
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable e) throws Exception {
                        e.printStackTrace();
                        if (e instanceof HttpException) {
                            HttpException exception = (HttpException) e;
                            ToastUtil.show(DemoApplicationLike.getInstance(), exception.getMessage());
                        } else if (e instanceof UnknownHostException) {
                            KLog.i("请打开网络");
                            ToastUtil.show(DemoApplicationLike.getInstance(), "请打开网络");
                        } else if (e instanceof SocketTimeoutException) {
                            KLog.i("请求超时");
                            ToastUtil.show(DemoApplicationLike.getInstance(), "请求超时");
                        } else if (e instanceof ConnectException) {
                            KLog.i("连接失败");
                            ToastUtil.show(DemoApplicationLike.getInstance(), "连接失败");
                        } else if (e instanceof HttpException) {
                            KLog.i("请求超时");
                            ToastUtil.show(DemoApplicationLike.getInstance(), "请求超时");
                        } else {
                            KLog.i("请求失败");
                            ToastUtil.show(DemoApplicationLike.getInstance(), "请求失败");
                        }
                    }
                });
    }

    /**
     * 根据Http的response中关于状态码的描述，自定义生成本地的Exception
     *
     * @param resourceObservable
     * @param <T>
     * @return
     */
    private <T extends String> Observable<T> wrapperObject(Observable<T> resourceObservable) {
        return resourceObservable
                .flatMap(new Function<T, ObservableSource<? extends T>>() {
                    @Override
                    public ObservableSource<? extends T> apply(@NonNull final T baseResponse) throws Exception {
                        return Observable.create(
                                new ObservableOnSubscribe<T>() {
                                    @Override
                                    public void subscribe(@NonNull ObservableEmitter<T> e) throws Exception {
                                        if (baseResponse == null) {

                                        } else {
                                            e.onNext(baseResponse);
                                        }
                                    }
                                });
                    }
                })
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable e) throws Exception {
                        e.printStackTrace();
                        if (e instanceof HttpException) {
                            HttpException exception = (HttpException) e;
                            ToastUtil.show(DemoApplicationLike.getInstance(), exception.getMessage());
                        } else if (e instanceof UnknownHostException) {
                            KLog.i("请打开网络");
                            ToastUtil.show(DemoApplicationLike.getInstance(), "请打开网络");
                        } else if (e instanceof SocketTimeoutException) {
                            KLog.i("请求超时");
                            ToastUtil.show(DemoApplicationLike.getInstance(), "请求超时");
                        } else if (e instanceof ConnectException) {
                            KLog.i("连接失败");
                            ToastUtil.show(DemoApplicationLike.getInstance(), "连接失败");
                        } else if (e instanceof HttpException) {
                            KLog.i("请求超时");
                            ToastUtil.show(DemoApplicationLike.getInstance(), "请求超时");
                        } else {
                            KLog.i("请求失败");
                            ToastUtil.show(DemoApplicationLike.getInstance(), "请求失败");
                        }
                    }
                });
    }

    //需要额外的添加其他的参数进去，所以把原有的参数和额外的参数通过这个方法一起添加进去.
    public static Map addParams(Map<String, String> data) {
        //添加统一的参数的地方
        return data;
    }

    //需要额外的添加其他的参数进去，所以把原有的参数和额外的参数通过这个方法一起添加进去.
    public static String addParams2String(Map<String, String> data) {
        //添加统一的参数的地方
        JsonObject js = new JsonObject();
        Iterator<Map.Entry<String, String>> it = data.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            js.addProperty(entry.getKey(), entry.getValue());
        }
        String temp = js.toString();
        byte[] encryptBytes = new byte[0];
        String encryStr = new String(encryptBytes);
        return encryStr;
    }

}
