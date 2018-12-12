package com.haier.invoice.service;

/**
 * @author lichunsheng
 * @create 2018-01-06
 **/
public interface InvalidInvoicesService {

    Integer InvalidInvoices(String forceCancelData, String invalidReason);
}
