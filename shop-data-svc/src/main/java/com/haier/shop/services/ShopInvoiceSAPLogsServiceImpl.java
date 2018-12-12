package com.haier.shop.services;

import com.haier.shop.dao.shopread.InvoiceSAPLogsReadDao;
import com.haier.shop.dao.shopwrite.InvoiceSAPLogsWriteDao;
import com.haier.shop.model.InvoiceSAPLogs;
import com.haier.shop.service.ShopInvoiceSAPLogsService;

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
public class ShopInvoiceSAPLogsServiceImpl implements ShopInvoiceSAPLogsService {

    @Autowired
    private InvoiceSAPLogsReadDao invoiceSAPLogsReadDao;
    @Autowired
    private InvoiceSAPLogsWriteDao invoiceSAPLogsWriteDao;

    @Override
    public List<InvoiceSAPLogs> getByInvoiceIdAndPushType(InvoiceSAPLogs invoiceSAPLogs) {
        return invoiceSAPLogsReadDao.getByInvoiceIdAndPushType(invoiceSAPLogs);
    }

    @Override
    public Integer insert(InvoiceSAPLogs invoiceSAPLogs) {
        return invoiceSAPLogsWriteDao.insert(invoiceSAPLogs);
    }

    @Override
    public Integer updateInvoiceSAPLogs(InvoiceSAPLogs invoiceSAPLogs) {
        return invoiceSAPLogsWriteDao.updateInvoiceSAPLogs(invoiceSAPLogs);
    }

    @Override
    public Map<String, Object> getInvoiceSapLogListByPage(Map<String, Object> paramMap) {
        //获取SAP接口监控列表List
        List<Map<String, Object>> result = invoiceSAPLogsReadDao.getInvoiceSapLogList(paramMap);
        //获得条数
        int resultcount = invoiceSAPLogsReadDao.getRowCnts();
        Map<String, Object> retMap = new HashMap<String, Object>();
        retMap.put("total", resultcount);
        retMap.put("rows", result);

        return retMap;
    }

    @Override
    public void resetCount(Integer id) {
        invoiceSAPLogsWriteDao.resetCount(id);
    }

	@Override
	public List<InvoiceSAPLogs> getInvoiceSAPLogsList(Integer limitNum) {
		return invoiceSAPLogsReadDao.getInvoiceSAPLogsList(limitNum);
	}


}
