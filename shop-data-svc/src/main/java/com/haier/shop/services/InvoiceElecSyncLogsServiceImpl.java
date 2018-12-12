package com.haier.shop.services;

import com.haier.shop.dao.shopread.InvoiceElectricSyncLogsReadDao;
import com.haier.shop.dao.shopwrite.InvoiceElectricSyncLogsWriteDao;
import com.haier.shop.model.InvoiceElectricSyncLogs;
import com.haier.shop.service.ShopInvoiceElecSyncLogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lichunsheng
 * @create 2018-01-03
 **/
@Service
public class InvoiceElecSyncLogsServiceImpl implements ShopInvoiceElecSyncLogsService {

    @Autowired
    InvoiceElectricSyncLogsReadDao invoiceElectricSyncLogsReadDao;

    @Autowired
    InvoiceElectricSyncLogsWriteDao invoiceElectricSyncLogsWriteDao;

    @Override
    public List<InvoiceElectricSyncLogs> getByCOrderSnAngStatusType(String cOrderSn, Integer statusType) {
        return invoiceElectricSyncLogsReadDao.getByCOrderSnAngStatusType(cOrderSn, statusType);
    }

    @Override
    public int insert(InvoiceElectricSyncLogs log) {
        return invoiceElectricSyncLogsWriteDao.insert(log);
    }

}
