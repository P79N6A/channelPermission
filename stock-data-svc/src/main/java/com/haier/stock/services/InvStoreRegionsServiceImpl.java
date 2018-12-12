package com.haier.stock.services;

import java.util.List;

import com.haier.stock.dao.stock.InvStoreRegionsDao;
import com.haier.stock.model.InvStoreRegions;
import com.haier.stock.service.InvStoreRegionsService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class InvStoreRegionsServiceImpl implements InvStoreRegionsService {
    @Autowired
    InvStoreRegionsDao invStoreRegionsDao;

    @Override
    public InvStoreRegions getByStoreCode(String storeCode, Integer status) {
        // TODO Auto-generated method stub
        return invStoreRegionsDao.getByStoreCode(storeCode, status);
    }

    @Override
    public List<InvStoreRegions> getByRegionId(Integer regionId, Integer status, Integer hpRemark) {
        // TODO Auto-generated method stub
        return invStoreRegionsDao.getByRegionId(regionId, status, hpRemark);
    }

    @Override
    public List<InvStoreRegions> getByCityId(Integer cityId, Integer status) {
        // TODO Auto-generated method stub
        return invStoreRegionsDao.getByCityId(cityId, status);
    }

    @Override
    public Integer insert(InvStoreRegions storeRegions) {
        // TODO Auto-generated method stub
        return invStoreRegionsDao.insert(storeRegions);
    }

}
