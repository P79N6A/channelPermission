package com.haier.shop.service;

import java.util.List;

import com.haier.shop.model.OrderYBCards;


public interface OrderYBCardsService {

    void insert(OrderYBCards orderYBCards);

    int update(OrderYBCards orderYBCards);

    List<OrderYBCards> queryByStatus(Integer status);
}
