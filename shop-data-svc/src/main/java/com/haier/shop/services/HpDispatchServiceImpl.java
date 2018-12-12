package com.haier.shop.services;


import java.util.List;
import java.util.Map;

import com.haier.shop.dao.shopread.HpDispatchReadDao;
import com.haier.shop.dao.shopwrite.HpDispatchWriteDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.model.HPQueues;
import com.haier.shop.service.HpDispatchService;

@Service
public class HpDispatchServiceImpl implements HpDispatchService {
    @Autowired
    HpDispatchReadDao hpDispatchReadDao;
    @Autowired
    HpDispatchWriteDao hpDispatchWriteDao;

    @Override
    public List<Map<String, Object>> getHpQueueInfo(Integer topX) {
        // TODO Auto-generated method stub
        return hpDispatchReadDao.getHpQueueInfo(topX);
    }

    @Override
    public Map<String, Object> getOrderProductInfo(Integer orderProductId) {
        // TODO Auto-generated method stub
        return hpDispatchReadDao.getOrderProductInfo(orderProductId);
    }

    @Override
    public Map<String, Object> getOrderInfo(Integer orderId) {
        // TODO Auto-generated method stub
        return hpDispatchReadDao.getOrderInfo(orderId);
    }

    @Override
    public Map<String, Object> getOrderWorkFlowInfo(Integer orderProductId) {
        // TODO Auto-generated method stub
        return hpDispatchReadDao.getOrderWorkFlowInfo(orderProductId);
    }

    @Override
    public Map<String, Object> getReservationShippingInfo(Integer orderId) {
        // TODO Auto-generated method stub
        return hpDispatchReadDao.getReservationShippingInfo(orderId);
    }

    @Override
    public Map<String, Object> getMemberInvoiceInfo(Integer orderId) {
        // TODO Auto-generated method stub
        return hpDispatchReadDao.getMemberInvoiceInfo(orderId);
    }

    @Override
    public List<Map<String, Object>> getOrderCountList(Map<String, Object> params) {
        // TODO Auto-generated method stub
        return hpDispatchReadDao.getOrderCountList(params);
    }

    @Override
    public int updateHPQueue(Map<String, Object> params) {
        // TODO Auto-generated method stub
        return hpDispatchWriteDao.updateHPQueue(params);
    }

    @Override
    public int updateOrderProductStatus(Integer id, Integer status) {
        // TODO Auto-generated method stub
        return hpDispatchWriteDao.updateOrderProductStatus(id, status);
    }

    @Override
    public int updateSyncHpTime(Integer orderProductId, Long sendHpTime) {
        // TODO Auto-generated method stub
        return hpDispatchWriteDao.updateSyncHpTime(orderProductId, sendHpTime);
    }

    @Override
    public List<Map<String, Object>> getRegions() {
        // TODO Auto-generated method stub
        return hpDispatchReadDao.getRegions();
    }

    @Override
    public List<Map<String, Object>> getSkuMappings() {
        // TODO Auto-generated method stub
        return hpDispatchReadDao.getSkuMappings();
    }

    @Override
    public List<HPQueues> getHpQueueUnSendInfo(Integer topX) {
        // TODO Auto-generated method stub
        return hpDispatchReadDao.getHpQueueUnSendInfo(topX);
    }

    @Override
    public int update(HPQueues hPQueues) {
        // TODO Auto-generated method stub
        return hpDispatchWriteDao.update(hPQueues);
    }

    @Override
    public int updateHPQueueBatch(String ids, String lastMessage) {
        // TODO Auto-generated method stub
        return hpDispatchWriteDao.updateHPQueueBatch(ids, lastMessage);
    }


}
