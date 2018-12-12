package com.haier.invoice.service;


import com.haier.invoice.util.HttpJsonResult;

import java.util.Map;

public interface BatchUpdateTaxInfoService {

    /**
     * 批量同步金税发票信息
     * @param cOrderSns
     * @param modelMap
     * @return
     */
    HttpJsonResult<Map<String, Object>> doBatchUpdateTaxInfo(String cOrderSns, Map<String, Object> modelMap);
}
