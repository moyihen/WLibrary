package com.moyihen.wutils;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 创建日期：2021/3/18 17:05
 *
 * @author moyihen
 * 包名： com.moyihen.facecomparephone.utils
 * 类说明：
 */
@SuppressLint("SimpleDateFormat")
public class TimeUtils {
    /**
     * SimpleDateFormat函数语法：
     * G 年代标志符
     * y 年
     * M 月
     * d 日
     * h 时 在上午或下午 (1~12)
     * H 时 在一天中 (0~23)
     * m 分
     * s 秒
     * S 毫秒
     * E 星期
     * D 一年中的第几天
     * F 一月中第几个星期几
     * w 一年中第几个星期
     * W 一月中第几个星期
     * a 上午 / 下午 标记符
     * k 时 在一天中 (1~24)
     * K 时 在上午或下午 (0~11)
     * z 时区
     */
    public static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat(
            "HH:mm:ss");
    public static final SimpleDateFormat DEFAULT_MIN_FORMAT = new SimpleDateFormat(
            "mm:ss");
    public static final SimpleDateFormat DATE_FORMAT_DATE = new SimpleDateFormat(
            "yyyy/MM/dd");
    public static final SimpleDateFormat DEL_FORMAT_DATE = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");

    public static final SimpleDateFormat INT_HOUR_FORMAT = new SimpleDateFormat(
            "HH");

    public static final SimpleDateFormat CUSTOM_FORMAT = new SimpleDateFormat(
            "yyyyMMdd");
    public static final SimpleDateFormat CUSTOM_FORMAT2 = new SimpleDateFormat(
            "yyyy.MM.dd");
    private TimeUtils() {
        throw new AssertionError();
    }

    /**
     * long time to int
     *
     * @param timeInMillis
     * @param dateFormat
     * @return
     */
    public static int getCurrentHourInInt(long timeInMillis, SimpleDateFormat dateFormat) {
        String date = dateFormat.format(new Date(timeInMillis));
        int time = 0;
        if (!TextUtils.isEmpty(date)) {
            time = Integer.parseInt(date);
        }
        return time;
    }

    /**
     * long time to string
     *
     * @param timeInMillis
     * @param dateFormat
     * @return
     */
    public static String getTime(long timeInMillis, SimpleDateFormat dateFormat) {
        return dateFormat.format(new Date(timeInMillis));
    }

    /**
     * long time to int
     *
     * @param timeInMillis
     * @param dateFormat
     * @return
     */
    public static long getCurrentDateInLong(long timeInMillis,
                                            SimpleDateFormat dateFormat) {
        String date = dateFormat.format(new Date(timeInMillis));
        long time = 0;
        if (!TextUtils.isEmpty(date)) {
            time = Long.parseLong(date);
        }
        return time;
    }

    /**
     * long time to string, format is {@link #DEFAULT_DATE_FORMAT}
     *
     * @param timeInMillis
     * @return
     */
    public static String getTime(long timeInMillis) {
        return getTime(timeInMillis, DEFAULT_DATE_FORMAT);
    }

    /**
     * get current time in milliseconds
     *
     * @return
     */
    public static long getCurrentTimeInLong() {
        return System.currentTimeMillis();
    }

    /**
     * get current time in milliseconds, format is {@link #DEFAULT_DATE_FORMAT}
     *
     * @return
     */
    public static String getCurrentTimeInString() {
        return getTime(getCurrentTimeInLong());
    }

    /**
     * get current time in milliseconds
     *
     * @return
     */
    public static String getCurrentTimeInString(SimpleDateFormat dateFormat) {
        return getTime(getCurrentTimeInLong(), dateFormat);
    }


    /**
     * @param str         日期
     * @param dateFormat1 转换之后格式
     * @param dateFormat2 转换之后格式
     * @return 。
     */
    public static String DateConvert(String str, SimpleDateFormat dateFormat1, SimpleDateFormat dateFormat2) {
        String format = "-1";
        try {
            Date date = dateFormat1.parse(str);
            format = dateFormat2.format(date);
            return format;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return format;
    }

    public static boolean isTime(String str) {

        try {
            Date parse = DATE_FORMAT_DATE.parse(str);
        } catch (ParseException e) {
           // e.printStackTrace();
            return false;
        }
        return true;

    }
}
