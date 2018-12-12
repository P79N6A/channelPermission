package com.haier.shop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopread.ProductActivitiesReadDao;
import com.haier.shop.model.ProductActivities;
import com.haier.shop.service.ProductActivitiesService;

/**
 * Created by 李超 on 2018/1/9.
 */
@Service
public class ProductActivitiesServiceImpl implements ProductActivitiesService {

    @Autowired
    ProductActivitiesReadDao productActivitiesReadDao;

    @Override
    public ProductActivities get(Integer id) {
        return productActivitiesReadDao.get(id);
    }
}
