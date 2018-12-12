package com.haier.purchase.data.services.vechile;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.purchase.data.dao.vehcile.VehicleProductPaymentDao;
import com.haier.purchase.data.model.vehcile.VehicleProductPaymentDTO;
import com.haier.purchase.data.service.vechile.PurchaseVehicleProductPaymentService;

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
public class PurchaseVehicleProductPaymentServiceImpl implements
		PurchaseVehicleProductPaymentService {

	@Autowired
	VehicleProductPaymentDao vehicleProductPaymentDao;

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
	public List<VehicleProductPaymentDTO> getList() {
		return vehicleProductPaymentDao.getList();
	}

	@Override
	public List<VehicleProductPaymentDTO> listByCondition(
			VehicleProductPaymentDTO entity) {
		return vehicleProductPaymentDao.listByCondition(entity);
	}

}
