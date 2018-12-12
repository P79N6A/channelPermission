package com.haier.shop.service;

import com.haier.shop.model.BasChangeStockRegion;

import java.util.List;

public interface BasChangeStockRegionService {
    
    Integer insert(List<BasChangeStockRegion> insertList);

    Integer update(BasChangeStockRegion changeStock);

    Integer insert2( List<BasChangeStockRegion> insertList);

    Integer update2(BasChangeStockRegion changeStock);
}