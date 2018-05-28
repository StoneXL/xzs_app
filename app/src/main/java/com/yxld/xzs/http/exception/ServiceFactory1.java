package com.yxld.xzs.http.exception;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.yxld.xzs.http.HttpService;
import com.yxld.xzs.http.api.API;
import com.yxld.xzs.http.api.HttpApi;
import com.yxld.xzs.http.api.RequestBodyInterceptor;
import com.yxld.xzs.utils.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 作者：yishangfei on 2016/12/31 0031 10:16
 * 邮箱：yishangfei@foxmail.com
 */
public class ServiceFactory1 {

    //      public static final String BASE_URL = "http://222.240.1.133/";
    public static final String BASE_URL = "http://120.25.78.92/";
    private static final int DEFAULT_TIMEOUT = 10;
    private static Retrofit sRetrefit;
    private static OkHttpClient sClient;

    static {
        sClient = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
//                .addInterceptor(new HeaderInterceptor())
//                .addInterceptor(new TokenInterceptor())
                .addNetworkInterceptor(
                        new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .retryOnConnectionFailure(true)
                .build();
        OkHttpUtils.initClient(sClient);

        sRetrefit = new Retrofit.Builder()
                .client(sClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public static <T> T createService(Class<T> serviceClazz) {

        return sRetrefit.create(serviceClazz);
    }

    /**
     * 创建
     *
     * @param baseUrl
     * @param serviceClazz
     * @param <T>
     * @return
     */
    public static <T> T createService(String baseUrl, Class<T> serviceClazz) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(sClient)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        return retrofit.create(serviceClazz);
    }

    /**
     * 创建
     *
     * @param serviceClazz
     * @param okHttpClient 外部传入自定义okhttp，如上传文件时加长timeout时间
     * @param <T>
     * @return
     */
    public static <T> T createService(Class<T> serviceClazz, OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        return retrofit.create(serviceClazz);
    }

    /**
     * 创建
     *
     * @param baseUrl
     * @param serviceClazz
     * @param okHttpClient
     * @param <T>
     * @return
     */
    public static <T> T createService(String baseUrl, Class<T> serviceClazz, OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        return retrofit.create(serviceClazz);
    }

    /**
     * 向外部提供api请求
     *
     * @return
     */
    public static HttpService httpService() {
        return ServiceFactory1.createService(HttpService.class);
    }

    /**
     * 向外部提供api请求
     *
     * @return
     */
    public static HttpApi httpApi() {
        return ServiceFactory1.provideRetrofit(provideOkHttpClient()).create(HttpApi.class);
    }

    public static OkHttpClient provideOkHttpClient() {
        final OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(logging);
        builder.addInterceptor(new ErrorHandlerInterceptor());
        builder.connectTimeout(API.CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(API.IO_TIMEOUT, TimeUnit.MILLISECONDS)
                .addInterceptor(new RequestBodyInterceptor());
        return builder.build();
    }

    public static Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.client(okHttpClient)
                .baseUrl(API.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create());
        return builder.build();
    }
}
