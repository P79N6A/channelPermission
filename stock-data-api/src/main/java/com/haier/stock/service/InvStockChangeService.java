package com.haier.stock.service;


import com.haier.stock.model.InvStockChange;

public interface InvStockChangeService {

    Integer insert(InvStockChange stockChange);

    Integer update(InvStockChange stockChange);

    InvStockChange get(String sku, String secCode);

}
