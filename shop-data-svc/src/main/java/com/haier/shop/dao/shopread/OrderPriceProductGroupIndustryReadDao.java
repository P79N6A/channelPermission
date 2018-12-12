package com.haier.shop.dao.shopread;

import com.haier.shop.model.OrderPriceProductGroupIndustry;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderPriceProductGroupIndustryReadDao {

    List<OrderPriceProductGroupIndustry> getProductGroupIndustryList();

}