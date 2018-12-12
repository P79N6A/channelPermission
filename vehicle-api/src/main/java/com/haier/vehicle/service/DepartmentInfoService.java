package com.haier.vehicle.service;


import java.util.List;

import com.haier.purchase.data.model.vehcile.DepartmentInfoDTO;

public interface DepartmentInfoService {
	
	public int insertSelective(DepartmentInfoDTO entity) ;

	
	public int updateSelectiveById(DepartmentInfoDTO entity) ;

	
	public DepartmentInfoDTO getOneById(long id);

	
	public DepartmentInfoDTO getOneByCondition(DepartmentInfoDTO entity) ;

	
	public List<DepartmentInfoDTO> getListByCondition(DepartmentInfoDTO entity) ;

	
	public List<DepartmentInfoDTO> getPageByCondition(DepartmentInfoDTO entity,
			int start, int rows) ;

	
	public long getPagerCount(DepartmentInfoDTO entity) ;

	
	public DepartmentInfoDTO getOneByDeliveryToCode(String deliveryToCode);


}