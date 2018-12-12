package com.haier.shop.services;

import java.util.List;

import com.haier.shop.dao.shopread.InvoiceElectricSyncLogsReadDao;
import com.haier.shop.dao.shopwrite.InvoiceElectricSyncLogsWriteDao;
import com.haier.shop.model.InvoiceElectricSyncLogs;
import com.haier.shop.service.InvoiceElectricSyncLogsService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


@Service
public class InvoiceElectricSyncLogsServiceImpl implements InvoiceElectricSyncLogsService {
    @Autowired
    InvoiceElectricSyncLogsReadDao invoiceElectricSyncLogsReadDao;
    @Autowired
    InvoiceElectricSyncLogsWriteDao invoiceElectricSyncLogsWriteDao;

    @Override
    public int insert(InvoiceElectricSyncLogs log) {
        // TODO Auto-generated method stub
        return invoiceElectricSyncLogsWriteDao.insert(log);
    }

    @Override
    public int update(InvoiceElectricSyncLogs log) {
        // TODO Auto-generated method stub
        return invoiceElectricSyncLogsWriteDao.update(log);
    }

    @Override
    public List<InvoiceElectricSyncLogs> getByCOrderSn(String cOrderSn) {
        // TODO Auto-generated method stub
        return invoiceElectricSyncLogsReadDao.getByCOrderSn(cOrderSn);
    }

    @Override
    public List<InvoiceElectricSyncLogs> getByCOrderSnAngStatusType(String cOrderSn, Integer statusType) {
        // TODO Auto-generated method stub
        return invoiceElectricSyncLogsReadDao.getByCOrderSnAngStatusType(cOrderSn, statusType);
    }

}
