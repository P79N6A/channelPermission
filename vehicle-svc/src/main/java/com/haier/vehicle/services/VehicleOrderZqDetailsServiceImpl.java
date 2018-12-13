package com.haier.vehicle.services;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.haier.purchase.data.model.vehcile.VehicleOrderDTO;
import com.haier.purchase.data.model.vehcile.VehicleOrderDetailsDTO;
import com.haier.purchase.data.model.vehcile.VehicleOrderZqDetailsDTO;
import com.haier.purchase.data.service.vechile.PurchaseVehicleOrderZqDetailsService;
import com.haier.vehicle.service.VehicleOrderZqDetailsService;
import com.haier.vehicle.util.HttpUtils;

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
public class VehicleOrderZqDetailsServiceImpl implements
		VehicleOrderZqDetailsService {

	@Autowired
	PurchaseVehicleOrderZqDetailsService purchaseVehicleOrderZqDetailsService;


	@Override
	public int insertSelective(VehicleOrderZqDetailsDTO entity) {
		return purchaseVehicleOrderZqDetailsService.insertSelective(entity);
	}

	@Override
	public int updateSelectiveById(VehicleOrderZqDetailsDTO entity) {
		return purchaseVehicleOrderZqDetailsService.updateSelectiveById(entity);
	}

	@Override
	public VehicleOrderZqDetailsDTO getOneById(long id) {
		return purchaseVehicleOrderZqDetailsService.getOneById(id);
	}

	@Override
	public VehicleOrderZqDetailsDTO getOneByCondition(
			VehicleOrderZqDetailsDTO entity) {
		return purchaseVehicleOrderZqDetailsService.getOneByCondition(entity);
	}

	@Override
	public List<VehicleOrderZqDetailsDTO> getListByCondition(
			VehicleOrderZqDetailsDTO entity) {
		return purchaseVehicleOrderZqDetailsService.getListByCondition(entity);
	}

	@Override
	public List<VehicleOrderZqDetailsDTO> getPageByCondition(
			VehicleOrderZqDetailsDTO entity, int start, int rows) {
		return purchaseVehicleOrderZqDetailsService.getPageByCondition(entity,
				start, rows);
	}

	@Override
	public long getPagerCount(VehicleOrderZqDetailsDTO entity) {
		return purchaseVehicleOrderZqDetailsService.getPagerCount(entity);
	}

	@Override
	public VehicleOrderZqDetailsDTO getOneByDeliveryToCode(String deliveryToCode) {
		return purchaseVehicleOrderZqDetailsService
				.getOneByDeliveryToCode(deliveryToCode);
	}

	@Override
	public List<VehicleOrderZqDetailsDTO> listByCondition(
			VehicleOrderZqDetailsDTO entity) {
		return purchaseVehicleOrderZqDetailsService.listByCondition(entity);
	}

	@Override
	public int updateSelectiveByZqItemNo(VehicleOrderZqDetailsDTO entity) {
		return purchaseVehicleOrderZqDetailsService
				.updateSelectiveByZqItemNo(entity);
	}

	@Override
	public int updateStatusDetail(String orderNo) {
		return purchaseVehicleOrderZqDetailsService.updateStatusDetail(orderNo);
	}

	@Override
	public int updateMessageDetail(String orderNo, String mesageg) {
		return purchaseVehicleOrderZqDetailsService.updateMessageDetail(
				orderNo, mesageg);
	}

	@Override
	public List<VehicleOrderZqDetailsDTO> getListByOrderNo(String orderNo) {
		return purchaseVehicleOrderZqDetailsService.getListByOrderNo(orderNo);
	}

	@Override
	public void updateSelectiveByZqOrderNo(
			VehicleOrderZqDetailsDTO zqOrderDetail) {
		purchaseVehicleOrderZqDetailsService
				.updateSelectiveByZqOrderNo(zqOrderDetail);
	}

}
