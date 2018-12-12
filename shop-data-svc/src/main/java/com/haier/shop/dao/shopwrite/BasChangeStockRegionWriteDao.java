package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.BasChangeStockRegion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BasChangeStockRegionWriteDao {
    
    Integer insert(@Param("changeList") List<BasChangeStockRegion> insertList);

    Integer update(BasChangeStockRegion changeStock);

    Integer insert2(@Param("changeList") List<BasChangeStockRegion> insertList);

    Integer update2(BasChangeStockRegion changeStock);
}