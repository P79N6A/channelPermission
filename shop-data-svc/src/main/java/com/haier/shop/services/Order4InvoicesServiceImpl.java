package com.haier.shop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopread.Order4InvoicesReadDao;
import com.haier.shop.dao.shopwrite.Order4InvoicesWriteDao;
import com.haier.shop.model.Order4Invoices;
import com.haier.shop.service.Order4InvoicesService;

@Service
public class Order4InvoicesServiceImpl implements Order4InvoicesService {
    @Autowired
    Order4InvoicesWriteDao order4InvoicesWriteDao;
    @Autowired
    Order4InvoicesReadDao order4InvoicesReadDao;

    @Override
    public Order4Invoices get(Integer id) {
        // TODO Auto-generated method stub
        return order4InvoicesReadDao.get(id);
    }

    @Override
    public int update(Order4Invoices order4Invoice) {
        // TODO Auto-generated method stub
        return order4InvoicesWriteDao.update(order4Invoice);
    }

    @Override
    public int updateForsynInvoices(Order4Invoices order4Invoice) {
        // TODO Auto-generated method stub
        return order4InvoicesWriteDao.updateForsynInvoices(order4Invoice);
    }

}
