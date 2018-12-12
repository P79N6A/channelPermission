package com.haier.stock.dao.stock;

import org.apache.ibatis.annotations.Param;

import com.haier.stock.model.BaseStock;




public interface BaseStockDao {
    BaseStock get(@Param("sku") String sku, @Param("code") String code);

    BaseStock getByItemProperty(@Param("sku") String sku, @Param("code") String code,
                                @Param("itemProperty") String itemProperty);
}
