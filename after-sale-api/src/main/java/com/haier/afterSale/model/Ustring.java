package com.haier.afterSale.model;

import java.text.SimpleDateFormat;
/**
 * Created by zhangbo on 2017/11/6.
 */
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Ustring {
    public Ustring() {
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isEmpty(Object[] array) {
        return array == null || array.length == 0;
    }

    public static String getRandomString(int fixedLength) {
        String KeyString = "abcdefghjkmnpqrstuvwxyABCDEFGHJKLMNOPQRSTUVWXY3456789";
        StringBuffer sb = new StringBuffer();
        int len = KeyString.length();

        for(int i = 0; i < fixedLength; ++i) {
            sb.append(KeyString.charAt((int)Math.round(Math.random() * (double)(len - 1))));
        }

        return sb.toString();
    }

    public static String[] getArrayByString(String str, String delim) {
        if(isEmpty(delim)) {
            delim = ",";
        }

        String[] arr = str.split(delim);
        return arr;
    }

    public static List<String> getListByString(String str, String delim) {
        if(isEmpty(delim)) {
            delim = ",";
        }

        String[] arr = str.split(delim);
        List list = Arrays.asList(arr);
        return list;
    }

    public static String getStringByArray(String[] paramArray) {
        try {
            String ex = "";
            if(isEmpty((Object[])paramArray)) {
                return ex;
            } else {
                int size = paramArray.length;

                for(int i = 0; i < size; ++i) {
                    if(!isEmpty(paramArray[i])) {
                        ex = ex + "\'" + paramArray[i] + "\',";
                    }
                }

                ex = ex.substring(0, ex.length() - 1);
                return ex;
            }
        } catch (Exception var4) {
            var4.printStackTrace(System.out);
            return "";
        }
    }

    public static String getStringByList(List<String> paramList) {
        try {
            String ex = "";
            if(paramList == null) {
                return ex;
            } else {
                int size = paramList.size();

                for(int i = 0; i < size; ++i) {
                    if(!isEmpty((String)paramList.get(i))) {
                        ex = ex + "\'" + (String)paramList.get(i) + "\',";
                    }
                }

                ex = ex.substring(0, ex.length() - 1);
                return ex;
            }
        } catch (Exception var4) {
            var4.printStackTrace(System.out);
            return "";
        }
    }

    public static String getFormatUUIDs(String param) {
        String str = param.replaceAll(",,", ",");
        if(",".equals(str.substring(0, 1))) {
            str = str.substring(1);
        }

        if(",".equals(str.substring(str.length() - 1, str.length()))) {
            str = str.substring(0, str.length() - 1);
        }

        str = str.replaceAll(",", "\',\'");
        str = "\'" + str + "\'";
        return str;
    }

    public static String getMaskMobile(String mobile) {
        return mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

    public static boolean isFormatStr(String str) {
        Matcher matcher = Pattern.compile("^[0-9a-zA-Z _-]+$").matcher(str);
        return matcher.find();
    }
    /**
     * 如果为null 就转成""
     * @param obj
     * @return
     */
	public static String getString(Object obj){
		if(obj == null || obj.equals("null") || obj.equals("") || obj.equals("[]")){
			return "";
		}
		return obj.toString();
	}
	
	/**
	 * 如果为null 或者 "" 则转成0
	 * @param obj
	 * @return
	 */
	public static String getString0(Object obj){
		if(obj == null || obj.equals("null") || obj.equals("") || obj.equals("[]")){
			return "0";
		}
		return obj.toString();
	}
	
	 public static String timeStamp(){  
         long time = System.currentTimeMillis();
         String t = String.valueOf(time/1000);  
         return t;  
     }  
	 public static String timeStamp2Date(String seconds,String format) {  
         if(seconds == null || seconds.isEmpty() || seconds.equals("null")){  
            return "";  
        }  
          if(format == null || format.isEmpty()){
               format = "yyyy-MM-dd HH:mm:ss";
           }   
           SimpleDateFormat sdf = new SimpleDateFormat(format);  
           Calendar c = Calendar.getInstance();  
           c.setTime(new Date(Long.valueOf(seconds+"000")));  
           c.add(Calendar.DAY_OF_MONTH, 1);// 今天+1天 
           Date tomorrow = c.getTime();  
           return sdf.format(tomorrow);  
       }
    public static String timeStamp3Date(String seconds,String format) {
        if(seconds == null || seconds.isEmpty() || seconds.equals("null")){
            return "";
        }
        if(format == null || format.isEmpty()){
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Calendar c = Calendar.getInstance();
        c.setTime(new Date(Long.valueOf(seconds+"000")));
        c.add(Calendar.DAY_OF_MONTH, 3);// 今天+1天
        Date tomorrow = c.getTime();
        return sdf.format(tomorrow);
    }
}

