package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.OrdersAttributes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/*
* 作者:张波
* 2017/12/19
* */
@Mapper
public interface OrdersAttributesWriteDao {
    int update(OrdersAttributes ordersAttributes);
}
