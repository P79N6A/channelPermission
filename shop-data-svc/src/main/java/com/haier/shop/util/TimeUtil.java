package com.haier.shop.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {

    /**
     * 根据传入的时间字符串返回相应的Long时间戳
     * @param time
     * @return
     */
    public static long timeStrTOlong(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat(ReviewConstants.TIME.FORMAT_DATE);
        Date dt = null;
        try {
            dt = sdf.parse(time);
        } catch (ParseException e) {
            return 0L;
        }
        return dt.getTime();
    }

    /**
     * 根据传入的时间字符串返回相应的Long时间戳
     * @param time
     * @return
     */
    public static Date timeStrTOdate(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat(ReviewConstants.TIME.FORMAT_DATE);
        Date dt = null;
        try {
            dt = sdf.parse(time);
        } catch (ParseException e) {
            return dt;
        }
        return dt;
    }

    /**
     * 格式化时间
     * @param time 时间字符串 格式：yyyy-MM-dd HH:mm:ss
     * @return 1.表示今天 2.表示昨天 3.表示以前 0.表示错误
     */
    public static int formatDateTime(String time) {
        SimpleDateFormat format = new SimpleDateFormat(ReviewConstants.TIME.FORMAT_DATE);
        if (time == null || "".equals(time)) {
            return 0;
        }
        Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            return 0;
        }

        Calendar current = Calendar.getInstance();

        Calendar today = Calendar.getInstance(); //今天

        today.set(Calendar.YEAR, current.get(Calendar.YEAR));
        today.set(Calendar.MONTH, current.get(Calendar.MONTH));
        today.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH));
        //  Calendar.HOUR——12小时制的小时数 Calendar.HOUR_OF_DAY——24小时制的小时数
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);

        Calendar yesterday = Calendar.getInstance(); //昨天

        yesterday.set(Calendar.YEAR, current.get(Calendar.YEAR));
        yesterday.set(Calendar.MONTH, current.get(Calendar.MONTH));
        yesterday.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH) - 1);
        yesterday.set(Calendar.HOUR_OF_DAY, 0);
        yesterday.set(Calendar.MINUTE, 0);
        yesterday.set(Calendar.SECOND, 0);

        current.setTime(date);

        if (current.after(today)) {
            return 1;
        } else if (current.before(today) && current.after(yesterday)) {

            return 2;
        } else {
            //            int index = time.indexOf("-") + 1;
            return 3;
        }
    }

    /**
     * 获得指定某天某点时间(24小时至)
     * @param time 指定时间字符串(yyyy-MM-dd HH:mm:ss)
     * @param hour 指定小时
     * @return 返回给定天指定点的long值
     */
    public static long getAppointTime(String time, int hour) {
        SimpleDateFormat format = new SimpleDateFormat(ReviewConstants.TIME.FORMAT_DATE);
        Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            return 0L;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime().getTime();
    }

    /**
     * 获取两个时间段经过的时间
     * @param l
     * @return
     */
    public static String convertTimeByMillisecods(Long l) {
        long day = l / (24 * 60 * 60 * 1000);
        day = l / (24 * 60 * 60 * 1000);
        long hour = (l / (60 * 60 * 1000) - day * 24);
        long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
        //        long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        String r = "";
        if (day != 0) {
            r = Math.abs(day) + "天";
        }
        if (hour != 0) {
            r += Math.abs(hour) + "小时";
        }
        if (min != 0) {
            r += Math.abs(min) + "分";
        }
        //        if (s != 0) {
        //            r += s + "秒";
        //        }
        return r;
    }

    /**
     * 计算两个时间段跨越了几天(支持跨年)
     * @param startDate
     * @param endDate
     * @return
     */
    public static int getIntervalDays(Date startDate, Date endDate) {
        Calendar sCalendar = Calendar.getInstance();
        Calendar eCalendar = Calendar.getInstance();
        int day1 = 0;
        int day2 = 0;
        sCalendar.setTime(startDate);
        day1 = sCalendar.get(Calendar.DAY_OF_YEAR);
        System.out.println(day1);
        eCalendar.setTime(endDate);
        day2 = eCalendar.get(Calendar.DAY_OF_YEAR);
        System.out.println(day2);
        //支持跨年  
        //判断两个时间是否是同一年
        if (sCalendar.get(Calendar.YEAR) == eCalendar.get(Calendar.YEAR)) {
            return day2 - day1;
        } else {
            sCalendar.set(Calendar.MONTH, 11);
            sCalendar.set(Calendar.DATE, 31);
            //当前开始时间的年份中的最后一天是当前的第几天
            int dayEnd = sCalendar.get(Calendar.DAY_OF_YEAR);
            //当年最后一天 - 当年开始时间那天 + 结束年结束时间那天
            return dayEnd - day1 + day2;
        }
    }

}