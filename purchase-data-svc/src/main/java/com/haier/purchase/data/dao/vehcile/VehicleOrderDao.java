package com.haier.purchase.data.dao.vehcile;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.haier.purchase.data.model.vehcile.ExportVehicleDTO;
import com.haier.purchase.data.model.vehcile.VehicleOrderDTO;

/**
 * <p>
 * Description:
 * </p>
 * ClassName:VehicleOrderDao Created on 2017/9/6
 *
 * @author wsh
 * @version 1.0 Copyright (c) 2015 北京柯莱特科技有限公司 交付部
 */
public interface VehicleOrderDao {

	String getVehicleOrderNo(@Param("begin") String begin);

	int updateSelectiveByOrderNo(@Param("entity") VehicleOrderDTO entity);

	int insertSelective(VehicleOrderDTO entity);

	int updateSelectiveById(@Param("entity") VehicleOrderDTO entity);

	VehicleOrderDTO getOneById(long id);

	VehicleOrderDTO getOneByCondition(@Param("entity") VehicleOrderDTO entity);

	List<VehicleOrderDTO> getListByCondition(
			@Param("entity") VehicleOrderDTO entity);

	List<VehicleOrderDTO> getPageByCondition(
			@Param("entity") VehicleOrderDTO entity, @Param("start") int start,
			@Param("rows") int rows);

	long getPagerCount(@Param("entity") VehicleOrderDTO entity);

	VehicleOrderDTO getOneByDeliveryToCode(
			@Param("deliveryToCode") String deliveryToCode);
	
	List<ExportVehicleDTO> selectVehicleExport(Map<String, Object> params);

	String getWhCode (String code);
}
