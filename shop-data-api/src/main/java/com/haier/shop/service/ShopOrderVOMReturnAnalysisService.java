package com.haier.shop.service;


import com.haier.shop.model.OrderVOMReturnAnalysis;

/**
 * 接收VOM主动推送过来解析之后的数据。（主表单）
 * @author wukunyang
 *
 */
public interface ShopOrderVOMReturnAnalysisService {
    int insert(OrderVOMReturnAnalysis record);
}