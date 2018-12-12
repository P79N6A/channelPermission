package com.haier.stock.services;

import java.util.List;

import com.haier.stock.dao.stock.SgFlagShipStoreAuthorizationDao;
import com.haier.stock.model.SgFlagShipStoreAuthorization;
import com.haier.stock.service.SgFlagShipStoreAuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SgFlagShipStoreAuthorizationServiceImpl implements SgFlagShipStoreAuthorizationService {

    @Autowired
    SgFlagShipStoreAuthorizationDao sgFlagShipStoreAuthorizationDao;
    @Override
    public List<SgFlagShipStoreAuthorization> queryByCondition(Integer storeId, Integer brandId, String department) {
        return sgFlagShipStoreAuthorizationDao.queryByCondition(storeId,brandId,department);
    }

    @Override
    public List<String> getStoreCodeByStreet(Integer street, Integer brandId, String department, Integer storeStatus, Integer storeType) {
        return sgFlagShipStoreAuthorizationDao.getStoreCodeByStreet(street,brandId,department,storeStatus,storeType);
    }
}
