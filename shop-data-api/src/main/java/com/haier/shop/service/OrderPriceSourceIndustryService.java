package com.haier.shop.service;

import com.haier.shop.model.OrderPriceSourceIndustry;

import java.util.List;
import java.util.Map;

/**
 * Created by 李超 on 2018/1/10.
 */
public interface OrderPriceSourceIndustryService {
    List<OrderPriceSourceIndustry> getSourceIndustryList();

    /**
     * 产业下拉框
     * @return
     */
    List<Map<String, String>> getOrderPriceIndustryBySource(String source);

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

    /**
     * 创建订单价格管控来源产业配置列表
     * @return
     */
    int createOrderPriceSourceIndustry(OrderPriceSourceIndustry orderPriceSourceIndustry);

    /**
     * 更新订单价格管控来源产业配置列表
     * @return
     */
    int updateOrderPriceSourceIndustry(OrderPriceSourceIndustry orderPriceSourceIndustry);

    /**
     * 根据id删除订单价格管控来源产业配置列表
     * @param id
     * @return
     */
    int deleteOrderPriceSourceIndustryById(Integer id);
}
