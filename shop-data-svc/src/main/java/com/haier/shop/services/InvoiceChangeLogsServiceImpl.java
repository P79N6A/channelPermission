package com.haier.shop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopread.InvoiceChangeLogsReadDao;
import com.haier.shop.dao.shopwrite.InvoiceChangeLogsWriteDao;
import com.haier.shop.model.InvoiceChangeLogs;
import com.haier.shop.service.InvoiceChangeLogsService;

@Service
public class InvoiceChangeLogsServiceImpl implements InvoiceChangeLogsService {

    @Autowired
    InvoiceChangeLogsReadDao invoiceChangeLogsReadDao;
    @Autowired
    InvoiceChangeLogsWriteDao invoiceChangeLogsWriteDao;

    @Override
    public InvoiceChangeLogs get(Integer id) {
        // TODO Auto-generated method stub
        return invoiceChangeLogsReadDao.get(id);
    }

    @Override
    public int insert(InvoiceChangeLogs invoiceChangeLogs) {
        // TODO Auto-generated method stub
        return invoiceChangeLogsWriteDao.insert(invoiceChangeLogs);
    }

    @Override
    public int update(InvoiceChangeLogs invoiceChangeLogs) {
        // TODO Auto-generated method stub
        return invoiceChangeLogsWriteDao.update(invoiceChangeLogs);
    }

}