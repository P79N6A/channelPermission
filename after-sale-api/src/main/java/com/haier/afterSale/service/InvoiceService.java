package com.haier.afterSale.service;

import java.math.BigDecimal;

import com.haier.common.ServiceResult;

public interface InvoiceService {
    /**
     * 发票金额计算(原有计算方式)
     * @param cOrderSn 网单号
     * @return
     */
    ServiceResult<BigDecimal> getInvoiceAmount(String cOrderSn);
}
