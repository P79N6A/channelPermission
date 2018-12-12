package com.haier.afterSale.service;

import com.haier.common.ServiceResult;

/**
 * 电子发票接口:包括查询、创建和作废
 *                       
 * @Filename: EInvoiceV2Service.java
 * @Version: 电子发票2.0
 * @Author: xinm
 * @Email: xinmeng@ehaier.com
 *
 */
public interface StockCenterEInvoiceV2Service {

    /**
     * 查询发票
     * @param queryXml 查询xml
     * @param orderProductId 网单id
     * @return
     */
    ServiceResult<String> queryInvoice(String foreignKey, String pushData);

    /**
     * 创建发票
     * @param invoiceXml 推送发票xml
     * @param orderProductId 网单id
     * @return
     */
    ServiceResult<String> createInvoice(String foreignKey, String pushData);

    /**
     * 作废发票
     * @param invoiceXml 作废发票xml
     * @param orderProductId 网单id
     * @return
     */
    ServiceResult<String> invalidInvoice(String foreignKey, String pushData);

}
