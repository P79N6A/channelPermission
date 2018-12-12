package com.haier.vehicle.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.purchase.data.model.vehcile.DepartmentInfoDTO;
import com.haier.purchase.data.service.vechile.PurchaseDepartmentInfoService;
import com.haier.vehicle.service.DepartmentInfoService;

@Service
public class DepartmentInfoServiceImpl implements DepartmentInfoService{
	
	@Autowired
	PurchaseDepartmentInfoService purchaseDepartmentInfoService;

	@Override
	public int insertSelective(DepartmentInfoDTO entity) {
		return purchaseDepartmentInfoService.insertSelective(entity);
	}

	@Override
	public int updateSelectiveById(DepartmentInfoDTO entity) {
		return purchaseDepartmentInfoService.updateSelectiveById(entity);
	}

	@Override
	public DepartmentInfoDTO getOneById(long id) {
		return purchaseDepartmentInfoService.getOneById(id);
	}

	@Override
	public DepartmentInfoDTO getOneByCondition(DepartmentInfoDTO entity) {
		return purchaseDepartmentInfoService.getOneByCondition(entity);
	}

	@Override
	public List<DepartmentInfoDTO> getListByCondition(DepartmentInfoDTO entity) {
		return purchaseDepartmentInfoService.getListByCondition(entity);
	}

	@Override
	public List<DepartmentInfoDTO> getPageByCondition(DepartmentInfoDTO entity,
			int start, int rows) {
		return purchaseDepartmentInfoService.getPageByCondition(entity, start, rows);
	}

	@Override
	public long getPagerCount(DepartmentInfoDTO entity) {
		return purchaseDepartmentInfoService.getPagerCount(entity);
	}

	@Override
	public DepartmentInfoDTO getOneByDeliveryToCode(String deliveryToCode) {
		return purchaseDepartmentInfoService.getOneByDeliveryToCode(deliveryToCode);
	}


}