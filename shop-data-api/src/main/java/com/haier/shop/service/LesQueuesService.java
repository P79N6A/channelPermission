package com.haier.shop.service;


import com.haier.shop.model.LesQueues;


import java.util.List;
import java.util.Map;

public interface LesQueuesService {
    LesQueues get(Integer id);

    List<LesQueues> getSendQueues( Integer topX);

    int updateAfterSyncLes(LesQueues lesQueue);

    List<LesQueues> getCreateInvoiceQueues(Integer topX);

    int insert(List<LesQueues> lesQueuesList);

    int getCountByOpId(Integer orderProductId);

    LesQueues getLesQueueByOpId(Integer orderProductId);

    public List<Map<String, Object>> checkOrderLessSuccess(Integer orderProductId);

    int updateByOpId(LesQueues lesQueue);

}
