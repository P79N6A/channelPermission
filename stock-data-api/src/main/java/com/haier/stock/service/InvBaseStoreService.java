package com.haier.stock.service;


import com.haier.stock.model.InvBaseStore;

public interface InvBaseStoreService extends BaseStockService {

    public InvBaseStore getByItemPropertyForUpdate(String sku,
                                                   String storeCode,
                                                   String itemProperty);

    /**
     * 通过物料编码、店铺码和批次查询店铺库存
     * @param params
     * @return
     */
    public InvBaseStore getStore(String sku, String storeCode,
                                  String itemProperty);

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
    public Integer releaseFrozenQty(Integer id,
                                   Integer releaseQty);

    /**
     * 释放店铺库存,扣减总库存
     * @param params
     * @return
     */
    public Integer releaseStockQtyAndFrozenQty(Integer id,
                                               Integer releaseQty);

    public Integer updateStockQty( Integer id, Long storeTs,
                                   Integer newStockQty);

    /**
     * 更新库存占用数量
     * @param baseStore
     * @return
     */
    Integer updateQtyForFrozen(InvBaseStore baseStore);

}
