package com.haier.shop.dao.shopread;

import com.haier.shop.model.ShippingStatusSyncLogs;
import org.apache.ibatis.annotations.Mapper;

/*
* 作者:张波
* 2018/1/3
* */
@Mapper
public interface ShippingStatusSyncLogsReadDao {
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

}
