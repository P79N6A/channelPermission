package com.haier.stock.dao.stock;

import java.util.List;

import com.haier.stock.model.SgFlagShipStoreAuthorization;
import org.apache.ibatis.annotations.Param;



public interface SgFlagShipStoreAuthorizationDao {

    List<SgFlagShipStoreAuthorization> queryByCondition(@Param("storeId") Integer storeId,
                                                        @Param("brandId") Integer brandId,
                                                        @Param("department") String department);

    List<String> getStoreCodeByStreet(@Param("streetId") Integer street,
                                      @Param("brandId") Integer brandId,
                                      @Param("department") String department,
                                      @Param("storeStatus") Integer storeStatus,
                                      @Param("storeType") Integer storeType);

}
