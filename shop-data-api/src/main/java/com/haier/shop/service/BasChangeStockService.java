package com.haier.shop.service;

import com.haier.shop.model.BasChangeStock;

import java.util.List;


public interface BasChangeStockService {

    Integer insert(List<BasChangeStock> insertList);

    Integer update(BasChangeStock changeStock);

    Integer insert2( List<BasChangeStock> insertList);

    Integer update2(BasChangeStock changeStock);

    Integer deleteB2CInfoBySku(String sku);

}
