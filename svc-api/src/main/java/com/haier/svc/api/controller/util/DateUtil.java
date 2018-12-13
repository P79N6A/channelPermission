package com.haier.svc.api.controller.util;

import com.haier.common.BusinessException;
import com.haier.common.util.StringUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    // 日期式样，年月日，用横杠离开，例如：2006-12-25，2008-08-08
    public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";

    // 日期式样，年月日，用横杠离开，例如：2006-12-25，2008-08-08
    public static final String DATE_FORMAT_YYYY_MM_DD_HMS = "yyyy-MM-dd HH:mm:ss";

    public static Date addMinutes(Date theDate, int minutes) {
        long Time = (theDate.getTime() / 1000) + 60 * minutes;
        Date myDate = new Date();
        myDate.setTime(Time * 1000);
        return myDate;
    }

    public static Date toParse(String date, String pattern) {
        if (date == null)
            return null;
        if (StringUtil.isEmpty(pattern))
            return null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            return sdf.parse(date);
        } catch (Throwable e) {
            throw new BusinessException("无法格式化日期时间：" + date.toString() + ", 格式：" + pattern);
        }
    }

    public static String format(Date date, String pattern) {
        if (date == null)
            return "";
        if (StringUtil.isEmpty(pattern))
            return date.toString();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            return sdf.format(date);
        } catch (Throwable e) {
            throw new BusinessException("无法格式化日期时间：" + date.toString() + ", 格式：" + pattern);
        }
    }

    /**
     * 计算两个日期相隔天数
     *
     * @param smdate
     * @param bdate
     * @return
     * @throws ParseException
     */
    public static int daysBetween(Date smdate, Date bdate) {
        String pattern = "yyyy-MM-dd";
        smdate = toParse(format(smdate, pattern), pattern);
        bdate = toParse(format(bdate, pattern), pattern);
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }


    /**
     * @param specifiedDay
     * @param days
     * @param offset       1 后几天 -1 前几天
     * @return
     */
    public static String getSpecifiedDay(String specifiedDay, int days, int offset) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        if (offset == 1) {
            c.set(Calendar.DATE, day + days);
        } else {
            c.set(Calendar.DATE, day - days);
        }

        String dayAfter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c.getTime());
        return dayAfter;
    }

    public static String convertTimeByMillisecods(long milliSecods, String pattern) {
        StringBuffer time = new StringBuffer("");
        long dayMillisecods = 28800000;
        if (milliSecods / dayMillisecods > 0) {//满一天
            time.append(milliSecods / dayMillisecods + "天");
            long countD = milliSecods / dayMillisecods;
            if ((milliSecods - dayMillisecods * countD) / 3600000 > 0) {//满一个小时
                time.append((milliSecods - dayMillisecods * countD) / 3600000 + "时");
                long countH = (milliSecods - dayMillisecods * countD) / 3600000;
                if ((milliSecods - dayMillisecods * countD - 3600000 * countH) / 60000 > 0) {//满一分钟
                    time.append((milliSecods - dayMillisecods * countD - 3600000 * countH) / 60000 + "分");
                    long countM = (milliSecods - dayMillisecods * countD - 3600000 * countH) / 60000;
                    if ((milliSecods - dayMillisecods * countD - 3600000 * countH - 60000 * countM) / 1000 > 0) {
                        time.append((milliSecods - dayMillisecods * countD - 3600000 * countH - 60000 * countM) / 1000 + "秒");
                    }
                } else {
                    if ((milliSecods - dayMillisecods * countD - 3600000 * countH) / 1000 > 0)
                        time.append((milliSecods - dayMillisecods * countD - 3600000 * countH) / 1000 + "秒");
                }
            } else {
                if ((milliSecods - dayMillisecods * countD) / 60000 > 0) {
                    time.append((milliSecods - dayMillisecods * countD) / 60000 + "分");
                    long countM = (milliSecods - dayMillisecods * countD) / 60000;
                    if ((milliSecods - dayMillisecods * countD - 60000 * countM) / 1000 > 0) {
                        time.append((milliSecods - dayMillisecods * countD - 60000 * countM) / 1000 + "秒");
                    }
                } else {
                    if ((milliSecods - dayMillisecods * countD) / 1000 > 0) {
                        time.append((milliSecods - dayMillisecods * countD) / 1000 + "秒");
                    }
                }
            }

        } else {//不满一天
            if (milliSecods / 3600000 > 0) {
                time.append(milliSecods / 3600000 + "时");
                long countH = milliSecods / 3600000;
                if ((milliSecods - 3600000 * countH) / 60000 > 0) {
                    time.append((milliSecods - 3600000 * countH) / 60000 + "分");
                    long countM = (milliSecods - 3600000 * countH) / 60000;
                    if ((milliSecods - 3600000 * countH - 60000) / 1000 > 0) {
                        time.append((milliSecods - 3600000 * countH - 60000 * countM) / 1000 + "秒");
                    }
                } else {
                    if ((milliSecods - 3600000 * countH) / 1000 > 0)
                        time.append((milliSecods - 3600000 * countH) / 1000 + "秒");
                }
            } else {
                if (milliSecods / 60000 > 0) {
                    time.append(milliSecods / 60000 + "分");
                    long countM = milliSecods / 60000;
                    if ((milliSecods - 60000 * countM) / 1000 > 0) {
                        time.append((milliSecods - 60000 * countM) / 1000 + "秒");
                    }
                } else {
                    if (milliSecods / 1000 > 0) {
                        time.append(milliSecods / 1000 + "秒");
                    }
                }

            }
        }
        return time.toString();
    }

    /**
     * 获取当前日期的前n天
     *
     * @return
     */
    public static String getBeforeDay(int n) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -n);
        date = calendar.getTime();
        return sdf.format(date);
    }

    /**
     * 获取当前日期的前n天
     *
     * @return
     */
    public static Date getYesterday(int n) {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -n);
        return date;
    }


    /**
     * 获取当前日期的(前后)n天
     *
     * @param d
     * @param n >0 (after)or n < 0(before)
     * @return
     */
    public static String getBeforeDay(Date d, int n) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.add(Calendar.DAY_OF_MONTH, n);
        d = calendar.getTime();
        return sdf.format(d);
    }

    /**
     * 获取当前月份之前月份的第一天(n:前几个月(0表示当前月份))
     *
     * @return
     */
    public static String getFirstDayOfMonth(int n) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal_1 = Calendar.getInstance();//获取当前日期
        cal_1.add(Calendar.MONTH, n);
        cal_1.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        String firstDay = format.format(cal_1.getTime());
        return firstDay;
    }

    /**
     * 获取指定月份第一天(n-1 = 当前月份)
     *
     * @return
     */
    public static String getAppointDayOfMonth(int n) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal_1 = Calendar.getInstance();//获取当前日期
        cal_1.set(Calendar.MONTH, n - 1);
        cal_1.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        String firstDay = format.format(cal_1.getTime());
        return firstDay;
    }

    /**
     * 获取指定月份第一天(n-1 = 当前月份)
     *
     * @return
     */
    public static Date getDayOfMonth(int n) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal_1 = Calendar.getInstance();//获取当前日期
        cal_1.set(Calendar.MONTH, n - 1);
        cal_1.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        Date d = cal_1.getTime();
        return d;
    }

    /**
     * 根据日期计算属于第几周(周日是一周的第一天)
     *
     * @param date 格式 yyyy-MM-dd dispflg:0 返回yyyyww;1 返回yyyy年ww周
     * @throws ParseException return 返回空表示异常，或日期为空
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
