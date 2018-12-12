package com.haier.invoice.service;

import java.util.Map;

/**
 * @author lichunsheng
 * @create 2018-01-05
 **/
public interface GetCustomerCodeService {

    /**
     * 获取客户码
     * @param cOrderSn
     * @return
     */
    Map<String, String> getCustomerCode(String cOrderSn);
}
