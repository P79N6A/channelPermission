package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.InvoiceApiLogs;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author lichunsheng
 * @create 2018-01-03
 **/
@Mapper
public interface InvoiceApiLogsWriteDao {

    /**
     * 创建开票日志信息
     * @param invoiceApiLogs
     */
    Integer insert(InvoiceApiLogs invoiceApiLogs);

    /**
     * 更新开票日志信息
     * @param invoiceApiLogs
     */
    int update(InvoiceApiLogs invoiceApiLogs);

    /**
     * 更新开票日志信息
     * @param invoiceApiLogs
     */
    int updateByInvoiceIdAndType(InvoiceApiLogs invoiceApiLogs);

}
