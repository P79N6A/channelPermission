package com.haier.purchase.data.dao.vehcile;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haier.purchase.data.model.vehcile.MaterielInfoDTO;

public interface MaterielInfoDao {

	List<MaterielInfoDTO> getVehicleInfo(@Param("status") Integer status);

	// void updateMaterielInfo(@Param("MaterielInfoList") List<MaterielInfoDTO>
	// materielInfoList);

	void updateMaterielInfo(@Param("volume") Double volume,
			@Param("materielCode") String materielCode);

	int insertSelective(MaterielInfoDTO entity);

	int updateSelectiveById(@Param("entity") MaterielInfoDTO entity);

	List<MaterielInfoDTO> selectByKeys(@Param("keys") List<String> keys);

	MaterielInfoDTO getOneById(long id);

	MaterielInfoDTO getOneByCondition(@Param("entity") MaterielInfoDTO entity);

	List<MaterielInfoDTO> getListByCondition(
			@Param("entity") MaterielInfoDTO entity);

	List<MaterielInfoDTO> getPageByCondition(
			@Param("entity") MaterielInfoDTO entity, @Param("start") int start,
			@Param("rows") int rows);

	long getPagerCount(@Param("entity") MaterielInfoDTO entity);

	MaterielInfoDTO getOneByDeliveryToCode(
			@Param("deliveryToCode") String deliveryToCode);
	void deleteMateriel();
	
	public int batchAdd(@Param("infoList")List<MaterielInfoDTO> infoList);

	public void truncateMaterielInfo();
	
	public List<MaterielInfoDTO>  getListByParams( MaterielInfoDTO entity);
}