package com.haier.shop.service;


import com.haier.shop.model.InvoiceSAPLogs;

import java.util.List;
import java.util.Map;

/**
 * @author lichunsheng
 * @create 2018-01-03
 **/
public interface ShopInvoiceSAPLogsService {

    /**
     * 获取发票SAP日志信息列表
     *
     * @return
     */
    List<InvoiceSAPLogs> getByInvoiceIdAndPushType(InvoiceSAPLogs invoiceSAPLogs);

    /**
     * 创建发票SAP日志信息
     */
    Integer insert(InvoiceSAPLogs invoiceSAPLogs);

    /**
     * 更新
     * @param invoiceSAPLogs
     */
    Integer updateInvoiceSAPLogs(InvoiceSAPLogs invoiceSAPLogs);

    /**
     *  获取SAP接口监控列表List
     * @param paramMap
     * @return
     */
    Map<String, Object> getInvoiceSapLogListByPage(Map<String, Object> paramMap);

    /**
     * 重置推送次数
     * @param id
     */
    void resetCount (Integer id);
    
    /**
     * 取得Invoice到SAP同步的数据
     * @param limitNum
     * @return
     */
    List<InvoiceSAPLogs> getInvoiceSAPLogsList(Integer limitNum);
}
