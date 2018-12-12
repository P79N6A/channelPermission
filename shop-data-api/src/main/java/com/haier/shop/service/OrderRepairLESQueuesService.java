package com.haier.shop.service;

import com.haier.shop.model.OrderRepairLESQueues;

public interface OrderRepairLESQueuesService {
    OrderRepairLESQueues getByOrderProductId(  Integer orderProductId);
    Integer updateVomResult(OrderRepairLESQueues queues);
    /**
     * 写LESRecords
     * @param orderRepairLESQueues
     * @return
     */
    Integer insert(OrderRepairLESQueues orderRepairLESQueues);
}
