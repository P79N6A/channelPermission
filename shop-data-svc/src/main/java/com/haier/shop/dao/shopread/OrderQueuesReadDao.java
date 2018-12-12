package com.haier.shop.dao.shopread;

import com.haier.shop.model.OrderQueues;

import java.util.List;

public interface OrderQueuesReadDao {
    List<OrderQueues> getByOrderProductId(Integer orderProductId);
}
