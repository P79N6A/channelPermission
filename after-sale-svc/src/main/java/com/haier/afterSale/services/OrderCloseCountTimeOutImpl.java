package com.haier.afterSale.services;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.haier.afterSale.util.BeanUtil;
import com.haier.afterSale.service.ICountTimeOut;
import com.haier.afterSale.service.ReportType;
import com.haier.afterSale.util.NumberParseUtil;
import com.haier.shop.service.OrderWorkFlowModelDataService;

/**
 * 逆向闭环超时计算实现类
 * 
 * @Filename: OrderCloseCountTimeOutImpl.java
 * @Version: 1.0
 * @Author: yaoyu
 * @Email: yaoyu@ehaier.com
 *
 */
public class OrderCloseCountTimeOutImpl implements ICountTimeOut {

    @Override
    public void countTimeOut(List<Map<String, Object>> dataList) {
        if (null == dataList || dataList.isEmpty()) {
            return;
        }
        Set<Long> orsIdSet = new HashSet<Long>();
        Integer status = null;
        Long applyTime = null;
        Long finishTime = null;
        Long cancelTime = null;
        Long closeTime = null;
        Long state = null;
        Long handleStatus = null;
        Long handleTime = null;
        Long endTime = null;
        Calendar realCalendar = null;
        Calendar mustCalendar = null;
        Date now = new Date();
        int timeoutType = 0;
        Map<String, Object> resultMap = null;
        for (Map<String, Object> map : dataList) {
            //计算超时
            this.countTimeOutEachOne(orsIdSet, status, applyTime, finishTime, cancelTime,
                closeTime, state, handleStatus, handleTime, endTime, realCalendar, mustCalendar,
                now, timeoutType, map, resultMap);
        }
    }

    @Override
    public void countTimeOutWithFilter(List<Map<String, Object>> dataList) {
        if (null == dataList || dataList.isEmpty()) {
            return;
        }
        Set<Long> orsIdSet = new HashSet<Long>();
        Integer status = null;
        Long applyTime = null;
        Long finishTime = null;
        Long cancelTime = null;
        Long closeTime = null;
        Long state = null;
        Long handleStatus = null;
        Long handleTime = null;
        Long endTime = null;
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
            //剔除重复数据(存性变更的出库21和入库13),只剩下空和入库-网点不检验11,入库-网点检验12
            Integer operate = NumberParseUtil.parseInteger(map.get("operate"));
            if (operate > 12) {
                continue;
            }
            Integer success = NumberParseUtil.parseInteger(map.get("success"));
            if (success == 2) {
                continue;
            }
            //退换货订单关闭及时率，开始时间为推送HP时间
            //            applyTime = NumberParseUtil.parseLong(map.get("addTime"));
            applyTime = NumberParseUtil.parseLong(map.get("hpFirstAddTime"));
            if (applyTime == 0) {
                continue;
            }
            /** 过滤数据结束**/
            //计算超时
            this.countTimeOutEachOne(orsIdSet, status, applyTime, finishTime, cancelTime,
                closeTime, state, handleStatus, handleTime, endTime, realCalendar, mustCalendar,
                now, timeoutType, map, resultMap);
        }
    }

    /**
     *
     * @param orsIdSet set默认空集合
     * @param status    默认nill
     * @param applyTime hp第一次推送时间
     * @param finishTime    默认nill
     * @param cancelTime    默认nill
     * @param closeTime 默认nill
     * @param state 默认nill
     * @param handleStatus  默认nill
     * @param handleTime    默认nill
     * @param endTime   默认nill
     * @param realCalendar  默认nill
     * @param mustCalendar  默认nill
     * @param now 当前时间
     * @param timeoutType   默认0
     * @param map   报表数据
     * @param resultMap 默认nill
     */
    private void countTimeOutEachOne(Set<Long> orsIdSet, Integer status, Long applyTime,
                                     Long finishTime, Long cancelTime, Long closeTime, Long state,
                                     Long handleStatus, Long handleTime, Long endTime,
                                     Calendar realCalendar, Calendar mustCalendar, Date now,
                                     int timeoutType, Map<String, Object> map,
                                     Map<String, Object> resultMap) {
        Long orsId = NumberParseUtil.parseLong(map.get("id"));
        if (orsIdSet.contains(orsId)) {
            return;
        }
        orsIdSet.add(orsId);
        status = NumberParseUtil.parseInteger(map.get("status"));
        //退换货订单关闭及时率，开始时间为推送HP时间
        //        applyTime = NumberParseUtil.parseLong(map.get("addTime"));
        applyTime = NumberParseUtil.parseLong(map.get("hpFirstAddTime"));
        //        finishTime = NumberParseUtil.parseLong(map.get("finishTime"));
        cancelTime = NumberParseUtil.parseLong(map.get("cancelTime"));
        //        closeTime = status == 110 ? cancelTime : finishTime;
        closeTime = cancelTime;
        //************优化增加开始**********************
        state = NumberParseUtil.parseLong(map.get("state"));
        handleStatus = NumberParseUtil.parseLong(map.get("handleStatus"));

        if (handleStatus == 5
            && (handleTime = NumberParseUtil.parseLong(map.get("handleTime"))) > 0) {
            closeTime = handleTime;
        } else if ((handleStatus == 3 || handleStatus == 6)
                   && (endTime = NumberParseUtil.parseLong(map.get("endTime"))) > 0) {
            closeTime = endTime;
        }
        //************优化增加结束**********************
        realCalendar = Calendar.getInstance();
        mustCalendar = Calendar.getInstance();
        if (closeTime == 0) {
            realCalendar.setTime(now);
        } else {
            realCalendar.setTimeInMillis(closeTime * 1000l);
        }
        mustCalendar.setTimeInMillis(applyTime * 1000l);
        //开发票需在les入库后24H内完成
        mustCalendar.add(Calendar.DAY_OF_YEAR, 9);

        //如果一次质检结果为开箱正品,应完成时间再加21天
        Integer quality = NumberParseUtil.parseInteger(map.get("quality"));
        if (NumberParseUtil.parseInteger(map.get("checkType")) == 1 && quality == 2) {
            mustCalendar.add(Calendar.DAY_OF_YEAR, 21);
        }
        double distance = ((OrderWorkFlowModelDataService) BeanUtil.getBean("orderWorkFlowModelDataService"))
            .getDistances(NumberParseUtil.parseLong(map.get("region")));

        //线下已退款直接入实 //不良品直接入实 //超500公里直接入实
        if (handleStatus == 7
            || (NumberParseUtil.parseInteger(map.get("checkType")) == 1 && quality == 3)
            || distance > 500) {
            realCalendar = mustCalendar;
        }

        resultMap = new HashMap<String, Object>();
        map.put(ReportType.ORDERCLOSE, resultMap);
        //应完成时间
        //map.put("mustDate", DateFormatUtil.format(mustCalendar.getTime()));
        resultMap.put("mustDate", mustCalendar);
        //实际完成时间,如果等于0设置为空
        if (closeTime == 0) {
            resultMap.put("realDate", null);
        } else {
            //map.put("realDate", DateFormatUtil.format(realCalendar.getTime()));
            resultMap.put("realDate", realCalendar);
        }
        if (realCalendar.after(mustCalendar)) {
            //超时天数
            resultMap.put("outDay",
                (realCalendar.getTimeInMillis() - mustCalendar.getTimeInMillis()) / DAY_MILLIS);
            timeoutType = closeTime > 0 ? FINISH_TIMEOUT : NOT_FINISH_TIMEOUT;
        } else {
            resultMap.put("outDay", 0);
            timeoutType = closeTime > 0 ? FINISH_NOT_TIMEOUT : NOT_FINISH_NOT_TIMEOUT;
        }
        //发票未冲红情况
        if (state != 4 && state != 2 && !(handleStatus == 5 && closeTime > 0)
            && !(handleStatus == 6 && closeTime > 0)) {
            if (timeoutType == 0) {
                timeoutType = NOT_FINISH_NOT_TIMEOUT;
            } else if (timeoutType == 1) {
                timeoutType = NOT_FINISH_TIMEOUT;
            }
        }
        //超时类型
        resultMap.put("timeoutType", timeoutType);

        //线下已退款直接入实 //不良品直接入实 //超500公里直接入实
        if (handleStatus == 7
            || (NumberParseUtil.parseInteger(map.get("checkType")) == 1 && quality == 3)
            || distance > 500) {
            resultMap.put("realDate", realCalendar);
            resultMap.put("timeoutType", FINISH_NOT_TIMEOUT);
        }
    }

}
