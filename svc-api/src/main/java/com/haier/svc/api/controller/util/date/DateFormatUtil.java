package com.haier.svc.api.controller.util.date;

import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DateFormatUtil {
    private static Map<String, ThreadLocal<SimpleDateFormat>> map = new HashMap<String, ThreadLocal<SimpleDateFormat>>();

    static {
        map.put("yyyy-MM-dd HH:mm:ss", new ThreadLocal<SimpleDateFormat>());
        map.put("yyyy-MM-dd", new ThreadLocal<SimpleDateFormat>());
        map.put("yyyyMMddHHmmss", new ThreadLocal<SimpleDateFormat>());
    }

    public static SimpleDateFormat getFormat(String type) {
        if (null == type) {
            type = "yyyy-MM-dd HH:mm:ss";
        }
        if (!map.containsKey(type)) {
            map.put(type, new ThreadLocal<SimpleDateFormat>());
        }
        ThreadLocal<SimpleDateFormat> fomatThreadLocal = map.get(type);
        if (null == fomatThreadLocal.get()) {
            fomatThreadLocal.set(new SimpleDateFormat(type));
        }
        return fomatThreadLocal.get();
    }

    public static String formatByType(String type, Date date) {
        if (null == date) {
            return null;
        }
        return getFormat(type).format(date);
    }

    public static String format(Date date) {
        return formatByType(null, date);
    }

    public static Date parseByType(String type, String dateStr) {
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }
        try {
            return getFormat(type).parse(dateStr);
        } catch (Exception e) {
        }
        return null;
    }

    public static Date parse(String dateStr) {
        return parseByType(null, dateStr);
    }


}
