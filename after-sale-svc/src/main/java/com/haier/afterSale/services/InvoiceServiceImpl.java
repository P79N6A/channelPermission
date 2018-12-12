package com.haier.afterSale.services;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.haier.afterSale.model.InvoiceModel;
import com.haier.afterSale.service.InvoiceService;
import com.haier.common.ServiceResult;
@Service
public class InvoiceServiceImpl implements  InvoiceService{
	 private static Logger         log    = LoggerFactory.getLogger(InvoiceServiceImpl.class);

	@Autowired
	 private InvoiceModel          invoiceModel;
	 @Override
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
}
