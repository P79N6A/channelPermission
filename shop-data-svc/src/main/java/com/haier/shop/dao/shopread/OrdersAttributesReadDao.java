package com.haier.shop.dao.shopread;

import com.haier.shop.model.OrdersAttributes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/*
* 作者:张波
* 2017/12/19
* */
@Mapper
public interface OrdersAttributesReadDao {
    OrdersAttributes getByOrderId(@Param("orderId") Integer orderId);
}
