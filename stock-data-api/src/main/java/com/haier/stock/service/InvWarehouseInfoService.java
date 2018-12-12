package com.haier.stock.service;


import com.haier.stock.model.InvWarehouseInfo;

public interface InvWarehouseInfoService {

    InvWarehouseInfo getBySecCode(String secCode);

    int insert(InvWarehouseInfo invWarehouseInfo);

    int update(InvWarehouseInfo invWarehouseInfo);

}