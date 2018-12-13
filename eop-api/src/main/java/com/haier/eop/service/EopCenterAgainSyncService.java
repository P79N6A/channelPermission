package com.haier.eop.service;

import com.haier.eop.data.model.OrdersQueue;

public interface EopCenterAgainSyncService {

    OrdersQueue selectBysourceOrderSnAndsource(String sourceOrderSn , String source);

    boolean insert(OrdersQueue record);

    boolean update(OrdersQueue record);
}
