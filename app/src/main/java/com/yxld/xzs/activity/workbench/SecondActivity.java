package com.yxld.xzs.activity.workbench;

import android.util.Log;

import com.alibaba.sdk.android.push.AndroidPopupActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

/**
 * 阿里云推送辅助弹窗指定通知点击后跳转的Activity,原本的通知回调onNotification()和onNotificationOpened()不适用于辅助弹窗
 * Created by William on 2018/1/12.
 */

public class SecondActivity extends AndroidPopupActivity {
    /**
     * 辅助弹窗指定打开Activity回调
     *
     * @param title   标题
     * @param content 内容
     * @param map     额外参数
     */
    @Override
    protected void onSysNoticeOpened(String title, String content, Map<String, String> map) {

        // TODO: 2018/1/12  这个activity应该直接跳具体的activity页面，需要和服务端约定推送来的消息内容与格式，要和普通状态下一致
        // https://help.aliyun.com/document_detail/30067.html?spm=5176.7740007.2.3.jZuL0U


        Log.d("wh", "Receive XiaoMi notification, title: " + title + ", content: " + content + "," +
                " extraMap: " + map);

        //从MyALYReceiver文件copy过来，此处推送声音没有实现
        String customs = map.get("custom");
        if (customs != null && "tongzhi".equals(customs)) {
            //收到新通知推送
            Log.d("geek", "onNotificationMessageArrived: customs=" + customs);
            EventBus.getDefault().post("通知");
        } else if (customs != null && "OUTlOGIN".equals(customs)) {
            //收到退出登录通知  移除别名
            Log.d("geek", "onNotificationMessageArrived: customs=" + customs);
            //EventBus.getDefault().post("退出登录");
        }
    }
}
