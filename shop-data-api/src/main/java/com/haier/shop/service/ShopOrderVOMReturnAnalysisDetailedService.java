package com.haier.shop.service;


import com.haier.shop.model.OrderVOMReturnAnalysisDetailed;

/**
 * 接收VOM推送到CBS数据之后解析出来的明细 
 * @author wukunyang
 *
 */
public interface ShopOrderVOMReturnAnalysisDetailedService {
    int insert(OrderVOMReturnAnalysisDetailed record);
}