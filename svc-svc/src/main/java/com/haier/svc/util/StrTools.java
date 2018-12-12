package com.haier.svc.util;

import java.util.regex.Pattern;

import org.jdom.output.Format;

import com.haier.common.util.StringUtil;

public class StrTools {
    /**
     * 判断输入的字符串是否为纯汉字，为null返回false
     * @param str 传入的字符窜
     * @return 如果是纯汉字返回true,否则返回false
     */
    public static boolean isChinese(String str) {
        if (str == null) {
            return false;
        }
        Pattern pattern = Pattern.compile("[\u0391-\uFFE5]+$");
        return pattern.matcher(str).matches();
    }

    /**
     * 判断输入的字符串是否为匹配国内电话号码，为null返回false
     * @param str 传入的字符窜
     * @return 如果是国内电话号码返回true,否则返回false
     */
    public static boolean isTelephone(String str) {
        if (str == null) {
            return false;
        }
        Pattern pattern = Pattern
            .compile("\\d{3}-\\d{8}|\\d{4}-\\d{7}|\\d{3}-\\d{7}|\\d{4}-\\d{8}");
        return pattern.matcher(str).matches();
    }

    /**
     * 判断输入的字符串是否为匹配国内手机号码，为null返回false
     * @param str 传入的字符窜
     * @return 如果是国内手机号码返回true,否则返回false
     */
    public static boolean isMobile(String str) {
        if (str == null) {
            return false;
        }
        Pattern pattern = Pattern.compile("[1][3-8]\\d{9}");
        return pattern.matcher(str).matches();
    }

    /**
     * 判断输入的字符串是否为匹配Email地址，为null返回false
     * @param str 传入的字符窜
     * @return 如果是Email地址返回true,否则返回false
     */
    public static boolean isEmail(String str) {
        if (str == null) {
            return false;
        }
        Pattern pattern = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
        return pattern.matcher(str).matches();
    }

    /**
     * 格式化xml
     * @return
     */
    public static Format formatXML() {
        //格式化生成的xml文件，如果不进行格式化的话，生成的xml文件将会是很长的一行...  
        Format format = Format.getCompactFormat();
        format.setEncoding("utf-8");
        format.setIndent("\t");//对齐方式
        return format;
    }

    /**
     * 异常堆栈字符串输出
     * @return
     */
    public static String printExceptionStackInfo(Exception e) {
        StringBuilder sb = new StringBuilder();
        sb.append(e.toString()).append("\r\n");
        for (StackTraceElement key : e.getStackTrace()) {
            sb.append("\t" + key.toString()).append("\r\n");
        }
        return sb.toString();
    }

    /**
     * 格式化日志输出
     * @param logType 日志分类
     * @param logKey 记录标志
     * @param msg 日志信息
     * @return
     */
    public static String logPrefix(String logType, String logKey, String msg) {
        StringBuilder sb = new StringBuilder();
        if (!StringUtil.isEmpty(logType)) {
            sb.append("[" + logType + "] ");
        }
        if (!StringUtil.isEmpty(logKey)) {
            sb.append("[" + logKey + "] ");
        }
        if (!StringUtil.isEmpty(msg)) {
            sb.append(msg);
        }
        return sb.toString();
    }
}
