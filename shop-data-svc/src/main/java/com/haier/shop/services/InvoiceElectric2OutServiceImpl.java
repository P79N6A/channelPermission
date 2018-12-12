package com.haier.shop.services;

import java.util.List;

import com.haier.shop.dao.shopread.InvoiceElectric2OutReadDao;
import com.haier.shop.dao.shopwrite.InvoiceElectric2OutWriteDao;
import com.haier.shop.model.InvoiceElectric2Out;
import com.haier.shop.service.InvoiceElectric2OutService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


@Service
public class InvoiceElectric2OutServiceImpl implements InvoiceElectric2OutService {
    @Autowired
    InvoiceElectric2OutReadDao invoiceElectric2OutReadDao;
    @Autowired
    InvoiceElectric2OutWriteDao invoiceElectric2OutWriteDao;

    @Override
    public InvoiceElectric2Out get(Integer id) {
        // TODO Auto-generated method stub
        return invoiceElectric2OutReadDao.get(id);
    }

    @Override
    public List<InvoiceElectric2Out> getByInvoiceIdAndSendType(InvoiceElectric2Out invoiceElectric2Out) {
        // TODO Auto-generated method stub
        return invoiceElectric2OutReadDao.getByInvoiceIdAndSendType(invoiceElectric2Out);
    }

    @Override
    public int insert(InvoiceElectric2Out invoiceElectric2Out) {
        // TODO Auto-generated method stub
        return invoiceElectric2OutWriteDao.insert(invoiceElectric2Out);
    }

    @Override
    public List<InvoiceElectric2Out> getSendToHpList(Integer topx) {
        // TODO Auto-generated method stub
        return invoiceElectric2OutReadDao.getSendToHpList(topx);
    }

    @Override
    public List<InvoiceElectric2Out> getSendToSapList(Integer topx) {
        // TODO Auto-generated method stub
        return invoiceElectric2OutReadDao.getSendToSapList(topx);
    }

    @Override
    public Integer updateAfterSync(InvoiceElectric2Out invoiceElectric2Out) {
        // TODO Auto-generated method stub
        return invoiceElectric2OutWriteDao.updateAfterSync(invoiceElectric2Out);
    }

}
