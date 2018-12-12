package com.haier.invoice.service;

import com.haier.common.ServiceResult;
import com.haier.invoice.model.eai.InvoiceEntity;
import com.haier.invoice.model.eai.InvoiceOutType;
import com.haier.invoice.model.eai.QueryInvoiceInputType;

public interface InvoiceToTaxService {

    /**
     * 向金税请求修改发票信息
     * @param inputType
     * @return
     */
    ServiceResult<InvoiceOutType> modifyInvoiceToTaxSys(InvoiceEntity inputType);

    /**
     * 向金税请求查询数据
     * @param inputType
     * @return
     */
    ServiceResult<InvoiceEntity> queryInvoiceToTaxSys(QueryInvoiceInputType inputType);

    /**
     * 向金税发送开票数据
     * @param invoiceEntity
     * @return
     */
    ServiceResult<InvoiceOutType> createInvoiceToTaxSys(InvoiceEntity invoiceEntity);

    /**
     * 向金税请求发票强制作废(实体只需设置网单号WDH和发票创建时间RRRQ)
     * @param invoiceEntity
     * @return
     */
    ServiceResult<InvoiceOutType> invalidInvoiceToTaxSys(InvoiceEntity invoiceEntity);

}
