package com.haier.shop.service;

import com.haier.shop.model.SgRealtimeStock;

public interface SgRealtimeStockService {
	SgRealtimeStock selectByParams(SgRealtimeStock record);

    int insert(SgRealtimeStock record);

    int updateByParams(SgRealtimeStock record);
    String findStoreCodeByStoreId(Integer id);
}