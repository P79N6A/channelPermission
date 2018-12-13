package com.haier.shop.util;

import org.apache.commons.lang.math.NumberUtils;

import java.util.Arrays;

/**
 * 数字格式化工具类
 *                       
 * @Filename: NumberParseUtil.java
 * @Version: 1.0
 * @Author: yaoyu
 * @Email: yaoyu@ehaier.com
 *
 */
public class NumberParseUtil {
    /**
     * 将Object类型的数字转换成Long
     * @param obj
     * @return
     */
    public static Long parseLong(Object obj) {
        if (null == obj) {
            return 0l;
        }
        if (obj instanceof Long) {
            return (Long) obj;
        } else if (obj instanceof Integer) {
            return ((Integer) obj).longValue();
        } else {
            String str = obj.toString();
            if (NumberUtils.isNumber(str)) {
                return Long.parseLong(str);
            }
        }
        return 0l;
    }

    /**
     * 将Object类型的数字转换成Integer
     * @param obj
     * @return
     */
    public static Integer parseInteger(Object obj) {
        if (null == obj) {
            return 0;
        }
        if (obj instanceof Integer) {
            return (Integer) obj;
        } else if (obj instanceof Long) {
            return ((Long) obj).intValue();
        } else {
            String str = obj.toString();
            if (NumberUtils.isNumber(str)) {
                return Integer.parseInt(str);
            }
        }
        return 0;
    }
    public static Boolean getBoolean(int num) {
        return num == 0 ? Boolean.FALSE : Boolean.TRUE;
    }

    public static Boolean getBoolean(String str) {
        return "0".equals(str) ? Boolean.FALSE : Boolean.TRUE;
    }

    public static Boolean getBoolean(Object obj) {
        if (obj instanceof Boolean) {
            return (Boolean) obj;
        }
        return getBoolean(obj.toString());
    }
    /**
     * 按照升序排序
     * @param numbers
     */
    public static void sortByAsc(Long... numbers) {
        Arrays.sort(numbers);
    }

    /**
     * 取不为0的最小值,如果都为0,返回0
     * @param numbers
     * @return
     */
    public static Long getMinimumExceptZero(Long... numbers) {
        sortByAsc(numbers);
        for (Long num : numbers) {
            if (num != 0) {
                return num;
            }
        }
        return 0l;
    }
}
