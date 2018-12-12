package com.haier.purchase.data.services.vechile;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.purchase.data.dao.vehcile.DepartmentInfoDao;
import com.haier.purchase.data.model.vehcile.DepartmentInfoDTO;
import com.haier.purchase.data.service.vechile.PurchaseDepartmentInfoService;

@Service
public class PurchaseDepartmentInfoServiceImpl implements PurchaseDepartmentInfoService{
	
	@Autowired
	DepartmentInfoDao departmentInfoDao;

	@Override
	public int insertSelective(DepartmentInfoDTO entity) {
		return departmentInfoDao.insertSelective(entity);
	}

	@Override
	public int updateSelectiveById(DepartmentInfoDTO entity) {
		return departmentInfoDao.updateSelectiveById(entity);
	}

	@Override
	public DepartmentInfoDTO getOneById(long id) {
		return departmentInfoDao.getOneById(id);
	}

	@Override
	public DepartmentInfoDTO getOneByCondition(DepartmentInfoDTO entity) {
		return departmentInfoDao.getOneByCondition(entity);
	}

	@Override
	public List<DepartmentInfoDTO> getListByCondition(DepartmentInfoDTO entity) {
		return departmentInfoDao.getListByCondition(entity);
	}

	@Override
	public List<DepartmentInfoDTO> getPageByCondition(DepartmentInfoDTO entity,
			int start, int rows) {
		return departmentInfoDao.getPageByCondition(entity, start, rows);
	}

	@Override
	public long getPagerCount(DepartmentInfoDTO entity) {
		return departmentInfoDao.getPagerCount(entity);
	}

	@Override
	public DepartmentInfoDTO getOneByDeliveryToCode(String deliveryToCode) {
		return departmentInfoDao.getOneByDeliveryToCode(deliveryToCode);
	}


}