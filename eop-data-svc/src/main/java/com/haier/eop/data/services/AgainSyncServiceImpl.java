package com.haier.eop.data.services;

import com.haier.eop.data.dao.OrdersQueueDao;
import com.haier.eop.data.model.OrdersQueue;
import com.haier.eop.data.service.AgainSyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgainSyncServiceImpl implements AgainSyncService {
    @Autowired
    private OrdersQueueDao ordersQueueDao;


    @Override
    public OrdersQueue selectBysourceOrderSnAndsource(OrdersQueue record) {
        return ordersQueueDao.selectBysourceOrderSnAndsource(record);
    }

    @Override
    public int insert(OrdersQueue record) {
        return ordersQueueDao.insert(record);
    }

    @Override
    public int update(OrdersQueue record) {
        return ordersQueueDao.updateByPrimaryKey(record);
    }
}
