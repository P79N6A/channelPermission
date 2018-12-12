package com.haier.shop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopread.ShippingStatusSyncLogsReadDao;
import com.haier.shop.dao.shopwrite.ShippingStatusSyncLogsWriteDao;
import com.haier.shop.model.ShippingStatusSyncLogs;
import com.haier.shop.service.ShippingStatusSyncLogsService;

/*
* 作者:张波
* 2018/1/3
* */
@Service
public class ShippingStatusSyncLogsServiceImpl implements ShippingStatusSyncLogsService {

    @Autowired
    ShippingStatusSyncLogsWriteDao shippingStatusSyncLogsWriteDao;
    @Autowired
    ShippingStatusSyncLogsReadDao shippingStatusSyncLogsReadDao;

    @Override
    public ShippingStatusSyncLogs getByOrderProductId(Integer orderProductId) {
        return shippingStatusSyncLogsReadDao.getByOrderProductId(orderProductId);
    }

    @Override
    public ShippingStatusSyncLogs getByOrderId(Integer orderId) {
        return shippingStatusSyncLogsReadDao.getByOrderId(orderId);
    }

    @Override
    public Integer insert(ShippingStatusSyncLogs log) {
        return shippingStatusSyncLogsWriteDao.insert(log);
    }
}
