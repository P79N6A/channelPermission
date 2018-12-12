package com.haier.shop.service;


import com.haier.shop.model.OrderRepairshpLogs;

/**
 * 接收HP返回原始数据
 * 吴坤洋 2017-12-2
 * @author wukunyang
 *
 */
public interface ShopOrderRepairshpLogsService {

    int insert(OrderRepairshpLogs record);


    OrderRepairshpLogs selectByPrimaryKey(Integer id);

}