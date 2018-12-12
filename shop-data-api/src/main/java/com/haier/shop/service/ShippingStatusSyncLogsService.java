package com.haier.shop.service;

import com.haier.shop.model.ShippingStatusSyncLogs;

/*
* 作者:张波
* 2018/1/3
* */
public interface ShippingStatusSyncLogsService {
    /**
     * 根据网单id，获取对象
     * @param orderProductId
     * @return
     */
    ShippingStatusSyncLogs getByOrderProductId(Integer orderProductId);

    /**
     * 根据订单id，获取对象
     * @param orderId
     * @return
     */
    ShippingStatusSyncLogs getByOrderId(Integer orderId);

    /**
     * 新增对象
     * @param log
     * @return 影响行数
     */
    Integer insert(ShippingStatusSyncLogs log);
}
