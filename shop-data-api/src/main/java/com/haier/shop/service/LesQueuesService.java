package com.haier.shop.service;


import com.haier.shop.model.LesQueues;


import java.util.List;

public interface LesQueuesService {
    LesQueues get(Integer id);

    List<LesQueues> getSendQueues( Integer topX);

    int updateAfterSyncLes(LesQueues lesQueue);

    List<LesQueues> getCreateInvoiceQueues(Integer topX);

    int insert(List<LesQueues> lesQueuesList);

    int getCountByOpId(Integer orderProductId);

    int updateByOpId(LesQueues lesQueue);

}
