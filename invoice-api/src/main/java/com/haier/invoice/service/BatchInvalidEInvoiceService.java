package com.haier.invoice.service;

/**
 * @author lichunsheng
 * @create 2018-01-10
 **/
public interface BatchInvalidEInvoiceService {

    /**
     * 批量作废电子发票
     * @param totalArray
     * @return
     */
    String batchInvalidEInvoice(String[] totalArray);
}
