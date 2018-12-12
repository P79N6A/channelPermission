package com.haier.shop.services;


import java.util.List;

import com.haier.shop.dao.shopread.OrderRepairHPRecordsnNewDao;
import com.haier.shop.model.OrderRepairHPRecordsNew;
import com.haier.shop.service.OrderRepairHPRecordsnNewService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderRepairHPRecordsnNewServiceImpl implements OrderRepairHPRecordsnNewService {
    @Autowired
    OrderRepairHPRecordsnNewDao orderRepairHPRecordsnNewDao;

    @Override
    public OrderRepairHPRecordsNew getByRepairIdAndCheckType(Integer orderRepairId, Integer checkType) {
        // TODO Auto-generated method stub
        return orderRepairHPRecordsnNewDao.getByRepairIdAndCheckType(orderRepairId, checkType);
    }

    @Override
    public List<OrderRepairHPRecordsNew> getByRepairId(int orderRepairId) {
        // TODO Auto-generated method stub
        return null;
    }
}
