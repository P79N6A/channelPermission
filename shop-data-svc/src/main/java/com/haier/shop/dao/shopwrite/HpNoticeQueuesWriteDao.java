package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.HpNoticeQueues;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface HpNoticeQueuesWriteDao {

    int update(HpNoticeQueues hpNoticeQueues);
    
    //根据是否成功和网单id更新队列
    int updateQueuesBySuccessAndOrderProId(@Param("orderProductId") Integer orderProductId);
}
