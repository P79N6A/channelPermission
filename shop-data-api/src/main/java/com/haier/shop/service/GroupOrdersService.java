package com.haier.shop.service;

import java.util.List;

import com.haier.shop.model.GroupOrders;



public interface GroupOrdersService {
    GroupOrders getByDepositOrderId(Integer depositOrderId);

    Object insert(GroupOrders groupOrders);

    Object updateStatus(GroupOrders groupOrders);

    List<GroupOrders> getGroupOrdersQueues(Integer topX);

    Object updateLesStatus(GroupOrders groupOrders);

    public GroupOrders getByTailOrderId(Integer tailOrderId);

    List<GroupOrders> getListByDepositOrderProductId(Integer depositOrderProductId);

    Object update(GroupOrders groupOrders);

    GroupOrders getByDepositOrderProductId(Integer depositOrderProductId);

}
