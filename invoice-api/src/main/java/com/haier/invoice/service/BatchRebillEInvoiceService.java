package com.haier.invoice.service;

/**
 * @author lichunsheng
 * @create 2018-01-10
 **/
public interface BatchRebillEInvoiceService {

    /**
     * 批量重推电子发票
     * @param totalArray
     * @return
     */
    String batchRebillEInvoice(String[] totalArray);
}
