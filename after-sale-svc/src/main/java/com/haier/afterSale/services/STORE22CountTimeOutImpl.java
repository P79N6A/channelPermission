package com.haier.afterSale.services;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.haier.afterSale.service.ICountTimeOut;
import com.haier.afterSale.service.ReportType;
import com.haier.afterSale.util.NumberParseUtil;

/**
 * 22库转10或41库超时计算实现类
 * 
 * @Filename: STORE22CountTimeOutImpl.java
 * @Version: 1.0
 * @Author: lsr
 * @Email: liushangru@ehaier.com
 *
 */
public class STORE22CountTimeOutImpl implements ICountTimeOut {

    @Override
    public void countTimeOut(List<Map<String, Object>> dataList) {
        if (null == dataList || dataList.isEmpty()) {
            return;
        }
        Set<Long> orsIdSet = new HashSet<Long>();
        Map<Long, Map<String, Object>> orsIdDataMap = new HashMap<Long, Map<String, Object>>();
        Long in1041StoreTime = null;
        Calendar realCalendar = null;
        Calendar mustCalendar = null;
        Date now = new Date();
        int timeoutType = 0;
        Map<String, Object> resultMap = null;
        for (Map<String, Object> map : dataList) {
            //计算超时
            this.countTimeOutEachOne(orsIdSet, orsIdDataMap, in1041StoreTime, realCalendar,
                mustCalendar, now, timeoutType, map, resultMap, false);
        }
        //处理入22库时间有时间但是没有入10或41库时间的未完成超期数据
        if (orsIdDataMap.isEmpty()) {
            return;
        }
        for (Entry<Long, Map<String, Object>> entry : orsIdDataMap.entrySet()) {
            //计算超时
            this.countTimeOutEachOne(orsIdSet, orsIdDataMap, in1041StoreTime, realCalendar,
                mustCalendar, now, timeoutType, entry.getValue(), resultMap, true);
        }
    }

    @Override
    public void countTimeOutWithFilter(List<Map<String, Object>> dataList) {
        if (null == dataList || dataList.isEmpty()) {
            return;
        }
        Set<Long> orsIdSet = new HashSet<Long>();
        Map<Long, Map<String, Object>> orsIdDataMap = new HashMap<Long, Map<String, Object>>();
        Long in1041StoreTime = null;
        Calendar realCalendar = null;
        Calendar mustCalendar = null;
        Date now = new Date();
        int timeoutType = 0;
        Long lesOutPingTime = null;
        Integer operate = null;
        Integer storageType = null;
        Integer success = null;
        Map<String, Object> resultMap = null;

        for (Map<String, Object> map : dataList) {
            /** 过滤数据开始**/
        	if (!"WA".equalsIgnoreCase(map.get("stockType").toString())) {
                continue;
            }
            operate = NumberParseUtil.parseInteger(map.get("operate"));
            storageType = NumberParseUtil.parseInteger(map.get("storageType"));
            lesOutPingTime = NumberParseUtil.parseLong(map.get("lesOutPingTime"));
            if (operate != 11 && operate != 12 && operate != 13) {
                continue;
            }
            if (storageType != 22 && storageType != 10 && storageType != 41) {
                continue;
            }
            if ((operate == 11 || operate == 12) && storageType != 22) {
                continue;
            }
            if (operate == 13 && !(storageType == 10 || storageType == 41)) {
                continue;
            }
            if ((operate == 11 || operate == 12) && storageType == 22 && lesOutPingTime == 0) {
                continue;
            }

            success = NumberParseUtil.parseInteger(map.get("success"));
            if (success == 2) {
                continue;
            }
            /** 过滤数据结束**/
            //计算超时
            this.countTimeOutEachOne(orsIdSet, orsIdDataMap, in1041StoreTime, realCalendar,
                mustCalendar, now, timeoutType, map, resultMap, false);
        }
        //处理入22库时间有时间但是没有入10或41库时间的未完成超期数据
        if (orsIdDataMap.isEmpty()) {
            return;
        }
        for (Entry<Long, Map<String, Object>> entry : orsIdDataMap.entrySet()) {
            //计算超时
            this.countTimeOutEachOne(orsIdSet, orsIdDataMap, in1041StoreTime, realCalendar,
                mustCalendar, now, timeoutType, entry.getValue(), resultMap, true);
        }
    }

    private void countTimeOutEachOne(Set<Long> orsIdSet,
                                     Map<Long, Map<String, Object>> orsIdDataMap,
                                     Long in1041StoreTime, Calendar realCalendar,
                                     Calendar mustCalendar, Date now, int timeoutType,
                                     Map<String, Object> map, Map<String, Object> resultMap,
                                     boolean isTwice) {
        in1041StoreTime = NumberParseUtil.parseLong(map.get("lesOutPingTime"));
        realCalendar = Calendar.getInstance();
        mustCalendar = Calendar.getInstance();
        Long orsId = NumberParseUtil.parseLong(map.get("id"));
        if (orsIdSet.contains(orsId)) {
            return;
        }
        Integer operate = NumberParseUtil.parseInteger(map.get("operate"));
        Integer storageType = NumberParseUtil.parseInteger(map.get("storageType"));
        if (isTwice) {
            in1041StoreTime = 0L;
            orsIdSet.add(orsId);
        } else {
            if ((operate == 11 || operate == 12) && storageType == 22) {
                if (!orsIdDataMap.containsKey(orsId)) {
                    orsIdDataMap.put(orsId, map);
                }
                return;
            }
            if (operate == 13 && (storageType == 10 || storageType == 41)) {
                if (!orsIdDataMap.containsKey(orsId)) {
                    return;
                }
                orsIdSet.add(orsId);
            }
        }

        //取两个时间:finishTime和handleTime和实际完成时间比较,取最小不为0的
        Integer handleStatus = NumberParseUtil.parseInteger(map.get("handleStatus"));
        Long handleTime = 0l;
        Long endTime = 0l;
        //已驳回
        if (handleStatus == 5) {
            handleTime = NumberParseUtil.parseLong(map.get("handleTime"));
        }
        //受理完成 已终止
        if (handleStatus == 3 || handleStatus == 6) {
            endTime = NumberParseUtil.parseLong(map.get("endTime"));//finishTime在sql里别名为endTime            
        }
        in1041StoreTime = NumberParseUtil.getMinimumExceptZero(handleTime, endTime, in1041StoreTime);

        //入10或41库时间
        if (in1041StoreTime == 0) {
            realCalendar.setTime(now);
        } else {
            realCalendar.setTimeInMillis(in1041StoreTime * 1000l);
        }
        //入22库时间
        mustCalendar.setTimeInMillis(NumberParseUtil.parseLong(orsIdDataMap.get(orsId).get(
            "lesOutPingTime")) * 1000l);
        if (!isTwice) {
            //取出入22库时间之后删除key,剩下的就是未完成超期的key
            orsIdDataMap.remove(orsId);
        }
        mustCalendar.add(Calendar.DAY_OF_YEAR, 22);

        resultMap = new HashMap<String, Object>();
        map.put(ReportType.STORE22, resultMap);
        //应完成时间
        //map.put("mustDate", DateFormatUtil.format(mustCalendar.getTime()));
        resultMap.put("mustDate", mustCalendar);
        //实际完成时间,如果等于0设置为空
        if (in1041StoreTime == 0) {
            resultMap.put("realDate", null);
        } else {
            //map.put("realDate", DateFormatUtil.format(realCalendar.getTime()));
            resultMap.put("realDate", realCalendar);
        }
        if (realCalendar.after(mustCalendar)) {
            //超时天数
            resultMap.put("outDay",
                (realCalendar.getTimeInMillis() - mustCalendar.getTimeInMillis()) / DAY_MILLIS);
            timeoutType = in1041StoreTime > 0 ? FINISH_TIMEOUT : NOT_FINISH_TIMEOUT;
        } else {
            resultMap.put("outDay", 0);
            timeoutType = in1041StoreTime > 0 ? FINISH_NOT_TIMEOUT : NOT_FINISH_NOT_TIMEOUT;
        }
        //超时类型
        resultMap.put("timeoutType", timeoutType);
    }

}
