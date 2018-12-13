package com.haier.shop.dao.shopread;


import com.haier.shop.model.LesQueues;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface LesQueuesReadDao {

    LesQueues get(Integer id);

    List<LesQueues> getSendQueues(@Param("topX") Integer topX);

    List<LesQueues> getCreateInvoiceQueues(@Param("topX") Integer topX);

    int getCountByOpId(@Param("orderProductId") Integer orderProductId);

    List<Map<String, Object>> checkOrderLessSuccess(@Param("orderProductId") Integer orderProductId);

    LesQueues getLesQueueByOpId(@Param("orderProductId") Integer orderProductId);
}
