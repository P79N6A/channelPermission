package com.haier.shop.service;

import com.haier.shop.model.ReservationShipping;

/**
 * Created by 李超 on 2018/1/9.
 */
public interface ReservationShippingService {
    ReservationShipping get(Integer orderId);
}
