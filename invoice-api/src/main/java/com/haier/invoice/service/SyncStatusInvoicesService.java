package com.haier.invoice.service;

import com.haier.common.ServiceResult;

/**
 * @author lichunsheng
 * @create 2018-01-06
 **/
public interface SyncStatusInvoicesService {

    /**
     * 同步发票状态
     * @param syncData
     * @return
     */
    ServiceResult<Integer> syncStatusInvoices(String syncData);
}
