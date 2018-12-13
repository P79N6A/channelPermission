package com.haier.stock.service;

import com.haier.common.ServiceResult;

public interface HopStockSyncService {

    /**
     * 同步库存--同步库存到海尔电商
     * @param sku
     * @param secCode
     * @param source
     * @param qty
     * @return
     */
    public ServiceResult<Boolean> syncStock(String sku, String secCode, Integer qty, String source);

}
