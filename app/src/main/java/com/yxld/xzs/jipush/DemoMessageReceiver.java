package com.yxld.xzs.jipush;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;

import com.socks.library.KLog;
import com.yxld.xzs.MainActivity;
import com.yxld.xzs.R;
import com.yxld.xzs.activity.Navigation.ApplyBuyActivity;
import com.yxld.xzs.activity.Navigation.ApplyRepairActivity;
import com.yxld.xzs.activity.Navigation.ApplyScrapActivity;
import com.yxld.xzs.activity.Navigation.TicketApplyActivity;
import com.yxld.xzs.activity.patrol.PatrolManagerActivity;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;

/**
 * 1、PushMessageReceiver 是个抽象类，该类继承了 BroadcastReceiver。<br/>
 * 2、需要将自定义的 DemoMessageReceiver 注册在 AndroidManifest.xml 文件中：
 * <pre>
 * {@code
 *  <receiver
 *      android:name="com.xiaomi.mipushdemo.DemoMessageReceiver"
 *      android:exported="true">
 *      <intent-filter>
 *          <action android:name="com.xiaomi.mipush.RECEIVE_MESSAGE" />
 *      </intent-filter>
 *      <intent-filter>
 *          <action android:name="com.xiaomi.mipush.MESSAGE_ARRIVED" />
 *      </intent-filter>
 *      <intent-filter>
 *          <action android:name="com.xiaomi.mipush.ERROR" />
 *      </intent-filter>
 *  </receiver>
 *  }</pre>
 * 3、DemoMessageReceiver 的 onReceivePassThroughMessage 方法用来接收服务器向客户端发送的透传消息。<br/>
 * 4、DemoMessageReceiver 的 onNotificationMessageClicked 方法用来接收服务器向客户端发送的通知消息，
 * 这个回调方法会在用户手动点击通知后触发。<br/>
 * 5、DemoMessageReceiver 的 onNotificationMessageArrived 方法用来接收服务器向客户端发送的通知消息，
 * 这个回调方法是在通知消息到达客户端时触发。另外应用在前台时不弹出通知的通知消息到达客户端也会触发这个回调函数。<br/>
 * 6、DemoMessageReceiver 的 onCommandResult 方法用来接收客户端向服务器发送命令后的响应结果。<br/>
 * 7、DemoMessageReceiver 的 onReceiveRegisterResult 方法用来接收客户端向服务器发送注册命令后的响应结果。<br/>
 * 8、以上这些方法运行在非 UI 线程中。
 *
 * @author mayixiang
 */
public class DemoMessageReceiver extends BroadcastReceiver {
    private static final String TAG = "jiguang";

    private NotificationManager notificationManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        KLog.i("接受到了极光推送的消息");
        if (null == notificationManager) {
            notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }

        Bundle bundle = intent.getExtras();
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            KLog.i(TAG, "JPush用户注册成功");

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            KLog.i(TAG, "接受到推送下来的自定义消息");
            processCustomMessage(context, bundle);
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            KLog.i(TAG, "接受到推送下来的通知");
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            KLog.i(TAG, "用户点击打开了通知");
        } else {
            KLog.i(TAG, "Unhandled intent - " + intent.getAction());
        }
    }

    /**
     * 实现自定义推送声音
     *
     * @param context
     * @param bundle
     */

    private void processCustomMessage(Context context, Bundle bundle) {
        NotificationCompat.Builder notification = new NotificationCompat.Builder(context);

        String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        KLog.i(message);
        KLog.i(extras);

        notification.setAutoCancel(true)
                .setContentText(message)
                .setContentTitle("欣助手")
                .setSmallIcon(R.mipmap.icon_launcher);
        Intent intent = new Intent();
        if (!TextUtils.isEmpty(extras)) {
            try {
                JSONObject extraJson = new JSONObject(extras);
                if (null != extraJson && extraJson.length() > 0) {
                    String sound = extraJson.getString("custom");
                    switch (sound) {
                        case "RING_XIADAN"://下单铃声
                            EventBus.getDefault().post("来单了");
                            notification.setSound(Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.shaking));
                            break;
                        case "RING_YICHULI"://已处理铃声
                            break;
                        case "RING_DAICHULI"://待处理铃声
                            notification.setSound(Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.shaking1));
                            break;
                        case "RING_NEWFUZEREN"://新负责人铃声
                            break;
                        case "RING_ZHIBANJIESHU"://值班结束铃声
                            break;
                        case "apply":            //申购
                            intent.setClass(context, ApplyBuyActivity.class);
                            break;
                        case "baoxiu":           //报修
                            intent.setClass(context, ApplyRepairActivity.class);
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
                        default:
                            Uri ringUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                            notification.setSound(ringUri);
                            intent.setClass(context, MainActivity.class);
                            break;
                    }
                }
            } catch (JSONException e) {

            }

        }
        intent.putExtras(bundle);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        notification.setContentIntent(pendingIntent);
        notificationManager.notify(2, notification.build());
    }
}