package com.haier.stock.services;

import java.util.List;

import com.haier.stock.dao.stock.SgStoreDao;
import com.haier.stock.model.SgStore;
import com.haier.stock.service.SgStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SgStoreServiceImpl implements SgStoreService {

    @Autowired
    SgStoreDao sgStoreDao;

    @Override
    public SgStore getSgStore(String storeCode, Integer storeType, Integer storeState) {
        return sgStoreDao.getSgStore(storeCode,storeType,storeState);
    }

    @Override
    public List<SgStore> getSgStoreList(Integer regionId, Integer storeType, Integer storeState, String department) {
        return sgStoreDao.getSgStoreList(regionId,storeType,storeState,department);
    }

    @Override
    public SgStore getSgStoreById(Integer storeId) {
        return sgStoreDao.getSgStoreById(storeId);
    }
}