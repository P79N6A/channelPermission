package com.haier.stock.dao.stock;

import com.haier.stock.model.InvStockChange;
import org.apache.ibatis.annotations.Param;


public interface InvStockChangeDao {

    Integer insert(InvStockChange stockChange);

    Integer update(InvStockChange stockChange);

    InvStockChange get(@Param("sku") String sku, @Param("secCode") String secCode);

}
