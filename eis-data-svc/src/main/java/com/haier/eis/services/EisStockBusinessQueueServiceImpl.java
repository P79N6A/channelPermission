package com.haier.eis.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.haier.eis.dao.eis.EisStockBusinessQueueDao;
import com.haier.eis.model.EisStockBusinessQueue;
import com.haier.eis.service.EisStockBusinessQueueService;

/**
 * 2018-1-2
 *
 * @author wukunyang
 */
@Service
public class EisStockBusinessQueueServiceImpl implements EisStockBusinessQueueService {
    @Autowired
    EisStockBusinessQueueDao eisStockBusinessQueueDao;

    @Override
    public Integer insert(EisStockBusinessQueue stockBusinessQueue) {
        // TODO Auto-generated method stub
        return eisStockBusinessQueueDao.insert(stockBusinessQueue);
    }

    @Override
    public List<EisStockBusinessQueue> getTops(int i) {
        return eisStockBusinessQueueDao.getTops(i);
    }

    @Override
    public void delete(EisStockBusinessQueue queue) {
        eisStockBusinessQueueDao.delete(queue);
    }

}
