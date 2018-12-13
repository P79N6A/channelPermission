package com.haier.vehicle.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.boot.SpringApplication;

import com.haier.vehicle.VehicleApplication;

/**
 * 工具类
 *
 * @author zzb
 * @create 2017-09-18 9:27
 **/
public class DateUtil {

    public static Date getArrivalDate(int dateNum) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, cal.get(Calendar.DATE) + dateNum);
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        cal.setFirstDayOfWeek(Calendar.SUNDAY);//设置一个星期的第一天
        int day = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);//根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, 5);
        if (w < 0)
            w = 0;
        if (!("星期一".equals(weekDays[w]) || "星期二".equals(weekDays[w]) || "星期三".equals(weekDays[w]))) {
            cal.set(Calendar.DATE, cal.get(Calendar.DATE) + 7);
        }
        return cal.getTime();
    }

    public static String getT2Date(int dateNum) {
        return new SimpleDateFormat("yyyy-MM-dd").format(getArrivalDate(dateNum));
    }
    
	public static String getFormatDate(java.util.Date myDate) {
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(
				"yyyyMMdd");
		return formatter.format(myDate);
	}
	
	public static String getFormatDate2(java.util.Date myDate) {
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(
				"yyyy-MM-dd");
		return formatter.format(myDate);
	}
    
    public static String getFormatDateTime(java.util.Date myDate) {
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(myDate);
    }
    
    public static String getFormatDateTime2(java.util.Date myDate) {
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMddHHmmss");
        return formatter.format(myDate);
    }
//    public static void main(String[] args) {
//    	getT2Date();
//	}
}
