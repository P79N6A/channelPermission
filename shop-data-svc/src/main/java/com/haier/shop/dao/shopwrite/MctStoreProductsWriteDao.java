package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.MctStoreProducts;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MctStoreProductsWriteDao {

    /**
     * 新增mctStoreProducts
     * @param mctStoreProducts
     * @return
     */
    public int insert(MctStoreProducts mctStoreProducts);

    /**
     * 更新
     * @param mctStoreProducts
     * @return
     */
    public int update(MctStoreProducts mctStoreProducts);


    /**
     * 更新库存数量
     * @param stockNum
     * @param storeProductId
     * @return
     */
    public int updateStockNumById(@Param("storeProductId") Integer storeProductId,
                                  @Param("stockNum") Integer stockNum);
}