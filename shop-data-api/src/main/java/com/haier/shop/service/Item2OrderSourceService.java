package com.haier.shop.service;

import com.haier.shop.model.Item2OrderSource;
import org.apache.ibatis.annotations.Param;

/**
 * Created by 李超 on 2018/1/9.
 */
public interface Item2OrderSourceService {
    /**
     * 根据订单来源查询批次
     * @param orderSource
     * @return
     */
    Item2OrderSource getByOrderSource(@Param("order_source") String orderSource);
}
