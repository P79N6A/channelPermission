package com.haier.shop.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopread.InvSgScodeStreetsRefReadDao;
import com.haier.shop.model.InvSgScodeStreetsRefEntity;
import com.haier.shop.service.InvSgScodeStreetsRefService;


/**
 * [顺逛自有库位与街道关系]Dao
 * <p>Table: <strong>inv_sg_scode_streets_ref</strong>
 *
 * @Filename: InvSgScodeStreetsRefDao.java
 * @Version: 1.0
 * @Author: 陈闯
 * @Email: chuang.chen@dhc.com.cn
 */
@Service
public class InvSgScodeStreetsRefServiceImpl implements InvSgScodeStreetsRefService {
    @Autowired
    InvSgScodeStreetsRefReadDao invSgScodeStreetsRefReadDao;

    @Override
    public InvSgScodeStreetsRefEntity findInvSgScodeStreetsRefById(String id) {
        // TODO Auto-generated method stub
        return invSgScodeStreetsRefReadDao.findInvSgScodeStreetsRefById(id);
    }

    @Override
    public List<String> findScodeByStreetIdAndStoreId(String storeId, Integer streetId) {
        // TODO Auto-generated method stub
        return invSgScodeStreetsRefReadDao.findScodeByStreetIdAndStoreId(storeId, streetId);
    }

}