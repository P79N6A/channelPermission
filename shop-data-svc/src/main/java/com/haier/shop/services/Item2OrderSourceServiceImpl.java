package com.haier.shop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopread.Item2OrderSourceReadDao;
import com.haier.shop.model.Item2OrderSource;
import com.haier.shop.service.Item2OrderSourceService;

/**
 * Created by 李超 on 2018/1/9.
 */
@Service
public class Item2OrderSourceServiceImpl implements Item2OrderSourceService{

    @Autowired
    Item2OrderSourceReadDao item2OrderSourceReadDao;

    @Override
    public Item2OrderSource getByOrderSource(String orderSource) {
        return item2OrderSourceReadDao.getByOrderSource(orderSource);
    }
}
