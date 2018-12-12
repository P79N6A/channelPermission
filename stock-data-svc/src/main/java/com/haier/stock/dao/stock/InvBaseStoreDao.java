package com.haier.stock.dao.stock;

import com.haier.stock.model.InvBaseStore;
import org.apache.ibatis.annotations.Param;



public interface InvBaseStoreDao extends BaseStockDao {

    public InvBaseStore getByItemPropertyForUpdate(@Param("sku") String sku,
                                                   @Param("storeCode") String storeCode,
                                                   @Param("itemProperty") String itemProperty);

    /**
     * 通过物料编码、店铺码和批次查询店铺库存
     * @param params
     * @return
     */
    public InvBaseStore getStore(@Param("sku") String sku, @Param("storeCode") String storeCode,
                                 @Param("itemProperty") String itemProperty);

    public Integer insert(InvBaseStore invBaseStore);

    /**
     * 更新店铺库存信息
     * @param store
     * @return
     */
    public Integer update(InvBaseStore store);

    /**
     * 释放店铺库存
     * @param params
     * @return
     */
    public Integer releaseFrozenQty(@Param("id") Integer id,
                                    @Param("releaseQty") Integer releaseQty);

    /**
     * 释放店铺库存,扣减总库存
     * @param params
     * @return
     */
    public Integer releaseStockQtyAndFrozenQty(@Param("id") Integer id,
                                               @Param("releaseQty") Integer releaseQty);

    public Integer updateStockQty(@Param("id") Integer id, @Param("storeTs") Long storeTs,
                                  @Param("qty") Integer newStockQty);

    /**
     * 更新库存占用数量
     * @param baseStore
     * @return
     */
    Integer updateQtyForFrozen(InvBaseStore baseStore);

}
