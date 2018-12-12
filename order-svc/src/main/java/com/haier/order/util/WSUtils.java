package com.haier.order.util;

import com.alibaba.dubbo.common.utils.StringUtils;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class WSUtils {
    // 日期式样，年月日，用横杠离开，例如：2006-12-25，2008-08-08
    public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
    
    public static String normalDateString(String dateStr) {
        if (StringUtils.isNotEmpty(dateStr)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                return sdf2.format(sdf.parse(dateStr));
            } catch (ParseException e) {
                sdf2 = new SimpleDateFormat("yyyy-MM-dd");
                sdf = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    return sdf2.format(sdf.parse(dateStr));
                } catch (ParseException e1) {
                    sdf = new SimpleDateFormat("yyyyMMdd");
                    try {
                        return sdf2.format(sdf.parse(dateStr));
                    } catch (ParseException e2) {
                        e2.printStackTrace();
                        return dateStr;
                    }
                }
            }
        }
        return null;
    }

    public static String xmlCalendarToString(XMLGregorianCalendar c) {
        if (c == null)
            return "";
        if (c.getHour() < 0) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(c.toGregorianCalendar().getTime());
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.format(c.toGregorianCalendar().getTime());
        }
    }

    public static XMLGregorianCalendar stringToXmlCalendar(String s, String dateFormat) {
        if (s == null)
            return null;
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        try {
            Date d = sdf.parse(s);
            XMLGregorianCalendar c = DatatypeFactory.newInstance().newXMLGregorianCalendar(
                d.getYear() + 1900, d.getMonth() + 1, d.getDate(), d.getHours(), d.getMinutes(),
                d.getSeconds(), 0, d.getTimezoneOffset());
            return c;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /*
     * 把拆单后的订单号转化成为拆单前的原始单号
     * @param 拆单后的订单号或拆单前的原始单号
     * @return 拆单前的原始单号
     */
    public static String getSourceOrderID(String orderID) {
        int index = orderID.lastIndexOf("-");
        if (index == -1)
            return orderID;
        return orderID.substring(0, index);
    }

    /**
     * 根据日期计算属于第几周(周四是一周的第一天)
     * @param date 格式 yyyy-MM-dd dispflg:0 返回yyyyww;1 返回yyyy年ww周
     * @throws ParseException
     * return 返回空表示异常，或日期为空
     */
    public static String getWeekOfYear_Sunday(String date, String pattern, String dispflg) {
        if (date == null)
            return "";
        if (pattern == null)
            pattern = DATE_FORMAT_YYYY_MM_DD;
        try {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            Calendar cal = Calendar.getInstance();
            cal.setFirstDayOfWeek(Calendar.SUNDAY); // 设置每周的第一天为星期日
            //cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);// 每周从周一开始
            cal.setMinimalDaysInFirstWeek(1); // 设置每周最少为1天
            cal.setTime(df.parse(date));
            if (cal.get(Calendar.DAY_OF_WEEK) > 4) {
                cal.add(Calendar.DATE, 7);
            }
            int year = cal.get(Calendar.YEAR);//获得当前年
            int month = cal.get(Calendar.MONTH);//获得当前月
            int week = cal.get(Calendar.WEEK_OF_YEAR);//获得周数
            if (month + 1 == 12 && week == 1) {
                year += 1;//如果当前月是12月并且周数是1，作为明年的第一周
            }
            if (dispflg.equals("0")) {
                return year + "" + (week < 10 ? "0" + week : week);//返回yyyyww格式
            } else if (dispflg.equals("1")) {
                return year + "年" + (week < 10 ? "0" + week : week) + "周";//返回yyyyww格式
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 根据日期计算属于第几周(周日是一周的第一天)
     * @param date 格式 yyyy-MM-dd dispflg:0 返回yyyyww;1 返回yyyy年ww周
     * @throws ParseException
     * return 返回空表示异常，或日期为空
     */
    public static String getWeekOfYear_Sunday_Normal(String date, String pattern, String dispflg) {
        if (date == null)
            return "";
        if (pattern == null)
            pattern = DATE_FORMAT_YYYY_MM_DD;
        try {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            Calendar cal = Calendar.getInstance();
            cal.setFirstDayOfWeek(Calendar.SUNDAY); // 设置每周的第一天为星期日
            cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);// 每周从周一开始
            cal.setMinimalDaysInFirstWeek(1); // 设置每周最少为1天
            cal.setTime(df.parse(date));
            int year = cal.get(Calendar.YEAR);//获得当前年
            int month = cal.get(Calendar.MONTH);//获得当前月
            int week = cal.get(Calendar.WEEK_OF_YEAR);//获得周数
            if (month + 1 == 12 && week == 1) {
                year += 1;//如果当前月是12月并且周数是1，作为明年的第一周
            }
            if (dispflg.equals("0")) {
                return year + "" + (week < 10 ? "0" + week : week);//返回"yyyyww"格式
            } else if (dispflg.equals("1")) {
                return year + "年" + (week < 10 ? "0" + week : week) + "周";//返回"yyyy年ww周"格式
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
