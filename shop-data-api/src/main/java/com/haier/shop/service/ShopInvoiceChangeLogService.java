package com.haier.shop.service;


import com.haier.shop.model.InvoiceChangeLogs;

import java.util.List;

/**
 * @author lichunsheng
 * @create 2018-01-07
 **/
public interface ShopInvoiceChangeLogService {

    /**
     * 根据invoiceId获取发票修改日志信息
     * memberInvoiceId=InvoiceChangeLogs.invoiceId
     * @param invoiceId
     * @return
     */
    List<InvoiceChangeLogs> getInvoiceChangeLogs(Integer invoiceId);

    /**
     * 保存发票修改日志
     * @param invoiceChangeLogs
     * @return
     */
    int insert(InvoiceChangeLogs invoiceChangeLogs);
}
