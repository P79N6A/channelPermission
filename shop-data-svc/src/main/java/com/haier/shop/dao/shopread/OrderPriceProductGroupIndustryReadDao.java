package com.haier.shop.dao.shopread;

import com.haier.shop.model.OrderPriceProductGroupIndustry;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderPriceProductGroupIndustryReadDao {

    List<OrderPriceProductGroupIndustry> getProductGroupIndustryList();

    /**
     * 获取产业下拉框
     * @return
     */
    List<Map<String, String>> getOrderPriceIndustry();

    /**
     * 产品组下拉框
     * @return
     */
    List<Map<String, String>> getOrderPriceProductGroup(@Param("industry") String industry);

    /**
     * 查询订单价格管控产品组产业配置列表
     * @param params
     * @return
     */
    List<OrderPriceProductGroupIndustry> getOrderPriceProductGroupIndustryList(Map<String, Object> params);

    /**
     * 查询订单价格管控产品组产业配置列表总条数
     * @param params
     * @return
     */
    int getOrderPriceProductGroupIndustryListCount(Map<String, Object> params);
}