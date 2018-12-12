package com.haier.stock.services;


import com.haier.stock.dao.stock.StoreInfoDao;
import com.haier.stock.model.StoreInfo;
import com.haier.stock.service.StoreInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreInfoServiceImpl implements StoreInfoService {

    @Autowired
    StoreInfoDao storeInfoDao;

    @Override
    public StoreInfo getByOwerId(Integer owerId) {
        return storeInfoDao.getByOwerId(owerId);
    }
}