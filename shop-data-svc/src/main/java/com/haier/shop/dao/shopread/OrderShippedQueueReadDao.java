package com.haier.shop.dao.shopread;

import com.haier.shop.model.OrderShippedQueue;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/*
* 作者:张波
* 2017/12/20
* */
@Mapper
public interface OrderShippedQueueReadDao {
    /**
     * 获取待处理的出库队列
     * @param topX 前x条
     * @return
     */
    List<OrderShippedQueue> getListToProcess(@Param("topX") Integer topX);
}
