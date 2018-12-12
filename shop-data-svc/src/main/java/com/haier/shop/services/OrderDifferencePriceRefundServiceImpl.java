package com.haier.shop.services;

import com.haier.shop.dao.shopread.OrderDifferencePriceRefundReadDao;
import com.haier.shop.service.OrderDifferencePriceRefundService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class OrderDifferencePriceRefundServiceImpl implements OrderDifferencePriceRefundService {
    @Autowired
    OrderDifferencePriceRefundReadDao orderDifferencePriceRefundDao;

    @Override
    public int getValidCountByCorderSn(String cOrderSn) {
        // TODO Auto-generated method stub
        return orderDifferencePriceRefundDao.getValidCountByCorderSn(cOrderSn);
    }

}