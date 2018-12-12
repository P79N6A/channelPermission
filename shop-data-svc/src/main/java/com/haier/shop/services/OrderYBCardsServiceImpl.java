package com.haier.shop.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopread.OrderYBCardsReadDao;
import com.haier.shop.dao.shopwrite.OrderYBCardsWriteDao;
import com.haier.shop.model.OrderYBCards;
import com.haier.shop.service.OrderYBCardsService;

@Service
public class OrderYBCardsServiceImpl implements OrderYBCardsService {

    @Autowired
    OrderYBCardsWriteDao orderYBCardsWriteDao;
    @Autowired
    OrderYBCardsReadDao orderYBCardsReadDao;

    @Override
    public void insert(OrderYBCards orderYBCards) {
        orderYBCardsWriteDao.insert(orderYBCards);
    }

    @Override
    public int update(OrderYBCards orderYBCards) {
        return orderYBCardsWriteDao.update(orderYBCards);
    }

    @Override
    public List<OrderYBCards> queryByStatus(Integer status) {
        return orderYBCardsReadDao.queryByStatus(status);
    }
}
