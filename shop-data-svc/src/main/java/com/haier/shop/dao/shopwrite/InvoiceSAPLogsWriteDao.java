package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.InvoiceSAPLogs;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lichunsheng
 * @create 2018-01-03
 **/
@Mapper
public interface InvoiceSAPLogsWriteDao {

    List<InvoiceSAPLogs> getByInvoiceIdAndPushType(InvoiceSAPLogs invoiceSAPLogs);

    Integer updateInvoiceSAPLogs(InvoiceSAPLogs invoiceSAPLogs);

    void resetCount(@Param("id") Integer id);

    /**
     * 创建发票SAP日志信息
     */
    Integer insert(InvoiceSAPLogs invoiceSAPLogs);
}
