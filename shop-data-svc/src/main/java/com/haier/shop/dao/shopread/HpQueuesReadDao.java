package com.haier.shop.dao.shopread;

import com.haier.shop.model.HPQueues;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface HpQueuesReadDao {

    int getCountByOpId(@Param("orderProductId") Integer orderProductId);

    HPQueues getByOrderProductId(Integer orderProductId);

}
