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

    /**
     * 根据网单id开票
     * @param orderProductId
     * @return
     */
    String eInvoiceBatchBillingByOrderProductId(String orderProductId);

    /**
     * 根据网单id重新开票
     * @param orderProductId
     * @return
     */
    String eInvoiceBatchReBillingByOrderProductId(String orderProductId);
}
