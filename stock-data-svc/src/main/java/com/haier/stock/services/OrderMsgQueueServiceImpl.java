package com.haier.stock.services;

import com.haier.stock.dao.stock.OrderMsgQueueDao;
import com.haier.stock.model.OrderMsgQueue;
import com.haier.stock.service.OrderMsgQueueService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class OrderMsgQueueServiceImpl implements OrderMsgQueueService {
    @Autowired
    OrderMsgQueueDao orderMsgQueueDao;

    @Override
    public int insert(OrderMsgQueue record) {
        // TODO Auto-generated method stub
        return orderMsgQueueDao.insert(record);
    }
}