package com.haier.shop.services;

import com.haier.shop.dao.shopread.HPFailedsReadDao;
import com.haier.shop.dao.shopwrite.HPFailedsWriteDao;
import com.haier.shop.model.HPFaileds;
import com.haier.shop.service.HPFailedsService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class HPFailedsServiceImpl implements HPFailedsService {
    @Autowired
    HPFailedsReadDao hpFailedsReadDao;
    @Autowired
    HPFailedsWriteDao hpFailedsWriteDao;

    @Override
    public HPFaileds getByOrderProductId(Integer orderProductId) {
        // TODO Auto-generated method stub
        return hpFailedsReadDao.getByOrderProductId(orderProductId);
    }

    @Override
    public Integer insert(HPFaileds hpFailed) {
        // TODO Auto-generated method stub
        return hpFailedsWriteDao.insert(hpFailed);
    }

    @Override
    public Integer updateHpFailed(HPFaileds hpFailed) {
        // TODO Auto-generated method stub
        return hpFailedsWriteDao.updateHpFailed(hpFailed);
    }

}
