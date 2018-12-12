package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.OrderRepairHPQueues;
import org.apache.ibatis.annotations.Mapper;

/*
*
* 作者:张波
* 2017/12/20
* */
@Mapper
public interface OrderRepairHPQueuesWriteDao {
    /**
     * 创建HP退货队列
     * @param orderRepairHPQueues
     * @return
     */
    Integer insert(OrderRepairHPQueues orderRepairHPQueues);
}
