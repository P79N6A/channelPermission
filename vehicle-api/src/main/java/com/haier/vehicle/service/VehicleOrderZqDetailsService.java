package com.haier.vehicle.service;

import java.util.List;

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
public interface VehicleOrderZqDetailsService {

	public int insertSelective(VehicleOrderZqDetailsDTO entity);

	public int updateSelectiveById(VehicleOrderZqDetailsDTO entity);


	public VehicleOrderZqDetailsDTO getOneById(long id);

	public VehicleOrderZqDetailsDTO getOneByCondition(
			VehicleOrderZqDetailsDTO entity);

	public List<VehicleOrderZqDetailsDTO> getListByCondition(
			VehicleOrderZqDetailsDTO entity);

	public List<VehicleOrderZqDetailsDTO> getPageByCondition(
			VehicleOrderZqDetailsDTO entity, int start, int rows);

	public long getPagerCount(VehicleOrderZqDetailsDTO entity);

	public VehicleOrderZqDetailsDTO getOneByDeliveryToCode(String deliveryToCode);

	public List<VehicleOrderZqDetailsDTO> listByCondition(
			VehicleOrderZqDetailsDTO entity);

	public int updateSelectiveByZqItemNo(VehicleOrderZqDetailsDTO entity);

	public int updateStatusDetail(String orderNo);

	public int updateMessageDetail(String orderNo, String mesageg);

	public List<VehicleOrderZqDetailsDTO> getListByOrderNo(String orderNo);

	public void updateSelectiveByZqOrderNo(
			VehicleOrderZqDetailsDTO zqOrderDetail);

}
