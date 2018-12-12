package com.haier.shop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopwrite.IcmsOutStoreWriteDao;
import com.haier.shop.model.IcmsOutStore;
import com.haier.shop.service.IcmsOutStoreService;

/*
*
* 作者:张波
* 2017/12/19
* */
@Service
public class IcmsOutStoreServiceImpl implements IcmsOutStoreService {
    @Autowired
    IcmsOutStoreWriteDao icmsOutStoreWriteDao;

    /**
     * 新增 京东订单出库信息
     *
     * @param icmsOutStore
     * @return
     */

    @Override
    public Integer insert(IcmsOutStore icmsOutStore) {
        // TODO Auto-generated method stub
        return icmsOutStoreWriteDao.insert(icmsOutStore);
    }
}
