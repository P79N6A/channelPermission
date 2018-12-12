package com.haier.vehicle.service;

import java.util.List;

import com.haier.purchase.data.model.vehcile.MaterielInfoDTO;

public interface MaterielInfoService {

	public int insertSelective(MaterielInfoDTO entity);

	public int updateSelectiveById(MaterielInfoDTO entity);

	public List<MaterielInfoDTO> selectByKeys(List<String> keys);

	public MaterielInfoDTO getOneById(long id);

	public MaterielInfoDTO getOneByCondition(MaterielInfoDTO entity);

	public List<MaterielInfoDTO> getListByCondition(MaterielInfoDTO entity);

	public List<MaterielInfoDTO> getPageByCondition(MaterielInfoDTO entity,
			int start, int rows);

	public long getPagerCount(MaterielInfoDTO entity);

	public MaterielInfoDTO getOneByDeliveryToCode(String deliveryToCode);

	public List<MaterielInfoDTO> getVehicleInfo(Integer status);

	public void updateMaterielInfo(Double volume, String materielCode);
	
	public void deleteMateriel();

}