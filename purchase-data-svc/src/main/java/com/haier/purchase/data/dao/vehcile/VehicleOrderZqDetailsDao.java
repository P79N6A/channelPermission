package com.haier.purchase.data.dao.vehcile;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haier.purchase.data.model.vehcile.VehicleOrderZqDetailsDTO;

/**
 * <p>
 * Description: []
 * </p>
 * Created on 2017年09月15日}
 *
 * @author <a href="mailto: Zhangzengbao32@camelotchina.com">zzb</a>
 * @version 1.0 Copyright (c) 2016 北京柯莱特科技有限公司 交付部
 */
public interface VehicleOrderZqDetailsDao {
	List<VehicleOrderZqDetailsDTO> listByCondition(
			@Param("entity") VehicleOrderZqDetailsDTO entity);

	int updateSelectiveByZqItemNo(
			@Param("entity") VehicleOrderZqDetailsDTO entity);

	int updateStatusDetail(@Param("orderNo")String orderNo);

	int updateMessageDetail(@Param("orderNo")String orderNo, @Param("mesageg")String mesageg);

	int insertSelective(VehicleOrderZqDetailsDTO entity);

	int updateSelectiveById(@Param("entity")VehicleOrderZqDetailsDTO entity);

	VehicleOrderZqDetailsDTO getOneById(long id);

	VehicleOrderZqDetailsDTO getOneByCondition(@Param("entity")VehicleOrderZqDetailsDTO entity);

	List<VehicleOrderZqDetailsDTO> getListByCondition(
			VehicleOrderZqDetailsDTO entity);

	List<VehicleOrderZqDetailsDTO> getPageByCondition(
			@Param("entity")VehicleOrderZqDetailsDTO entity, @Param("start") int start,
			@Param("rows") int rows);

	long getPagerCount(@Param("entity")VehicleOrderZqDetailsDTO entity);

	VehicleOrderZqDetailsDTO getOneByDeliveryToCode(
			@Param("deliveryToCode") String deliveryToCode);

	List<VehicleOrderZqDetailsDTO> selectByStatus();
}
