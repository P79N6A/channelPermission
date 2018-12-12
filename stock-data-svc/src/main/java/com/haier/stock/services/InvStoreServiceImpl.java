package com.haier.stock.services;

import java.util.Date;
import java.util.List;

import com.haier.stock.dao.stock.InvStoreDao;
import com.haier.stock.model.InvStore;
import com.haier.stock.service.InvStoreService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


@Service
public class InvStoreServiceImpl implements InvStoreService {
    @Autowired
    InvStoreDao invStoreDao;

    @Override
    public InvStore getForUpdate(String sku, String storeCode, String itemProperty) {
        // TODO Auto-generated method stub
        return invStoreDao.getForUpdate(sku, storeCode, itemProperty);
    }

    @Override
    public InvStore getByStoreCodeAndSku(String sku, String storeCode, String itemProperty) {
        // TODO Auto-generated method stub
        return invStoreDao.getByStoreCodeAndSku(sku, storeCode, itemProperty);
    }

    @Override
    public List<InvStore> getByStoreCodesAndSku(String sku, String storeCodes, String itemProperty) {
        // TODO Auto-generated method stub
        return invStoreDao.getByStoreCodesAndSku(sku, storeCodes, itemProperty);
    }

    @Override
    public List<InvStore> getChangedStockQty(Date beginTime) {
        // TODO Auto-generated method stub
        return invStoreDao.getChangedStockQty(beginTime);
    }

    @Override
    public List<InvStore> getByStoreCode(String storeCodes, String itemProperty) {
        // TODO Auto-generated method stub
        return invStoreDao.getByStoreCode(storeCodes, itemProperty);
    }

    @Override
    public Integer insert(InvStore invStore) {
        // TODO Auto-generated method stub
        return invStoreDao.insert(invStore);
    }

    @Override
    public Integer updateQty(InvStore invStore) {
        // TODO Auto-generated method stub
        return invStoreDao.updateQty(invStore);
    }

    @Override
    public Integer updateInvStore(InvStore invStore) {
        // TODO Auto-generated method stub
        return invStoreDao.updateInvStore(invStore);
    }

    @Override
    public List<InvStore> getEStoreBySkuAndStoreCodeList(String sku, String storeCode, String itemProperty) {
        // TODO Auto-generated method stub
        return invStoreDao.getEStoreBySkuAndStoreCodeList(sku, storeCode, itemProperty);
    }

    @Override
    public List<InvStore> getEStoreBySkuListAndStoreCode(String sku, String storeCode, String itemProperty) {
        // TODO Auto-generated method stub
        return invStoreDao.getEStoreBySkuListAndStoreCode(sku, storeCode, itemProperty);
    }


}
