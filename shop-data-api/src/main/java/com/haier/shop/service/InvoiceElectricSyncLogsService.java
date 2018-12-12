package com.haier.shop.service;

import java.util.List;

import com.haier.shop.model.InvoiceElectricSyncLogs;



public interface InvoiceElectricSyncLogsService {

    /**
     * 创建发票同步日志
     * @param log
     */
    int insert(InvoiceElectricSyncLogs log);

    /**
     * 修改发票同步日志
     * @param log
     */
    int update(InvoiceElectricSyncLogs log);

    /**
     * 根据网单号，获取电子发票同步日志对象
     * @param id
     * @return
     */
    List<InvoiceElectricSyncLogs> getByCOrderSn(String cOrderSn);

    List<InvoiceElectricSyncLogs> getByCOrderSnAngStatusType(String cOrderSn,
                                                              Integer statusType);

}
