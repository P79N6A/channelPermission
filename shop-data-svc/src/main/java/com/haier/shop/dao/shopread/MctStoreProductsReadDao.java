package com.haier.shop.dao.shopread;

import com.haier.shop.model.MctStoreProducts;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MctStoreProductsReadDao {

    /**
     * 由主键查询
     * @param storeProductId
     * @return
     */
    public MctStoreProducts get(Integer storeProductId);

    /**
     * 由店铺id、店铺码、物料编码查询
     * @param storeId
     * @param storeCode
     * @param sku
     * @return
     */
    public MctStoreProducts getByStoreIdStoreCodeSku(@Param("storeId") Integer storeId,
                                                     @Param("storeCode") String storeCode,
                                                     @Param("sku") String sku);

}