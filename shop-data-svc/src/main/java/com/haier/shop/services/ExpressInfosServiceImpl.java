package com.haier.shop.services;

import com.haier.shop.dao.shopread.ExpressInfosReadDao;
import com.haier.shop.dao.shopwrite.ExpressInfosWriteDao;
import com.haier.shop.model.ExpressInfos;
import com.haier.shop.service.ExpressInfosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpressInfosServiceImpl implements ExpressInfosService {
    @Autowired
    private ExpressInfosWriteDao expressInfosWriteDao;
    @Autowired
    private ExpressInfosReadDao expressInfosReadDao;
    public Integer insert(ExpressInfos expressInfos){
        return expressInfosWriteDao.insert(expressInfos);
    }

    public ExpressInfos findBycOrderSn(String cOrderSn){
        return expressInfosReadDao.findBycOrderSn(cOrderSn);
    }
}
