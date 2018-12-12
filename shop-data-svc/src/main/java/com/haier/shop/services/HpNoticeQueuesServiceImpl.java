package com.haier.shop.services;


import java.util.List;

import com.haier.shop.dao.shopread.HpNoticeQueuesReadDao;
import com.haier.shop.dao.shopwrite.HpNoticeQueuesWriteDao;
import com.haier.shop.model.HpNoticeQueues;
import com.haier.shop.service.HpNoticeQueuesService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class HpNoticeQueuesServiceImpl implements HpNoticeQueuesService {
    @Autowired
    HpNoticeQueuesReadDao hpNoticeQueuesReadDao;

    @Autowired
    HpNoticeQueuesWriteDao hpNoticeQueuesWriteDao;

    @Override
    public List<HpNoticeQueues> getNoticeQueuesList(Integer max) {
        // TODO Auto-generated method stub
        return hpNoticeQueuesReadDao.getNoticeQueuesList(max);
    }

    @Override
    public int update(HpNoticeQueues hpNoticeQueues) {
        // TODO Auto-generated method stub
        return hpNoticeQueuesWriteDao.update(hpNoticeQueues);
    }

    @Override
    public int updateQueuesBySuccessAndOrderProId(Integer orderProductId) {
        // TODO Auto-generated method stub
        return hpNoticeQueuesWriteDao.updateQueuesBySuccessAndOrderProId(orderProductId);
    }
}
