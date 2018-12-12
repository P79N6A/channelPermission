package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.InvoiceElectricSyncLogs;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author lichunsheng
 * @create 2018-01-03
 **/
@Mapper
public interface InvoiceElectricSyncLogsWriteDao {

    /**
     * 创建发票同步日志
     * @param log
     */
    int insert(InvoiceElectricSyncLogs log);

    /**
     * 修改发票同步日志
     * @param log
     */
    int update(InvoiceElectricSyncLogs log);
}
