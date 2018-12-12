package com.haier.eis.service;

import com.haier.eis.model.OrdShippingStatusQueue;

public interface OrdShippingStatusQueueService {
    /**
     * 根据网单号获取队列信息
     * @param orderProductId
     * @return
     */
    OrdShippingStatusQueue getByOrderProductId(Integer orderProductId);

    /**
     * 新增队列（已处理并发）
     * @param queue
     * @return
     */
    Integer insert(OrdShippingStatusQueue queue);
}
