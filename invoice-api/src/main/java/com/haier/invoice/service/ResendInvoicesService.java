package com.haier.invoice.service;

import com.haier.common.BusinessException;

/**
 * @author lichunsheng
 * @create 2018-01-06
 **/
public interface ResendInvoicesService{

    Integer resendInvoices(String resendData) throws BusinessException;
}
