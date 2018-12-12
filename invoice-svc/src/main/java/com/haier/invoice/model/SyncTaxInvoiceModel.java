package com.haier.invoice.model;

import com.haier.common.BusinessException;
import com.haier.common.ServiceResult;
import com.haier.common.util.StringUtil;
import com.haier.invoice.model.eai.InvoiceEntity;
import com.haier.invoice.model.eai.QueryInvoiceInputType;
import com.haier.invoice.service.InvoiceToTaxService;
import com.haier.shop.model.*;
import com.haier.shop.service.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 同步金税发票
 *
 * @author lichunsheng
 * @create 2018-01-06
 **/
@Service
public class SyncTaxInvoiceModel {

    private static final Logger logger = LogManager.getLogger(SyncTaxInvoiceModel.class);

    @Autowired
    private ShopInvoiceService shopInvoiceService;
    @Autowired
    private InvoiceToTaxService invoiceToTaxService;

    /**
     * 同步金税发票
     *
     * @param invoicesList
     * @return
     */
    public ServiceResult<Integer> syncStatusInvoices(List<Invoices> invoicesList) {

        ServiceResult<Integer> result = new ServiceResult<Integer>();
        int successCount = 0;
        if (invoicesList == null || invoicesList.size() <= 0) {
            result.setMessage("发票不存在!");
            result.setSuccess(false);
            result.setResult(0);
        } else {
            for (Invoices invoice : invoicesList) {
                try {
                    this.syncInvoiceFromTax(invoice);
                    successCount++;
                } catch (Exception e) {
                    logger.error("同步发票发生异常，发票编号：" + invoice.getCOrderSn(), e);
                }
            }
            result.setSuccess(true);
            result.setResult(successCount);
        }
        return result;
    }

    /**
     * 同步金税发票
     *
     * @param queryInvoice
     * @throws BusinessException
     */
    public void syncInvoiceFromTax(Invoices queryInvoice) throws BusinessException {
        Invoices invoices = shopInvoiceService.getById(queryInvoice.getId());
        if (invoices == null) {
            throw new BusinessException("同步的发票信息不存在");
        }
        String receiveData = null;
        Invoices entity = null;

        QueryInvoiceInputType inputType = new QueryInvoiceInputType();
        inputType.setWdh(invoices.getCOrderSn());
        ServiceResult<InvoiceEntity> queryResult = invoiceToTaxService
                .queryInvoiceToTaxSys(inputType);

        if (queryResult.getSuccess()) {
            InvoiceEntity resultInvoice = queryResult.getResult();
            entity = new Invoices();
            entity.setId(queryInvoice.getId());
            entity.setState(resultInvoice.getFpzt());
            entity.setEaiWriteState(
                    resultInvoice.getKpzt() == null || resultInvoice.getKpzt().equals(0) ? ""
                            : resultInvoice.getKpzt().toString());
            entity.setDrawer(resultInvoice.getKpman());
            entity.setInvoiceNumber(resultInvoice.getFphm());
            entity.setBackupFieldA(resultInvoice.getAdd1());
            entity.setBillingTime(
                    resultInvoice.getKprq() == null ? null : resultInvoice.getKprq().getTime() / 1000);
            entity.setEaiWriteTime(
                    resultInvoice.getSkrq() == null ? null : resultInvoice.getSkrq().getTime() / 1000);
            entity.setInvalidTime(
                    resultInvoice.getZfrq() == null ? null : resultInvoice.getZfrq().getTime() / 1000);
            entity.setSuccess(InvoiceConst.SUCCESS);
        }
        receiveData = queryResult.getSuccess() ? "" : "响应失败";
        receiveData = StringUtil.isEmpty(receiveData) ? ((entity == null) ? "响应结果为空" : "") : "";
        if (!StringUtil.isEmpty(receiveData)) {
            throw new BusinessException("同步EAI状态失败");
        }
        //更新金税相关的表数据
        shopInvoiceService.updateInvoiceStatus4Tax(entity, true);
    }
}
