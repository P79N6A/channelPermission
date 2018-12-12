package com.haier.stock.services;

import java.util.List;

import com.haier.stock.dao.stock.InvThOrderDao;
import com.haier.stock.model.InvThOrder;
import com.haier.stock.service.InvThOrderService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


@Service
public class InvThOrderServiceImpl implements InvThOrderService {
    @Autowired
    InvThOrderDao invThOrderDao;

    @Override
    public InvThOrder getInvThOrderByChannelSnAndRepairSn(String channelSn, String orderRepairSn) {
        // TODO Auto-generated method stub
        return invThOrderDao.getInvThOrderByChannelSnAndRepairSn(channelSn, orderRepairSn);
    }

    @Override
    public List<InvThOrder> queryInvThOrderData() {
        // TODO Auto-generated method stub
        return invThOrderDao.queryInvThOrderData();
    }

    @Override
    public int updateInvThOrder(InvThOrder invThOrder) {
        // TODO Auto-generated method stub
        return invThOrderDao.updateInvThOrder(invThOrder);
    }

}