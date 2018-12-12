package com.haier.shop.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopread.InvoiceChangeLogsReadDao;
import com.haier.shop.dao.shopwrite.InvoiceChangeLogsWriteDao;
import com.haier.shop.model.InvoiceChangeLogs;
import com.haier.shop.service.ShopInvoiceChangeLogService;

/**
 * 发票业务专用
 **/
@Service
public class ShopInvoiceChangeLogServiceImpl implements ShopInvoiceChangeLogService {

    @Autowired
    private InvoiceChangeLogsReadDao invoiceChangeLogsReadDao;
    @Autowired
    private InvoiceChangeLogsWriteDao invoiceChangeLogsWriteDao;

    @Override
    public List<InvoiceChangeLogs> getInvoiceChangeLogs(Integer invoiceId) {
        return invoiceChangeLogsReadDao.getInvoiceChangeLogs(invoiceId);
    }

    @Override
    public int insert(InvoiceChangeLogs invoiceChangeLogs) {
        return invoiceChangeLogsWriteDao.insert(invoiceChangeLogs);
    }
}
