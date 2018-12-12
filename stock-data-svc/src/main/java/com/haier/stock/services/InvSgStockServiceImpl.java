package com.haier.stock.services;

import java.util.Date;
import java.util.List;

import com.haier.stock.dao.stock.InvSgStockDao;
import com.haier.stock.model.InvSgStockEntity;
import com.haier.stock.model.InvSgStockLock;
import com.haier.stock.model.InvStore;
import com.haier.stock.service.InvSgStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * [顺逛自有库存表]Dao
 * <p>Table: <strong>inv_sg_stock</strong>
 *
 * @Filename: InvSgStockDao.java
 * @Version: 1.0
 * @Author: 陈闯
 * @Email: chuang.chen@dhc.com.cn
 */
@Service
public class InvSgStockServiceImpl implements InvSgStockService {
    
    @Autowired
    InvSgStockDao invSgStockDao;

    @Override
    public InvSgStockEntity findInvSgStockById(String id) {
        return invSgStockDao.findInvSgStockById(id);
    }

    @Override
    public InvSgStockEntity findInvSgStockByScodesAndSkuAndRequireQty(List<String> sCodes, String sku, Integer requireQty) {
        return invSgStockDao.findInvSgStockByScodesAndSkuAndRequireQty(sCodes,sku,requireQty);
    }

    @Override
    public InvSgStockEntity findInvSgStockBySkuRefNoStoreCode(String sku, String scode, String storeCode) {
        return invSgStockDao.findInvSgStockBySkuRefNoStoreCode(sku,scode,storeCode);
    }

    @Override
    public Integer updatefrozenQty(String sku, String storeCode, Integer releaseQty, String refNo, String scode) {
        return invSgStockDao.updatefrozenQty(sku,storeCode,releaseQty,refNo,scode);
    }

    @Override
    public List<InvSgStockEntity> findInvSgStockByStoreId(Integer storeId) {
        return invSgStockDao.findInvSgStockByStoreId(storeId);
    }

    @Override
    public List<InvStore> findInvStockByStoreCode(String storeCode, String itemProperty) {
        return invSgStockDao.findInvStockByStoreCode(storeCode,itemProperty);
    }

    @Override
    public List<InvSgStockEntity> fingSgStockByLastTime(Date updateTime, int topX) {
        return invSgStockDao.fingSgStockByLastTime(updateTime,topX);
    }

    @Override
    public Integer updateReleaseForReturn(InvSgStockLock invSgStockLock) {
        return invSgStockDao.updateReleaseForReturn(invSgStockLock);
    }

    @Override
    public Integer updateInvSgStockQty(InvSgStockLock invSgStockLock) {
        return invSgStockDao.updateInvSgStockQty(invSgStockLock);
    }

    @Override
    public Integer updateInvSgStock(InvSgStockEntity invSgStock) {
        return invSgStockDao.updateInvSgStock(invSgStock);
    }

    @Override
    public Integer insertInvSgStock(InvSgStockEntity invSgStock) {
        return invSgStockDao.insertInvSgStock(invSgStock);
    }
}