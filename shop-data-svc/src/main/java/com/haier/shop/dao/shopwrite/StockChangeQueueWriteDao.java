package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.StockChangeQueue;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StockChangeQueueWriteDao {

    /**
     * 新增库存变化队列
     * @param stockChangeQueue
     * @return 影响条数
     */
    Integer insert(StockChangeQueue stockChangeQueue);

    /**
     * 删除已处理的队列
     * 注意：必须要处理后才能删除
     * @param id 队列id
     * @return 影响条数
     */
    Integer delete(Integer id);
}
