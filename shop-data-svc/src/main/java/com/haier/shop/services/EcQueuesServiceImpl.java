package com.haier.shop.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopread.EcQueuesReadDao;
import com.haier.shop.dao.shopwrite.EcQueuesWriteDao;
import com.haier.shop.model.EcQueues;
import com.haier.shop.service.EcQueuesService;


@Service
public class EcQueuesServiceImpl implements EcQueuesService {
    @Autowired
    EcQueuesReadDao ecQueuesReadDao;
    @Autowired
    EcQueuesWriteDao ecQueuesWriteDao;

    @Override
    public EcQueues get(Integer id) {
        // TODO Auto-generated method stub
        return ecQueuesReadDao.get(id);
    }

    @Override
    public EcQueues getByOrderProductId(Integer orderProductId) {
        // TODO Auto-generated method stub
        return ecQueuesReadDao.getByOrderProductId(orderProductId);
    }

    @Override
    public int insert(EcQueues ecQueues) {
        // TODO Auto-generated method stub
        return ecQueuesWriteDao.insert(ecQueues);
    }

    @Override
    public int update(EcQueues ecQueues) {
        // TODO Auto-generated method stub
        return ecQueuesWriteDao.update(ecQueues);
    }

    @Override
    public List<EcQueues> getUnSendOrders(Integer unSendQueryNum, Integer sendNum) {
        // TODO Auto-generated method stub
        return ecQueuesReadDao.getUnSendOrders(unSendQueryNum, sendNum);
    }
}