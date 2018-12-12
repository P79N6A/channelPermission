package com.haier.order.model;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.haier.order.services.OrderCenterStockCommonServiceImpl;
import com.haier.order.util.DateFormatUtil;
import com.haier.shop.model.*;
import com.haier.shop.service.RegionsService;
import com.haier.shop.service.ReservationShippingService;
import com.haier.shop.service.ShopOrderWorkflowsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Administrator on 2017/12/14 0014.
 */
@Service("countTimePubModel")
public class CountTimePubModel {
    @Autowired
    private RegionsService regionsService;
    @Autowired
    private ReservationShippingService reservationShippingService;
    @Autowired
    private ShopOrderWorkflowsService orderWorkflowsService;
    @Autowired
    private StockServiceModel stockServiceImpl;
    @Autowired
    private OrderCenterStockCommonServiceImpl orderThirdCenterStockCommonService;

    private Set<Integer> productTypeSet = new HashSet<Integer>();

    private ThreadLocal<Map<String, BigStoragesRela>> bigStoragesRelaMap = new ThreadLocal<Map<String, BigStoragesRela>>();

    private static org.apache.log4j.Logger log = org.apache.log4j.LogManager
            .getLogger(CountTimePubModel.class);


    /**
     * 计算应送达网点时间
     *
     * @param order
     * @param orderProduct
     * @param mustNetPointAcceptTime
     * @return
     */
    public Long countMustNetPointAcceptTime(OrdersNew order, OrderProductsNew orderProduct,
                                            Long mustNetPointAcceptTime) {
        if (null != mustNetPointAcceptTime && mustNetPointAcceptTime > 0) {
            return mustNetPointAcceptTime;
        } else {
            return this.countAndUpdateTimeOfOrderWorkFlow(order, orderProduct)
                    .getMustNetPointAcceptTime();
        }
    }

    private OrderWorkflows countAndUpdateTimeOfOrderWorkFlow(OrdersNew order,
                                                             OrderProductsNew orderProduct) {
        OrderWorkflows orderWorkflows = new OrderWorkflows();
        orderWorkflows.setOrderProductId(Math.toIntExact(orderProduct.getId()));
        //开始时间取付款时间
        long beginTime = 0L;
        if (order.getIsCod() == 1) {
            beginTime = order.getCodConfirmTime();
        } else if (OrderType.TYPE_GROUP_ADVANCE.getIntValue() == order.getOrderType().intValue()
                || OrderType.TYPE_GROUP_ADVANCE_TAIL.getIntValue() == order.getOrderType().intValue()) {
            beginTime = order.getTailPayTime();
        } else {
            beginTime = order.getPayTime();
        }
        if (beginTime == 0) {
            return orderWorkflows;
        }
        //取配送时效
        int day = 3;
        Regions regions = regionsService.get(order.getRegion());
        if (null != regions && !StringUtils.isBlank(regions.getShippingTime())) {
            day = Integer.parseInt(regions.getShippingTime());
        }
        if (StringUtils.isNotEmpty(orderProduct.getTsCode())) {
            //取转运时效
            int shippingHours = 0;
            if (bigStoragesRelaMap.get() == null) {
                List<BigStoragesRela> bigStoragesRelaList = stockServiceImpl
                        .getBigStoragesRelaList().getResult();
                Map<String, BigStoragesRela> map = new HashMap<String, BigStoragesRela>();
                for (BigStoragesRela bigStoragesRela : bigStoragesRelaList) {
                    map.put(bigStoragesRela.getCode(), bigStoragesRela);
                }
                bigStoragesRelaMap.set(map);
            }
            BigStoragesRela bigStoragesRela = bigStoragesRelaMap.get()
                    .get(orderProduct.getTsCode());
            if (null != bigStoragesRela) {
                if (bigStoragesRela.getCenterCode().equals(orderProduct.getSCode())) {
                    shippingHours = bigStoragesRela.getCenterShippingTime();
                } else {
                    shippingHours = bigStoragesRela.getMasterShippingTime();
                }
                day += Math.ceil(shippingHours / 24);
            }
        }

        int addTime = 0;
        if (day == 1) {
            addTime = 43200;
        } else {
            addTime = 28800;
        }
        int ewTime = 0;
        if (day > 2) {
            ewTime = 14400;
        } else {
            ewTime = 7200;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(beginTime * 1000L);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        if (hour > 18 || (hour == 17 && minute >= 30)) {
            day++;
        }
        //计算应送达网点时间
        long mustNetPointAcceptTime = beginTime + day * 86400 + addTime;
        long ewNetPointAcceptTime = beginTime + day * 86400 + addTime - ewTime;
        orderWorkflows.setMustNetPointAcceptTime(mustNetPointAcceptTime);
        orderWorkflows.setEwNetPointAcceptTime(ewNetPointAcceptTime);

        //计算应送达用户时间
        long mustUserAcceptTime;
        long ewUserAcceptTime;
        int mustUserAcceptTimeAddTime;
        if (productTypeSet.isEmpty()) {
            productTypeSet.add(39);
            productTypeSet.add(40);
            productTypeSet.add(108);
            productTypeSet.add(127);
            productTypeSet.add(113);
            productTypeSet.add(118);
        }
        if (productTypeSet.contains(orderProduct.getProductType())) {
            mustUserAcceptTimeAddTime = 18000;
        } else {
            mustUserAcceptTimeAddTime = 10800;
        }
        ReservationShipping reservationShipping = reservationShippingService.get(Math.toIntExact(Integer.valueOf(order.getId())));
        if (null != reservationShipping) {
            mustUserAcceptTime = DateFormatUtil.parseByType("yyyyMMddHHmmss",
                    reservationShipping.getDate() + reservationShipping.getTime()).getTime();
            mustUserAcceptTime = mustUserAcceptTime / 1000L + mustUserAcceptTimeAddTime;
        } else {
            mustUserAcceptTime = mustNetPointAcceptTime + 79200 + mustUserAcceptTimeAddTime;
        }
        //如果有HP回传时间
        if (orderProduct.getHpReservationDate() > 0) {//有hp回传的时间
            long newMustUserAcceptTime = orderProduct.getHpReservationDate()
                    + mustUserAcceptTimeAddTime;
            if (newMustUserAcceptTime > mustUserAcceptTime) {
                mustUserAcceptTime = orderProduct.getHpReservationDate()
                        + mustUserAcceptTimeAddTime;
            }
        }
        if (mustUserAcceptTime - beginTime > 86400 * 3) {
            ewUserAcceptTime = mustUserAcceptTime - 14400;
        } else {
            ewUserAcceptTime = mustUserAcceptTime - 7200;
        }
        orderWorkflows.setMustUserAcceptTime(mustUserAcceptTime);
        orderWorkflows.setEwUserAcceptTime(ewUserAcceptTime);
        orderWorkflowsService.updateForPubCountTime(orderWorkflows);
        return orderWorkflows;
    }


    /**
     * 更新配送时效
     *
     * @param order
     * @param orderProduct
     */
    public void setOrderWorkFlowShippingTime(OrdersNew order, OrderProductsNew orderProduct) {
        OrderWorkflows orderWorkflows = new OrderWorkflows();
        orderWorkflows.setOrderProductId(orderProduct.getId());
        //取配送时效
        int day = 0;
        Regions regions = regionsService.get(order.getRegion());
        if (null != regions && !StringUtils.isBlank(regions.getShippingTime())) {
            day = Integer.parseInt(regions.getShippingTime());
        } else {
            log.error("网单号：" + orderProduct.getCOrderSn() + ",regionId:" + order.getRegion()
                    + ",取得区县信息为空！");
        }
        if (StringUtils.isNotEmpty(orderProduct.getTsCode())) {
            //取转运时效
            int shippingHours = 0;
            if (bigStoragesRelaMap.get() == null) {
                List<BigStoragesRela> bigStoragesRelaList = orderThirdCenterStockCommonService
                        .getBigStoragesRelaList().getResult();
                Map<String, BigStoragesRela> map = new HashMap<String, BigStoragesRela>();
                for (BigStoragesRela bigStoragesRela : bigStoragesRelaList) {
                    map.put(bigStoragesRela.getCode(), bigStoragesRela);
                }
                bigStoragesRelaMap.set(map);
            }
            BigStoragesRela bigStoragesRela = bigStoragesRelaMap.get()
                    .get(orderProduct.getTsCode());
            if (null != bigStoragesRela) {
                if (bigStoragesRela.getCenterCode().equals(orderProduct.getSCode())) {
                    shippingHours = bigStoragesRela.getCenterShippingTime();
                } else {
                    shippingHours = bigStoragesRela.getMasterShippingTime();
                }
                if (shippingHours == 0) {
                    log.error("网单号：" + orderProduct.getCOrderSn() + ",sCode:"
                            + orderProduct.getSCode() + ",shippingHours:" + shippingHours);
                }
                day += Math.ceil(shippingHours / 24);
            } else {
                log.error("网单号：" + orderProduct.getCOrderSn() + ",tsCode:"
                        + orderProduct.getTsCode() + ",根据tsCode取得转运信息为空！");
            }
        }

        orderWorkflows.setShippingTime(day);
        orderWorkflowsService.updateForPubCountTime(orderWorkflows);
    }

    /**
     * 计算应送达用户时间
     * @param order
     * @param orderProduct
     * @param mustUserAcceptTime
     * @return
     */
    public Long countMustUserAcceptTime(OrdersNew order, OrderProductsNew orderProduct,
                                        Long mustUserAcceptTime) {
        if (null != mustUserAcceptTime && mustUserAcceptTime > 0) {
            return mustUserAcceptTime;
        } else {
            return this.countAndUpdateTimeOfOrderWorkFlow(order, orderProduct)
                    .getMustUserAcceptTime();
        }
    }

}
