package com.haier.invoice.services;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.common.json.JSONArray;
import com.alibaba.dubbo.common.json.ParseException;
import com.haier.common.BusinessException;
import com.haier.common.ServiceResult;
import com.haier.invoice.model.SyncElecInvoiceModel;
import com.haier.invoice.model.SyncTaxInvoiceModel;
import com.haier.invoice.service.SyncStatusInvoicesService;
import com.haier.shop.model.Invoices;
import com.haier.shop.service.ShopInvoiceService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 同步发票状态
 **/
@Service
public class SyncStatusInvoicesServiceImpl implements SyncStatusInvoicesService {

    private static final Logger logger = LogManager.getLogger(SyncStatusInvoicesServiceImpl.class);

    @Autowired
    private ShopInvoiceService shopInvoiceService;
    @Autowired
    private SyncElecInvoiceModel syncElecInvoiceModel;
    @Autowired
    private SyncTaxInvoiceModel syncTaxInvoiceModel;

    @Override
    public ServiceResult<Integer> syncStatusInvoices(String syncData) {
        List<Invoices> invoicesTaxList = new ArrayList<Invoices>();
        List<Invoices> invoicesEInvList = new ArrayList<Invoices>();
        JSONArray cacelData = null;
        try {
            cacelData = (JSONArray) JSON.parse(syncData);
        } catch (ParseException e) {
            logger.error("同步发票状态数据转换异常，异常数据：" + syncData);
            throw new BusinessException("同步发票状态数据转换异常");
        }
        for (int i = 0; i < cacelData.length(); i++) {
            String id = String.valueOf(cacelData.get(i));
            Invoices invoices = shopInvoiceService.getById(Integer.parseInt(id));
            if (invoices == null) {//发票不存在
                continue;
            }
            if (invoices.getElectricFlag().equals(1)) {//电子发票列表
                invoicesEInvList.add(invoices);
            } else {//金穗发票列表
                invoicesTaxList.add(invoices);
            }
        }
        ServiceResult<Integer> invoiceResult = new ServiceResult<Integer>();
        ServiceResult<Integer> invoiceEInvResult = new ServiceResult<Integer>();
        ServiceResult<Integer> invoiceTaxResult = new ServiceResult<Integer>();
        try {
            invoiceEInvResult = syncElecInvoiceModel.syncInvoiceInfoFromEInvoiceSystem(invoicesEInvList);
        } catch (Exception e) {
            invoiceEInvResult.setMessage("同步电子发票信息失败!" + e.getMessage());
            logger.error("同步电子发票信息失败!", e);
        }
        try {
            invoiceTaxResult = syncTaxInvoiceModel.syncStatusInvoices(invoicesTaxList);
        } catch (Exception e) {
            invoiceTaxResult.setMessage("同步金穗发票信息失败!" + e.getMessage());
            logger.error("同步金穗发票信息失败!", e);
        }
        if (invoiceEInvResult.getSuccess() && invoiceTaxResult.getSuccess()) {
            invoiceResult.setResult(invoiceEInvResult.getResult().intValue()
                    + invoiceTaxResult.getResult().intValue());
        } else {
            if (!invoiceEInvResult.getSuccess() && !invoiceTaxResult.getSuccess()) {
                invoiceResult.setSuccess(false);
                invoiceResult.setMessage("...电子发票同步信息：" + invoiceEInvResult.getMessage()
                        + " \n...金穗发票同步信息：" + invoiceTaxResult.getMessage());
            } else {
                invoiceResult.setResult(invoiceEInvResult.getResult().intValue()
                        + invoiceTaxResult.getResult().intValue());
            }
        }
        return invoiceResult;
    }
}
