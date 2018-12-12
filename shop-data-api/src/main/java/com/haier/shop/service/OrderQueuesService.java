package com.haier.shop.service;

import com.haier.shop.model.OrderQueues;

import java.util.List;

public interface OrderQueuesService {
    int insert(OrderQueues orderQueues);

    int update(OrderQueues orderQueues);
    List<OrderQueues> getByOrderProductId(Integer orderProductId);
}
