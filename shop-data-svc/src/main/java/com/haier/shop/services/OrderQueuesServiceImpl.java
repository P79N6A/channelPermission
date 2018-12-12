package com.haier.shop.services;

import com.haier.shop.dao.shopread.OrderQueuesReadDao;
import com.haier.shop.dao.shopwrite.OrderQueuesWriteDao;
import com.haier.shop.model.OrderQueues;
import com.haier.shop.service.OrderQueuesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OrderQueuesServiceImpl implements OrderQueuesService {
    @Autowired
    private OrderQueuesWriteDao orderQueuesWriteDao;
    @Autowired
    private OrderQueuesReadDao orderQueuesReadDao;
    public int insert(OrderQueues orderQueues){
        return orderQueuesWriteDao.insert(orderQueues);
    }

    public int update(OrderQueues orderQueues){
        return orderQueuesWriteDao.update(orderQueues);
    }
    public List<OrderQueues> getByOrderProductId(Integer orderProductId){
        return orderQueuesReadDao.getByOrderProductId(orderProductId);
    }
}
