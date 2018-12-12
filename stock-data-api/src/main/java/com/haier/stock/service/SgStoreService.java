package com.haier.stock.service;

import com.haier.stock.model.SgStore;

import java.util.List;




public interface SgStoreService {

    SgStore getSgStore(  String storeCode,  Integer storeType,
                        Integer storeState);

    List<SgStore> getSgStoreList(Integer regionId,
                                 Integer storeType,
                                 Integer storeState,
                                 String department);

    SgStore getSgStoreById(  Integer storeId);

}