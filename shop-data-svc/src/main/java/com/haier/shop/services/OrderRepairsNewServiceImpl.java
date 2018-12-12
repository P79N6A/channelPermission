package com.haier.shop.services;

import java.util.List;

import com.haier.shop.dao.shopread.OrderRepairsNewDao;
import com.haier.shop.model.OrderRepairsNew;
import com.haier.shop.service.OrderRepairsNewService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OrderRepairsNewServiceImpl implements OrderRepairsNewService {
    @Autowired
    private OrderRepairsNewDao orderRepairsNewDao;

    @Override
    public OrderRepairsNew get(Integer id) {
        // TODO Auto-generated method stub
        return orderRepairsNewDao.get(id);
    }

    @Override
    public OrderRepairsNew getValidByOrderProductId(Integer orderProductId) {
        // TODO Auto-generated method stub
        return orderRepairsNewDao.getValidByOrderProductId(orderProductId);
    }

    @Override
    public Integer updateAfterLesInStorage(OrderRepairsNew orderRepairs) {
        // TODO Auto-generated method stub
        return orderRepairsNewDao.updateAfterLesInStorage(orderRepairs);
    }

    @Override
    public List<OrderRepairsNew> getByOrderProductId(Integer orderProductId) {
        // TODO Auto-generated method stub
        return orderRepairsNewDao.getByOrderProductId(orderProductId);
    }

    @Override
    public Integer update(OrderRepairsNew orderRepairs) {
        // TODO Auto-generated method stub
        return orderRepairsNewDao.update(orderRepairs);
    }

    @Override
    public List<OrderRepairsNew> queryWaitCloseForCompleteList() {
        // TODO Auto-generated method stub
        return orderRepairsNewDao.queryWaitCloseForCompleteList();
    }

    @Override
    public Integer updateForComplete(OrderRepairsNew orderRepairs) {
        // TODO Auto-generated method stub
        return orderRepairsNewDao.updateForComplete(orderRepairs);
    }

    @Override
    public OrderRepairsNew getByRepairSn(String repairSn) {
        // TODO Auto-generated method stub
        return orderRepairsNewDao.getByRepairSn(repairSn);
    }

    @Override
    public Integer updateForRepairSn(String repairSn, Integer storageStatus) {
        // TODO Auto-generated method stub
        return orderRepairsNewDao.updateForRepairSn(repairSn, storageStatus);
    }

    @Override
    public Integer getCountByOrderProductId(Integer orderProductId) {
        // TODO Auto-generated method stub
        return orderRepairsNewDao.getCountByOrderProductId(orderProductId);
    }

    @Override
    public Integer insert(OrderRepairsNew orderRepairs) {
        // TODO Auto-generated method stub
        return orderRepairsNewDao.insert(orderRepairs);
    }

    @Override
    public Integer getCountRepairSn(String cOrderSn) {
        // TODO Auto-generated method stub
        return orderRepairsNewDao.getCountRepairSn(cOrderSn);
    }

    @Override
    public Integer updateForStatus(OrderRepairsNew orderRepairs) {
        // TODO Auto-generated method stub
        return orderRepairsNewDao.updateForStatus(orderRepairs);
    }

}
