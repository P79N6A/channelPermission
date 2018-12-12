package com.haier.shop.dao.shopread;

import com.haier.shop.model.OrderPriceSourceIndustry;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderPriceSourceIndustryReadDao {

    List<OrderPriceSourceIndustry> getSourceIndustryList();

}