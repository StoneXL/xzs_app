package com.yxld.xzs.http;

import com.socks.library.KLog;
import com.yxld.xzs.base.DemoApplicationLike;
import com.yxld.xzs.entity.BaseBack;
import com.yxld.xzs.http.api.API;
import com.yxld.xzs.utils.ToastUtil;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import retrofit2.adapter.rxjava.HttpException;

/**
 * 作者：hu on 2017/6/3
 * 邮箱：365941593@qq.com
 * 描述：
 */

public class HttpWrapper {
    private API api;

    public HttpWrapper () {
        this.api = api;
    }
//    public Observable<CxwyCommon> getCommonToken(LinkedHashMap data) {
//        return wrapper(api.getCommonToken(data)).compose(SCHEDULERS_TRANSFORMER);
//    }

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
                            ToastUtil.show(DemoApplicationLike.getInstance(),  "请求超时");
                        } else if (e instanceof ConnectException) {
                            KLog.i("连接失败");
                            ToastUtil.show(DemoApplicationLike.getInstance(), "连接失败");
                        } else if (e instanceof HttpException) {
                            KLog.i("请求超时");
                            ToastUtil.show(DemoApplicationLike.getInstance(), "请求超时");
                        }else {
                            KLog.i("请求失败");
                            ToastUtil.show(DemoApplicationLike.getInstance(), "请求失败");
                        }
                    }
                });
    }

    /**
     * 给任何Http的Observable加上通用的线程调度器
     */
    private static final ObservableTransformer SCHEDULERS_TRANSFORMER = new ObservableTransformer() {
        @Override
        public ObservableSource apply(@NonNull Observable upstream) {
            return upstream.subscribeOn(io.reactivex.schedulers.Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    };

}
