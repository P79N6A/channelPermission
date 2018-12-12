package com.haier.stock.service;

import com.haier.stock.model.StoreInfo;

public interface StoreInfoService {

    StoreInfo getByOwerId(Integer owerId);

}