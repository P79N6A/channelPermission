package com.haier.afterSale.service;

import com.haier.common.ServiceResult;
import com.haier.shop.model.InvoiceData2HP;

/**
 * 电子发票接口:包括查询、创建和作废
 *                       
 * @Filename: EInvoiceService.java
 * @Version: 1.0
 * @Author: yaoyu
 * @Email: yaoyu@ehaier.com
 *
 */
public interface StockCenterEInvoiceService {

    /**
     * 查询发票
     * @param queryXml 查询xml
     * @param orderProductId 网单id
     * @return
     */
    ServiceResult<String> queryInvoice(String queryXml, String orderProductId);

    /**
     * 创建发票
     * @param invoiceXml 推送发票xml
     * @param orderProductId 网单id
     * @return
     */
    ServiceResult<String> createInvoice(String invoiceXml, String orderProductId);

    /**
     * 作废发票
     * @param invoiceXml 作废发票xml
     * @param orderProductId 网单id
     * @return
     */
    ServiceResult<String> invalidInvoice(String invoiceXml, String orderProductId);

    /**
     * 电子发票开票成功后发HP
     * @param invoiceEntity 发票信息
     * @return
     */
    ServiceResult<Boolean> invoiceInfoToHpSys(final InvoiceData2HP invoiceData2HP);

}
