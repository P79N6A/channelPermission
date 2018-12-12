package com.haier.shop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopread.OrderRepairLESQueuesReadDao;
import com.haier.shop.dao.shopwrite.OrderRepairLESQueuesWriteDao;
import com.haier.shop.model.OrderRepairLESQueues;
import com.haier.shop.service.OrderRepairLESQueuesService;

@Service
public class OrderRepairLESQueuesServiceImpl implements OrderRepairLESQueuesService {
    @Autowired
    OrderRepairLESQueuesReadDao orderRepairLESQueuesReadDao;
    @Autowired
    OrderRepairLESQueuesWriteDao orderRepairLESQueuesWriteDao;
    

    @Override
    public OrderRepairLESQueues getByOrderProductId(Integer orderProductId) {
        // TODO Auto-generated method stub
        return orderRepairLESQueuesReadDao.getByOrderProductId(orderProductId);
    }

    @Override
    public Integer updateVomResult(OrderRepairLESQueues queues) {
        // TODO Auto-generated method stub
        return orderRepairLESQueuesWriteDao.updateVomResult(queues);
    }

    @Override
    public Integer insert(OrderRepairLESQueues orderRepairLESQueues) {
        // TODO Auto-generated method stub
        return orderRepairLESQueuesWriteDao.insert(orderRepairLESQueues);
    }
}
