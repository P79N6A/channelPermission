package com.haier.stock.dao.stock;

import java.util.List;

import com.haier.stock.model.InvStoreRegions;
import org.apache.ibatis.annotations.Param;

public interface InvStoreRegionsDao {

    public InvStoreRegions getByStoreCode(@Param("storeCode") String storeCode,
                                          @Param("status") Integer status);

    public List<InvStoreRegions> getByRegionId(@Param("regionId") Integer regionId,
                                               @Param("status") Integer status,
                                               @Param("hpRemark") Integer hpRemark);

    public List<InvStoreRegions> getByCityId(@Param("cityId") Integer cityId,
                                             @Param("status") Integer status);

    public Integer insert(InvStoreRegions storeRegions);

}
