package com.haier.purchase.data.service.vechile;

import java.util.List;

import com.haier.purchase.data.model.vehcile.MaterielInfoDTO;

public interface PurchaseMaterielInfoService extends BasService<MaterielInfoDTO> {
	
	List<MaterielInfoDTO> getVehicleInfo(Integer status);
	
//	void updateMaterielInfo(@Param("MaterielInfoList") List<MaterielInfoDTO> materielInfoList);

	void updateMaterielInfo(Double volume, String materielCode);

	List<MaterielInfoDTO> selectByKeys(List<String> keys);
	
	public void deleteMateriel();
	
	public int batchAdd(List<MaterielInfoDTO> infoList);

	public void truncateMaterielInfo();
	
	public List<MaterielInfoDTO>  getListByParams( MaterielInfoDTO entity);
}