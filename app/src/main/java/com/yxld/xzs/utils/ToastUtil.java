package com.yxld.xzs.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

/**
 * 提示工具类
 *
 * @author lijing
 */
public class ToastUtil {

    private static Toast longToast;
    private static Toast shortToast;
    private Toast customDurationToast;
    private static Toast toast;
    private static Runnable run = new Runnable() {
        public void run() {
            toast.cancel();
        }
    };
    private static Handler hdl;


    public static void show(final Context context, final String info) {
        if (hdl == null) {
            hdl = new Handler(context.getApplicationContext().getMainLooper()) {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    if (toast != null) {
                        toast.cancel();
                    }
                    toast = Toast.makeText(context, info, Toast.LENGTH_SHORT);
                    toast.show();
                }
            };
        }
        hdl.sendEmptyMessage(0);
    }

    public static void showInfo(Context context, String info) {
        if (toast != null) {
            toast.cancel();
            toast = Toast.makeText(context, info, Toast.LENGTH_SHORT);
            toast.show();
        } else {
            toast = Toast.makeText(context, info, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public static void showToast(Context context, String info) {
        Toast.makeText(context, info, Toast.LENGTH_SHORT).show();
    }
}
