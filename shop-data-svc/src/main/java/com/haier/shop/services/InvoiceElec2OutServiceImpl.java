package com.haier.shop.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopread.InvoiceElectric2OutReadDao;
import com.haier.shop.dao.shopwrite.InvoiceElectric2OutWriteDao;
import com.haier.shop.model.InvoiceElectric2Out;
import com.haier.shop.service.ShopInvoiceElectric2OutService;

/**
 * @author lichunsheng
 * @create 2018-01-03
 **/
@Service
public class InvoiceElec2OutServiceImpl implements ShopInvoiceElectric2OutService {

    @Autowired
    InvoiceElectric2OutReadDao invoiceElectric2OutReadDao;
    @Autowired
    InvoiceElectric2OutWriteDao invoiceElectric2OutWriteDao;

    @Override
    public List<InvoiceElectric2Out> getByInvoiceIdAndSendType(InvoiceElectric2Out invoiceElectric2Out) {
        return invoiceElectric2OutReadDao.getByInvoiceIdAndSendType(invoiceElectric2Out);
    }

    @Override
    public Integer insert(InvoiceElectric2Out invoiceElectric2Out) {
        return invoiceElectric2OutWriteDao.insert(invoiceElectric2Out);
    }
}
