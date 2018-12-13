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
import com.haier.afterSale.util.NumberParseUtil;
import com.haier.afterSale.service.ReportType;

/**
 * 转箱超时计算实现类
 * 
 * @Filename: TransmitBoxCountTimeOutImpl.java
 * @Version: 1.0
 * @Author: yaoyu
 * @Email: yaoyu@ehaier.com
 *
 */
public class TransmitBoxCountTimeOutImpl implements ICountTimeOut {

    @Override
    public void countTimeOut(List<Map<String, Object>> dataList) {
        if (null == dataList || dataList.isEmpty()) {
            return;
        }
        Set<Long> orsIdSet = new HashSet<Long>();
        Map<Long, Map<String, Object>> orsIdDataMap = new HashMap<Long, Map<String, Object>>();
        Long hpTime = null;
        Calendar realCalendar = null;
        Calendar mustCalendar = null;
        Date now = new Date();
        int timeoutType = 0;
        Map<String, Object> resultMap = null;
        for (Map<String, Object> map : dataList) {
            //计算超时
            this.countTimeOutEachOne(orsIdSet, orsIdDataMap, hpTime, realCalendar, mustCalendar,
                now, timeoutType, map, resultMap, false);
        }
        //处理checkType=2但是没有=3的未完成超期数据
        if (orsIdDataMap.isEmpty()) {
            return;
        }
        for (Entry<Long, Map<String, Object>> entry : orsIdDataMap.entrySet()) {
            //计算超时
            this.countTimeOutEachOne(orsIdSet, orsIdDataMap, hpTime, realCalendar, mustCalendar,
                now, timeoutType, entry.getValue(), resultMap, true);
        }
    }

    @Override
    public void countTimeOutWithFilter(List<Map<String, Object>> dataList) {
        if (null == dataList || dataList.isEmpty()) {
            return;
        }
        Set<Long> orsIdSet = new HashSet<Long>();
        Map<Long, Map<String, Object>> orsIdDataMap = new HashMap<Long, Map<String, Object>>();
        Long hpTime = null;
        Calendar realCalendar = null;
        Calendar mustCalendar = null;
        Date now = new Date();
        int timeoutType = 0;
        Integer checkType = null;
        Integer success = null;
        Map<String, Object> resultMap = null;
        for (Map<String, Object> map : dataList) {
            /** 过滤数据开始**/
        	if (!"WA".equalsIgnoreCase(map.get("stockType").toString())) {
                continue;
            }
            checkType = NumberParseUtil.parseInteger(map.get("checkType"));
            if (checkType != 2 && checkType != 3) {
                continue;
            }
            hpTime = NumberParseUtil.parseLong(map.get("hpTime"));
            if (hpTime == 0) {
                continue;
            }
            success = NumberParseUtil.parseInteger(map.get("success"));
            if (success == 2) {
                continue;
            }
            /** 过滤数据结束**/
            //计算超时
            this.countTimeOutEachOne(orsIdSet, orsIdDataMap, hpTime, realCalendar, mustCalendar,
                now, timeoutType, map, resultMap, false);
        }
        //处理checkType=2但是没有=3的未完成超期数据
        if (orsIdDataMap.isEmpty()) {
            return;
        }
        for (Entry<Long, Map<String, Object>> entry : orsIdDataMap.entrySet()) {
            //计算超时
            this.countTimeOutEachOne(orsIdSet, orsIdDataMap, hpTime, realCalendar, mustCalendar,
                now, timeoutType, entry.getValue(), resultMap, true);
        }
    }

    private void countTimeOutEachOne(Set<Long> orsIdSet,
                                     Map<Long, Map<String, Object>> orsIdDataMap, Long hpTime,
                                     Calendar realCalendar, Calendar mustCalendar, Date now,
                                     int timeoutType, Map<String, Object> map,
                                     Map<String, Object> resultMap, boolean isTwice) {
        hpTime = NumberParseUtil.parseLong(map.get("hpTime"));
        realCalendar = Calendar.getInstance();
        mustCalendar = Calendar.getInstance();
        Integer checkType = NumberParseUtil.parseInteger(map.get("checkType"));
        Long orsId = NumberParseUtil.parseLong(map.get("id"));
        if (orsIdSet.contains(orsId)) {
            return;
        }
        if (isTwice) {
            hpTime = 0L;
            orsIdSet.add(orsId);
        } else {
            if (checkType == 2) {
                if (!orsIdDataMap.containsKey(orsId)) {
                    orsIdDataMap.put(orsId, map);
                }
                return;
            }
            if (checkType == 3) {
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
        hpTime = NumberParseUtil.getMinimumExceptZero(handleTime, endTime, hpTime);

        //转箱的回传时间
        if (hpTime == 0) {
            realCalendar.setTime(now);
        } else {
            realCalendar.setTimeInMillis(hpTime * 1000l);
        }
        //二次质检的回传时间
        mustCalendar.setTimeInMillis(NumberParseUtil.parseLong(orsIdDataMap.get(orsId)
            .get("hpTime")) * 1000l);
        if (!isTwice) {
            //取出二次质检时间之后删除key,剩下的就是未完成超期的key
            orsIdDataMap.remove(orsId);
        }
        mustCalendar.add(Calendar.DAY_OF_YEAR, 15);

        resultMap = new HashMap<String, Object>();
        map.put(ReportType.TRANSMITBOX, resultMap);
        //应完成时间
        //map.put("mustDate", DateFormatUtil.format(mustCalendar.getTime()));
        resultMap.put("mustDate", mustCalendar);
        //实际完成时间,如果等于0设置为空
        if (hpTime == 0) {
            resultMap.put("realDate", null);
        } else {
            //map.put("realDate", DateFormatUtil.format(realCalendar.getTime()));
            resultMap.put("realDate", realCalendar);
        }
        if (realCalendar.after(mustCalendar)) {
            //超时天数
            resultMap.put("outDay",
                (realCalendar.getTimeInMillis() - mustCalendar.getTimeInMillis()) / DAY_MILLIS);
            timeoutType = hpTime > 0 ? FINISH_TIMEOUT : NOT_FINISH_TIMEOUT;
        } else {
            resultMap.put("outDay", 0);
            timeoutType = hpTime > 0 ? FINISH_NOT_TIMEOUT : NOT_FINISH_NOT_TIMEOUT;
        }
        //超时类型
        resultMap.put("timeoutType", timeoutType);
    }

}
