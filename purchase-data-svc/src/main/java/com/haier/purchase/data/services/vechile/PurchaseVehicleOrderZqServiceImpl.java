package com.haier.purchase.data.services.vechile;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.purchase.data.dao.vehcile.VehicleOrderZqDao;
import com.haier.purchase.data.model.vehcile.VehicleOrderZqDTO;
import com.haier.purchase.data.service.vechile.PurchaseVehicleOrderZqService;

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
public class PurchaseVehicleOrderZqServiceImpl implements PurchaseVehicleOrderZqService{
	
	@Autowired
	VehicleOrderZqDao vehicleOrderZqDao;

	@Override
	public int insertSelective(VehicleOrderZqDTO entity) {
		return vehicleOrderZqDao.insertSelective(entity);
	}

	@Override
	public int updateSelectiveById(VehicleOrderZqDTO entity) {
		return vehicleOrderZqDao.updateSelectiveById(entity);
	}

	@Override
	public VehicleOrderZqDTO getOneById(long id) {
		return vehicleOrderZqDao.getOneById(id);
	}

	@Override
	public VehicleOrderZqDTO getOneByCondition(VehicleOrderZqDTO entity) {
		return vehicleOrderZqDao.getOneByCondition(entity);
	}

	@Override
	public List<VehicleOrderZqDTO> getListByCondition(VehicleOrderZqDTO entity) {
		return vehicleOrderZqDao.getListByCondition(entity);
	}

	@Override
	public List<VehicleOrderZqDTO> getPageByCondition(VehicleOrderZqDTO entity,
			int start, int rows) {
		return vehicleOrderZqDao.getPageByCondition(entity, start, rows);
	}

	@Override
	public long getPagerCount(VehicleOrderZqDTO entity) {
		return vehicleOrderZqDao.getPagerCount(entity);
	}

	@Override
	public VehicleOrderZqDTO getOneByDeliveryToCode(String deliveryToCode) {
		return vehicleOrderZqDao.getOneByDeliveryToCode(deliveryToCode);
	}

	@Override
	public List<VehicleOrderZqDTO> listByCondition(VehicleOrderZqDTO entity) {
		return vehicleOrderZqDao.listByCondition(entity);
	}

	@Override
	public int updateStatus(String orderNo) {
		return vehicleOrderZqDao.updateStatus(orderNo);
	}
}
