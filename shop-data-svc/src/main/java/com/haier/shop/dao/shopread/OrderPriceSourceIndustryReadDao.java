package com.haier.shop.dao.shopread;

import com.haier.shop.model.OrderPriceSourceIndustry;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderPriceSourceIndustryReadDao {

    List<OrderPriceSourceIndustry> getSourceIndustryList();

    /**
     * 产业下拉框
     * @return
     */
    List<Map<String, String>> getOrderPriceIndustryBySource(@Param("source") String source);

    /**
     * 查询订单价格管控来源产业配置列表总条数
     * @param params
     * @return
     */
    int getOrderPriceSourceIndustryListCount(Map<String, Object> params);

    /**
     * 查询订单价格管控来源产业配置列表
     * @param params
     * @return
     */
    List<OrderPriceSourceIndustry> getOrderPriceSourceIndustryList(Map<String, Object> params);

}