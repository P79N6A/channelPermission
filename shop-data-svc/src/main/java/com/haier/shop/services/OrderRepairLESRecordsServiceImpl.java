package com.haier.shop.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopread.OrderRepairLESRecordsReadDao;
import com.haier.shop.dao.shopwrite.OrderRepairLESRecordsWriteDao;
import com.haier.shop.model.OrderRepairLESRecords;
import com.haier.shop.service.OrderRepairLESRecordsService;

/*
* 作者:张波
* 2017/12/19
* */
@Service
public class OrderRepairLESRecordsServiceImpl implements OrderRepairLESRecordsService {
    @Autowired
    OrderRepairLESRecordsReadDao orderRepairLESRecordsReadDao;
    @Autowired
    OrderRepairLESRecordsWriteDao orderRepairLESRecordsWriteDao;

    @Override
    public OrderRepairLESRecords getByRecordSn(String recordSn) {
        // TODO Auto-generated method stub
        return orderRepairLESRecordsReadDao.getByRecordSn(recordSn);
    }

    @Override
    public Integer updateAfterLesInStorage(OrderRepairLESRecords orderRepairLESRecords) {
        // TODO Auto-generated method stub
        return orderRepairLESRecordsWriteDao.updateAfterLesInStorage(orderRepairLESRecords);
    }

    @Override
    public Integer updateAfterVomAccepted(OrderRepairLESRecords orderRepairLESRecords) {
        // TODO Auto-generated method stub
        return orderRepairLESRecordsWriteDao.updateAfterVomAccepted(orderRepairLESRecords);
    }

    @Override
    public OrderRepairLESRecords getByLesOrderSn(String lesOrderSn, String cOrderSn) {
        // TODO Auto-generated method stub
        return orderRepairLESRecordsReadDao.getByLesOrderSn(lesOrderSn, cOrderSn);
    }

    @Override
    public Integer insert(OrderRepairLESRecords lesRecords) {
        // TODO Auto-generated method stub
        return orderRepairLESRecordsWriteDao.insert(lesRecords);
    }

    @Override
    public List<OrderRepairLESRecords> queryLesreCodrdsId(String id) {
        return orderRepairLESRecordsReadDao.queryLesreCodrdsId(id);
    }

    @Override
    public Integer updateLesRecordAfterJLIN(OrderRepairLESRecords orderRepairLESRecords) {
        // TODO Auto-generated method stub
        return orderRepairLESRecordsWriteDao.updateLesRecordAfterJLIN(orderRepairLESRecords);
    }

    @Override
    public List<OrderRepairLESRecords> getWaitforCancelOP(int limitNum) {
        // TODO Auto-generated method stub
        return orderRepairLESRecordsReadDao.getWaitforCancelOP(limitNum);
    }

    @Override
    public Integer updateOpCancelFlag(OrderRepairLESRecords orderRepairLESRecords) {
        // TODO Auto-generated method stub
        return orderRepairLESRecordsWriteDao.updateOpCancelFlag(orderRepairLESRecords);
    }

}
