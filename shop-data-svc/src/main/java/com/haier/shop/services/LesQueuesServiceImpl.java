package com.haier.shop.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopread.LesQueuesReadDao;
import com.haier.shop.dao.shopwrite.LesQueuesWriteDao;
import com.haier.shop.model.LesQueues;
import com.haier.shop.service.LesQueuesService;

@Service
public class LesQueuesServiceImpl implements LesQueuesService {
    @Autowired
    private LesQueuesReadDao lesQueuesReadDao;
    @Autowired
    private LesQueuesWriteDao lesQueuesWriteDao;

    public LesQueues get(Integer id){
        return lesQueuesReadDao.get(id);
    }

    public List<LesQueues> getSendQueues(Integer topX){
        return lesQueuesReadDao.getSendQueues(topX);
    }

    public int updateAfterSyncLes(LesQueues lesQueue){
        return lesQueuesWriteDao.updateAfterSyncLes(lesQueue);
    }

    public List<LesQueues> getCreateInvoiceQueues(Integer topX){
        return lesQueuesReadDao.getCreateInvoiceQueues(topX);
    }

    public int insert(List<LesQueues> lesQueuesList){
        return lesQueuesWriteDao.insert(lesQueuesList);
    }

    public int getCountByOpId(Integer orderProductId){
        return lesQueuesReadDao.getCountByOpId(orderProductId);
    }

    @Override
    public  LesQueues getLesQueueByOpId(Integer orderProductId){
        return lesQueuesReadDao.getLesQueueByOpId(orderProductId);
    }

    public List<Map<String, Object>> checkOrderLessSuccess(Integer orderProductId){
        return lesQueuesReadDao.checkOrderLessSuccess(orderProductId);
    }

    public int updateByOpId(LesQueues lesQueue){
        return lesQueuesWriteDao.updateByOpId(lesQueue);
    }

}
