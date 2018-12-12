package com.haier.svc.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.regex.Pattern;

import com.alibaba.dubbo.common.utils.StringUtils;

/**
 * 日期的工具类
 * @remark:
 */
public class DateUtil {

    public final static int EQUAL = 0;
    public final static int EARLY = -1;
    public final static int LATER = 1;

    /**
     * 判断时间字符串是否合法
     * "05.10.1981" // swiss date format (dd.MM.yyyy)
     * "05-10-1981"
     * "07-09-2006 23:00:33"
     * "2006-09-07 23:01:25"
     * "2003-08-30" //(yyyy.MM.dd)
     * "2003-30-30" // false
     * "some text"  // false
     * @param date
     * @return
     * @author QiuYan
     */
    public static boolean isDate(String date) {
        // some regular expression
        String time = "(\\s(([01]?\\d)|(2[0123]))[:](([012345]\\d)|(60))"
                      + "[:](([012345]\\d)|(60)))?"; // with a space before, zero or one time

        // no check for leap years (Schaltjahr)
        // and 31.02.2006 will also be correct
        String day = "(([12]\\d)|(3[01])|(0?[1-9]))"; // 01 up to 31
        String month = "((1[012])|(0\\d))"; // 01 up to 12
        String year = "\\d{4}";

        // define here all date format
        ArrayList<Pattern> patterns = new ArrayList<Pattern>();
        patterns.add(Pattern.compile(day + "[-.]" + month + "[-.]" + year + time));
        patterns.add(Pattern.compile(year + "-" + month + "-" + day + time));
        // here you can add more date formats if you want

        // check dates
        for (Pattern p : patterns)
            if (p.matcher(date).matches())
                return true;

        return false;
    }

    /**
     * 将形如yyyyMMdd的日期字符串转化为yyyy-MM-dd
     */
    public static String getFormatDate(String dateStr) {
        String year = dateStr.substring(0, 4);
        String month = dateStr.substring(4, 6);
        String day = dateStr.substring(6);
        return year + "-" + month + "-" + day;
    }

    /**
     * 把Date类型转换成一定格式的字符串
     *@remark：
     *@author:
     *@param myDate：java.util.Date
     *@return:按"yyyy-MM-dd"格式化返回的字符串
     */
    public static String getFormatDate(java.util.Date myDate) {
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(myDate);
    }
    
    public static String getFormatDateTime(java.util.Date myDate) {
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(myDate);
    }

    /**
     * 把Date类型转换成一定格式的字符串
     *@remark：
     *@author:
     *@param myDate:java.util.Date类型的日期值
     *@param strFormat:字符串的格式,例如"yyyy-MM-dd"
     *@return:按传入字符串格式化后返回的字符串,如果传入的格式为空,则按照默认的格式返回
     */
    public static String getFormatDate(java.util.Date myDate, String strFormat) {
        SimpleDateFormat formatter = null;
        if (StringUtils.isBlank(strFormat)) {
            formatter = new SimpleDateFormat("yyyy-MM-dd");
        } else {
            formatter = new SimpleDateFormat(strFormat);
        }
        return formatter.format(myDate);
    }

    /**
    * 将日期格式化为"YYYY-MM-DD"字符串
    * @param GregorianCalendar对象
    * @return "YYYY-MM-DD"字符串
    */
    public static String getFormatDate(GregorianCalendar gCalendar) {
        java.util.Date date = gCalendar.getTime();
        SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return bartDateFormat.format(date);
    }

    /**
    * 将日期"YYYY-MM-DD"字符串解析为GregorianCalendar对象
    */
    public static GregorianCalendar parseString(String dateStr) throws ParseException {
        DateFormat dateFormat = DateFormat.getDateInstance(2, Locale.CHINA);
        dateFormat.parse(dateStr);
        Calendar calendar = dateFormat.getCalendar();
        return new GregorianCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH));

    }

    /**
    * 比较两个日期的先后关系
    * @param 开始日期字符串(YYYY-MM-DD)，结束日期字符串(YYYY-MM-DD)
    * @return 整型。EQUAL--两个日期相等；EARLY--startDate在endDate之前；LATER--startDate在endDate之后
    */
    public static int compare(String startDate, String endDate) throws ParseException {
        DateFormat dateFormat = DateFormat.getDateInstance(2, Locale.CHINA);
        java.util.Date date = dateFormat.parse(startDate);
        java.util.Date date1 = dateFormat.parse(endDate);
        return date.compareTo(date1);
    }

    public static void main(String[] args) {
        //      System.out.println(DateUtil.compare("2009-04-05", "2009-03-21"));
        //      System.out.println(DateUtil.compare("2009-04-05", "2009-04-05"));
        //      System.out.println(DateUtil.compare("2009-04-05", "2009-08-21"));
        try {
            //System.out.println(DateUtil.compare("2010-04-02", "2010-04-05"));

            GregorianCalendar temp = DateUtil.parseString("2013-11-04");
            //System.out.println(temp.getTime());
            //System.out.println(temp.getTimeZone());
            long scond = temp.getTimeInMillis() / 1000;
            //System.out.println(">>" + temp.getTimeInMillis() / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
