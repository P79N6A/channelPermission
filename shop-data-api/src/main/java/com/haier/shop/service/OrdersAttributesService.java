package com.haier.shop.service;

import com.haier.shop.model.OrdersAttributes;

/*
* 作者:张波
* 2017/12/19
* */
public interface OrdersAttributesService {
    OrdersAttributes getByOrderId( Integer orderId);
    int update(OrdersAttributes ordersAttributes);
}
