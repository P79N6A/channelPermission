package com.haier.shop.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopread.HpQueuesReadDao;
import com.haier.shop.dao.shopwrite.HPQueuesWriteDao;
import com.haier.shop.model.HPQueues;
import com.haier.shop.service.HPQueuesService;

/**
 * Created by 李超 on 2018/1/10.
 */
@Service
public class HPQueuesServiceImpl implements HPQueuesService {

    @Autowired
    HpQueuesReadDao hpQueuesReadDao;
    @Autowired
    HPQueuesWriteDao hpQueuesWriteDao;

    @Override
    public void insert(List<HPQueues> hpQueuesList) {
        hpQueuesWriteDao.insert(hpQueuesList);
    }

    @Override
    public void update(HPQueues hpQueues) {
        hpQueuesWriteDao.update(hpQueues);
    }

    @Override
    public int getCountByOpId(Integer orderProductId) {
        return hpQueuesReadDao.getCountByOpId(orderProductId);
    }

    @Override
    public HPQueues getByOrderProductId(Integer orderProductId) {
        return hpQueuesReadDao.getByOrderProductId(orderProductId);
    }

    @Override
    public int updateHPAllotNetPoint(HPQueues hpQueues) {
        return hpQueuesWriteDao.updateHPAllotNetPoint(hpQueues);
    }
}
