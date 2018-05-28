package com.yxld.xzs.base;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;

import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.alibaba.sdk.android.push.register.MiPushRegister;
import com.baidu.mapapi.SDKInitializer;
import com.p2p.core.P2PSpecial.P2PSpecial;
import com.socks.library.KLog;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.tinker.anno.DefaultLifeCycle;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.loader.app.ApplicationLike;
import com.tencent.tinker.loader.shareutil.ShareConstants;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;
import com.videogo.openapi.EZOpenSDK;
import com.yxld.xzs.utils.AppActivityManager;
import com.yxld.xzs.utils.swipeback.ActivityLifecycleHelper;

/**
 * Created by yishangfei on 2016/11/3 0003.
 * 1、为了打开客户端的日志，便于在开发过程中调试，需要自定义一个 Application。
 * 并将自定义的 application 注册在 AndroidManifest.xml 文件中。<br/>
 * 2、为了提高 push 的注册率，您可以在 Application 的 onCreate 中初始化 push。你也可以根据需要，在其他地方初始化 push。
 *
 * @author wangkuiwei
 */

//此处DefaultLifeCycle注解为Tinker提供用来隔离Application的方式,另一种为继承,推荐使用DefaultLifeCycle注解来隔离Application,
// 这种方式会编译自动生成Application（命名为DemoApplication？） 看文档http://www.tinkerpatch.com/或网友教程http://blog.csdn
// .net/l2show/article/details/53187548
@DefaultLifeCycle(application = "com.yxld.xzs.base.DemoApplication",
        flags = ShareConstants.TINKER_ENABLE_ALL,
        loadVerifyFlag = false)
public class DemoApplicationLike extends ApplicationLike {
    public static DemoApplicationLike instance;

    //摄像头
    public final static String APPID = "03971010c9bb73b58b565d1fd90e3d3d";
    public final static String APPToken =
            "eeacbbeb6aeeacbd2e7ffd59a3bbb3cce16646f4431c80daca66890b3e5b3131";
    public final static String APPVersion = "05.14";
    public AppActivityManager mAppActivityManager;

    public DemoApplicationLike(Application application, int tinkerFlags, boolean
            tinkerLoadVerifyFlag, long applicationStartElapsedTime, long
                                       applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime,
                applicationStartMillisTime, tinkerResultIntent);
    }

    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        MultiDex.install(base);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //极光推送 // TODO: 2017/11/16 取消激光推送
//        JPushInterface.setDebugMode(true);
//        JPushInterface.init(getApplication().getApplicationContext());
        CrashReport.initCrashReport(getInstance(), "cb64c61ed6", true);//bugly的key要换
        //FIR 更新  Bug检测
        // FIR.init(getApplication());
//        UpdateKey.API_TOKEN = "29e937a851bc867e8932c84ef85db3df";
//        UpdateKey.APP_ID = "59352a1fca87a85afb000a64";
//        UpdateKey.API_TOKEN = "c6ea272a501b5db3dd57bc7bacf8a4ea";
//        UpdateKey.APP_ID = "59352a1fca87a85afb000a64";
//        //下载方式:
//        UpdateKey.DialogOrNotification = UpdateKey.WITH_DIALOG;//通过Dialog来进行下载
        //微信tinker
        TinkerInstaller.install(this);

//        KLog.init(BuildConfig.LOG_DEBUG);
        KLog.init(true);
        SDKInitializer.initialize(getApplication().getApplicationContext());
        mAppActivityManager = new AppActivityManager(getApplication());
        MultiDex.install(getInstance());
        //阿里云推送
        initCloudChannel(getApplication().getApplicationContext());
        //滑动返回
        getApplication().registerActivityLifecycleCallbacks(ActivityLifecycleHelper.build());

        //初始化二维码扫描
        ZXingLibrary.initDisplayOpinion(this.getApplication());
    }

    public void initSDK() {
        initP2P();
        initCommon();
    }

    //公共安防
    public static String AppKey = "13edae3069574e6bad0c49474b9344da";

    public static EZOpenSDK getOpenSDK() {
        return EZOpenSDK.getInstance();
    }

    public static Context getInstance() {
        return instance.getApplication().getApplicationContext();
    }

    public static DemoApplicationLike getApp() {
        return instance;
    }

    private void initCommon() {
        /**
         * sdk日志开关，正式发布需要去掉
         */
//			EZOpenSDK.showSDKLog(true);

        /**
         * 设置是否支持P2P取流,详见api
         */
        EZOpenSDK.enableP2P(false);

        /**
         * APP_KEY请替换成自己申请的
         */
        EZOpenSDK.initLib(instance.getApplication(), AppKey, "");
    }

    private void initP2P() {
        P2PSpecial.getInstance().init(getInstance(), APPID, APPToken, APPVersion);
    }

    /**
     * 初始化云推送通道
     *
     * @param applicationContext
     */
    private void initCloudChannel(Context applicationContext) {
        PushServiceFactory.init(applicationContext);
        CloudPushService pushService = PushServiceFactory.getCloudPushService();
        pushService.register(applicationContext, new CommonCallback() {
            @Override
            public void onSuccess(String response) {
                PushServiceFactory.getCloudPushService().onAppStart();
                KLog.i("初始化阿里云成功：" + response + PushServiceFactory.getCloudPushService()
                        .getDeviceId());
            }

            @Override
            public void onFailed(String errorCode, String errorMessage) {
                KLog.i("初始化阿里云失败：" + errorMessage);
            }
        });

        // 注册方法会自动判断是否支持小米系统推送，如不支持会跳过注册。
        //这里的AppID和Appkey要放在Contains文件中
        MiPushRegister.register(applicationContext, "2882303761517493512", "5711749339512");
        // 注册方法会自动判断是否支持华为系统推送，如不支持会跳过注册。
        //华为的应用还没通过
//        HuaWeiRegister.register(applicationContext);

        // TODO: 2018/1/12 推送的时候设置离线消息保存：pushRequest.setStoreOffline(true);//离线消息是否保存。若保存，
        // TODO: 2018/1/12 在推送时候，用户即使不在线，下一次上线则会收到，与expirationTime参数配合使用  由服务端配置
    }
}