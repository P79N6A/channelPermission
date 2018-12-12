package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.OrderShippedQueue;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/*
* 作者:张波
* 2017/12/20
* */
@Mapper
public interface OrderShippedQueueWriteDao {
    /**
     * 新增出库队列
     * @param queue
     * @return
     */
    Integer insert(OrderShippedQueue queue);
    /**
     * 根据主键，删除出库队列
     * @param id
     * @return
     */
    Integer delete(Integer id);
}
