package com.haier.shop.service;

import com.haier.shop.model.HPQueues;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by 李超 on 2018/1/10.
 */
public interface HPQueuesService {
    void insert(List<HPQueues> hpQueuesList);

    void update(HPQueues hpQueues);

    int getCountByOpId(@Param("orderProductId") Integer orderProductId);

    HPQueues getByOrderProductId(Integer orderProductId);

    int updateHPAllotNetPoint(HPQueues hpQueues);
}
