package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.EcQueues;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EcQueuesWriteDao {

    int insert(EcQueues ecQueues);

    int update(EcQueues ecQueues);

}