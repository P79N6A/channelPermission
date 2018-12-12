package com.haier.shop.services;

import com.haier.shop.dao.shopwrite.OrderRepairLogsWriteNewDao;
import com.haier.shop.model.OrderRepairLogsNew;
import com.haier.shop.service.OrderRepairLogsNewService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
* 作者:张波
* 2017/12/20
* */
@Service
public class OrderRepairLogsNewServiceImpl implements OrderRepairLogsNewService {
    @Autowired
    OrderRepairLogsWriteNewDao orderRepairLogsWriteNewDao;

    @Override
    public Integer insert(OrderRepairLogsNew orderRepairLogs) {
        // TODO Auto-generated method stub
        return orderRepairLogsWriteNewDao.insert(orderRepairLogs);
    }

    @Override
    public OrderRepairLogsNew get(Integer id) {
        // TODO Auto-generated method stub
        return orderRepairLogsWriteNewDao.get(id);
    }
}
