package com.haier.invoice.service;


/**
 * @author lichunsheng
 * @create 2018-01-10
 **/
public interface BatchBillEInvoiceService {

    /**
     * 批量开电子发票
     * @param totalArray
     * @return
     */
    String batchBillEInvoice(String[] totalArray);
}
