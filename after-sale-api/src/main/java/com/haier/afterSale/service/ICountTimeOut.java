package com.haier.afterSale.service;

import java.util.List;
import java.util.Map;

/**
 * 计算是否超时接口，各个实际指标类型各自实现
 * 
 * @Filename: ICountTimeOut.java
 * @Version: 1.0
 * @Author: yaoyu
 * @Email: yaoyu@ehaier.com
 *
 */
    public interface ICountTimeOut {
    /**
     * 已完成未超期
     */
    public static final int  FINISH_NOT_TIMEOUT     = 0;
    /**
     * 已完成已超期
     */
    public static final int  FINISH_TIMEOUT         = 1;
    /**
     * 未完成已超期
     */
    public static final int  NOT_FINISH_TIMEOUT     = 2;
    /**
     * 未完成未超期
     */
    public static final int  NOT_FINISH_NOT_TIMEOUT = 3;
    /**
     * 一天毫秒数
     */
    public static final long DAY_MILLIS             = 24 * 3600 * 1000l;

    /**
     * 计算超时,在map里加入:超时类型,超时时间,应完成时间,实际完成时间
     * 不做剔除数据逻辑,所有数据都会计算
     * @param dataList 数据列表
     */
    public void countTimeOut(List<Map<String, Object>> dataList);

    /**
     * 计算超时,在map里加入:超时类型,超时时间,应完成时间,实际完成时间
     * 如果sql已经过滤了无用数据,不需要再判断状态等剔除逻辑.不做无用功,提高效率
     * @param dataList 数据列表
     */
    public void countTimeOutWithFilter(List<Map<String, Object>> dataList);
}
