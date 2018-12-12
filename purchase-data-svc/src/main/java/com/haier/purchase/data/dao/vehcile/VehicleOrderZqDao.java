package com.haier.purchase.data.dao.vehcile;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haier.purchase.data.model.vehcile.VehicleOrderZqDTO;

/**
 * <p>
 * Description: []
 * </p>
 * Created on 2017年09月15日}
 *
 * @author <a href="mailto: Zhangzengbao32@camelotchina.com">zzb</a>
 * @version 1.0 Copyright (c) 2016 北京柯莱特科技有限公司 交付部
 */
public interface VehicleOrderZqDao {
	List<VehicleOrderZqDTO> listByCondition(
			@Param("entity") VehicleOrderZqDTO entity);

	int updateStatus(@Param("orderNo")String orderNo);

	int insertSelective(VehicleOrderZqDTO entity);

	int updateSelectiveById(@Param("entity")VehicleOrderZqDTO entity);

	VehicleOrderZqDTO getOneById(long id);

	VehicleOrderZqDTO getOneByCondition(@Param("entity")VehicleOrderZqDTO entity);

	List<VehicleOrderZqDTO> getListByCondition(@Param("entity")VehicleOrderZqDTO entity);

	List<VehicleOrderZqDTO> getPageByCondition(@Param("entity")VehicleOrderZqDTO entity,
			@Param("start") int start, @Param("rows") int rows);

	long getPagerCount(@Param("entity")VehicleOrderZqDTO entity);

	VehicleOrderZqDTO getOneByDeliveryToCode(
			@Param("deliveryToCode") String deliveryToCode);
}
