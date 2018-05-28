package com.yxld.xzs.utils;

import com.socks.library.KLog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by hu on 2017/5/5.
 */

public class TimeUtil {
    /**
     * 调用此方法输入所要转换的时间戳输入例如（1402733340）输出（"2014年06月14日16时09分00秒"）
     *
     * @param time
     * @return
     */
    public static String timesTamp2Year(long time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        @SuppressWarnings("unused")
        String times = sdr.format(new Date(time));
        return times;
    }
    /**
     * 调用此方法输入所要转换的时间戳输入例如（1402733340）输出（"2014年06月14日16时09分00秒"）
     *
     * @param time
     * @return
     */
    public static String timesTamp2YearMonthDay(long time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd");
        @SuppressWarnings("unused")
        String times = sdr.format(new Date(time));
        return times;
    }

    /**
     * 调此方法输入所要转换的时间输入例如（"2014-06-14 16:09:00"）返回时间戳
     *
     * @param time
     * @return
     */
    public static long timeStamp(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        Date date;
        KLog.i(time);
        String times = null;
        try {
            date = sdr.parse(time);
            long l = date.getTime();
            KLog.i(l);
            return l;
        } catch (Exception e) {
            e.printStackTrace();
            return new Long("123");
        }
    }

    public static String MMdd(long timestamp) {
        SimpleDateFormat sdr = new SimpleDateFormat("MM月dd日",Locale.CHINA);
        @SuppressWarnings("unused")
        String times = sdr.format(new Date(timestamp));
        return times;
    }

    public static String HHmm(long timestamp) {
        SimpleDateFormat sdr = new SimpleDateFormat("HH:mm",Locale.CHINA);
        @SuppressWarnings("unused")
        String times = sdr.format(new Date(timestamp));
        return times;
    }
}
