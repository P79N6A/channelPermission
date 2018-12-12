package com.haier.vehicle.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.purchase.data.model.vehcile.VehicleOrderZqDTO;
import com.haier.purchase.data.service.vechile.PurchaseVehicleOrderZqService;
import com.haier.vehicle.service.VehicleOrderZqService;

/**
 * <p>
 * Description: []
 * </p>
 * Created on 2017年09月15日}
 *
 * @author <a href="mailto: Zhangzengbao32@camelotchina.com">zzb</a>
 * @version 1.0 Copyright (c) 2016 北京柯莱特科技有限公司 交付部
 */
@Service
public class VehicleOrderZqServiceImpl implements VehicleOrderZqService{
	
	@Autowired
	PurchaseVehicleOrderZqService purchaseVehicleOrderZqService;

	@Override
	public int insertSelective(VehicleOrderZqDTO entity) {
		return purchaseVehicleOrderZqService.insertSelective(entity);
	}

	@Override
	public int updateSelectiveById(VehicleOrderZqDTO entity) {
		return purchaseVehicleOrderZqService.updateSelectiveById(entity);
	}

	@Override
	public VehicleOrderZqDTO getOneById(long id) {
		return purchaseVehicleOrderZqService.getOneById(id);
	}

	@Override
	public VehicleOrderZqDTO getOneByCondition(VehicleOrderZqDTO entity) {
		return purchaseVehicleOrderZqService.getOneByCondition(entity);
	}

	@Override
	public List<VehicleOrderZqDTO> getListByCondition(VehicleOrderZqDTO entity) {
		return purchaseVehicleOrderZqService.getListByCondition(entity);
	}

	@Override
	public List<VehicleOrderZqDTO> getPageByCondition(VehicleOrderZqDTO entity,
			int start, int rows) {
		return purchaseVehicleOrderZqService.getPageByCondition(entity, start, rows);
	}

	@Override
	public long getPagerCount(VehicleOrderZqDTO entity) {
		return purchaseVehicleOrderZqService.getPagerCount(entity);
	}

	@Override
	public VehicleOrderZqDTO getOneByDeliveryToCode(String deliveryToCode) {
		return purchaseVehicleOrderZqService.getOneByDeliveryToCode(deliveryToCode);
	}

	@Override
	public List<VehicleOrderZqDTO> listByCondition(VehicleOrderZqDTO entity) {
		return purchaseVehicleOrderZqService.listByCondition(entity);
	}

	@Override
	public int updateStatus(String orderNo) {
		return purchaseVehicleOrderZqService.updateStatus(orderNo);
	}
}
