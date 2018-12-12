package com.haier.stock.service;

import com.haier.stock.model.InvStoreRegions;

import java.util.List;

public interface InvStoreRegionsService {

    public InvStoreRegions getByStoreCode(String storeCode,
                                          Integer status);

    public List<InvStoreRegions> getByRegionId(  Integer regionId,
                                                Integer status,
                                                 Integer hpRemark);

    public List<InvStoreRegions> getByCityId( Integer cityId,
                                              Integer status);

    public Integer insert(InvStoreRegions storeRegions);

}
