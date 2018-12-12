package com.haier.logistics.Util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

public final class CommonTool {
    //判断字符串是否为空
    public static boolean isNull(String strIn) {
        if (null == strIn || ("").equals(strIn.trim()) || ("NULL").equals(strIn.toUpperCase())) {
            return true;
        }

        return false;
    }

    /**
     * 取得当前时间。 <BR>
     * <UL>
     * <LI>取得当前时间，并格式化。</LI>
     * </UL>
     *
     * @param format 要格式化的格式。
     * @return 格式化后的时间。
     */
    public static String getCurrentDate(String format) {
        // 日历类
        Calendar calendar = Calendar.getInstance();
        // 格式化
        String time = new SimpleDateFormat(format).format(calendar.getTime());

        return time;
    }

    /**
     * 取得当前月最后一天。 <BR>
     * <UL>
     * <LI>取得当前时间，并格式化。</LI>
     * </UL>
     *
     * @return 格式化后的时间。
     */
    public static String getCurrentMonthLastDate() {
        // 日历类
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int day = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        // 格式化
        String time = new SimpleDateFormat("yyyy-MM-" + day).format(calendar.getTime());
        return time;
    }

    /**
     * 取得传入最后一天。 <BR>
     * <UL>
     * <LI>取得当前时间，并格式化。</LI>
     * </UL>
     *
     * @return 格式化后的时间。
     */
    public static String getMonthLastDate(String mon) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(df.parse(mon));
        } catch (ParseException e) {
            e.fillInStackTrace();
        }
        int day = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        // 获取间隔n天时间
        String time = new SimpleDateFormat("yyyy-MM-" + day).format(calendar.getTime());
        return time;
    }

    /**
     * 取得当前昨天时间。 <BR>
     * <UL>
     * <LI>取得当前时间，并格式化。</LI>
     * </UL>
     *
     * @param format 要格式化的格式。
     * @return 格式化后的时间。
     */
    public static String getCurrentLastDate(String format) {

        // 日历类
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(calendar.getTime());
        // 取得7天前日期
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        // 格式化
        String time = new SimpleDateFormat(format).format(calendar.getTime());

        return time;
    }

    /**
     * 取得当前上月时间。 <BR>
     * <UL>
     * <LI>取得当前时间，并格式化。</LI>
     * </UL>
     *
     * @param format 要格式化的格式。
     * @return 格式化后的时间。
     */
    public static String getCurrentLastMonth(String format) {

        // 日历类
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(calendar.getTime());
        // 取得7天前日期
        calendar.add(Calendar.MONTH, -1);
        // 格式化
        String time = new SimpleDateFormat(format).format(calendar.getTime());

        return time;
    }

    /**
     * 取得当前上月时间。 <BR>
     * <UL>
     * <LI>取得当前时间，并格式化。</LI>
     * </UL>
     *
     * @param format 要格式化的格式。
     * @return 格式化后的时间。
     */
    public static String getDateByFormatANum(String format, int num) {
        // 日历类
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(calendar.getTime());
        // 取得7天前日期
        calendar.add(Calendar.MONTH, num);
        // 格式化
        String time = new SimpleDateFormat(format).format(calendar.getTime());

        return time;
    }

    /**
     * 串转sql串。 <BR>
     * <UL>
     * <LI>replace</LI>
     * </UL>
     *
     */
    public static String replaceToSqlStr(String str) {
        if (isNull(str)) {
            return "";
        } else {
            StringBuffer bf = new StringBuffer();
            String strs[] = str.split(",");
            for (int i = 0; i < strs.length; i++) {
                bf.append("'" + strs[i] + "'");
                if (strs.length - 1 != i) {
                    bf.append(",");
                }
            }
            return bf.toString();
        }
    }

    /**
     * 格式化数字，千分位
     * 11111.113 -->1,111.113
     * 123213-->123,213
     */
    public static String numberFormat(String num) {
        NumberFormat nf = NumberFormat.getInstance();
        return nf.format(Double.parseDouble(num));
    }

    /**
     * 格式化数字，千分位
     * 11111.113 -->1,111.113
     * 123213-->123,213.00
     */
    public static String decimalFormat(String num) {
        if ("0.0".equals(num)) {
            return "0";
        }
        double dou = Double.parseDouble(num);
        String format = "#,###.00";
        if (dou < 1) {
            format = "0.00";
        }
        NumberFormat nf = new DecimalFormat(format);
        return nf.format(dou);
    }

    /**
     * 格式化数字，千分位
     * 11111.113 -->1,111.1130
     * 123213-->123,213.0000
     */
    public static String decimalFourFormat(String num) {
        if ("0.0".equals(num)) {
            return "0";
        }
        double dou = Double.parseDouble(num);
        String format = "#,###.0000";
        if (dou < 1) {
            format = "0.0000";
        }
        NumberFormat nf = new DecimalFormat(format);
        return nf.format(dou);
    }

    /**
     * 格式化数字, 保留四位小数
     */
    public static String decimalFour(String num) {
        if ("0.0".equals(num)) {
            return "0";
        }
        double dou = Double.parseDouble(num);
        String format = "#.0000";
        if (dou < 1) {
            format = "0.0000";
        }
        NumberFormat nf = new DecimalFormat(format);
        return nf.format(dou);
    }

    /**
     * 根据传入时间获取间隔n天时间
     */
    public static String getNewDate(String format, String date, int num) {
        DateFormat df = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(df.parse(date));
        } catch (ParseException e) {
            e.fillInStackTrace();
        }
        // 获取间隔n天时间
        calendar.add(Calendar.DAY_OF_MONTH, num);
        return df.format(calendar.getTime());
    }

    /**
     * 根据传入时间获取间隔n天时间
     */
    public static String getNewDateByDate(String format, Date date, int num) {
        DateFormat df = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(date);
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        // 获取间隔n天时间
        calendar.add(Calendar.DAY_OF_MONTH, num);
        return df.format(calendar.getTime());
    }

    /**
     * 将字符数组转化SQL条件串
     */
    public static String replaceToSqlStr(String[] strs) {
        if (strs == null) {
            return "";
        } else {
            StringBuffer bf = new StringBuffer();
            int length = strs.length;
            for (int i = 0; i < length - 1; i++) {
                if (!"".equals(strs[i])) {
                    bf.append("'" + strs[i] + "'");
                    if (!"".equals(strs[length - 1])) {
                        bf.append(",");
                    }
                }
            }
            if (!"".equals(strs[length - 1])) {
                bf.append("'" + strs[length - 1] + "'");
            }
            return bf.toString();
        }
    }

    /**
     * 自动生成编码。 <BR>
     * @return 自动生成编码。
     */
    public static String getCode(int len, int val, String add, String head) {
        //实际值长度
        int valLen = String.valueOf(val).length();
        //需要补值的长度
        int addLen = len - valLen;
        StringBuffer valuebf = new StringBuffer();
        valuebf.append(head);
        for (int i = 0; i < addLen; i++) {
            valuebf.append(add);
        }
        valuebf.append(val);
        return valuebf.toString();
    }

    /**
     * 累加数组
     * 将一个int数组累加，并返回一个总数
     */
    public static int sumIntAry(int[] ary, int start, int end) {
        int sum = 0;
        for (int i = start; i < end; i++) {
            sum += ary[i];
        }
        return sum;
    }

    /**
     * 累加数组
     * 将一个int数组累加，并返回一个总数
     */
    public static double sumIntAry(double[] ary, int start, int end) {
        double sum = 0;
        for (int i = start; i < end; i++) {
            sum += ary[i];
        }
        return sum;
    }

    /**
     * 累加数组
     * 将一个int数组累加，并返回一个总数
     */
    public static BigDecimal sumIntAry(BigDecimal[] ary, int start, int end) {
        BigDecimal sum = BigDecimal.ZERO;
        for (int i = start; i < end; i++) {
            sum = sum.add(ary[i]);
        }
        return sum;
    }

    /**
     * 累加数组
     * 将一个int数组累加，并返回一个总数
     */
    public static int sumIntAry(long[] ary, int start, int end) {
        int sum = 0;
        for (int i = start; i < end; i++) {
            sum += ary[i];
        }
        return sum;
    }

    /**
     * 累加数组
     * 将一个int数组累加，并返回一个总数
     */
    public static int sumIntAry(int[] ary) {
        return sumIntAry(ary, 0, ary.length);
    }

    /**
     * 累加数组
     * 将一个int数组累加，并返回一个总数
     */
    public static BigDecimal sumIntAry(BigDecimal[] ary) {
        return sumIntAry(ary, 0, ary.length);
    }

    /**
     * 累加数组
     * 将一个int数组累加，并返回一个总数
     */
    public static int sumIntAry(long[] ary) {
        return sumIntAry(ary, 0, ary.length);
    }

    /**
     * 累加数组
     * 将一个double数组累加，并返回一个总数
     */
    public static double sumDoubleAry(double[] ary, int start, int end) {
        double sum = 0;
        for (int i = start; i < end; i++) {
            sum += ary[i];
        }
        return sum;
    }

    /**
     * 累加数组
     * 将一个double数组累加，并返回一个总数
     */
    public static double sumDoubleAry(double[] ary) {
        return sumDoubleAry(ary, 0, ary.length);
    }

    /**
     * 累加数组
     * 将一个double数组累加，并返回一个总数
     */
    public static double sumDoubleAry(double[] ary, int start, int end, int n) {
        double sum = 0;
        for (int i = start; i < end; i++) {
            sum += ary[i];
        }
        return doubleRound(sum, n);
    }

    /**
     * 累加数组
     * 将一个double数组累加，并返回一个总数
     */
    public static double sumDoubleAry(double[] ary, int n) {
        return doubleRound(sumDoubleAry(ary, 0, ary.length), n);
    }

    /**
     * 将double类型保留小数
     * 保留n位小数
     * 默认保留2位
     */
    public static double doubleRound(double d, int n) {
        if (d == Double.POSITIVE_INFINITY || d == Double.NEGATIVE_INFINITY) {
            //如果double是无穷大, 则抛出异常
            throw new RuntimeException();
        }
        return Math.round(d * Math.pow(10, n)) / Math.pow(10, n);
    }

    /**
     * doublue相除 分子乘以*n
     */
    public static double doubledivide(double d, double d2, int n) {
        if (0 == d2) {
            return 0;
        }
        return new BigDecimal(d * n / d2).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 将日维度数组转化为月维度
     * mons : 月
     * days : 日
     * cnts.length == mons.length
     */
    public static long[] day2Mon(long[] mons, long[] days, int[] cnts) {
        int cnt = 0;
        for (int i = 0; i < cnts.length; i++) {
            //fengyy
            //mons[i] = sumIntAry(days, cnt, (cnt=cnt+cnts[i]));
            mons[i] = sumIntAry(days, cnt, cnt + cnts[i]);
            cnt = cnt + cnts[i];
        }
        return mons;
    }

    /**
     * 将日维度数组转化为月维度
     * mons : 月
     * days : 日
     * cnts.length == mons.length
     */
    public static BigDecimal[] day2Mon(BigDecimal[] mons, BigDecimal[] days, int[] cnts) {
        int cnt = 0;
        for (int i = 0; i < cnts.length; i++) {
            //mons[i] = sumIntAry(days, cnt, (cnt=cnt+cnts[i]));
            mons[i] = sumIntAry(days, cnt, cnt + cnts[i]);
            cnt = cnt + cnts[i];
        }
        return mons;
    }

    public static double[] day2Mon(double[] mons, double[] days, int[] cnts, int n) {
        int cnt = 0;
        for (int i = 0; i < cnts.length; i++) {
            mons[i] = sumDoubleAry(days, cnt, (cnt + cnts[i]), n);
            cnt = cnt + cnts[i];
        }
        return mons;
    }

    /**
     * 将实体类的数值属性累加
     * 返回一个实体对象
     * @throws IllegalArgumentException
     * @throws Exception
     */
    public static <T> T sumDomain(Collection<T> c) throws Exception {
        if (c == null || c.size() == 0) {
            return null;
        }
        Class<T> clazz;
        try {
            clazz = (Class<T>) (c.iterator().next().getClass());
        } catch (Exception e) {
            return null;
        }
        T rst = clazz.newInstance();
        //保存有效返回值类型的集合
        LinkedList<String> names = new LinkedList<String>();
        names.add("int");
        //names.add("java.lang.Integer");
        names.add("double");
        //names.add("java.lang.Double");
        //实体类中有效get,set方法的集合
        LinkedList<Method> gets = new LinkedList<Method>();
        LinkedList<Method> sets = new LinkedList<Method>();
        for (Method m : clazz.getDeclaredMethods()) {
            String name = m.getName();
            //System.out.println(name);
            if (name.startsWith("get") && names.contains(m.getReturnType().getName())) {
                gets.add(m);
                continue;
            }
            if (name.startsWith("set")) {
                sets.add(m);
            }
        }
        for (Method get : gets) {
            double sum = 0;
            for (T obj : c) {
                sum += Double.valueOf(get.invoke(obj) + "");
            }
            //根据当前get方法,得到对应的set方法
            Method set = get2set(get, sets);
            if (set != null) {
                String paramName = set.getParameterTypes()[0].getName();
                if ("double".equals(paramName)) {
                    set.invoke(rst, sum);
                } else if ("int".equals(paramName)) {
                    set.invoke(rst, (int) Math.round(sum));
                }
            }
        }
        return rst;
    }

    //根据当前get方法,从set方法的集合中,得到对应名字的set方法
    private static Method get2set(Method get, Collection<Method> sets) {
        String setName = "s" + get.getName().substring(1);
        for (Method set : sets) {
            if (set.getName().equals(setName)) {
                return set;
            }
        }
        return null;
    }

    //去除空格回车与换行
    public static String cleanParm(String param) {
        String reparam = param.replaceAll("\n", "").replaceAll("\r", "").replaceAll("\t", "");
        return reparam;
    }

    public static String sendPost(String destUrl, String postData, String type) throws Exception {

        String recString = "";
        URL url = null;
        HttpURLConnection urlconn = null;
        OutputStream out = null;
        BufferedReader rd= null;
        try{
            url = new URL(destUrl);
            urlconn = (HttpURLConnection) url.openConnection();
            urlconn.setRequestProperty("content-type", type);
            urlconn.setRequestMethod("POST");
            urlconn.setDoInput(true);
            urlconn.setDoOutput(true);
            out = urlconn.getOutputStream();
            out.write(postData.getBytes("UTF-8"));
            out.flush();

            rd = new BufferedReader(new InputStreamReader(urlconn.getInputStream(),
                    "UTF-8"));
            StringBuffer sb = new StringBuffer();
            int ch = rd.read();
            while (ch > -1) {
                sb.append((char) ch);
            }
            recString = sb.toString();
            urlconn.disconnect();
            return recString;
        }finally{
            if(rd!=null){
                rd.close();
            }
            if(out!=null){
                out.close();
            }
        }
    }
    //post通道接口入参方法解密.
    //@auth lichh
    /* public static String decodeIn(String param) throws Exception
     {
       return  param =CommonTool.cleanParm(URLDecoder.decode(new String(Base64.decodeBase64(param), "UTF-8"), "UTF-8"));
     }*/
    //post通道接口出参data解密.
    //@auth lichh
    /* public static String decodeOut(String param) throws Exception
     {
        return new String(Base64.decodeBase64(param.getBytes()),"UTF-8");
     }*/
}
