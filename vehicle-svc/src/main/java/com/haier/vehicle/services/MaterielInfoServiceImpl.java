package com.haier.vehicle.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.purchase.data.model.vehcile.MaterielInfoDTO;
import com.haier.purchase.data.service.vechile.PurchaseMaterielInfoService;
import com.haier.vehicle.service.MaterielInfoService;

@Service
public class MaterielInfoServiceImpl implements MaterielInfoService {

	@Autowired
	PurchaseMaterielInfoService purchaseMaterielInfoService;
	
	@Override
	public int insertSelective(MaterielInfoDTO entity) {
		return purchaseMaterielInfoService.insertSelective(entity);
	}

	@Override
	public int updateSelectiveById(MaterielInfoDTO entity) {
		return purchaseMaterielInfoService.updateSelectiveById(entity);
	}

	@Override
	public List<MaterielInfoDTO> selectByKeys(List<String> keys) {
		return purchaseMaterielInfoService.selectByKeys(keys);
	}

	@Override
	public MaterielInfoDTO getOneById(long id) {
		return purchaseMaterielInfoService.getOneById(id);
	}

	@Override
	public MaterielInfoDTO getOneByCondition(MaterielInfoDTO entity) {
		return purchaseMaterielInfoService.getOneByCondition(entity);
	}

	@Override
	public List<MaterielInfoDTO> getListByCondition(MaterielInfoDTO entity) {
		return purchaseMaterielInfoService.getListByCondition(entity);
	}

	@Override
	public List<MaterielInfoDTO> getPageByCondition(MaterielInfoDTO entity,
			int start, int rows) {
		return purchaseMaterielInfoService.getPageByCondition(entity, start, rows);
	}

	@Override
	public long getPagerCount(MaterielInfoDTO entity) {
		return purchaseMaterielInfoService.getPagerCount(entity);
	}

	@Override
	public MaterielInfoDTO getOneByDeliveryToCode(String deliveryToCode) {
		return purchaseMaterielInfoService.getOneByDeliveryToCode(deliveryToCode);
	}

	@Override
	public List<MaterielInfoDTO> getVehicleInfo(Integer status) {
		return purchaseMaterielInfoService.getVehicleInfo(status);
	}

	@Override
	public void updateMaterielInfo(Double volume, String materielCode) {
		purchaseMaterielInfoService.updateMaterielInfo(volume, materielCode);		
	}

	@Override
	public void deleteMateriel() {
		purchaseMaterielInfoService.deleteMateriel();		
	}
}