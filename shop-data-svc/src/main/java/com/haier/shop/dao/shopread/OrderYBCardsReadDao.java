package com.haier.shop.dao.shopread;

import com.haier.shop.model.OrderYBCards;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderYBCardsReadDao {

    List<OrderYBCards> queryByStatus(Integer status);
}
