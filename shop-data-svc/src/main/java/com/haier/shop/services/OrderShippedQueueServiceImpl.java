package com.haier.shop.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopread.OrderShippedQueueReadDao;
import com.haier.shop.dao.shopwrite.OrderShippedQueueWriteDao;
import com.haier.shop.model.OrderShippedQueue;
import com.haier.shop.service.OrderShippedQueueService;

/*
* 作者:张波
* 2017/12/20
* */
@Service
public class OrderShippedQueueServiceImpl implements OrderShippedQueueService {
    @Autowired
    OrderShippedQueueReadDao orderShippedQueueReadDao;
    @Autowired
    OrderShippedQueueWriteDao orderShippedQueueWriteDao;

    @Override
    public Integer insert(OrderShippedQueue queue) {
        // TODO Auto-generated method stub
        return orderShippedQueueWriteDao.insert(queue);
    }

    @Override
    public List<OrderShippedQueue> getListToProcess(Integer topX) {
        // TODO Auto-generated method stub
        return orderShippedQueueReadDao.getListToProcess(topX);
    }

    @Override
    public Integer delete(Integer id) {
        // TODO Auto-generated method stub
        return orderShippedQueueWriteDao.delete(id);
    }
}
