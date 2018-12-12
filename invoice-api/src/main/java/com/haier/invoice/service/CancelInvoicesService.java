package com.haier.invoice.service;

import com.haier.common.BusinessException;

/**
 * @author lichunsheng
 * @create 2018-01-06
 **/
public interface CancelInvoicesService {

    /**
     * 取消发票
     * @param resendData
     * @return
     * @throws BusinessException
     */
    Integer cancelInvoices(String resendData) throws BusinessException;
}
