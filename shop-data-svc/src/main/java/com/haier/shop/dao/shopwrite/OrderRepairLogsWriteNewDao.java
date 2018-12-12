package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.OrderRepairLogsNew;

/*
* 作者:张波
* 2017/12/20
* */
// TODO 与OrderRepairLogs模型冲突
public interface OrderRepairLogsWriteNewDao {
    /**
     * 创建退货单日志
     * @param orderRepairLogs
     * @return 影响行数
     */
    Integer insert(OrderRepairLogsNew orderRepairLogs);

    OrderRepairLogsNew get(Integer id);
}
