package com.haier.vehicle.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.purchase.data.model.vehcile.ExportVehicleDTO;
import com.haier.purchase.data.model.vehcile.VehicleOrderDTO;
import com.haier.purchase.data.service.vechile.PurchaseVehicleOrderService;
import com.haier.vehicle.service.VehicleOrderService;

@Service
public class VehicleOrderServiceImpl implements VehicleOrderService{
	
	@Autowired
	PurchaseVehicleOrderService pourchaseVehicleOrderService; 
	@Override
	public int insertSelective(VehicleOrderDTO entity) {
		return pourchaseVehicleOrderService.insertSelective(entity);
	}

	@Override
	public int updateSelectiveById(VehicleOrderDTO entity) {
		return pourchaseVehicleOrderService.updateSelectiveById(entity);
	}

	@Override
	public VehicleOrderDTO getOneById(long id) {
		return pourchaseVehicleOrderService.getOneById(id);
	}

	@Override
	public VehicleOrderDTO getOneByCondition(VehicleOrderDTO entity) {
		return pourchaseVehicleOrderService.getOneByCondition(entity);
	}

	@Override
	public List<VehicleOrderDTO> getListByCondition(VehicleOrderDTO entity) {
		return pourchaseVehicleOrderService.getListByCondition(entity);
	}

	@Override
	public List<VehicleOrderDTO> getPageByCondition(VehicleOrderDTO entity,
			int start, int rows) {
		return pourchaseVehicleOrderService.getPageByCondition(entity, start, rows);
	}

	@Override
	public long getPagerCount(VehicleOrderDTO entity) {
		return pourchaseVehicleOrderService.getPagerCount(entity);
	}

	@Override
	public VehicleOrderDTO getOneByDeliveryToCode(String deliveryToCode) {
		return pourchaseVehicleOrderService.getOneByDeliveryToCode(deliveryToCode);
	}

	@Override
	public String getVehicleOrderNo(String begin) {
		return pourchaseVehicleOrderService.getVehicleOrderNo(begin);
	}

	@Override
	public int updateSelectiveByOrderNo(VehicleOrderDTO entity) {
		return pourchaseVehicleOrderService.updateSelectiveByOrderNo(entity);
	}
	
	@Override
	public List<ExportVehicleDTO> selectVehicleExport(Map<String, Object> params) {
		return pourchaseVehicleOrderService.selectVehicleExport(params);
	}

	@Override
	public String getWhCode(String code) {
		return pourchaseVehicleOrderService.getWhCode(code);
	}

}
