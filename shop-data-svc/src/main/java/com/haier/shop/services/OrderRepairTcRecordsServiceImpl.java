package com.haier.shop.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopread.OrderRepairTcRecordsReadDao;
import com.haier.shop.dao.shopwrite.OrderRepairTcRecordsWriteDao;
import com.haier.shop.model.OrderRepairTcRecords;
import com.haier.shop.service.OrderRepairTcRecordsService;

@Service
public class OrderRepairTcRecordsServiceImpl implements OrderRepairTcRecordsService {
    @Autowired
    OrderRepairTcRecordsWriteDao orderRepairTcRecordsWriteDao;
    @Autowired
    OrderRepairTcRecordsReadDao orderRepairTcRecordsReadDao;

    @Override
    public Integer updateHpReturn(OrderRepairTcRecords orderRepairTcRecords) {
        // TODO Auto-generated method stub
        return orderRepairTcRecordsWriteDao.updateHpReturn(orderRepairTcRecords);
    }

    @Override
    public OrderRepairTcRecords getOrderRepairByVomTcSnAndSku(String orderRepairSn, String sku) {
        // TODO Auto-generated method stub
        return orderRepairTcRecordsReadDao.getOrderRepairByVomTcSnAndSku(orderRepairSn, sku);
    }

    @Override
    public OrderRepairTcRecords getByRecordSn(String recordSn) {
        // TODO Auto-generated method stub
        return orderRepairTcRecordsReadDao.getByRecordSn(recordSn);
    }

    @Override
    public Integer updateAfterVomAccepted(OrderRepairTcRecords orderRepairTcRecords) {
        // TODO Auto-generated method stub
        return orderRepairTcRecordsWriteDao.updateAfterVomAccepted(orderRepairTcRecords);
    }

    @Override
    public Integer updateAfterLesInStorage(OrderRepairTcRecords orderRepairTcRecords) {
        // TODO Auto-generated method stub
        return orderRepairTcRecordsWriteDao.updateAfterLesInStorage(orderRepairTcRecords);
    }

    @Override
    public List<OrderRepairTcRecords> queryByOrderRepairTcId(Integer orderRepairTcId) {
        // TODO Auto-generated method stub
        return orderRepairTcRecordsReadDao.queryByOrderRepairTcId(orderRepairTcId);
    }

}
