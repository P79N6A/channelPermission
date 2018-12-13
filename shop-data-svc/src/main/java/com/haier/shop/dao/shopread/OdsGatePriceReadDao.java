package com.haier.shop.dao.shopread;

import com.haier.shop.model.GatePrice;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OdsGatePriceReadDao {
    GatePrice getOdsGatePriceBySku(String sku);
}
