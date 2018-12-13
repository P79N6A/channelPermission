package com.haier.svc.api.controller.util.http.jingdong;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * 日期处理类
 * @FileName:DataUtils.java
 * @Version: 1.0
 * @Author: liulianghui
 * @Author: 641899873@qq.com
 * @CreateDate: 2014年11月4日 下午2:17:47
 */
public class DateUtils {

	
	/**
	 * 格式化日期格式为：yyyy-MM-dd HH:mm:ss
	 * @param date
	 * @return
	 */
	public static Date parseDate(String date){
		return DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").parseDateTime(date).toDate();
	}
	
	/**
	 * 自定义格式化日期
	 * @param pattern
	 * @param date
	 * @return
	 */
	public static Date parseDate(String pattern,String date){ 
		return DateTimeFormat.forPattern(pattern).parseDateTime(date).toDate();
	}
	
	/**
	 * 格式化为:yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static Date parseCurrentDate(){
		return new DateTime(new Date()).toDate();
	}
	
	/**
	 *自定义格式化
	 * @return
	 */
	public static String formatDate(String pattern,Date date){
		return new SimpleDateFormat(pattern).format(date);
	}
	
	/**
	 *格式化为"yyyy-MM-dd HH:mm:ss"
	 * @return
	 */
	public static String formatDate(Date date){
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}
	
	
	 /**
     * 把yyyy-MM-dd HH:mm:ss 转换为ISO8601的时间，格式为'yyyy-MM-dd'T'HH:mm:ss.SSS'Z''
     * @return
     */
/*	public static String formatISO8601Timestamp(String date){
		  SimpleDateFormat df = ISO8601SimpleDateFormat();
	      return df.format(parseDate(date));
	}*/ 
	
	 /**
     * 把yyyy-MM-dd HH:mm:ss 转换为UTF的时间，格式为'yyyy-MM-dd'T'HH:mm:ss.SSS'Z''
     * @return
     */
	public static String formatUTFTimestamp(String date){
		  SimpleDateFormat df = UTFSimpleDateFormat();
	      return df.format(parseDate(date));
	}
	
	 /**
     * 把yyyy-MM-dd HH:mm:ss 转换为MGT
     * @return
     */
	public static String formatGMTTimestamp(String date){
		  SimpleDateFormat df = GMTSimpleDateFormat();
	      return df.format(parseDate(date));
	}
	
	 /**
     * 把ISO8601的时间，格式为'yyyy-MM-dd'T'HH:mm:ss.SSS'Z'转换为'yyyy-MM-dd HH:mm:ss 
     * @return
     */
	public static String formatTimestamp(String iso8601Date){
	  if(iso8601Date.contains("%")){
			  iso8601Date=iso8601Date.replace("%3A", ":").replace("T"," ").replace("Z", "");
			  iso8601Date=iso8601Date.substring(0,iso8601Date.lastIndexOf("."));
	  }else if(iso8601Date.contains("Z")){
    	  iso8601Date=iso8601Date.replace("T"," ").replace("Z", "");
    	  DateTime dateTime= new DateTime(parseDate(iso8601Date));  
    	  iso8601Date=dateTime.plusHours(8).toString("yyyy-MM-dd HH:mm:ss");
      } 
		 return iso8601Date;
	}

    /**
     * 获取ISO8601的当前时间，格式为'yyyy-MM-dd'T'HH:mm:ss.SSS'Z''
     * @return
     */
	public static String getFormatISO8601Timestamp() {
	        SimpleDateFormat df = ISO8601SimpleDateFormat();
	        return df.format(new Date());
	  }
	
	private static SimpleDateFormat GMTSimpleDateFormat() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	     df.setTimeZone(TimeZone.getTimeZone("GMT+8")); 
		return df;
	}
	
	private static SimpleDateFormat ISO8601SimpleDateFormat() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	     df.setTimeZone(TimeZone.getTimeZone("GMT")); 
		 return df;
	}
	
	private static SimpleDateFormat UTFSimpleDateFormat() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
	     df.setTimeZone(TimeZone.getTimeZone("UTF")); 
		return df;
	}
	
	public static int dateDiff(Date d1, Date d2) throws Exception {
		long n1 = d1.getTime();
		long n2 = d2.getTime();
		long diff = Math.abs(n1 - n2);

		diff /= 3600 * 1000 * 24;
		return (int) diff;
	}
}
