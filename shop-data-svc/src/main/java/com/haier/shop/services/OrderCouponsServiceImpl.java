package com.haier.shop.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopread.OrderCouponsReadDao;
import com.haier.shop.model.OrderCoupons;
import com.haier.shop.service.OrderCouponsService;

/**
 * Created by 李超 on 2018/1/10.
 */
@Service
public class OrderCouponsServiceImpl implements OrderCouponsService {

    @Autowired
    OrderCouponsReadDao orderCouponsReadDao;

    @Override
    public OrderCoupons getOrderCouponsByCOrderSn(String cOrderSn) {
        return orderCouponsReadDao.getOrderCouponsByCOrderSn(cOrderSn);
    }

    @Override
    public Map<String, Object> getLpTotalFeeNum(Integer orderId) {
        return orderCouponsReadDao.getLpTotalFeeNum(orderId);
    }
}
