package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.OrderYBCards;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderYBCardsWriteDao {

    void insert(OrderYBCards orderYBCards);

    int update(OrderYBCards orderYBCards);
}
