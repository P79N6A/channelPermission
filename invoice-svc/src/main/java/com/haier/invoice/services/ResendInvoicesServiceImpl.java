package com.haier.invoice.services;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.common.json.JSONArray;
import com.alibaba.dubbo.common.json.ParseException;
import com.haier.common.BusinessException;
import com.haier.invoice.service.ResendInvoicesService;
import com.haier.shop.model.InvoiceConst;
import com.haier.shop.model.Invoices;
import com.haier.shop.service.ShopInvoiceService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 重开发票
 **/
@Service
public class ResendInvoicesServiceImpl implements ResendInvoicesService {

    private static final Logger logger = LogManager.getLogger(ResendInvoicesServiceImpl.class);

    @Autowired
    private ShopInvoiceService shopInvoiceService;

    @Override
    public Integer resendInvoices(String resendData) throws BusinessException {
        JSONArray idArray = null;
        try {
            idArray = (JSONArray) JSON.parse(resendData);
        } catch (ParseException e) {
            logger.error("重推发票数据转换异常，异常数据：" + resendData);
            throw new BusinessException("重推发票数据转换异常");
        }
        List<Invoices> invoicesList = new ArrayList<Invoices>();
        for (int i = 0; i < idArray.length(); i++) {
            String id = String.valueOf(idArray.get(i));
            Invoices invoices = shopInvoiceService.getById(Integer.parseInt(id));
            if (invoices == null) {//发票不存在
                continue;
            }
            if (invoices.getSuccess() == InvoiceConst.SUCCESS) {//发票已经推送成功
                continue;
            }
            invoices.setTryNum(0);
            invoicesList.add(invoices);
        }
        int successCount = 0;
        for (Invoices invoice : invoicesList) {
            try {
                shopInvoiceService.updateInvoiceOperate(invoice);
                successCount++;
            } catch (Exception e) {
                logger.error("更新发票发生异常，发票编号：" + invoice.getCOrderSn(), e);
                throw new BusinessException("更新发票发生异常");
            }
        }
        return successCount;
    }
}
