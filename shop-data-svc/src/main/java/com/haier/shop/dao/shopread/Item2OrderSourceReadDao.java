package com.haier.shop.dao.shopread;

import com.haier.shop.model.Item2OrderSource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface Item2OrderSourceReadDao {
    /**
     * 根据订单来源查询批次
     * @param orderSource
     * @return
     */
    public Item2OrderSource getByOrderSource(@Param("order_source") String orderSource);
}
