package com.haier.eop.data.service;

import java.util.List;

import com.haier.eop.data.model.Stocksyncproducts;

public interface StocksyncproductsService {
    int deleteByPrimaryKey(Integer id);
    int insert(Stocksyncproducts record);
    int updateByPrimaryKey(Stocksyncproducts record);
    List<Stocksyncproducts> Listf(Stocksyncproducts entity, int start, int rows);
    int getPagerCountS(Stocksyncproducts entity);
    Stocksyncproducts getId(String sku,String source);
}