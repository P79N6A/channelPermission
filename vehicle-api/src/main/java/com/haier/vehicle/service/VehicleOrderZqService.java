package com.haier.vehicle.service;

import java.util.List;

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
public interface VehicleOrderZqService {

	public int insertSelective(VehicleOrderZqDTO entity);

	public int updateSelectiveById(VehicleOrderZqDTO entity);

	public VehicleOrderZqDTO getOneById(long id);

	public VehicleOrderZqDTO getOneByCondition(VehicleOrderZqDTO entity);

	public List<VehicleOrderZqDTO> getListByCondition(VehicleOrderZqDTO entity);

	public List<VehicleOrderZqDTO> getPageByCondition(VehicleOrderZqDTO entity,
			int start, int rows);

	public long getPagerCount(VehicleOrderZqDTO entity);

	public VehicleOrderZqDTO getOneByDeliveryToCode(String deliveryToCode);

	public List<VehicleOrderZqDTO> listByCondition(VehicleOrderZqDTO entity);

	public int updateStatus(String orderNo);
}
