package com.haier.eop.data.dao;

import com.haier.eop.data.model.OrdersQueue;
import org.apache.ibatis.annotations.Param;

public interface OrdersQueueDao {

    OrdersQueue  selectBysourceOrderSnAndsource(@Param("entity")OrdersQueue entity);

    int insert(OrdersQueue record);

    int updateByPrimaryKey(OrdersQueue record);
}
