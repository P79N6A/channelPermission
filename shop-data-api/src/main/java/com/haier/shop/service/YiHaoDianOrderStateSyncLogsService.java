package com.haier.shop.service;

import com.haier.shop.model.YiHaoDianOrderStateSyncLogs;

/*
* 作者:张波
* 2018/1/3
* */
public interface YiHaoDianOrderStateSyncLogsService {
    /**
     * 根据订单编号，获取对象
     * @param orderSn 订单编号
     * @return
     */
    YiHaoDianOrderStateSyncLogs getByOrderSn(String orderSn);

    /**
     * 创建对象
     * @param log
     * @return
     */
    Integer insert(YiHaoDianOrderStateSyncLogs log);
}
