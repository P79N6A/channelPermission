package com.haier.shop.service;

import com.haier.shop.model.MctStoreProducts;

public interface MctStoreProductsService {

    /**
     * 由主键查询
     * @param storeProductId
     * @return
     */
    public MctStoreProducts get(Integer storeProductId);

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
     * 由店铺id、店铺码、物料编码查询
     * @param storeId
     * @param storeCode
     * @param sku
     * @return
     */
    public MctStoreProducts getByStoreIdStoreCodeSku( Integer storeId,
                                                      String storeCode,
                                                       String sku);

    /**
     * 更新库存数量
     * @param stockNum
     * @param storeProductId
     * @return
     */
    public int updateStockNumById(  Integer storeProductId,
                                    Integer stockNum);
}