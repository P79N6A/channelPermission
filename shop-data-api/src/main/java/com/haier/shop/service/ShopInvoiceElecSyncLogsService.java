package com.haier.shop.service;


import com.haier.shop.model.InvoiceElectricSyncLogs;

import java.util.List;

/**
 * @author lichunsheng
 * @create 2018-01-03
 **/
public interface ShopInvoiceElecSyncLogsService {

    /**
     * 获取电子发票同步日志信息
     * @param cOrderSn
     * @param statusType
     * @return
     */
    List<InvoiceElectricSyncLogs> getByCOrderSnAngStatusType(String cOrderSn, Integer statusType);

    /**
     * 创建发票同步日志
     * @param log
     */
    int insert(InvoiceElectricSyncLogs log);
}
