package com.haier.shop.services;

import java.util.List;

import com.haier.shop.dao.shopread.GroupOrdersReadDao;
import com.haier.shop.dao.shopwrite.GroupOrdersWriteDao;
import com.haier.shop.model.GroupOrders;
import com.haier.shop.service.GroupOrdersService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


@Service
public class GroupOrdersServiceImpl implements GroupOrdersService {
    @Autowired
    GroupOrdersReadDao groupOrdersReadDao;
    @Autowired
    GroupOrdersWriteDao groupOrdersWriteDao;

    @Override
    public GroupOrders getByDepositOrderId(Integer depositOrderId) {
        // TODO Auto-generated method stub
        return groupOrdersReadDao.getByDepositOrderId(depositOrderId);
    }

    @Override
    public Object insert(GroupOrders groupOrders) {
        // TODO Auto-generated method stub
        return groupOrdersWriteDao.insert(groupOrders);
    }

    @Override
    public Object updateStatus(GroupOrders groupOrders) {
        // TODO Auto-generated method stub
        return groupOrdersWriteDao.updateStatus(groupOrders);
    }

    @Override
    public List<GroupOrders> getGroupOrdersQueues(Integer topX) {
        // TODO Auto-generated method stub
        return groupOrdersReadDao.getGroupOrdersQueues(topX);
    }

    @Override
    public Object updateLesStatus(GroupOrders groupOrders) {
        // TODO Auto-generated method stub
        return groupOrdersWriteDao.updateLesStatus(groupOrders);
    }

    @Override
    public GroupOrders getByTailOrderId(Integer tailOrderId) {
        // TODO Auto-generated method stub
        return groupOrdersReadDao.getByTailOrderId(tailOrderId);
    }

    @Override
    public List<GroupOrders> getListByDepositOrderProductId(Integer depositOrderProductId) {
        // TODO Auto-generated method stub
        return groupOrdersReadDao.getListByDepositOrderProductId(depositOrderProductId);
    }

    @Override
    public Integer update(GroupOrders groupOrders) {
        // TODO Auto-generated method stub
        return groupOrdersWriteDao.update(groupOrders);
    }

    @Override
    public GroupOrders getByDepositOrderProductId(Integer depositOrderProductId) {
        // TODO Auto-generated method stub
        return groupOrdersReadDao.getByDepositOrderProductId(depositOrderProductId);
    }
}
