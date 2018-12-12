package com.haier.shop.services;

import com.haier.shop.dao.shopread.InvoiceElectricLogsReadDao;
import com.haier.shop.dao.shopwrite.InvoiceElectricLogsWriteDao;
import com.haier.shop.model.InvoiceElectricLogDispItem;
import com.haier.shop.model.InvoiceElectricLogs;
import com.haier.shop.service.ShopInvoiceElecLogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lichunsheng
 * @create 2018-01-03
 **/
@Service
public class InvoiceElectLogsServiceImpl implements ShopInvoiceElecLogsService {

    @Autowired
    InvoiceElectricLogsWriteDao invoiceElectricLogsWriteDao;

    @Autowired
    InvoiceElectricLogsReadDao invoiceElectricLogsReadDao;

    @Override
    public Integer updateByInvoiceIdAndType(InvoiceElectricLogs eInvoiceLog) {
        return invoiceElectricLogsWriteDao.updateByInvoiceIdAndType(eInvoiceLog);
    }

    @Override
    public Integer insertLog(InvoiceElectricLogs eInvoiceLog) {
        return invoiceElectricLogsWriteDao.insertLog(eInvoiceLog);
    }

    @Override
    public Map<String, Object> getElectronicInvoiceLogsByPage(Map<String, Object> paramMap) {

        List<InvoiceElectricLogDispItem> list = invoiceElectricLogsReadDao.getElectronicInvoiceLogsList(paramMap);
        //获得条数
        int resultcount = invoiceElectricLogsReadDao.getRowCnts();
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total", resultcount);
        result.put("rows", list);
        return result;
    }

}
