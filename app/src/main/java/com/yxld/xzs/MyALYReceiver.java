package com.yxld.xzs;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.sdk.android.push.MessageReceiver;
import com.alibaba.sdk.android.push.notification.CPushMessage;
import com.socks.library.KLog;
import com.yxld.xzs.activity.Navigation.ApplyBuyActivity;
import com.yxld.xzs.activity.Navigation.ApplyScrapActivity;
import com.yxld.xzs.activity.Navigation.TicketApplyActivity;
import com.yxld.xzs.activity.Repair.RepairActivity;
import com.yxld.xzs.activity.index.HomeActivity;
import com.yxld.xzs.activity.patrol.PatrolManagerActivity;
import com.yxld.xzs.activity.workbench.SuggestActivity;
import com.yxld.xzs.utils.SPUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * @author Yuan.Y.Q
 * @Date 2017/9/12.
 */

public class MyALYReceiver extends MessageReceiver {
    // 消息接收部分的LOG_TAG
    public static final String REC_TAG = "AL_PUSH";
    private NotificationManager notificationManager;

    @Override
    public void onNotification(Context context, String title, String summary, Map<String, String> extraMap) {
        // TODO 处理推送通知
        KLog.i("收到通知 title: " + title + ", summary: " + summary + ", extraMap: " + extraMap);
        receivingNotification(context, title, summary, extraMap);

    }

    @Override
    public void onMessage(Context context, CPushMessage cPushMessage) {
        if (null == notificationManager) {
            notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }
        processCustomMessage(context, cPushMessage.getTitle(), cPushMessage.getContent());
        KLog.i("阿里收到消息 messageId: " + cPushMessage.getMessageId() + ", title: " + cPushMessage.getTitle() + ", content:" + cPushMessage.getContent());
    }

    @Override
    public void onNotificationOpened(Context context, String title, String summary, String extraMap) {
        KLog.i("打开通知 title: " + title + ", summary: " + summary + ", extraMap:" + extraMap);
    }

    @Override
    protected void onNotificationClickedWithNoAction(Context context, String title, String summary, String extraMap) {
        KLog.i("onNotificationClickedWithNoAction, title: " + title + ", summary: " + summary + ", extraMap:" + extraMap);
    }

    @Override
    protected void onNotificationReceivedInApp(Context context, String title, String summary, Map<String, String> extraMap, int openType, String openActivity, String openUrl) {
        KLog.i("onNotificationReceivedInApp, title: " + title + ", summary: " + summary + ", extraMap:" + extraMap + ", openType:" + openType + ", openActivity:" + openActivity + ", openUrl:" + openUrl);
    }

    @Override
    protected void onNotificationRemoved(Context context, String messageId) {
        KLog.i("onNotificationRemoved" + messageId);
    }

    private void receivingNotification(Context context, String title, String summary, Map<String, String> extraMap) {
//        KLog.i(TAG, " title : " + title);
//        KLog.i(TAG, "message : " + summary);
//        KLog.i(TAG, "extras : " + extraMap);
//        KLog.i("title : " + title);
//        KLog.i("message : " + summary);
//        KLog.i("extras : " + extraMap);
//        Log.d("geek", "openNotification: extras" + extraMap.toString());
//        Map<String, String> map = null;
//        try {
//            JSONObject jsonObject = new JSONObject(extraMap);
//            map = JSONUtils.toMap(jsonObject);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        if (map == null) {
//            return;
//        }
        String customs = extraMap.get("custom");
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

    /**
     * 实现自定义推送声音
     *
     * @param context
     */

    private void processCustomMessage(Context context, String title, String content) {
        //自定义消息格式
        String[] split = content.split("custom=");
        KLog.i("content:" + split[0] + "custom:" + split[1]);
        String messeage = split[0];
        String custom = split[1];
//        if (!TextUtils.isEmpty(custom) && "OUTlOGIN".equals(custom)) {
//            return;
//        }
        KLog.i("消息自定义通知");
        NotificationCompat.Builder notification = new NotificationCompat.Builder(context);
        notification.setAutoCancel(true)
                .setContentText(messeage)
                .setContentTitle(title)
                .setSmallIcon(R.mipmap.icon_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.icon_launcher));
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (!TextUtils.isEmpty(custom)) {
            switch (custom) {
                case "order"://下单铃声
                    EventBus.getDefault().post("刷新数据");
                    notification.setSound(Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.shaking));
                    intent.setClass(context, HomeActivity.class);
                    break;
                case "RING_YICHULI"://已处理铃声
                    break;
                case "RING_DAICHULI"://待处理铃声
                    EventBus.getDefault().post("刷新数据");
                    notification.setSound(Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.shaking1));
                    intent.setClass(context, HomeActivity.class);
                    break;
                case "RING_NEWFUZEREN"://新负责人铃声
                    break;
                case "RING_ZHIBANJIESHU"://值班结束铃声
                    break;
                case "apply":            //申购
                    intent.setClass(context, ApplyBuyActivity.class);
                    break;
                case "baoxiu":           //报修
                    notification.setSound(Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.xinweixiudan));
                    intent.setClass(context, RepairActivity.class);
                    SPUtil spUtil = new SPUtil(context);
                    spUtil.put(SPUtil.KEY_BAOXIU,0);
                    break;
                case "baofei":            //报废
                    intent.setClass(context, ApplyScrapActivity.class);
                    break;
                case "dianziquan":        //电子券
                    intent.setClass(context, TicketApplyActivity.class);
                    break;
                case "zhuangxiu":         //装修
                    break;
                case "tousu":              //投诉
                    notification.setSound(Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.xintousu));
                    intent.setClass(context, SuggestActivity.class);
                    break;
                case "malltousu":         //商城投诉
                    break;
                case "xungeng":
                    notification.setSound(Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.show_your_skill));
                    intent.setClass(context, PatrolManagerActivity.class);
                    intent.putExtra(PatrolManagerActivity.KEY_PAGE, 0);
                    break;
                case "xungeng_jilu_plan": //收到班组记录
                    notification.setSound(Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.now_its_turn_2_me));
                    intent.setClass(context, PatrolManagerActivity.class);
                    intent.putExtra(PatrolManagerActivity.KEY_PAGE, 4);
                    break;
                case "xungeng_jilu_start":
                    notification.setSound(Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.gaichujile));
                    intent.setClass(context, PatrolManagerActivity.class);
                    intent.putExtra(PatrolManagerActivity.KEY_PAGE, 0);
                    break;
                case "OUTlOGIN":
                    KLog.i("阿里退出登录");
                    EventBus.getDefault().post("退出登录");
                    break;
                default:
                    Uri ringUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    notification.setSound(ringUri);
                    intent.setClass(context, HomeActivity.class);
                    break;
            }


        }
        //设置界面设置声音震动的开关
        SharedPreferences sp = context.getSharedPreferences("ls", MODE_PRIVATE);
        boolean shy = sp.getBoolean("rememberPass", true);
        SharedPreferences sp1 = context.getSharedPreferences("zd", MODE_PRIVATE);
        boolean zhd = sp1.getBoolean("rememberPass", true);
        notification.setDefaults(Notification.DEFAULT_VIBRATE);
        KLog.i("阿里声音开关" + shy + "震动开关" + zhd);
        if (!shy) {
            KLog.i("关闭声音");
            notification.setSound(null);
        }
        if (!zhd) {
            KLog.i("关闭震动");
            notification.setVibrate(new long[]{0});
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        notification.setContentIntent(pendingIntent);
        notificationManager.notify(2, notification.build());
    }

}