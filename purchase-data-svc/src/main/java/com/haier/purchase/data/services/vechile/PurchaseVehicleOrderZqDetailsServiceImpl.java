package com.haier.purchase.data.services.vechile;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.purchase.data.dao.vehcile.VehicleOrderZqDetailsDao;
import com.haier.purchase.data.model.vehcile.VehicleOrderZqDetailsDTO;
import com.haier.purchase.data.service.vechile.PurchaseVehicleOrderZqDetailsService;

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
public class PurchaseVehicleOrderZqDetailsServiceImpl implements PurchaseVehicleOrderZqDetailsService{

	@Autowired
	VehicleOrderZqDetailsDao vehicleOrderZqDetailsDao ;
	
	@Override
	public int insertSelective(VehicleOrderZqDetailsDTO entity) {
		return vehicleOrderZqDetailsDao.insertSelective(entity);
	}

	@Override
	public int updateSelectiveById(VehicleOrderZqDetailsDTO entity) {
		return vehicleOrderZqDetailsDao.updateSelectiveById(entity);
	}

	@Override
	public VehicleOrderZqDetailsDTO getOneById(long id) {
		return vehicleOrderZqDetailsDao.getOneById(id);
	}

	@Override
	public VehicleOrderZqDetailsDTO getOneByCondition(
			VehicleOrderZqDetailsDTO entity) {
		return vehicleOrderZqDetailsDao.getOneByCondition(entity);
	}

	@Override
	public List<VehicleOrderZqDetailsDTO> getListByCondition(
			VehicleOrderZqDetailsDTO entity) {
		return vehicleOrderZqDetailsDao.getListByCondition(entity);
	}

	@Override
	public List<VehicleOrderZqDetailsDTO> getPageByCondition(
			VehicleOrderZqDetailsDTO entity, int start, int rows) {
		return vehicleOrderZqDetailsDao.getPageByCondition(entity, start, rows);
	}

	@Override
	public long getPagerCount(VehicleOrderZqDetailsDTO entity) {
		return vehicleOrderZqDetailsDao.getPagerCount(entity);
	}

	@Override
	public VehicleOrderZqDetailsDTO getOneByDeliveryToCode(String deliveryToCode) {
		return vehicleOrderZqDetailsDao.getOneByDeliveryToCode(deliveryToCode);
	}

	@Override
	public List<VehicleOrderZqDetailsDTO> listByCondition(
			VehicleOrderZqDetailsDTO entity) {
		return vehicleOrderZqDetailsDao.listByCondition(entity);
	}

	@Override
	public int updateSelectiveByZqItemNo(VehicleOrderZqDetailsDTO entity) {
		return vehicleOrderZqDetailsDao.updateSelectiveByZqItemNo(entity);
	}

	@Override
	public int updateStatusDetail(String orderNo) {
		return vehicleOrderZqDetailsDao.updateStatusDetail(orderNo);
	}

	@Override
	public int updateMessageDetail(String orderNo, String mesageg) {
		return vehicleOrderZqDetailsDao.updateMessageDetail(orderNo, mesageg);
	}

	@Override
	public List<VehicleOrderZqDetailsDTO> selectByStatus() {
		return vehicleOrderZqDetailsDao.selectByStatus();
	}
	
}
