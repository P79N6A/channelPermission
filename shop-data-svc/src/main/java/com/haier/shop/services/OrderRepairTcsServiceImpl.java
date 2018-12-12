package com.haier.shop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopread.OrderRepairTcsReadDao;
import com.haier.shop.dao.shopwrite.OrderRepairTcsWriteDao;
import com.haier.shop.model.OrderRepairTcs;
import com.haier.shop.service.OrderRepairTcsService;

@Service
public class OrderRepairTcsServiceImpl implements OrderRepairTcsService {
    @Autowired
    OrderRepairTcsReadDao orderRepairTcsReadDao;
    @Autowired
    OrderRepairTcsWriteDao orderRepairTcsWriteDao;

    @Override
    public OrderRepairTcs getById(Integer orderRepairTcsId) {
        // TODO Auto-generated method stub
        return orderRepairTcsReadDao.getById(orderRepairTcsId);
    }

    @Override
    public int updateTcExtStatus(Integer orderRepairTcsId, Integer caiNiaoTcExtStatus) {
        // TODO Auto-generated method stub
        return orderRepairTcsWriteDao.updateTcExtStatus(orderRepairTcsId, caiNiaoTcExtStatus);
    }

}
