package com.haier.distribute.data.service;


import java.util.List;
import java.util.Map;

import com.haier.distribute.data.model.TWarehouse;

public interface TWarehouseService {
    int deleteByPrimaryKey(Integer id);

    int insert(TWarehouse record);

    int insertSelective(TWarehouse record);

    TWarehouse selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TWarehouse record);

    int updateByPrimaryKey(TWarehouse record);

    List<TWarehouse> getAll();

    List<TWarehouse> getWareHouseServiceStart();

    List<Map<String, Object>> autoLoadPid(Integer channelId, Integer id);

    List<TWarehouse> getPageByCondition(TWarehouse entity, int start, int rows);

    long getPagerCount(TWarehouse entity);

    List<TWarehouse> checkCode(TWarehouse entity);
}