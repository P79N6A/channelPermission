package com.haier.afterSale.services;

import java.util.Arrays;
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
 * 退款超时计算实现类
 * 
 * @Filename: RefundCountTimeOutImpl.java
 * @Version: 1.0
 * @Author: yaoyu
 * @Email: yaoyu@ehaier.com
 *
 */
public class RefundCountTimeOutImpl implements ICountTimeOut {
    //天猫渠道code-订单来源代码    
    //天猫渠道（淘宝海尔官方旗舰店、海尔生活家电旗舰店、淘宝海尔官方旗舰店分销平台、淘宝海尔生活家电旗舰店分销平台、淘宝海尔买帮专卖店、淘宝海尔热水器专卖店、淘宝海尔生活电器专卖店、统帅日日顺乐家专卖店、海尔新宝旗舰店、淘宝海尔新宝旗舰店分销平台）
    Set<String> sourceSet = new HashSet<String>(Arrays.asList("TBSC", "TOPDHSC", "TOPFENXIAO",
                              "TOPFENXIAODHSC", "TOPBUYBANG", "TOPBOILER", "TOPSHJD", "TONGSHUAI",
                              "TOPXB", "TOPFENXIAOXB"));

    @Override
    public void countTimeOut(List<Map<String, Object>> dataList) {
        if (null == dataList || dataList.isEmpty()) {
            return;
        }
        Set<Long> orsIdSet = new HashSet<Long>();
        Integer handleStatus = null;
        long handleTime = 0;
        long endTime = 0;
        long paymentTime = 0;
        long realTime = 0;
        Long addtime = null;
        String source = null;
        Calendar realCalendar = null;
        Calendar mustCalendar = null;
        Date now = new Date();
        int timeoutType = 0;
        boolean isTmall = false;
        Map<String, Object> resultMap = null;
        for (Map<String, Object> map : dataList) {
            //计算超时
            this.countTimeOutEachOne(orsIdSet, handleStatus, handleTime, endTime, paymentTime,
                realTime, addtime, source, realCalendar, mustCalendar, now, timeoutType, isTmall,
                map, resultMap);
        }
    }

    @Override
    public void countTimeOutWithFilter(List<Map<String, Object>> dataList) {
        if (null == dataList || dataList.isEmpty()) {
            return;
        }
        Set<Long> orsIdSet = new HashSet<Long>();
        Integer handleStatus = null;
        long handleTime = 0;
        long endTime = 0;
        long paymentTime = 0;
        long realTime = 0;
        Long addtime = null;
        String source = null;
        Calendar realCalendar = null;
        Calendar mustCalendar = null;
        Date now = new Date();
        int timeoutType = 0;
        boolean isTmall = false;
        Integer typeActual = null;
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
            typeActual = NumberParseUtil.parseInteger(map.get("typeActual"));
            if (typeActual != 1) {
                continue;
            }
            /** 过滤数据结束**/
            //计算超时
            this.countTimeOutEachOne(orsIdSet, handleStatus, handleTime, endTime, paymentTime,
                realTime, addtime, source, realCalendar, mustCalendar, now, timeoutType, isTmall,
                map, resultMap);
        }
    }

    private void countTimeOutEachOne(Set<Long> orsIdSet, Integer handleStatus, long handleTime,
                                     long endTime, long paymentTime, long realTime, Long addtime,
                                     String source, Calendar realCalendar, Calendar mustCalendar,
                                     Date now, int timeoutType, boolean isTmall,
                                     Map<String, Object> map, Map<String, Object> resultMap) {
        Long orsId = NumberParseUtil.parseLong(map.get("id"));
        if (orsIdSet.contains(orsId)) {
            return;
        }
        orsIdSet.add(orsId);
        handleStatus = NumberParseUtil.parseInteger(map.get("handleStatus"));
        //handleStatus=5 已驳回
        if (handleStatus == 5) {
            //审核驳回时间
            handleTime = NumberParseUtil.parseLong(map.get("handleTime")).longValue();
        } else {
            handleTime = 0l;
        }
        //终止时间
        endTime = NumberParseUtil.parseLong(map.get("endTime")).longValue();
        //已退款时间
        paymentTime = NumberParseUtil.parseLong(map.get("paymentTime")).longValue();
        //订单来源-渠道
        source = (String) map.get("source");
        //实际处理时间,取已退款时间,审核驳回时间,终止时间三个里面最小不为0的时间
        realTime = NumberParseUtil.getMinimumExceptZero(paymentTime, handleTime, endTime);
        isTmall = false;
        /**
         * 应：应完成时间=【申请退款时间】+3天（剔除【退换货类型】为“退货不退款”的情况）；
         * 实：【已退款时间】、【审核驳回时间】、【终止时间】任意字段有值最早时间且小于应完成时间为实，否则为差；
         * ①非天猫渠道的直接控制中心取数，②天猫渠道的目前未对接数据获取不到，如果【审核驳回时间】、【终止时间】全为空，先按实计算
         */
        addtime = (Long) map.get("addTime");
        realCalendar = Calendar.getInstance();
        mustCalendar = Calendar.getInstance();
        //判断是否天猫渠道
        isTmall = sourceSet.contains(source);
        if (realTime > 0) {
            realCalendar.setTimeInMillis(realTime * 1000l);
        } else {
            if (isTmall) {
                //如果是天猫渠道来源订单，则设置实际完成时间为申请时间，需求规定这部分已完成，即应、实、差、里的“实”
                realCalendar.setTimeInMillis(addtime * 1000l);
            } else {
                realCalendar.setTime(now);
            }
        }
        mustCalendar.setTime(new Date(addtime * 1000L));
        //申请退货成功后3天内完成
        mustCalendar.add(Calendar.DAY_OF_YEAR, 3);

        resultMap = new HashMap<String, Object>();
        map.put(ReportType.REFUND, resultMap);
        //应完成时间
        //map.put("mustDate", DateFormatUtil.format(mustCalendar.getTime()));
        resultMap.put("mustDate", mustCalendar);
        //实际完成时间,如果等于0设置为空
        if (realTime == 0) {
            resultMap.put("realDate", null);
        } else {
            //map.put("realDate", DateFormatUtil.format(realCalendar.getTime()));
            resultMap.put("realDate", realCalendar);
        }
        if (isTmall) {
            if (realTime > 0 && realCalendar.after(mustCalendar)) {
                //超时天数
                resultMap.put("outDay",
                    (realCalendar.getTimeInMillis() - mustCalendar.getTimeInMillis()) / DAY_MILLIS);
                timeoutType = FINISH_TIMEOUT;
            } else {
                resultMap.put("outDay", 0);
                timeoutType = FINISH_NOT_TIMEOUT;
            }
        } else {
            if (realCalendar.after(mustCalendar)) {
                //超时天数
                resultMap.put("outDay",
                    (realCalendar.getTimeInMillis() - mustCalendar.getTimeInMillis()) / DAY_MILLIS);
                timeoutType = realTime > 0 ? FINISH_TIMEOUT : NOT_FINISH_TIMEOUT;
            } else {
                resultMap.put("outDay", 0);
                timeoutType = realTime > 0 ? FINISH_NOT_TIMEOUT : NOT_FINISH_NOT_TIMEOUT;
            }
        }
        //超时类型
        resultMap.put("timeoutType", timeoutType);
    }
}
