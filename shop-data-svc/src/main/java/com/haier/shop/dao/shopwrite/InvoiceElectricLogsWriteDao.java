package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.InvoiceElectricLogs;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author lichunsheng
 * @create 2018-01-03
 **/
@Mapper
public interface InvoiceElectricLogsWriteDao {

    /**
     * 创建发票日志
     * @param eInvoiceLog
     */
    int insertLog(InvoiceElectricLogs eInvoiceLog);

    /**
     * 修改发票日志
     * @param eInvoiceLog
     */
    int update(InvoiceElectricLogs eInvoiceLog);

    /**
     * 根据发票id修改发票日志
     * @param eInvoiceLog
     */
    int updateByInvoiceIdAndType(InvoiceElectricLogs eInvoiceLog);
}
