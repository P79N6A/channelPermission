package com.haier.stock.service;

import com.haier.stock.model.SgFlagShipStoreAuthorization;

import java.util.List;



public interface SgFlagShipStoreAuthorizationService {

    List<SgFlagShipStoreAuthorization> queryByCondition(Integer storeId,
                                                        Integer brandId,
                                                        String department);

    List<String> getStoreCodeByStreet(  Integer street,
                                       Integer brandId,
                                        String department,
                                       Integer storeStatus,
                                        Integer storeType);

}
