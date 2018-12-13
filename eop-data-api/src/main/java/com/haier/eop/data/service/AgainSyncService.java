package com.haier.eop.data.service;

import com.haier.eop.data.model.OrdersQueue;

public interface AgainSyncService {

    OrdersQueue selectBysourceOrderSnAndsource(OrdersQueue record);

    int insert(OrdersQueue record);

    int update(OrdersQueue record);

}
