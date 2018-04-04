package com.yueyue.readhub.common.utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * author : yueyue on 2018/4/3 10:15
 * desc   :
 */
public class TimeUtil {

    private TimeUtil() {
    }

    /**
     * yyyy-MM-dd HH:mm:ss
     */
    @SuppressLint("SimpleDateFormat")
    public static String getNowYMDHMSTime() {
        SimpleDateFormat mDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        return mDateFormat.format(new Date());
    }

    /**
     * MM-dd HH:mm:ss
     */
    @SuppressLint("SimpleDateFormat")
    public static String getNowMDHMSTime() {
        SimpleDateFormat mDateFormat = new SimpleDateFormat(
                "MM-dd HH:mm:ss");
        return mDateFormat.format(new Date());
    }

    /**
     * MM-dd
     */
    @SuppressLint("SimpleDateFormat")
    public static String getNowYMD() {

        SimpleDateFormat mDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd");
        return mDateFormat.format(new Date());
    }

    /**
     * yyyy-MM-dd
     */
    @SuppressLint("SimpleDateFormat")
    public static String getYMD(Date date) {

        SimpleDateFormat mDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd");
        return mDateFormat.format(date);
    }

    @SuppressLint("SimpleDateFormat")
    public static String getMD(Date date) {

        SimpleDateFormat mDateFormat = new SimpleDateFormat(
                "MM-dd");
        return mDateFormat.format(date);
    }

    /**
     * 判断当前日期是星期几
     *
     * @param pTime 修要判断的时间
     * @return dayForWeek 判断结果
     * @Exception 发生异常
     */
    public static String dayForWeek(String pTime) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(format.parse(pTime));
        int dayForWeek;
        String week = "";
        dayForWeek = c.get(Calendar.DAY_OF_WEEK);
        switch (dayForWeek) {
            case 1:
                week = "星期日";
                break;
            case 2:
                week = "星期一";
                break;
            case 3:
                week = "星期二";
                break;
            case 4:
                week = "星期三";
                break;
            case 5:
                week = "星期四";
                break;
            case 6:
                week = "星期五";
                break;
            case 7:
                week = "星期六";
                break;
        }
        return week;
    }

    public static Long getTimeStamp(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.CHINA);
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            return format.parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0L;
        }
    }

    public static String countDown(String dateString) {
        if (TextUtils.isEmpty(dateString)) return "";
        Date date;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.CHINA);
            format.setTimeZone(TimeZone.getTimeZone("UTC"));
            date = format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
        String interval;
        interval = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA).format(date);

        if (TextUtils.isEmpty(dateString)) return interval;
        long currentTimeMillis = System.currentTimeMillis(), dateTimeMillis = date.getTime();
        Calendar currCalendar = Calendar.getInstance();//当前日期零点
        currCalendar.setTimeInMillis(currentTimeMillis);
        currCalendar.set(Calendar.HOUR_OF_DAY, 0);
        currCalendar.set(Calendar.MINUTE, 0);
        currCalendar.set(Calendar.SECOND, 0);
        currCalendar.set(Calendar.MILLISECOND, 0);
        long countdownTime = currentTimeMillis - dateTimeMillis;
        if (countdownTime < 0) return interval;//传入时间早于当前时间
        if (countdownTime / 1000 < 10) {
            interval = "刚刚";
        } else if (countdownTime / 3600000 >= 24) {
            Calendar dateCalendar = Calendar.getInstance();//传入日期零点
            dateCalendar.setTimeInMillis(dateTimeMillis);
            dateCalendar.set(Calendar.HOUR_OF_DAY, 0);
            dateCalendar.set(Calendar.MINUTE, 0);
            dateCalendar.set(Calendar.SECOND, 0);
            dateCalendar.set(Calendar.MILLISECOND, 0);
            int day =
                    (int) ((currCalendar.getTimeInMillis() - dateCalendar.getTimeInMillis()) / 86400000);
            if (day == 1) {
                interval = "昨天";
            } else if (day > 1) {
                interval = day + "天前";
            }
        } else if (countdownTime / 3600000 < 24 && countdownTime / 3600000 > 0) {
            int hour = (int) (countdownTime / 3600000);
            interval = hour + "小时前";
        } else if (countdownTime / 60000 < 60 && countdownTime / 60000 > 0) {
            int minute = (int) (countdownTime / 60000);
            interval = minute + "分钟前";
        } else if (countdownTime / 1000 < 60 && countdownTime / 1000 > 0) {
            int second = (int) (countdownTime / 1000);
            interval = second + "秒前";
        }
        return interval;
    }

    public static String getFormatDate(String dateString) {
        if (TextUtils.isEmpty(dateString)) return "";
        Date date;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.CHINA);
            format.setTimeZone(TimeZone.getTimeZone("UTC"));
            date = format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
        return new SimpleDateFormat("yyyy年MM月dd日 HH:mm", Locale.CHINA).format(date);
    }
}
