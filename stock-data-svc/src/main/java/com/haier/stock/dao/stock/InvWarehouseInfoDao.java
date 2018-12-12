package com.haier.stock.dao.stock;

import com.haier.stock.model.InvWarehouseInfo;

import java.util.List;

import org.apache.ibatis.annotations.Param;


public interface InvWarehouseInfoDao {

    InvWarehouseInfo getBySecCode(@Param("secCode") String secCode);

    int insert(InvWarehouseInfo invWarehouseInfo);

    int update(InvWarehouseInfo invWarehouseInfo);

	

}