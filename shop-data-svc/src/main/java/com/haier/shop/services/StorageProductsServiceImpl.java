package com.haier.shop.services;


import java.util.List;

import com.haier.shop.dao.shopread.StorageProductsReadDao;
import com.haier.shop.dao.shopwrite.StorageProductsWriteDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.model.StorageProducts;
import com.haier.shop.service.StorageProductsService;

@Service
public class StorageProductsServiceImpl implements StorageProductsService {

    @Autowired
    StorageProductsReadDao storageProductsReadDao;
    @Autowired
    StorageProductsWriteDao storageProductsWriteDao;

    @Override
    public StorageProducts getBySCodeAndSku(String sCode, String sku) {
        return storageProductsReadDao.getBySCodeAndSku(sCode, sku);
    }

    @Override
    public StorageProducts getBySCodeAndSkuForLock(String sCode, String sku) {
        return storageProductsReadDao.getBySCodeAndSkuForLock(sCode, sku);
    }

    @Override
    public List<StorageProducts> getBySkuAndScodeList(String sku, List<String> scodeList) {
        return storageProductsReadDao.getBySkuAndScodeList(sku, scodeList);
    }

    @Override
    public List<StorageProducts> getByScodeList(List<String> scodeList) {
        return storageProductsReadDao.getByScodeList(scodeList);
    }

    @Override
    public List<StorageProducts> getAllEffectiveLocks() {
        return storageProductsReadDao.getAllEffectiveLocks();
    }

    @Override
    public Integer insert(StorageProducts storageProduct) {
        return storageProductsWriteDao.insert(storageProduct);
    }

    @Override
    public Integer updateAfterLesChanged(StorageProducts storageProduct) {
        return storageProductsWriteDao.updateAfterLesChanged(storageProduct);
    }

    @Override
    public Integer updateStockQty(StorageProducts storageProduct) {
        return storageProductsWriteDao.updateStockQty(storageProduct);
    }

    @Override
    public Integer releaseFrozenQty(Integer id, Integer releaseQty) {
        return storageProductsWriteDao.releaseFrozenQty(id, releaseQty);
    }

    @Override
    public Integer releaseFrozenQtyForRRS(Integer id, Integer releaseQty) {
        return storageProductsWriteDao.releaseFrozenQtyForRRS(id, releaseQty);
    }

    @Override
    public Integer frozenStock(Integer id, Integer frozenQty) {
        return storageProductsWriteDao.frozenStock(id, frozenQty);
    }

    @Override
    public Integer updateLocksQY(Integer id, Integer locksQY) {
        return storageProductsWriteDao.updateLocksQY(id, locksQY);
    }
}
