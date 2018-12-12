package com.haier.shop.service;



import com.haier.shop.model.OrderTmallReturnLogs;

import java.util.List;

/**
 * 	吴坤洋～ 2017-11-23
 * 天猫回传退货信息
 * @author wukunyang
 *
 */
public interface ShopOrderTmallReturnLogsService {

    int insert(OrderTmallReturnLogs record);


    List<OrderTmallReturnLogs> selectByPrimaryKey();


}