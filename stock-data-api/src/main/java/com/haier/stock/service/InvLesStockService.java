package com.haier.stock.service;


import com.haier.stock.model.InvLesStock;

public interface InvLesStockService {

    /**
     * 根据逻辑主键，获取库存对象
     * @param secCode 库位编码
     * @param sku 物料编码
     * @return
     */
    InvLesStock getBySecCodeAndSku(String secCode, String sku);

    Integer insert(InvLesStock stock);

    Integer update(InvLesStock stock);
}
