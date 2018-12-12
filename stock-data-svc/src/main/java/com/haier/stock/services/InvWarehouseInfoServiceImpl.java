package com.haier.stock.services;

import com.haier.stock.dao.stock.InvWarehouseInfoDao;
import com.haier.stock.model.InvWarehouseInfo;
import com.haier.stock.service.InvWarehouseInfoService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class InvWarehouseInfoServiceImpl implements InvWarehouseInfoService {
    @Autowired
    InvWarehouseInfoDao invWarehouseInfoDao;

    @Override
    public InvWarehouseInfo getBySecCode(String secCode) {
        // TODO Auto-generated method stub
        return invWarehouseInfoDao.getBySecCode(secCode);
    }

    @Override
    public int insert(InvWarehouseInfo invWarehouseInfo) {
        // TODO Auto-generated method stub
        return invWarehouseInfoDao.insert(invWarehouseInfo);
    }

    @Override
    public int update(InvWarehouseInfo invWarehouseInfo) {
        // TODO Auto-generated method stub
        return invWarehouseInfoDao.update(invWarehouseInfo);
    }
}