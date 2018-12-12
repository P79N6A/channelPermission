package com.haier.shop.services;

import com.haier.shop.dao.shopread.MctStoreProductsReadDao;
import com.haier.shop.dao.shopwrite.MctStoreProductsWriteDao;
import com.haier.shop.model.MctStoreProducts;
import com.haier.shop.service.MctStoreProductsService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class MctStoreProductsServiceImpl implements MctStoreProductsService {

    @Autowired
    MctStoreProductsReadDao mctStoreProductsReadDao;
    @Autowired
    MctStoreProductsWriteDao mctStoreProductsWriteDao;

    @Override
    public MctStoreProducts get(Integer storeProductId) {
        // TODO Auto-generated method stub
        return mctStoreProductsReadDao.get(storeProductId);
    }

    @Override
    public int insert(MctStoreProducts mctStoreProducts) {
        // TODO Auto-generated method stub
        return mctStoreProductsWriteDao.insert(mctStoreProducts);
    }

    @Override
    public int update(MctStoreProducts mctStoreProducts) {
        // TODO Auto-generated method stub
        return mctStoreProductsWriteDao.update(mctStoreProducts);
    }

    @Override
    public MctStoreProducts getByStoreIdStoreCodeSku(Integer storeId, String storeCode, String sku) {
        // TODO Auto-generated method stub
        return mctStoreProductsReadDao.getByStoreIdStoreCodeSku(storeId, storeCode, sku);
    }

    @Override
    public int updateStockNumById(Integer storeProductId, Integer stockNum) {
        // TODO Auto-generated method stub
        return mctStoreProductsWriteDao.updateStockNumById(storeProductId, stockNum);
    }
}