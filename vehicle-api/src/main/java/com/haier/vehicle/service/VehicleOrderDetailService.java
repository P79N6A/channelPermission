package com.haier.vehicle.service;

import java.util.List;
import java.util.Map;

import com.haier.purchase.data.model.vehcile.Cn3wPurchaseStock;
import com.haier.purchase.data.model.vehcile.VehicleOrderDTO;
import com.haier.purchase.data.model.vehcile.VehicleOrderDetailsDTO;

public interface VehicleOrderDetailService {

	public int insertSelective(VehicleOrderDetailsDTO entity);

	public int updateSelectiveById(VehicleOrderDetailsDTO entity);

	public VehicleOrderDetailsDTO getOneById(long id);

	public VehicleOrderDetailsDTO getOneByCondition(
			VehicleOrderDetailsDTO entity);

	public List<VehicleOrderDetailsDTO> getListByCondition(
			VehicleOrderDetailsDTO entity);

	public List<VehicleOrderDetailsDTO> getPageByCondition(
			VehicleOrderDetailsDTO entity, int start, int rows);

	public long getPagerCount(VehicleOrderDetailsDTO entity);

	public VehicleOrderDetailsDTO getOneByDeliveryToCode(String deliveryToCode);

	public List<VehicleOrderDetailsDTO> selectByStatus();

	public List<VehicleOrderDetailsDTO> selectByWaitToSap();

	public int updateSelectiveByItemNo(VehicleOrderDetailsDTO entity);

	public int deleteByOrderId(Long orderId);

	public List<VehicleOrderDetailsDTO> getListByVbeln();

	public void updateVbelnDnStatus(List<String> vbelnDn);

	public int updateDetailStatus(String itemNo, String orderStatus);

	public int selectCount(String itemNo, String orderStatus);

	public int updateStatus(String itemNo, String orderStatus);

	public int updateZqStatus(String itemNo, String orderStatus);

	public int updateByItemNo(Map<String, String> map);

	public void addPurchaseStock(Cn3wPurchaseStock cn3wPurchaseStock);

	public void updateCn3wPurchaseStock(Cn3wPurchaseStock cn3wPurchaseStock);

	public List<Cn3wPurchaseStock> queryCn3wPurchaseStock(
			Map<String, Object> map);

	public void updateByOrderNo(VehicleOrderDetailsDTO order);

	public int updateVbelnSpareByItemNo(String itemNo, String vbelnSpare);

	public boolean vbelnExists(String itemNo, String vbelnSpare);
}
