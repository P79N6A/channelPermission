package com.haier.vehicle.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.purchase.data.model.vehcile.Cn3wPurchaseStock;
import com.haier.purchase.data.model.vehcile.VehicleOrderDTO;
import com.haier.purchase.data.model.vehcile.VehicleOrderDetailsDTO;
import com.haier.purchase.data.service.vechile.PurchaseVehicleOrderDetailService;
import com.haier.vehicle.service.VehicleOrderDetailService;

@Service
public class VehicleOrderDetailServiceImpl implements VehicleOrderDetailService {

	@Autowired
	PurchaseVehicleOrderDetailService purchaseVehicleOrderDetailService;

	@Override
	public int insertSelective(VehicleOrderDetailsDTO entity) {
		return purchaseVehicleOrderDetailService.insertSelective(entity);
	}

	@Override
	public int updateSelectiveById(VehicleOrderDetailsDTO entity) {
		return purchaseVehicleOrderDetailService.updateSelectiveById(entity);
	}

	@Override
	public VehicleOrderDetailsDTO getOneById(long id) {
		return purchaseVehicleOrderDetailService.getOneById(id);
	}

	@Override
	public VehicleOrderDetailsDTO getOneByCondition(
			VehicleOrderDetailsDTO entity) {
		return purchaseVehicleOrderDetailService.getOneByCondition(entity);
	}

	@Override
	public List<VehicleOrderDetailsDTO> getListByCondition(
			VehicleOrderDetailsDTO entity) {
		return purchaseVehicleOrderDetailService.getListByCondition(entity);
	}

	@Override
	public List<VehicleOrderDetailsDTO> getPageByCondition(
			VehicleOrderDetailsDTO entity, int start, int rows) {
		return purchaseVehicleOrderDetailService.getPageByCondition(entity,
				start, rows);
	}

	@Override
	public long getPagerCount(VehicleOrderDetailsDTO entity) {
		return purchaseVehicleOrderDetailService.getPagerCount(entity);
	}

	@Override
	public VehicleOrderDetailsDTO getOneByDeliveryToCode(String deliveryToCode) {
		return purchaseVehicleOrderDetailService
				.getOneByDeliveryToCode(deliveryToCode);
	}

	@Override
	public List<VehicleOrderDetailsDTO> selectByStatus() {
		return purchaseVehicleOrderDetailService.selectByStatus();
	}

	@Override
	public List<VehicleOrderDetailsDTO> selectByWaitToSap() {
		return purchaseVehicleOrderDetailService.selectByWaitToSap();
	}

	@Override
	public int updateSelectiveByItemNo(VehicleOrderDetailsDTO entity) {
		return purchaseVehicleOrderDetailService
				.updateSelectiveByItemNo(entity);
	}

	@Override
	public int deleteByOrderId(Long orderId) {
		return purchaseVehicleOrderDetailService.deleteByOrderId(orderId);
	}

	@Override
	public List<VehicleOrderDetailsDTO> getListByVbeln() {
		return purchaseVehicleOrderDetailService.getListByVbeln();
	}

	@Override
	public void updateVbelnDnStatus(List<String> vbelnDn) {
		purchaseVehicleOrderDetailService.updateVbelnDnStatus(vbelnDn);
	}

	@Override
	public int updateDetailStatus(String itemNo, String orderStatus) {
		return purchaseVehicleOrderDetailService.updateDetailStatus(itemNo,
				orderStatus);
	}

	@Override
	public int selectCount(String itemNo, String orderStatus) {
		return purchaseVehicleOrderDetailService.selectCount(itemNo,
				orderStatus);
	}

	@Override
	public int updateStatus(String itemNo, String orderStatus) {
		return purchaseVehicleOrderDetailService.updateStatus(itemNo,
				orderStatus);
	}

	@Override
	public int updateZqStatus(String itemNo, String orderStatus) {
		return purchaseVehicleOrderDetailService.updateZqStatus(itemNo,
				orderStatus);
	}

	@Override
	public int updateByItemNo(Map<String, String> map) {
		return purchaseVehicleOrderDetailService.updateByItemNo(map);
	}

	@Override
	public void addPurchaseStock(Cn3wPurchaseStock cn3wPurchaseStock) {
		purchaseVehicleOrderDetailService.addPurchaseStock(cn3wPurchaseStock);
	}

	@Override
	public void updateCn3wPurchaseStock(Cn3wPurchaseStock cn3wPurchaseStock) {
		purchaseVehicleOrderDetailService
				.updateCn3wPurchaseStock(cn3wPurchaseStock);
	}

	@Override
	public List<Cn3wPurchaseStock> queryCn3wPurchaseStock(
			Map<String, Object> map) {
		return purchaseVehicleOrderDetailService.queryCn3wPurchaseStock(map);
	}

	@Override
	public void updateByOrderNo(VehicleOrderDetailsDTO order) {
		purchaseVehicleOrderDetailService.updateByOrderNo(order);
	}

	@Override
	public int updateVbelnSpareByItemNo(String itemNo, String vbelnSpare) {
		return purchaseVehicleOrderDetailService.updateVbelnSpareByItemNo(itemNo, vbelnSpare);
	}

	/**
	 * 是否存在相同的vbeln
	 * @param itemNo
	 * @param vbelnSpare
	 * @return
	 */
	@Override
	public boolean vbelnExists(String itemNo, String vbelnSpare) {
		return purchaseVehicleOrderDetailService.vbelnExists(itemNo, vbelnSpare);
	}
}
