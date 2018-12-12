package com.haier.stock.services;

import java.util.List;

import com.haier.stock.dao.stock.InvGdFactoryDao;
import com.haier.stock.model.InvGdFactory;
import com.haier.stock.service.InvGdFactoryService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


@Service
public class InvGdFactoryServiceImpl implements InvGdFactoryService {
    @Autowired
    InvGdFactoryDao invGdFactoryDao;

    @Override
    public List<InvGdFactory> queryGdFactory() {
        // TODO Auto-generated method stub
        return invGdFactoryDao.queryGdFactory();
    }


}
