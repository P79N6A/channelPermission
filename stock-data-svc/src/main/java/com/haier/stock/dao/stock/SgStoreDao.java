package com.haier.stock.dao.stock;

import java.util.List;

import com.haier.stock.model.SgStore;
import org.apache.ibatis.annotations.Param;



public interface SgStoreDao {

    SgStore getSgStore(@Param("storeCode") String storeCode, @Param("storeType") Integer storeType,
                       @Param("storeState") Integer storeState);

    List<SgStore> getSgStoreList(@Param("regionId") Integer regionId,
                                 @Param("storeType") Integer storeType,
                                 @Param("storeState") Integer storeState,
                                 @Param("department") String department);

    SgStore getSgStoreById(@Param("storeId") Integer storeId);

}