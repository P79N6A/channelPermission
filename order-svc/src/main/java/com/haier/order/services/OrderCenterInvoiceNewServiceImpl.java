package com.haier.order.services;

import com.haier.common.ServiceResult;
import com.haier.order.model.InvoiceModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OrderCenterInvoiceNewServiceImpl {
    private static Logger log = LoggerFactory.getLogger(OrderCenterInvoiceNewServiceImpl.class);
    @Autowired
    private InvoiceModel invoiceModel;

    public void setInvoiceModel(InvoiceModel invoiceModel) {
        this.invoiceModel = invoiceModel;
    }

    public String getCustomerCode(int productID) {
        return invoiceModel.getCustomerCode(productID);
    }

    public ServiceResult<BigDecimal> getInvoiceAmount(String cOrderSn) {
        ServiceResult<BigDecimal> result = new ServiceResult<BigDecimal>();
        try {
            BigDecimal count = invoiceModel.getInvoiceAmount(cOrderSn);
            if (count != null && count.compareTo(BigDecimal.ZERO) >= 0) {
                result.setMessage("");
                result.setSuccess(true);
                result.setResult(count);
            } else {
                result.setMessage("计算发票金额出错，请检查日志");
                result.setSuccess(false);
                result.setResult(count);
            }
        } catch (Exception e) {
            log.error("生成发票时, 发生未知异常：", e);
            result.setMessage("服务器发生异常：" + e.getMessage());
            result.setSuccess(false);
            result.setResult(null);
        }
        return result;
    }

    public ServiceResult<BigDecimal> getInvoiceAmount(String cOrderSn, boolean flag) {
        ServiceResult<BigDecimal> result = new ServiceResult<BigDecimal>();
        try {
            BigDecimal count = invoiceModel.getInvoiceAmount(cOrderSn, flag);
            if (count != null && count.compareTo(BigDecimal.ZERO) >= 0) {
                result.setMessage("");
                result.setSuccess(true);
                result.setResult(count);
            } else {
                result.setMessage("计算发票金额出错，请检查日志");
                result.setSuccess(false);
                result.setResult(count);
            }
        } catch (Exception e) {
            log.error("生成发票时, 发生未知异常：", e);
            result.setMessage("服务器发生异常：" + e.getMessage());
            result.setSuccess(false);
            result.setResult(null);
        }
        return result;
    }
}
