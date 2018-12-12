package com.haier.shop.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopread.OrderQueueExtendReadDao;
import com.haier.shop.dao.shopwrite.OrderQueueExtendWriteDao;
import com.haier.shop.model.OrderQueueExtend;
import com.haier.shop.model.OrdersNew;
import com.haier.shop.service.OrderQueueExtendService;

@Service
public class OrderQueueExtendServiceImpl implements OrderQueueExtendService {
    @Autowired
    OrderQueueExtendReadDao orderQueueExtendReadDao;
    @Autowired
    OrderQueueExtendWriteDao orderQueueExtendWriteDao;

    @Override
    public Integer update(OrderQueueExtend orderQueueExtend) {
        // TODO Auto-generated method stub
        return orderQueueExtendWriteDao.update(orderQueueExtend);
    }

    @Override
    public Integer updateSapStatus(String cOrderSn, int status, String sapMsg) {
        // TODO Auto-generated method stub
        return orderQueueExtendWriteDao.updateSapStatus(cOrderSn, status, sapMsg);
    }

    @Override
    public Integer updateInvoiceStatus(String cOrderSn, int status, String invoiceMsg) {
        // TODO Auto-generated method stub
        return orderQueueExtendWriteDao.updateInvoiceStatus(cOrderSn, status, invoiceMsg);
    }

    @Override
    public Integer insert(OrderQueueExtend orderQueueExtend) {
        // TODO Auto-generated method stub
        return orderQueueExtendWriteDao.insert(orderQueueExtend);
    }

    @Override
    public List<OrderQueueExtend> queryOrderQueueExtList(OrderQueueExtend orderQueueExtend) {
        // TODO Auto-generated method stub
        return orderQueueExtendReadDao.queryOrderQueueExtList(orderQueueExtend);
    }

    @Override
    public List<OrderQueueExtend> queryOrderOutList(int financeStatus) {
        // TODO Auto-generated method stub
        return orderQueueExtendReadDao.queryOrderOutList(financeStatus);
    }

    @Override
    public List<OrderQueueExtend> queryThirdPartyOrderOutList(int financeStatus, String source, int status) {
        // TODO Auto-generated method stub
        return orderQueueExtendReadDao.queryThirdPartyOrderOutList(financeStatus, source, status);
    }

    @Override
    public List<OrderQueueExtend> queryInvoiceList(int invoiceStatus) {
        // TODO Auto-generated method stub
        return orderQueueExtendReadDao.queryInvoiceList(invoiceStatus);
    }

    @Override
    public Integer cancelOrderExt(String orderSn, Integer cancelStatus) {
        // TODO Auto-generated method stub
        return orderQueueExtendWriteDao.cancelOrderExt(orderSn, cancelStatus);
    }

    @Override
    public OrderQueueExtend getLastOrderSnByOrderSource(String orderSource) {
        // TODO Auto-generated method stub
        return orderQueueExtendReadDao.getLastOrderSnByOrderSource(orderSource);
    }

    @Override
    public List<OrderQueueExtend> getByCOrderSnAndOrderSource(String cordersn, String orderSource) {
        // TODO Auto-generated method stub
        return orderQueueExtendReadDao.getByCOrderSnAndOrderSource(cordersn, orderSource);
    }

    @Override
    public OrdersNew getOrderById(int id) {
        // TODO Auto-generated method stub
        return orderQueueExtendReadDao.getOrderById(id);
    }

    @Override
    public String getMemberMobile(int id) {
        // TODO Auto-generated method stub
        return orderQueueExtendReadDao.getMemberMobile(id);
    }

}
