package com.haier.eis.service;


import com.haier.eis.model.OrdShippingStatusQueue;

/*
* 作者:张波
* 2017/12/19
* */
public interface EisShippingStatusQueueService {
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
