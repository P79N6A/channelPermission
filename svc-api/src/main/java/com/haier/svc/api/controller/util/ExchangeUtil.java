package com.haier.svc.api.controller.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.haier.common.PagerInfo;

/**
 * 公用方法
 *
 * @Filename ExchangeUtil.java
 *
 * @Description
 *
 * @Version 1.0
 *
 * @Author zhaozj
 *
 * @Email zhiju.zhao@rogrand.com
 *
 * @History
 *<li>Author:  zhaozj</li>
 *<li>Date: 2013年12月17日</li>
 *<li>Version: 1.0</li>
 *<li>Content: create</li>
 *
 */
public class ExchangeUtil {
    public static Map<String, String> getSourceMap(List<Map<String, Object>> channels) {
        Map<String, String> mapSource = new HashMap<String, String>();
        if (CollectionUtils.isNotEmpty(channels)) {
            int length = channels.size();
            for (int k = 0; k < length; k++) {
                Map<String, Object> info = channels.get(k);
                mapSource.put(info.get("order_source").toString(), info.get("note").toString());
            }
        }
        return mapSource;
    }

    /**
     * 获取渠道名称
     */
    public static String getSourceName(String sourceIds, Map<String, String> mapSource) {
        StringBuffer sbStr = new StringBuffer();
        if (StringUtils.isNotBlank(sourceIds)) {
            if (sourceIds.indexOf(",") != -1) { // 多个
                String[] strArr = sourceIds.split(",");
                for (int i = 0; i < strArr.length; i++) {
                    if (StringUtils.isBlank(sbStr.toString())) {
                        sbStr.append(mapSource.get(strArr[i]));
                    } else {
                        sbStr.append(",");
                        sbStr.append(mapSource.get(strArr[i]));
                    }

                }
            } else { // 单个
                if (mapSource.containsKey(sourceIds)) {
                    sbStr.append(mapSource.get(sourceIds));
                } else {
                    sbStr.append("无渠道名称");
                }

            }
        }
        return sbStr.toString();
    }

    /**
     * 获取渠道名称
     */
    public static String getSourceName(String sourceIds, List<Map<String, Object>> channels) {
        StringBuffer sbStr = new StringBuffer();
        Map<String, String> mapSource = new HashMap<String, String>();
        if (CollectionUtils.isNotEmpty(channels)) {
            int length = channels.size();
            for (int k = 0; k < length; k++) {
                Map<String, Object> info = channels.get(k);
                mapSource.put(info.get("order_source").toString(), info.get("note").toString());
            }
        }

        if (StringUtils.isNotBlank(sourceIds)) {
            if (sourceIds.indexOf(",") != -1) { // 多个
                String[] strArr = sourceIds.split(",");
                for (int i = 0; i < strArr.length; i++) {
                    if (StringUtils.isBlank(sbStr.toString())) {
                        sbStr.append(mapSource.get(strArr[i]));
                    } else {
                        sbStr.append(",");
                        sbStr.append(mapSource.get(strArr[i]));
                    }

                }
            } else { // 单个
                if (mapSource.containsKey(sourceIds)) {
                    sbStr.append(mapSource.get(sourceIds));
                } else {
                    sbStr.append("无渠道名称");
                }

            }
        }

        return sbStr.toString();
    }

    /**
     * 字典项列转行，方便代码转换名称使用
     * @param sourceList
     * @return
     */
    public static Map<String, String> getDictListToMap(List<Map<String, Object>> sourceList,
                                                       String keyfield, String valuefield) {
        Map<String, String> mapSource = new HashMap<String, String>();
        if (CollectionUtils.isNotEmpty(sourceList)) {
            int length = sourceList.size();
            for (int k = 0; k < length; k++) {
                Map<String, Object> info = sourceList.get(k);
                if (info.get(valuefield) != null) {
                    mapSource.put(info.get(keyfield).toString(), info.get(valuefield).toString());
                } else {
                    mapSource.put(info.get(keyfield).toString(), "");
                }
            }
        }
        return mapSource;
    }

    /**
     * 字典项列转行，方便代码转换名称使用(用于一次得到两个value值的情况)
     * @param sourceList
     * @return
     */
    public static Map<String, String> getDictListToMap(List<Map<String, Object>> sourceList,
                                                       String keyfield, String valuefield1, String valuefield2) {
        Map<String, String> mapSource = new HashMap<String, String>();
        if (CollectionUtils.isNotEmpty(sourceList)) {
            int length = sourceList.size();
            for (int k = 0; k < length; k++) {
                Map<String, Object> info = sourceList.get(k);
                if (info.get(valuefield1) != null && info.get(valuefield2) != null) {
                    mapSource.put(info.get(keyfield).toString(), info.get(valuefield1).toString()+"%"+info.get(valuefield2).toString());
                } else {
                    mapSource.put(info.get(keyfield).toString(), "");
                }
            }
        }
        return mapSource;
    }

    public static Integer dateToInteger(String str) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date date = format.parse(str);
        return ((Long) (date.getTime() / 1000)).intValue();
    }

    /**
     * 翻译库位
     */
    public static String getIsCod(Boolean isCod) {
        if (isCod)
            return "是";
        return "否";
    }

    /**
     * 格式化时间
     */
    public static String formatTime(Object time) {
        Long tempTime = 0L;
        if (time instanceof Integer)
            tempTime = ((Integer) time).longValue();
        else if (time instanceof Long)
            tempTime = ((Long) time);
        if (tempTime == 0)
            return tempTime.toString();
        Date date = new Date(tempTime * 1000);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = format.format(date);
        return str;
    }

    /**
     * 将yyyyMMdd格式化为yyyy-MM-dd
     */
    public static String formatReservationDate(String date) {
        if (date.length() < 8)
            return date;
        return date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6, 8);
    }

    /**
     * 将hhiiss格式化为hh:ii:ss
     */
    public static String formatReservationTime(String time) {
        if (time.length() < 6)
            return time;
        return time.substring(0, 2) + ":" + time.substring(2, 4) + ":" + time.substring(4, 6);
    }

    /**
     * 翻译网单状态
     */
    public static String formatStatus(Integer value) {
        if (value == 0)
            return "初始状态";
        else if (value == 2)
            return "同步到HP";
        else if (value == 4)
            return "已分配网点";
        else if (value == 8)
            return "LES 开提单, 待出库";
        else if (value == 10)
            return "待审核";
        else if (value == 11)
            return "待转运入库";
        else if (value == 12)
            return "待转运出库";
        else if (value == 40)
            return "待发货";
        else if (value == 70)
            return "待交付";
        else if (value == 110)
            return "取消关闭";
        else if (value == 130)
            return "完成关闭 ";
        else if (value == 140)
            return "用户签收";
        else if (value == 150)
            return "网点拒单";
        else if (value == 160)
            return "用户拒收";
        else if (value == 170)
            return "用户取消";
        else if (value == 180)
            return "无法执行";
        return value.toString();
    }

    /**
     * 翻译退换货类型
     */
    public static String formatTypeActual(Integer value) {
        if (value == null)
            return "";
        if (value == 1)
            return "退货退款";
        else if (value == 2)
            return "退货不退款";
        return value.toString();
    }

    /**
     * 翻译退款状态
     */
    public static String formatPaymentStatus(Integer value) {
        if (value == 1)
            return "已付款";
        else if (value == 2)
            return "待退款";
        else if (value == 3)
            return "已退款";
        else if (value == 4)
            return "线下已退款";
        else if (value == 5)
            return "无需退款";
        return value.toString();
    }

    /**
     * 翻译货物状态
     */
    public static String formatStorageStatus(Integer value) {
        if (value == 1)
            return "已出库";
        else if (value == 2)
            return "未出库";
        else if (value == 3)
            return "已召回";
        else if (value == 4)
            return "已入库";
        else if (value == 10)
            return "待召回";
        else if (value == 122)
            return "已入22库";
        else if (value == 121)
            return "已入21库";
        else if (value == 110)
            return "已入10库";
        else if (value == 221)
            return "已入日日顺21库";
        return value.toString();
    }

    /**
     * 翻译发票状态
     */
    public static String formatInvoiceStatus(Integer value) {
        if (value == null)
            return "";
        else if (value == 1)
            return "已开票";
        else if (value == 2)
            return "未开票";
        else if (value == 3)
            return "已召回";
        else if (value == 4)
            return "已冲票";
        else if (value == 10)
            return "待召回";
        return value.toString();
    }

    public static String formatCheckResult(Integer value) {
        if (value == null)
            return "";
        else if (value == 0)
            return "咨询结单";
        else if (value == 1)
            return "符合";
        else if (value == 2)
            return "不符合";
        return value.toString();
    }

    public static String formatIsTimeoutFree(Long isTimeoutFree) {
        if (isTimeoutFree == null)
            return "";
        else if (isTimeoutFree == 0)
            return "否";
        else if (isTimeoutFree == 1)
            return "是";
        else if (isTimeoutFree == 2)
            return "否";
        return isTimeoutFree.toString();
    }

    public static String formatHandleStatus(Long value) {
        if (value == null)
            return "";
        else if (value == 1)
            return "审核中";
        else if (value == 2)
            return "进行中";
        else if (value == 3)
            return "受理完成";
        else if (value == 4)
            return "已完结";
        else if (value == 5)
            return "已驳回";
        else if (value == 6)
            return "已终止";
        else if (value == 7)
            return "线下已退款";
        return value.toString();
    }

    public static void addPagerParam(Map<String, Object> modelMap, PagerInfo pager) {
        Integer totalPage = pager.getRowsCount() % pager.getPageSize() == 0 ? pager.getRowsCount()
                / pager.getPageSize()
                : pager.getRowsCount() / pager.getPageSize() + 1;
        modelMap.put("totalCount", pager.getRowsCount());
        if (pager.getPageIndex() > 1)
            modelMap.put("hasFirst", true);
        else
            modelMap.put("hasFirst", false);
        if (pager.getPageIndex() - 1 > 0)
            modelMap.put("hasPrevious", true);
        else
            modelMap.put("hasPrevious", false);
        if (pager.getPageIndex() + 1 <= totalPage)
            modelMap.put("hasNext", true);
        else
            modelMap.put("hasNext", false);
        if (totalPage > 1 && pager.getPageIndex() != totalPage)
            modelMap.put("hasLast", true);
        else
            modelMap.put("hasLast", false);
        modelMap.put("curPage", totalPage > 0 ? pager.getPageIndex() : 0);
        modelMap.put("totalPage", totalPage);
        modelMap.put("startNum", pager.getStart() + 1);
        modelMap
                .put(
                        "endNum",
                        (pager.getStart() + pager.getPageSize()) > pager.getRowsCount() ? (pager.getStart() + pager
                                .getRowsCount() % pager.getPageSize())
                                : pager.getStart() + pager.getPageSize());
    }
}
