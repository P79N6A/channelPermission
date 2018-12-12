package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.HPQueues;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface HPQueuesWriteDao {

    void insert(List<HPQueues> hpQueuesList);

    void update(HPQueues hpQueues);

    int updateHPAllotNetPoint(HPQueues hpQueues);
}
