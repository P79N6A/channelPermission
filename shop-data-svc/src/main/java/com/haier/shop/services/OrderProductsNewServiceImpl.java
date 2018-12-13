package com.haier.shop.services;


import java.math.BigDecimal;
import java.util.List;

import com.haier.shop.model.OrderProducts;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopwrite.OrderProductsNewDao;
import com.haier.shop.model.OrderProductsVo;
import com.haier.shop.model.OrderProductsNew;
import com.haier.shop.service.OrderProductsNewService;

@Service
public class OrderProductsNewServiceImpl implements OrderProductsNewService {
    @Autowired
    OrderProductsNewDao orderProductsNewDao;

    @Override
    public OrderProductsNew get(Integer id) {
        // TODO Auto-generated method stub
        return orderProductsNewDao.get(id);
    }

    @Override
    public List<OrderProductsNew> getByCOrderSnList(List<String> snList) {
        // TODO Auto-generated method stub
        return orderProductsNewDao.getByCOrderSnList(snList);
    }

    @Override
    public Integer updateAfterDelivery3W(OrderProductsNew orderProducts) {
        // TODO Auto-generated method stub
        return orderProductsNewDao.updateAfterDelivery3W(orderProducts);
    }

    @Override
    public Integer updateAfterDelivery(OrderProductsNew orderProducts) {
        // TODO Auto-generated method stub
        return orderProductsNewDao.updateAfterDelivery(orderProducts);
    }

    @Override
    public Integer updateAfterTransferFirstOut(OrderProductsNew orderProducts) {
        // TODO Auto-generated method stub
        return orderProductsNewDao.updateAfterTransferFirstOut(orderProducts);
    }

    @Override
    public Integer updateAfterTransferIn(OrderProductsNew orderProducts) {
        // TODO Auto-generated method stub
        return orderProductsNewDao.updateAfterTransferIn(orderProducts);
    }

    @Override
    public int updateSyncLes(OrderProductsNew orderProduct) {
        // TODO Auto-generated method stub
        return orderProductsNewDao.updateSyncLes(orderProduct);
    }

    @Override
    public List<OrderProductsNew> getUnLockStockOpList() {
        // TODO Auto-generated method stub
        return orderProductsNewDao.getUnLockStockOpList();
    }

    @Override
    public List<OrderProductsNew> getLockStockExceptionOpList(Integer id) {
        // TODO Auto-generated method stub
        return orderProductsNewDao.getLockStockExceptionOpList(id);
    }

    @Override
    public List<OrderProductsNew> getByOrderIds(List<Integer> orderIds) {
        // TODO Auto-generated method stub
        return orderProductsNewDao.getByOrderIds(orderIds);
    }

    @Override
    public List<OrderProductsNew> getByOrderId(Integer orderId) {
        // TODO Auto-generated method stub
        return orderProductsNewDao.getByOrderId(orderId);
    }

    @Override
    public Integer completeClose(Integer id, Long closeTime) {
        // TODO Auto-generated method stub
        return orderProductsNewDao.completeClose(id, closeTime);
    }

    @Override
    public int updateForFrozenStock(OrderProductsNew orderProduct) {
        // TODO Auto-generated method stub
        return orderProductsNewDao.updateForFrozenStock(orderProduct);
    }

    @Override
    public List<OrderProductsNew> getByOrderIdsForConfirm(List<Integer> orderIds) {
        // TODO Auto-generated method stub
        return orderProductsNewDao.getByOrderIdsForConfirm(orderIds);
    }

    @Override
    public Integer updateStatus(Integer id, Integer status) {
        // TODO Auto-generated method stub
        return orderProductsNewDao.updateStatus(id, status);
    }

    @Override
    public Integer updatePaymentStatusByOrderId(Integer orderId, Integer cPaymentStatus) {
        // TODO Auto-generated method stub
        return orderProductsNewDao.updatePaymentStatusByOrderId(orderId, cPaymentStatus);
    }

    @Override
    public int updateNum(Long newNum, BigDecimal productAmount,String orderProductId) {
        return orderProductsNewDao.updateNum(newNum,productAmount,orderProductId);
    }

    @Override
    public int updatesCodeBycOrderSn(String sCode, String cOrderSn) {
        return orderProductsNewDao.updatesCodeBycOrderSn(sCode,cOrderSn);
    }


    @Override
    public OrderProductsNew getByCOrderSn(String cOrderSn) {
        // TODO Auto-generated method stub
        return orderProductsNewDao.getByCOrderSn(cOrderSn);
    }

    @Override
    public Integer updateShippingModeById(OrderProductsNew orderProduct) {
        // TODO Auto-generated method stub
        return orderProductsNewDao.updateShippingModeById(orderProduct);
    }

    @Override
    public int updateAfterCreateInvoice(OrderProductsNew orderProduct) {
        // TODO Auto-generated method stub
        return orderProductsNewDao.updateAfterCreateInvoice(orderProduct);
    }

    @Override
    public Integer updateOpHpReservationDate(OrderProductsNew orderProduct) {
        // TODO Auto-generated method stub
        return orderProductsNewDao.updateOpHpReservationDate(orderProduct);
    }

    @Override
    public int updateForMakeReceiptType(OrderProductsNew orderProduct) {
        // TODO Auto-generated method stub
        return orderProductsNewDao.updateForMakeReceiptType(orderProduct);
    }

    @Override
    public Integer getStorageStreetsTimeoutFreeByStreetId(Integer streetId) {
        // TODO Auto-generated method stub
        return orderProductsNewDao.getStorageStreetsTimeoutFreeByStreetId(streetId);
    }

    @Override
    public Integer getStorageCitiesTimeoutFreeByRegionId(Integer regionId) {
        // TODO Auto-generated method stub
        return orderProductsNewDao.getStorageCitiesTimeoutFreeByRegionId(regionId);
    }

    @Override
    public Integer updateIsTimeoutFree(Integer id, Integer isTimeoutFree) {
        // TODO Auto-generated method stub
        return orderProductsNewDao.updateIsTimeoutFree(id, isTimeoutFree);
    }

    @Override
    public List<OrderProductsNew> getByTbNo(String tbNo) {
        // TODO Auto-generated method stub
        return orderProductsNewDao.getByTbNo(tbNo);
    }

    @Override
    public int updateOpModify(Integer opId) {
        // TODO Auto-generated method stub
        return orderProductsNewDao.updateOpModify(opId);
    }

    @Override
    public Integer forceCancelClose(Integer id, Long closeTime) {
        // TODO Auto-generated method stub
        return orderProductsNewDao.forceCancelClose(id, closeTime);
    }

    @Override
    public int update(OrderProductsNew orderProduct) {
        // TODO Auto-generated method stub
        return orderProductsNewDao.update(orderProduct);
    }

    @Override
    public List<OrderProductsNew> getByIds(List<Integer> ids) {
        // TODO Auto-generated method stub
        return orderProductsNewDao.getByIds(ids);
    }

    @Override
    public int updateMakeReceiptType(OrderProductsNew orderProduct) {
        // TODO Auto-generated method stub
        return orderProductsNewDao.updateMakeReceiptType(orderProduct);
    }

    @Override
    public int updateForsyncInvoice(OrderProductsNew orderProduct) {
        // TODO Auto-generated method stub
        return orderProductsNewDao.updateForsyncInvoice(orderProduct);
    }

    @Override
    public Integer updateHPAllotNetPoint(OrderProductsNew orderProduct) {
        return orderProductsNewDao.updateHPAllotNetPoint(orderProduct);
    }
    /**
     * 开提单后更新返回状态信息
     * @param orderProduct 网单信息
     * @return
     */
    @Override
    public int updateAfterSyncLes(OrderProductsNew orderProduct){
        return orderProductsNewDao.updateAfterSyncLes(orderProduct);
    }

	@Override
	public int insert(OrderProducts orderProducts) {
		// TODO Auto-generated method stub
		orderProductsNewDao.insertOrderProducts(orderProducts);
		return orderProducts.getId();
	}

	@Override
	public int interceptCancelClose(Integer id, Long closeTime) {
		// TODO Auto-generated method stub
		return orderProductsNewDao.interceptCancelClose(id, closeTime);
	}

    @Override
    public void updateCOrderSn(OrderProducts orderProducts) {
        orderProductsNewDao.updateCOrderSn(orderProducts);
    }

    /**
     * 根据OrderId查询
     * @param id
     * @return
     */
    @Override
    public OrderProductsNew getOrderId(Integer id) {
        // TODO Auto-generated method stub
        return orderProductsNewDao.getOrderId(id);
    }

    @Override
    public List<OrderProductsNew> queryOccupyStockFail(Map<String, Object> params) {
        return orderProductsNewDao.queryOccupyStockFail(params);
    }

    @Override
    public Integer queryOccupyStockFailCount(Map<String, Object> params) {
        return orderProductsNewDao.queryOccupyStockFailCount(params);
    }

    @Override
    public List<OrderProductsNew> getByPdOrderStatusPaging(Integer pdOrderStatus, Integer minId, Integer size) {
        return orderProductsNewDao.getByPdOrderStatusPaging(pdOrderStatus, minId, size);
    }

    @Override
    public Integer updateRRSById(OrderProductsNew orderProducts) {
        return orderProductsNewDao.updateRRSById(orderProducts);
    }

    @Override
    public Integer getSuccessNum(Integer orderId) {
        return orderProductsNewDao.getSuccessNum(orderId);
    }
}
