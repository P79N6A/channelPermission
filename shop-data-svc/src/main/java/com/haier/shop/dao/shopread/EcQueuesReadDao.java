package com.haier.shop.dao.shopread;

import com.haier.shop.model.EcQueues;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EcQueuesReadDao {

    EcQueues get(Integer id);

    EcQueues getByOrderProductId(Integer orderProductId);

    List<EcQueues> getUnSendOrders(@Param("unSendQueryNum") Integer unSendQueryNum,
                                   @Param("sendNum") Integer sendNum);

}