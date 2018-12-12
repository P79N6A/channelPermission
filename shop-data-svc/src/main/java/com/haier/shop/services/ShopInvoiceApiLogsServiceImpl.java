package com.haier.shop.services;

import com.haier.shop.dao.shopread.InvoiceApiLogsReadDao;
import com.haier.shop.dao.shopwrite.InvoiceApiLogsWriteDao;
import com.haier.shop.model.InvoiceApiLogDispItem;
import com.haier.shop.model.InvoiceApiLogs;
import com.haier.shop.service.ShopInvoiceApiLogsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lichunsheng
 * @create 2018-01-04
 **/
@Service
public class ShopInvoiceApiLogsServiceImpl implements ShopInvoiceApiLogsService {

    @Autowired
    private InvoiceApiLogsWriteDao invoiceApiLogsWriteDao;
    @Autowired
    private InvoiceApiLogsReadDao invoiceApiLogsReadDao;

    @Override
    public Integer insert(InvoiceApiLogs apilogs) {
        return invoiceApiLogsWriteDao.insert(apilogs);
    }

    @Override
    public Map<String, Object> getInvoiceApiLogsByPage(Map<String, Object> paramMap) {

        List<InvoiceApiLogDispItem> list = invoiceApiLogsReadDao.getInvoiceApiLogs(paramMap);
        //获得条数
        int resultcount = invoiceApiLogsReadDao.getRowCnts();

        Map<String, Object> retMap = new HashMap<String, Object>();
        retMap.put("total", resultcount);
        retMap.put("rows", list);
        return retMap;
    }

	@Override
	public InvoiceApiLogs get(Integer id) {
		return invoiceApiLogsReadDao.get(id);
	}

	@Override
	public InvoiceApiLogs getByInvoiceIdAndType(Integer invoiceId, Integer type) {
		return invoiceApiLogsReadDao.getByInvoiceIdAndType(invoiceId, type);
	}

	@Override
	public int update(InvoiceApiLogs apilogs) {
		return invoiceApiLogsWriteDao.update(apilogs);
	}

	@Override
	public int updateByInvoiceIdAndType(InvoiceApiLogs apilogs) {
		return invoiceApiLogsWriteDao.updateByInvoiceIdAndType(apilogs);
	}
}
