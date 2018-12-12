package com.haier.shop.dao.shopread;

import com.haier.shop.model.InvoiceSAPLogs;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author lichunsheng
 * @create 2018-01-03
 **/
@Mapper
public interface InvoiceSAPLogsReadDao {

    List<Map<String, Object>> getInvoiceSapLogList(Map<String, Object> paramMap);

    int getRowCnts();

    /**
     * 取得Invoice到SAP同步的数据
     * @param limitNum
     * @return
     */
    List<InvoiceSAPLogs> getInvoiceSAPLogsList(@Param(value = "limitNum") Integer limitNum);

    /**
     * 获取发票SAP日志信息列表
     *
     * @return
     */
    List<InvoiceSAPLogs> getByInvoiceIdAndPushType(InvoiceSAPLogs invoiceSAPLogs);
}
