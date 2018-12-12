package com.haier.vehicle.service;

import java.util.List;

import com.haier.purchase.data.model.vehcile.AreaCenterInfoDTO;

public interface AreaCenterInfoService {

	public int insertSelective(AreaCenterInfoDTO entity);

	public int updateSelectiveById(AreaCenterInfoDTO entity);

	public AreaCenterInfoDTO getOneById(long id);

	public AreaCenterInfoDTO getOneByCondition(AreaCenterInfoDTO entity);

	public List<AreaCenterInfoDTO> getListByCondition(AreaCenterInfoDTO entity);

	public List<AreaCenterInfoDTO> getPageByCondition(AreaCenterInfoDTO entity,
			int start, int rows);

	public long getPagerCount(AreaCenterInfoDTO entity);

	public AreaCenterInfoDTO getOneByDeliveryToCode(String deliveryToCode);

}