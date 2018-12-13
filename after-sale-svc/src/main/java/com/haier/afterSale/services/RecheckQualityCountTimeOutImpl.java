package com.haier.afterSale.services;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.haier.afterSale.service.ICountTimeOut;
import com.haier.afterSale.util.NumberParseUtil;
import com.haier.afterSale.service.ReportType;

/**
 * 二次质检超时计算实现类
 * 
 * @Filename: RecheckQualityCountTimeOutImpl.java
 * @Version: 1.0
 * @Author: yaoyu
 * @Email: yaoyu@ehaier.com
 *
 */
public class RecheckQualityCountTimeOutImpl implements ICountTimeOut {

    @Override
    public void countTimeOut(List<Map<String, Object>> dataList) {
        if (null == dataList || dataList.isEmpty()) {
            return;
        }
        Set<Long> orsIdSet = new HashSet<Long>();
        Long storageTime = null;
        Long hpTime = null;
        Calendar realCalendar = null;
        Calendar mustCalendar = null;
        Date now = new Date();
        int timeoutType = 0;
        Map<String, Object> resultMap = null;
        for (Map<String, Object> map : dataList) {
            //计算超时
            this.countTimeOutEachOne(orsIdSet, storageTime, hpTime, realCalendar, mustCalendar,
                now, timeoutType, map, resultMap);
        }
    }

    @Override
    public void countTimeOutWithFilter(List<Map<String, Object>> dataList) {
        if (null == dataList || dataList.isEmpty()) {
            return;
        }
        Set<Long> orsIdSet = new HashSet<Long>();
        Long lesOutPingTime = null;
        Long storageTime = null;
        Long hpTime = null;
        Calendar realCalendar = null;
        Calendar mustCalendar = null;
        Date now = new Date();
        int timeoutType = 0;
        Integer checkType = null;
        //        Integer handleStatus = null;
        Map<String, Object> resultMap = null;
        for (Map<String, Object> map : dataList) {
            /** 过滤数据开始**/
        	if (!"WA".equalsIgnoreCase(map.get("stockType").toString())) {
                continue;
            }
            checkType = NumberParseUtil.parseInteger(map.get("checkType"));
            if (checkType != 2) {
                continue;
            }
            //剔除重复数据(存性变更的出库21和入库13),只剩下空和入库-网点不检验11,入库-网点检验12
            Integer operate = NumberParseUtil.parseInteger(map.get("operate"));
            if (operate != 12) {
                continue;
            }
            Integer storageType = NumberParseUtil.parseInteger(map.get("storageType"));
            if (storageType != 22) {
                continue;
            }
            Integer success = NumberParseUtil.parseInteger(map.get("success"));
            if (success == 2) {
                continue;
            }
            //            hpSecondAddTime = NumberParseUtil.parseLong(map.get("hpSecondAddTime"));
            //            if (hpSecondAddTime == 0) {
            //                continue;
            //            }
            //            handleStatus = NumberParseUtil.parseInteger(map.get("handleStatus"));
            //            if (handleStatus == 6) {
            //                continue;
            //            }
            lesOutPingTime = NumberParseUtil.parseLong(map.get("lesOutPingTime"));
            if (lesOutPingTime == 0) {
                continue;
            }
            /** 过滤数据结束**/
            //计算超时
            this.countTimeOutEachOne(orsIdSet, storageTime, hpTime, realCalendar, mustCalendar,
                now, timeoutType, map, resultMap);
        }
    }

    private void countTimeOutEachOne(Set<Long> orsIdSet, Long storageTime, Long hpTime,
                                     Calendar realCalendar, Calendar mustCalendar, Date now,
                                     int timeoutType, Map<String, Object> map,
                                     Map<String, Object> resultMap) {
        Long orsId = NumberParseUtil.parseLong(map.get("id"));
        if (orsIdSet.contains(orsId)) {
            return;
        }
        orsIdSet.add(orsId);
        Long lesOutPingTime = NumberParseUtil.parseLong(map.get("lesOutPingTime"));
        hpTime = NumberParseUtil.parseLong(map.get("hpTime"));
        realCalendar = Calendar.getInstance();
        mustCalendar = Calendar.getInstance();

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

        if (hpTime == 0) {
            realCalendar.setTime(now);
        } else {
            realCalendar.setTimeInMillis(hpTime * 1000l);
        }
        mustCalendar.setTimeInMillis(lesOutPingTime * 1000l);
        mustCalendar.add(Calendar.DAY_OF_YEAR, 5);

        resultMap = new HashMap<String, Object>();
        map.put(ReportType.RECHECKQUALITY, resultMap);
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
