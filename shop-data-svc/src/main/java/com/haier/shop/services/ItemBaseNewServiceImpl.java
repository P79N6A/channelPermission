package com.haier.shop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopread.ItemBaseReadDao;
import com.haier.shop.service.ItemBaseNewService;

@Service
public class ItemBaseNewServiceImpl implements ItemBaseNewService {
    @Autowired
    ItemBaseReadDao itemBaseReadDao;

    @Override
    public String getByMaterialCode(String materialCode) {
        return itemBaseReadDao.getByMaterialCode(materialCode);
    }
}