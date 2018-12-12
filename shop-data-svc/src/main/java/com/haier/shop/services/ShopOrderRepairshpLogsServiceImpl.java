package com.haier.shop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopread.OrderRepairshpLogsReadDao;
import com.haier.shop.dao.shopwrite.OrderRepairshpLogsWriteDao;
import com.haier.shop.model.OrderRepairshpLogs;
import com.haier.shop.service.ShopOrderRepairshpLogsService;

@Service
public class ShopOrderRepairshpLogsServiceImpl implements ShopOrderRepairshpLogsService {
    @Autowired
    private OrderRepairshpLogsReadDao orderRepairshpLogsReadDao;
    @Autowired
    private OrderRepairshpLogsWriteDao orderRepairshpLogsWriteDao;

    public int insert(OrderRepairshpLogs record){
        return orderRepairshpLogsWriteDao.insert(record);
    }

    public OrderRepairshpLogs selectByPrimaryKey(Integer id){
        return orderRepairshpLogsReadDao.selectByPrimaryKey(id);
    }
}
