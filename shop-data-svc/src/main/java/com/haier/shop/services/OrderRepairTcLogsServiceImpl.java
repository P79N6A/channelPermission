package com.haier.shop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopwrite.OrderRepairTcLogsWriteDao;
import com.haier.shop.model.OrderRepairTcLogs;
import com.haier.shop.service.OrderRepairTcLogsService;

@Service
public class OrderRepairTcLogsServiceImpl implements OrderRepairTcLogsService {
    @Autowired
    OrderRepairTcLogsWriteDao orderRepairTcLogsWriteDao;

    @Override
    public Integer insert(OrderRepairTcLogs orderRepairTcLogs) {
        // TODO Auto-generated method stub
        return orderRepairTcLogsWriteDao.insert(orderRepairTcLogs);
    }
}
