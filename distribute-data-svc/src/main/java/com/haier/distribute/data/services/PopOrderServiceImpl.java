package com.haier.distribute.data.services;
/**
 * Created by Administrator on 2popOrderDao17/11/7 popOrderDaopopOrderDaopopOrderDao7.
 */


import java.util.List;
import java.util.Map;

import com.haier.distribute.data.dao.distribute.PopOrderDao;
import com.haier.distribute.data.model.PopOrders;
import com.haier.distribute.data.service.PopOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: 胡万来
 * \* Date: 2popOrderDao17/11/7 popOrderDaopopOrderDaopopOrderDao7
 * \* Time: 9:13
 * \* To change this template use File | Settings | File Templates.
 * \* Description:
 * \
 */
@Service
public class PopOrderServiceImpl implements PopOrderService {

    @Autowired
    PopOrderDao popOrderDao;

    @Override
    public String getVehicleOrderNo(String begin) {
        return popOrderDao.getVehicleOrderNo(begin);
    }

    @Override
    public int updateSelectiveByOrderNo(PopOrders entity) {
        return popOrderDao.updateSelectiveByOrderNo(entity);
    }

    @Override
    public int updateStatus(String orderStatus, String Id) {
        return popOrderDao.updateStatus(orderStatus, Id);
    }

    @Override
    public int orderOpertionToSure(String sellPeople, String orderStatus, String oId, String Id) {
        return popOrderDao.orderOpertionToSure(sellPeople, orderStatus, oId, Id);
    }

    @Override
    public int addConfirmTime(long confirmTime, String Id) {
        return popOrderDao.addConfirmTime(confirmTime, Id);
    }

    @Override
    public int confirmTime(long confirmTime, String Id) {
        return popOrderDao.confirmTime(confirmTime, Id);
    }

    @Override
    public int updateCancelStatus(String cancelStatus, String orderId) {
        return popOrderDao.updateCancelStatus(cancelStatus, orderId);
    }

    @Override
    public int editRemark(String codConfirmRemark, String orderSn) {
        return popOrderDao.editRemark(codConfirmRemark, orderSn);
    }

    @Override
    public int editOid(String oid, String orderSn) {
        return popOrderDao.editOid(oid, orderSn);
    }

    @Override
    public int editOrigin(String consignee, String mobile, String originRegionName, String originAddress, String orderSn) {
        return popOrderDao.editOrigin(consignee, mobile, originRegionName, originAddress, orderSn);
    }

    @Override
    public List<PopOrders> checkOid(String oid, String orderSn) {
        return popOrderDao.checkOid(oid, orderSn);
    }

    @Override
    public List<PopOrders> exportlist(PopOrders entity) {
        return popOrderDao.exportlist(entity);
    }

    @Override
    public PopOrders get(Integer id) {
        return popOrderDao.get(id);
    }

    @Override
    public List<PopOrders> getPageByCondition(PopOrders entity, int start, int rows) {
        // TODO Auto-generated method stub
        return popOrderDao.getPageByCondition(entity, start, rows);
    }

    @Override
    public long getPagerCount(PopOrders entity) {
        // TODO Auto-generated method stub
        return popOrderDao.getPagerCount(entity);
    }

    @Override
    public List<PopOrders> listByCondition(PopOrders entity) {
        // TODO Auto-generated method stub
        return popOrderDao.listByCondition(entity);
    }

    @Override
    public PopOrders getOneByCondition(PopOrders entity) {
        // TODO Auto-generated method stub
        return popOrderDao.getOneByCondition(entity);
    }

    @Override
    public int finishToCancel(String orderSn) {
        return popOrderDao.finishToCancel(orderSn);
    }
    @Override
    public List<Map<String,Object>> selectIdAndUrl(){
        return popOrderDao.selectIdAndUrl();
    }
}
