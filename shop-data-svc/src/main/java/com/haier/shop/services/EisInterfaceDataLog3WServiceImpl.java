package com.haier.shop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopwrite.EisInterfaceDataLog3wWriteDao;
import com.haier.shop.model.EisInterfaceDataLog3W;
import com.haier.shop.service.EisInterfaceDataLog3WService;

@Service
public class EisInterfaceDataLog3WServiceImpl implements EisInterfaceDataLog3WService {
    @Autowired
    EisInterfaceDataLog3wWriteDao eisInterfaceDataLog3wWriteDao;

    @Override
    public Integer insert(EisInterfaceDataLog3W log) {
        // TODO Auto-generated method stub
        return eisInterfaceDataLog3wWriteDao.insert(log);
    }

}
