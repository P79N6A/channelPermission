package com.haier.shop.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haier.shop.dao.shopread.InvoiceElectricLogsReadDao;
import com.haier.shop.dao.shopwrite.InvoiceElectricLogsWriteDao;
import com.haier.shop.model.InvoiceElectricLogs;
import com.haier.shop.service.InvoiceElectricLogsService;


@Service
public class InvoiceElectricLogsServiceImpl implements InvoiceElectricLogsService {
    @Autowired
    InvoiceElectricLogsReadDao invoiceElectricLogsReadDao;
    @Autowired
    InvoiceElectricLogsWriteDao invoiceElectricLogsWriteDao;

    @Override
    public InvoiceElectricLogs getByInvoiceIdAndType(Integer invoiceId, Integer type) {
        // TODO Auto-generated method stub
        return invoiceElectricLogsReadDao.getByInvoiceIdAndType(invoiceId, type);
    }

    @Override
    public List<InvoiceElectricLogs> getByInvoiceIdAndTypeList(Integer invoiceId, Integer type) {
        // TODO Auto-generated method stub
        return invoiceElectricLogsReadDao.getByInvoiceIdAndTypeList(invoiceId, type);
    }

    @Override
    public int insertLog(InvoiceElectricLogs eInvoiceLog) {
        // TODO Auto-generated method stub
        return invoiceElectricLogsWriteDao.insertLog(eInvoiceLog);
    }

    @Override
    public int update(InvoiceElectricLogs eInvoiceLog) {
        // TODO Auto-generated method stub
        return invoiceElectricLogsWriteDao.update(eInvoiceLog);
    }

    @Override
    public int updateByInvoiceIdAndType(InvoiceElectricLogs eInvoiceLog) {
        // TODO Auto-generated method stub
        return invoiceElectricLogsWriteDao.updateByInvoiceIdAndType(eInvoiceLog);
    }
}
