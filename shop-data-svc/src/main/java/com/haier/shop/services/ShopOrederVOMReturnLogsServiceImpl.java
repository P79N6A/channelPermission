package com.haier.shop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopwrite.OrederVOMReturnLogsWriteDao;
import com.haier.shop.model.OrederVOMReturnLogs;
import com.haier.shop.service.ShopOrederVOMReturnLogsService;

@Service
public class ShopOrederVOMReturnLogsServiceImpl implements ShopOrederVOMReturnLogsService {
    @Autowired
    private OrederVOMReturnLogsWriteDao orederVOMReturnLogsWriteDao;

    public int insert(OrederVOMReturnLogs record){
        return orederVOMReturnLogsWriteDao.insert(record);
    }
}
