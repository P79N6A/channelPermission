package com.haier.afterSale.services;

import java.util.Calendar;

/**
 * 日期工具类
 *                       
 * @Filename: DateUtils.java
 * @Version: 1.0
 * @Author: yaoyu
 * @Email: yaoyu@ehaier.com
 *
 */
public class DateUtils {

    /**
     * 日期加指定天数
     * @param date
     * @param days
     */
    public static void addDays(Calendar date, int days) {
        date.add(Calendar.DAY_OF_YEAR, days);
    }

    /**
     * 日期加指定小时
     * @param date
     * @param hours
     */
    public static void addHours(Calendar date, int hours) {
        date.add(Calendar.HOUR_OF_DAY, hours);
    }

    /**
     * 日期设置为一天开始0时0分0秒
     * @param date
     */
    public static void setStartDay(Calendar date) {
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
    }

    /**
     * 日期设置为一天结束,23时59分59秒
     * @param date
     */
    public static void setEndDay(Calendar date) {
        date.set(Calendar.HOUR_OF_DAY, 23);
        date.set(Calendar.MINUTE, 59);
        date.set(Calendar.SECOND, 59);
    }

    /**
     * 设置18.7.1日
     * @param date
     */
    public static void setStartYear(Calendar date) {
        int year = date.get(Calendar.YEAR);
                year = 2018;
        date.set(year, 6, 1, 0, 0, 0);
        //        date.setTimeInMillis(0);
        //        date.set(Calendar.YEAR, year);
        //        date.set(Calendar.MONTH, 0);
        //        date.set(Calendar.DATE, 1);
        //        date.set(Calendar.HOUR, 0);
        //        date.set(Calendar.MINUTE, 0);
        //        date.set(Calendar.SECOND, 0);

    }
}
