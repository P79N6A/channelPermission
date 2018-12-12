package com.haier.shop.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
* 作者:张波
* 2017/12/25
*/
public class DateFormatUtilNew {
    static String DEFAULT_PATTERN = "yyyy-MM-dd hh:mm:ss";

    public static String dateFormat(Date date, String pattern) {
        if (pattern == null)
            pattern = DEFAULT_PATTERN;
        DateFormat datef = new java.text.SimpleDateFormat(pattern);
        return datef.format(date);
    }

    public static String getCurrentDateWithFormat(String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }

    public static String getCurrentDateWithFormat(Date date,String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /**
     * 格式化整型类型时间
     * 由1342847622到2014-01-01 12:59:59
     */
    public static String formatTime(String type, Object time) {
        if (time == null) {
            return "";
        }
        if (null == type || type.equals("")) {
            type = "yyyy-MM-dd HH:mm:ss";
        }
        Long tempTime = 0L;
        if (time instanceof Integer)
            tempTime = ((Integer) time).longValue();
        else if (time instanceof Long)
            tempTime = ((Long) time);
        if (time instanceof String)
            try {
                tempTime = Long.parseLong(((String) time));
            } catch (NumberFormatException e) {
                return time.toString();
            }
        if (tempTime == 0)
            return tempTime.toString();
        Date date = new Date(tempTime * 1000);
        SimpleDateFormat format = new SimpleDateFormat(type);
        String str = format.format(date);
        return str;
    }

    public static String formatTime(Object time) {
        if (time == null) {
            return "";
        }
        Long tempTime = 0L;
        if (time instanceof Integer)
            tempTime = ((Integer) time).longValue();
        else if (time instanceof Long)
            tempTime = ((Long) time);
        if (time instanceof String)
            try {
                tempTime = Long.parseLong(((String) time));
            } catch (NumberFormatException e) {
                return time.toString();
            }
        if (tempTime == 0)
            return tempTime.toString();
        Date date = new Date(tempTime * 1000);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = format.format(date);
        return str;
    }
}
