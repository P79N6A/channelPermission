package com.haier.stock.services;

import java.util.List;

import com.haier.stock.dao.stock.InvWaStreetRefDao;
import com.haier.stock.model.InvWaStreetRefEntity;
import com.haier.stock.service.InvWaStreetRefService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


/**
 * [WA库位与街道关系表]Dao
 * <p>Table: <strong>inv_wa_street_ref</strong>
 *
 * @Filename: InvWaStreetRefDao.java
 * @Version: 1.0
 * @Author: 陈闯
 * @Email: chuang.chen@dhc.com.cn
 */
@Service
public class InvWaStreetRefServiceImpl implements InvWaStreetRefService {
    @Autowired
    InvWaStreetRefDao invWaStreetRefDao;

    @Override
    public List<String> findInvWaStreetRefByStreetId(Integer streetId) {
        // TODO Auto-generated method stub
        return invWaStreetRefDao.findInvWaStreetRefByStreetId(streetId);
    }

    @Override
    public List<InvWaStreetRefEntity> findInvWaStreetRefAll() {
        // TODO Auto-generated method stub
        return invWaStreetRefDao.findInvWaStreetRefAll();
    }


}