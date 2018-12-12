package com.haier.shop.service;

import java.util.List;

import com.haier.shop.model.InvoiceElectricLogs;



public interface InvoiceElectricLogsService {

    /**
     * 查询电子发票日志
     * @param invoiceId
     * @param type
     */
    InvoiceElectricLogs getByInvoiceIdAndType(Integer invoiceId,
                                              Integer type);

    /**
     * 查询电子发票日志列表
     * @param invoiceId
     * @param type
     */
    List<InvoiceElectricLogs> getByInvoiceIdAndTypeList(Integer invoiceId,
                                                         Integer type);

    /**
     * 创建发票日志
     * @param eInvoiceLog
     */
    int insertLog(InvoiceElectricLogs eInvoiceLog);

    /**
     * 修改发票日志
     * @param eInvoiceLog
     */
    int update(InvoiceElectricLogs eInvoiceLog);

    /**
     * 根据发票id修改发票日志
     * @param eInvoiceLog
     */
    int updateByInvoiceIdAndType(InvoiceElectricLogs eInvoiceLog);

}
