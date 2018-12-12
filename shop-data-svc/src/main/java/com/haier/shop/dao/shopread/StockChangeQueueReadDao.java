package com.haier.shop.dao.shopread;

import com.haier.shop.model.StockChangeQueue;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StockChangeQueueReadDao {
    /**
     * 获取待处理的队列
     * @param topX 获取前X条
     * @return
     */
    List<StockChangeQueue> getListToProcess(@Param("topX") Integer topX);

}
