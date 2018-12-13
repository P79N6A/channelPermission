package com.haier.afterSale.services;

import com.haier.afterSale.service.ICountTimeOut;
import com.haier.afterSale.util.NumberParseUtil;
import com.haier.afterSale.service.ReportType;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * 审核超时计算实现类
 * 
 * @Filename: AuditCountTimeOutImpl.java
 * @Version: 1.0
 * @Author: yaoyu
 * @Email: yaoyu@ehaier.com
 *
 */
public class AuditCountTimeOutImpl implements ICountTimeOut {

    @Override
    public void countTimeOut(List<Map<String, Object>> dataList) {
        if (null == dataList || dataList.isEmpty()) {
            return;
        }
        Set<Long> orsIdSet = new HashSet<Long>();
        Long applyTime = null;
        Long auditTime = null;
        Calendar realCalendar = null;
        Calendar mustCalendar = null;
        Date now = new Date();
        int timeoutType = 0;
        Map<String, Object> resultMap = null;
        for (Map<String, Object> map : dataList) {
            //计算超时
            this.countTimeOutEachOne(orsIdSet, applyTime, auditTime, realCalendar, mustCalendar,
                now, timeoutType, map, resultMap);
        }
    }

    @Override
    public void countTimeOutWithFilter(List<Map<String, Object>> dataList) {
        if (null == dataList || dataList.isEmpty()) {
            return;
        }
        Set<Long> orsIdSet = new HashSet<Long>();
        Long applyTime = null;
        Long auditTime = null;
        Calendar realCalendar = null;
        Calendar mustCalendar = null;
        Date now = new Date();
        int timeoutType = 0;
        Map<String, Object> resultMap = null;
        for (Map<String, Object> map : dataList) {
        	/** 过滤数据开始**/
            if (!"WA".equalsIgnoreCase(map.get("stockType").toString())) {
                continue;
            }
            if (NumberParseUtil.parseInteger(map.get("checkType")) > 1) {
                continue;
            }
            Integer success = NumberParseUtil.parseInteger(map.get("success"));
            if (success == 2) {
                continue;
            }
            //剔除重复数据(存性变更的出库21和入库13),只剩下空和入库-网点不检验11,入库-网点检验12
            Integer operate = NumberParseUtil.parseInteger(map.get("operate"));
            if (operate > 12) {
                continue;
            }
            applyTime = NumberParseUtil.parseLong(map.get("addTime"));
            if (applyTime == 0) {
                continue;
            }
            /** 过滤数据结束**/
            //计算超时
            this.countTimeOutEachOne(orsIdSet, applyTime, auditTime, realCalendar, mustCalendar,
                now, timeoutType, map, resultMap);
        }
    }

    private void countTimeOutEachOne(Set<Long> orsIdSet, Long applyTime, Long auditTime,
                                     Calendar realCalendar, Calendar mustCalendar, Date now,
                                     int timeoutType, Map<String, Object> map,
                                     Map<String, Object> resultMap) {
        Long orsId = NumberParseUtil.parseLong(map.get("id"));
        if (orsIdSet.contains(orsId)) {
            return;
        }
        orsIdSet.add(orsId);
        applyTime = NumberParseUtil.parseLong(map.get("addTime"));
        auditTime = NumberParseUtil.parseLong(map.get("handleTime"));
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
        auditTime = NumberParseUtil.getMinimumExceptZero(handleTime, endTime, auditTime);

        if (auditTime == 0) {
            realCalendar.setTime(now);
        } else {
            realCalendar.setTimeInMillis(auditTime * 1000l);
        }
        mustCalendar.setTimeInMillis(applyTime * 1000l);
        //退换货审核在申请后24H内完成
        mustCalendar.add(Calendar.DAY_OF_YEAR, 1);

        resultMap = new HashMap<String, Object>();
        map.put(ReportType.AUDIT, resultMap);
        //应完成时间
        //map.put("mustDate", DateFormatUtil.format(mustCalendar.getTime()));
        resultMap.put("mustDate", mustCalendar);
        //实际完成时间,如果等于0设置为空
        if (auditTime == 0) {
            resultMap.put("realDate", null);
        } else {
            //map.put("realDate", DateFormatUtil.format(realCalendar.getTime()));
            resultMap.put("realDate", realCalendar);
        }
        if (realCalendar.after(mustCalendar)) {
            //超时天数
            resultMap.put("outDay",
                (realCalendar.getTimeInMillis() - mustCalendar.getTimeInMillis()) / DAY_MILLIS);
            timeoutType = auditTime > 0 ? FINISH_TIMEOUT : NOT_FINISH_TIMEOUT;
        } else {
            resultMap.put("outDay", 0);
            timeoutType = auditTime > 0 ? FINISH_NOT_TIMEOUT : NOT_FINISH_NOT_TIMEOUT;
        }
        //超时类型
        resultMap.put("timeoutType", timeoutType);
    }

}
