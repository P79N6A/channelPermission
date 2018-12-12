package com.haier.purchase.data.dao.vehcile;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.haier.purchase.data.model.vehcile.VehicleProductPaymentDTO;

/**
 * <p>Description: </p>
 * ClassName:VehicleProductPaymentDao
 * Created on 2017/9/8
 *
 * @author wsh
 * @version 1.0
 * Copyright (c) 2015 北京柯莱特科技有限公司 交付部
 */
public interface VehicleProductPaymentDao {
    List<VehicleProductPaymentDTO> getList();

    List<VehicleProductPaymentDTO> listByCondition(@Param("entity") VehicleProductPaymentDTO entity);
    
    int insertSelective(VehicleProductPaymentDTO entity);

    int updateSelectiveById(@Param("entity")VehicleProductPaymentDTO entity);

    List<VehicleProductPaymentDTO> selectByKeys(@Param("keys") List<String> keys);

    VehicleProductPaymentDTO getOneById(long id);

    VehicleProductPaymentDTO getOneByCondition(@Param("entity") VehicleProductPaymentDTO entity);

    List<VehicleProductPaymentDTO> getListByCondition(@Param("entity") VehicleProductPaymentDTO entity);

    List<VehicleProductPaymentDTO> getPageByCondition(@Param("entity") VehicleProductPaymentDTO entity, @Param("start") int start, @Param("rows") int rows);

    long getPagerCount(@Param("entity") VehicleProductPaymentDTO entity);

    VehicleProductPaymentDTO getOneByDeliveryToCode(@Param("deliveryToCode") String deliveryToCode);
}
