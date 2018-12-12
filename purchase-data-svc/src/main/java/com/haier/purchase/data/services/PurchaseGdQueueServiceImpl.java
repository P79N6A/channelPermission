package com.haier.purchase.data.services;

import java.util.List;

import com.haier.purchase.data.dao.purchase.PurchaseGdQueueDao;
import com.haier.purchase.data.model.PurchaseGdQueue;
import com.haier.purchase.data.service.PurchaseGdQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseGdQueueServiceImpl implements PurchaseGdQueueService{

    @Autowired
    PurchaseGdQueueDao purchaseGdQueueDao;

    @Override
    public PurchaseGdQueue getByOrderSn(String ordersn) {
        return purchaseGdQueueDao.getByOrderSn(ordersn);
    }

    @Override
    public Integer insert(PurchaseGdQueue purchaseGdQueue) {
        return purchaseGdQueueDao.insert(purchaseGdQueue);
    }

    @Override
    public Integer update(PurchaseGdQueue purchaseGdQueue) {
        return purchaseGdQueueDao.update(purchaseGdQueue);
    }

    @Override
    public List<PurchaseGdQueue> queryUnSyncWaitOut() {
        return purchaseGdQueueDao.queryUnSyncWaitOut();
    }

    @Override
    public Integer updateSyncWaitOut(PurchaseGdQueue purchaseGdQueue) {
        return purchaseGdQueueDao.updateSyncWaitOut(purchaseGdQueue);
    }
}
