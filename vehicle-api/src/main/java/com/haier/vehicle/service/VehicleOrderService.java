package com.haier.vehicle.service;

import java.util.List;
import java.util.Map;

import com.haier.purchase.data.model.vehcile.ExportVehicleDTO;
import com.haier.purchase.data.model.vehcile.VehicleOrderDTO;

public interface VehicleOrderService {
	
	public int insertSelective(VehicleOrderDTO entity);

	
	public int updateSelectiveById(VehicleOrderDTO entity);

	
	public VehicleOrderDTO getOneById(long id);

	
	public VehicleOrderDTO getOneByCondition(VehicleOrderDTO entity);
	
	public List<VehicleOrderDTO> getListByCondition(VehicleOrderDTO entity);

	
	public List<VehicleOrderDTO> getPageByCondition(VehicleOrderDTO entity,
			int start, int rows);

	
	public long getPagerCount(VehicleOrderDTO entity);

	
	public VehicleOrderDTO getOneByDeliveryToCode(String deliveryToCode);

	
	public String getVehicleOrderNo(String begin);

	public int updateSelectiveByOrderNo(VehicleOrderDTO entity);
	
	public List<ExportVehicleDTO> selectVehicleExport(Map<String, Object> params);

	public String getWhCode (String code);
}
