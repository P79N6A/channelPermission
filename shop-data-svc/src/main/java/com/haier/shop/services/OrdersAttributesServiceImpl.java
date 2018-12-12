package com.haier.shop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopread.OrdersAttributesReadDao;
import com.haier.shop.dao.shopwrite.OrdersAttributesWriteDao;
import com.haier.shop.model.OrdersAttributes;
import com.haier.shop.service.OrdersAttributesService;

/*
* 作者:张波
* 2017/12/19
* */
@Service
public class OrdersAttributesServiceImpl implements OrdersAttributesService {
    @Autowired
    OrdersAttributesWriteDao ordersAttributesWriteDao;
    @Autowired
    OrdersAttributesReadDao ordersAttributesReadDao;

    @Override
    public OrdersAttributes getByOrderId(Integer orderId) {
        // TODO Auto-generated method stub
        return ordersAttributesReadDao.getByOrderId(orderId);
    }

    @Override
    public int update(OrdersAttributes ordersAttributes) {
        // TODO Auto-generated method stub
        return ordersAttributesWriteDao.update(ordersAttributes);
    }
}
