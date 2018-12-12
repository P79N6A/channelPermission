package com.haier.logistics.service;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.haier.shop.model.OrderProducts;
import com.haier.shop.model.OrderProductsNew;
import com.haier.shop.model.Orders;
import com.haier.shop.model.OrdersNew;
import com.haier.shop.model.SmsLogs;

public interface OrderRebackService {
    public OrderProductsNew getOrderProductsByCOrderSn(String cOrderSn);
    public boolean saveHpReservationDateRelation(OrderProductsNew orderProducts, String remark,
                                                 String changeLog);
    /**
     * 发送短信
     * @param orderProducts
     */
    public int sendSms(OrderProductsNew orderProducts);
    public String constructSmsMsg(OrdersNew orders, OrderProductsNew orderProducts);
}
