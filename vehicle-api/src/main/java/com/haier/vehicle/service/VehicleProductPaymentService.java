package com.haier.vehicle.service;

import java.util.List;

import com.haier.purchase.data.model.vehcile.PurchaseProductPaymentDTO;
import com.haier.purchase.data.model.vehcile.VehicleOrderDTO;
import com.haier.purchase.data.model.vehcile.VehicleProductPaymentDTO;

public interface VehicleProductPaymentService {

	public int insertSelective(VehicleProductPaymentDTO entity);

	public int updateSelectiveById(VehicleProductPaymentDTO entity) ;


	public VehicleProductPaymentDTO getOneById(long id);

	public VehicleProductPaymentDTO getOneByCondition(
			VehicleProductPaymentDTO entity);
	
	public PurchaseProductPaymentDTO getPurchasePaymentOneByCondition(VehicleProductPaymentDTO entity);

	public List<VehicleProductPaymentDTO> getListByCondition(
			VehicleProductPaymentDTO entity);

	public List<VehicleProductPaymentDTO> getPageByCondition(
			VehicleProductPaymentDTO entity, int start, int rows);

	public long getPagerCount(VehicleProductPaymentDTO entity);

	public VehicleProductPaymentDTO getOneByDeliveryToCode(String deliveryToCode);

	public List<PurchaseProductPaymentDTO> getList();

	public List<VehicleProductPaymentDTO> listByCondition(
			VehicleProductPaymentDTO entity);
}
