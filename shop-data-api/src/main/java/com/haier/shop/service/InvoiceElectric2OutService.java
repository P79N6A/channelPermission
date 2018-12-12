package com.haier.shop.service;

import java.util.List;

import com.haier.shop.model.InvoiceElectric2Out;



public interface InvoiceElectric2OutService {
    /**
     * 根据ID，获取电子发票队列对象
     * @param id
     * @return
     */
    InvoiceElectric2Out get(Integer id);

    /**
     * 根据发票ID（invoice_id）和业务类型（send_type），获取电子发票队列对象
     * @param id
     * @return
     */
    List<InvoiceElectric2Out> getByInvoiceIdAndSendType(InvoiceElectric2Out invoiceElectric2Out);

    /**
     * 创建电子发票同步外部列表
     * @param invoice
     */
    int insert(InvoiceElectric2Out invoiceElectric2Out);

    /**
     * 获取待同步HP系统发票信息列表
     * @param topx 取数条数
     * @return
     */
    List<InvoiceElectric2Out> getSendToHpList(Integer topx);

    /**
     * 获取待同步SAP系统发票信息列表
     * @param topx 取数条数
     * @return
     */
    List<InvoiceElectric2Out> getSendToSapList(Integer topx);

    /**
     * 同步发票后更新表
     * @return
     */
    Integer updateAfterSync(InvoiceElectric2Out invoiceElectric2Out);

}
