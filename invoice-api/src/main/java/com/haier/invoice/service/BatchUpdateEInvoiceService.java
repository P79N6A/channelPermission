package com.haier.invoice.service;


import com.haier.invoice.util.HttpJsonResult;

import java.util.Map;

public interface BatchUpdateEInvoiceService {

    /**
     * 批量更新电子发票信息
     * @param cOrderSns
     * @param modelMap
     * @return
     */
    HttpJsonResult<Map<String, Object>> doBatchUpdateEInvoiceInfo(String cOrderSns, Map<String, Object> modelMap);

}
