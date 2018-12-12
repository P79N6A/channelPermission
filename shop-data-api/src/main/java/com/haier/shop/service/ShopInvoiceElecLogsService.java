package com.haier.shop.service;


import com.haier.shop.model.InvoiceElectricLogs;

import java.util.Map;

/**
 * @author lichunsheng
 * @create 2018-01-03
 **/
public interface ShopInvoiceElecLogsService {

    /**
     * 更新电子发票日志信息
     * @param eInvoiceLog
     * @return
     */
    Integer updateByInvoiceIdAndType(InvoiceElectricLogs eInvoiceLog);

    /**
     * 创建电子发票日志信息
     * @param eInvoiceLog
     */
    Integer insertLog(InvoiceElectricLogs eInvoiceLog);

    /**
     * 分页查询电子发票日志，用于前台展示
     * @param paramMap
     * @return
     */
    Map<String, Object> getElectronicInvoiceLogsByPage(Map<String, Object> paramMap);
}
