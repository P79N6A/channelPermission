package com.haier.distribute.data.service;


import java.util.List;


import com.haier.distribute.data.model.TWarehouseRegion;

public interface TWarehouseRegionService {
    int deleteByPrimaryKey(Integer id);

    int insert(TWarehouseRegion record);

    int insertSelective(TWarehouseRegion record);

    TWarehouseRegion selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TWarehouseRegion record);

    int updateByPrimaryKey(TWarehouseRegion record);

    int checkRegion(int channelId, int regionId, int id);

    List<TWarehouseRegion> getPageByCondition(TWarehouseRegion entity, int start, int rows);

    long getPagerCount(TWarehouseRegion entity);
}