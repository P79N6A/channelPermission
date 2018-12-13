package com.haier.svc.api.controller.util;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.CharArrayWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class StringUtil {
    public static boolean areNotEmpty(String... values) {
        boolean result = true;
        if (values == null || values.length == 0) {
            result = false;
        } else {
            for (String value : values) {
                result &= !isEmpty(value);
            }
        }
        return result;
    }

    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean isNumeric(final CharSequence cs) {
        if (isEmpty(cs)) {
            return false;
        }
        final int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            if (Character.isDigit(cs.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    public static String dbSafeString(String value, boolean nullable, int maxLength) {
        if (value == null) {
            if (nullable)
                return null;
            return nullSafeString(value);
        }
        if (value.length() > maxLength)
            return value.substring(0, maxLength);
        return value;
    }

    public static String toJson(Object o) {
        if (o == null) {
            return null;
        }else if(o instanceof Throwable){
            return toString((Throwable)o,new StringBuilder());
        }else{
            try{
                return new GsonBuilder().create().toJson(o);
            }catch (Throwable e) {
                return "json转换异常"+e+":"+e.getMessage();
            }
        }
    }

    public static String toString(Throwable t,StringBuilder simpleStackTrace){
        if(simpleStackTrace == null){
            simpleStackTrace = new StringBuilder();
        }
        if(simpleStackTrace.length() > 200){
            simpleStackTrace.replace(197, simpleStackTrace.length(), "...");
            return simpleStackTrace.toString();
        }
        if(t == null){
            simpleStackTrace.deleteCharAt(simpleStackTrace.length()-1);
            return simpleStackTrace.toString();
        }else{
            simpleStackTrace.append(t.toString()+"|");
            return toString(t.getCause(), simpleStackTrace);
        }
    }

    public static String toString(Object o) {
        if (o == null) {
            return null;
        }
        String oStr = "";
        if(o instanceof String){
            oStr = o.toString();
        }else{
            oStr = toJson(o);
        }
        String info = o.getClass().getName() + "|" + oStr;
        if(info.getBytes().length>65535){
            info = info.substring(0,21845);
        }
        return info;
    }

    public static String toString(Exception e,boolean isSimple) {
        if(e == null){
            return null;
        }
        CharArrayWriter arrayWriter = new CharArrayWriter();
        e.printStackTrace(new PrintWriter(arrayWriter, true));
        arrayWriter.close();
        String errorMsg = arrayWriter.toString();
        if(isSimple){
            String[] st = errorMsg.split("\r\n");
            if(st.length>=2){
                errorMsg = st[0]+"\r\n"+st[1];
            }
        }
        if(errorMsg.getBytes().length>65535-100){
            errorMsg = errorMsg.substring(0,21845);
        }
        return errorMsg;
    }

    /**
     * 从下标0开始截取固定长度字符串
     * @return
     */
    public static String subString(String str, int length){
        if (null == str){
            return "";
        }else if(str.length() > length){
            return str.substring(0,length);
        }else{
            return str;
        }
    }

    public static String parseJsonInfo(JsonObject jsonObject, String key){
        JsonElement je = jsonObject.get(key);
        if(je == null){
            return "";
        }
        return StringUtil.nullSafeString(je.getAsString()).trim();
    }

    public static String nullSafeString(String value) {
        return value == null ? "" : value;
    }

    public static String textSafeString(String value) {
        return textSafeString(value,65535);
    }

    public static String textSafeString(String value,int maxLength) {
        if(value == null||value.length() < 3){
            return nullSafeString(value);
        }
        int i = 0;
        String end = "";
        while(value.getBytes().length > maxLength-3){
            value = new String(Arrays.copyOf(value.getBytes(),maxLength-3-(i++)));
            end = "...";
        }
        return value+end;
    }

    public static boolean isEmpty(String value, boolean trim, char... trimChars) {
        if (trim) {
            return value == null || trim(value, trimChars).length() <= 0;
        } else {
            return value == null || value.length() <= 0;
        }
    }

    public static boolean isEmpty(String value, boolean trim) {
        return isEmpty(value, trim, ' ');
    }

    public static boolean isEmpty(String value) {
        return isEmpty(value, false);
    }

    public static String trim(String value) {
        return trim(3, value, ' ');
    }

    public static String trim(String value, char... chars) {
        return trim(3, value, chars);
    }

    private static String trim(int mode, String value, char... chars) {
        if (value != null && value.length() > 0) {
            int startIndex = 0;
            int endIndex = value.length();
            int index = 0;
            if (mode == 1 || mode == 3) {
                while(index < endIndex && contains(chars, value.charAt(index++))) {
                    ++startIndex;
                }
            }

            if (startIndex >= endIndex) {
                return "";
            } else {
                if (mode == 2 || mode == 3) {
                    for(index = endIndex - 1; index >= 0 && contains(chars, value.charAt(index--)); --endIndex) {
                        ;
                    }
                }

                if (startIndex >= endIndex) {
                    return "";
                } else {
                    return startIndex == 0 && endIndex == value.length() - 1 ? value : value.substring(startIndex, endIndex);
                }
            }
        } else {
            return value;
        }
    }

    private static boolean contains(char[] chars, char chr) {
        if (chars != null && chars.length > 0) {
            for(int i = 0; i < chars.length; ++i) {
                if (chars[i] == chr) {
                    return true;
                }
            }

            return false;
        } else {
            return false;
        }
    }

    /**
     * 判断字符串是否null或者空
     * @param str
     * @return
     */
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }
}
