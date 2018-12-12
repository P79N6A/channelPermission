package com.haier.order.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
* 一些日期运算的方法
*/

public class DateCal {
    public static GregorianCalendar gCalendar = null;

    public DateCal() {
        gCalendar = new GregorianCalendar();
    }

    public DateCal(String strDate) {
        try {
            DateFormat dateFormat = DateFormat.getDateInstance(2, Locale.CHINA);
            dateFormat.parse(strDate);
            Calendar calendar = dateFormat.getCalendar();
            gCalendar = new GregorianCalendar(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            //            System.out.println("exchange="+exchange());
        } catch (ParseException e) {
        }
    }

    /**
    * 计算指定的两个日期间隔的天数
    * @param startDate 开始日期
    * @param endDate 结束日期
    * @return 间隔天数
    * @exception WorkFlowException。
    */
    public static String interval(String date1, String date2) throws ParseException {
        String str = "";

        GregorianCalendar startGregorianCalendar = DateUtil.parseString(date1);
        GregorianCalendar endGregorianCalendar = DateUtil.parseString(date2);
        //如果两个日期相等
        if (startGregorianCalendar.equals(endGregorianCalendar)) {
            return "0";
        }//end if
         //如果endGregorianCalendar日期小于startGregorianCalendar日期，则交换
        if (startGregorianCalendar.after(endGregorianCalendar)) {
            GregorianCalendar temp = startGregorianCalendar;
            startGregorianCalendar = endGregorianCalendar;
            endGregorianCalendar = temp;
        }//end if
         //
        int count = 1;
        while (true) {
            //开始累加
            startGregorianCalendar.add(GregorianCalendar.DATE, 1);
            if (startGregorianCalendar.equals(endGregorianCalendar))
                break; //累加到相等则退出
            else
                count++; //间隔天数自加
        }//end while
        str = String.valueOf(count);

        return str;
    }

    /**
    * 在指定的日期上加上指定的天数。
    * @param num 天数。
    * @return YYYY-MM-DD字符串。
    * @exception WorkFlowException。
    */
    public static String addDay(GregorianCalendar gCalendar, String num) {
        if (num == null || num.trim().equals(""))
            num = "0";
        gCalendar.add(GregorianCalendar.DATE, Integer.valueOf(num.trim()).intValue());
        return DateUtil.getFormatDate(gCalendar);
    }

    /**
    * 在当前日期上加上指定的天数。
    * @param num 天数。
    * @return YYYY-MM-DD字符串。
    * @exception WorkFlowException。
    */
    public static String addDate(String num) {
        if (num == null || num.trim().equals(""))
            num = "0";
        GregorianCalendar gCalendar = new GregorianCalendar();
        gCalendar.add(GregorianCalendar.DATE, Integer.valueOf(num.trim()).intValue());
        return DateUtil.getFormatDate(gCalendar);
    }

    /**
    * 在当前日期上加上指定的天数，除去历法中的周六、周日。
    * @param num 天数。
    * @return YYYY-MM-DD字符串。
    * @exception WorkFlowException。
    */
    public static String addDateExceptWeekend(String num) {
        String str = "";

        GregorianCalendar gCalendar = new GregorianCalendar();
        for (int i = 1; i <= Integer.valueOf(num).intValue(); i++) {
            gCalendar.add(GregorianCalendar.DATE, 1);
            int t = gCalendar.get(Calendar.DAY_OF_WEEK);
            while (t == Calendar.SATURDAY || t == Calendar.SUNDAY) {
                gCalendar.add(GregorianCalendar.DATE, 1);
                t = gCalendar.get(Calendar.DAY_OF_WEEK);
            }//end while
        }//end for
        str = DateUtil.getFormatDate(gCalendar);

        return str;
    }

    /**
    * 在指定的日期上加上指定的天数，除去历法中的周六、周日。
    * @param num 天数。
    * @return YYYY-MM-DD字符串。
    * @exception WorkFlowException。
    */
    public static String addDateExceptWeekend(String strDate, String num) throws ParseException {
        String str = "";

        GregorianCalendar gCalendar = DateUtil.parseString(strDate);
        for (int i = 1; i <= Integer.valueOf(num).intValue(); i++) {
            gCalendar.add(GregorianCalendar.DATE, 1);
            int t = gCalendar.get(Calendar.DAY_OF_WEEK);
            while (t == Calendar.SATURDAY || t == Calendar.SUNDAY) {
                gCalendar.add(GregorianCalendar.DATE, 1);
                t = gCalendar.get(Calendar.DAY_OF_WEEK);
            }//end while
        }//end for
        str = DateUtil.getFormatDate(gCalendar);

        return str;
    }

    /**
    * 加上指定的月数
    * @param 月数
    * @return 日期字符串(YYYY-MM-DD)
    */
    public String addMonth(int month) {
        gCalendar.add(GregorianCalendar.MONTH, month);
        return exchange();
    }

    /**
    * 加上指定的年数
    * @param 年数
    * @return 日期字符串(YYYY-MM-DD)
    */
    public String addYear(int year) {
        gCalendar.add(GregorianCalendar.YEAR, year);
        return exchange();
    }

    /**
    * 加上指定的星期数
    * @param 星期数
    * @return 日期字符串(YYYY-MM-DD)
    */
    public String addWeek(int week) {
        gCalendar.add(GregorianCalendar.WEEK_OF_MONTH, week);
        return exchange();
    }

    /**
    * 减去指定的天数
    * @param 天数
    * @return 日期字符串(YYYY-MM-DD)
    */
    public String decDay(int day) {
        day = -1 * day;
        gCalendar.add(GregorianCalendar.DATE, day);
        return exchange();
    }

    /**
    * 减去指定的月数
    * @param 月数
    * @return 日期字符串(YYYY-MM-DD)
    */
    public String decMonth(int month) {
        month = -1 * month;
        gCalendar.add(GregorianCalendar.MONTH, month);
        return exchange();
    }

    /**
    * 减去指定的年数
    * @param 年数
    * @return 日期字符串(YYYY-MM-DD)
    */
    public String decYear(int year) {
        year = -1 * year;
        gCalendar.add(GregorianCalendar.YEAR, year);
        return exchange();
    }

    /**
    * 减去指定的星期数
    * @param 星期数
    * @return 日期字符串(YYYY-MM-DD)
    */
    public String decWeek(int week) {
        week = -1 * week;
        gCalendar.add(GregorianCalendar.WEEK_OF_MONTH, week);
        return exchange();
    }

    /**
    * 判断是否为工作日（星期六、星期日）
    * @param 无
    * @return true--工作日；false--星期六、星期日
    */
    public boolean judgeWorkDate() {
        int j = gCalendar.get(Calendar.DAY_OF_WEEK);
        if ((j == Calendar.SUNDAY) || (j == Calendar.SATURDAY)) {
            return false;
        } else {
            return true;
        }
    }

    /**
    * 将日期转换为"YYYY-MM-DD"形式输出
    */
    private String exchange() {
        String sYear = "", sMonth = "", sDay = "";
        //
        int year = gCalendar.get(Calendar.YEAR);
        int month = gCalendar.get(Calendar.MONTH);
        int day = gCalendar.get(Calendar.DAY_OF_MONTH);
        //
        sYear = String.valueOf(year); //年
        month = month + 1; //月
        if (month < 10) {
            sMonth = "0" + String.valueOf(month);
        } else {
            sMonth = String.valueOf(month);
        }
        if (day < 10) //日
        {
            sDay = "0" + String.valueOf(day);
        } else {
            sDay = String.valueOf(day);
        }
        //
        return sYear + "-" + sMonth + "-" + sDay;
    }

    /*private static String[] getChangedDate(String start, String end){
        String[] returnStrArr = new String[2];
        String startDate = DateUtil.getFormatDate(start);
        String endDate = DateUtil.getFormatDate(end);
        new DateCal(endDate);
        String end_result = DateCal.addDay(DateCal.gCalendar,"1");
        returnStrArr[0] = startDate;
        returnStrArr[1] = end_result;
        
        return returnStrArr;
    }
    
    public static String[] getAllDateArr(String startDate, String endDate) throws ParseException{
        String[] stt = DateCal.getChangedDate(startDate, endDate);
        //System.out.println("=="+stt[0] +"  "+stt[1]);
        String number = DateCal.interval(stt[0], stt[1]);
        //System.out.println("=="+number);
        int _number = Integer.parseInt(number);
        String [] returnArr = new String[_number];
        returnArr[0] = stt[0];
        String checkDate = stt[0];
        
        if(_number > 1){
            for(int k = 1; k < _number; k++){
                new DateCal(checkDate);
                String result = DateCal.addDay(DateCal.gCalendar,"1");
                  if(DateUtil.compare(stt[1],result) == 1){
                      returnArr[k] = result;
                      checkDate = result;
                  } else {
                      checkDate = result;
                  }
            }
        } else{
            return returnArr;
        }
    //        returnArr   
        
        
        return returnArr;
    }
    
    private static String[] getChangedAddOneDate(String[] dateArr){
        String[] returnStrArr = {}; 
        int _length = dateArr.length;
        if(dateArr != null && _length > 0){
            returnStrArr = new String[_length];
            for(int k = 0; k < _length; k++){
                new DateCal(dateArr[k]);
                String result = DateCal.addDay(DateCal.gCalendar,"1");
                returnStrArr[k] = result;
            }
        } else {
            return null;
        }
        
        return returnStrArr;
    }*/

    public static void main(String[] args) throws ParseException {
        /*String startDate = DateUtil.getFormatDate("20130901");
        String endDate = DateUtil.getFormatDate("20131001");
        
        new DateCal(startDate);
        String start = DateCal.addDay(DateCal.gCalendar, "0");
        System.out.println("start=="+start);
        
        new DateCal(endDate);
        String end = DateCal.addDay(DateCal.gCalendar,"1");
        System.out.println("end=="+end);
        
        String number = DateCal.interval(startDate, endDate);
        System.out.println("=="+number);*/
        /*System.out.println("------------------------");
        String[] StrArr = DateCal.getAllDateArr("20130901", "20130910");
        
        for(int k = 0; k < StrArr.length; k++){
            System.out.println("--"+StrArr[k]);
        }
        
        System.out.println("====================");
        
        String[] StrArr2 = DateCal.getChangedAddOneDate(StrArr);
        for(int k = 0; k < StrArr2.length; k++){
            System.out.println("--"+StrArr2[k]);
        }*/
    }

}