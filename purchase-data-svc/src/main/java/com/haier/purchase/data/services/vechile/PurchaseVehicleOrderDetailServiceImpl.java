package com.haier.purchase.data.services.vechile;

import java.util.List;
import java.util.Map;

import com.haier.purchase.data.dao.vehcile.VehicleOrderDetailDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.purchase.data.model.vehcile.Cn3wPurchaseStock;
import com.haier.purchase.data.model.vehcile.Entry3wOrder;
import com.haier.purchase.data.model.vehcile.VehicleOrderDTO;
import com.haier.purchase.data.model.vehcile.VehicleOrderDetailsDTO;
import com.haier.purchase.data.service.vechile.PurchaseVehicleOrderDetailService;

/**
 * <p>
 * Description:
 * </p>
 * ClassName:VehicleOrderDetailDao Created on 2017/9/7
 *
 * @author wsh
 * @version 1.0 Copyright (c) 2015 北京柯莱特科技有限公司 交付部
 */
@Service
public class PurchaseVehicleOrderDetailServiceImpl implements PurchaseVehicleOrderDetailService{
	
	@Autowired
	VehicleOrderDetailDao vehicleOrderDetailDao;

	@Override
	public int insertSelective(VehicleOrderDetailsDTO entity) {
		return vehicleOrderDetailDao.insertSelective(entity);
	}

	@Override
	public int updateSelectiveById(VehicleOrderDetailsDTO entity) {
		return vehicleOrderDetailDao.updateSelectiveById(entity);
	}

	@Override
	public VehicleOrderDetailsDTO getOneById(long id) {
		return vehicleOrderDetailDao.getOneById(id);
	}

	@Override
	public VehicleOrderDetailsDTO getOneByCondition(
			VehicleOrderDetailsDTO entity) {
		return vehicleOrderDetailDao.getOneByCondition(entity);
	}

	@Override
	public List<VehicleOrderDetailsDTO> getListByCondition(
			VehicleOrderDetailsDTO entity) {
		return vehicleOrderDetailDao.getListByCondition(entity);
	}

	@Override
	public List<VehicleOrderDetailsDTO> getPageByCondition(
			VehicleOrderDetailsDTO entity, int start, int rows) {
		return vehicleOrderDetailDao.getPageByCondition(entity, start, rows);
	}

	@Override
	public long getPagerCount(VehicleOrderDetailsDTO entity) {
		return vehicleOrderDetailDao.getPagerCount(entity);
	}

	@Override
	public VehicleOrderDetailsDTO getOneByDeliveryToCode(String deliveryToCode) {
		return vehicleOrderDetailDao.getOneByDeliveryToCode(deliveryToCode);
	}

	@Override
	public List<VehicleOrderDetailsDTO> selectByStatus() {
		return vehicleOrderDetailDao.selectByStatus();
	}

	@Override
	public List<VehicleOrderDetailsDTO> selectByWaitToSap() {
		return vehicleOrderDetailDao.selectByWaitToSap();
	}

	@Override
	public int updateSelectiveByItemNo(VehicleOrderDetailsDTO entity) {
		return vehicleOrderDetailDao.updateSelectiveByItemNo(entity);
	}

	@Override
	public int deleteByOrderId(Long orderId) {
		return vehicleOrderDetailDao.deleteByOrderId(orderId);
	}

	@Override
	public List<VehicleOrderDetailsDTO> getListByVbeln() {
		return vehicleOrderDetailDao.getListByVbeln();
	}

	@Override
	public void updateVbelnDnStatus(List<String> vbelnDn) {
		vehicleOrderDetailDao.updateVbelnDnStatus(vbelnDn);
	}

	@Override
	public int updateDetailStatus(String itemNo, String orderStatus) {
		return vehicleOrderDetailDao.updateDetailStatus(itemNo, orderStatus);
	}

	@Override
	public int selectCount(String itemNo, String orderStatus) {
		return vehicleOrderDetailDao.selectCount(itemNo, orderStatus);
	}

	@Override
	public int updateStatus(String itemNo, String orderStatus) {
		return vehicleOrderDetailDao.updateStatus(itemNo, orderStatus);
	}

	@Override
	public int updateZqStatus(String itemNo, String orderStatus) {
		return vehicleOrderDetailDao.updateZqStatus(itemNo, orderStatus);
	}

	@Override
	public int updateByItemNo(Map<String, String> map) {
		return vehicleOrderDetailDao.updateByItemNo(map);
	}

	@Override
	public void addPurchaseStock(Cn3wPurchaseStock cn3wPurchaseStock) {
		vehicleOrderDetailDao.addPurchaseStock(cn3wPurchaseStock);		
	}

	@Override
	public void updateCn3wPurchaseStock(Cn3wPurchaseStock cn3wPurchaseStock) {
		vehicleOrderDetailDao.updateCn3wPurchaseStock(cn3wPurchaseStock);
	}

	@Override
	public List<Cn3wPurchaseStock> queryCn3wPurchaseStock(
			Map<String, Object> map) {
		return vehicleOrderDetailDao.queryCn3wPurchaseStock(map);
	}

	@Override
	public void addEntry3wOrder(Entry3wOrder entry3wOrder) {
		vehicleOrderDetailDao.addEntry3wOrder(entry3wOrder);		
	}

	@Override
	public void updateEntry3wOrder(Entry3wOrder entry3wOrder) {
		vehicleOrderDetailDao.updateEntry3wOrder(entry3wOrder);		
	}

	@Override
	public List<Entry3wOrder> queryEntry3wOrder(Map<String, Object> map) {
		return vehicleOrderDetailDao.queryEntry3wOrder(map);
	}

	@Override
	public List<VehicleOrderDetailsDTO> selectByWaitUpdateLbx() {
		return vehicleOrderDetailDao.selectByWaitUpdateLbx();
	}

	@Override
	public void updateZqDetailStatus(String zqItemNo, String status) {
		vehicleOrderDetailDao.updateZqDetailStatus(zqItemNo, status);
	}

	@Override
	public int selectZqCount(String zqItemNo, String status) {
		return vehicleOrderDetailDao.selectZqCount(zqItemNo, status);
	}

	@Override
	public void updateByOrderNo(VehicleOrderDetailsDTO order) {
		vehicleOrderDetailDao.updateByOrderNo(order);		
	}

	@Override
	public List<VehicleOrderDetailsDTO> getByVbeln(String vbeln) {
		return vehicleOrderDetailDao.getByVbeln(vbeln);
	}

	@Override
	public void updateEntry3wOrderById(Cn3wPurchaseStock cn3wPurchaseStock) {
		vehicleOrderDetailDao.updateEntry3wOrderById(cn3wPurchaseStock);		
	}

	@Override
	public int updateVbelnSpareByItemNo(String itemNo, String vbelnSpare) {
		return vehicleOrderDetailDao.updateVbelnSpareByItemNo(itemNo, vbelnSpare);
	}

	@Override
	public boolean vbelnExists(String itemNo, String vbelnSpare) {
		int count = vehicleOrderDetailDao.vbelnExists(itemNo, vbelnSpare);
		return count > 0 ? true : false;
	}


}
