package com.haier.shop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopread.ReservationShippingReadDao;
import com.haier.shop.model.ReservationShipping;
import com.haier.shop.service.ReservationShippingService;

/**
 * Created by 李超 on 2018/1/9.
 */
@Service
public class ReservationShippingServiceImpl implements ReservationShippingService {

    @Autowired
    ReservationShippingReadDao reservationShippingReadDao;

    @Override
    public ReservationShipping get(Integer orderId) {
        return reservationShippingReadDao.get(orderId);
    }
}
