package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.LesQueues;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LesQueuesWriteDao {

    int updateAfterSyncLes(LesQueues lesQueue);

    int insert(List<LesQueues> lesQueuesList);

    int updateByOpId(LesQueues lesQueue);

}
