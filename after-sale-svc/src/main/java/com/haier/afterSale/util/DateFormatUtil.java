package com.haier.afterSale.util;

import com.alibaba.dubbo.common.utils.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DateFormatUtil {
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


	private static Map<String, ThreadLocal<SimpleDateFormat>> map = new HashMap<String, ThreadLocal<SimpleDateFormat>>();
	private static org.apache.log4j.Logger                    log = org.apache.log4j.LogManager
			.getLogger(DateFormatUtil.class);

	static {
		map.put("yyyy-MM-dd HH:mm:ss", new ThreadLocal<SimpleDateFormat>());
		map.put("yyyy-MM-dd", new ThreadLocal<SimpleDateFormat>());
		map.put("yyyyMMddHHmmss", new ThreadLocal<SimpleDateFormat>());
		map.put("yyyyMMdd", new ThreadLocal<SimpleDateFormat>());
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
			log.error("日期格式化出错:", e);
		}
		return null;
	}

	public static Date parse(String dateStr) {
		return parseByType(null, dateStr);
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
