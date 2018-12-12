package com.haier.invoice.services;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.common.json.JSONArray;
import com.alibaba.dubbo.common.json.ParseException;
import com.haier.common.BusinessException;
import com.haier.invoice.service.CancelInvoicesService;
import com.haier.shop.model.InvoiceConst;
import com.haier.shop.model.Invoices;
import com.haier.shop.service.ShopInvoiceService;
import com.haier.shop.service.ShopOrderOperateLogsService;
import com.haier.shop.service.ShopOrderProductsService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 批量取消电子发票
 **/
@Service
public class CancelInvoicesServiceImpl implements CancelInvoicesService {

    private static final Logger logger = LogManager.getLogger(CancelInvoicesServiceImpl.class);

    @Autowired
    private ShopInvoiceService shopInvoiceService;
    @Autowired
    private ShopOrderProductsService shopOrderProductsService;
    @Autowired
    private ShopOrderOperateLogsService shopOrderOperateLogsService;

    @Override
    public Integer cancelInvoices(String cancelData) throws BusinessException {

        List<Invoices> invoicesList = new ArrayList<Invoices>();
        JSONArray idArray = null;
        try {
            idArray = (JSONArray) JSON.parse(cancelData);
        } catch (ParseException e) {
            logger.error("取消发票数据转换异常，异常数据：" + cancelData);
            throw new BusinessException("取消发票数据转换异常");
        }
        for (int i = 0; i < idArray.length(); i++) {
            String id = String.valueOf(idArray.get(i));
            Invoices invoices = shopInvoiceService.getById(Integer.parseInt(id));
            if (invoices == null) {//发票不存在
                continue;
            }
            if (invoices.getStatusType() == InvoiceConst.CANCEL_KIND
                    && invoices.getSuccess() == InvoiceConst.SUCCESS) {//推送取消且已经成功
                continue;
            }
            invoices.setStatusType(InvoiceConst.CANCEL_KIND);//推送取消
            invoices.setSuccess(0);//待推送
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
            }
        }
        return successCount;
    }
}
