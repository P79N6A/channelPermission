package com.haier.shop.service;

import java.util.List;

import com.haier.shop.model.EcQueues;




public interface EcQueuesService {
    EcQueues get(Integer id);

    EcQueues getByOrderProductId(Integer orderProductId);

    int insert(EcQueues ecQueues);

    int update(EcQueues ecQueues);

    List<EcQueues> getUnSendOrders(Integer unSendQueryNum,
                                   Integer sendNum);

}