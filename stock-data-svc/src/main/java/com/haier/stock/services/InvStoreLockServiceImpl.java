package com.haier.stock.services;

import java.util.List;

import com.haier.stock.dao.stock.InvStoreLockDao;
import com.haier.stock.model.InvStoreLock;
import com.haier.stock.service.InvStoreLockService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class InvStoreLockServiceImpl implements InvStoreLockService {
    @Autowired
    InvStoreLockDao invStoreLockDao;

    @Override
    public Integer insert(InvStoreLock storeLock) {
        // TODO Auto-generated method stub
        return invStoreLockDao.insert(storeLock);
    }

    @Override
    public List<InvStoreLock> getNotReleased(String refNo, String sku, String storeCode, String itemProperty) {
        // TODO Auto-generated method stub
        return invStoreLockDao.getNotReleased(refNo, sku, storeCode, itemProperty);
    }

    @Override
    public InvStoreLock getNotReleasedForUpdate(String refNo, String sku, String storeCode, String itemProperty) {
        // TODO Auto-generated method stub
        return invStoreLockDao.getNotReleasedForUpdate(refNo, sku, storeCode, itemProperty);
    }

    @Override
    public Integer updateReleaseQty(Integer id, Integer releaseQty, String optUser, String itemProperty) {
        // TODO Auto-generated method stub
        return invStoreLockDao.updateReleaseQty(id, releaseQty, optUser, itemProperty);
    }

    @Override
    public List<InvStoreLock> getNoReleaseByLockTime(String lockTime, Integer topx) {
        // TODO Auto-generated method stub
        return invStoreLockDao.getNoReleaseByLockTime(lockTime, topx);
    }


}
