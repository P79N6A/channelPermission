package com.haier.stock.services;

import java.util.List;

import com.haier.stock.dao.stock.InvStockOrderLockDao;
import com.haier.stock.model.InvStockOrderLockEntity;
import com.haier.stock.service.InvStockOrderLockService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


/**
 * [库存下单锁定表]Dao
 * <p>
 * Table: <strong>inv_stock_order_lock</strong>
 *
 * @Filename: InvStockOrderLockDao.java
 * @Version: 1.0
 * @Author: 陈闯
 * @Email: chuang.chen@dhc.com.cn
 */
@Service
public class InvStockOrderLockServiceImpl implements InvStockOrderLockService {
    @Autowired
    InvStockOrderLockDao invStockOrderLockDao;

    @Override
    public InvStockOrderLockEntity findInvStockOrderLockById(String id) {
        // TODO Auto-generated method stub
        return invStockOrderLockDao.findInvStockOrderLockById(id);
    }

    @Override
    public Integer findLockQtyByScodeAndStoreCodeAndSku(String scode, String storeCode, String sku, String refNo) {
        // TODO Auto-generated method stub
        return invStockOrderLockDao.findLockQtyByScodeAndStoreCodeAndSku(scode, storeCode, sku, refNo);
    }

    @Override
    public List<InvStockOrderLockEntity> findLockQtyByScodeAndSku(String[] arrayScode, String[] arraySku) {
        // TODO Auto-generated method stub
        return invStockOrderLockDao.findLockQtyByScodeAndSku(arrayScode, arraySku);
    }

    @Override
    public InvStockOrderLockEntity findInvStockOrderLockByRefNo(String refNo) {
        // TODO Auto-generated method stub
        return invStockOrderLockDao.findInvStockOrderLockByRefNo(refNo);
    }

    @Override
    public Integer insertStockOrderLock(InvStockOrderLockEntity stockLockEntity) {
        // TODO Auto-generated method stub
        return invStockOrderLockDao.insertStockOrderLock(stockLockEntity);
    }

    @Override
    public Integer releaseOrderLockByRefNo() {
        // TODO Auto-generated method stub
        return invStockOrderLockDao.releaseOrderLockByRefNo();
    }

    @Override
    public Integer paymentSuccessReleaseOrderLock(String refNo) {
        // TODO Auto-generated method stub
        return invStockOrderLockDao.paymentSuccessReleaseOrderLock(refNo);
    }

    @Override
    public Integer updateStockOrderLock(InvStockOrderLockEntity stockLockEntity) {
        // TODO Auto-generated method stub
        return invStockOrderLockDao.updateStockOrderLock(stockLockEntity);
    }
}