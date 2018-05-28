package com.yxld.xzs.base;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.socks.library.KLog;
import com.yxld.xzs.activity.Login.LoginNewActivity;
import com.yxld.xzs.contain.Contains;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * 作者：yishangfei on 2017/1/6 0006 10:37
 * 邮箱：yishangfei@foxmail.com
 */
public abstract class BaseFragment extends Fragment {
    protected boolean isVisible;
    public SharedPreferences sp;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
    }

    /**
     * 在这里实现Fragment数据的缓加载.
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    protected void onVisible() {
        lazyLoad();
    }

    protected abstract void lazyLoad();

    protected void onInvisible() {
    }

    protected void onError(int status, String msg) {

        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();

        if (status == 99 || status == -99 || (status == -1 && !TextUtils.isEmpty(msg) && msg.contains("登录失效"))) {
            JPushInterface.setAlias(DemoApplicationLike.getApp(), "", new TagAliasCallback() {
                @Override
                public void gotResult(int i, String s, Set<String> set) {
                    Log.d("geek", "JPushInterface clearData  setAlias  gotResult: " + i);
                }
            });
            PushServiceFactory.getCloudPushService().removeAlias("", new CommonCallback() {
                @Override
                public void onSuccess(String s) {
                    KLog.i("aliyunpush removeAlias  gotResult: " + s);
                }

                @Override
                public void onFailed(String s, String s1) {

                }
            });
            PushServiceFactory.getCloudPushService().unbindAccount(new CommonCallback() {
                @Override
                public void onSuccess(String s) {
                    KLog.i("aliyunpush unbindAccount  gotResult: " + s);
                }

                @Override
                public void onFailed(String s, String s1) {

                }
            });
            Contains.jilu = null;
            Contains.appLogin = null;
            Contains.cvoListBean = null;
            Contains.cxwyCommons = null;
            Contains.uuid = "";
            SharedPreferences.Editor edit = sp.edit();
            edit.putBoolean("AUTO_ISCHECK", false).apply();
            edit.putString("NAME", "");
            edit.putString("PASSWORD", "");
            edit.commit();
            DemoApplicationLike.getApp().mAppActivityManager.finishAllActivity();
            Intent intent = new Intent(getActivity(), LoginNewActivity.class);
            this.startActivity(intent);
        }

    }
}