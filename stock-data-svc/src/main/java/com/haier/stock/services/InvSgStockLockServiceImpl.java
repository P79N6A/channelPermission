package com.haier.stock.services;

import com.haier.stock.dao.stock.InvSgStockLockDao;
import com.haier.stock.model.InvSgStockLock;
import com.haier.stock.model.InvSgStockLockEntity;
import com.haier.stock.service.InvSgStockLockService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class InvSgStockLockServiceImpl implements InvSgStockLockService {
    @Autowired
    InvSgStockLockDao invSgStockLockDao;

    @Override
    public InvSgStockLockEntity findSgStockLockByRefNo(String refNo) {
        // TODO Auto-generated method stub
        return invSgStockLockDao.findSgStockLockByRefNo(refNo);
    }

    @Override
    public Integer updateReleaseSgStockLock(InvSgStockLockEntity stockLockEntity) {
        // TODO Auto-generated method stub
        return invSgStockLockDao.updateReleaseSgStockLock(stockLockEntity);
    }

    @Override
    public Integer findSgStockLockBySkuRefNoStoreCode(String sku, String refNo, String storeCode) {
        // TODO Auto-generated method stub
        return invSgStockLockDao.findSgStockLockBySkuRefNoStoreCode(sku, refNo, storeCode);
    }

    @Override
    public Integer insertInvSgStockLock(InvSgStockLock invSgStockLock) {
        // TODO Auto-generated method stub
        return invSgStockLockDao.insertInvSgStockLock(invSgStockLock);
    }

}