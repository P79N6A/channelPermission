package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.OrderQueues;

public interface OrderQueuesWriteDao {
    int insert(OrderQueues orderQueues);

    int update(OrderQueues orderQueues);
}
