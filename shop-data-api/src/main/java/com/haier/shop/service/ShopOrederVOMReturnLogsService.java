package com.haier.shop.service;


import com.haier.shop.model.OrederVOMReturnLogs;

/**
 * 接收VOM主动推送过来的原始数据
 * @author wukunyang
 *
 */
public interface ShopOrederVOMReturnLogsService {

    int insert(OrederVOMReturnLogs record);
}