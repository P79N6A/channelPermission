package com.haier.invoice.util;


public class DataTypeUtil {
	 /**
     * 把longlong型数据转化为integer型数据
     * @param longData 传入long型数据
     * @return 返回integer型数据
     */
    public static Integer longToInteger(Long longData) {
    	Integer integerData=0;
        if (longData == null) {
            return 0;
        }
        integerData=new Integer(longData.intValue());
        return integerData;
    }

}
