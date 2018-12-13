package com.haier.afterSale.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.haier.afterSale.util.BeanUtil;
import com.haier.afterSale.service.ICountTimeOut;
import com.haier.afterSale.util.DateFormatUtil;
import com.haier.afterSale.util.NumberParseUtil;
import com.haier.shop.service.OrderWorkFlowModelDataService;
import com.haier.afterSale.service.ReportType;
import org.apache.commons.lang.StringUtils;

/**
 * 妥投率超时计算实现类
 * 
 * @Filename: OwfCountTimeOutImpl.java
 * @Version: 1.0
 * @Author: yaoyu
 * @Email: yaoyu@ehaier.com
 *
 */
public class OwfCountTimeOutImpl implements ICountTimeOut {

    ThreadLocal<SimpleDateFormat> tParse = new ThreadLocal<SimpleDateFormat>();

    @Override
    public void countTimeOut(List<Map<String, Object>> dataList) {
        if (null == dataList || dataList.isEmpty()) {
            return;
        }
        Integer status = null;
        Long productType = null;
        Integer orderType = null;
        Integer shippingTime = null;
        Boolean isCod = null;
        Long payTime = null;
        Long codConfirmTime = null;
        Long tailPayTime = null;
        Long hpReservationDate = null;
        String date = null;
        String time = null;
        Long userAcceptTime = null;
        Long orsAddTime = null;
        Long finishCloseTime = null;
        Calendar realCalendar = null;
        Calendar mustCalendar = null;
        Date now = new Date();
        int hour = 0;
        int timeoutType = 0;
        Map<String, Object> resultMap = null;
        for (Map<String, Object> map : dataList) {
            //计算超时
            this.countTimeOutEachOne(status, productType, orderType, shippingTime, isCod, payTime,
                    codConfirmTime, tailPayTime, hpReservationDate, date, time, userAcceptTime,
                    orsAddTime, finishCloseTime, realCalendar, mustCalendar, now, hour, timeoutType,
                    map, resultMap);
        }

    }

    @Override
    public void countTimeOutWithFilter(List<Map<String, Object>> dataList) {
        if (null == dataList || dataList.isEmpty()) {
            return;
        }
        Integer status = null;
        Long productType = null;
        Integer orderType = null;
        Integer shippingTime = null;
        Boolean isCod = null;
        Long payTime = null;
        Long codConfirmTime = null;
        Long tailPayTime = null;
        Long hpReservationDate = null;
        String date = null;
        String time = null;
        Long userAcceptTime = null;
        Long orsAddTime = null;
        Long finishCloseTime = null;
        Calendar realCalendar = null;
        Calendar mustCalendar = null;
        Date now = new Date();
        int hour = 0;
        int timeoutType = 0;
        Map<String, Object> resultMap = null;
        for (Map<String, Object> map : dataList) {
            /** 过滤数据开始**/
            if (!"WA".equalsIgnoreCase(map.get("stockType").toString())) {
                continue;
            }
            //如果来源是"样品机"剔除掉
            if ("COS".equals(map.get("source"))) {
                continue;
            }
            orderType = NumberParseUtil.parseInteger(map.get("orderType"));
            tailPayTime = NumberParseUtil.parseLong(map.get("tailPayTime"));
            if ((orderType == 1 || orderType == 4) && tailPayTime == 0) {
                continue;
            }
            payTime = NumberParseUtil.parseLong(map.get("payTime"));
            isCod = NumberParseUtil.getBoolean(map.get("isCod"));
            codConfirmTime = NumberParseUtil.parseLong(map.get("codConfirmTime"));
            if (!isCod && payTime == 0) {
                continue;
            } else if (isCod && codConfirmTime == 0) {
                continue;
            }

            //加判断逻辑天猫官方旗舰店渠道、日日单模式订单，符合这两个维度的订单将起点时间【付款时间】改为【同步HP时间】，其他标准不变；
            //2015-04-16修改   日日单不限制渠道
            //2015-04-29改动 起点时间【同步HP时间】
            if (NumberParseUtil.parseLong(map.get("hpRegisterDate")) == 0l) {
                continue;
            }

            if (NumberParseUtil.parseInteger(map.get("orderStatus")) == 200
                    || NumberParseUtil.parseInteger(map.get("orderStatus")) == 204) {
                continue;
            }

            /** 过滤数据结束**/
            //计算超时
            this.countTimeOutEachOne(status, productType, orderType, shippingTime, isCod, payTime,
                    codConfirmTime, tailPayTime, hpReservationDate, date, time, userAcceptTime,
                    orsAddTime, finishCloseTime, realCalendar, mustCalendar, now, hour, timeoutType,
                    map, resultMap);
        }
    }

    private void countTimeOutEachOne(Integer status, Long productType, Integer orderType,
                                     Integer shippingTime, Boolean isCod, Long payTime,
                                     Long codConfirmTime, Long tailPayTime, Long hpReservationDate,
                                     String date, String time, Long userAcceptTime, Long orsAddTime,
                                     Long finishCloseTime, Calendar realCalendar,
                                     Calendar mustCalendar, Date now, int hour, int timeoutType,
                                     Map<String, Object> map, Map<String, Object> resultMap) {
        status = NumberParseUtil.parseInteger(map.get("status"));
        productType = (Long) map.get("productType");
        orderType = NumberParseUtil.parseInteger(map.get("orderType"));
        shippingTime = NumberParseUtil.parseInteger(map.get("shippingTime"));
        shippingTime = ((OrderWorkFlowModelDataService) BeanUtil.getBean("orderWorkFlowModelDataService"))
                .formatShippingTime(shippingTime, NumberParseUtil.parseLong(map.get("region")), 0);
        isCod = NumberParseUtil.getBoolean(map.get("isCod"));
        //        payTime = NumberParseUtil.parseLong(map.get("payTime"));
        //加判断逻辑天猫官方旗舰店渠道、日日单模式订单，符合这两个维度的订单将起点时间【付款时间】改为【同步HP时间】，其他标准不变；
        //2015-04-29改动 起点时间【同步HP时间】
        //        Boolean isTmallDayOrderFlg = false;
        //        //2015-04-16修改   日日单不限制渠道
        //        if (0l != NumberParseUtil.parseLong(map.get("pdOrderStatus"))) {
        //            isTmallDayOrderFlg = true;
        //            //            payTime = NumberParseUtil.parseLong(map.get("hpRegisterDate"));
        //        }
        //        codConfirmTime = NumberParseUtil.parseLong(map.get("codConfirmTime"));
        tailPayTime = NumberParseUtil.parseLong(map.get("tailPayTime"));
        //新逻辑取尾款时间与同步HP时间中大的值
        //        Long confirmTime = NumberParseUtil.parseLong(map.get("confirmTime"));
        Long hpRegisterDate = NumberParseUtil.parseLong(map.get("hpRegisterDate"));
        if (tailPayTime < hpRegisterDate) {
            tailPayTime = hpRegisterDate;
        }
        //网点改约时间
        hpReservationDate = NumberParseUtil.parseLong(map.get("hpReservationDate"));
        date = (String) map.get("date");
        time = (String) map.get("time");

        //如果是COD订单,还是用用户签收时间
        //如果是非COD订单,将目前的用户签收时间变为完成关闭时间来判断
        //订单来源：不良品换货   结束时间同货到付款一致（取用户签收时间为最终考核时间）
        if ((isCod || "BLPHH".equalsIgnoreCase(map.get("source").toString()))) {
            userAcceptTime = NumberParseUtil.parseLong(map.get("userAcceptTime"));
        } else {
            userAcceptTime = NumberParseUtil.parseLong(map.get("finishCloseTime"));
        }

        realCalendar = Calendar.getInstance();
        mustCalendar = Calendar.getInstance();
        orsAddTime = NumberParseUtil.parseLong(map.get("orsAddTime"));
        finishCloseTime = "110".equals(map.get("status").toString())
                ? NumberParseUtil.parseLong(map.get("finishCloseTime")) : 0l;
        //退换货创建时间,取消关闭时间,和实际完成时间比较,取最小值;都为空取系统时间
        userAcceptTime = NumberParseUtil.getMinimumExceptZero(userAcceptTime, orsAddTime,
                finishCloseTime);
        if (userAcceptTime == 0) {
            realCalendar.setTime(now);
        } else {
            realCalendar.setTimeInMillis(userAcceptTime * 1000l);
        }
        //订金尾款订单:网点改约>默认时间(T日17:30前付尾款，应送达用户时间T+N日20点;T日17:30后付尾款，应送达用户时间T+N+1日20点)
        //其它订单：网点改约>用户预约>默认时间(T日17:30前付款或者审核，应送达用户时间T+N日20点;T日17:30后付款或者审核，应送达用户时间T+N+1日20点)

        //库存不足产生新情况，如果下单后一直没库存，过了很久有库存，但是按照原逻辑一抓取出来就入差了
        //新逻辑
        //订金尾款订单:网点改约>默认时间(T日17:00前付尾款，（尾款时间与同步HP时间比较，取大的时间）
        //              应送达用户时间T+N日20点;T日17:00后付尾款，应送达用户时间T+N+1日20点)
        //      配送时效0天，T日11:00前付尾款，（尾款时间与同步HP时间比较，取较晚的时间）应送达用户时间T日22点;T日11:00后支付尾款，
        //                   应送达用户时间T+1日22点
        //      配送时效大于等于1天 (T日17:00前付尾款，（尾款时间与同步HP时间比较，取大的时间）应送达用户时间T+N日20点;T日17:00后付尾款，
        //              应送达用户时间T+N+1日20点)
        //其它订单：网点改约>用户预约>默认时间(T日17:00前订单同步HP，应送达用户时间T+N日20点;T日17:00后订单同步HP，应送达用户时间T+N+1日20点)
        //      配送时效等于0天的 T日11:00前订单同步HP，应送达用户时间T日22点;T日11:00后订单同步HP，应送达用户时间T+1日22点
        //      配送时效大于等于1天 (T日17:00前订单同步HP，应送达用户时间T+N日20点;T日17:00后订单同步HP，应送达用户时间T+N+1日20点)

        //终点逻辑变更:系统分配应完成时间、HP一次预约时间、HP二次改约时间，如HP一次操作预约时间大于系统应完成时间或者HP二次操作改约时间大于HP一次预约时间，此WD单号妥投记录为差。

        //        if (hpReservationDate > 0) {
        //            mustCalendar.setTimeInMillis(hpReservationDate * 1000l);
        //        } else {
        if (orderType == 1 || orderType == 4) {
            mustCalendar.setTimeInMillis(tailPayTime * 1000l);
            hour = mustCalendar.get(Calendar.HOUR_OF_DAY);
            if (shippingTime.intValue() == 0) {
                if (hour >= 11) {
                    mustCalendar.add(Calendar.DATE, 1);
                }
                mustCalendar.set(Calendar.HOUR_OF_DAY, 22);
            } else {
                if (hour >= 17)
                    mustCalendar.add(Calendar.DATE, shippingTime + 1);
                else
                    mustCalendar.add(Calendar.DATE, shippingTime);
                mustCalendar.set(Calendar.HOUR_OF_DAY, 20);
            }
            //            if (hour >= 17) {
            //                shippingTime += 1;
            //            }
            //            mustCalendar.add(Calendar.DAY_OF_YEAR, shippingTime);
            //            mustCalendar.set(Calendar.HOUR_OF_DAY, 20);
            mustCalendar.set(Calendar.MINUTE, 0);
            mustCalendar.set(Calendar.SECOND, 0);
        } else {
            if (StringUtils.isNotBlank(date)) {
                if (StringUtils.isBlank(time)) {
                    time = "000000";
                }
                mustCalendar.setTime(DateFormatUtil.parseByType("yyyyMMddHHmmss", date + time));
                //                if (mustCalendar.getTimeInMillis() / 1000 <= hpRegisterDate
                //                    || (hpRegisterDate + 86400 * shippingTime > mustCalendar.getTimeInMillis() / 1000)) {
                //                    //                        if (isTmallDayOrderFlg) {
                //                    //                            mustCalendar.setTimeInMillis(NumberParseUtil.parseLong(map
                //                    //                                .get("hpRegisterDate")) * 1000l);
                //                    //                        } else {
                //                    mustCalendar.setTimeInMillis(hpRegisterDate * 1000l);
                //                    //                        }
                //                    hour = mustCalendar.get(Calendar.HOUR_OF_DAY);
                //                    if (hour >= 17) {
                //                        shippingTime += 1;
                //                    }
                //                    mustCalendar.add(Calendar.DAY_OF_YEAR, shippingTime);
                //                    mustCalendar.set(Calendar.HOUR_OF_DAY, 20);
                //                    mustCalendar.set(Calendar.MINUTE, 0);
                //                    mustCalendar.set(Calendar.SECOND, 0);
                //                }
                long bookTime = mustCalendar.getTimeInMillis() / 1000;
                mustCalendar.setTimeInMillis(hpRegisterDate * 1000l);
                hour = mustCalendar.get(Calendar.HOUR_OF_DAY);
                if (shippingTime.intValue() == 0) {
                    if (hour >= 11) {
                        mustCalendar.add(Calendar.DATE, 1);
                    }
                    mustCalendar.set(Calendar.HOUR_OF_DAY, 22);
                } else {
                    if (hour >= 17)
                        mustCalendar.add(Calendar.DATE, shippingTime + 1);
                    else
                        mustCalendar.add(Calendar.DATE, shippingTime);
                    mustCalendar.set(Calendar.HOUR_OF_DAY, 20);
                }
                //                if (hour >= 17) {
                //                    shippingTime += 1;
                //                }
                //                mustCalendar.add(Calendar.DAY_OF_YEAR, shippingTime);
                //                mustCalendar.set(Calendar.HOUR_OF_DAY, 20);
                mustCalendar.set(Calendar.MINUTE, 0);
                mustCalendar.set(Calendar.SECOND, 0);
                long rightTime = mustCalendar.getTimeInMillis() / 1000;
                //如果标准时间在后，取标准时间，在前，取预约时间
                if (rightTime > bookTime) {
                    mustCalendar.setTimeInMillis(rightTime * 1000);
                } else {
                    mustCalendar.setTimeInMillis(bookTime * 1000);
                }
            } else {
                //                    if (isCod) {
                //                        mustCalendar.setTimeInMillis(codConfirmTime * 1000l);
                //                    } else {
                //                        mustCalendar.setTimeInMillis(payTime * 1000l);
                //                    }
                //                    if (isTmallDayOrderFlg) {
                //                        mustCalendar.setTimeInMillis(NumberParseUtil.parseLong(map
                //                            .get("hpRegisterDate")) * 1000l);
                //                    } else {
                mustCalendar.setTimeInMillis(hpRegisterDate * 1000l);
                //                    }
                hour = mustCalendar.get(Calendar.HOUR_OF_DAY);
                if (shippingTime.intValue() == 0) {
                    if (hour >= 11) {
                        mustCalendar.add(Calendar.DATE, 1);
                    }
                    mustCalendar.set(Calendar.HOUR_OF_DAY, 22);
                } else {
                    if (hour >= 17)
                        mustCalendar.add(Calendar.DATE, shippingTime + 1);
                    else
                        mustCalendar.add(Calendar.DATE, shippingTime);
                    mustCalendar.set(Calendar.HOUR_OF_DAY, 20);
                }
                //                if (hour >= 17) {
                //                    shippingTime += 1;
                //                }
                //                mustCalendar.add(Calendar.DAY_OF_YEAR, shippingTime);
                //                mustCalendar.set(Calendar.HOUR_OF_DAY, 20);
                mustCalendar.set(Calendar.MINUTE, 0);
                mustCalendar.set(Calendar.SECOND, 0);
            }
        }
        //        }

        //如果来源是"样品机",应完成时间再加2天
        if ("COS".equals(map.get("source"))) {
            mustCalendar.add(Calendar.DATE, 2);
        }

        //加上安装时间
        addInstallTime(productType, mustCalendar);

        //2015-04-29改动
        if (hpReservationDate > 0) {
            List<Map<String, Object>> gaiyueList = ((OrderWorkFlowModelDataService) BeanUtil
                    .getBean("orderWorkFlowModelDataService"))
                    .getGaiyueInfo(NumberParseUtil.parseLong(map.get("orderProductId")));
            if (gaiyueList != null && gaiyueList.size() > 0) {
                if (gaiyueList.size() == 1) {
                    //改约一次
                    //HP一次操作预约时间大于系统应完成时间
                    if (NumberParseUtil.parseLong(
                            gaiyueList.get(0).get("logTime")) <= mustCalendar.getTimeInMillis()
                            / 1000) {
                        //如果在规定时间内改约，应完成时间要变
                        mustCalendar.setTime(new Date(hpReservationDate * 1000L));
                        //加上安装时间
                        addInstallTime(productType, mustCalendar);
                    }
                } else if (gaiyueList.size() >= 2) {
                    //超过3次，取前2次
                    //一次改约
                    //HP一次操作预约时间大于系统应完成时间
                    if (NumberParseUtil.parseLong(
                            gaiyueList.get(0).get("logTime")) <= mustCalendar.getTimeInMillis()
                            / 1000) {
                        //如果在一次在规定时间内改约，应完成时间要变
                        //                        Long firstGT = parseDate(gaiyueList.get(0).get("remark").toString()
                        //                            .replace("HP系统回传预约送货时间：", ""));
                        Long firstGT = NumberParseUtil
                                .parseLong(gaiyueList.get(0).get("gaiyueTime"));
                        mustCalendar.setTime(new Date(firstGT * 1000L));
                        //加上安装时间
                        addInstallTime(productType, mustCalendar);
                        if (NumberParseUtil.parseLong(
                                gaiyueList.get(1).get("logTime")) <= mustCalendar.getTimeInMillis()
                                / 1000) {
                            //二次改约
                            //HP二次操作改约时间大于HP一次预约时间
                            //如果二次在规定时间内改约，应完成时间要变
                            mustCalendar.setTime(new Date(hpReservationDate * 1000L));
                            //加上安装时间
                            addInstallTime(productType, mustCalendar);
                        }
                    }
                }
            }
        }

        Integer handleStatus =  NumberParseUtil.parseInteger(map.get("handleStatus"));
        //对于非货到付款订单，逆向WDTH单被驳回或终止的，需要继续在妥投率 、O2O转单妥投率报表中进行展示
        //时效计算开始时间取最后一笔WDTH订单的被驳回或终止的时间进行计算
        if(!isCod && (handleStatus==5 || handleStatus==6)){
            Long handleTime = NumberParseUtil.parseLong(map.get("handleTime"));
            mustCalendar.setTimeInMillis(handleTime * 1000l);
            //加上安装时间
            addInstallTime(productType, mustCalendar);
        }

        resultMap = new HashMap<String, Object>();
        map.put(ReportType.OWF, resultMap);
        //应完成时间
        //map.put("mustDate", DateFormatUtil.format(mustCalendar.getTime()));
        resultMap.put("mustDate", mustCalendar);
        //实际完成时间,如果等于0设置为空
        if (userAcceptTime == 0) {
            resultMap.put("realDate", null);
        } else {
            //map.put("realDate", DateFormatUtil.format(realCalendar.getTime()));
            resultMap.put("realDate", realCalendar);
        }

        if (realCalendar.after(mustCalendar)) {
            //超时天数
            resultMap.put("outDay",
                    (realCalendar.getTimeInMillis() - mustCalendar.getTimeInMillis()) / DAY_MILLIS);
            if (status > 70) {
                timeoutType = FINISH_TIMEOUT;
            } else {
                timeoutType = userAcceptTime > 0 ? FINISH_TIMEOUT : NOT_FINISH_TIMEOUT;
            }
        } else {
            resultMap.put("outDay", 0);
            if (status > 70 || userAcceptTime > 0) {
                timeoutType = FINISH_NOT_TIMEOUT;
            } else {
                timeoutType = NOT_FINISH_NOT_TIMEOUT;
            }
        }

        //超时类型
        resultMap.put("timeoutType", timeoutType);
    }

    /**
     * 日期转换成数值
     * @param date
     * @return str
     * @throws ParseException
     */
    private Long parseDate(String date) {
        if (null == tParse.get()) {
            tParse.set(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS+08:00"));
        }
        try {
            return tParse.get().parse(date).getTime() / 1000;
        } catch (ParseException e) {
            return 0l;
        }
    }

    /**
     * 加上安装时间
     * @param productType
     * @param userMustCalendar
     */
    private void addInstallTime(Long productType, Calendar userMustCalendar) {
        //空调、热水器5个小时包含5个小时的安装时间，其它电器3个小时
        //113空调;118商用空调;148空调面板;39电热水器;40燃气热水器;42空气能热水器;127太阳能热水器配件;
        //安装时间超过24点按24点计算
        if (productType == 113 || productType == 118 || productType == 148 || productType == 39
                || productType == 40 || productType == 42 || productType == 127) {
            userMustCalendar.add(Calendar.HOUR_OF_DAY, 5);
            if (userMustCalendar.get(Calendar.HOUR_OF_DAY) < 5) {
                userMustCalendar.add(Calendar.DATE, -1);
                userMustCalendar.set(Calendar.HOUR_OF_DAY, 23);
                userMustCalendar.set(Calendar.MINUTE, 59);
                userMustCalendar.set(Calendar.SECOND, 59);
            }
        } else {
            userMustCalendar.add(Calendar.HOUR_OF_DAY, 3);
            if (userMustCalendar.get(Calendar.HOUR_OF_DAY) < 3) {
                userMustCalendar.add(Calendar.DATE, -1);
                userMustCalendar.set(Calendar.HOUR_OF_DAY, 23);
                userMustCalendar.set(Calendar.MINUTE, 59);
                userMustCalendar.set(Calendar.SECOND, 59);
            }
        }
    }

}
