package com.haier.shop.service;


import com.haier.shop.model.InvoiceApiLogs;

import java.util.Map;

/**
 * @author lichunsheng
 * @create 2018-01-04
 **/
public interface ShopInvoiceApiLogsService {

    /**
     * 创建开票日志信息
     * @param apilogs
     */
    Integer insert(InvoiceApiLogs apilogs);

    /**
     * 开票日志列表
     * @param paramMap
     * @return
     */
    Map<String, Object> getInvoiceApiLogsByPage(Map<String, Object> paramMap);
    
    /**
     * 查询开票日志信息
     * @param id
     */
    InvoiceApiLogs get(Integer id);

    /**
     * 查询开票日志信息
     * @param invoiceId
     * @param type
     */
    InvoiceApiLogs getByInvoiceIdAndType( Integer invoiceId,
                                         Integer type);

    /**
     * 更新开票日志信息
     * @param invoice
     */
    int update(InvoiceApiLogs apilogs);

    /**
     * 更新开票日志信息
     * @param invoice
     */
    int updateByInvoiceIdAndType(InvoiceApiLogs apilogs);


}
