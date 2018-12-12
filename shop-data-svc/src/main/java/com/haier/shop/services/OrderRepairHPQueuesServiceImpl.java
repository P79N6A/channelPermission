package com.haier.shop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopwrite.OrderRepairHPQueuesWriteDao;
import com.haier.shop.model.OrderRepairHPQueues;
import com.haier.shop.service.OrderRepairHPQueuesService;

/*
*
* 作者:张波
* 2017/12/20
* */
@Service
public class OrderRepairHPQueuesServiceImpl implements OrderRepairHPQueuesService {
    @Autowired
    OrderRepairHPQueuesWriteDao orderRepairHPQueuesWriteDao;

    @Override
    public Integer insert(OrderRepairHPQueues orderRepairHPQueues) {
        // TODO Auto-generated method stub
        return orderRepairHPQueuesWriteDao.insert(orderRepairHPQueues);
    }
}
