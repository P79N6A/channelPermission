package com.haier.purchase.data.services.vechile;



import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.purchase.data.dao.vehcile.VehicleOrderDao;
import com.haier.purchase.data.model.vehcile.ExportVehicleDTO;
import com.haier.purchase.data.model.vehcile.VehicleOrderDTO;
import com.haier.purchase.data.service.vechile.PurchaseVehicleOrderService;

/**
 * <p>Description: </p>
 * ClassName:VehicleOrderDao
 * Created on 2017/9/6
 *
 * @author wsh
 * @version 1.0
 * Copyright (c) 2015 北京柯莱特科技有限公司 交付部
 */
@Service
public class PurchaseVehicleOrderServiceImpl implements PurchaseVehicleOrderService{

	@Autowired
	VehicleOrderDao vehicleOrderDao;
	
	@Override
	public int insertSelective(VehicleOrderDTO entity) {
		return vehicleOrderDao.insertSelective(entity);
	}

	@Override
	public int updateSelectiveById(VehicleOrderDTO entity) {
		return vehicleOrderDao.updateSelectiveById(entity);
	}

	@Override
	public VehicleOrderDTO getOneById(long id) {
		return vehicleOrderDao.getOneById(id);
	}

	@Override
	public VehicleOrderDTO getOneByCondition(VehicleOrderDTO entity) {
		return vehicleOrderDao.getOneByCondition(entity);
	}

	@Override
	public List<VehicleOrderDTO> getListByCondition(VehicleOrderDTO entity) {
		return vehicleOrderDao.getListByCondition(entity);
	}

	@Override
	public List<VehicleOrderDTO> getPageByCondition(VehicleOrderDTO entity,
			int start, int rows) {
		return vehicleOrderDao.getPageByCondition(entity, start, rows);
	}

	@Override
	public long getPagerCount(VehicleOrderDTO entity) {
		return vehicleOrderDao.getPagerCount(entity);
	}

	@Override
	public VehicleOrderDTO getOneByDeliveryToCode(String deliveryToCode) {
		return vehicleOrderDao.getOneByDeliveryToCode(deliveryToCode);
	}

	@Override
	public String getVehicleOrderNo(String begin) {
		return vehicleOrderDao.getVehicleOrderNo(begin);
	}

	@Override
	public int updateSelectiveByOrderNo(VehicleOrderDTO entity) {
		return vehicleOrderDao.updateSelectiveByOrderNo(entity);
	}

	@Override
	public List<ExportVehicleDTO> selectVehicleExport(Map<String, Object> params) {
		return vehicleOrderDao.selectVehicleExport(params);
	}

	@Override
	public String getWhCode(String code) {
		return vehicleOrderDao.getWhCode(code);
	}


}
