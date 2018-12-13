package com.haier.shop.service;

import com.haier.shop.model.OrderPriceProductGroupIndustry;

import java.util.List;
import java.util.Map;

/**
 * Created by 李超 on 2018/1/10.
 */
public interface OrderPriceProductGroupIndustryService {
    List<OrderPriceProductGroupIndustry> getProductGroupIndustryList();

    /**
     * 查询订单价格管控产品组产业配置列表总条数
     * @param params
     * @return
     */
    int getOrderPriceProductGroupIndustryListCount(Map<String, Object> params);

    /**
     * 查询订单价格管控产品组产业配置列表
     * @param params
     * @return
     */
    List<OrderPriceProductGroupIndustry> getOrderPriceProductGroupIndustryList(Map<String, Object> params);

    /**
     * 获取产业下拉框
     * @return
     */
    List<Map<String, String>> getOrderPriceIndustry();

    /**
     * 产品组下拉框
     * @return
     */
    List<Map<String, String>> getOrderPriceProductGroup(String industry);

    /**
     * 创建订单价格管控产品组产业配置列表
     * @return
     */
    int createOrderPriceProductGroupIndustry(OrderPriceProductGroupIndustry orderPriceProductGroupIndustry);

    /**
     * 更新订单价格管控产品组产业配置列表
     * @return
     */
    int updateOrderPriceProductGroupIndustry(OrderPriceProductGroupIndustry orderPriceProductGroupIndustry);

    /**
     * 根据id删除订单价格管控产品组产业配置列表
     * @param id
     * @return
     */
    int deleteOrderPriceProductGroupIndustryById(Integer id);
}
