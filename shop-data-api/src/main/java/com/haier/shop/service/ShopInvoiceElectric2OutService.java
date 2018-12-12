package com.haier.shop.service;


import com.haier.shop.model.InvoiceElectric2Out;

import java.util.List;

/**
 * @author lichunsheng
 * @create 2018-01-03
 **/
public interface ShopInvoiceElectric2OutService {

    /**
     * 查询要同步SAP的发票信息
     * @param invoiceElectric2Out
     * @return
     */
    List<InvoiceElectric2Out> getByInvoiceIdAndSendType(InvoiceElectric2Out invoiceElectric2Out);

    /**
     * 新增要同步SAP的发票信息
     * @param invoiceElectric2Out
     * @return
     */
    Integer insert(InvoiceElectric2Out invoiceElectric2Out);
}
