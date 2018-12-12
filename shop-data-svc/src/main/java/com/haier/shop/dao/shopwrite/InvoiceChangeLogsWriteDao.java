package com.haier.shop.dao.shopwrite;

import com.haier.shop.model.InvoiceChangeLogs;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author lichunsheng
 * @create 2018-01-07
 **/
@Mapper
public interface InvoiceChangeLogsWriteDao {

    int insert(InvoiceChangeLogs invoiceChangeLogs);

    int update(InvoiceChangeLogs invoiceChangeLogs);
}
