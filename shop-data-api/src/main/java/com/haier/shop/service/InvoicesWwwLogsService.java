package com.haier.shop.service;


import com.haier.common.BusinessException;
import com.haier.shop.model.InvoicesWwwLogDispItem;
import com.haier.shop.model.InvoicesWwwLogs;
import com.haier.shop.model.Orders;

import java.util.List;
import java.util.Map;

public interface InvoicesWwwLogsService {

    InvoicesWwwLogs get(Integer orderProductId);

    List<InvoicesWwwLogs> getByOrderId(Integer orderId);

    int insert(InvoicesWwwLogs invoicesWwwLogs);

    /**
     * 获取3w待人工处理发票列表展示
     * @param paramMap
     * @return
     */
    List<InvoicesWwwLogDispItem> findInvoiceWwwLogList(Map<String, Object> paramMap);

    /**
     * 获取3w待人工处理发票列表展示
     * @param paramMap
     * @return
     */
    Map<String, Object> findInvoiceWwwLogPage(Map<String, Object> paramMap);

    /**
     * 更新3w待人工处理发票信息
     * @param invoicesWwwLogs
     * @return
     */
    int updateInvoiceWwwLogs(InvoicesWwwLogs invoicesWwwLogs);

    /**
     * 从3w发票待人工处理界面 开电子发票 业务处理
     * @param invoicesWwwLogs
     * @param orderProductId
     * @param auditor
     * @return
     */
    String billElecInvoice(Orders orders, InvoicesWwwLogs invoicesWwwLogs, Integer orderProductId, String auditor) throws BusinessException;
}