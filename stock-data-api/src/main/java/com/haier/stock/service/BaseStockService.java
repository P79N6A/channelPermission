package com.haier.stock.service;


import com.haier.stock.model.BaseStock;

public interface BaseStockService {
    BaseStock get(String sku, String code);

    BaseStock getByItemProperty( String sku,  String code,
                                 String itemProperty);
}
