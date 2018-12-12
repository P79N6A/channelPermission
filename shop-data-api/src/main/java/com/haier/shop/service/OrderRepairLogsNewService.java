package com.haier.shop.service;

import com.haier.shop.model.OrderRepairLogsNew;

/*
* 作者:张波
* 2017/12/20
* */
public interface OrderRepairLogsNewService {
    /**
     * 创建退货单日志
     * @param orderRepairLogs
     * @return 影响行数
     */
    Integer insert(OrderRepairLogsNew orderRepairLogs);

    OrderRepairLogsNew get(Integer id);
}
