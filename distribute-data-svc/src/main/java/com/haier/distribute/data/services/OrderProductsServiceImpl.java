package com.haier.distribute.data.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import com.haier.distribute.data.dao.distribute.OrderProductsDao;
import com.haier.distribute.data.model.PopOrderProducts;
import com.haier.distribute.data.service.OrderProductsService;
@Service
public class OrderProductsServiceImpl implements OrderProductsService {
    @Autowired
    OrderProductsDao orderProductsDao;

    @Override
    public int orderOpertion(String orderStatus, String Id) {
        // TODO Auto-generated method stub
        return orderProductsDao.orderOpertion(orderStatus, Id);
    }

    @Override
    public List<PopOrderProducts> checkStatus(String id) {
        // TODO Auto-generated method stub
        return orderProductsDao.checkStatus(id);
    }

    @Override
    public List<PopOrderProducts> checkOid(String oid) {
        // TODO Auto-generated method stub
        return orderProductsDao.checkOid(oid);
    }

    @Override
    public PopOrderProducts getOneByOrderSn(String cOrderSn) {
        // TODO Auto-generated method stub
        return orderProductsDao.getOneByOrderSn(cOrderSn);
    }

    @Override
    public List<PopOrderProducts> commodityList(Long orderId) {
        // TODO Auto-generated method stub
        return orderProductsDao.commodityList(orderId);
    }

    @Override
    public List<PopOrderProducts> exportOrderProductsList(PopOrderProducts entity) {
        // TODO Auto-generated method stub
        return orderProductsDao.exportOrderProductsList(entity);
    }

    @Override
    public List<PopOrderProducts> commission_product_invoice(int start, int rows, String categoryId, String startTime,
                                                             String endTime, String policyYear, int channelId, int channelType, int brandId) {
        // TODO Auto-generated method stub
        return orderProductsDao.commission_product_invoice(start, rows, categoryId, startTime, endTime, policyYear, channelId, channelType, brandId);
    }

    @Override
    public List<PopOrderProducts> commission_product_invoice2(int start, int rows, String categoryId, String startTime,
                                                              String endTime, String policyYear, int channelId, int channelType, int brandId) {
        // TODO Auto-generated method stub
        return orderProductsDao.commission_product_invoice2(start, rows, categoryId, startTime, endTime, policyYear, channelId, channelType, brandId);
    }

    @Override
    public List<PopOrderProducts> commission_product_invoice3(String categoryId, String policyYear, int channelId,
                                                              int channelType, int brandId) {
        // TODO Auto-generated method stub
        return orderProductsDao.commission_product_invoice3(categoryId, policyYear, channelId, channelType, brandId);
    }

    @Override
    public List<PopOrderProducts> commission_product_invoice1(String categoryId, String policyYear, int channelId,
                                                              int channelType, int brandId) {
        // TODO Auto-generated method stub
        return orderProductsDao.commission_product_invoice1(categoryId, policyYear, channelId, channelType, brandId);
    }

    @Override
    public int getPagerCountS(String categoryId, String startTime, String endTime, String policyYear, int channelId,
                              int channelType, int brandId) {
        // TODO Auto-generated method stub
        return orderProductsDao.getPagerCountS(categoryId, startTime, endTime, policyYear, channelId, channelType, brandId);
    }

    @Override
    public Integer updateHPAllotNetPoint(PopOrderProducts orderProduct) {
        // TODO Auto-generated method stub
        return orderProductsDao.updateHPAllotNetPoint(orderProduct);
    }

    @Override
    public List<PopOrderProducts> getPageByCondition(PopOrderProducts entity, int start, int rows) {
        return orderProductsDao.getPageByCondition(entity,start,rows);
    }

    @Override
    public long getPagerCount(PopOrderProducts entity) {
        return orderProductsDao.getPagerCount(entity);
    }

    @Override
    public List<PopOrderProducts> listByCondition(PopOrderProducts entity) {
        return orderProductsDao.listByCondition(entity);
    }

}
