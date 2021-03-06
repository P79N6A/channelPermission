package com.haier.vehicle.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.purchase.data.model.vehcile.PurchaseProductPaymentDTO;
import com.haier.purchase.data.model.vehcile.VehicleProductPaymentDTO;
import com.haier.purchase.data.service.vechile.PurchaseVehicleProductPaymentService;
import com.haier.vehicle.service.VehicleProductPaymentService;

/**
 * <p>
 * Description:
 * </p>
 * ClassName:VehicleProductPaymentDao Created on 2017/9/8
 *
 * @author wsh
 * @version 1.0 Copyright (c) 2015 北京柯莱特科技有限公司 交付部
 */
@Service
public class VehicleProductPaymentServiceImpl implements
		VehicleProductPaymentService {

	@Autowired
	PurchaseVehicleProductPaymentService vehicleProductPaymentDao;

	@Override
	public int insertSelective(VehicleProductPaymentDTO entity) {
		return vehicleProductPaymentDao.insertSelective(entity);
	}

	@Override
	public int updateSelectiveById(VehicleProductPaymentDTO entity) {
		return vehicleProductPaymentDao.updateSelectiveById(entity);
	}

	@Override
	public VehicleProductPaymentDTO getOneById(long id) {
		return vehicleProductPaymentDao.getOneById(id);
	}

	@Override
	public VehicleProductPaymentDTO getOneByCondition(
			VehicleProductPaymentDTO entity) {
		return vehicleProductPaymentDao.getOneByCondition(entity);
	}
	
	@Override
	public PurchaseProductPaymentDTO getPurchasePaymentOneByCondition(VehicleProductPaymentDTO entity){
		return vehicleProductPaymentDao.getPurchasePaymentOneByCondition(entity);
	}

	@Override
	public List<VehicleProductPaymentDTO> getListByCondition(
			VehicleProductPaymentDTO entity) {
		return vehicleProductPaymentDao.getListByCondition(entity);
	}

	@Override
	public List<VehicleProductPaymentDTO> getPageByCondition(
			VehicleProductPaymentDTO entity, int start, int rows) {
		return vehicleProductPaymentDao.getPageByCondition(entity, start, rows);
	}

	@Override
	public long getPagerCount(VehicleProductPaymentDTO entity) {
		return vehicleProductPaymentDao.getPagerCount(entity);
	}

	@Override
	public VehicleProductPaymentDTO getOneByDeliveryToCode(String deliveryToCode) {
		return vehicleProductPaymentDao.getOneByDeliveryToCode(deliveryToCode);
	}

	@Override
	public List<PurchaseProductPaymentDTO> getList() {
		return vehicleProductPaymentDao.getList();
	}

	@Override
	public List<VehicleProductPaymentDTO> listByCondition(
			VehicleProductPaymentDTO entity) {
		return vehicleProductPaymentDao.listByCondition(entity);
	}

}
