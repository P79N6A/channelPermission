package com.haier.shop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopread.Order2thsReadDao;
import com.haier.shop.dao.shopwrite.Order2thsWriteDao;
import com.haier.shop.model.Order2ths;
import com.haier.shop.service.Order2thsService;

@Service
public class Order2thsServiceImpl implements Order2thsService {
    @Autowired
    Order2thsReadDao order2thsReadDao;
    @Autowired
    Order2thsWriteDao order2thsWriteDao;

    @Override
    public Order2ths get(Integer id) {
        // TODO Auto-generated method stub
        return order2thsReadDao.get(id);
    }

    @Override
    public int update(Order2ths order2ths) {
        // TODO Auto-generated method stub
        return order2thsWriteDao.update(order2ths);
    }

    @Override
    public int updateForsynInvoices(Order2ths order2ths) {
        // TODO Auto-generated method stub
        return order2thsWriteDao.updateForsynInvoices(order2ths);
    }
}
