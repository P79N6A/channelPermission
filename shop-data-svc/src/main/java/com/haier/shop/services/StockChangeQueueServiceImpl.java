package com.haier.shop.services;

import java.util.List;

import com.haier.shop.dao.shopread.StockChangeQueueReadDao;
import com.haier.shop.dao.shopwrite.StockChangeQueueWriteDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.model.StockChangeQueue;
import com.haier.shop.service.StockChangeQueueService;

@Service
public class StockChangeQueueServiceImpl implements StockChangeQueueService {
    @Autowired
    StockChangeQueueReadDao stockChangeQueueReadDao;
    @Autowired
    StockChangeQueueWriteDao stockChangeQueueWriteDao;
    
    @Override
    public List<StockChangeQueue> getListToProcess(Integer topX) {
        return stockChangeQueueReadDao.getListToProcess(topX);
    }

    @Override
    public Integer insert(StockChangeQueue stockChangeQueue) {
        return stockChangeQueueWriteDao.insert(stockChangeQueue);
    }

    @Override
    public Integer delete(Integer id) {
        return stockChangeQueueWriteDao.delete(id);
    }
}
