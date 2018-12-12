package com.haier.purchase.data.dao.vehcile;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haier.purchase.data.model.vehcile.DepartmentInfoDTO;

public interface DepartmentInfoDao {

	int insertSelective(DepartmentInfoDTO entity);

	int updateSelectiveById(@Param("entity") DepartmentInfoDTO entity);

	DepartmentInfoDTO getOneById(long id);

	DepartmentInfoDTO getOneByCondition(
			@Param("entity") DepartmentInfoDTO entity);

	List<DepartmentInfoDTO> getListByCondition(
			@Param("entity") DepartmentInfoDTO entity);

	List<DepartmentInfoDTO> getPageByCondition(
			@Param("entity") DepartmentInfoDTO entity,
			@Param("start") int start, @Param("rows") int rows);

	long getPagerCount(@Param("entity") DepartmentInfoDTO entity);

	DepartmentInfoDTO getOneByDeliveryToCode(
			@Param("deliveryToCode") String deliveryToCode);

}