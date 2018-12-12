package com.haier.shop.dao.shopread;


import com.haier.shop.model.InvoicesWwwLogDispItem;
import com.haier.shop.model.InvoicesWwwLogs;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface InvoicesWwwLogsReadDao {

    InvoicesWwwLogs get(Integer orderProductId);

    /**
     * 获取 3w待人工处理发票列表 展示
     * @param paramMap
     * @return
     */
    List<InvoicesWwwLogDispItem> findInvoiceWwwLogList(Map<String, Object> paramMap);

    /**
     * 获得记录数
     * @return
     */
    int getRowCnts();

    /**
     * 根据orderId,获取所有日志信息
     * @param orderId
     * @return
     */
    List<InvoicesWwwLogs> getByOrderId(Integer orderId);
}